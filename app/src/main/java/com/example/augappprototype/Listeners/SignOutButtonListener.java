package com.example.augappprototype.Listeners;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

import com.example.augappprototype.LoginScreen;
import com.example.augappprototype.MainMenu;
import com.example.augappprototype.R;

/**
 * Created by Pao on 1/12/2018.
 */

public class SignOutButtonListener implements View.OnClickListener {
    private MainMenu mainMenu;

    public SignOutButtonListener(MainMenu mainMenu){
        this.mainMenu = mainMenu;
    }


    @Override
    public void onClick(View view) {
        signoutButtonListener();
    }

    public void signoutButtonListener(){
        ImageButton signout = mainMenu.findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToLogin = new Intent(mainMenu, LoginScreen.class);
                mainMenu.startActivity(goToLogin);
            }
        });
    }
}
