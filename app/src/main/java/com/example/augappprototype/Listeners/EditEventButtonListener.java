package com.example.augappprototype.Listeners;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bskim.maxheightscrollview.widgets.MaxHeightScrollView;
import com.example.augappprototype.MainActivity;
import com.example.augappprototype.R;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Pao on 1/9/2018.
 * EditEventButtonListener
 * implements View.OnClickListener
 * Responsible for the events that occur when the edit event button is clicked
 *
 * Methods:
 * onClick(View v)
 *      on click if user is guest it will say button not available on guest mode, otherwise it will
 *      bring up the edit event dialog that will prompt the user to choose an event to edit
 * nextEventListener(final Dialog editEvents)
 *      when user clicks on an event they want to edit, it displays the event details
 * openEditEventDetails()
 *      opens the event details popup so the user can edit the event
 * submitEditEventDetails(final Dialog submitEvents)
 *      closes the event details popup when clicked
 */


public class EditEventButtonListener implements View.OnClickListener {
    /*--Data--*/
    private final MainActivity mainActivity;
    LinearLayout eventList;
    List<String> monthNames = Arrays.asList("January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December");

    /*--Constructor--*/
    public EditEventButtonListener(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

    /*--Methods--*/
    /**
     * onClick(View) --> void
     * If user is a guest then a toast message will popup saying this button is not available on
     * guest mode, otherwise it will bring up the edit event dialog prompting the user to choose an
     * event to edit
     * @param v
     */
    @Override
    public void onClick(View v) {
            final Dialog editEventDialog = new Dialog(mainActivity);
            editEventDialog.setContentView(R.layout.edit_event);
            editEventDialog.show();
            nextEventListener(editEventDialog);
    }//onClick

    /**
     * nextEventListener(Dialog) --> void
     * When the user selects an event they want to edit, it will bring up the event details,
     * where the user can edit the event details
     * @param editEvents
     */
    public void nextEventListener(final Dialog editEvents) {
        MaxHeightScrollView eventsInDay = editEvents.findViewById(R.id.allEvents);
        eventList = new LinearLayout(mainActivity);
        eventList.setLayoutParams(new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        eventList.setOrientation(LinearLayout.VERTICAL);
        datesIntoButtons();
        eventsInDay.addView(eventList);
    }//nextEventListener

    public void datesIntoButtons(){
        for (final Date key : AddEventListener.allEvents.keySet()) {
            Button eachEvent = new Button(mainActivity);
            eachEvent.setText(monthNames.get(key.getMonth()) + " " +
                    key.getDate() + ", " + (key.getYear() + 1900));
            eachEvent.setBackgroundResource(R.drawable.eventbuttonarrow);
            eachEvent.setTextSize(20);
            eachEvent.setLayoutParams(new LinearLayout
                    .LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            eachEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openEditEventDetails(key);
                }
            });
            eventList.addView(eachEvent);
        }
    }

    /**
     * openEditEventDetails() --> void
     * Displays a popup of the event details and allows the user to edit the details that have
     * changed
     */
    public void openEditEventDetails(Date date) {
        final Dialog editDetailEventDialog = new Dialog(mainActivity);
        editDetailEventDialog.setContentView(R.layout.eventpopup);
        MaxHeightScrollView eventsInDay = editDetailEventDialog.findViewById(R.id.allEvents);
        editDetailEventDialog.show();
        eventList = new LinearLayout(mainActivity);
        eventList.setLayoutParams(new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        eventList.setOrientation(LinearLayout.VERTICAL);
        TextView eventBanner = editDetailEventDialog.findViewById(R.id.eventBanner);
        eventBanner.setText(monthNames.get(date.getMonth()) + " " +
                date.getDate() + ", " + (date.getYear() + 1900));
        eventsIntoButtons(date);
        eventsInDay.addView(eventList);
    }//openEditEventDetails

    public void eventsIntoButtons(final Date date){
        for (final int key : AddEventListener.allEvents.get(date).keySet()) {
            Button eachEvent = new Button(mainActivity);
            eachEvent.setText(AddEventListener.allEvents.get(date).get(key).get(0));
            eachEvent.setBackgroundResource(R.drawable.eventbuttonarrow);
            eachEvent.setTextSize(20);
            eachEvent.setLayoutParams(new LinearLayout
                    .LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            eachEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    submitEditEventDetails(date, key);

                }
            });
            eventList.addView(eachEvent);
        }
    }

    /**
     * submitEditEventDetails(Dialog) --> void
     * Closes the popup for event details which submits the edit
     */
    public void submitEditEventDetails(Date date, int eventID) {
        Dialog editEventDetails = new Dialog(mainActivity);
        editEventDetails.setContentView(R.layout.edit_event_options);
        EditText title = editEventDetails.findViewById(R.id.eventTitle);
        EditText location = editEventDetails.findViewById(R.id.eventLocation);
        EditText description = editEventDetails.findViewById(R.id.eventDescription);
        title.setText(AddEventListener.allEvents.get(date).get(eventID).get(0));
        location.setText(AddEventListener.allEvents.get(date).get(eventID).get(1));
        description.setText(AddEventListener.allEvents.get(date).get(eventID).get(2));
        editEventDetails.show();
    }//submitEditEventDetails
}//EditEventButtonListener
