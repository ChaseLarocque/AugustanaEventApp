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
    private final MainActivity mainActivity;



    public CalendarButtonListener(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    public void onSelectDate(Date date, View view) {
        Date converted = convertDate(date);
        if (dayEvents(converted) == true) {
            String eventDisplay = AddEventListener.events.get(date).get(0);
            String eventDisplay1 = AddEventListener.events.get(date).get(1);
            String eventDisplay2 = AddEventListener.events.get(date).get(2);
            Toast.makeText(mainActivity, "Location: " + eventDisplay + " " + "Event: " + eventDisplay1 + " " + "Description: " + eventDisplay2,
                    Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(mainActivity, "No events for this day",
                    Toast.LENGTH_SHORT).show();
    }

    public boolean dayEvents(Date date){
        if (AddEventListener.events.containsKey(date))
            return true;
        else
            return false;
    }

    public Date convertDate(Date date){
        return new Date(date.getYear(), date.getMonth(), date.getDate());
    }



}
