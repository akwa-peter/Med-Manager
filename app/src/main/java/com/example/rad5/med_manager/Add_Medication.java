package com.example.rad5.med_manager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rad5.med_manager.Help_Classes.Medication;
import com.example.rad5.med_manager.Help_Classes.toTitleCase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Add_Medication extends AppCompatActivity {

    //debugging tag
    private static String TAG = "debugger";

    //initialize the toolbar
    Toolbar toolbar;

    DatePickerDialog datePickerDialog;

    EditText medicationName;
    EditText description;
    EditText quantity;
    EditText frequency;
    EditText startDate;
    EditText endDate;
    private int zMedicationMonth;

    toTitleCase titleCase = new toTitleCase();

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

        // get firebase database instance
        database = FirebaseDatabase.getInstance();

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
        quantity = (EditText) findViewById(R.id.edt_Quantity);
        frequency = (EditText) findViewById(R.id.edt_frequency);

        //find the start date edit text view and set an onClick listener to trigger date dialog
        startDate = (EditText) findViewById(R.id.edt_start_date);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get the calender class instance and get the current date from the calender
                Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                //instantiate the date picker Dialog and set onDateListener to pick the selected date
                datePickerDialog = new DatePickerDialog(Add_Medication.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
                        //set the date
                        startDate.setText(day + "/" + month + "/" + year);
                        zMedicationMonth = month;
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        //find the end date edit text view and set an onClick listener to trigger date dialog
        endDate = (EditText) findViewById(R.id.edt_end_date);
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get the calender class instance and get the current date from the calender
                Calendar calendar = Calendar.getInstance();

                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(Add_Medication.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
                                //set the date
                                endDate.setText(day + "/" + month + "/" + year);
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();
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
                        || quantity.getText().toString().isEmpty()
                        || frequency.getText().toString().isEmpty()
                        || startDate.getText().toString().isEmpty()
                        || endDate.getText().toString().isEmpty()){
                    Snackbar.make(view, "Please all fields are required", Snackbar.LENGTH_LONG).show();
                }
                else {
                    addNewMedication(titleCase.toTitle(description.getText().toString()),
                            endDate.getText().toString(),
                            frequency.getText().toString(),
                            titleCase.toTitle(medicationName.getText().toString()),
                            quantity.getText().toString(),
                            startDate.getText().toString(),
                            zMedicationMonth);

                    Toast.makeText(Add_Medication.this, "Medication added successfully", Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });

        //get the intent that opened this activity
        Intent mIntent = getIntent();
        String preceedingIntent = mIntent.getStringExtra("intent");

        /**
         * Get the firebase database reference to the currently selected list item
         */
        final DatabaseReference ref = database.getReference()
                .child("users").child(currentUser.getDisplayName())
                .child("medications").child(preceedingIntent);

        //initialize the delete button and set an onClick listener to delete the medication
        final LinearLayout btnDelete = (LinearLayout) findViewById(R.id.delete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete the current selected medication
                ref.setValue(null);
                Toast.makeText(Add_Medication.this, "Medication deleted successfully", Toast.LENGTH_LONG).show();
                finish();
            }
        });


        //if the preceeding intent is triggered by the add new medication floating action button
        if (!preceedingIntent.equals("btn_add_medication")){

            //Change the title of the action bar to the medication name
            toolbar.setTitle(preceedingIntent);

            //disable all the edit text views
            medicationName.setFocusable(false);

            /**
             *add a value event listener to the reference
             * to get the value of each of each child
             */
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //get the value of the clicked medication as a Medication object
                    Medication medication = dataSnapshot.getValue(Medication.class);

                    //use the medication get methods to set the values for the edit medication edit views
                    medicationName.setText(medication != null ? medication.getName() : null);
                    description.setText(medication != null ? medication.getDescription() : null);
                    quantity.setText(medication != null ? medication.getQuantity() : null);
                    frequency.setText(medication != null ? medication.getFrequency() : null);
                    startDate.setText(medication != null ? medication.getStartDate() : null);
                    endDate.setText(medication != null ? medication.getEndDate() : null);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

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
    private void addNewMedication(String description, String endDate,
                                  String frequency, String name, String quantity,
                                  String startDate, int zMedicationMonth) {

        //get reference to the medications key in the database
        DatabaseReference myRef = database.getReference("users")
                .child(currentUser.getDisplayName()).child("medications");

        //create a new Medication object
        Medication medication = new Medication(description, endDate, frequency, name, quantity, startDate, zMedicationMonth);
        Map<String, Object> userValues = medication.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(name, userValues);

        //update the medications with the new values
        myRef.updateChildren(childUpdates);
    }

}
