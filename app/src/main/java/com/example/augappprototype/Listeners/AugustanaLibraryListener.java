package com.example.augappprototype.Listeners;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.example.augappprototype.MainMenu;

/**
 * Created by Peter Zeng on 2018-01-17.
 */

public class AugustanaLibraryListener implements View.OnClickListener{

    private final MainMenu mainMenu;

    public AugustanaLibraryListener(MainMenu mainMenu){
        this.mainMenu = mainMenu;
    } // AugustanaLibraryListener(MainMenu)

    @Override
    public void onClick(View v) {
        String url = "https://www.library.ualberta.ca/augustana";

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        mainMenu.startActivity(intent);
    } // onClick(View)
} // AugustanaLibraryListener
