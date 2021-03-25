package com.example.womensafetyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.ArrayList;

public class Dashboard_1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView featuredRecycler, mostViewedRecycler, categoriesRecycler;
    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1, gradient2, gradient3, gradient4;
    DrawerLayout drawerlayout;
    NavigationView navigationView;
    ImageView menuIcon;
    static final float END_SCALE = 0.7f;
    LinearLayout contentView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        featuredRecycler = findViewById(R.id.featured_recycler);
        featuredRecycler();
        mostViewedRecycler = findViewById(R.id.ms_recycler);
        mostViewedRecycler();
        categoriesRecycler = findViewById(R.id.categories_recycler);
        categoriesRecycler();
        drawerlayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);


        navigationDrawer();
    }

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerlayout.isDrawerVisible(GravityCompat.START)) {
                    drawerlayout.closeDrawer(GravityCompat.START);
                } else drawerlayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();

    }


    private void animateNavigationDrawer() {
        drawerlayout.setScrimColor(getResources().getColor(R.color.colorPrimary));
        drawerlayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerlayout.isDrawerVisible(GravityCompat.START)) {
            drawerlayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }


    private void categoriesRecycler() {


        //gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        // gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        // gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});


        ArrayList<CategoriesHelperClass> categoriesHelperClasses = new ArrayList<>();
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.user_profile, "Update Profile"));
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.add_phoneno, "Update Guardian \nPhoneNo"));
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.logout, "LOGOUT"));


        categoriesRecycler.setHasFixedSize(true);
        adapter = new CategoriesAdapter(categoriesHelperClasses);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycler.setAdapter(adapter);

    }

    private void mostViewedRecycler() {
        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<MostViewedHelperClass> mostViewedLocations = new ArrayList<>();
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.emergency_call, "EASY CALLING FACILITIES"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.message, "INSTANT MESSAGING ANYTIME ANYWHERE"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.location_sharing, "SHARE LOCATION  EASILY"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.alert, "ALERT NOTIFICATION IN CASE OF EMERGENCY"));

        adapter = new MostViewedAdapter(mostViewedLocations);
        mostViewedRecycler.setAdapter(adapter);
    }

    private void featuredRecycler() {
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.emergency_call, "EMERGENCY CALLING",
                "“Emergency call” button brings up the dial pad and by using that you can make an emergency call"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.location_sharing, "LOCATION SHARING", "THIS ALLOW YOU TO SHARE YOUR LOCATION !"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.message, "MESSAGING", "USING THIS YOU CAN INSTANT MESSAGE ! "));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.alert, "ALERT\nNOTIFICATION", "IN CASE OF EMERGENCY YOU CAN MAKE USE OF ALERT NOTIFICATION"));
        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_all_categories:
                startActivity(new Intent(getApplicationContext(), AllCategories.class));

                break;
            //case R.id.nav_share:
            //    ApplicationInfo api = getApplicationContext().getApplicationInfo();
            //    String apkpath = api.sourceDir;
            //    Intent intent = new Intent(Intent.ACTION_SEND);
            //    intent.setType("application/vnd.android.package-archive");
             //   intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkpath)));
             //   startActivity(Intent.createChooser(intent, "Share Via"));
             //   break;

            case R.id.nav_login:
                startActivity(new Intent(getApplicationContext(), Login.class));
                break;

            case R.id.nav_logout:
                startActivity(new Intent(getApplicationContext(), Login.class));
                break;







        }
        //  switch (item.getItemId()){
        //     case  R.id.nav_share:
        //     ApplicationInfo api =  getApplicationContext().getApplicationInfo();
        //   String apkpath = api.sourceDir;
        //   Intent intent = new Intent(Intent.ACTION_SEND);
        //    intent.setType("application/vnd.android.package-archive");
        //    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkpath)));
        //   startActivity(Intent.createChooser(intent,"Share Via"));
        //     break;
        //  Intent shareintent= new Intent();
        //shareintent.setAction(Intent.ACTION_SEND);
        //  shareintent.putExtra(Intent.EXTRA_TEXT,"");
        // shareintent.setType("text/plain");
        //   startActivity(Intent.createChooser(shareintent,"Share Via"));
        //}
        drawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public void feedback(View view){
        Intent intent=new Intent(Dashboard_1.this,Feeback.class);
        startActivity(intent);
    }
    public void viewAll_security(View view){
        Intent intent=new Intent(Dashboard_1.this,AllCategories.class);
        startActivity(intent);
    }
    public void dashboard_call(View view){
        Intent intent=new Intent(Dashboard_1.this,CallingApplication.class);
        startActivity(intent);
    }
    public void dashboard_location(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/@18.9728803,73.0287206,15z"));
        startActivity(intent);
    }

}