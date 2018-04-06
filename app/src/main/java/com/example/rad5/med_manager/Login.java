package com.example.rad5.med_manager;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rad5.med_manager.Help_Classes.User;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends AppCompatActivity {

    View view;

    //check which button was clicked
    int clickedButton;

    //debugging tag
    private static String TAG = "debugger";

    //create an instance of sign in textview object
    TextView continueWithGoogle;

    //create an instance of sign up textview object
    TextView sign_up;

    //successful sign in value
    private static final int RC_SIGN_IN = 100;

    //create a google sign in client to fetch the informations required
    GoogleSignInClient mGoogleSignInClient;

    FirebaseUser currentUser;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //default view for snack bar
        view = (View) findViewById(R.id.health_care_logo);

        // get firebase database instance
        database = FirebaseDatabase.getInstance();

        //check if user is logged in
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Choose authentication providers
        final List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

        //find the log in with google button and set an onClick listener to trigger google sign in
        continueWithGoogle = (TextView) findViewById(R.id.txt_continue_with_google);
        continueWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //assign the sign in id to the clicked button
                clickedButton = continueWithGoogle.getId();

                // Create and launch sign-in intent
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .setLogo(R.drawable.health_care_logo) // Set logo drawable
                                .setTheme(R.style.AppTheme)
                                .build(),
                        RC_SIGN_IN);
            }
        });

        //initialize the sign up text view and set an onClic listener to sign up a new user
        sign_up = (TextView) findViewById(R.id.sign_up);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //assign the sign up id to the clicked button
                clickedButton = sign_up.getId();

                // Create and launch sign-in intent
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .setLogo(R.drawable.health_care_logo) // Set logo drawable
                                .setTheme(R.style.AppTheme)
                                .build(),
                        RC_SIGN_IN);
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);

            // Successfully signed in
            if (resultCode == RESULT_OK) {

                progressBar.setVisibility(View.VISIBLE);

                //get the current signed in user
                currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String userId = currentUser.getUid();
                String name = currentUser.getDisplayName();
                String email = currentUser.getEmail();
                String imgUrl = currentUser.getPhotoUrl().toString();

                if (clickedButton == sign_up.getId()){
                    //Write a message to the database
//                    DatabaseReference myRef = database.getReference("users");
//                    myRef.setValue("Hello, World!");
                    writeNewUser(name, email, imgUrl);

                }

                startActivity(new Intent(Login.this, MainActivity.class));
                finish();
            }
            else {
                // Sign in failed, check response for error code
                if (response == null) {
                    Snackbar.make(view, "Login Canceled", Snackbar.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Snackbar.make(view, "Login Failed", Snackbar.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
            }
        }
    }

    /**
     * create a new user
     * @param name the name of the user
     * @param email the user email
     * @param imgUrl the user image url
     */
    private void writeNewUser(String name, String email, String imgUrl) {

        //get reference to the users in the database
        DatabaseReference myRef = database.getReference("users");

        //create a new user object
        User user = new User(email, imgUrl);
        Map<String, Object> userValues = user.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(name, userValues);

        myRef.updateChildren(childUpdates);
    }
}
