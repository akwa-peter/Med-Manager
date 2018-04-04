package com.example.rad5.med_manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
                    sleep(3000);
                    //check if user is logged in
                    // Check for existing Google Sign In account, if the user is already signed in
                    // the GoogleSignInAccount will be non-null.
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                    if (currentUser != null){
                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                        finish();
                    }
                    else {
                        startActivity(new Intent(SplashScreen.this, Login.class));
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        //Start the thread
        my_thread.start();
    }
}
