package com.example.otplogin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.RequiresApi;

import static android.app.AppOpsManager.OPSTR_GET_USAGE_STATS;


public class PhoneNumber extends Activity {
    private static final int MODE_ALLOWED = 1;
    public static long startTime = 0;
    public static long duration = 0;

    private static final String TAG = "1";
    EditText phoneNumber;
    Button buttonCode;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        startTime = duration + System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        loadLocale();  ///For calling the language change function
        setContentView(R.layout.phone_number_activity);
        phoneNumber = findViewById(R.id.etPhoneNumber);
        buttonCode = findViewById(R.id.btnSendConfirmationCode);

//****************************************************************Function to check the Phone Nuber*******************************************

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
        //***************************************************************************************************************************************

 //***************************************************Fucntion to check user is signed in ****************************************************

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
//*********************************************************************************************************************************************

//******************************************************************Function button to change Language****************************************


        findViewById(R.id.changeLanguage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLanguageDialog();

            }
        });
        //***********************************************************************************************************************************************
    }

    @Override
    protected void onPause() {
        super.onPause();
        duration += System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
    }
//**************************************************************Function to display the Language Change - Start****************************************
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

//**********************************************************END*************************************************************************************

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static long getRunningTime(){
        return duration;

    }

    public void showTime(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Long start = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
            Long end =  ZonedDateTime.now().toInstant().toEpochMilli();
            UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
            Map<String, UsageStats> stats = usageStatsManager.queryAndAggregateUsageStats(start, end);

            //Long total = Duration.ofMillis(stats.v
        }
    }

}