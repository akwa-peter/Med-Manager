package com.example.rad5.med_manager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rad5.med_manager.Help_Classes.DatabaseUtil;
import com.example.rad5.med_manager.Help_Classes.Medication;
import com.example.rad5.med_manager.Help_Classes.NotificationPublisher;
import com.example.rad5.med_manager.Help_Classes.RecyclerAdapter;
import com.example.rad5.med_manager.Help_Classes.toTitleCase;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //debugging tag
    private static String TAG = "debugger";

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FloatingActionButton add_medication;

    FirebaseUser currentUser;

    private FirebaseDatabase database;
    DatabaseReference ref;
    RecyclerView list;
    ArrayList<Medication> medications;
    RecyclerAdapter adapter;
    Medication medication;
    private static MainActivity inst;

    toTitleCase titleCase = new toTitleCase();

    public static MainActivity instance() {
        return inst;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = DatabaseUtil.getDatabase();

        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

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

        list = (RecyclerView) findViewById(R.id.my_list);
        // Set the layout manager
        list.setLayoutManager(new LinearLayoutManager(this));

        medications = new ArrayList<Medication>();
        medication = new Medication();

        adapter = new RecyclerAdapter(this, medications);

        //get the firebase database reference
        ref = database.getReference()
                .child("users").child(currentUser.getDisplayName()).child("medications");

        Query query = ref.orderByChild("zMedicationMonth");

        //add a value event listener to the database reference
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                medications.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    medication = ds.getValue(Medication.class);
                    medications.add(medication);

                }

                list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadMedications : onCancelled", databaseError.toException());
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
                Intent intent = new Intent(MainActivity.this, Add_Medication.class);
                intent.putExtra("intent", "btn_add_medication");
                startActivity(intent);
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

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(MainActivity.this.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String search) {
                return false;
            }

            Query filterQuery;

            @Override
            public boolean onQueryTextChange(final String search) {

                //Convert the string to a sentence case
                final String text = titleCase.toTitle(search);

                //if the length of the string is equal to 0 then order by month
                if (text.length() == 0){
                    filterQuery = ref.orderByChild("zMedicationMonth");
                }
                else {//else order by name and start at queried text
                    filterQuery = ref.orderByChild("name").startAt(text);
                }

                //add value event listener to the query
                filterQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        medications.clear();

                        //for each data in our datasnapshot,
                        for (DataSnapshot ds : dataSnapshot.getChildren()){

                            //get the value as a medication object
                            medication = ds.getValue(Medication.class);
                            //if the name of the medication contains the text constrain
                            if (medication.getName().contains(text)) {
                                //add the medication to our list of medications
                                medications.add(medication);
                            }

                        }

                        //bind the adapter to our listView
                        list.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

//                adapter.getFilter().filter(search);
//                list.setAdapter(adapter);
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });

        return true;
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
                /**
                 * Set the logout progress visible
                 */
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.mProgress_bar);
                TextView progressBar_text = (TextView) findViewById(R.id.progressBar_text);
                progressBar.setVisibility(View.VISIBLE);
                progressBar_text.setVisibility(View.VISIBLE);
                signOut();
                break;

            case R.id.action_search:
                onSearchRequested();
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
