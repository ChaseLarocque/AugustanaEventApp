package com.example.augappprototype.Listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.augappprototype.MainActivity;
import com.example.augappprototype.MainMenu;

/**
 * Created by Chase on 2018-01-10.
 * EventsCalendarListener
 * implements View.OnClickListener
 * Responsible for the events that occur when the event calendar button is clicked
 *
 * Methods:
 * onClick(View v)
 *      On click will take the user to the Calendar UI screen
 */

public class EventCalendarListener implements View.OnClickListener{
    /*--Data--*/
    private final MainMenu mainMenu;

    /*--Constructor--*/
    public EventCalendarListener(MainMenu mainMenu){
        this.mainMenu = mainMenu;
    }

    /*--Methods--*/
    /**
     * onClick(View) --> void
     * Takes the user to the calendar UI screen when the event calendar button is clicked
     * @param v
     */
    @Override
    public void onClick(View v) {

    }//onClick
}//EventCalendarListener
