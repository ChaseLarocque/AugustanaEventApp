package com.example.augappprototype.Listeners;

import android.app.Dialog;
import android.view.View;

import com.example.augappprototype.MainActivity;
import com.example.augappprototype.R;

/**
 * Created by Pao on 1/9/2018.
 * EditEventButtonListener
 * implements View.OnClickListener
 * Responsible for the events that occur when the edit event button is clicked
 *
 * Methods:
 * onClick(View v)
 *
 */

public class EditEventButtonListener implements View.OnClickListener {
    /*--Data--*/
    private final MainActivity mainActivity;

    /*--Constructor--*/
    public EditEventButtonListener(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }//EditEventButtonListener

    /*--Methods--*/
    /**
     * onClick(View) --> void
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        final Dialog addEventDialog = new Dialog(mainActivity);
        addEventDialog.setContentView(R.layout.edit_event);
        addEventDialog.show();
    }//onClick
}//EditEventButtonListener
