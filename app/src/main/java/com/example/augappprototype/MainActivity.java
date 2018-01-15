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


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.augappprototype.Listeners.AddEventListener;
import com.example.augappprototype.Listeners.BackButtonListener;
import com.example.augappprototype.Listeners.CalendarButtonListener;
import com.example.augappprototype.Listeners.CategoryButtonListener;
import com.example.augappprototype.Listeners.EditEventButtonListener;
import com.roomorama.caldroid.CaldroidFragment;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    CaldroidFragment caldroidFragment = new CaldroidFragment();


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
        updateCalendar();
        registerListenersForCalendarUIButtons();
    }//onCreate

    /**
     * convertCalendar() --> void
     * Makes android's calendar view the caldroid calendar
     * Sets the minimum date to January 1st 2018 and the maximum date to December 31st 2018
     */
    private void convertCalendar() {
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
        findViewById(R.id.backbutton).setOnClickListener((new BackButtonListener(this)));
    }//registerListenersForButtons

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