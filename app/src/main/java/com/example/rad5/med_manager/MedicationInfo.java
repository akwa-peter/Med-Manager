package com.example.rad5.med_manager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rad5.med_manager.Help_Classes.Medication;
import com.example.rad5.med_manager.Help_Classes.MedicationReminderIntentService;
import com.example.rad5.med_manager.Help_Classes.PreferenceUtils;
import com.example.rad5.med_manager.Help_Classes.ReminderTask;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MedicationInfo extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener{

    TextView description;
    TextView dosage;
    TextView frequency;
    TextView startDate;
    TextView endDate;
    FloatingActionButton editMedication;

    TextView txt_medicationCount;
    ImageView btnAddMedicationCount;
    Toast mToast;

    Toolbar toolbar;

    FirebaseUser currentUser;
    //instantiate firebase database
    private FirebaseDatabase database;

    String preceedingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_info);

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

        description = (TextView) findViewById(R.id.txt_description);
        dosage = (TextView) findViewById(R.id.txt_dosage);
        frequency = (TextView) findViewById(R.id.txt_frequency);
        startDate = (TextView) findViewById(R.id.txt_startDate);
        endDate = (TextView) findViewById(R.id.txt_endDate);
        txt_medicationCount = (TextView) findViewById(R.id.txt_medication_count);
        btnAddMedicationCount = (ImageView) findViewById(R.id.btn_addMedicationCount);

        btnAddMedicationCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementMedicationCount();
            }
        });


        editMedication = (FloatingActionButton) findViewById(R.id.fab_edit_medication);

        //get the intent that opened this activity
        final Intent mIntent = getIntent();
        preceedingIntent = mIntent.getStringExtra("intent");

        /**
         * Get the firebase database reference to the currently selected list item
         */
        final DatabaseReference ref = database.getReference()
                .child("users").child(currentUser.getDisplayName())
                .child("medications").child(preceedingIntent);

        //Change the title of the action bar to the medication name
        toolbar.setTitle(preceedingIntent);

        editMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(MedicationInfo.this, Add_Medication.class);
                editIntent.putExtra("intent", preceedingIntent);
                startActivity(editIntent);
            }
        });

        /**
         *add a value event listener to the reference
         * to get the value of each of each child
         */
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get the value of the clicked medication as a Medication object
                Medication medication = dataSnapshot.getValue(Medication.class);

                description.setText(medication != null ? medication.getDescription() : null);
                dosage.setText(medication != null ? medication.getQuantity() : null);
                frequency.setText(medication != null ? medication.getFrequency() : null);
                startDate.setText(medication != null ? medication.getStartDate() : null);
                endDate.setText(medication != null ? medication.getEndDate() : null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /** Set the original values in the UI **/
        updateMedicationCount();

        /** Setup the shared preference listener **/
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    String KEY_MEDICATION_NAME = preceedingIntent;

    /**
     * Updates the TextView to display the new water count from SharedPreferences
     */
    private void updateMedicationCount() {
        int medicationCount = PreferenceUtils.setMedicationCount();
        txt_medicationCount.setText(medicationCount + "");
    }

    /**
     * Adds one to the medication count and shows a toast
     */
    public void incrementMedicationCount() {
        if (mToast != null) mToast.cancel();
        mToast = Toast.makeText(this, "Good Job", Toast.LENGTH_SHORT);
        mToast.show();

        //Create an intent for MedicationReminderIntentService
        Intent incrementMedicationCountIntent = new Intent(this, MedicationReminderIntentService.class);
        // Set the action of the intent to ACTION_INCREMENT_MEDICATION_COUNT
        incrementMedicationCountIntent.setAction(ReminderTask.ACTION_INCREMENT_MEDICATION_COUNT);
        incrementMedicationCountIntent.putExtra("intent", preceedingIntent);
        //Call startService and pass the intent you just created
        startService(incrementMedicationCountIntent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /** Cleanup the shared preference listener **/
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    /**
     * This is a listener that will update the UI when the medication count changes
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (preceedingIntent.equals(key)) {
            updateMedicationCount();
        }
    }
}
