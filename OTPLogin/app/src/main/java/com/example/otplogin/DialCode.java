package com.example.otplogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class DialCode extends AppCompatActivity {

    EditText UssdCode;



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
                String number = UssdCode.getText().toString();

                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                String encodedHash = Uri.encode("#"); //**If needed***
                callIntent.setData(Uri.parse("tel:"+ Uri.encode(number)));//***********Encode used  to read "#" in USSD Codes*******************
//                if (ActivityCompat.checkSelfPermission(DialCode.this,
//                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
                startActivity(callIntent);
            }
        });



    }
}
