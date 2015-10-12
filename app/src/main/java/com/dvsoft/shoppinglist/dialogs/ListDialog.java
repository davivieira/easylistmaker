package com.dvsoft.shoppinglist.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.dvsoft.shoppinglist.R;
import com.dvsoft.shoppinglist.models.ListModel;
import com.dvsoft.shoppinglist.types.ListTypeEnum;
import com.dvsoft.shoppinglist.util.MainActivityUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by davivieira on 28/03/15.
 */
public class ListDialog extends DialogFragment {

    @InjectView(R.id.dialog_header)
    TextView dialogHeader;

    @InjectView(R.id.dialog_list_name)
    EditText listName;

    @InjectView(R.id.checkbox_can_repeat_items)
    CheckBox canRepeatItems;

    @InjectView(R.id.checkbox_ask_for_price)
    CheckBox askForPrice;

    private boolean isEditing = false;
    private ListModel listModelToEdit;
    private Fragment previousFragment;
    public ListDialog() {}

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_list, container, false);
        ButterKnife.inject(this, view);

        getBundleArguments();

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.cancel_dialog_list)
    public void cancelDialogEvent() {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        new MainActivityUtil().closeDialog(getActivity());
    }

    @OnClick(R.id.submit_dialog_list)
    public void submitListEvent() {
        validateAndSubmit();
    }

    private void validateAndSubmit() {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (previousFragment != null) {
            new MainActivityUtil().updateListAndDismissDialogOnItemsList(getActivity(), listName.getText(),
                    ListTypeEnum.LIST, askForPrice.isChecked(), canRepeatItems.isChecked(),
                    listModelToEdit.getId());
            listName.clearFocus();
        } else {
            if (isEditing) {
                new MainActivityUtil().updateListAndDismissDialog(getActivity(), listName.getText(),
                        ListTypeEnum.LIST, askForPrice.isChecked(), canRepeatItems.isChecked(),
                        listModelToEdit.getId());
            } else {
                new MainActivityUtil().saveListAndDismissDialog(getActivity(), listName.getText(),
                        ListTypeEnum.LIST, askForPrice.isChecked(), canRepeatItems.isChecked());
            }
        }
    }

    private void getBundleArguments() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            listModelToEdit = (ListModel) bundle.getSerializable("listModel");
            previousFragment = (Fragment) bundle.getSerializable("itemListFragment");

            String dialogTitle = getActivity().getResources().getString(R.string.dialog_list_title_edit);
            dialogHeader.setText(dialogTitle);

            listName.setText(listModelToEdit.getListName());
            canRepeatItems.setChecked(listModelToEdit.isCanRepeatItem());
            askForPrice.setChecked(listModelToEdit.getAskForPrice());

            isEditing = true;
        }
    }
}
