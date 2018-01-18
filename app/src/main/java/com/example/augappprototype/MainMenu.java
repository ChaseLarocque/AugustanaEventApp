package com.example.augappprototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.augappprototype.Listeners.AugustanaLibraryListener;
import com.example.augappprototype.Listeners.AugustanaNewsletterListener;
import com.example.augappprototype.Listeners.AugustanaWebsiteListener;
import com.example.augappprototype.Listeners.EventCalendarListener;
import com.example.augappprototype.Listeners.SignOutButtonListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class MainMenu extends AppCompatActivity {

    GoogleSignInAccount account;

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

        account = GoogleSignIn.getLastSignedInAccount(this);

    } // onCreate(Bundle)

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    } // onBackPressed()

} // MainMenu
