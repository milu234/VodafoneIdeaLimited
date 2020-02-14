package com.example.otplogin;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;


class Connection extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        String host = params[0];
        try{
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(host));

            HttpResponse response = client.execute(request);
            //Log.e("data", response.toString());

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
    protected void onPostExecute(String result){
        // Parsing the JSON data
    }
}