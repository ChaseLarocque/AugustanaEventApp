package com.example.augappprototype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.augappprototype.Listeners.AugustanaLibraryListener;
import com.example.augappprototype.Listeners.AugustanaNewsletterListener;
import com.example.augappprototype.Listeners.AugustanaWebsiteListener;
import com.example.augappprototype.Listeners.EventCalendarListener;
import com.example.augappprototype.Listeners.SignOutButtonListener;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        findViewById(R.id.eventsCalendarImage)
                .setOnClickListener(new EventCalendarListener(this));
        findViewById(R.id.newsletterImage)
                .setOnClickListener(new AugustanaNewsletterListener(this));
        findViewById(R.id.libraryImage)
                .setOnClickListener(new AugustanaLibraryListener(this));
        findViewById(R.id.websiteImage)
                .setOnClickListener(new AugustanaWebsiteListener(this));
        findViewById(R.id.signout)
                .setOnClickListener(new SignOutButtonListener(this));
    } // onCreate(Bundle)

    @Override
    public void onBackPressed() {

    } // onBackPressed()

} // MainMenu
