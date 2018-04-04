package com.example.rad5.med_manager;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class Login extends AppCompatActivity {

    View view;

    //debugging tag
    private static String TAG = "debugger";

    //create an instatnce of a textview object
    TextView continueWithGoogle;

    //successful sign in value
    private static final int RC_SIGN_IN = 100;

    //create a google sign in client to fetch the informations required
    GoogleSignInClient mGoogleSignInClient;

    FirebaseUser currentUser;
    String userEmail;
    String userId;
    String displayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //default view for snack bar
        view = (View) findViewById(R.id.health_care_logo);

        //check if user is logged in
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Choose authentication providers
        final List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

        //find the log in with google button and set an onClick listener
        continueWithGoogle = (TextView) findViewById(R.id.txt_continue_with_google);
        continueWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.VISIBLE);

            // Successfully signed in
            if (resultCode == RESULT_OK) {

                currentUser = FirebaseAuth.getInstance().getCurrentUser();

                userEmail = currentUser.getEmail();
                userId = currentUser.getUid();
                displayName = currentUser.getDisplayName();

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
}
