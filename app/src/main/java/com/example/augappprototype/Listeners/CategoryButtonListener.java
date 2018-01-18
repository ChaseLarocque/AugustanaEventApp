package com.example.augappprototype.Listeners;

import android.app.Dialog;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;


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
 *      Shows a popup consisting of different categories that can be checked or unchecked
 * closeButtonListener(final Dialog categoryDialog)
 *      Sets an on click listener for the close button that closes the dialog when clicked
 * saveCheckBox(final Dialog categoryDialog)
 *      saves the state of the checkbox's in the category popup using shared preferences
 */

public class CategoryButtonListener implements View.OnClickListener {

    /*--Data--*/
    private final MainActivity mainActivity;
    private final String athleticsKey = "athleticsKey";
    private final String performanceKey = "performanceKey";
    private final String clubKey = "clubKey";
    private final String researchKey = "researchKey";
    private final String asaKey = "asaKey";

    /*--Constructor--*/
    public CategoryButtonListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }//CategoryButtonListener

    /*--Methods--*/
    /**
     * onClick(View) --> void
     * When the category button is clicked it will open a popup that has categories with check
     * boxes that are automatically checked to start. Calls the closeButtonListener that will
     * dismiss the dialog when it is clicked
     * @param v
     */
    @Override
    public void onClick(View v) {
        final Dialog categoryDialog = new Dialog(mainActivity);
        categoryDialog.setContentView(R.layout.categorypopup);
        categoryDialog.show();
        closeButtonListener(categoryDialog);
        saveCheckBox(categoryDialog);
    }//onClick

    /**
     * closeButtonListener(Dialog) --> void
     * Closes the category popup when clicked
     * @param categoryDialog
     */
    public void closeButtonListener(final Dialog categoryDialog) {
        final Button closeButton = (Button) categoryDialog.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryDialog.dismiss();
            }//onClick
        });
    }//closeButtonListener

    /**
     * saveCheckBox(Dialog) --> void
     * Stores all the different category checkbox's into a hash map with a string as the key
     * and the checkbox as a value. It then saves the state of the checkbox with shared
     * preferences.
     * @param categoryDialog
     */
    public void saveCheckBox(final Dialog categoryDialog) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mainActivity);
        final CheckBox athletics = (CheckBox)categoryDialog.findViewById(R.id.athleticsCategory);
        final CheckBox performance = (CheckBox)categoryDialog.findViewById(R.id.performanceCategory);
        final CheckBox club = (CheckBox)categoryDialog.findViewById(R.id.clubCategory);
        final CheckBox research = (CheckBox)categoryDialog.findViewById(R.id.researchCategory);
        final CheckBox asa = (CheckBox)categoryDialog.findViewById(R.id.asaCategory);
        Map<String, CheckBox> checkBoxMap = new HashMap();
        checkBoxMap.put(athleticsKey, athletics);
        checkBoxMap.put(performanceKey, performance);
        checkBoxMap.put(clubKey, club);
        checkBoxMap.put(researchKey, research);
        checkBoxMap.put(asaKey, asa);
        for (Map.Entry<String, CheckBox> checkboxEntry: checkBoxMap.entrySet()) {
            Boolean checked = sharedPreferences.getBoolean(checkboxEntry.getKey(), true);
            checkboxEntry.getValue().setChecked(checked);
        }//for
        for (final Map.Entry<String, CheckBox> checkboxEntry: checkBoxMap.entrySet()) {
            checkboxEntry.getValue().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(checkboxEntry.getKey(), isChecked);
                    editor.apply();
                }//onCheckedChange
            });
        }//for
    }//saveCheckBox
}//CategoryButtonListener

