package com.example.otplogin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    Animation animationUptoDown , animationDownToUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        textView = findViewById(R.id.txt_v);
        imageView = findViewById(R.id.image_view);
        animationUptoDown = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.uptodownanim);
        animationDownToUp = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.downtotopanim);

        imageView.setAnimation(animationUptoDown);
        textView.setAnimation(animationDownToUp);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, PhoneNumber.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        },4000);
    }
}
