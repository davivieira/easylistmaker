package com.dvsoft.shoppinglist.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;

import com.dvsoft.shoppinglist.R;
import com.dvsoft.shoppinglist.models.ListModel;
import com.dvsoft.shoppinglist.util.ShareUtil;
import com.dvsoft.shoppinglist.util.SharedPreferencesUtil;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by davivieira on 21/04/15.
 */
public class SharePreferencesDialog extends DialogFragment {

    @InjectView(R.id.show_checkboxes)
    CheckBox showCheckboxes;

    @InjectView(R.id.show_prices)
    CheckBox showPrices;

    @InjectView(R.id.show_title)
    CheckBox showListTitle;

    @InjectView(R.id.show_total)
    CheckBox showTotal;

    Bundle bundle;
    ListModel listModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_share_config, container, false);
        ButterKnife.inject(this, view);

        bundle = this.getArguments();
        if (bundle != null) {
            listModel = (ListModel) bundle.getSerializable("listModel");
        }

        Map<String, Boolean> preferences = new SharedPreferencesUtil(getActivity()).getSharedPreferencesMap();
        Boolean sCheckboxes = preferences.get("showCheckboxes");
        Boolean sPrices = preferences.get("showPrices");
        Boolean sTotal = preferences.get("showTotal");
        Boolean sListName = preferences.get("showListName");

        showCheckboxes.setChecked(sCheckboxes);
        showPrices.setChecked(sPrices);
        showTotal.setChecked(sTotal);
        showListTitle.setChecked(sListName);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @OnClick(R.id.save_share_config)
    public void onClickSaveShareConfig() {
        new ShareUtil().saveConfig(showCheckboxes.isChecked(), showListTitle.isChecked(),
                showPrices.isChecked(), showTotal.isChecked(), getActivity());
        new ShareUtil().closeShareConfigDialog(getActivity());
    }

    @OnClick(R.id.save_and_share_config)
    public void onClickSaveAndShareConfig() {
        new ShareUtil().saveConfigAndShare(showCheckboxes.isChecked(), showListTitle.isChecked(),
                showPrices.isChecked(), showTotal.isChecked(), getActivity(), listModel);
        new ShareUtil().closeShareConfigDialog(getActivity());
    }

    @OnClick(R.id.cancel_share_config)
    public void onClickCancelShareConfig() {
        new ShareUtil().closeShareConfigDialog(getActivity());
    }
}
