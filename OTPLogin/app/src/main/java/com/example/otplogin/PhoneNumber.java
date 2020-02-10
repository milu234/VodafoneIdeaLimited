package com.example.otplogin;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.VolumeShaper;
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

import java.util.Locale;

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
        loadLocale();
        // Get the view from new_activity.xml
        setContentView(R.layout.phone_number_activity);
        //Enabling button if Phone Number is entered
        phoneNumber = findViewById(R.id.etPhoneNumber);


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

        findViewById(R.id.changeLanguage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLanguageDialog();

            }
        });



    }

        private void showChangeLanguageDialog(){
        final String[] listItems = { "हिंदी", "मराठी", "বাংলা", "English"};
            AlertDialog.Builder builder = new AlertDialog.Builder(PhoneNumber.this);
            builder.setTitle("Choose Language...");
            builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == 0){
                        setLocale("hi");
                        recreate();
                    }

                    else if (i==1){
                        setLocale("mr");
                        recreate();
                    }

                    else if(i==2){
                        setLocale("bn");
                        recreate();
                    }

                    else if (i==3){
                        setLocale("en");
                        recreate();
                    }
                    dialogInterface.dismiss();

                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();


        }

        private  void setLocale(String lang){
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.locale = locale;
            getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

            SharedPreferences.Editor editor = getSharedPreferences("Settings",MODE_PRIVATE).edit();
            editor.putString("My_Lang",lang);
            editor.apply();
        }

        public void loadLocale(){
        SharedPreferences preferences = getSharedPreferences("Settings",Activity.MODE_PRIVATE);
        String language = preferences.getString("My_Lang", "");
        setLocale(language);
        }

}