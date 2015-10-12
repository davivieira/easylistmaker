package com.dvsoft.shoppinglist.BroadcastReceivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.dvsoft.shoppinglist.R;
import com.dvsoft.shoppinglist.activities.MainActivity;
import com.dvsoft.shoppinglist.models.ListModel;

/**
 * Created by davivieira on 23/04/15.
 */
public class ReminderReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        ListModel listModel = (ListModel) bundle.getSerializable("listModel");

        createNotification(context, context.getResources().getString(R.string.alertMessageTitle),
                context.getResources().getString(R.string.alertMessage)+ " \"" + listModel.getListName() + "\"",
                context.getResources().getString(R.string.alert));
    }

    public void createNotification(Context context, String msgTitle, String msgText, String msgAlarm) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(msgTitle)
                .setTicker(msgAlarm)
                .setContentText(msgText);

        nBuilder.setContentIntent(pendingIntent);
        nBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        nBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, nBuilder.build());
    }
}
