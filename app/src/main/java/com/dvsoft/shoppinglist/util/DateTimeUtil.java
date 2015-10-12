package com.dvsoft.shoppinglist.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.dvsoft.shoppinglist.BroadcastReceivers.ReminderReceiver;
import com.dvsoft.shoppinglist.models.ListModel;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by davivieira on 26/04/15.
 */
public class DateTimeUtil {

    public void setAlarm(FragmentActivity activity, DatePicker datePicker, TimePicker timePicker, ListModel list) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, datePicker.getYear());
        calendar.set(Calendar.MONTH, datePicker.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
        calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Long alertTime = calendar.getTimeInMillis();

        Intent reminderIntent = new Intent(activity, ReminderReceiver.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("listModel", list);
        reminderIntent.putExtras(bundle);

        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime,
                PendingIntent.getBroadcast(activity, Integer.parseInt(list.getId().toString()),
                reminderIntent, PendingIntent.FLAG_UPDATE_CURRENT));
    }
}
