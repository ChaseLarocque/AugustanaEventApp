package com.example.augappprototype.Listeners;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.augappprototype.LoginScreen;
import com.example.augappprototype.MainActivity;
import com.example.augappprototype.R;

import java.util.HashMap;

/**
 * Created by Pao on 1/7/2018.
 */

public class LoginButtonListener implements View.OnClickListener{
    HashMap<String, String> permissions = new HashMap<>();
    HashMap<String, String> passwords = new HashMap<>();
    private final LoginScreen loginScreen;
    public LoginButtonListener(LoginScreen loginScreen){
        this.loginScreen = loginScreen;
    }


    @Override
    public void onClick(View v) {
        addName();
        checkNameInDatabase();

    }

    public void addName(){
        permissions.put("paolo", "faculty");
        permissions.put("zach", "student");
        passwords.put("paolo", "123");
        passwords.put("zach", "123");
    }

    public void confirmLogin(String username, String password){
        if (password.equals(passwords.get(username))){
            Intent k = new Intent(loginScreen, MainActivity.class);
            loginScreen.startActivity(k);
            if (permissions.get(username).equals("faculty"))
                Toast.makeText(loginScreen, "Logged in as a faculty member",
                        Toast.LENGTH_LONG).show();
            else
                Toast.makeText(loginScreen, "Logged in as a student",
                        Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(loginScreen, "Login Failed!",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void checkNameInDatabase(){
        EditText username = loginScreen.findViewById(R.id.userEmail);
        EditText password = loginScreen.findViewById(R.id.userPassword);
        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();
        confirmLogin(usernameString, passwordString);
    }
}
