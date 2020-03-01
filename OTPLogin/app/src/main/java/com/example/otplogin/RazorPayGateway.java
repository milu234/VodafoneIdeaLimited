package com.example.otplogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class RazorPayGateway extends AppCompatActivity implements PaymentResultListener {

    EditText payAmount ;
    int amount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razor_pay_gateway);

        payAmount = findViewById(R.id.payAmount);
        Intent intent = getIntent();

        payAmount.setText(intent.getStringExtra("amountPrevious"));

        Intent intent1 = getIntent();
        payAmount.setText(intent1.getStringExtra("QRAmount"));


        Intent intent2 = getIntent();

        payAmount.setText(intent2.getStringExtra("RechargeUnlimitedFragment"));


//        Intent intent3 = getIntent();
//        payAmount.setText(intent3.getStringExtra("RechargeDataFragment"));
//
//
//        Intent intent4 = getIntent();
//        payAmount.setText(intent4.getStringExtra("RechargeRecommendedFragment"));
//
//
//        Intent intent5 = getIntent();
//        payAmount.setText(intent5.getStringExtra("RechargeUnlimitedFragment"));







        findViewById(R.id.pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();

            }
        });


    }

    private void startPayment(){
        amount = Integer.parseInt(payAmount.getText().toString());

        Checkout checkout = new Checkout();

        checkout.setImage(R.mipmap.ic_launcher);

        final Activity activity=this;

        try{
            JSONObject options = new JSONObject();
            options.put("Description","Order #123456");
            options.put("currency","INR");
            options.put("amount",amount*100);
            checkout.open(activity,options);

        } catch (JSONException e){
            e.printStackTrace();

        }

    }

    @Override
    public void onPaymentSuccess(String s) {
        UpdateCoins.updateVodaCoins("30");
        Toast.makeText(RazorPayGateway.this,"Your Payment is succcessful",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
            Toast.makeText(RazorPayGateway.this,"Your Payment Failed",Toast.LENGTH_SHORT).show();
    }
}
