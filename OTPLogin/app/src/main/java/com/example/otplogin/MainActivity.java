package com.example.otplogin;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;
    Menu optionsMenu;
    private static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        BottomNavigationView navigation = findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new UnoFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);

        return true;
    }

    public  boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.chatbot){
            Intent intent2 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
            startActivityForResult(intent2, 10);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 10:
                    String Found = new UnoFragment().getNumberFromResult(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS));
                    if (Found.equals("Recharge")) {
                        loadFragment(new DosFragment());
                        //firstNumTextView.setText(String.valueOf(intFound));
                    } else if (Found.equals("Home")){
                        Intent intent = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intent);

                    } else if (Found.equals("Vodafone Stores Near Me")){
                        Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                        startActivity(intent);

                    }  else if (Found.equals("QuickRecharge")){
                        Intent intent = new Intent(MainActivity.this,RazorPayGateway.class);
                        intent.putExtra("RechargeUnlimitedFragment","399");
                        startActivity(intent);


                    } else if (Found.equals("Profile")) {

                        loadFragment(new AccountFragment());
                    }

                    else if (Found.equals("Offers")) {
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    else if (Found.equals("Calling")) {
                        makePhoneCall();

                    }

                    else {
                        Toast.makeText(MainActivity.this, "Sorry, I didn't catch that! Please try again", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }else {
            Toast.makeText(MainActivity.this, "Failed to recognize speech!", Toast.LENGTH_LONG).show();
        }
    }

    private String getNumberFromResult(ArrayList<String> results) {
        for (String str : results) {
            if (!getIntNumberFromText(str).equals("Sorry")) {
                return getIntNumberFromText(str);
            }
        }
        return "No";
    }

    private String getIntNumberFromText(String strNum) {
        switch (strNum) {
            case "recharge":

                return "Recharge";
            case "home":
                return "Home";
            case "Vodafone stores near me":
                return "Vodafone Stores Near Me";
            case "Vodafone stores":
                return "Vodafone Stores Near Me";
            case "vodacoins":
                return "Profile";
            case "balance":
                return "Profile";
            case "data":
                return "Profile";
            case "quick recharge":
                return "QuickRecharge";
            case "offers":
                return "Offers";
            case "call customer care":
                return "Calling";
        }
        return "Sorry";
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    toolbar.setTitle("Home");
                    loadFragment(new UnoFragment());
                    return true;
                case R.id.frag2:
                    toolbar.setTitle("Recharge");
                    loadFragment(new DosFragment());
                    return true;
                case R.id.frag3:
                    toolbar.setTitle("Earn VodaCoins");
                    loadFragment(new TresFragment());
                    return true;
                case R.id.frag4:
                    toolbar.setTitle("Explore");
                    loadFragment(new CautroFragment());
                    return true;
                case R.id.account:
                    toolbar.setTitle("Profile");
                    loadFragment(new AccountFragment());
                    return true;
            }
            return false;
        }
    };

    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }







    //***************************************************************************************************
    private void makePhoneCall(){

        String number = "9820098200";
        if (number.trim().length()> 0) {

//
//
            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

            } else {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + Uri.encode(number)));
                startActivity(callIntent);
            }
        }
        else {
            Toast.makeText(MainActivity.this,"Enter USSD Code",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode ==REQUEST_CALL){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            } else {
                Toast.makeText(MainActivity.this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
