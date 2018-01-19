package com.example.augappprototype.Listeners;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.augappprototype.LoginScreen;
import com.example.augappprototype.MainActivity;
import com.example.augappprototype.MainMenu;
import com.example.augappprototype.R;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Peter on 1/18/2018.
 */

public class SignOutMainActivityListener implements View.OnClickListener {
    private MainActivity mainActivity;
    private GoogleApiClient googleApiClient;

    public SignOutMainActivityListener(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    } // SignOUtButtonListener(MainActivity)


    @Override
    public void onClick(View view) {
        signoutButtonListener();
    } // onClick(View)

    public void signoutButtonListener(){
        Button signout = mainActivity.findViewById(R.id.signOutButton);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToLogin = new Intent(mainActivity, LoginScreen.class);
                mainActivity.startActivity(goToLogin);
            }
        });
    } // signoutButtonListener()

} // SignOutMainActivityListener