package com.example.salma.doctolib;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        passToTheNextActivity();
    }

    private void passToTheNextActivity() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                final Intent mainIntent;

                mainIntent = new Intent(getApplicationContext(), MainActivity.class);

                SplashScreen.this.startActivity(mainIntent);
                //setLanguage();
                SplashScreen.this.finish();
            }
        }, SPLASH_TIME_OUT);
    }




}
