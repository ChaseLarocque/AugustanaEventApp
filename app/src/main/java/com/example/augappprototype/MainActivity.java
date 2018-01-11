package com.example.augappprototype;

/**
 * MainActivity
 * extends AppCompatActivity
 * Responsible for displaying the caldroid calendar instead of android's calendar view and
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

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;

import com.example.augappprototype.Listeners.AddEventListener;
import com.example.augappprototype.Listeners.CalendarButtonListener;
import com.example.augappprototype.Listeners.CategoryButtonListener;
import com.example.augappprototype.Listeners.EditEventButtonListener;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    /*--Data--*/
    private SharedPreferences sharedPreferences;
    private static final String ATHLETICS_CATEGORY_CHECKED_TAG = "athleticsCategoryChecked";
    private static final String PERFORMANCE_CATEGORY_CHECKED_TAG = "performanceCategoryChecked";
    private static final String CLUB_CATEGORY_CHECKED_TAG = "clubCategoryChecked";
    private static final String RESEARCH_CATEGORY_CHECKED_TAG = "researchCategoryChecked";
    private static final String ASA_CATEGORY_CHECKED_TAG = "asaCategoryChecked";
    private static final String athleticsKey = "athletics_key";
    private static final String performanceKey = "performance_key";
    private static final String clubKey = "club_key";
    private static final String researchKey = "research_key";
    private static final String asaKey = "asa_key";
    public boolean athleticsCategoryChecked = true;
    public boolean performanceCategoryChecked = true;
    public boolean clubCategoryChecked = true;
    public boolean researchCategoryChecked = true;
    public boolean asaCategoryChecked = true;


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

    public void userSettings() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ATHLETICS_CATEGORY_CHECKED_TAG, athleticsCategoryChecked);
        editor.putBoolean(PERFORMANCE_CATEGORY_CHECKED_TAG, performanceCategoryChecked);
        editor.putBoolean(CLUB_CATEGORY_CHECKED_TAG, clubCategoryChecked);
        editor.putBoolean(RESEARCH_CATEGORY_CHECKED_TAG, researchCategoryChecked);
        editor.putBoolean(ASA_CATEGORY_CHECKED_TAG, asaCategoryChecked);
        editor.commit();
        athleticsCategoryChecked = sharedPreferences.getBoolean(ATHLETICS_CATEGORY_CHECKED_TAG, true);
        performanceCategoryChecked = sharedPreferences.getBoolean(PERFORMANCE_CATEGORY_CHECKED_TAG, true);
        clubCategoryChecked = sharedPreferences.getBoolean(CLUB_CATEGORY_CHECKED_TAG, true);
        researchCategoryChecked = sharedPreferences.getBoolean(RESEARCH_CATEGORY_CHECKED_TAG, true);
        asaCategoryChecked = sharedPreferences.getBoolean(ASA_CATEGORY_CHECKED_TAG, true);
        CheckBox cbAthletics = (CheckBox) findViewById(R.id.athleticsCategory);
        CheckBox cbPerformance = (CheckBox) findViewById(R.id.performanceCategory);
        CheckBox cbClub = (CheckBox) findViewById(R.id.clubCategory);
        CheckBox cbResearch = (CheckBox) findViewById(R.id.researchCategory);
        CheckBox cbasa = (CheckBox) findViewById(R.id.asaCategory);




    }


}//MainActivity