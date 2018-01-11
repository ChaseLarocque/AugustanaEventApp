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



    public void nextEventListener(final Dialog addEvents) {
        ImageButton firstEvent = addEvents.findViewById(R.id.clickableEvent1);
        ImageButton secondEvent = addEvents.findViewById(R.id.clickableEvent2);
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
            }
        });
    }

    public void openEditEventDetails(){
        final Dialog editDetailEventDialog = new Dialog(mainActivity);
        editDetailEventDialog.setContentView(R.layout.edit_event_options);
        editDetailEventDialog.show();
    }
}





/*
    public void getLocation(){
        TextView location = mainActivity.findViewById(R.id.getLocation);
        // location.setText(AddEventListener.eventDetails.get(0));
    }
    */