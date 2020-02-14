package com.example.otplogin;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;
    private static final String TAG = "LOLL";
    TextView textView;
    TextView optView;
    ProgressBar progressBar;
    TextView progressText;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textView = findViewById(R.id.timeView);
        progressText = findViewById(R.id.textProgress);

        textView.setText(String.valueOf(PhoneNumber.getRunningTime()));


        optView = findViewById(R.id.optView);
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String carrierName = telephonyManager.getNetworkOperatorName();
        optView.setText(carrierName);


        progressBar = findViewById(R.id.progressBar);
        String progress = textView.getText().toString();
        int x = Integer.parseInt(progress)/10;

        String y = String.valueOf(x)+"%";
        progressText.setText(y);
        progressBar.setProgress(Integer.parseInt(progress)/10);

        findViewById(R.id.btnPayPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,RazorPayGateway.class);
                startActivity(intent);

            }
        });

        findViewById(R.id.btnSignOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this,PhoneNumber.class);
                startActivity(intent);


            }
        });

        findViewById(R.id.btnInvite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    onInviteClicked();
            }
        });


        findViewById(R.id.btnUSSD).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,USSDLIst.class);
                startActivity(intent);
            }
        });

    }

 //***********************************************************Invitation Link******************************************************************
    private  void onInviteClicked(){
        Intent intent = new AppInviteInvitation.IntentBuilder("Invitation Title")
                .setMessage("hey there is a nice app")
                .setDeepLink(Uri.parse("https://google.com"))
                .setCallToActionText("Invitation CTA")
                .build();
        startActivityForResult(intent,REQUEST_CODE);


    }
//***************************************************************Function to check Invite - Start***********************************************
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK){
                String [] ids = AppInviteInvitation.getInvitationIds(resultCode,data);
                for (String id : ids){
                    Log.d(TAG, "onActivityResult: sent invitation " + id);
                }
            }
            else {

                Toast.makeText(HomeActivity.this, "Invite Not Sent", Toast.LENGTH_LONG).show();


            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static long UsedDuration(){
        long x = PhoneNumber.getRunningTime();
        long y = x/1000;
        long z = y/60;
        long progressPercent = (z/1440)*100;
        return progressPercent;
    }


 //**************************************************************************End*****************************************************************
}
