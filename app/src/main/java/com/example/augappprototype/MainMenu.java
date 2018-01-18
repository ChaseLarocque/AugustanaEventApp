package com.example.augappprototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.Toast;


import com.example.augappprototype.Listeners.EventCalendarListener;
import com.example.augappprototype.Listeners.OtherMainMenuButtonListeners;
import com.example.augappprototype.Listeners.SignOutButtonListener;

/**
 * MainMenu
 * extends AppCompatActivity
 * Responsible for registering all buttons on the main menu screen
 *
 * Methods:
 * onCreate(Bundle savedInstanceState)
 *      calls the registerMenuButtons method that sets on click listeners for all buttons in the
 *      main menu
 * registerMenuButtons()
 *      sets on click listeners for all buttons on the main menu
 */
public class MainMenu extends AppCompatActivity {

    MainMenu mainMenu;
    Bundle extras;

    /*--Methods--*/
    /**
     * onCreate(Bundle) --> void
     * Calls the registerMenuButtons method that sets on click listeners for all buttons in the
     * main menu
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        registerMenuButtons();

        extras = getIntent().getExtras();
        Toast.makeText(this, "Logged in As: "+extras.getString("com.example.augappprototype.userName"), Toast.LENGTH_LONG).show();

    }//onCreate




    /**
     * registerMenuButtons() --> void
     * Sets on click listeners for all the buttons that are in the main menu
     */
    public void registerMenuButtons() {
        findViewById(R.id.eventsCalendarImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainMenu = new MainMenu();
                Intent goToCalendar = new Intent(MainMenu.this, MainActivity.class);
                goToCalendar.putExtra("userName", extras.getString("com.example.augappprototype.userName"));
                startActivity(goToCalendar);
            }
        });

        findViewById(R.id.newsletterImage).setOnClickListener
                (new OtherMainMenuButtonListeners(this));
        findViewById(R.id.libraryImage).setOnClickListener(new OtherMainMenuButtonListeners(this));
        findViewById(R.id.websiteImage).setOnClickListener(new OtherMainMenuButtonListeners(this));
        findViewById(R.id.signout).setOnClickListener(new SignOutButtonListener(this));
    }//registerMenuButtons
}//MainMenu
