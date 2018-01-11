package com.example.augappprototype.Listeners;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.augappprototype.MainActivity;
import com.example.augappprototype.R;

import java.util.Date;

/**
 * Created by Pao on 1/9/2018.
 */

public class EditEventButtonListener implements View.OnClickListener {
    private final MainActivity mainActivity;
    public static boolean showEditedEvent;
    public EditEventButtonListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(View v) {
        final Dialog editEventDialog = new Dialog(mainActivity);
        editEventDialog.setContentView(R.layout.edit_event);
        //getLocation();
        editEventDialog.show();
        nextEventListener(editEventDialog);
    }



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
    }

    public void openEditEventDetails(){
        final Dialog editDetailEventDialog = new Dialog(mainActivity);
        editDetailEventDialog.setContentView(R.layout.edit_event_options);
        editDetailEventDialog.show();
        submitEditEventDetails(editDetailEventDialog);
    }

    public void submitEditEventDetails(final Dialog submitEvents){
        Button closeEditEvent = submitEvents.findViewById(R.id.submitEvent);
        closeEditEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEvents.dismiss();
                showEditedEvent = true;
            }
        });
    }
}





/*
    public void getLocation(){
        TextView location = mainActivity.findViewById(R.id.getLocation);
        // location.setText(AddEventListener.eventDetails.get(0));
    }
    */