package com.example.augappprototype.Listeners;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bskim.maxheightscrollview.widgets.MaxHeightScrollView;
import com.example.augappprototype.MainActivity;
import com.example.augappprototype.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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
    private LinearLayout eventList;
    private List<String> monthNames = Arrays.asList("January", "February", "March", "April",
            "May", "June", "July", "August", "September", "October", "November", "December");
    private Date eventDate;
    private int newEventID;
    private String newEventTime;
    private boolean dateWasChanged;

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
    private void nextEventListener(final Dialog editEvents) {
        MaxHeightScrollView eventsInDay = editEvents.findViewById(R.id.allEvents);
        eventList = new LinearLayout(mainActivity);
        eventList.setLayoutParams(new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        eventList.setOrientation(LinearLayout.VERTICAL);
        datesIntoButtons();
        eventsInDay.addView(eventList);
    }//nextEventListener

    /**
     * datesIntoButtons() --> void
     * Makes a button for every date that has an event or events
     */
    private void datesIntoButtons(){
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
        }//for
    }//datesIntoButtons

    /**
     * openEditEventDetails() --> void
     * Displays a popup of the event details and allows the user to edit the details that have
     * changed
     */
    private void openEditEventDetails(Date date) {
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

    /**
     * eventsIntoButtons(Date) --> void
     * Goes through every event and makes it a button
     * @param date
     */
    private void eventsIntoButtons(final Date date){
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
                    openEditEventDetails(date, key);
                }//onClick
            });
            eventList.addView(eachEvent);
        }//for
    }//eventsIntoButtons

    /**
     * openEditEventDetails(Dialog) --> void
     *
     */
    private void openEditEventDetails(Date date, int eventID) {
        eventDate = date;
        Dialog editEventDetails = new Dialog(mainActivity);
        editEventDetails.setContentView(R.layout.edit_event_options);
        editEventDetails.show();
        submitEditEventDetails(editEventDetails, date, eventID);
        openEditEventTimeDialog(editEventDetails, date, eventID);
        openEditEventDateDialog(editEventDetails, date, eventID);
    }//openEditEventDetails

    /**
     * submitEditEventDetails(Dialog, Date, int) --> void
     * Stores the new details that the user has entered and changes the event id if the date was
     * changed and updates the calendar showing the change
     * @param eventDetails
     * @param date
     * @param eventID
     */
    private void submitEditEventDetails(final Dialog eventDetails, final Date date, final int eventID){
        Button submitNewDetails = eventDetails.findViewById(R.id.submitEvent);
        final EditText title = eventDetails.findViewById(R.id.eventTitle);
        final EditText location = eventDetails.findViewById(R.id.eventLocation);
        final EditText description = eventDetails.findViewById(R.id.eventDescription);
        title.setText(AddEventListener.allEvents.get(date).get(eventID).get(0));
        location.setText(AddEventListener.allEvents.get(date).get(eventID).get(2));
        description.setText(AddEventListener.allEvents.get(date).get(eventID).get(3));
        submitNewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateWasChanged)
                    makeNewKey(date, eventID, eventDate);
                AddEventListener.allEvents.get(eventDate).get(newEventID).set(0, title.getText().toString());
                AddEventListener.allEvents.get(eventDate).get(newEventID).set(1, newEventTime);
                AddEventListener.allEvents.get(eventDate).get(newEventID).set(2, location.getText().toString());
                AddEventListener.allEvents.get(eventDate).get(newEventID).set(3, description.getText().toString());
                deleteDatesWithNoEvents();
                mainActivity.updateCalendar();
                Toast.makeText(mainActivity, "Event Edited!", Toast.LENGTH_LONG).show();
                eventDetails.dismiss();
            }
        });
    }//submitEditEventDetails

    /**
     * openEditEventTimeDialog(Dialog, Date, int) --> void
     * Opens the dialog that allows the user to edit the time of an event
     * @param eventDetails
     * @param date
     * @param eventID
     */
    private void openEditEventTimeDialog(final Dialog eventDetails, final Date date, final int eventID){
        final Button editTimeButton = eventDetails.findViewById(R.id.timechange);
        editTimeButton.setText(AddEventListener.allEvents.get(date).get(eventID).get(1));
        editTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editEventTime(eventDetails, date, eventID);
            }
        });
    }//openEditEventTimeDialog

    /**
     * editEventTime(Dialog, Date, int) --> void
     * User if able to change the time and it is then converted into a string
     * @param editEventDetails
     * @param date
     * @param eventID
     */
    private void editEventTime(final Dialog editEventDetails, final Date date, final int eventID) {
        final Dialog editTimeDialog = new Dialog(mainActivity);
        editTimeDialog.setContentView(R.layout.addeventtime);
        editTimeDialog.show();
        final TimePicker eventTime = editTimeDialog.findViewById(R.id.timePicker);
        Button continueButton = editTimeDialog.findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeNewTime(convertTimeToString(eventTime));
                editTimeDialog.dismiss();
                Button timeChange = editEventDetails.findViewById(R.id.timechange);
                timeChange.setText(convertTimeToString(eventTime));
            }
        });
    }//editEventTime

    /**
     * convertTimeToString(TimePicker) --> String
     * Takes the time from the time picker and converts it into a string
     * @param timePicker
     * @return String
     */
    private String convertTimeToString(TimePicker timePicker){
        String doubleDigitMinute = String.format("%02d", timePicker.getCurrentMinute());
        if (timePicker.getCurrentHour() > 12)
            return ((timePicker.getCurrentHour() - 12) + ":" + doubleDigitMinute + "pm");
        else if (timePicker.getCurrentHour() == 0)
            return ((timePicker.getCurrentHour() + 12) + ":" + doubleDigitMinute + "am");
        else if (timePicker.getCurrentHour() == 12)
            return (timePicker.getCurrentHour() + ":" + doubleDigitMinute + "pm");
        else
            return (timePicker.getCurrentHour() + ":" + doubleDigitMinute + "am");
    }//convertTimeToString

    /**
     * openEditEventDateDialog(Dialog, Date, int) --> void
     * Opens the dialog that will allow the user to change the date
     * @param eventDetails
     * @param date
     * @param currentEventID
     */
    private void openEditEventDateDialog(final Dialog eventDetails, final Date date, final int currentEventID){
        final Button editDateButton = eventDetails.findViewById(R.id.dateOfTheEvent);
        editDateButton.setText((eventDate.getMonth() + 1) + "/" +  eventDate.getDate()
                + "/" +  (eventDate.getYear() + 1900));
        editDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editEventDate(eventDetails, date, currentEventID);
            }
        });
    }//openEditEventDateDialog

    /**
     * editEventDate(Dialog, Date, int) --> void
     * Displays the date picker where the user can select a new date for an event they want to edit
     * and stores that date
     * @param editEventDetails
     * @param date
     * @param currentEventID
     */
    private void editEventDate(final Dialog editEventDetails, final Date date, final int currentEventID) {
        final Dialog editDateDialog = new Dialog(mainActivity);
        editDateDialog.setContentView(R.layout.addeventpopup);
        editDateDialog.show();
        final DatePicker eventDatePicker = editDateDialog.findViewById(R.id.datePicker);
        Toast.makeText(mainActivity, "" + date.getYear() + date.getMonth() + date.getDate(),
                Toast.LENGTH_LONG).show();
        eventDatePicker.updateDate(date.getYear() + 1900, date.getMonth(), date.getDate());
        Button continueButton = editDateDialog.findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventDate = new Date((eventDatePicker.getYear() - 1900),
                        eventDatePicker.getMonth(), eventDatePicker.getDayOfMonth());
                if (eventDate.equals(date))
                    dateWasChanged = false;
                else
                    dateWasChanged = true;
                Toast.makeText(mainActivity, date + "/" + eventDate,
                        Toast.LENGTH_LONG).show();
                initializeNewDate(eventDate, dateWasChanged);
                Button dateChange = editEventDetails.findViewById(R.id.dateOfTheEvent);
                dateChange.setText((eventDate.getMonth() + 1) + "/" +  eventDate.getDate()
                        + "/" +  (eventDate.getYear() + 1900));
                editDateDialog.dismiss();
            }
        });
    }//editEventDate

    /**
     * makeNewKey(Date, int, Date) --> void
     * If the user edited the date of an event then a new key is made so that it has a different
     * event id
     * @param oldEventDate
     * @param oldEventID
     * @param newEventDate
     */
    private void makeNewKey(Date oldEventDate, int oldEventID, Date newEventDate){
        HashMap<Integer, ArrayList<String>> events = new HashMap<>();
        if (AddEventListener.allEvents.containsKey(newEventDate)) {
            newEventID = 0;
            while(AddEventListener.allEvents.get(newEventDate).containsKey(newEventID))
                newEventID++;
            AddEventListener.allEvents.get(newEventDate).put(newEventID,
                    AddEventListener.allEvents.get(oldEventDate).remove(oldEventID));
        }//if
        else {
            AddEventListener.allEvents.put(newEventDate, events);
            AddEventListener.allEvents.get(newEventDate)
                    .put(0, AddEventListener.allEvents.get(oldEventDate).get(oldEventID));
            AddEventListener.allEvents.get(oldEventDate).remove(oldEventID);
        }//else
    }//makeNewKey

    /**
     * initializeNewDate(Date, boolean) --> void
     *
     * @param date
     * @param editedDate
     */
    private void initializeNewDate(Date date, boolean editedDate){
        eventDate = date;
        dateWasChanged = editedDate;
    }//initializeNewDate

    /**
     * initializeNewTime(String) --> void
     *
     * @param time
     */
    private void initializeNewTime(String time){
        newEventTime = time;
    }//initializeNewTime

    /**
     * deleteDatesWithNoEvents() --> void
     *
     */
    private void deleteDatesWithNoEvents(){
        for (Date date : AddEventListener.allEvents.keySet()) {
            if (AddEventListener.allEvents.get(date).size() == 0)
                AddEventListener.allEvents.remove(date);
        }//if
    }//deleteDatesWithNoEvents
}//EditEventButtonListener
