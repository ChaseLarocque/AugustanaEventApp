package com.example.augappprototype;

/**
 * LoginScreen
 * extends AppCompatActivity
 * Is responsible for the Login Screen and all the buttons within the login screen
 * <p>
 * Methods:
 * onCreate(Bundle savedInstanceState)
 *      Sets the content view as the login screen and calls the method that registers the listeners
 *      for each button
 * signIn()
 *      Handles Signing in
 * signOut()
 *      Handles Signing out
 * handleSignInResult(GoogleSignInResult)
 *      Uses the account information to set new fields
 * onActivityResult(int, int, Intent)
 *      This is where the the login is validated
 * addName()
 *      Creates our local whitelist
 * checkPermission(String)
 *      Sets who is able to edit the calendar and calls the intent to go to the next screen.
\ */

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {

    public boolean isAbleToEditCalendar;
    private GoogleApiClient googleApiClient;
    private SignInButton signInButton;
    private static final int REQUEST_CODE = 9001;
    private ArrayList<String> whiteList = new ArrayList<String>();
    public GoogleSignInAccount account;
    GoogleSignInOptions signInOptions;


    /**
     * onCreate(Bundle) --> void
     * Creates new singINOptions and googleApiClient
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isAbleToEditCalendar = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        addName();
        signInButton = (SignInButton) findViewById(R.id.login_button);
        signInButton.setOnClickListener(this);

        signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,
                this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
    }//onCreate

    /**
     * onClick for the login button
     * @param v - View
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                signIn();
                break;
        } // switch(View)
    } // onClick(View)

    /**
     * onConnectionFailed(ConnectionResult)
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    } // onConnectionFailed(ConnectionResult)

    /**
     * signIn()
     * Handles the sign in events when the login button is pressed
     */
    private void signIn() {
        signOut(); //signOut is called right after so user can change accounts

        Intent intent = Auth.GoogleSignInApi
                .getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQUEST_CODE);
    } // signIn()

    /**
     * signOut()
     * Signs out of logged in account
     */
    private void signOut() {
        Auth.GoogleSignInApi
                .signOut(googleApiClient)
                .setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
            }//onResult
        });
    } // signOut()

    /**
     * handleSignInResult(GoogleSignInResult)
     * @param result - result of GoogleSignIn
     * Method handles the results of if the user was able to login
     */
    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            account = result.getSignInAccount();
            String userName = account.getDisplayName();
            String userEmail = account.getEmail();
            checkPermissions(userEmail);
        } else {
        } // else
    } // handleSignInResult(GoogleSignInResult)

    /**
     * onActivityResult (int, int, Intent)
     * @param requestCode - requestCode
     * @param resultCode - resultCode
     * @param data - Intent data
     * Handles the result of the GoogleSignInResult
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } // if
    } // onActivityResult(int, int, Intent)

    /**
     * addNames()
     * Adds names to our local whitelist
     */
    public void addName(){
        whiteList.add("shichun1@ualberta.ca");
        whiteList.add("vpreyes@ualberta.ca");
        whiteList.add("cwlarocq@ualberta.ca");
        whiteList.add("frithsmi@ualberta.ca");
        whiteList.add("tlalani@ualberta.ca");
    } // addName()

    /**
     * checkPermission(String)
     * @param email
     * Uses our whiteList to set who is able to edit the calendar.
     * **Eventually this should be handled by the Google Calendar API
     */
    public void checkPermissions(String email){
        if (whiteList.contains(email)) {
            isAbleToEditCalendar = true;
        }//if
        Intent goToMenu = new Intent(this, MainMenu.class);
        this.startActivity(goToMenu);
        goToMenu.putExtra("com.example.augappprototype.userName", account.getEmail());
        goToMenu.putExtra("canEditCalendar", isAbleToEditCalendar);
        startActivity(goToMenu);
    }//checkPermissions

}//LoginScreen