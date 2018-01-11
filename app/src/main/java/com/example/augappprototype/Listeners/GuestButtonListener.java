package com.example.augappprototype.Listeners;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.augappprototype.LoginScreen;
import com.example.augappprototype.MainActivity;

/**
 * Created by Pao on 1/7/2018.
 */

public class GuestButtonListener implements View.OnClickListener{

    private final LoginScreen loginScreen;
    public GuestButtonListener(LoginScreen loginScreen){
        this.loginScreen = loginScreen;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(loginScreen, "Logged in as a guest",
                Toast.LENGTH_LONG).show();
        Intent k = new Intent(loginScreen, MainActivity.class);
        loginScreen.startActivity(k);
    }


}
