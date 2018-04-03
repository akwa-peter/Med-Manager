package com.example.rad5.med_manager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FloatingActionButton add_medication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //find the navigation view and the drawer layout
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
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

                    default:
                }

                return true;
            }
        });

        //find the add_medication floating action button and set onClick listener on it
        add_medication = (FloatingActionButton) findViewById(R.id.fab_add_medication);
        add_medication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Add_Medication.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.START);
                break;
            default:
                //action
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
