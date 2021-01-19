package com.example.anna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PreBeginActivity extends AppCompatActivity {

    Button startBtn,backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_begin);
        startBtn = (Button) findViewById(R.id.startButton_begin);
        backBtn = (Button) findViewById(R.id.backbutton_prebegin);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreBeginActivity.this,BeginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreBeginActivity.this,OnBoardingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("page",3);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }
}