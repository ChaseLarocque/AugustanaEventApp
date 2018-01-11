package com.example.augappprototype.Listeners;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.augappprototype.MainActivity;
import com.example.augappprototype.R;
import com.roomorama.caldroid.CaldroidListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Pao on 1/7/2018.
 * CalendarButtonListener
 * extends CaldroidListener
 * Responsible for the events that occur when a date on the calendar is clicked
 *
 * Methods:
 * onSelectDate(Date date, View view)
 * dayEvents(Date date)
 * convertDate(Date date)
 *      Returns the year, month, and day
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
        final Dialog eventPopupDialog = new Dialog(mainActivity);
        eventPopupDialog.setContentView(R.layout.eventpopup);
        filterEvent(eventPopupDialog);
        showEditedEvent(eventPopupDialog);
        studentMode(eventPopupDialog);
        closeWindowListener(eventPopupDialog);
        eventPopupDialog.show();

        Date converted = convertDate(date);
        if (dayEvents(converted) == true) {
            String eventDisplay = AddEventListener.events.get(date).get(0);
            String eventDisplay1 = AddEventListener.events.get(date).get(1);
            String eventDisplay2 = AddEventListener.events.get(date).get(2);
            //Toast.makeText(mainActivity, "Location: " + eventDisplay + " " + "Event: " +
                       //     eventDisplay1 + " " + "Description: " + eventDisplay2,
                //    Toast.LENGTH_SHORT).show();
        }
        else
            ;
    }

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
    }

    public void closeWindowListener(final Dialog eventPopup){
        Button closeWindow = eventPopup.findViewById(R.id.closeButton);
        closeWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventPopup.dismiss();
            }
        });

    }

    public void filterEvent(final Dialog dialog){
        TextView event1 = dialog.findViewById(R.id.imageButton);
        if (CategoryButtonListener.filterAthletics == true && LoginButtonListener.facultyOrStudent == "faculty")
            event1.setVisibility(View.GONE);
    }

    public void showEditedEvent(final Dialog dialog){
        TextView event2 = dialog.findViewById(R.id.imageButton2);
        if (EditEventButtonListener.showEditedEvent == true)
            event2.setBackgroundResource(R.drawable.prototypeevent3);
    }

    public void studentMode(final Dialog dialog){
        TextView event2 = dialog.findViewById(R.id.imageButton2);
        if (LoginButtonListener.facultyOrStudent == "faculty" || EditEventButtonListener.showEditedEvent == true)
            event2.setVisibility(View.VISIBLE);
        else
            event2.setVisibility(View.GONE);
    }//convertDate
}//CalendarButtonListener
