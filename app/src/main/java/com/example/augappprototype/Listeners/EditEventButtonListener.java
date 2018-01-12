package com.example.augappprototype.Listeners;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.augappprototype.MainActivity;
import com.example.augappprototype.R;

import java.util.Date;

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
    public static boolean showEditedEvent;

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
        if (GuestButtonListener.isGuest)//edit or add event button is not usable
            Toast.makeText(mainActivity, "This button is not available on guest mode",
                    Toast.LENGTH_SHORT).show();
        else {
            final Dialog editEventDialog = new Dialog(mainActivity);
            editEventDialog.setContentView(R.layout.edit_event);
            editEventDialog.show();
            nextEventListener(editEventDialog);
        }//else
    }//onClick

    /**
     * nextEventListener(Dialog) --> void
     * When the user selects an event they want to edit, it will bring up the event details,
     * where the user can edit the event details
     * @param editEvents
     */
    public void nextEventListener(final Dialog editEvents) {
        ImageButton firstEvent = editEvents.findViewById(R.id.clickableEvent1);
        ImageButton secondEvent = editEvents.findViewById(R.id.clickableEvent2);
        firstEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditEventDetails();
            }
        });
        secondEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditEventDetails();
                editEvents.dismiss();
            }
        });
    }//nextEventListener

    /**
     * openEditEventDetails() --> void
     * Displays a popup of the event details and allows the user to edit the details that have
     * changed
     */
    public void openEditEventDetails() {
        final Dialog editDetailEventDialog = new Dialog(mainActivity);
        editDetailEventDialog.setContentView(R.layout.edit_event_options);
        editDetailEventDialog.show();
        submitEditEventDetails(editDetailEventDialog);
    }//openEditEventDetails

    /**
     * submitEditEventDetails(Dialog) --> void
     * Closes the popup for event details which submits the edit
     * @param submitEvents
     */
    public void submitEditEventDetails(final Dialog submitEvents) {
        Button closeEditEvent = submitEvents.findViewById(R.id.submitEvent);
        closeEditEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEvents.dismiss();
                showEditedEvent = true;//shows choir event on student mode
            }
        });
    }//submitEditEventDetails
}//EditEventButtonListener


/*
    public void getLocation(){
        TextView location = mainActivity.findViewById(R.id.getLocation);
        // location.setText(AddEventListener.eventDetails.get(0));
    }
    */