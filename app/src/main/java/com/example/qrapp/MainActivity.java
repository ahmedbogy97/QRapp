package com.example.qrapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.qrapp.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    EditText qr_value;
//    Button generateBtn,scanBtn;
    ImageView qrImage;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_qr);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String current_user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();//get user id
        mContext = this;
     //   qr_value = findViewById(R.id.qrinput);
     //   generateBtn = findViewById(R.id.generateBtn);
        qrImage = findViewById(R.id.qrPlaceHolder);

        if(current_user_id.isEmpty()){
            qr_value.setError("WE NEED VALUE");
        }else {
            QRGEncoder qrgEncoder = new QRGEncoder(current_user_id, null, QRGContents.Type.TEXT, 500);
            Bitmap qrbits = qrgEncoder.getBitmap();
            qrImage.setImageBitmap(qrbits);
        }

//        findViewById(R.id.login_btn_signIn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(mContext, EditProfile.class));
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.edit_profile:
                startActivity(new Intent(mContext, EditProfile.class));
                return true;
            case R.id.logout:
                logout();
                return true;
            case R.id.scan_btn:
                startActivity(new Intent(mContext,Scanner.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        mAuth.signOut();
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}