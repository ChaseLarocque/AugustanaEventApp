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

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import com.example.augappprototype.Listeners.AddEventListener;
import com.example.augappprototype.Listeners.CalendarButtonListener;
import com.example.augappprototype.Listeners.CategoryButtonListener;
import com.example.augappprototype.Listeners.EditEventButtonListener;
import com.example.augappprototype.Listeners.GuestButtonListener;
import com.roomorama.caldroid.CaldroidFragment;


import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    boolean isGuest = GuestButtonListener.isGuest;


    /*--Methods--*/
    /**
     * onCreate(Bundle) --> void
     * Calls the convertCalendar and registerListenersForButtons methods so that there is a new on
     * click listener for them on creation
     * on creation
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        convertCalendar();
        registerListenersForCalendarUIButtons();
        goToMainMenu();
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

    public void goToMainMenu(){
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
}//MainActivity