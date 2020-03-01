package com.example.otplogin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
//import android.support.v7.widget.CardView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

public class RechargeUnlimitedFragment extends Fragment {

    CardView cardview;
    LayoutParams layoutparams;
    LayoutParams layoutparamsLinear;
    View Rootview;
    CardView firstCardView;
    CardView secondCardView;
    Context context;

    String hostAddress = "http://10.10.40.58/vil/getRechargeData.php";

    public RechargeUnlimitedFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Rootview = inflater.inflate(R.layout.recharge_unlimited_fragment, container, false);

        context = getActivity().getApplicationContext();
        getDetails();
        firstCardView= (CardView) Rootview.findViewById(R.id.firstCardView);
        firstCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),RazorPayGateway.class);
                intent.putExtra("RechargeUnlimitedFragment","399");
                startActivity(intent);
            }
        });
        secondCardView = (CardView) Rootview.findViewById(R.id.secondCardView);secondCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(),RazorPayGateway.class);
                intent1.putExtra("RechargeUnlimitedFragment","249");
                startActivity(intent1);
            }
        });
        return Rootview;
    }

    public void getDetails() {
        new GetDetails().execute();
    }


    class GetDetails extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                String host = hostAddress + "?rechargeType=2";
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(host));

                HttpResponse response = client.execute(request);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer stringBuffer = new StringBuffer("");
                String line = "";

                while ((line = reader.readLine()) != null) {
                    //Log.e("data",line);
                    stringBuffer.append(line);
                    break;
                }
                reader.close();
                result = stringBuffer.toString();
            } catch (Exception e) {
                return new String("Exception :" + e.getMessage());
            }
            return result;
        }
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonResult = new JSONObject(result);
                int success = jsonResult.getInt("success");

                if(success == 1){
                    //Toast.makeText(getApplicationContext(), "Showing Customer Database.", Toast.LENGTH_SHORT).show();
                    JSONArray customers = jsonResult.getJSONArray("content");
                    for(int i = 0; i<customers.length(); i++){
                        JSONObject data = customers.getJSONObject(i);
                        String rechargeAmount = data.getString("recharge_amount");
                        String rechargeValidity = data.getString("recharge_validity");
                        String talktimeBalance = data.getString("talktime_balance_provided");
                        String dataBalance = data.getString("dala_balance_provided");

                        Log.e("data",rechargeAmount+" ; "+rechargeValidity+" ; "+talktimeBalance+" ; "+dataBalance);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
