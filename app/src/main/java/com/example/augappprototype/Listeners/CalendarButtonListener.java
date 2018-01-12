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
        }//if
        else
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

    /**
     * filterEvent(Dialog) --> void
     * When the athletics category is set to unchecked, then the basketball event will not be shown
     * in faculty mode
     * @param dialog
     */
    public void filterEvent(final Dialog dialog){
        TextView event1 = dialog.findViewById(R.id.imageButton);
        if (CategoryButtonListener.filterAthletics == true
                && LoginButtonListener.facultyOrStudent == "faculty")
            event1.setVisibility(View.GONE);
    }//filterEvent

    /**
     * showEditedEvent(Dialog) --> void
     * Shows the edited choir event that was changed to Faith and Life Chapel instead of Performing
     * Arts Centre
     * @param dialog
     */
    public void showEditedEvent(final Dialog dialog){
        TextView event2 = dialog.findViewById(R.id.imageButton2);
        if (EditEventButtonListener.showEditedEvent == true)
            event2.setBackgroundResource(R.drawable.prototypeevent3);
    }//showEditedEvent

    /**
     * studentMode(Dialog) --> void
     * Shows only the basketball game if showEditedEvent is false, but shows the choir event if
     * showEditedEvent is true or user is logged in as faculty
     * @param dialog
     */
    public void studentMode(final Dialog dialog){
        TextView event2 = dialog.findViewById(R.id.imageButton2);
        if (LoginButtonListener.facultyOrStudent == "faculty"
                || EditEventButtonListener.showEditedEvent == true)
            event2.setVisibility(View.VISIBLE);
        else
            event2.setVisibility(View.GONE);
    }//studentMode
}//CalendarButtonListener
