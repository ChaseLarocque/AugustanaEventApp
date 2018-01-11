package com.example.augappprototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.augappprototype.Listeners.EventCalendarListener;
import com.example.augappprototype.Listeners.OtherMainMenuButtonListeners;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        findViewById(R.id.eventsCalendarImage).setOnClickListener(new EventCalendarListener(this));
        findViewById(R.id.newsletterImage).setOnClickListener(new OtherMainMenuButtonListeners(this));
        findViewById(R.id.libraryImage).setOnClickListener(new OtherMainMenuButtonListeners(this));
        findViewById(R.id.websiteImage).setOnClickListener(new OtherMainMenuButtonListeners(this));
        signoutButtonListener();
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
