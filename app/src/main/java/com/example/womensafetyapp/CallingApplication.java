package com.example.womensafetyapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static android.Manifest.permission.CALL_PHONE;

public class CallingApplication extends AppCompatActivity {

    private EditText editText, editText1;
    TextView Textview;
    public Object TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling_application);
        Textview=findViewById(R.id.editText1);
        //editText1 = findViewById(R.id.editText1);
        editText = findViewById(R.id.editText);
        ActivityCompat.requestPermissions(this, new String[]{CALL_PHONE}, PackageManager.PERMISSION_GRANTED);

    }

    public void callbutton(View view) {
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        startActivity(new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", Textview.getText().toString(), null)));

    }


    public void CallButton(View view) {
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        startActivity(new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", editText.getText().toString(), null)));

    }
    public void back_button(View view){
        Intent intent=new Intent(getApplicationContext(),AllCategories.class);
        startActivity(intent);
    }



}