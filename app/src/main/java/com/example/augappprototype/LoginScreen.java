package com.example.augappprototype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.augappprototype.Listeners.GuestButtonListener;
import com.example.augappprototype.Listeners.LoginButtonListener;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        findViewById(R.id.loginButton).setOnClickListener(new LoginButtonListener(this));
        findViewById(R.id.continueAsGuestButton).setOnClickListener(new GuestButtonListener(this));
    }
}
