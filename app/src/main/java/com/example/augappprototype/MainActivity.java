package com.example.augappprototype;

/**
 * MainActivity
 * extends AppCompatActivity
 * Resposinble for displaying the caldroid calendar instead of android's calendar view and
 * also responsible for registering all the buttons on the calendar screen
 *
 * Methods:
 * onCreate(Bundle savedInstanceState)
 * convertCalendar()
 *      Display's caldroid's calendar on the calendar screen and sets an on click listener
 *      for the calendar buttons
 * registerListenersForCalendarButtons()
 *      Sets on click listeners for all buttons on the calendar screen
 */

import android.*;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.augappprototype.Listeners.AddEventListener;
import com.example.augappprototype.Listeners.CalendarButtonListener;
import com.example.augappprototype.Listeners.CategoryButtonListener;
import com.example.augappprototype.Listeners.EditEventButtonListener;
import com.example.augappprototype.Listeners.GuestButtonListener;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;
import com.roomorama.caldroid.CaldroidFragment;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    CaldroidFragment caldroidFragment = new CaldroidFragment();

    private static final String[] SCOPES = {CalendarScopes.CALENDAR};
    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    GoogleSignInAPI gsia;
    public static List<Event> items;

    TextView mOutputText;
    /*--Methods--*/

    /**
     * onCreate(Bundle) --> void
     * Calls the convertCalendar and registerListenersForButtons methods so that there is a new on
     * click listener for them on creation
     * on creation
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras= getIntent().getExtras();

        convertCalendar();
        registerListenersForCalendarUIButtons();
        goToMainMenu();

        EasyPermissions.requestPermissions(
                this,
                "This app needs to access your Google account (via Contacts).",
                REQUEST_PERMISSION_GET_ACCOUNTS,
                android.Manifest.permission.GET_ACCOUNTS);
        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(),
                Collections.singleton("https://www.googleapis.com/auth/calendar"));
        credential.setSelectedAccountName(extras.getString("userName"));
        mOutputText = findViewById(R.id.testText);
        new MakeRequestTask(credential).execute();
        new addAnEvent(credential).execute(); //this would go in the onclick listener at some point




    }//onCreate

    /**
     * convertCalendar() --> void
     * Makes android's calendar view the caldroid calendar
     * Sets the minimum date to January 1st 2018 and the maximum date to December 31st 2018
     */
    private void convertCalendar() {
        CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.caldroidCalendar, caldroidFragment);
        t.commit();
        Date jan1 = new Date(1514790000000L);
        Date dec31 = new Date(1546300800000L);
        caldroidFragment.setMinDate(jan1);
        caldroidFragment.setMaxDate(dec31);
        caldroidFragment.setCaldroidListener(new CalendarButtonListener(this));
    }//convertCalendar

    public void goToMainMenu() {
        ImageButton back = findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMainMenu = new Intent(MainActivity.this, MainMenu.class);
                startActivity(goToMainMenu);

            }
        });
    }

    /**
     * registerListenersForCalendarUIButtons() --> void
     * Sets on click listeners for the add event button, edit event button and the category button
     * on the calendar UI screen
     */
    public void registerListenersForCalendarUIButtons() {
        findViewById(R.id.addEventButton).setOnClickListener(new AddEventListener(this));
        findViewById(R.id.editEventButton).setOnClickListener
                (new EditEventButtonListener(this));
        findViewById(R.id.categoryButton).setOnClickListener
                (new CategoryButtonListener(this));
    }//registerListenersForButtons


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
                    .setApplicationName("AugAppPrototype2")
                    .build();
        }

        /**
         * Background task to call Google Calendar API.
         *
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                return getDataFromApi();
            } catch (final Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }


        /**
         * Fetch a list of the next 10 events from the primary calendar.
         *
         * @return List of Strings describing returned events.
         * @throws IOException
         */
        private List<String> getDataFromApi() throws IOException {
            // List the next 10 events from the primary calendar.
            DateTime now = new DateTime(System.currentTimeMillis());
            List<String> eventStrings = new ArrayList<String>();
            Events events = mService.events().list("csc320augapp@gmail.com")
                    .setMaxResults(10)
                    .setOrderBy("startTime")
                    .setTimeMin(new DateTime("2016-04-17T17:10:00+06:00"))
                    .setSingleEvents(true)
                    .execute();
            items = events.getItems();


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
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(List<String> output) {
            mOutputText.setText("Grabbed " + output.size() + " things");
          //  Toast.makeText(MainActivity.this, output.size(), Toast.LENGTH_LONG).show();

        }

        @Override
        protected void onCancelled() {
//            mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    // showGooglePlayServicesAvailabilityErrorDialog(
                    //((GooglePlayServicesAvailabilityIOException) mLastError)
                    // .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            MainActivity.REQUEST_AUTHORIZATION);
                } else {
                    Toast.makeText(MainActivity.this, "The following error occurred:\n"
                            + mLastError.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else {
                //               mOutputText.setText("Request cancelled.");
            }
        }
    }//makeRequests

    private class addAnEvent extends AsyncTask {
        private com.google.api.services.calendar.Calendar mService2 = null;
        private Exception mLastError = null;

        addAnEvent(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService2 = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("AugAppPrototype2")
                    .build();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            createEvent();
            return null;
        }

        /**
         *
         *
         */
    public void createEvent() {

        Event event = new Event()
                .setSummary("popcorn")
                .setLocation("alligator")
                .setDescription("New Event 1");

        DateTime startDateTime = new DateTime("2017-04-17T18:10:00+06:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime);
        event.setStart(start);

        DateTime endDateTime = new DateTime("2017-04-17T18:40:00+06:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime);
        event.setEnd(end);

        String calendarId = "csc320augapp@gmail.com";
        try {
            mService2.events().insert(calendarId, event).execute();
        } catch (IOException e) {
            //Toast.makeText(MainActivity.this, "There was an Error pushing the event", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

        /**
         *
         */
        @Override
        protected void onPreExecute(){
            Toast.makeText(MainActivity.this, "HELLO", Toast.LENGTH_LONG).show();
        }
    }

    public void setEventCount(Date day){
        switch (AddEventListener.allEvents.get(day).size()){
            case(1):
                Drawable count1 = getResources().getDrawable(R.drawable.count1);
                caldroidFragment.setBackgroundDrawableForDate(count1, day);
                break;
            case(2):
                Drawable count2 = getResources().getDrawable(R.drawable.count2);
                caldroidFragment.setBackgroundDrawableForDate(count2, day);
                break;
            case(3):
                Drawable count3 = getResources().getDrawable(R.drawable.count3);
                caldroidFragment.setBackgroundDrawableForDate(count3, day);
                break;
            case(4):
                Drawable count4 = getResources().getDrawable(R.drawable.count4);
                caldroidFragment.setBackgroundDrawableForDate(count4, day);
                break;
            case(5):
                Drawable count5 = getResources().getDrawable(R.drawable.count5);
                caldroidFragment.setBackgroundDrawableForDate(count5, day);
                break;
            case(6):
                Drawable count6 = getResources().getDrawable(R.drawable.count6);
                caldroidFragment.setBackgroundDrawableForDate(count6, day);
                break;
            case(7):
                Drawable count7 = getResources().getDrawable(R.drawable.count7);
                caldroidFragment.setBackgroundDrawableForDate(count7, day);
                break;
            case(8):
                Drawable count8 = getResources().getDrawable(R.drawable.count8);
                caldroidFragment.setBackgroundDrawableForDate(count8, day);
                break;
            case(9):
                Drawable count9 = getResources().getDrawable(R.drawable.count9);
                caldroidFragment.setBackgroundDrawableForDate(count9, day);
                break;
            case(10):
                Drawable count10 = getResources().getDrawable(R.drawable.count10);
                caldroidFragment.setBackgroundDrawableForDate(count10, day);
                break;

        }
        caldroidFragment.refreshView();
    }

    public void updateCalendar(){
        for (Date daysWithEvents : AddEventListener.allEvents.keySet()){
            setEventCount(daysWithEvents);
        }
    }
}//MainActivity