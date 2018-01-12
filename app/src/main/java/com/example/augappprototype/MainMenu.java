package com.example.augappprototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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
 *      calls the registerMenuButtons method that sets on click listeners for all buttons on the
 *      main menu
 * registerMenuButtons()
 *      sets on click listeners for all buttons on the main menu
 */
public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        registerMenuButtons();
    }//onCreate

    public void registerMenuButtons() {
        findViewById(R.id.eventsCalendarImage).setOnClickListener
                (new EventCalendarListener(this));
        findViewById(R.id.newsletterImage).setOnClickListener
                (new OtherMainMenuButtonListeners(this));
        findViewById(R.id.libraryImage).setOnClickListener(new OtherMainMenuButtonListeners(this));
        findViewById(R.id.websiteImage).setOnClickListener(new OtherMainMenuButtonListeners(this));
        findViewById(R.id.signout).setOnClickListener(new SignOutButtonListener(this));
    }//registerMenuButtons
}//MainMenu
