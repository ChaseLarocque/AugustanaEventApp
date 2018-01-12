package com.example.augappprototype.Listeners;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

import com.example.augappprototype.LoginScreen;
import com.example.augappprototype.MainMenu;
import com.example.augappprototype.R;

/**
 * Created by Pao on 1/12/2018.
 * SignOutButtonListener
 * implements View.OnClickListener
 * Responsible for the events that occur when the sign out button is clicked
 *
 * Methods:
 * onClick(View v)
 *      on click will take the user back to the login screen
 */

public class SignOutButtonListener implements View.OnClickListener {
    /*--Data--*/
    private MainMenu mainMenu;

    /*--Constructor--*/
    public SignOutButtonListener(MainMenu mainMenu){
        this.mainMenu = mainMenu;
    }

    /*--Methods--*/
    @Override
    public void onClick(View view) {
        Intent goToLogin = new Intent(mainMenu, LoginScreen.class);
        mainMenu.startActivity(goToLogin);
    }//onClick
}//SignOutButtonListener
