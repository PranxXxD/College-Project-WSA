package com.example.womensafetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Profile extends AppCompatActivity {
    TextInputLayout pfullname,pusername,pemail,ppassword,pphoneno;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        pfullname = findViewById(R.id.fullname_profile);
        pusername = findViewById(R.id.username_profile);
        pemail =findViewById(R.id.email_profile);
        ppassword=findViewById(R.id.password_profile);
        pphoneno=findViewById(R.id.phonenumber_profile);
        update=findViewById(R.id.update_profile);
        showAllUserData();

    }

    private void showAllUserData() {

        String fullname = getIntent().getStringExtra("fullname");
        String email = getIntent().getStringExtra("email");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        String phoneNo = getIntent().getStringExtra("phoneNo");

        pfullname.getEditText().setText(fullname);
        pusername.getEditText().setText(username);
        pemail.getEditText().setText(email);
        ppassword.getEditText().setText(password);
        pphoneno.getEditText().setText(phoneNo);



    }


    public void next_profile (View view){

        String fullname = getIntent().getStringExtra("fullname");
        String email = getIntent().getStringExtra("email");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        String phoneNo = getIntent().getStringExtra("phoneNo");


        Toast.makeText(Profile.this, fullname + "\n" + email + "\n" + phoneNo, Toast.LENGTH_LONG).show();

        Intent intent=new Intent(Profile.this,Dashboard_1.class);

        intent.putExtra("fullname",fullname);
        intent.putExtra("username",username);
        intent.putExtra("email",email);
        intent.putExtra("phoneNo",phoneNo);
        intent.putExtra("password",password);
        startActivity(intent);
    }
}