package com.example.womensafetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ShakeApplication extends AppCompatActivity {
    private SensorManager sm;
    private float acelVal;
    private float acelLast;
    private float shake;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_application);


        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
        mediaPlayer=MediaPlayer.create(this,R.raw.alarm);
    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            acelLast = acelVal;
            acelVal = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta=acelVal - acelLast;
            shake = shake * 0.9f + delta;

            if (shake > 12) {
                mediaPlayer.start();
                Toast toast = Toast.makeText(getApplicationContext(), "MOTION DETETED", Toast.LENGTH_LONG);
                toast.show();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
    public void stopbutton(View view)
    {
        mediaPlayer.stop();
    }
}