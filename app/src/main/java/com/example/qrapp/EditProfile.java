package com.example.qrapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.qrapp.services.ButtonAnim;
import com.example.qrapp.services.Dialogs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import tableNames.user.Device;
import tableNames.user.Users;

public class EditProfile extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static final String TAG = "EditProfile";
    private Button btn_Edit;
    private EditText edTx_fullname, edTx_email, edTx_phone, edTx_id, edTx_phone1, edTx_phone2, edTx_phone3, edTx_age, edTx_medicine, edTx_diseases, edTx_surgeries;
    private Dialog mProgress;
    private FirebaseAuth mAuth;
    private Spinner bloodType;

    private DatabaseReference mDatabase;
    private Context mContext;
    private String bloodTypeValue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mContext = this;

        initUI();

//        Intent data = getIntent();
//        String fullName = data.getStringExtra("fullName");
//        String emailEdit = data.getStringExtra("email");
//        String passwordEdit = data.getStringExtra("password");
//        String phoneEdit = data.getStringExtra("phone");
//        String idEdit = data.getStringExtra("id");
//        String bloodEdit = data.getStringExtra("blood");
//        String phone1Edit = data.getStringExtra("phone1");
//        String phone2Edit = data.getStringExtra("phone2");
//        String phone3Edit = data.getStringExtra("phone3");
//        String ageEdit = data.getStringExtra("age");
//        String medicineEdit = data.getStringExtra("medicine");
//        String diseasesEdit = data.getStringExtra("diseases");
//        String surgeriesEdit = data.getStringExtra("surgeries");


        viewUserData();


