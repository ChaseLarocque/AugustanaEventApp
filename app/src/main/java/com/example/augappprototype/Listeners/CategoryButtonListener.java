package com.example.augappprototype.Listeners;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;

import com.example.augappprototype.MainActivity;
import com.example.augappprototype.R;

/**
 * Created by ZachyZachy7 on 2018-01-08.
 */

public class CategoryButtonListener implements View.OnClickListener {
    private final MainActivity mainActivity;
    public CategoryButtonListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    @Override
    public void onClick(View v) {
        final Dialog categoryDialog = new Dialog(mainActivity);
        categoryDialog.setContentView(R.layout.categorypopup);
        categoryDialog.show();
        closeButtonListener(categoryDialog);
    }

    public void closeButtonListener(final Dialog categoryDialog) {
        Button closeButton = (Button) categoryDialog.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryDialog.dismiss();
            }
        });
    }
}

