package com.example.augappprototype.Listeners;

import android.content.Intent;
import android.view.View;
import com.example.augappprototype.MainActivity;
import com.example.augappprototype.MainMenu;

/**
 * Created by ZachyZachy7 on 2018-01-12.
 * BackButtonListener
 * implements View.OnClickListener
 * Responsible for the events that occur when the back button is clicked
 *
 * Methods:
 * onClick(View v)
 *      on click will take the user back to the main menu screen
 */
public class BackButtonListener implements View.OnClickListener {
    /*--Data--*/
    private MainActivity mainActivity;

    /*--Constructor--*/
    public BackButtonListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }//BackButtonListener

    /*--Methods--*/
    @Override
    public void onClick(View v) {
        Intent goToMainMenu = new Intent(mainActivity, MainMenu.class);
        mainActivity.startActivity(goToMainMenu);
    }//onClick
}//BackButtonListener
