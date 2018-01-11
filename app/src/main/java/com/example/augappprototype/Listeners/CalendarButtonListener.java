package com.example.augappprototype.Listeners;

import android.app.Dialog;
import android.view.View;
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
 */

public class CalendarButtonListener extends CaldroidListener {
    private final MainActivity mainActivity;



    public CalendarButtonListener(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    public void onSelectDate(Date date, View view) {
        final Dialog addEventDialog = new Dialog(mainActivity);
        addEventDialog.setContentView(R.layout.eventpopup);
        filterEvent(addEventDialog);
        addEventDialog.show();
        Date converted = convertDate(date);
        if (dayEvents(converted) == true) {

            String eventDisplay = AddEventListener.events.get(date).get(0);
            String eventDisplay1 = AddEventListener.events.get(date).get(1);
            String eventDisplay2 = AddEventListener.events.get(date).get(2);
            Toast.makeText(mainActivity, "Location: " + eventDisplay + " " + "Event: " +
                            eventDisplay1 + " " + "Description: " + eventDisplay2,
                    Toast.LENGTH_SHORT).show();
        }
        else
            ;
    }

    public boolean dayEvents(Date date){
        if (AddEventListener.events.containsKey(date))
            return true;
        else
            return false;
    }

    public Date convertDate(Date date){
        return new Date(date.getYear(), date.getMonth(), date.getDate());
    }

    public void filterEvent(final Dialog dialog){
        TextView event2 = dialog.findViewById(R.id.imageButton);
        if (CategoryButtonListener.filterAthletics == true)
            event2.setVisibility(View.GONE);
    }

    /**
    public void eventTextview(){
        int countEvents = 0;
        for (Date event: AddEventListener.events.keySet() ) {
            countEvents += 1;
        }
        LinearLayout layout = new LinearLayout(mainActivity);
        layout.findViewById(R.id.eachEvents);

        final TextView txt = new TextView(mainActivity);
        txt.findViewById(R.id.event1);

        txt.setText("popcorn");
        layout.invalidate();

        Toast.makeText(mainActivity, txt.getText() + "lol",
                Toast.LENGTH_SHORT).show();
    }
*/


}

/**
    final TextView[] myTextViews = new TextView[countEvents];

        for (int i = 0; i < countEvents; i++) {
// create a new textview
final TextView rowTextView = new TextView(mainActivity);

        // set some properties of rowTextView or something
        rowTextView.setText("This is row #" + i);

        // add the textview to the linearlayout
        eventList.addView(rowTextView);

        // save a reference to the textview for later
        myTextViews[i] = rowTextView;
        }
        */