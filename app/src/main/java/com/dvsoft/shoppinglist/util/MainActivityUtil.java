package com.dvsoft.shoppinglist.util;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.widget.Toast;

import com.dvsoft.shoppinglist.R;
import com.dvsoft.shoppinglist.dialogs.ListDialog;
import com.dvsoft.shoppinglist.dialogs.RatingDialog;
import com.dvsoft.shoppinglist.dialogs.ReminderDialog;
import com.dvsoft.shoppinglist.fragments.MainFragment;
import com.dvsoft.shoppinglist.repositories.ListRepository;
import com.dvsoft.shoppinglist.types.ListTypeEnum;

/**
 * Created by davivieira on 31/03/15.
 */
public class MainActivityUtil {

    FragmentTransaction fragmentTransaction;

    public void openListDialogFragment(FragmentActivity activity, Bundle bundle) {
        fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        ListDialog listDialog = new ListDialog();
        if (bundle != null) {
            listDialog.setArguments(bundle);
        }

        listDialog.show(fragmentTransaction, "list-dialog");

    }

    public void openReminderDialogFragment(FragmentActivity activity, Bundle bundle) {
        fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        ReminderDialog reminderDialog = new ReminderDialog();
        if (bundle != null) {
            reminderDialog.setArguments(bundle);
        }

        reminderDialog.show(fragmentTransaction, "reminder-dialog");
    }

    public void openRatingDialog(FragmentActivity activity) {
        fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        RatingDialog ratingDialog = new RatingDialog();

        ratingDialog.show(fragmentTransaction, "rating-dialog");
    }

    public void saveListAndDismissDialog(FragmentActivity activity, Editable listName, ListTypeEnum listType,
                                         boolean askForPrice, boolean canRepeatItems) {
        String testListName = listName.toString();

        if (testListName != null && testListName.trim().length() > 0) {
            ListRepository listRepository = new ListRepository();
            listRepository.create(listName.toString(), listType, askForPrice, canRepeatItems);

            closeDialog(activity);
            refreshCurrentFragment(activity);
        } else {
            Toast.makeText(activity, R.string.list_name_alert, Toast.LENGTH_SHORT).show();
        }
    }

    public void updateListAndDismissDialog(FragmentActivity activity, Editable listName, ListTypeEnum listType,
                                           boolean askForPrice, boolean canRepeatItem, Long id) {
        String testListName = listName.toString();

        if (testListName != null && testListName.trim().length() > 0) {
            ListRepository listRepository = new ListRepository();
            listRepository.update(listName.toString(), listType, askForPrice, canRepeatItem, id);

            closeDialog(activity);
            refreshCurrentFragment(activity);
        } else {
            Toast.makeText(activity, R.string.list_name_alert, Toast.LENGTH_SHORT).show();
        }
    }

    public void refreshCurrentFragment(FragmentActivity activity) {
        fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(new MainFragment());
        fragmentTransaction.replace(R.id.fragment_container, new MainFragment());

        fragmentTransaction.commit();
    }

    public void closeDialog(FragmentActivity activity) {
        fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        ListDialog listDialog = (ListDialog) activity.getSupportFragmentManager().findFragmentByTag("list-dialog");


        if (listDialog != null) {
            listDialog.dismiss();
            fragmentTransaction.remove(listDialog);
            fragmentTransaction.commit();
        }
    }

    public void closeReminderDialog(FragmentActivity activity) {
        fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        ReminderDialog reminderDialog = (ReminderDialog) activity.getSupportFragmentManager().findFragmentByTag("reminder-dialog");

        if (reminderDialog != null) {
            reminderDialog.dismiss();
            fragmentTransaction.remove(reminderDialog);
            fragmentTransaction.commit();
        }
    }

    public void updateListAndDismissDialogOnItemsList(FragmentActivity activity, Editable listName,
                                                      ListTypeEnum listType, boolean askForPrice, boolean canRepeatItem,
                                                      Long id) {

        String testListName = listName.toString();

        if (testListName != null && testListName.trim().length() > 0) {
            ListRepository listRepository = new ListRepository();
            listRepository.update(listName.toString(), listType, askForPrice, canRepeatItem, id);

            Toast.makeText(activity, R.string.list_updated_alert, Toast.LENGTH_SHORT).show();
            closeDialog(activity);
        } else {
            Toast.makeText(activity, R.string.list_name_alert, Toast.LENGTH_SHORT).show();
        }
    }
}
