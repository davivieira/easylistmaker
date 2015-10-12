package com.dvsoft.shoppinglist.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dvsoft.shoppinglist.R;
import com.dvsoft.shoppinglist.models.ListModel;
import com.dvsoft.shoppinglist.util.DateTimeUtil;
import com.dvsoft.shoppinglist.util.MainActivityUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by davivieira on 25/04/15.
 */
public class ReminderDialog extends DialogFragment {

    @InjectView(R.id.reminder_datePicker)
    DatePicker datePicker;

    @InjectView(R.id.reminder_timePicker)
    TimePicker timePicker;

    ListModel listModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_reminder, container, false);
        ButterKnife.inject(this, view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            listModel = (ListModel) bundle.getSerializable("listModel");
        }

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @OnClick(R.id.confirm_reminder)
    public void onClickConfirmReminder() {
        new DateTimeUtil().setAlarm(getActivity(), datePicker, timePicker, listModel);
        new MainActivityUtil().closeReminderDialog(getActivity());
        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.reminder_set), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.cancel_reminder)
    public void onClickCancelReminder() {
        new MainActivityUtil().closeReminderDialog(getActivity());
    }
}
