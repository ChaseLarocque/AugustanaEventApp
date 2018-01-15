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

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.augappprototype.Listeners.GuestButtonListener;
import com.example.augappprototype.Listeners.LoginButtonListener;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import com.google.api.services.calendar.CalendarScopes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {

    private Button signOutButton;
    private GoogleApiClient googleApiClient;
    private ImageView profilePicture;
    private LinearLayout profileSection;
    private SignInButton signInButton;
    private TextView email;
    private TextView name;
    private static final int REQUEST_CODE = 9001;
    GoogleAccountCredential mCredential;
    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    private static final String[] SCOPES = { CalendarScopes.CALENDAR };
    TextView mOutputText;

    HashMap<String, String> permissions = new HashMap<>();

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
        registerListenersForLoginScreenButtons();

        profileSection = (LinearLayout) findViewById(R.id.profile_section);
        signOutButton = (Button) findViewById(R.id.logout_button);
        signInButton = (SignInButton) findViewById(R.id.login_button);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        profilePicture = (ImageView) findViewById(R.id.profile_picture);

        signInButton.setOnClickListener(this);
        signOutButton.setOnClickListener(this);
        profileSection.setVisibility(View.GONE);
        mOutputText = findViewById(R.id.testText2);


        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
        new LoginScreen.MakeRequestTask(mCredential).execute();

        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                signIn();
                break;
            case R.id.logout_button:
                signOut();
                break;
        } // switch(View)
    } // onClick(View)

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    } // onConnectionFailed(ConnectionResult)

    private void signIn() {
        startActivityForResult(
                mCredential.newChooseAccountIntent(),
                REQUEST_ACCOUNT_PICKER);
        checkPermissions(mCredential.getSelectedAccountName());
       // Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        //startActivityForResult(intent, REQUEST_CODE);
    } // signIn()

    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });
    } // signOut()

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            String userName = account.getDisplayName();
            String userEmail = account.getEmail();
            name.setText(userName);
            email.setText(userEmail);
            checkPermissions(userEmail);
            Glide.with(this).load(account.getPhotoUrl()).into(profilePicture);
            updateUI(true);
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
        permissions.put("shichun1@ualberta.ca", "faculty");
        permissions.put("vpreyes@ualberta.ca", "faculty");
        permissions.put("cwlarocq@ualberta.ca", "faculty");
        permissions.put("frithsmi@ualberta.ca", "faculty");
    }//addName

    public void checkPermissions(String email){
        if (permissions.containsKey(email)) {
                Toast.makeText(this, "faculty",
                        Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "student",
                    Toast.LENGTH_LONG).show();
        }
        Intent goToMenu = new Intent(this, MainMenu.class);
        this.startActivity(goToMenu);
    } // checkPermissions(String)


    //***************

    /**
     * An asynchronous task that handles the Google Calendar API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
        private com.google.api.services.calendar.Calendar mService = null;
        private Exception mLastError = null;

        MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
            if (mCredential.getSelectedAccount()==null)
                mOutputText.setText("null");
            else
                mOutputText.setText("not null");

        }

        /**
         * Background task to call Google Calendar API.
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                return getDataFromApi();

            } catch (Exception e) {

                mLastError = e;
                cancel(true);
                return null;
            }
        }

        /**
         * Fetch a list of the next 10 events from the primary calendar.
         * @return List of Strings describing returned events.
         * @throws IOException
         */
        private List<String> getDataFromApi() throws IOException {
            // List the next 10 events from the primary calendar.
            DateTime now = new DateTime(System.currentTimeMillis());
            List<String> eventStrings = new ArrayList<String>();

            Events events = mService.events().list("csc320augapp@gmail.com")
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();


            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    // All-day events don't have start times, so just use
                    // the start date.
                    start = event.getStart().getDate();
                }
                eventStrings.add(
                        String.format("%s (%s)", event.getSummary(), start));
            }
            return eventStrings;
        }



        @Override
        //List<String>.: The result of the operation computed by doInBackground(Params...).
        //(which would end up being eventStrings.
        protected void onPostExecute(List<String> output) {
            if (output == null || output.size() == 0) {
                mOutputText.setText("No results returned.");
            } else {

                output.add(0, "Data retrieved using the Google Calendar API:");
                mOutputText.setText(TextUtils.join("\n", output));
            }
        }

        @Override
        protected void onCancelled() {

            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            MainActivity.REQUEST_AUTHORIZATION);
                } else {

                }
            } else {

            }
        }
        /**
         * Display an error dialog showing that Google Play Services is missing
         * or out of date.
         * @param connectionStatusCode code describing the presence (or lack of)
         *     Google Play Services on this device.
         */
        void showGooglePlayServicesAvailabilityErrorDialog(
                final int connectionStatusCode) {
            GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
            Dialog dialog = apiAvailability.getErrorDialog(
                    LoginScreen.this,
                    connectionStatusCode,
                    REQUEST_GOOGLE_PLAY_SERVICES);
            dialog.show();
        }
    }
}//LoginScreen
