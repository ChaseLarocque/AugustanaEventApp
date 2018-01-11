package com.example.augappprototype;

import android.app.Dialog;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.augappprototype.Listeners.AddEventListener;
import com.example.augappprototype.Listeners.CalendarButtonListener;
import com.example.augappprototype.Listeners.CategoryButtonListener;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.addEventButton).setOnClickListener(new AddEventListener(this));
        findViewById(R.id.categoryButton).setOnClickListener(new CategoryButtonListener(this));
        convertCalendar();

    }

    private void convertCalendar() {

        CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);


        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();


        Date firstDate1 = new Date(1514790000000L);
        Date firstDate2 = new Date(1546300800000L);
        caldroidFragment.setMinDate(firstDate1);
        caldroidFragment.setMaxDate(firstDate2);

        caldroidFragment.setCaldroidListener(new CalendarButtonListener(this));

    }
}