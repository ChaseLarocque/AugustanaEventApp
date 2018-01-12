package com.example.augappprototype.Listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.augappprototype.MainActivity;
import com.example.augappprototype.MainMenu;

/**
 * Created by Chase on 2018-01-10.
 */

public class EventCalendarListener implements View.OnClickListener{

    private final MainMenu mainMenu;
    public EventCalendarListener(MainMenu mainMenu){
        this.mainMenu = mainMenu;
    }
    @Override
    public void onClick(View v) {
        Intent goToCalendar = new Intent(mainMenu, MainActivity.class);
        mainMenu.startActivity(goToCalendar);
    }
}
