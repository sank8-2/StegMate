package com.devcrushers.stegmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("Login",MODE_PRIVATE);
                Boolean check = preferences.getBoolean("flag",false);

                Intent i;
                if(check){
                    i=new Intent(SplashScreen.this,MainActivity.class);
                }else {
                    i=new Intent(SplashScreen.this,Signin.class);
                }
                startActivity(i);
                finish();
            }
        },2000);

    }
}