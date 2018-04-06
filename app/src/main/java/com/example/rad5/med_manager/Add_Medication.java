package com.example.rad5.med_manager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rad5.med_manager.Help_Classes.DatePicker;
import com.example.rad5.med_manager.Help_Classes.Medication;
import com.example.rad5.med_manager.Help_Classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Add_Medication extends AppCompatActivity {

    //debugging tag
    private static String TAG = "debugger";

    //initialize the toolbar
    Toolbar toolbar;

    EditText medicationName;
    EditText description;
    EditText frequency;
    EditText startDate;
    EditText endDate;

    FloatingActionButton addMedication;

    FirebaseUser currentUser;
    //instantiate firebase database
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__medication);

        // Check for existing Google Sign In account
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

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

        medicationName = (EditText) findViewById(R.id.edt_name);
        description = (EditText) findViewById(R.id.edt_description);
        frequency = (EditText) findViewById(R.id.edt_frequency);
        startDate = (EditText) findViewById(R.id.edt_start_date);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePicker(Add_Medication.this, R.id.edt_start_date);
            }
        });

        endDate = (EditText) findViewById(R.id.edt_end_date);
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePicker(Add_Medication.this, R.id.edt_end_date);
            }
        });

        addMedication = (FloatingActionButton) findViewById(R.id.edtMed_done);
        addMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Hide the keyboard
                InputMethodManager keyboard = (InputMethodManager) getSystemService(Add_Medication.this.INPUT_METHOD_SERVICE);
                keyboard.hideSoftInputFromWindow(medicationName.getWindowToken(), 0);

                //check if any field is empty
                if (medicationName.getText().toString().isEmpty()
                        || description.getText().toString().isEmpty()
                        || frequency.getText().toString().isEmpty()
                        || startDate.getText().toString().isEmpty()
                        || endDate.getText().toString().isEmpty()){
                    Snackbar.make(view, "Please all fields are required", Snackbar.LENGTH_LONG).show();
                }
                else {
                    addNewMedication(medicationName.getText().toString(),
                            description.getText().toString(),
                            frequency.getText().toString(),
                            startDate.getText().toString(),
                            endDate.getText().toString());
                }

                Toast.makeText(Add_Medication.this, "Medication added successfully", Toast.LENGTH_LONG).show();
                finish();
            }
        });


        // get firebase database instance
        database = FirebaseDatabase.getInstance();



    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    /**
     * create a new user
     * @param name the name of the medication
     * @param description the description about the medication
     * @param frequency the frequency at which the medication is taken
     * @param startDate the date at which the medication starts
     * @param endDate the date at which the medication ends
     */
    private void addNewMedication(String name, String description, String frequency, String startDate, String endDate) {

        //get reference to the medications key in the database
        DatabaseReference myRef = database.getReference("users")
                .child(currentUser.getDisplayName()).child("medications");

        //create a new Medication object
        Medication medication = new Medication(description, frequency, startDate, endDate);
        Map<String, Object> userValues = medication.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(name, userValues);

        //update the medications with the new values
        myRef.updateChildren(childUpdates);
    }
}
