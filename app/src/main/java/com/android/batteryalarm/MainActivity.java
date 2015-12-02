package com.android.batteryalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.batteryalarm.Custom.WaterWaveProgress;

public class MainActivity extends AppCompatActivity {

    private WaterWaveProgress waveProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(getBaseContext(), BatteryStatus.class));

        registerReceiver(mBatInfoReceiver, new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED));

        waveProgress = (WaterWaveProgress) findViewById(R.id.waterWaveProgress1);
        waveProgress.setShowProgress(false);
        waveProgress.animateWave();

        /*AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getBaseContext(), ServiceHandler.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        am.cancel(pendingIntent);
        am.set(AlarmManager.RTC, System.currentTimeMillis(), pendingIntent);*/

    }

    // Put this Code into your MainActivity
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent i) {
            int level = i.getIntExtra("level", 0);
            ProgressBar pb = (ProgressBar) findViewById(R.id.progressbar);
            pb.setProgress(level);
            TextView tv = (TextView) findViewById(R.id.txtBatteryLevel);
            tv.setText("Battery Level: " + Integer.toString(level) + "%");
            waveProgress.setProgress(level);
        }
    };

}
