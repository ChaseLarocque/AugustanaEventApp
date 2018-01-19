package com.example.augappprototype.Listeners;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setCancelable(true);
        builder.setTitle("Signing out");
        builder.setMessage("Are you sure you want to sign out?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent goToLogin = new Intent(mainActivity, LoginScreen.class);
                        mainActivity.startActivity(goToLogin);
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    } // onClick(View)

} // SignOutMainActivityListener