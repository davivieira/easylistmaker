package com.dvsoft.shoppinglist.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.dvsoft.shoppinglist.R;
import com.dvsoft.shoppinglist.fragments.ItemsListFragment;
import com.dvsoft.shoppinglist.fragments.ItemsListsActionBarFragment;
import com.dvsoft.shoppinglist.fragments.MainActionBarFragment;
import com.dvsoft.shoppinglist.fragments.MainFragment;
import com.dvsoft.shoppinglist.util.MainActivityUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

import butterknife.ButterKnife;

/**
 * Created by davivieira on 25/03/15.
 */
public class MainActivity extends FragmentActivity {

    private FragmentTransaction fragmentTransaction;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new MainFragment());
        fragmentTransaction.replace(R.id.action_bars_container, new MainActionBarFragment());
        fragmentTransaction.commit();

        SharedPreferences ratingSettings = getSharedPreferences(this.getResources()
                .getString(R.string.rating_preferences), 0);
        boolean showRatingDialog = ratingSettings.getBoolean("showRatingDialog", true);

        if (showRatingDialog && new Random().nextInt(3) == 1) {
            new MainActivityUtil().openRatingDialog(this);
        }

    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }
}
