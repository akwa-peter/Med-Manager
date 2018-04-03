package com.example.rad5.med_manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /*Create a thread method for the splash screen
        * and override the run method
        */
        Thread my_thread = new Thread(){
            @Override
            public void run() {
                try {
                    Intent mIntent = new Intent(SplashScreen.this, MainActivity.class);
                    sleep(3000);
                    startActivity(mIntent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        //Start the thread
        my_thread.start();
    }
}
