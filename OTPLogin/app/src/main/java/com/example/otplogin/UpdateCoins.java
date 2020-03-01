package com.example.otplogin;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

public class UpdateCoins extends Activity {

    static String hostAddress = "http://10.10.40.58/vil/updateVodaCoins.php";
    public static void updateVodaCoins(String field){
        new UpdateVodaCoins().execute(field);
    }

    static class UpdateVodaCoins extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try{
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String phone  = user.getPhoneNumber();
                String finalPhone = phone.replace("+91","");
                Log.e("data", String.valueOf(params[0])+" ; "+finalPhone);
                String host = hostAddress+"?phoneNo="+finalPhone+"&coins="+params[0];
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
        }
    }
}
