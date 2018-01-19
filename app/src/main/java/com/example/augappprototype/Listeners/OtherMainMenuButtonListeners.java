package com.example.augappprototype.Listeners;

import android.view.View;
import android.widget.Toast;

import com.example.augappprototype.MainMenu;

/**
 * Created by Chase on 2018-01-10.
 * OtherMainMenuButtonsListener
 * implements View.OnClickListener
 * Responsible for the events that occur when the other main menu buttons are clicked.
 * Does not include the sign out button or the event calendar button.
 *
 * Methods:
 * onClick(View v)
 *      Shows a toast message that displays coming soon to the user
 */

public class OtherMainMenuButtonListeners implements View.OnClickListener{
    /*--Data--*/
    private final MainMenu mainMenu;

    /*--Constructor--*/
    public OtherMainMenuButtonListeners(MainMenu mainMenu){
        this.mainMenu = mainMenu;
    }//OtherMainMenuButtons

    /*--Methods--*/
    /**
     * onClick(View) --> void
     * Displays a coming soon toast message to the user when clicked
     * @param v
     */
    @Override
    public void onClick(View v) {
        Toast.makeText(mainMenu, "COMING SOON!", Toast.LENGTH_LONG).show();
    }//onClick
}//OtherMainMenuButtons
