package com.example.augappprototype;

/**
 * LoginScreen
 * extends AppCompatActivity
 * Is responsible for the Login Screen and all the buttons within the login screen
 * <p>
 * Methods:
 * onCreate(Bundle savedInstanceState)
 * Sets the content view as the login screen and calls the method that registers the listeners
 * for each button
 * registerListenersForLoginButtons()
 * Sets on click listeners for every button on the login screen
 */

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.augappprototype.Listeners.GuestButtonListener;
import com.example.augappprototype.Listeners.LoginButtonListener;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private static final int REQUEST_CODE = 9001;

    /*--Methods--*/

    /**
     * onCreate(Bundle) --> void
     * Calls the registerListenersForLoginScreenButtons method so there is a new on click listener
     * for them on creation
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        registerListenersForLoginScreenButtons();

        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions
                        .DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,
                this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
    }//onCreate

    /**
     * registerListenersForLoginScreenButtons() --> void
     * Sets on click listeners for the Login button and the Guest Login Button on the Login screen
     */
    public void registerListenersForLoginScreenButtons() {
        findViewById(R.id.loginButton).setOnClickListener(new LoginButtonListener(this));
        findViewById(R.id.continueAsGuestButton).setOnClickListener
                (new GuestButtonListener(this));
    }//registerListenersForLoginScreenButtons

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.googleLoginButton:
                signIn();
                break;
        } // switch(View)
    } // onClick(View)

    /**
     *
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    } // onConnectionFailed(ConnectionResult)

    private void signIn() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQUEST_CODE);
    } // signIn()

    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient)
                .setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                //updateUI(false);
            } // onResult(Status)
        });
    } // signOut()

}//LoginScreen
