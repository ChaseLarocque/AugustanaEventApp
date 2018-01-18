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



import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.example.augappprototype.Listeners.GuestButtonListener;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

import pub.devrel.easypermissions.EasyPermissions;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {

    GoogleSignInAccount account;

    TextView mOutputText;
    private Button signOutButton;
    GoogleSignInOptions gso;
    private GoogleApiClient googleApiClient;
    private ImageView profilePicture;
    private LinearLayout profileSection;
    private SignInButton signInButton;
    private TextView email;
    private TextView name;
    private static final int REQUEST_CODE = 9001;
    ArrayList<String> whiteList = new ArrayList<String>();

    GoogleSignInAPI gsi;

    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;


    Context appContext = this;

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
        addName();

        signInButton = (SignInButton) findViewById(R.id.login_button);
        signOutButton = findViewById(R.id.test);
        signInButton.setOnClickListener(this);
        signOutButton.setOnClickListener(this);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope("https://www.googleapis.com/auth/calendar"))
                .build();
        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        googleApiClient.connect();


    }//onCreate

    /**
     * registerListenersForLoginScreenButtons() --> void
     * Sets on click listeners for the Login button and the Guest Login Button on the Login screen
     */



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                signIn();
                break;
            case R.id.test:
                signOut();
                break;
        } // switch(View)
    } // onClick(View)

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    } // onConnectionFailed(ConnectionResult)

    private void signIn() {

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope("https://www.googleapis.com/auth/calendar"))
                .build();
        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        googleApiClient.connect();
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, 9001);

    } // signIn()

    public void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
              //  googleApiClient.disconnect();
            }
        });
    } // signOut()

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    android.Manifest.permission.GET_ACCOUNTS);

            account = result.getSignInAccount();
            String userName = account.getDisplayName();
            String userEmail = account.getEmail();
            //name.setText(userName);
            //email.setText(userEmail);
            checkPermissions(userEmail);
            //Glide.with(this).load(account.getPhotoUrl()).into(profilePicture);
            //updateUI(true);
            Toast.makeText(this, account.getEmail(),Toast.LENGTH_LONG).show();
        } else {
            updateUI(false);
        } // else
    } // handleSignInResult(GoogleSignInResult)

    private void updateUI(boolean isLogin) {
        if(isLogin) {
            profileSection.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.GONE);
        } else {
            profileSection.setVisibility(View.GONE);
            signInButton.setVisibility(View.VISIBLE);
        } // else
    } // updateUI

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
            Toast.makeText(this, "faculty",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "student",
                    Toast.LENGTH_LONG).show();
        } // else
        Intent goToMenu = new Intent(this, MainMenu.class);

        goToMenu.putExtra("com.example.augappprototype.userName", account.getEmail());
        startActivity(goToMenu);
    }

}//LoginScreen