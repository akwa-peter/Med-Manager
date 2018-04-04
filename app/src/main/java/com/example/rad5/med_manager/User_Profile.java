package com.example.rad5.med_manager;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_Profile extends AppCompatActivity {

    FirebaseUser currentUser;

    Toolbar toolbar;
    TextView userEmail;
    TextView displayName;
    TextView phoneNumber;
    TextView address;
    TextView nickName;
    CircleImageView profileImage;
    ImageView editProfile;
    FloatingActionButton done_editing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);

        //get the instance of the current signed in user
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        //Add Action bar to the user profile activity
        toolbar = (Toolbar) findViewById(R.id.mainActivity_toolbar);
        setSupportActionBar(toolbar);

        // Add back icon to the Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        /**initialize the text view object of the user email
         *and set the value from the current user
         */
        userEmail = (TextView) findViewById(R.id.txt_email);
        userEmail.setText(currentUser.getEmail());

        /**initialize the text view object of the display name
         *and set the value from the current user
         */
        displayName = (TextView) findViewById(R.id.txt_name);
        displayName.setText(currentUser.getDisplayName());

        /**initialize the Image view object of the user photo
         *and set the value from the current user
         */
        profileImage = (CircleImageView) findViewById(R.id.user_image);
        Picasso.with(User_Profile.this).load(currentUser.getPhotoUrl()).into(profileImage);

        /**initialize the text views object of phone number,
         * address and nickname
         */
        phoneNumber = (TextView) findViewById(R.id.edt_phone);
        address = (TextView) findViewById(R.id.edt_address);
        nickName = (TextView) findViewById(R.id.edt_nickName);

        /**
         * initialize the object of done editing floating action button
         */
        done_editing = (FloatingActionButton) findViewById(R.id.edtProfile_done);

        //find the edit profile image button and set an onclick listener to enable editing
        editProfile = (ImageView) findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done_editing.setVisibility(View.VISIBLE);
            }
        });

        /**Check if the done editing floating action button is visible
         * and set the focus of the text views on the user profile
          */
        if (done_editing.isShown()){
            phoneNumber.requestFocus();;
            address.requestFocus();
            nickName.requestFocus();
        }
        else {
            phoneNumber.setFocusable(false);
            address.setFocusable(false);
            nickName.setFocusable(false);
        }
    }

    /**
     * On navigating to the parent activity, destroy the user profile activity
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
