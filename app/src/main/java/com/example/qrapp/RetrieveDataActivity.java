package com.example.qrapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.qrapp.services.Dialogs;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import tableNames.user.Users;

public class RetrieveDataActivity extends AppCompatActivity {


//    private List<User> userList;

    private String user_id;
//    private ListAdapter mAdapter;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_data_);
        Bundle bundle = getIntent().getExtras();

        mContext = this;

     //   user_id = "PoZeBrRbFoNqd9hzTnpr9SayELn1";
        user_id = bundle.getString("id");
//        userList = new ArrayList<>();
        initUI();
        viewUserData();
//        userDBref = FirebaseDatabase.getInstance().getReference(Device.getTable_name());
//
//        userDBref.child(user_id).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                userList.clear();
//                for (DataSnapshot child : snapshot.getChildren()) {
//                    for (DataSnapshot childd : child.getChildren()) {
//                        userList.add(new User(childd.getKey(), childd.getValue(String.class)));
//                    }
//                }
//                mAdapter.setItems(userList);
//                mAdapter.notifyDataSetChanged();
////
////                for (DataSnapshot userDatasnap : snapshot.getChildren()) {
////
//////                    User user = userDatasnap.getValue(User.class);
////                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
    private Dialog mProgress;
    private AppCompatTextView txVw_userName, txVw_email, txVw_phone, txVw_id, txVw_phone1, txVw_phone2, txVw_phone3, txVw_age, txVw_bloodtype, txVw_medicine, txVw_diseases, txVw_surgeries;

