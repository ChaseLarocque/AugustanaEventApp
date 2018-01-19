package com.example.augappprototype.Listeners;

import android.app.Dialog;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.bskim.maxheightscrollview.widgets.MaxHeightScrollView;
import com.example.augappprototype.MainActivity;
import com.example.augappprototype.R;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.roomorama.caldroid.CaldroidListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Pao on 1/7/2018.
 * CalendarButtonListener
 * extends CaldroidListener
 * Responsible for the events that occur when a date on the calendar is clicked
 *
 * Methods:
 * onSelectDate(Date date, View view)
 *      popup of the events for that day displays on screen
 * convertDate(Date date)
 *      Returns the year, month, and day
 * closeWindowListener(final Dialog eventPopup)
 *      on click will dismiss the event popup and go back to the calendar screen
 * addButtonsForEvents(final Dialog eventPopup, final Date date)
 *      Makes buttons for every event on the event popup when a date it selected
 * addTextViewForDetails(Dialog eventPopup, Event event)
 *      Adds a text view to display the event details
 * getDateFromDateTime(Event eachEvent)
 *      Returns the date from a date time format
 * getStartTimeFromDateTime(Event eachEvent)
 *      Returns the start time from a date time format
 * getEndTimeFromDateTime(Event eachEvent)
 *      Returns the end time from a date time format
 * dateForBanner(Dialog eventPopup, Date date)
 *      Displays the current day on the event popup banner
 */
public class CalendarButtonListener extends CaldroidListener {

    /*--Data--*/
    private final MainActivity mainActivity;
    LinearLayout eventList;
    List<String> monthNames = Arrays.asList("January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December");
    int eventYear;
    int eventMonth;
    int eventDay;

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
        dateForBanner(eventPopupDialog, date);
        addButtonsForEvents(eventPopupDialog, date);
        eventPopupDialog.show();
    }//onSelectDate

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
     * addButtonsForEvents(Dialog, Date) --> void
     * Adds a button that displays the event title on the event popup dialog for all events on
     * that day and when clicked will show the event details of that event
     * @param eventPopup
     * @param date
     */
    public void addButtonsForEvents(final Dialog eventPopup, final Date date) {
        MaxHeightScrollView eventsInDay = eventPopup.findViewById(R.id.allEvents);
        eventList = new LinearLayout(mainActivity);
        eventList.setLayoutParams(new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        eventList.setOrientation(LinearLayout.VERTICAL);
        for (final Event event : mainActivity.items) {
            if (getDateFromDateTime(event).equals(convertDate(date))) {
                Button eachEvent = new Button(mainActivity);
                eachEvent.setText(event.getSummary());
                eachEvent.setBackgroundResource(R.drawable.eventbuttonarrow);
                eachEvent.setTextSize(20);
                eachEvent.setLayoutParams(new LinearLayout
                        .LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                eachEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        eventPopup.setContentView(R.layout.eventpopup_details);
                        addTextViewForDetails(eventPopup, event);
                    }
                });
                eventList.addView(eachEvent);
            }//if
        }//for
        eventsInDay.addView(eventList);
    }//addButtonsForEvents

    /**
     * addTextViewForDetails(Dialog, Event) --> void
     * Adds a text view for displaying the event title, time, location, and description
     * @param eventPopup
     * @param event
     */
    public void addTextViewForDetails(Dialog eventPopup, Event event) {
        MaxHeightScrollView eventsInDay = eventPopup.findViewById(R.id.eventDetails);
        eventList = new LinearLayout(mainActivity);
        eventList.setLayoutParams(new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        eventList.setOrientation(LinearLayout.VERTICAL);
        for (Event eachEvents : mainActivity.items) {
            if (eachEvents.equals(event)) {
                for (int y = 0; y < 4; y++) {
                    TextView textView = new TextView(mainActivity);
                    if (y == 0) {
                        textView.setText(event.getSummary());
                        textView.setTextColor(mainActivity.getResources()
                                .getColor(R.color.uofaGreen));
                        textView.setTextSize(30);
                        eventList.addView(textView);
                    }//if
                    else if (y == 1) {
                        textView.setText("Time: " + getStartTimeFromDateTime(event) + " - " +
                                getEndTimeFromDateTime(event));
                        textView.setTextColor(Color.BLACK);
                        eventList.addView(textView);
                    }//else if
                    else if (y == 2) {
                        textView.setText("Location: " + event.getLocation());
                        textView.setTextColor(Color.BLACK);
                        eventList.addView(textView);
                    }//else if
                    else {
                        textView.setText("Description: " + event.getDescription());
                        textView.setTextColor(Color.BLACK);
                        eventList.addView(textView);
                    }//else
                }//for
            }//if
        }//for
        eventsInDay.addView(eventList);
        closeWindowListener(eventPopup);
    }//addTextViewForDetails

    /**
     * getDateFromDateTime(Event) --> Date
     * Gets the date from a date that also includes the time
     * @param eachEvent
     * @return Date
     */
    public Date getDateFromDateTime(Event eachEvent){
        String eventDetails = eachEvent.getStart().toString();
        eventYear = Integer.parseInt(eventDetails.substring(13,17));
        eventMonth = Integer.parseInt(eventDetails.substring(18,20));
        eventDay = Integer.parseInt(eventDetails.substring(21,23));
        return new Date(eventYear - 1900, eventMonth - 1, eventDay);
    }//getDateFromDateTime

    /**
     * getStartTimeFromDateTime(Event) --> String
     * Gets the start time from a date time format
     * @param eachEvent
     * @return
     */
    public String getStartTimeFromDateTime(Event eachEvent){
        String eventDetails = eachEvent.getStart().toString();
        int startEventHour = Integer.parseInt(eventDetails.substring(24,26));
        int startEventMinute = Integer.parseInt(eventDetails.substring(27,29));
        return mainActivity.convertEventTime(startEventHour, startEventMinute);
    }//getStartTimeFromDateTime

    /**
     * getEndTimeFromDateTime(Event) --> String
     * Gets the end time from a date time format
     * @param eachEvent
     * @return
     */
    public String getEndTimeFromDateTime(Event eachEvent){
        String eventDetails = eachEvent.getEnd().toString();
        int endEventHour = Integer.parseInt(eventDetails.substring(24,26));
        int endEventMinute = Integer.parseInt(eventDetails.substring(27,29));
        return mainActivity.convertEventTime(endEventHour, endEventMinute);
    }//getEndTimeFromDateTime

    /**
     * dateForBanner(Dialog, Date) --> void
     * Gets the current date and displays it on the event popup banner
     * @param eventPopup
     * @param date
     */
    public void dateForBanner(Dialog eventPopup, Date date){
        TextView dateBanner = eventPopup.findViewById(R.id.eventBanner);
        dateBanner.setText(" " + monthNames.get(date.getMonth()) + " " +
                date.getDate() + ", " + (date.getYear() + 1900));
    }//dateForBanner
}//CalendarButtonListener
