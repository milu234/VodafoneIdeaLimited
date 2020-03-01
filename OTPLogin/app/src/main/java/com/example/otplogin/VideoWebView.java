package com.example.otplogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class VideoWebView extends AppCompatActivity {
    WebView webView;
    WebSettings webSettings;
    long startTime,endTime;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_web_view);

        webView = findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/game.html");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        webSettings = webView.getSettings();

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                startTime = System.currentTimeMillis();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                endTime = System.currentTimeMillis();
                new callScratchCards();
            }
        });
        giveCredits(startTime, endTime);
    }

    class callScratchCards extends Activity {
        @Nullable
        public void onCreate(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            loadFragment(new ScratchCards());
        }
    }

    public void giveCredits(long start, long end){
        long duration = end - start;
        long seconds = duration/1000;
        long minutes = seconds/60;

        if(minutes>5){
            UpdateCoins.updateVodaCoins("50");
        }else if(minutes>3){
            UpdateCoins.updateVodaCoins("30");
        }else if (minutes > 1){
            UpdateCoins.updateVodaCoins("15");
        }else{
            UpdateCoins.updateVodaCoins("5");
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()){
            this.webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode,event);

    }

    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
