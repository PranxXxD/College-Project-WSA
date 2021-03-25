package com.example.womensafetyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class Verify_OTP extends AppCompatActivity {
    PinView pinfromUser;
    String codeBySystem;
    String _phoneNo, email, password, date, fullname, username, whatToDo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify__otp);

        pinfromUser = findViewById(R.id.pin_view);

        String fullname = getIntent().getStringExtra("fullName");
        String email = getIntent().getStringExtra("email");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        // String date = getIntent().getStringExtra("date");
        String _phoneNo = getIntent().getStringExtra("_phoneNo");
        String whatToDo = getIntent().getStringExtra("whatToDo");

        sendVerificationCodeToUser(_phoneNo);
    }

    private void sendVerificationCodeToUser(String _phoneNo) {
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phoneNo,        // Phone number to verify
//                60,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
//                Verify_OTP.this,
//                mCallbacks);        // OnVerificationStateChangedCallbacks
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(_phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(String   s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        pinfromUser.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(Verify_OTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            if (whatToDo.equals("updateData")) {
                                updateOldUsersData();

                            }
                            else {
                                storeNewUsersData();
                            }

                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(Verify_OTP.this, "Verification Not Completed ! Try again.", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
    }

    private void updateOldUsersData() {
        Intent intent = new Intent(Verify_OTP.this,SetPassword.class);
        // intent.putExtra("phoneNo",phoneNo);
        startActivity(intent);

    }

    private void storeNewUsersData() {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Users");

        UserHelperClass addNewUser = new UserHelperClass(fullname, username, email, password, _phoneNo);
        reference.child(_phoneNo).setValue(addNewUser);
    }


    public void callNextScreenfromOTP(View view) {
        String code = pinfromUser.getText().toString();
        if (!code.isEmpty()) {
            verifyCode(code);
        }
    }

    public void gotoHomeFromOTP(View view) {
        Intent intent = new Intent(Verify_OTP.this, Signup.class);
        startActivity(intent);
    }
}
