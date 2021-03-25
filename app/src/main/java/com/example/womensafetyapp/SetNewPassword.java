package com.example.womensafetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetNewPassword extends AppCompatActivity {
    Animation animation;
    ImageView icon;
    TextView title, description;
    TextInputLayout newpassword, confirmpassword;
    RelativeLayout progressbar;
    Button updatebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);
        icon = findViewById(R.id.screenicon);
        title = findViewById(R.id.titletext);
        newpassword = findViewById(R.id.newpassword);
        confirmpassword = findViewById(R.id.confirmpassword);
        description = findViewById(R.id.description);
        animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        description.setAnimation(animation);
        title.setAnimation(animation);
        icon.setAnimation(animation);
        newpassword.setAnimation(animation);
        confirmpassword.setAnimation(animation);
        progressbar = findViewById(R.id.progressbar);
        updatebtn = findViewById(R.id.updatebtn);
    }

    public void setnewpasswordbtn (View view) {
        // CheckInternet checkInternet=new CheckInternet();
        //   if(!checkInternet.isConnected(this){
        //   showCustomDialog();
        //   return;}
        //if (!validatenewPassword()|!validateconfirmpassword()){
        //    return;
        // }
        progressbar.setVisibility(View.VISIBLE);
        String _newPassword = newpassword.getEditText().getText().toString().trim();
        String _phoneNumber = getIntent().getStringExtra("phoneNo");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(_phoneNumber).child("password").setValue(_newPassword);
        startActivity(new Intent(getApplicationContext(),ForgotPasswordSuccess.class));
        finish();
    }

}