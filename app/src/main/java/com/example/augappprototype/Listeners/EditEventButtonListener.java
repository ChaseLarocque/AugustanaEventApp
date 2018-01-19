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
import com.google.api.services.calendar.model.Event;

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
    HashMap<Date, ArrayList> allEvents = new HashMap<>();
    ArrayList<Event> events;
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
            closeButtonListener(editEventDialog);
    }//onClick

    public void closeButtonListener(final Dialog editEvents){
        Button closeWindow = editEvents.findViewById(R.id.closeButton);
        closeWindow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editEvents.dismiss();
            }
        });
    }//closeWindowListener

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
        datesIntoButtons(editEvents);
        eventsInDay.addView(eventList);
    }//nextEventListener

    private void datesIntoButtons(final Dialog eventDialog){
        CalendarButtonListener calendarButtonListener = new CalendarButtonListener(mainActivity);
        for (final Event key : mainActivity.items) {
            events = new ArrayList<>();
            if (!allEvents.containsKey(calendarButtonListener.getDateFromDateTime(key))) {
                events.add(key);
                allEvents.put(calendarButtonListener.getDateFromDateTime(key), events);
            } else {
                allEvents.get(calendarButtonListener.getDateFromDateTime(key));
                allEvents.put(calendarButtonListener.getDateFromDateTime(key), allEvents.get(key));
            }
            Button eachEvent = new Button(mainActivity);
            eachEvent.setText(key.getSummary());
            eachEvent.setBackgroundResource(R.drawable.eventbuttonarrow);
            eachEvent.setTextSize(20);
            eachEvent.setLayoutParams(new LinearLayout
                    .LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            eventList.addView(eachEvent);
            eachEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openEditEventDetails(key, eventDialog);
                }
            });

            }
        }

    /**
     * openEditEventDetails() --> void
     * Displays a popup of the event details and allows the user to edit the details that have
     * changed
     */
    private void openEditEventDetails(Event event, Dialog eventDialog) {
        final Dialog editDetailEventDialog = new Dialog(mainActivity);
        editDetailEventDialog.setContentView(R.layout.edit_event_options);
        editDetailEventDialog.show();
        EditText eventTitle = editDetailEventDialog.findViewById(R.id.eventTitle);
        eventTitle.setText(event.getSummary());
        EditText eventLocation = editDetailEventDialog.findViewById(R.id.eventLocation);
        eventLocation.setText(event.getLocation());
        EditText eventDescription = editDetailEventDialog.findViewById(R.id.eventDescription);
        eventDescription.setText(event.getDescription());
        TextView eventBanner = editDetailEventDialog.findViewById(R.id.eventBanner);
        eventBanner.setText("Event");
        closeButtonListener(editDetailEventDialog);
        openEditEventTimeDialog(editDetailEventDialog);
        eventDialog.dismiss();
    }//openEditEventDetails



    /**
     * submitEditEventDetails(Dialog) --> void
     * Closes the popup for event details which submits the edit
     */
    private void editEventDetails(Date date, int eventID) {
        eventDate = date;
        Dialog editEventDetails = new Dialog(mainActivity);
        editEventDetails.setContentView(R.layout.edit_event_options);
        editEventDetails.show();
        submitEditEventDetails(editEventDetails, date, eventID);
        openEditEventDateDialog(editEventDetails, date, eventID);
    }//submitEditEventDetails

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

                Toast.makeText(mainActivity, "Event Edited!", Toast.LENGTH_LONG).show();
                eventDetails.dismiss();
            }
        });
    }
    private void openEditEventTimeDialog(final Dialog eventDetails){
        final Button editTimeButton = eventDetails.findViewById(R.id.timechange);

        editTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editEventTime(eventDetails);
            }
        });
    }

    private void editEventTime(final Dialog editEventDetails) {
        final Dialog editTimeDialog = new Dialog(mainActivity);
        editTimeDialog.setContentView(R.layout.addevent_timepicker);
        editTimeDialog.show();
        final TimePicker eventTime = editTimeDialog.findViewById(R.id.timePicker);
        Button continueButton = editTimeDialog.findViewById(R.id.submitButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeNewTime(convertTimeToString(eventTime));
                editTimeDialog.dismiss();
                Button timeChange = editEventDetails.findViewById(R.id.timechange);
                timeChange.setText(convertTimeToString(eventTime));
            }
        });
    }

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
    }

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
    }

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
    }

    private void makeNewKey(Date oldEventDate, int oldEventID, Date newEventDate){
        HashMap<Integer, ArrayList<String>> events = new HashMap<>();
        if (AddEventListener.allEvents.containsKey(newEventDate)) {
            newEventID = 0;
            while(AddEventListener.allEvents.get(newEventDate).containsKey(newEventID))
                newEventID++;
            AddEventListener.allEvents.get(newEventDate).put(newEventID,
                    AddEventListener.allEvents.get(oldEventDate).remove(oldEventID));
        }
        else{
            AddEventListener.allEvents.put(newEventDate, events);
            AddEventListener.allEvents.get(newEventDate)
                    .put(0, AddEventListener.allEvents.get(oldEventDate).get(oldEventID));
            AddEventListener.allEvents.get(oldEventDate).remove(oldEventID);
        }
    }

    private void initializeNewDate(Date date, boolean editedDate){
        eventDate = date;
        dateWasChanged = editedDate;
    }

    private void initializeNewTime(String time){
        newEventTime = time;
    }

    private void deleteDatesWithNoEvents(){
        for (Date date : AddEventListener.allEvents.keySet()) {
            if (AddEventListener.allEvents.get(date).size() == 0)
                AddEventListener.allEvents.remove(date);
        }
    }
}//EditEventButtonListener
