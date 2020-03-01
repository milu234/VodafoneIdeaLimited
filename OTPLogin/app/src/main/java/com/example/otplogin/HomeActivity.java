package com.example.otplogin;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;
    private static final String TAG = "LOLL";
    TextView textView;
    TextView optView;
    ProgressBar progressBar;
    TextView progressText;
    Button clickImageButton;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//
//            <TextView
//        android:id="@+id/textView"
//        android:layout_width="wrap_content"
//        android:layout_height="wrap_content"
//        android:layout_centerHorizontal="true"
//        android:layout_marginTop="25dp"
//        android:text="@string/welcome"
//        android:textAppearance="@style/Base.TextAppearance.AppCompat.Headline"
//        android:textColor="@color/colorPrimary" />

        progressText = (TextView)findViewById(R.id.textProgress);

        long textView = PhoneNumber.getRunningTime();
        progressBar = findViewById(R.id.progressBar);
        String progress = String.valueOf(textView);
        int x = Integer.parseInt(progress)/10;

        String y = String.valueOf(x)+"%";
        progressText.setText(y);
        progressBar.setProgress(Integer.parseInt(progress)/10);

        clickImageButton = (Button)findViewById(R.id.clickImage);
        clickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this,  AugmentedFacesActivity.class);
                    startActivity(intent);
            }
        });


        findViewById(R.id.btnInvite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    onInviteClicked();
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
                int finalPoints = ids.length*10;
                UpdateCoins.updateVodaCoins(String.valueOf(finalPoints));
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

        UpdateCoins.updateVodaCoins(String.valueOf(progressPercent));

        return progressPercent;
    }

    //########################################## Click Image Task ###############################################//



    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


 //**************************************************************************End*****************************************************************
}