    private void initUI() {
        mProgress = Dialogs.createProgressBarDialog(mContext, getString(R.string.please_wait_while_we_check_your_credentials));
        txVw_userName = findViewById(R.id.retrieveData_date_userName);
        txVw_email = findViewById(R.id.retrieveData_date_email);
        txVw_phone = findViewById(R.id.retrieveData_date_phone);
        txVw_id = findViewById(R.id.retrieveData_date_Id);
        txVw_phone1 = findViewById(R.id.retrieveData_date_phone1);
        txVw_phone2 = findViewById(R.id.retrieveData_date_phone2);
        txVw_phone3 = findViewById(R.id.retrieveData_date_phone3);
        txVw_age = findViewById(R.id.retrieveData_date_age);
        txVw_bloodtype = findViewById(R.id.retrieveData_date_bloodType);
        txVw_medicine = findViewById(R.id.retrieveData_date_medicine);
        txVw_diseases = findViewById(R.id.retrieveData_date_diseases);
        txVw_surgeries = findViewById(R.id.retrieveData_date_surgeries);

        txVw_phone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCall(txVw_phone1.getText().toString());
            }
        });

        txVw_phone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCall(txVw_phone2.getText().toString());
            }
        });

        txVw_phone3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCall(txVw_phone3.getText().toString());
            }
        });
    }
    private void goToCall(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:$phoneNumber")) ;
            if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                mContext.startActivity(intent);
            }
        }
    }
    private void viewUserData() {
        mProgress.show();
        DatabaseReference mUserDatabase = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Users.getTable_name())
                .child(user_id);//current user Data pointer
        mUserDatabase.keepSynced(true);// keep current user Synced
        //if current user change his data
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) throws NullPointerException {
//                Log.d(TAG, "onDataChange: data "+dataSnapshot.getValue().toString() );
                if (dataSnapshot.child(Users.getUserName()).getValue() != null) {
                    String name = Objects.requireNonNull(dataSnapshot.child(Users.getUserName()).getValue()).toString();//get name
                    txVw_userName.setText(name);
                }
                if (dataSnapshot.child(Users.getEmail()).getValue() != null) {
                    String email = Objects.requireNonNull(dataSnapshot.child(Users.getEmail()).getValue()).toString();//get email
                    txVw_email.setText(email);
                }
                if (dataSnapshot.child(Users.getID()).getValue() != null) {
                    String id = Objects.requireNonNull(dataSnapshot.child(Users.getID()).getValue()).toString();//get name
                    txVw_id.setText(id);
                }
                if (dataSnapshot.child(Users.getAge()).getValue() != null) {
                    String age = Objects.requireNonNull(dataSnapshot.child(Users.getAge()).getValue()).toString();//get age
                    txVw_age.setText(age);
                }
                if (dataSnapshot.child(Users.getBlood_type()).getValue() != null) {
                    String blood_type = Objects.requireNonNull(dataSnapshot.child(Users.getBlood_type()).getValue()).toString();//get bloodtype
                    txVw_bloodtype.setText(blood_type);
                }
                if (dataSnapshot.child(Users.getPhone()).getValue() != null) {
                    String phone = Objects.requireNonNull(dataSnapshot.child(Users.getPhone()).getValue()).toString();//get name
                    txVw_phone.setText(phone);
                }
                if (dataSnapshot.child(Users.getPhone_1()).getValue() != null) {
                    String phone1 = Objects.requireNonNull(dataSnapshot.child(Users.getPhone_1()).getValue()).toString();//get name
                    txVw_phone1.setText(phone1);
                }
                if (dataSnapshot.child(Users.getPhone_2()).getValue() != null) {
                    String phone2 = Objects.requireNonNull(dataSnapshot.child(Users.getPhone_2()).getValue()).toString();//get name
                    txVw_phone2.setText(phone2);
                }
                if (dataSnapshot.child(Users.getPhone_3()).getValue() != null) {
                    String phone3 = Objects.requireNonNull(dataSnapshot.child(Users.getPhone_3()).getValue()).toString();//get name
                    txVw_phone3.setText(phone3);
                }
                if (dataSnapshot.child(Users.getDiseases()).getValue() != null) {
                    String diseases = Objects.requireNonNull(dataSnapshot.child(Users.getDiseases()).getValue()).toString();//get name
                    txVw_diseases.setText(diseases);
                }
                if (dataSnapshot.child(Users.getMedicine()).getValue() != null) {
                    String medicine = Objects.requireNonNull(dataSnapshot.child(Users.getMedicine()).getValue()).toString();//get name
                    txVw_medicine.setText(medicine);
                }
                if (dataSnapshot.child(Users.getSurgeries()).getValue() != null) {
                    String surgeries = Objects.requireNonNull(dataSnapshot.child(Users.getSurgeries()).getValue()).toString();//get name
                    txVw_surgeries.setText(surgeries);
                }
                mProgress.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mProgress.dismiss();
            }
        });
//        btn_Edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (txVw_fullname.getText().toString().isEmpty() ||
//                        txVw_age.getText().toString().isEmpty() ||
//                        txVw_bloodtype.getText().toString().isEmpty() ||
//                        txVw_email.getText().toString().isEmpty() ||
//                        txVw_diseases.getText().toString().isEmpty() ||
//                        txVw_phone.getText().toString().isEmpty() ||
//                        txVw_id.getText().toString().isEmpty() ||
//                        txVw_medicine.getText().toString().isEmpty() ||
//                        txVw_surgeries.getText().toString().isEmpty() ||
//                        txVw_phone1.getText().toString().isEmpty() ||
//                        txVw_phone2.getText().toString().isEmpty() ||
//                        txVw_phone3.getText().toString().isEmpty()) ;
//            }
//        });
    }


//    private void initializeRecyclerViewFromWitnTools() {
//        RecyclerView rcyVw_criminalsList = findViewById(R.id.myListview);
//        LinearLayoutManager mLinearLayoutManagerFrom = new LinearLayoutManager(RetrieveDataActivity.this);
//        rcyVw_criminalsList.setLayoutManager(mLinearLayoutManagerFrom);
//        mAdapter = new ListAdapter();
//        rcyVw_criminalsList.setAdapter(mAdapter);
//    }
}