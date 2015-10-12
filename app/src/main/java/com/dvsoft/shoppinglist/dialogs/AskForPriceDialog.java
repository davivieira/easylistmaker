package com.dvsoft.shoppinglist.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.dvsoft.shoppinglist.R;
import com.dvsoft.shoppinglist.models.ItemModel;
import com.dvsoft.shoppinglist.models.ListModel;
import com.dvsoft.shoppinglist.util.ItemsUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by davivieira on 06/04/15.
 */
public class AskForPriceDialog extends DialogFragment {

    @InjectView(R.id.item_price)
    EditText itemPrice;

    FragmentTransaction fragmentTransaction;
    ListModel listModelToEdit;
    ItemModel itemModelToEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_ask_for_price, container, false);
        ButterKnife.inject(this, view);
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            listModelToEdit = (ListModel) bundle.getSerializable("listModel");
            itemModelToEdit = (ItemModel) bundle.getSerializable("itemModel");

            if (itemModelToEdit.getPrice() != null) {
                String price = String.format("%.2f", itemModelToEdit.getPrice());
                price = price.replace(",", ".");
                itemPrice.setText(price);
            }
        }

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(R.string.dialog_ask_for_price_title);
        return dialog;
    }

    @OnClick(R.id.cancel_price_dialog)
    public void onCancelPriceDialog() {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        closeDialog();
    }

    @OnClick(R.id.confirm_item_price)
    public void onConfirmItemPrice() {
        new ItemsUtil().saveItemPrice(itemPrice.getText(), itemModelToEdit);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        closeDialog();

        Bundle bundle = new Bundle();
        bundle.putSerializable("listModel", listModelToEdit);
        new ItemsUtil().refreshItemsListFragment(getActivity(), bundle);
    }

    private void closeDialog() {
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        this.dismiss();
        fragmentTransaction.remove(this);
    }
}
