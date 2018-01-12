package com.example.augappprototype.Listeners;

import android.view.View;
import android.widget.Toast;

import com.example.augappprototype.MainMenu;

/**
 * Created by Chase on 2018-01-10.
 */

public class OtherMainMenuButtonListeners implements View.OnClickListener{

    private final MainMenu mainMenu;
    public OtherMainMenuButtonListeners(MainMenu mainMenu){
        this.mainMenu = mainMenu;
    }
    @Override
    public void onClick(View v) {
        Toast.makeText(mainMenu, "COMING SOON!", Toast.LENGTH_LONG).show();
    }
}
