package com.example.augappprototype;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * EmptyActivity
 * extends AppCompatActivity
 * Responsible for saving the users login such that once a user logs in it will take them straight
 * to the main menu when they reopen the app
 *
 * Methods:
 * onCreate(Bundle savedInstanceState)
 *      Uses shared preferences to save the users login
 */
public class EmptyActivity extends AppCompatActivity {

    /*--Methods--*/

    /**
     * onCreate(Bundle) --> void
     * uses shared preferences to remember if a user has logged in or not. If the user has logged in
     * they will be taken straight to the main menu when opening the app, but if they have not
     * logged in, then they will be taken to the login screen.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        SharedPreferences sharedPreferences = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("activity_executed", false)) {
            Intent intent = new Intent(this, MainMenu.class);//start app on main menu
            startActivity(intent);
            finish();
        }//if
        else {
            Intent intent = new Intent(this, LoginScreen.class);//start app on login
            startActivity(intent);
            finish();
        }//else
    }//onCreate
}//EmptyActivity
