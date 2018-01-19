package com.example.augappprototype.Listeners;

import android.app.Dialog;
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
            }
        }
        eventsInDay.addView(eventList);
    }

    public void addTextViewForDetails(Dialog eventPopup, Event event) {
        MaxHeightScrollView eventsInDay = eventPopup.findViewById(R.id.parentLayout);
        eventList = new LinearLayout(mainActivity);
        eventList.setLayoutParams(new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        eventList.setOrientation(LinearLayout.VERTICAL);
        for (Event eachEvents : mainActivity.items) {
            if (eachEvents.equals(event)) {
                for (int y = 0; y < 4; y++) {
                    TextView textView = new TextView(mainActivity);
                    if (y == 0) {
                        textView.setText("Title: " + event.getSummary());
                        eventList.addView(textView);
                    } else if (y == 1) {
                        textView.setText("Time: " + getStartTimeFromDateTime(event) + " - " +
                                getEndTimeFromDateTime(event));
                        eventList.addView(textView);
                    } else if (y == 2) {
                        textView.setText("Location: " + event.getLocation());
                        eventList.addView(textView);
                    } else {
                        textView.setText("Description: " + event.getDescription());
                        eventList.addView(textView);
                    }
                }
            }
        }
        eventsInDay.addView(eventList);

    }

    public Date getDateFromDateTime(Event eachEvent){
        String eventDetails = eachEvent.getStart().toString();
        eventYear = Integer.parseInt(eventDetails.substring(13,17));
        eventMonth = Integer.parseInt(eventDetails.substring(18,20));
        eventDay = Integer.parseInt(eventDetails.substring(21,23));
        return new Date(eventYear - 1900, eventMonth - 1, eventDay);
    }

    public String getStartTimeFromDateTime(Event eachEvent){
        String eventDetails = eachEvent.getStart().toString();
        int startEventHour = Integer.parseInt(eventDetails.substring(24,26));
        int startEventMinute = Integer.parseInt(eventDetails.substring(27,29));
        return mainActivity.convertEventTime(startEventHour, startEventMinute);
    }

    public String getEndTimeFromDateTime(Event eachEvent){
        String eventDetails = eachEvent.getEnd().toString();
        int endEventHour = Integer.parseInt(eventDetails.substring(24,26));
        int endEventMinute = Integer.parseInt(eventDetails.substring(27,29));
        return mainActivity.convertEventTime(endEventHour, endEventMinute);
    }


    public void dateForBanner(Dialog eventPopup, Date date){
        TextView dateBanner = eventPopup.findViewById(R.id.eventBanner);
        dateBanner.setText(" " + monthNames.get(date.getMonth()) + " " +
                date.getDate() + ", " + (date.getYear() + 1900));
    }
}//CalendarButtonListener
