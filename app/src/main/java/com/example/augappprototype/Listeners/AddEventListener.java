package com.example.augappprototype.Listeners;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.augappprototype.MainActivity;
import com.example.augappprototype.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Pao on 1/7/2018.
 */

public class AddEventListener implements View.OnClickListener {
    public static HashMap<Date, ArrayList<String>> events = new HashMap<>();
    public static ArrayList<String> eventDetails = new ArrayList();
    private int day;
    private int month;
    private int year;
    private int minute;
    private int hour;
    private String step;
    private final MainActivity mainActivity;
    private String amOrPm;
    EditText titleBox;
    EditText locationBox;
    EditText descriptionBox;
    String eventTitle;
    String eventDescription;
    String eventLocation;


    public AddEventListener(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(View v) {
        selectDate();

    }

    public void selectDate(){
        /**
        eventDetails.add(0, "Augustana");
        eventDetails.add(1, "Basketball Game");
        eventDetails.add(2, "Dennis Didusenko gonna dunk on mfers");
        events.put(new Date(118, 0, 1), eventDetails);
        Toast.makeText(mainActivity, "Event Added",
                Toast.LENGTH_SHORT).show();
         */

        final Dialog addEventDialog = new Dialog(mainActivity);
        addEventDialog.setContentView(R.layout.addeventpopup);
        addEventDialog.show();
        step = "date";
        closeWindowListener(addEventDialog);
        continueButtonListener(addEventDialog);
    }

    public void closeWindowListener(final Dialog addEvents){
        Button closeWindow = addEvents.findViewById(R.id.closeWindow);
        closeWindow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addEvents.dismiss();
            }
        });
    }

    public void continueButtonListener(final Dialog addEvents){
        Button continueButton = addEvents.findViewById(R.id.continueAdd);
        continueButton.setOnClickListener(new View.OnClickListener() {
        DatePicker eventDate = addEvents.findViewById(R.id.datePicker);
        TimePicker eventTime = addEvents.findViewById(R.id.timePicker);
            @Override
            public void onClick(View v) {
                if (step == "date") {
                    saveEventDate(eventDate);
                    addEvents.dismiss();
                    openEventTime();
                    step = "time";
                }
                else if (step == "time"){
                    saveEventTime(eventTime);
                    addEvents.dismiss();
                    openEventDetails();
                    step = "details";
                }
                else
                    addEvents.dismiss();

            }
        });
    }

    public void saveEventDate(DatePicker datePicker){
        year = datePicker.getYear() - 1900;
        month = datePicker.getMonth();
        day = datePicker.getDayOfMonth();
    }

    public void saveEventTime(TimePicker timePicker){
        minute = timePicker.getCurrentMinute();
        hour = timePicker.getCurrentHour();
        amOrPm = checkAmOrPm(hour);
    }

    public void saveEventDetails(EditText title, EditText location, EditText description){
        eventTitle = title.getText().toString();
        eventDescription = description.getText().toString();
        eventLocation = location.getText().toString();
    }


    public void openEventTime(){
        final Dialog addEventDialog = new Dialog(mainActivity);
        addEventDialog.setContentView(R.layout.addeventtime);
        addEventDialog.show();
        continueButtonListener(addEventDialog);
        closeWindowListener(addEventDialog);
    }

    public void openEventDetails(){
        final Dialog addEventDialog = new Dialog(mainActivity);
        addEventDialog.setContentView(R.layout.addeventdetails);
        addEventDialog.show();
        addEventButtonListener(addEventDialog);
        closeWindowListener(addEventDialog);
        locationBox = addEventDialog.findViewById(R.id.eventLocation);
        descriptionBox = addEventDialog.findViewById(R.id.eventDescription);
        titleBox = addEventDialog.findViewById(R.id.eventTitle);
    }

    public void addEventButtonListener(final Dialog addEvents){
        Button continueButton = addEvents.findViewById(R.id.submitEvent);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEventDetails(titleBox, locationBox, descriptionBox);
                eventDetails.add(0, eventLocation);
                eventDetails.add(1, eventTitle);
                eventDetails.add(2, eventDescription);
                events.put(new Date(year, month, day), eventDetails);
                Toast.makeText(mainActivity, "Event Added!",
                        Toast.LENGTH_SHORT).show();
                addEvents.dismiss();
            }
        });
    }

    private String checkAmOrPm(int hourOfDay){
        if (hourOfDay < 12)
            return "AM";
        else
            return "PM";

    }
}
