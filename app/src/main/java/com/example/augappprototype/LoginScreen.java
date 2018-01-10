package com.example.augappprototype;

/**
 *
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.augappprototype.Listeners.GuestButtonListener;
import com.example.augappprototype.Listeners.LoginButtonListener;

public class LoginScreen extends AppCompatActivity {

    /*--Methods--*/
    /**
     * onCreate(Bundle savedInstanceState) --> void
     * Calls the registerListenersForLoginScreenButtons method so there is a new on click listener
     * for them on creation
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        registerListenersForLoginScreenButtons();
    }//onCreate

    /**
     * registerListenersForLoginScreenButtons() --> void
     * Sets on click listeners for the Login button and the Guest Login Button on the Login screen
     */
    public void registerListenersForLoginScreenButtons() {
        findViewById(R.id.loginButton).setOnClickListener(new LoginButtonListener(this));
        findViewById(R.id.continueAsGuestButton).setOnClickListener
                (new GuestButtonListener(this));
    }//registerListenersForLoginScreenButtons
}//LoginScreen
