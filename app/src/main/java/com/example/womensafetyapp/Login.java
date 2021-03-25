package com.example.womensafetyapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class Login extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks {
    TextInputLayout password;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;
    RelativeLayout progressbar;
    Button login;
    TextInputLayout regfullname, regusername, regemail, regpassword;
    CheckBox checkbox;
    GoogleApiClient googleApiClient;
//    String SiteKey = "6LeaL84ZAAAAABJPz4a7g73pZL3ILpx5poseUpwD";
    String SiteKey = "AIzaSyDx1CP5OFwP_lOs-83OEZs0mZ44yzBMkXY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        password = findViewById(R.id.login_password);
        countryCodePicker = findViewById(R.id.login_country_code_picker);
        phoneNumber = findViewById(R.id.login_phone_number);
        progressbar = findViewById(R.id.login_progressbar);
        login = findViewById(R.id.letTheUserLogin);
        regfullname = findViewById(R.id.fullname);
        regusername = findViewById(R.id.username);
        regemail = findViewById(R.id.email);
        regpassword = findViewById(R.id.password);
        checkbox = findViewById(R.id.checkboxcaptcha);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(SafetyNet.API)
                .addConnectionCallbacks(Login.this)
                .build();
        googleApiClient.connect();

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox.isChecked()){
                    SafetyNet.SafetyNetApi.verifyWithRecaptcha(googleApiClient,SiteKey)
                            .setResultCallback(new ResultCallback<SafetyNetApi.RecaptchaTokenResult>() {
                                @Override
                                public void onResult(@NonNull SafetyNetApi.RecaptchaTokenResult recaptchaTokenResult) {
                                    Status status = recaptchaTokenResult.getStatus();
                                    if ((status != null) && status.isSuccess()){
                                        Toast.makeText(getApplicationContext(),"successfully verified....",Toast.LENGTH_SHORT).show();
                                        checkbox.setTextColor(Color.GREEN);
                                    }
                                }
                            });
                }else {
                    checkbox.setTextColor(Color.BLACK);
                }
            }
        });
    }

    public void letTheUserLogin(View view) {
        if (!isConnected(this)) {
            showCustomDialog();
        }

        if (!validateFields()) {
            return;
        }
        progressbar.setVisibility(View.VISIBLE);

        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        final String _password = password.getEditText().getText().toString().trim();

        if (_phoneNumber.charAt(0) == '0') {
            _phoneNumber = _phoneNumber.substring(1);
        }
        final String _completePhoneNumber = "+" + countryCodePicker.getFullNumber() + _phoneNumber;

        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);

                    String systemPassword = dataSnapshot.child(_completePhoneNumber).child("password").getValue(String.class);
                    if (systemPassword.equals(_password)) {
                        password.setError(null);
                        password.setErrorEnabled(false);


                        String _username = dataSnapshot.child(_completePhoneNumber).child("username").getValue(String.class);
                        String _password = dataSnapshot.child(_completePhoneNumber).child("password").getValue(String.class);
                        String _fullname = dataSnapshot.child(_completePhoneNumber).child("fullname").getValue(String.class);
                        String _email = dataSnapshot.child(_completePhoneNumber).child("email").getValue(String.class);
                        String _phoneNo = dataSnapshot.child(_completePhoneNumber).child("phoneNo").getValue(String.class);
                        //String _dateOfBirth = dataSnapshot.child(_completePhoneNumber).child("date").getValue(String.class);

                        Toast.makeText(Login.this, _fullname + "\n" + _email + "\n" + _phoneNo, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Login.this, Profile.class);
                        intent.putExtra("fullname",_fullname);
                        intent.putExtra("username",_username);
                        intent.putExtra("email",_email);
                        intent.putExtra("phoneNo",_phoneNo);
                        intent.putExtra("password",_password);


                        startActivity(intent);
                    } else {
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(Login.this, "password does not match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "data does not exist", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(Login.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private boolean isConnected(Login login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiConn != null && wifiConn.isConnected() || (mobileConn != null && mobileConn.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage("Please Connect to the Internet to Proceed Further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), UserDashboard.class));

                        finish();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean validateFields() {
        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        if (_phoneNumber.isEmpty()) {
            phoneNumber.setError("PhoneNumber cannot be empty");
            phoneNumber.requestFocus();
            return false;
        } else if (_password.isEmpty()) {
            password.setError("password cannot be empty");
            password.requestFocus();
            return false;
        } else {
            password.setError(null);
            phoneNumber.setError(null);
            return true;
        }
    }

    public void back(View view) {
        Intent intent = new Intent(Login.this, UserDashboard.class);
        startActivity(intent);
    }

    public void create_account(View view) {
        Intent intent = new Intent(Login.this, Signup.class);
        startActivity(intent);
    }

    public void fogotpassword(View view) {
        startActivity(new Intent(getApplicationContext(), ForgotPassword.class));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
