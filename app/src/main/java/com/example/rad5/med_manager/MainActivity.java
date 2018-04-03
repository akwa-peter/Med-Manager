package com.example.rad5.med_manager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    FloatingActionButton add_medication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add Action bar to the main activity
        toolbar = (Toolbar) findViewById(R.id.mainActivity_toolbar);
        setSupportActionBar(toolbar);

        //find the add_medication floating action button and set onClick listener on it
        add_medication = (FloatingActionButton) findViewById(R.id.fab_add_medication);
        add_medication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Add_Medication.class));
            }
        });
    }

}
