package com.example.rad5.med_manager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rad5.med_manager.Help_Classes.Medication;
import com.example.rad5.med_manager.Help_Classes.MedicationAdapter;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //debugging tag
    private static String TAG = "debugger";

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FloatingActionButton add_medication;

    FirebaseUser currentUser;

    DatabaseReference ref;
    ListView listView;
    ArrayList<Medication> medications;
    MedicationAdapter adapter;
    Medication medication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        //Toast.makeText(MainActivity.this, " "+currentUser.getDisplayName() + " "+currentUser.getEmail(), Toast.LENGTH_LONG).show();

        //default view for snack bar
        final View view = (View) findViewById(R.id.drawer_layout);

        //Add Action bar to the main activity
        toolbar = (Toolbar) findViewById(R.id.mainActivity_toolbar);
        setSupportActionBar(toolbar);

        // Add menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        listView = (ListView) findViewById(R.id.my_list);
        medications = new ArrayList<Medication>();
        medication = new Medication();

        adapter = new MedicationAdapter(this, medications);

        //get the firebase database reference
        ref = FirebaseDatabase.getInstance().getReference()
                .child("users").child(currentUser.getDisplayName()).child("medications");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                medications.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    medication = ds.getValue(Medication.class);
                    medications.add(medication);

                }

                Log.d("debugger", "medication item = " + medication.getDescription());
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //find the navigation view and the drawer layout
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        /**
         * set action for when any of the drawer navigation
         * menu item is clicked or selected
         */
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.about:
                        drawerLayout.closeDrawers();
                        Snackbar.make(view, "About Clicked", Snackbar.LENGTH_LONG).show();
                        break;

                    case R.id.contact:
                        drawerLayout.closeDrawers();
                        Snackbar.make(view, "Contact Clicked", Snackbar.LENGTH_LONG).show();
                        break;

                    case R.id.share:
                        drawerLayout.closeDrawers();
                        Snackbar.make(view, "Share Clicked", Snackbar.LENGTH_LONG).show();
                        break;

                    case R.id.help:
                        drawerLayout.closeDrawers();
                        Snackbar.make(view, "Help Clicked", Snackbar.LENGTH_LONG).show();
                        break;

                    case R.id.profile:
                        drawerLayout.closeDrawers();
                        startActivity(new Intent(MainActivity.this, User_Profile.class));
                        break;

                    default:
                }

                return true;
            }
        });

        /**
         * find the add_medication floating action button and set onClick listener on it
         * to open the edit medication activity
         */
        add_medication = (FloatingActionButton) findViewById(R.id.fab_add_medication);
        add_medication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Add_Medication.class));
            }
        });

    }

    /**
     * Inflate the action_bar_menu on the main activity
     * @param menu the menu resource file to be inflated
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Set action on when any of the action bar item is selected
     * @param item action bar item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.START);
                break;

            case R.id.sign_out:
                signOut();
                break;

            default:
                //action
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Sign out the current user
     */
    private void signOut()  {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent signOut = new Intent(MainActivity.this, Login.class);
                        startActivity(signOut);
                        finish();
                        Toast.makeText(getApplicationContext(), "Logged out successfully "  , Toast.LENGTH_LONG).show();
                    }
                });

    }
}
