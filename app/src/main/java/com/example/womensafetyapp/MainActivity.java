package com.example.womensafetyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIMER=5000;

    Animation topanim, bottomanim;
    SharedPreferences onBoardingScreen;
    ImageView image;
    TextView logo,slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        topanim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomanim=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        image=findViewById(R.id.imageView);
        logo=findViewById(R.id.textView);
        slogan=findViewById(R.id.textView2);

        image.setAnimation(topanim);
        logo.setAnimation(bottomanim);
        slogan.setAnimation(bottomanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                onBoardingScreen = getSharedPreferences("onBoardScreen", MODE_PRIVATE);
                boolean isFirstTime = onBoardingScreen.getBoolean("firstTime",true);

               // if(isFirstTime) {
                 //   SharedPreferences.Editor editor = onBoardingScreen.edit();
                  //  editor.putBoolean("firstTime", false);
                  //  editor.commit();

                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(intent);
                    finish();
               // }
              //  else {
                 //   Intent intent = new Intent(getApplicationContext(), UserDashboard.class);
                 //   startActivity(intent);
                  //  finish();

               // }

            }


        },SPLASH_TIMER );

    }
}

