package com.example.augappprototype.Listeners;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;

import com.example.augappprototype.LoginScreen;
import com.example.augappprototype.MainMenu;
import com.example.augappprototype.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * Created by Pao on 1/12/2018.
 */

public class SignOutButtonListener implements View.OnClickListener {
    private MainMenu mainMenu;
    private GoogleApiClient googleApiClient;

    public SignOutButtonListener(MainMenu mainMenu){
        this.mainMenu = mainMenu;
    } // SignOUtButtonListener(MainMenu)


    @Override
    public void onClick(View view) {
        signoutButtonListener();
    } // onClick(View)

    public void signoutButtonListener(){
        ImageButton signout = mainMenu.findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToLogin = new Intent(mainMenu, LoginScreen.class);
                mainMenu.startActivity(goToLogin);
            }
        });
    } // signoutButtonListener()

} // SignOutButtonListener
