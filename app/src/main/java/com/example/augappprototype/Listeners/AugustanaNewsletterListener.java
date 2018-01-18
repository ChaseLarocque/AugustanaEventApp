package com.example.augappprototype.Listeners;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.example.augappprototype.MainMenu;

/**
 * Created by Peter Zeng on 2018-01-17.
 */

public class AugustanaNewsletterListener implements View.OnClickListener{

    private final MainMenu mainMenu;

    public AugustanaNewsletterListener(MainMenu mainMenu){
        this.mainMenu = mainMenu;
    } // AugustanaNewsletterListener(MainMenu)

    @Override
    public void onClick(View v) {
        String url = "http://news.augustana.ualberta.ca/";

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        mainMenu.startActivity(intent);
    } // onClick(View)
} // AugustanaNewsletterListener
