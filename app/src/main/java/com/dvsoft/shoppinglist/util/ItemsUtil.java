package com.dvsoft.shoppinglist.util;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;

import com.dvsoft.shoppinglist.R;
import com.dvsoft.shoppinglist.dialogs.AskForPriceDialog;
import com.dvsoft.shoppinglist.dialogs.ItemNameDialog;
import com.dvsoft.shoppinglist.fragments.ItemsListFragment;
import com.dvsoft.shoppinglist.fragments.ItemsListsActionBarFragment;
import com.dvsoft.shoppinglist.models.ItemModel;
import com.dvsoft.shoppinglist.repositories.ItemRepository;

/**
 * Created by davivieira on 05/04/15.
 */
public class ItemsUtil {

    private FragmentTransaction fragmentTransaction;
    private ItemRepository itemRepository;

    public void openItemsListFragment(FragmentActivity activity, Bundle bundle) {
        fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        ItemsListFragment itemsListFragment = new ItemsListFragment();
        ItemsListsActionBarFragment itemsListsActionBarFragment = new ItemsListsActionBarFragment();
        if (bundle != null) {
            itemsListFragment.setArguments(bundle);
            itemsListsActionBarFragment.setArguments(bundle);
        }

        fragmentTransaction.addToBackStack("main-fragment");
        fragmentTransaction.replace(R.id.fragment_container, itemsListFragment);
        fragmentTransaction.replace(R.id.action_bars_container, itemsListsActionBarFragment);
        fragmentTransaction.commit();
    }

    public void addItemOnList(FragmentActivity activity, Bundle bundle) {
        fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        ItemNameDialog itemNameDialog = new ItemNameDialog();
        if (bundle != null) {
            itemNameDialog.setArguments(bundle);
        }

        itemNameDialog.show(fragmentTransaction, "item-name-dialog");
    }

    public void refreshItemsListFragment(FragmentActivity activity, Bundle bundle) {
        fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

        ItemsListFragment itemsListFragment = new ItemsListFragment();

        if (bundle != null) {
            itemsListFragment.setArguments(bundle);
        }

        fragmentTransaction.remove(new ItemsListFragment());
        fragmentTransaction.replace(R.id.fragment_container, itemsListFragment);
        fragmentTransaction.commit();
    }

    public void refreshItemsListFragmentAfterDelete(FragmentActivity activity, Bundle bundle) {
        fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

        ItemsListFragment itemsListFragment = new ItemsListFragment();

        if (bundle != null) {
            itemsListFragment.setArguments(bundle);
        }

        fragmentTransaction.remove(new ItemsListFragment());
        fragmentTransaction.replace(R.id.fragment_container, itemsListFragment);
        fragmentTransaction.commit();
    }

    public void openAskForPriceDialog(FragmentActivity activity, Bundle bundle) {
        fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        AskForPriceDialog askForPriceDialog = new AskForPriceDialog();
        if (bundle != null) {
            askForPriceDialog.setArguments(bundle);
        }

        askForPriceDialog.show(fragmentTransaction, "ask-for-price-dialog");
    }

    public void saveItemPrice(Editable itemPriceText, ItemModel itemModelToEdit) {
        if (itemPriceText.toString().equals("")) {
            itemModelToEdit.setPrice(null);
        } else {
            itemModelToEdit.setPrice(Double.parseDouble(itemPriceText.toString()));
        }
        itemRepository = new ItemRepository();
        itemRepository.updateItemPrice(itemModelToEdit.getPrice(), itemModelToEdit.getId());
    }

    public void checkOrUncheckItem(ItemModel itemModel) {
        ItemRepository itemRepository = new ItemRepository();
        itemRepository.afterCheckUpdate(itemModel.getId(), itemModel.isChecked());
    }
}
