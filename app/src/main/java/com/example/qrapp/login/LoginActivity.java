package com.example.qrapp.login;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qrapp.MainActivity;
import com.example.qrapp.R;
import com.example.qrapp.Scanner;
import com.example.qrapp.Signup;
import com.example.qrapp.services.ButtonAnim;
import com.example.qrapp.services.Connectivity;
import com.example.qrapp.services.Dialogs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import tableNames.user.Device;
import tableNames.user.Users;

import static com.example.qrapp.R.id.edit_btn1;
import static com.example.qrapp.R.id.login_btn_signIn;
import static com.example.qrapp.R.id.login_txVw_forgotPass;
import static com.example.qrapp.R.id.scan_btn;


public class LoginActivity extends AppCompatActivity implements LoginInterface, View.OnClickListener {
    private Dialog mProgress;

    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;

    private EditText edTx_email, edTx_password;

    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        initUI();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void initUI() {
        //Buttons
        findViewById(login_btn_signIn).setOnClickListener(this);//  set On Click Listener to  login_btn_signIn
        findViewById(login_txVw_forgotPass).setOnClickListener(this);//  set On Click Listener to  login_txVw_forgotPass
        findViewById(edit_btn1).setOnClickListener(this);// set on click listener to sign up
        findViewById(scan_btn).setOnClickListener(this);//set on click listener to scan
        //save views in variable
        edTx_email = findViewById(R.id.login_edTx_email);//save view(login_edTx_email) in variable(edTx_email)
        edTx_password = findViewById(R.id.login_edTx_password);//save view(login_edTx_password) in variable(edTx_password)


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
                    case R.id.login_btn_signIn:
                        // check internet connection
                        if (Connectivity.isConnected(mContext)) {
                            checkEmailAndPassword();
                        } else {
                            Toast.makeText(mContext, R.string.no_internet_connection, Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.login_txVw_forgotPass:
                        createDialogToEditPassword();
                        break;
                    case edit_btn1:
                        Intent mainIntent = new Intent(LoginActivity.this, Signup.class);
                        startActivity(mainIntent);
                        break;
                    case scan_btn:
                        Intent intent = new Intent(LoginActivity.this, Scanner.class);
                        startActivity(intent);
                        break;
                    default:
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
    public void checkEmailAndPassword() {

        String email = edTx_email.getText().toString();//"Ahmedsayed.cs2019@gmail.com";//get email from login_edTx_email(EditText)
        String password = edTx_password.getText().toString();//"123456";//get email from login_edTx_password(EditText)

        // check if email or password editText is empty
        if (!TextUtils.isEmpty(email)) {
            if (!TextUtils.isEmpty(password)) {

                mProgress = Dialogs.createProgressBarDialog(mContext, getString(R.string.please_wait_while_we_check_your_credentials));
                mProgress.show();

                loginUser(email, password);// check if user has access to login or not
//
//                startPhoneNumberVerification();

            } else {
//                edTx_password.setErrorEnabled(true);
                edTx_email.setFocusable(true);
                edTx_password.setError(getString(R.string.password_is_required));
            }
        } else {
//            edTx_email.setErrorEnabled(true);
            edTx_email.setFocusable(true);
            edTx_email.setError(getString(R.string.email_is_required));
        }
    }

    @Override
    public void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        mProgress.dismiss();
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);

                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);   //clear activities MyTrips
                        startActivity(mainIntent);//finish main activity
                        //finish login activity

                        finish();
                                        } else {

                        mProgress.hide();

                        String task_result = Objects.requireNonNull(task.getException()).getMessage();

                        Toast.makeText(LoginActivity.this, "Error : " + task_result, Toast.LENGTH_LONG).show();

                    }

                });


    }



    @Override
    public void saveUserData() {
        String current_user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();//get user id

        HashMap<String, String> userMap = new HashMap<>();
        userMap.put(Device.getIs_active(), "yes");
        userMap.put(Device.getOS(), "Android");



        mDatabase
                .child(Users.getTable_name())
                .child(current_user_id)
                .child(Device.getTable_name())
                .setValue(userMap).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mProgress.dismiss();
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);

                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);   //clear activities MyTrips
                        startActivity(mainIntent);//finish main activity
                        finish();//finish login activity

                    }else {
                        mProgress.dismiss();
                        Toast.makeText(mContext, "Error with save data", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void createDialogToEditPassword() {
        final Dialog dialog = new Dialog(mContext, R.style.Theme_Dialog);
        dialog.setContentView(R.layout.dialog_forget_password);
        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimationLeft;
        //find Views

        final EditText dialogForgetPass_edtx_email = dialog.findViewById(R.id.dialogForgetPass_edTx_email);
        Button but_done = dialog.findViewById(R.id.dialogVerification_but_done);
        but_done.setOnClickListener(v -> {
            forgetPass(dialogForgetPass_edtx_email.getText().toString());
            Toast.makeText(mContext, getString(R.string.check_your_email), Toast.LENGTH_SHORT).show();
        });
        Button but_edit = dialog.findViewById(R.id.dialogVerification_but_edit);
        but_edit.setOnClickListener(v -> dialog.dismiss());


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void forgetPass(String mail) {
        Toast.makeText(mContext, mail, Toast.LENGTH_SHORT).show();
        FirebaseAuth.getInstance().sendPasswordResetEmail(mail)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
//                                Log.d(TAG, "Email sent.");
                        Toast.makeText(mContext, getString(R.string.check_your_email), Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        startActivity(new Intent(mContext, StartActivity.class));
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//    }
}
