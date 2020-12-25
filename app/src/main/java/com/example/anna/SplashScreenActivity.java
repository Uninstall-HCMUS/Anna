package com.example.anna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 3500;

    //Variables
    Animation topAnim, bottomAnim;
    ImageView imageViewLogo;
    TextView textViewNameApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        //Animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anmation);

        imageViewLogo = findViewById(R.id.logoApp);
        textViewNameApp = findViewById(R.id.nameApp);

        imageViewLogo.setAnimation(topAnim);
        textViewNameApp.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                Boolean isStarted = preferences.getBoolean(getString(R.string.isStarted), false);
                if (!isStarted) {
                    Intent intent = new Intent(SplashScreenActivity.this, OnBoardingActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{

                    Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, SPLASH_SCREEN);
    }
}