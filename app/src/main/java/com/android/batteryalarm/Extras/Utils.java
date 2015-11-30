package com.android.batteryalarm.Extras;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.support.v4.app.NotificationCompat;

import com.android.batteryalarm.MainActivity;
import com.android.batteryalarm.R;

public class Utils {


    public static int MAX_BATTERY_LEVEL = 100;
    public static int NOTIFICATION_ID = 2601;

    @SuppressWarnings("static-access")
    public static Notification generateNotification(Context context, String strTitle, String strMessage) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(strTitle)
                        .setAutoCancel(true)
                        .setOngoing(true)
                        .setContentText(strMessage);
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, MainActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
//        mNotificationManager.notify(0, mBuilder.build());
        return mBuilder.build();
    }

    public static boolean isPhonePluggedIn(Context context) {
        boolean charging = false;

        final Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean batteryCharge = status == BatteryManager.BATTERY_STATUS_CHARGING;

        int chargePlug = batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

        if (batteryCharge) charging = true;
        if (usbCharge) charging = true;
        if (acCharge) charging = true;

        return charging;
    }

    /*public static void playAlarmSound(Context context) {
        ringtone.play();
    }

    public static void stopAlarmSound(Context context){
        getNotificationRingtone(context).st
    }*/

    public static Ringtone getNotificationRingtone(Context context){
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone ringtone = RingtoneManager.getRingtone(context, notification);
        return ringtone;
    }

}