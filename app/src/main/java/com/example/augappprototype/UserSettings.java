package com.example.augappprototype;

import android.content.SharedPreferences;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * Created by ZachyZachy7 on 2018-01-10.
 */

public class UserSettings {
    private static final String ATHLETICS_CATEGORY_CHECKED_TAG = "athleticsCategoryChecked";
    private static final String PERFORMANCE_CATEGORY_CHECKED_TAG = "performanceCategoryChecked";
    private static final String CLUB_CATEGORY_CHECKED_TAG = "clubCategoryChecked";
    private static final String RESEARCH_CATEGORY_CHECKED_TAG = "researchCategoryChecked";
    private static final String ASA_CATEGORY_CHECKED_TAG = "asaCategoryChecked";
    public boolean athleticsCategoryChecked = true;
    public boolean performanceCategoryChecked = true;
    public boolean clubCategoryChecked = true;
    public boolean researchCategoryChecked = true;
    public boolean asaCategoryChecked = true;
    private MainActivity mainActivity;
    public UserSettings(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    CheckBox athletics = (CheckBox) mainActivity.findViewById(R.id.athleticsCategory);
    CheckBox performance = (CheckBox) mainActivity.findViewById(R.id.performanceCategory);
    CheckBox club = (CheckBox) mainActivity.findViewById(R.id.clubCategory);
    CheckBox research = (CheckBox) mainActivity.findViewById(R.id.researchCategory);
    CheckBox asa = (CheckBox) mainActivity.findViewById(R.id.asaCategory);




    public void loadPreviousSettings(SharedPreferences preferences) {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(ATHLETICS_CATEGORY_CHECKED_TAG, asaCategoryChecked);
        editor.putBoolean(PERFORMANCE_CATEGORY_CHECKED_TAG, performanceCategoryChecked);
        editor.putBoolean(CLUB_CATEGORY_CHECKED_TAG, clubCategoryChecked);
        editor.putBoolean(RESEARCH_CATEGORY_CHECKED_TAG, researchCategoryChecked);
        editor.putBoolean(ASA_CATEGORY_CHECKED_TAG, asaCategoryChecked);
        editor.commit();
        athleticsCategoryChecked = preferences.getBoolean(ATHLETICS_CATEGORY_CHECKED_TAG, true);
        performanceCategoryChecked = preferences.getBoolean(PERFORMANCE_CATEGORY_CHECKED_TAG, true);
        clubCategoryChecked = preferences.getBoolean(CLUB_CATEGORY_CHECKED_TAG, true);
        researchCategoryChecked = preferences.getBoolean(RESEARCH_CATEGORY_CHECKED_TAG, true);
        asaCategoryChecked = preferences.getBoolean(ASA_CATEGORY_CHECKED_TAG, true);
        athletics.setChecked(athleticsCategoryChecked);
        athletics.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean(ATHLETICS_CATEGORY_CHECKED_TAG, isChecked);
                editor.commit();
            }
        });
    }//loadPreviousSettings
}//UserSettings
