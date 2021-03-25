package com.example.womensafetyapp;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    ImageView backBtn;
    Button regbtn;
    TextView titleText;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    TextInputLayout regfullname, regusername, regemail, regpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        backBtn = findViewById(R.id.signup_back_button);
        titleText = findViewById(R.id.signup_title_text);
        regfullname = findViewById(R.id.fullname);
        regusername = findViewById(R.id.username);
        regemail = findViewById(R.id.email);
        regpassword = findViewById(R.id.password);

        regbtn = findViewById(R.id.signup_button);
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateName() | !validatePassword() | !validateEmail() | !validateUsername()) {
                    return;
                }
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Users");

                String fullname = regfullname.getEditText().getText().toString();
                String username = regusername.getEditText().getText().toString();
                String email = regemail.getEditText().getText().toString();
                String password = regpassword.getEditText().getText().toString();

                //UserHelperClass helperClass = new UserHelperClass(fullname, username, email, password);
                // reference.child(username).setValue(helperClass);

                Intent intent = new Intent(getApplicationContext(), Signup3.class);
                intent.putExtra("fullName", fullname);
                intent.putExtra("email", email);
                intent.putExtra("username", username);
                intent.putExtra("password", password);

                Pair[] pairs = new Pair[3];
                pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_button");
                pairs[1] = new Pair<View, String>(regbtn, "transition_next_btn");
                //pairs[1] = new Pair<View, String>(signup_button, "transition_login_btn");
                pairs[2] = new Pair<View, String>(titleText, "transition_title_text");


                //  if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //      ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Signup.this, pairs);
                //       startActivity(intent, options.toBundle());
                //   } else {
                startActivity(intent);
                // }


            }
        });
    }

    private Boolean validateName() {
        String val = regfullname.getEditText().getText().toString();

        if (val.isEmpty()) {
            regfullname.setError("Field cannot be empty");
            return false;
        } else {
            regfullname.setError(null);
            regfullname.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = regusername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            regusername.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            regusername.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            regusername.setError("White Spaces are not allowed");
            return false;
        } else {
            regusername.setError(null);
            regusername.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateEmail() {
        String val = regemail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regemail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regemail.setError("Invalid email address");
            return false;
        } else {
            regemail.setError(null);
            regemail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regpassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                // "\\A\\w{4,20}\\z" +           //no white spaces
                //"(?=\\S+$)"
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            regpassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regpassword.setError("Password is too weak ! ");
            return false;
        } else {
            regpassword.setError(null);
            regpassword.setErrorEnabled(false);
            return true;
        }
    }

    public void back(View view) {
        Intent intent = new Intent(Signup.this, UserDashboard.class);
        startActivity(intent);
    }
}



       










