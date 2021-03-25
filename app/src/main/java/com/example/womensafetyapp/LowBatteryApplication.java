package com.example.womensafetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class LowBatteryApplication extends AppCompatActivity {
    private TextView textView;
    private Ringtone ringtone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_low_battery_application);

        textView = findViewById(R.id.textView);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ringtone = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        BroadcastReceiver broadcastReceiverBatteryLevel = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Integer integerbatterylevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                textView.setText(integerbatterylevel.toString());
                if (integerbatterylevel >47) {
                    ringtone.play();
                    Toast.makeText(LowBatteryApplication.this, "BATTERY LESS THAN 20 %", Toast.LENGTH_SHORT).show();

                }


            }
        };
        registerReceiver(broadcastReceiverBatteryLevel, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    public void stopbutton(View view)
    {
        ringtone.stop();
    }
}