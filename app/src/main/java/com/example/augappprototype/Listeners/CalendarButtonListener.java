package com.example.augappprototype.Listeners;

import android.app.Dialog;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
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
 *      popup of the events for that day displays on screen
 * dayEvents(Date date)
 *      checks if there are any events in the hash map
 * convertDate(Date date)
 *      Returns the year, month, and day
 * closeWindowListener(final Dialog eventPopup)
 *      on click will dismiss the event popup and go back to the calendar screen
 * filterEvent(final Dialog dialog)
 *      hides the basketball game event when the user unchecks the athletics category
 * showEditedEvent(final Dialog dialog)
 *      shows the Choir Performance event is showEdited event is true
 * studentMode(final Dialog dialog)
 *      shows the restrictions of student mode. Choir event is not visible to students unless
 *      showEditedEvent is set to true
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
     * When a date is selected a popup will be displayed that shows the events for that day
     * @param date
     * @param view
     */
    @Override
    public void onSelectDate(Date date, View view) {
        final Dialog eventPopupDialog = new Dialog(mainActivity);
        eventPopupDialog.setContentView(R.layout.eventpopup);
        closeWindowListener(eventPopupDialog);
        eventPopupDialog.show();
        Date converted = convertDate(date);
        if (dayEvents(converted) == true) {
            addTextViewForDetails(eventPopupDialog);
            //Toast.makeText(mainActivity, "Location: " + eventDisplay + " " + "Event: " +
            //                eventDisplay1 + " " + "Description: " + eventDisplay2,
            //        Toast.LENGTH_SHORT).show();
        }//if
        else
            Toast.makeText(mainActivity, "No Events On This Day",
                    Toast.LENGTH_SHORT).show();
            ;
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

    /**
     * closeWindowListener(Dialog) --> void
     * Closes the dialog when the close window button is clicked
     * @param eventPopup
     */
    public void closeWindowListener(final Dialog eventPopup){
        Button closeWindow = eventPopup.findViewById(R.id.closeButton);
        closeWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventPopup.dismiss();
            }
        });
    }//closeWindowListener

    public void addTextViewForDetails(Dialog eventPopup){
        LinearLayout eventList = eventPopup.findViewById(R.id.layoutEvents);
        for(int i = 0; i < 3; i++ )
        {
            TextView textView = new TextView(mainActivity);
            textView.setText(AddEventListener.eventDetails.get(i));
            textView.setGravity(Gravity.LEFT);
            eventList.addView(textView);
        }
    }
}//CalendarButtonListener
