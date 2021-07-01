package com.example.qrapp.splashScreen;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.qrapp.MainActivity;
import com.example.qrapp.R;
import com.example.qrapp.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;


public class SplashScreen extends AppCompatActivity implements SplashScreenInterface {
    private TextView txVw_version;
    private ImageView logo;
    private RelativeLayout layout;


    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        initUI();


        //background
        Animation smallToBig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);//create animation to resize background from small to big
        layout.setAnimation(smallToBig);

        //logo
        Animation alfa = AnimationUtils.loadAnimation(this, R.anim.alpha);//create Animation
        txVw_version.startAnimation(alfa);
        logo.startAnimation(alfa);//set Animation to logo

        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();


        /* New Handler to start the (login | main )Activities
         * and close this Splash-Screen after some seconds.*/
        /* Duration of wait */
        int SPLASH_DISPLAY_LENGTH = 3000;
        new Handler().postDelayed(() -> {

            updateUI(currentUser);
        }, SPLASH_DISPLAY_LENGTH);


    }

    @Override
    public void initUI() {
        txVw_version = findViewById(R.id.splash_txVw_version);
        logo = findViewById(R.id.splash_logo);//find logo with id
        layout = findViewById(R.id.splash_background);//find layout(main) with id
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            moveTo(MainActivity.class);
        } else {
            moveTo(LoginActivity.class);
        }
    }

    // update UI
    @Override
    public void moveTo(Class toClass) {
        startActivity(new Intent(SplashScreen.this, toClass));
        finish();
    }
}
