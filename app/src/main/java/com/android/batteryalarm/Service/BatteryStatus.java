package com.android.batteryalarm.Service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.android.batteryalarm.Extras.Utils;

/**
 * Created by HariNadar on 27/11/15.
 */
public class BatteryStatus extends Service {

    String tag = "BatteryStatus";
    int lastBatteryLevel = -1;

    @Override
    public void onCreate() {
        super.onCreate();
//        Toast.makeText(this, "Service created...", Toast.LENGTH_LONG).show();
//        Log.i(tag, "Service created...");
    }

    // Put this Code into your MainActivity
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent i) {

            if (Utils.isPhonePluggedIn(getBaseContext())) {
                int level = i.getIntExtra("level", 0);

                if (lastBatteryLevel > level) {
                    startForeground(Utils.NOTIFICATION_ID, Utils.generateNotification(c, "Battery level reduced!!", "The current level is " + level));
//                    Utils.generateNotification(c, "Battery level reduced!!", "The current level is " + level);

//                    if (!Utils.getNotificationRingtone(c).isPlaying())
//                        Utils.getNotificationRingtone(c).play();

                    lastBatteryLevel = level;
                } else if (lastBatteryLevel < level) {
                    startForeground(Utils.NOTIFICATION_ID, Utils.generateNotification(c, "Battery level increased!!", "The current level is " + level));
//                    Utils.generateNotification(c, "Battery level increased!!", "The current level is " + level);

//                    if (!Utils.getNotificationRingtone(c).isPlaying())
//                        Utils.getNotificationRingtone(c).play();

                    lastBatteryLevel = level;
                } else if (level == Utils.MAX_BATTERY_LEVEL) {
                    if (!Utils.getNotificationRingtone(c).isPlaying())
                        Utils.getNotificationRingtone(c).play();

                    lastBatteryLevel = level;
                } else {
                    startForeground(Utils.NOTIFICATION_ID, Utils.generateNotification(c, "Battery level increased!!", "The current level is " + level));
                    lastBatteryLevel = level;
                }
//                Toast.makeText(c, "Charging", Toast.LENGTH_LONG).show();
            } else {
                if (Utils.getNotificationRingtone(c).isPlaying())
                    Utils.getNotificationRingtone(c).stop();

                stopForeground(true);
//                Toast.makeText(c, "Not Charging", Toast.LENGTH_LONG).show();
            }
        }
    };

//    @Override
//    public void onStart(Intent intent, int startId) {
//        super.onStart(intent, startId);
//        Log.i(tag, "Service started...");
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Toast.makeText(this, "Service destroyed...", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
