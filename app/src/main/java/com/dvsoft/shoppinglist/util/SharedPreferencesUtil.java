package com.dvsoft.shoppinglist.util;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

import com.dvsoft.shoppinglist.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by davivieira on 19/04/15.
 */
public class SharedPreferencesUtil {

    private FragmentActivity fragmentActivity;

    public SharedPreferencesUtil(FragmentActivity activity) {
        this.fragmentActivity = activity;
    }

    public Map<String, Boolean> getSharedPreferencesMap() {
        Map<String, Boolean> preferences = new HashMap<>();
        String preferencesName = fragmentActivity.getResources().getString(R.string.share_preferences);

        SharedPreferences sharedPreferences = fragmentActivity.getSharedPreferences(preferencesName, 0);
        boolean showCheckboxes = sharedPreferences.getBoolean("showCheckboxes", false);
        boolean showPrices = sharedPreferences.getBoolean("showPrices", false);
        boolean showTotal = sharedPreferences.getBoolean("showTotal", false);
        boolean showListName = sharedPreferences.getBoolean("showListName", false);

        preferences.put("showCheckboxes", showCheckboxes);
        preferences.put("showPrices", showPrices);
        preferences.put("showTotal", showTotal);
        preferences.put("showListName", showListName);

        return preferences;
    }
}
