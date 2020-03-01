package com.example.otplogin;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class AccountFragment extends Fragment {
    TextView userName;
    TextView plan;
    TextView talktimeBalanceView;
    TextView dataBalanceView;
    TextView phoneNumber;
    TextView validityDuration;
    TextView rechargePlan;
    TextView vodaCoin;

    Button vodaCoins;
    Button scratchCards;
    Button scratchcard;

    String hostAddress = "http://10.10.40.58/vil/getAccountInfo.php";
    static String phone = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        phone = user.getPhoneNumber();


        View RootView = inflater.inflate(R.layout.fragment_account, container, false);
        // Inflate the layout for this fragment
        userName = (TextView) RootView.findViewById(R.id.nameView);
        plan = (TextView) RootView.findViewById(R.id.planView);
        talktimeBalanceView = (TextView) RootView.findViewById(R.id.talktimeBalanceView);
        dataBalanceView = (TextView) RootView.findViewById(R.id.dataBalanceView);
        phoneNumber = (TextView) RootView.findViewById(R.id.phoneNumber);
        rechargePlan = (TextView) RootView.findViewById(R.id.lastRecharge);
        validityDuration = (TextView) RootView.findViewById(R.id.validity);
        vodaCoin = (TextView)RootView.findViewById(R.id.vodaCoins);

        Button signOut = (Button) RootView.findViewById(R.id.buttonSignOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), PhoneNumber.class);
                startActivity(intent);
            }


//        vodaCoins.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadFragment(new VodaCoins());
//            }
//        });
//
//        scratchCards.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadFragment(new ScratchCards());
//            }
//        });

        });


        Button buttonScratchCards = (Button)RootView.findViewById(R.id.buttonScratchCards);
        buttonScratchCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new ScratchCards());
            }
        });






        //vodaCoins = RootView.findViewById(R.id.vodaCoins);
        //scratchCards = RootView.findViewById(R.id.scratchCards);

        //vodaCoins.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
                //loadFragment(new VodaCoins());
            //}
        //});

        //scratchCards.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
                //loadFragment(new ScratchCards());
            //}
        //});

        //vodaCoins = RootView.findViewById(R.id.vodaCoins);
        //scratchCards = RootView.findViewById(R.id.scratchCards);
        getDetails();
        return RootView;
    }

    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void getDetails(){
        new GetDetails().execute();
    }

    class GetDetails extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try{
                String finalPhone = phone.replace("+91","");
                String host = hostAddress+"?phoneNo="+finalPhone;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(host));

                HttpResponse response = client.execute(request);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer stringBuffer = new StringBuffer("");
                String line = "";

                while ((line = reader.readLine()) != null){
                    //Log.e("data",line);
                    stringBuffer.append(line);
                    break;
                }
                reader.close();
                result = stringBuffer.toString();
            }catch (Exception e){
                return new String("Exception :" + e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
            JSONObject jsonResult = new JSONObject(result);
            int success = jsonResult.getInt("success");

            if(success == 1){
                //Toast.makeText(getApplicationContext(), "Showing Customer Database.", Toast.LENGTH_SHORT).show();
                JSONArray customers = jsonResult.getJSONArray("content");
                for(int i = 0; i<customers.length(); i++){
                    JSONObject data = customers.getJSONObject(i);
                    String phoneNo = data.getString("pnumber");
                    String name = data.getString("name");
                    String planName = data.getString("plan");
                    String rechargeAmount = data.getString("recharge_amount");
                    String rechargeValidity = data.getString("recharge_validity");
                    String talktimeBalance = data.getString("talktime_balance");
                    String dataBalance = data.getString("data_balance");
                    String vodaCoinVal = data.getString("voda_coins");

                    Log.e("data",phoneNo);

                    userName.setText(name);
                    phoneNumber.setText(phoneNo);
                    rechargePlan.setText(rechargeAmount);
                    validityDuration.setText(rechargeValidity);
                    talktimeBalanceView.setText(talktimeBalance);
                    dataBalanceView.setText(dataBalance);
                    vodaCoin.setText(vodaCoinVal);
                    if(planName.equals("1")){
                        plan.setText("Prepaid");
                    }else if (planName.equals("0")){
                        plan.setText("Postpaid");
                    }else{
                        plan.setText("Plan Unavailable");
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        }
    }


}
