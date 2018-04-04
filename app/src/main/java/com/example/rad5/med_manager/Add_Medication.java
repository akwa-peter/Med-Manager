package com.example.rad5.med_manager;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Add_Medication extends AppCompatActivity {

    //debugging tag
    private static String TAG = "debugger";

    //initialize the toolbar
    Toolbar toolbar;

    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__medication);

        //Add Action bar to the addMedication activity
        toolbar = (Toolbar) findViewById(R.id.addMedication_toolbar);
        setSupportActionBar(toolbar);

        // Add menu back to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        //set an onClick listener on the profile icon
        LinearLayout profileButton = (LinearLayout) findViewById(R.id.profile);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Add_Medication.this, User_Profile.class));
            }
        });



        /**
         * Database configurations
         */

        // get firebase database instance
        database = FirebaseDatabase.getInstance();
        //Write a message to the database
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");
//
        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.d(TAG, "Failed to read value.", error.toException());
//            }
//        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
