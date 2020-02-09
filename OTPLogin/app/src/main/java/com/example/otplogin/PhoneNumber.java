package com.example.otplogin;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.RequiresApi;


public class PhoneNumber extends Activity {

    private static final String TAG = "1";
    EditText phoneNumber;
    Button buttonCode;
    FirebaseAuth mAuth;
    String codeSent;

    private ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.phone_number_activity);

        //Enabling button if Phone Number is entered
        phoneNumber = findViewById(R.id.etPhoneNumber);

        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        phoneNumber.setText(telephonyManager.getLine1Number());

        buttonCode = findViewById(R.id.btnSendConfirmationCode);
//        phoneNumber.addTextChangedListener(phoneNumberWatcher);

        // Switch to next activity
        buttonCode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                String mobile = phoneNumber.getText().toString().trim();

                if(mobile.isEmpty() || mobile.length() < 10){
                    phoneNumber.setError("Enter a valid mobile");
                    phoneNumber.requestFocus();
                    return;
                }

                Intent intent = new Intent(PhoneNumber.this, OTPVerify.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);

            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(PhoneNumber.this, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }



    }






//    private TextWatcher phoneNumberWatcher = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            String phoneDetails = phoneNumber.getText().toString();
//            buttonCode.setEnabled(!phoneDetails.isEmpty() && phoneDetails.length() == 10);
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//
//        }
//    };




//    private void sendVerificationCode(){
//        String phone = phoneNumber.getText().toString().trim();
//
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                "+91" + phone,        // Phone number to verify
//                60,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                this,               // Activity (for callback binding)
//                mCallbacks);        // OnVerificationStateChangedCallbacks
//    }
//
//    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        @Override
//        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//
//        }
//
//        @Override
//        public void onVerificationFailed(FirebaseException e) {
//
//        }
//
//        @Override
//        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//
//            codeSent = s;
//        }
//    };
//
//
//
//
//




}