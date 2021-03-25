package com.example.womensafetyapp;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class ForgotPassword extends AppCompatActivity {
    CountryCodePicker countryCodePicker;
    Button nextbutton;
    Animation animation;
    ImageView screenicon;
    TextView title, description;
    TextInputLayout phonenumberTextfield;
    RelativeLayout forgot_progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        screenicon = findViewById(R.id.screenicon);
        title = findViewById(R.id.forgotpasswordtitle);
        description = findViewById(R.id.forgotpassworddescription);
        phonenumberTextfield = findViewById(R.id.forgot_phone_number);
        countryCodePicker = findViewById(R.id.countrycodepicker);
        nextbutton = findViewById(R.id.forgotpasswordnext);
        animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        forgot_progressbar = findViewById(R.id.forgot_progressbar);
        screenicon.setAnimation(animation);
        title.setAnimation(animation);
        phonenumberTextfield.setAnimation(animation);
        countryCodePicker.setAnimation(animation);
        nextbutton.setAnimation(animation);
        description.setAnimation(animation);
    }

    public void verifyPhoneNumber(View view) {
        // CheckInternet checkInternet=new CheckInternet();
        //   if(!checkInternet.isConnected(this){
        //   showCustomDialog();
        //   return;}
        if (!validateFields()) {
            return;
        }
        forgot_progressbar.setVisibility(View.VISIBLE);
        String _phoneNumber = phonenumberTextfield.getEditText().getText().toString().trim();
        if (_phoneNumber.charAt(0) == '0') {
            _phoneNumber = _phoneNumber.substring(1);
        }
        final String _completePhoneNumber = "+" + countryCodePicker.getFullNumber() + _phoneNumber;
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    phonenumberTextfield.setError(null);
                    phonenumberTextfield.setErrorEnabled(false);

                    /*Intent intent = new Intent(getApplicationContext(), Verify_OTP.class);
                    intent.putExtra("phoneNo", _completePhoneNumber);
                    intent.putExtra("whatToDo", "updateData");

                    startActivity(intent);
                    finish();*/
                    forgot_progressbar.setVisibility(View.GONE);
                } else {
                    forgot_progressbar.setVisibility(View.GONE);
                    phonenumberTextfield.setError("NO SUCH USER EXISTS");
                    phonenumberTextfield.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean validateFields() {
        String _phoneNumber = phonenumberTextfield.getEditText().getText().toString().trim();

        if (_phoneNumber.isEmpty()) {
            phonenumberTextfield.setError("PhoneNumber cannot be empty");
            phonenumberTextfield.requestFocus();
            return false;
        } else {

            phonenumberTextfield.setError(null);
            return true;
        }
    }
}