package com.example.rad5.med_manager;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

public class Add_Medication extends AppCompatActivity {

    //initialize the toolbar
    Toolbar toolbar;

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
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
