package com.example.augappprototype.Listeners;

import android.app.Dialog;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;


import com.example.augappprototype.MainActivity;
import com.example.augappprototype.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZachyZachy7 on 2018-01-08.
 * CategoryButtonListener
 * implements View.OnClickListener
 * Responsible for the events that occur when the category button is clicked
 *
 * Methods:
 * onClick(View v)
 *      Displays message saying Google API does not support categories
 */

public class CategoryButtonListener implements View.OnClickListener {

    /*--Data--*/
    private final MainActivity mainActivity;

    /*--Constructor--*/
    public CategoryButtonListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }//CategoryButtonListener

    /*--Methods--*/
    /**
     * onClick(View) --> void
     * toast message says Google API does not support categories
     * @param v
     */
    @Override
    public void onClick(View v) {
        Toast.makeText(mainActivity, "Google API does not support categories",
                Toast.LENGTH_SHORT).show();
    }//onClick
}//CategoryButtonListener

