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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    private Button signOutButton;
    private GoogleApiClient googleApiClient;
    public ImageView profilePicture;
    private LinearLayout profileSection;
    private SignInButton signInButton;
    private TextView email;
    private TextView name;
    private static final int REQUEST_CODE = 9001;
    private ArrayList<String> whiteList = new ArrayList<String>();
    public GoogleSignInAccount account;
    GoogleSignInOptions signInOptions;

    /*--Methods--*/

    /**
     * onCreate(Bundle) --> void
     * Calls the registerListenersForLoginScreenButtons method so there is a new on click listener
     * for them on creation
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                signIn();
                break;
        } // switch(View)
    } // onClick(View)

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    } // onConnectionFailed(ConnectionResult)

    private void signIn() {
        signOut();
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQUEST_CODE);
    } // signIn()

    private void signOut() {
        Auth.GoogleSignInApi
                .signOut(googleApiClient)
                .setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {

            }
        });
    } // signOut()

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            account = result.getSignInAccount();
            String userName = account.getDisplayName();
            String userEmail = account.getEmail();
           // setProfilePicture();


            checkPermissions(userEmail);//figure out how to call this again
        } else {

        } // else
    } // handleSignInResult(GoogleSignInResult)



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } // if
    } // onActivityResult(int, int, Intent)

    public void addName(){
        whiteList.add("shichun1@ualberta.ca");
        whiteList.add("vpreyes@ualberta.ca");
        whiteList.add("cwlarocq@ualberta.ca");
        whiteList.add("frithsmi@ualberta.ca");
    } // addName()

    public void checkPermissions(String email){
        if (whiteList.contains(email)) {
            isAbleToEditCalendar = true;
        }//if
        Intent goToMenu = new Intent(this, MainMenu.class);
        this.startActivity(goToMenu);
        goToMenu.putExtra("com.example.augappprototype.userName", account.getEmail());
        goToMenu.putExtra("canEditCalendar", isAbleToEditCalendar);
        startActivity(goToMenu);
    }

}//LoginScreen