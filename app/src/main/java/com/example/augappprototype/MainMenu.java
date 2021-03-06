package com.example.augappprototype;
import android.content.Intent;
import android.view.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Toast;

import com.example.augappprototype.Listeners.AugustanaLibraryListener;
import com.example.augappprototype.Listeners.AugustanaNewsletterListener;
import com.example.augappprototype.Listeners.AugustanaWebsiteListener;
import com.example.augappprototype.Listeners.SignOutButtonListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

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
 * onBackPressed()
 *      Override's method so that the back button cannot be pressed
 */
public class MainMenu extends AppCompatActivity {

    MainMenu mainMenu;
    Bundle extras;
    GoogleSignInAccount account;

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
        account = GoogleSignIn.getLastSignedInAccount(this);

        registerMenuButtons();

        findViewById(R.id.newsletterImage)
                .setOnClickListener(new AugustanaNewsletterListener(this));
        findViewById(R.id.libraryImage)
                .setOnClickListener(new AugustanaLibraryListener(this));
        findViewById(R.id.websiteImage)
                .setOnClickListener(new AugustanaWebsiteListener(this));
        findViewById(R.id.signout)
                .setOnClickListener(new SignOutButtonListener(this));

        extras = getIntent().getExtras();
        Toast.makeText(this, "Logged in As:  "+extras.getString("com.example.augappprototype.userName"),
                Toast.LENGTH_LONG).show();
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
                goToCalendar.putExtra("editCalendar", extras.getBoolean("canEditCalendar"));
                startActivity(goToCalendar);
            }//onClick
        });//findViewById

        findViewById(R.id.newsletterImage)
                .setOnClickListener(new AugustanaNewsletterListener(this));
        findViewById(R.id.libraryImage)
                .setOnClickListener(new AugustanaLibraryListener(this));
        findViewById(R.id.websiteImage)
                .setOnClickListener(new AugustanaWebsiteListener(this));
        findViewById(R.id.signout)
                .setOnClickListener(new SignOutButtonListener(this));
    }//registerMenuButtons

    /**
     * Method here so the back button cannot be pressed
     */
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    } // onBackPressed()

} // MainMenu
