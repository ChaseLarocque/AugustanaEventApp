package com.example.augappprototype.Listeners;

import android.view.View;
import android.widget.Toast;

import com.example.augappprototype.MainActivity;
import com.roomorama.caldroid.CaldroidListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Pao on 1/7/2018.
 */

public class CalendarButtonListener extends CaldroidListener {

    /*--Data--*/
    private final MainActivity mainActivity;

    /*--Constructor--*/
    public CalendarButtonListener(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }//CalendarButtonListener

    /*--Methods--*/
    /**
     * onSelectDate(Date, View) --> void
     *
     * @param date
     * @param view
     */
    @Override
    public void onSelectDate(Date date, View view) {
        Date converted = convertDate(date);
        if (dayEvents(converted) == true) {
            String eventDisplay = AddEventListener.events.get(date).get(0);
            String eventDisplay1 = AddEventListener.events.get(date).get(1);
            String eventDisplay2 = AddEventListener.events.get(date).get(2);
            Toast.makeText(mainActivity, "Location: " + eventDisplay + " " + "Event: "
                            + eventDisplay1 + " " + "Description: " + eventDisplay2,
                    Toast.LENGTH_SHORT).show();
        }//if
        else
            Toast.makeText(mainActivity, "No events for this day",
                    Toast.LENGTH_SHORT).show();
    }//onSelectDate

    /**
     * dayEvents(Date) --> boolean
     * Checks if there are any events in the hash map
     * @param date
     * @return
     */
    public boolean dayEvents(Date date){
        if (AddEventListener.events.containsKey(date))//events in hash map
            return true;
        else
            return false;//no events in hash map
    }//dayEvents

    /**
     * convertDate(Date) --> Date
     * Returns the year, month, and day
     * @param date
     * @return
     */
    public Date convertDate(Date date){
        return new Date(date.getYear(), date.getMonth(), date.getDate());
    }//convertDate
}//CalendarButtonListener
