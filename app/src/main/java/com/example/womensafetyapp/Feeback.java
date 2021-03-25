package com.example.womensafetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

public class Feeback extends AppCompatActivity {
    RatingBar ratingBar;
    Button btSubmit;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeback);
        ratingBar = findViewById(R.id.rating_bar);
        btSubmit = findViewById(R.id.bt_submit);
        back = findViewById(R.id.back_feedback);

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = String.valueOf(ratingBar.getRating());
                Toast.makeText(getApplicationContext(), s + "Star \n Thankyou for your Feedback", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void back_feedback(View view) {
        Intent intent = new Intent(Feeback.this, Dashboard_1.class);
        startActivity(intent);
    }
}