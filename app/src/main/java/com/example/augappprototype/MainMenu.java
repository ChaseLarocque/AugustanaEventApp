package com.example.augappprototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.augappprototype.Listeners.EventCalendarListener;
import com.example.augappprototype.Listeners.OtherMainMenuButtonListeners;

/**
 * MainMenu
 * extends AppCompatActivity
 * Responsible for all buttons and text on the main menu screen
 *
 * Methods:
 * onCreate(Bundle savedInstanceState)
 *      calls the registerMenuButtons method to set on click listeners for all buttons on the main
 *      menu
 *
 */
public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        signoutButtonListener();
        registerMenuButtons();
    }//onCreate

    public void registerMenuButtons() {
        findViewById(R.id.eventsCalendarImage).setOnClickListener(new EventCalendarListener(this));
        findViewById(R.id.newsletterImage).setOnClickListener(new OtherMainMenuButtonListeners(this));
        findViewById(R.id.libraryImage).setOnClickListener(new OtherMainMenuButtonListeners(this));
        findViewById(R.id.websiteImage).setOnClickListener(new OtherMainMenuButtonListeners(this));
    }

    public void signoutButtonListener(){
        ImageButton signout = findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToLogin = new Intent(MainMenu.this, LoginScreen.class);
                startActivity(goToLogin);
            }
        });
    }
}
