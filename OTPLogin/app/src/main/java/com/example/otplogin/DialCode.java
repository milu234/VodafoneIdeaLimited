package com.example.otplogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DialCode extends AppCompatActivity {

    EditText UssdCode;
    private static final int REQUEST_CALL = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial_code);
        UssdCode = findViewById(R.id.USSD_Code);
        final Intent intent = getIntent();
        UssdCode.setText(intent.getStringExtra("values"));


        findViewById(R.id.btnDial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                makePhoneCall();
            }


        });
    }


        private void makePhoneCall(){

        String number = UssdCode.getText().toString();
        if (number.trim().length()> 0) {

//
//
            if (ActivityCompat.checkSelfPermission(DialCode.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DialCode.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

            } else {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + Uri.encode(number)));
                startActivity(callIntent);
            }
        }
        else {
            Toast.makeText(DialCode.this,"Enter USSD Code",Toast.LENGTH_SHORT).show();
        }
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode ==REQUEST_CALL){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            } else {
                Toast.makeText(DialCode.this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
