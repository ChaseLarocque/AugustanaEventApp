package com.example.augappprototype.Listeners;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.augappprototype.MainActivity;
import com.example.augappprototype.R;

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
 *
 */

public class CategoryButtonListener implements View.OnClickListener {

    /*--Data--*/
    private final MainActivity mainActivity;
    public static boolean filterAthletics;

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
    }//onClick

    /**
     * closeButtonListener(Dialog) --> void
     * Closes the category popup when clicked
     * @param categoryDialog
     */
    public void closeButtonListener(final Dialog categoryDialog) {
        Button closeButton = (Button) categoryDialog.findViewById(R.id.closeButton);
        final CheckBox athletics = categoryDialog.findViewById(R.id.athleticsCategory);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (athletics.isChecked()) {
                    filterAthletics = false;
                } else {
                    filterAthletics = true;
                }
                categoryDialog.dismiss();
            }//onClick
        });
    }//closeButtonListener
}//CategoryButtonListener

