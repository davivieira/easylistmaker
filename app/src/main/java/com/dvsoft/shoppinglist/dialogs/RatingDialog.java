package com.dvsoft.shoppinglist.dialogs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.dvsoft.shoppinglist.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by davivieira on 01/05/15.
 */
public class RatingDialog extends DialogFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_rating, container, false);
        ButterKnife.inject(this, view);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @OnClick(R.id.rate)
    public void onClickRateButton() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getActivity().getPackageName()));

        getActivity().startActivity(intent);

        SharedPreferences settings = getActivity().getSharedPreferences(getActivity().getResources().getString(R.string.rating_preferences), 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("showRatingDialog", false);
        editor.commit();

        getDialog().dismiss();
    }

    @OnClick(R.id.not_now)
    public void onClickNotNowButton() {
        getDialog().dismiss();
    }

    @OnClick(R.id.never)
    public void onClickNeverButton() {
        SharedPreferences settings = getActivity().getSharedPreferences(getActivity().getResources().getString(R.string.rating_preferences), 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("showRatingDialog", false);
        editor.commit();

        getDialog().dismiss();
    }
}
