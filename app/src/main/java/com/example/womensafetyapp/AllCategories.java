package com.example.womensafetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import static android.net.Uri.parse;

public class AllCategories extends AppCompatActivity {
ImageView back_c;
Button calling,lowbattery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);
        back_c=findViewById(R.id.back_c);
        calling=findViewById(R.id.calling);
        lowbattery=findViewById(R.id.lowbattery);
        back_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllCategories.super.onBackPressed();
            }
        });

    }
    public void calling (View view){
        Intent intent= new Intent(getApplicationContext(),CallingApplication.class);
        startActivity(intent);

    }
    public void lowbattery (View view){
        Intent intent=new Intent(getApplicationContext(),LowBatteryApplication.class);
        startActivity(intent);
    }
    public void shake(View view){
        Intent intent=new Intent(getApplicationContext(),ShakeApplication.class);
        startActivity(intent);}

        public void location (View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/@18.9728803,73.0287206,15z"));
        startActivity(intent);
        }
}