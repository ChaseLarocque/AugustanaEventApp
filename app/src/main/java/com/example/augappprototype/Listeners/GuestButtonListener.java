package com.example.augappprototype.Listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.augappprototype.LoginScreen;
import com.example.augappprototype.MainMenu;
import com.example.augappprototype.MainActivity;
import com.example.augappprototype.R;

/**
 * Created by Pao on 1/7/2018.
 * GuestButtonListener
 * implements View.OnClickListener
 * Responsible for the events that occur when the guest login button is clicked
 *
 * Methods:
 * onClick(View v)
 *      Logs in user as a guest and brings them to the main menu
 */

public class GuestButtonListener extends MainActivity implements View.OnClickListener{
    /*--Data--*/
    public static boolean isGuest;
    private LoginScreen loginScreen;
    /*--Constructor--*/
    public GuestButtonListener(LoginScreen loginScreen){
        this.loginScreen = loginScreen;
    }//GuestLoginButtonListener


    /*--Methods--*/
    /**
     * Logs the user in as a guest meaning they can view the calendar, and add events to their local
     * device, but cannot edit any events that are already there. Takes user to main menu
     * @param v
     */
    @Override
    public void onClick(View v) {
        isGuest = true;
        Toast.makeText(loginScreen, "Logged in as a guest",
                Toast.LENGTH_LONG).show();
        Intent goToMainMenu = new Intent(loginScreen, MainMenu.class);
        loginScreen.startActivity(goToMainMenu);
    }




}
