package com.dvsoft.shoppinglist.util;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.dvsoft.shoppinglist.R;
import com.dvsoft.shoppinglist.dialogs.SharePreferencesDialog;
import com.dvsoft.shoppinglist.models.ItemModel;
import com.dvsoft.shoppinglist.models.ListModel;
import com.dvsoft.shoppinglist.repositories.ItemRepository;

import java.util.List;
import java.util.Map;

/**
 * Created by davivieira on 11/04/15.
 */
public class ShareUtil {

    private ItemRepository itemRepository = new ItemRepository();
    private FragmentTransaction fragmentTransaction;

    public void shareList(ListModel listModel, FragmentActivity activity) {

        Map<String, Boolean> preferences = new SharedPreferencesUtil(activity).getSharedPreferencesMap();
        Boolean showCheckboxes = preferences.get("showCheckboxes");
        Boolean showPrices = preferences.get("showPrices");
        Boolean showTotal = preferences.get("showTotal");
        Boolean showListName = preferences.get("showListName");

        List<ItemModel> itemsList = itemRepository.getItemsByList(listModel.getId());

        String contentToShare = "";
        if (showListName) {
            contentToShare += listModel.getListName() + ": \n";
        }

        if (showPrices) {
            double sum = 0;

            for (ItemModel item : itemsList) {
                if (item.isChecked()) {
                    if (item.getPrice() != null) {
                        contentToShare += (showCheckboxes ? "(x) " : "- " ) + item.getName() + " - $" + String.format("%.2f", item.getPrice()) + "\n";
                        sum += item.getPrice();
                    } else {
                        contentToShare += (showCheckboxes ? "(x) " : "- " ) + item.getName() +  "\n";
                    }
                } else {
                    contentToShare += (showCheckboxes ? "( ) " : "- " ) + item.getName() + "\n";
                }
            }

            if (showTotal) {
                contentToShare += "### TOTAL: $" + String.format("%.2f", sum) + " ###";
            }
        } else {
            for (ItemModel item : itemsList) {
                if (item.isChecked()) {
                    contentToShare += (showCheckboxes ? "(x) " : "- " ) + item.getName() + " \n";
                } else {
                    contentToShare += (showCheckboxes ? "( ) " : "- " ) + item.getName() + " \n";
                }

            }

        }

        contentToShare += "\n\n" + activity.getResources().getText(R.string.signature);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, contentToShare);
        sendIntent.setType("text/plain");
        activity.startActivity(Intent.createChooser(sendIntent, activity.getResources().getText(R.string.send_to)));
    }

    public void openShareConfigDialog(FragmentActivity activity, Bundle bundle) {
        fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        SharePreferencesDialog sharePreferencesDialog = new SharePreferencesDialog();
        if (bundle != null) {
            sharePreferencesDialog.setArguments(bundle);
        }

        sharePreferencesDialog.show(fragmentTransaction, "shareConfig-dialog");
    }

    public void closeShareConfigDialog(FragmentActivity activity) {
        fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        SharePreferencesDialog sharePreferencesDialog = (SharePreferencesDialog) activity.getSupportFragmentManager()
                .findFragmentByTag("shareConfig-dialog");


        if (sharePreferencesDialog != null) {
            sharePreferencesDialog.dismiss();
            fragmentTransaction.remove(sharePreferencesDialog);
            fragmentTransaction.commit();
        }
    }

    public void saveConfigAndShare(boolean checkbox, boolean title, boolean price, boolean total,
                                   FragmentActivity activity, ListModel listModel) {
        SharedPreferences settings = activity.getSharedPreferences(activity.getResources()
                .getString(R.string.share_preferences), 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putBoolean("showCheckboxes", checkbox);
        editor.putBoolean("showPrices", price);
        editor.putBoolean("showTotal", total);
        editor.putBoolean("showListName", title);

        editor.commit();

        shareList(listModel, activity);
    }

    public void saveConfig(boolean checkbox, boolean title, boolean price, boolean total,
                           FragmentActivity activity) {
        SharedPreferences settings = activity.getSharedPreferences(activity.getResources()
                .getString(R.string.share_preferences), 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putBoolean("showCheckboxes", checkbox);
        editor.putBoolean("showPrices", price);
        editor.putBoolean("showTotal", total);
        editor.putBoolean("showListName", title);

        editor.commit();
    }
}
