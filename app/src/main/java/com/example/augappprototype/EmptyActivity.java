package com.example.augappprototype;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        SharedPreferences sharedPreferences = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("activity_executed", false)) {
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, LoginScreen.class);
            startActivity(intent);
            finish();
        }
    }
}