//        Log.d(TAG, "onCreate: " + fullName + " " + emailEdit + " " + passwordEdit + " " + phoneEdit + " " + idEdit + " " + bloodEdit + " " + phone1Edit + " " + phone2Edit + " " + phone3Edit + " " + ageEdit + " " + medicineEdit + " " + diseasesEdit + " " + surgeriesEdit);

    }
    ArrayAdapter<CharSequence> adapter;
    private void initUI() {
        mProgress = Dialogs.createProgressBarDialog(mContext, getString(R.string.please_wait_while_we_check_your_credentials));
        btn_Edit = findViewById(R.id.editProfile_btn_edit);
        btn_Edit.setOnClickListener(this);

        edTx_fullname = findViewById(R.id.editProfile_fullName);
        edTx_email = findViewById(R.id.editProfile_email);
        edTx_phone = findViewById(R.id.editProfile_phone);
        edTx_id = findViewById(R.id.editProfile_id);
        bloodType = findViewById(R.id.bloodtype);
        adapter = ArrayAdapter.createFromResource(this ,R.array.bloodType,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodType.setAdapter(adapter);
        bloodType.setOnItemSelectedListener(this);
        edTx_phone1 = findViewById(R.id.editProfile_phone1);
        edTx_phone2 = findViewById(R.id.editProfile_phone2);
        edTx_phone3 = findViewById(R.id.editProfile_phone3);
        edTx_age = findViewById(R.id.editProfile_age);
        edTx_medicine = findViewById(R.id.editProfile_medicine);
        edTx_diseases = findViewById(R.id.editProfile_diseases);
        edTx_surgeries = findViewById(R.id.editProfile_surgeries);

        findViewById(R.id.closeImageView).setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.titleTextView);
        title.setText("Generate To Be Save");
    }

    private void editProfile() {
        String full_name = edTx_fullname.getText().toString().trim();
        String e_mail = edTx_email.getText().toString().trim();
//        String password = password_toggle1.getText().toString().trim();
        String phone_num = edTx_phone.getText().toString().trim();
        String Id = edTx_id.getText().toString().trim();
        String phone1 = edTx_phone1.getText().toString().trim();
        String phone2 = edTx_phone2.getText().toString().trim();
        String phone3 = edTx_phone3.getText().toString().trim();
        String Age = edTx_age.getText().toString().trim();
        String Medicine = edTx_medicine.getText().toString().trim();
        String Diseases = edTx_diseases.getText().toString().trim();
        String Surgeries = edTx_surgeries.getText().toString().trim();

        if (full_name.isEmpty()) {
            edTx_fullname.setError("Full name is required");
            edTx_fullname.requestFocus();
            return;
        }
        if (e_mail.isEmpty()) {
            edTx_email.setError("E-mail is riquired");
            edTx_email.requestFocus();
            return;
        }

        if (phone_num.isEmpty()) {
            edTx_phone.setError("Phone is required");
            edTx_phone.requestFocus();
            return;
        }
        if (Id.isEmpty()) {
            edTx_id.setError("Id is required");
            edTx_id.requestFocus();
            return;
        }
        if (bloodTypeValue.equals("Blood Type")) {
            Toast.makeText(EditProfile.this, "Blood type is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone1.isEmpty()) {
            edTx_phone.setError("Phone is required");
            edTx_phone.requestFocus();
            return;
        }
        if (Age.isEmpty()) {
            edTx_age.setError("Age is required");
            edTx_age.requestFocus();
            return;
        }
        saveUserData();
    }

    public void saveUserData() {
        String current_user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();//get user id

        String full_name = edTx_fullname.getText().toString().trim();
        String e_mail = edTx_email.getText().toString().trim();
        String phone_num = edTx_phone.getText().toString().trim();
        String Id = edTx_id.getText().toString().trim();
        String phone_1 = edTx_phone1.getText().toString().trim();
        String phone_2 = edTx_phone2.getText().toString().trim();
        String phone_3 = edTx_phone3.getText().toString().trim();
        String Age = edTx_age.getText().toString().trim();
        String Medicine = edTx_medicine.getText().toString().trim();
        String Diseases = edTx_diseases.getText().toString().trim();
        String Surgeries = edTx_surgeries.getText().toString().trim();

        HashMap<String, String> userMap = new HashMap<>();
        userMap.put(Device.getIs_active(), "yes");
        userMap.put(Device.getOS(), "Android");
        userMap.put(Users.getUserName(), full_name);
        userMap.put(Users.getEmail(), e_mail);
        userMap.put(Users.getPhone(), phone_num);
        userMap.put(Users.getID(), Id);
        userMap.put(Users.getBlood_type(), bloodTypeValue);
        userMap.put(Users.getPhone_1(), phone_1);
        userMap.put(Users.getPhone_2(), phone_2);
        userMap.put(Users.getPhone_3(), phone_3);
        userMap.put(Users.getAge(), Age);
        userMap.put(Users.getMedicine(), Medicine);
        userMap.put(Users.getDiseases(), Diseases);
        userMap.put(Users.getSurgeries(), Surgeries);
//
//        mDatabase.child(Users.getTable_name())
//                .child(current_user_id)
//                .child(Device.getTable_name())

        FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Users.getTable_name())
                .child(current_user_id)
                .setValue(userMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Intent mainIntent = new Intent(EditProfile.this, MainActivity.class);

                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);   //clear activities MyTrips
                startActivity(mainIntent);//finish main activity
                finish();//finish login activity
            } else {
                Toast.makeText(EditProfile.this, "Error with save data", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void viewUserData() {
//        final CircleImageView cirImg_profileImage = mView.findViewById(R.id.profile_cirImg_profileImage);


        FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();//get get Current User data

        String current_user_id = mCurrentUser != null ? mCurrentUser.getUid() : null;//get current user id


        assert current_user_id != null;

//        mDatabase.child(Users.getTable_name())
//                .child(current_user_id);
        Log.d(TAG, "viewUserData: current_user_id " + current_user_id);

//        mDatabase.keepSynced(true);// keep current user Synced
        DatabaseReference mUserDatabase = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Users.getTable_name())
                .child(current_user_id);//current user Data pointer
        mUserDatabase.keepSynced(true);// keep current user Synced
        //if current user change his data
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) throws NullPointerException {
//                Log.d(TAG, "onDataChange: data "+dataSnapshot.getValue().toString() );
                if (dataSnapshot.child(Users.getUserName()).getValue() != null) {
                    String name = Objects.requireNonNull(dataSnapshot.child(Users.getUserName()).getValue()).toString();//get name
                    edTx_fullname.setText(name);
                }
                if (dataSnapshot.child(Users.getEmail()).getValue() != null) {
                    String email = Objects.requireNonNull(dataSnapshot.child(Users.getEmail()).getValue()).toString();//get email
                    edTx_email.setText(email);
                }
                if (dataSnapshot.child(Users.getID()).getValue() != null) {
                    String id = Objects.requireNonNull(dataSnapshot.child(Users.getID()).getValue()).toString();//get name
                    edTx_id.setText(id);
                }
                if (dataSnapshot.child(Users.getAge()).getValue() != null) {
                    String age = Objects.requireNonNull(dataSnapshot.child(Users.getAge()).getValue()).toString();//get age
                    edTx_age.setText(age);
                }
                if (dataSnapshot.child(Users.getBlood_type()).getValue() != null) {
                    String blood_type = Objects.requireNonNull(dataSnapshot.child(Users.getBlood_type()).getValue()).toString();//get bloodtype
//                    edTx_bloodtype.setText(blood_type);
//                    bloodType.setSelection
                    if (blood_type != null) {
                        int spinnerPosition = adapter.getPosition(blood_type);
                        bloodType.setSelection(spinnerPosition);
                    }
                }
                if (dataSnapshot.child(Users.getPhone()).getValue() != null) {
                    String phone = Objects.requireNonNull(dataSnapshot.child(Users.getPhone()).getValue()).toString();//get name
                    edTx_phone.setText(phone);
                }
                if (dataSnapshot.child(Users.getPhone_1()).getValue() != null) {
                    String phone1 = Objects.requireNonNull(dataSnapshot.child(Users.getPhone_1()).getValue()).toString();//get name
                    edTx_phone1.setText(phone1);
                }
                if (dataSnapshot.child(Users.getPhone_2()).getValue() != null) {
                    String phone2 = Objects.requireNonNull(dataSnapshot.child(Users.getPhone_2()).getValue()).toString();//get name
                    edTx_phone2.setText(phone2);
                }
                if (dataSnapshot.child(Users.getPhone_3()).getValue() != null) {
                    String phone3 = Objects.requireNonNull(dataSnapshot.child(Users.getPhone_3()).getValue()).toString();//get name
                    edTx_phone3.setText(phone3);
                }
                if (dataSnapshot.child(Users.getDiseases()).getValue() != null) {
                    String diseases = Objects.requireNonNull(dataSnapshot.child(Users.getDiseases()).getValue()).toString();//get name
                    edTx_diseases.setText(diseases);
                }
                if (dataSnapshot.child(Users.getMedicine()).getValue() != null) {
                    String medicine = Objects.requireNonNull(dataSnapshot.child(Users.getMedicine()).getValue()).toString();//get name
                    edTx_medicine.setText(medicine);
                }
                if (dataSnapshot.child(Users.getSurgeries()).getValue() != null) {
                    String surgeries = Objects.requireNonNull(dataSnapshot.child(Users.getSurgeries()).getValue()).toString();//get name
                    edTx_surgeries.setText(surgeries);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        btn_Edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (edTx_fullname.getText().toString().isEmpty() ||
//                        edTx_age.getText().toString().isEmpty() ||
//                        edTx_bloodtype.getText().toString().isEmpty() ||
//                        edTx_email.getText().toString().isEmpty() ||
//                        edTx_diseases.getText().toString().isEmpty() ||
//                        edTx_phone.getText().toString().isEmpty() ||
//                        edTx_id.getText().toString().isEmpty() ||
//                        edTx_medicine.getText().toString().isEmpty() ||
//                        edTx_surgeries.getText().toString().isEmpty() ||
//                        edTx_phone1.getText().toString().isEmpty() ||
//                        edTx_phone2.getText().toString().isEmpty() ||
//                        edTx_phone3.getText().toString().isEmpty()) ;
//            }
//        });
    }


    @Override
    public void onClick(final View v) {

        ViewPropertyAnimatorCompat animate = ViewCompat.animate(v);
        animate.setDuration(150);
        animate.scaleX(0.9f);
        animate.scaleY(0.9f);
        animate.setInterpolator(new ButtonAnim());
        animate.setListener(new ViewPropertyAnimatorListener() {
            @Override
            public void onAnimationStart(final View view) {

            }

            @Override
            public void onAnimationEnd(final View view) {
                switch (v.getId()) {
                    case R.id.editProfile_btn_edit:
                        Log.d(TAG, "onClick: editProfile_btn_edit ");
                        editProfile();
                        break;
                    case R.id.closeImageView:
                        onBackPressed();
                        break;

                }
            }

            @Override
            public void onAnimationCancel(final View view) {

            }
        });
        animate.withLayer();
        animate.start();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        bloodTypeValue = parent.getItemAtPosition(position).toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}