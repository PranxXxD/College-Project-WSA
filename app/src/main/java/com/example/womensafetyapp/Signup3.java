package com.example.womensafetyapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class Signup3 extends AppCompatActivity {

    ScrollView scrollView;
    TextInputLayout _phoneNo;
    CountryCodePicker countryCodePicker;
    TextInputLayout regfullname, regusername, regemail, regpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup3);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        scrollView = findViewById(R.id.signup_3rd_screen_scroll_view);
        countryCodePicker = findViewById(R.id.country_code_picker);
        _phoneNo = findViewById(R.id.signup_phone_number);

        regfullname = findViewById(R.id.fullname);
        regusername = findViewById(R.id.username);
        regemail = findViewById(R.id.email);
        regpassword = findViewById(R.id.password);
    }

    private boolean validatePhoneNo() {
        String val = _phoneNo.getEditText().toString();
        if (val.isEmpty()) {
            _phoneNo.setError("Field cannot be empty");
            return false;
        } else {
           _phoneNo.setError(null);
            return true;
        }
    }

    public void callVerifyOTPScreen(View view) {
        if (!validatePhoneNo()) {

            return;
        }

        /*String _fullName = getIntent().getStringExtra("fullName");
        String _email = getIntent().getStringExtra("email");
        String _username = getIntent().getStringExtra("username");
        String _password = getIntent().getStringExtra("password");
        String _date = getIntent().getStringExtra("date");*/


        String fullname = getIntent().getStringExtra("fullName");
        String email = getIntent().getStringExtra("email");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        String date = getIntent().getStringExtra("date");

        String _getUserEnteredPhoneNumber = _phoneNo.getEditText().getText().toString().trim();
        String _phoneNo = "+" + countryCodePicker.getFullNumber() + _getUserEnteredPhoneNumber;


        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Users");

        UserHelperClass addNewUser = new UserHelperClass(fullname, username, email, password, _phoneNo);
        reference.child(_phoneNo).setValue(addNewUser);
        Intent intent = new Intent(getApplicationContext(), Verify_OTP.class);

        //Pass all fields to the next activity
        intent.putExtra("fullName", fullname);
        intent.putExtra("email", email);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        intent.putExtra("date", date);
        intent.putExtra("_phoneNo", _phoneNo);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(scrollView, "transition_OTP_screen");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Signup3.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }


}
