package com.vincent.hoangnguyen.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.progress_bar_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // lấy user hiện tại check xem nếu có user thì mở Main
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                // ko có user thì mở màn login
                if(firebaseUser == null){
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
                // có người dùng
                else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        },1000);
    }
}