package com.example.qrapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qrapp.services.ButtonAnim;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import tableNames.user.Device;
import tableNames.user.Users;

public class Signup extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private TextView banner, required, optional;
    private EditText fullname, email, password_toggle, phone, id, phone1, phone2, phone3, age, medicine, diseases, surgeries;
    private Button signup_btn1;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Spinner bloodType;
    private String bloodTypeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);



        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


//        banner = (TextView) findViewById(R.id.banner);
//        banner.setOnClickListener(this);

        signup_btn1 = (Button) findViewById(R.id.edit_btn1);
        signup_btn1.setOnClickListener(this);

        findViewById(R.id.closeImageView).setOnClickListener(this);
        AppCompatTextView title = findViewById(R.id.titleTextView);
        title.setText("Generate To Be Save");

        fullname = (EditText) findViewById(R.id.fullname);
        email = (EditText) findViewById(R.id.email);
        email.getText().toString().trim();
        password_toggle = (EditText) findViewById(R.id.password_toggle);
        phone = (EditText) findViewById(R.id.phone);
        id = (EditText) findViewById(R.id.id);
        bloodType = findViewById(R.id.bloodtype);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this ,R.array.bloodType,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodType.setAdapter(adapter);
        bloodType.setOnItemSelectedListener(this);
        phone1 = (EditText) findViewById(R.id.phone1);
        phone2 = (EditText) findViewById(R.id.phone2);
        phone3 = (EditText) findViewById(R.id.phone3);
        age = (EditText) findViewById(R.id.age);
        medicine = (EditText) findViewById(R.id.medicine);
        diseases = (EditText) findViewById(R.id.diseases);
        surgeries = (EditText) findViewById(R.id.surgeries);

    }

    @Override
    public void onClick(View v) {

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
//            case R.id.banner:
//                startActivity(new Intent(this, LoginActivity.class));
//                break;
                    case R.id.edit_btn1:
                        signup_btn1();
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
    //super.onBackPressed();

    private void signup_btn1() {
        String full_name = fullname.getText().toString().trim();
        String e_mail = email.getText().toString().trim();
        String password = password_toggle.getText().toString().trim();
        String phone_num = phone.getText().toString().trim();
        String Id = id.getText().toString().trim();

        String phone_1 = phone1.getText().toString().trim();
        String phone_2 = phone2.getText().toString().trim();
        String phone_3 = phone3.getText().toString().trim();
        String Age = age.getText().toString().trim();
        String Medicine = medicine.getText().toString().trim();
        String Diseases = diseases.getText().toString().trim();
        String Surgeries = surgeries.getText().toString().trim();

        if (full_name.isEmpty()) {
            fullname.setError("Full name is required");
            fullname.requestFocus();
            return;
        }
        if (e_mail.isEmpty()) {
            email.setError("E-mail is riquired");
            email.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            password_toggle.setError("Password is required");
            password_toggle.requestFocus();
            return;
        }
        if (password.length() < 6) {
            password_toggle.setError("Min password length should be 6 characters!");
            password_toggle.requestFocus();
            return;

        }
        if (phone_num.isEmpty()) {
            phone.setError("Phone is required");
            phone.requestFocus();
            return;
        }
        if (Id.isEmpty()) {
            id.setError("Id is required");
            id.requestFocus();
            return;
        }
        if (bloodTypeValue.equals("Blood Type")) {
            Toast.makeText(Signup.this, "Blood type is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone_1.isEmpty()) {
            phone1.setError("Phone is required");
            phone1.requestFocus();
            return;
        }
        if (Age.isEmpty()) {
            age.setError("Age is required");
            age.requestFocus();
            return;
        }
        loginUser(e_mail, password);
    }

    public void loginUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        saveUserData();
                    } else {


                        String task_result = Objects.requireNonNull(task.getException()).getMessage();

                        Toast.makeText(Signup.this, "Error : " + task_result, Toast.LENGTH_LONG).show();

                    }

                });

    }

    public void saveUserData() {
        String current_user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();//get user id

        String full_name = fullname.getText().toString().trim();
        String e_mail = email.getText().toString().trim();
        String password = password_toggle.getText().toString().trim();
        String phone_num = phone.getText().toString().trim();
        String Id = id.getText().toString().trim();

        String phone_1 = phone1.getText().toString().trim();
        String phone_2 = phone2.getText().toString().trim();
        String phone_3 = phone3.getText().toString().trim();
        String Age = age.getText().toString().trim();
        String Medicine = medicine.getText().toString().trim();
        String Diseases = diseases.getText().toString().trim();
        String Surgeries = surgeries.getText().toString().trim();

        HashMap<String, String> userMap = new HashMap<>();
        userMap.put(Device.getIs_active(), "yes");
        userMap.put(Device.getOS(), "Android");
        userMap.put(Users.getUserName(), full_name);
        userMap.put(Users.getEmail(), e_mail);
        userMap.put(Users.getPhone(), phone_num);

        userMap.put(Users.getBlood_type(), bloodTypeValue);
        userMap.put(Users.getPhone_1(), phone_1);
        userMap.put(Users.getPhone_2(), phone_2);
        userMap.put(Users.getPhone_3(), phone_3);
        userMap.put(Users.getID(), Id);
        userMap.put(Users.getAge(), Age);
        userMap.put(Users.getMedicine(), Medicine);
        userMap.put(Users.getDiseases(), Diseases);
        userMap.put(Users.getSurgeries(), Surgeries);

        mDatabase.child(Users.getTable_name())
                .child(current_user_id)

                .setValue(userMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Intent mainIntent = new Intent(Signup.this, MainActivity.class);

                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);   //clear activities MyTrips
                startActivity(mainIntent);//finish main activity
                finish();//finish login activity
            } else {
                Toast.makeText(Signup.this, "Error with save data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        bloodTypeValue = parent.getItemAtPosition(position).toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}