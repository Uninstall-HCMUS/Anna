package com.example.anna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private Button nextBtn, backBtn;
    private TextView[] mDots;

    private OnBoardingSliderAdapter sliderAdapter;

    public List<OnBoardingInfor> mListSplScreen;

    private int currentPage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        Intent intent = getIntent();
        currentPage = intent.getIntExtra("page",0);
        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);
        nextBtn = (Button) findViewById(R.id.nextbtn);
        backBtn = (Button) findViewById(R.id.backbtn);

        mListSplScreen = new ArrayList<>();

        mListSplScreen.add(new OnBoardingInfor(R.drawable.intersting, R.string.intersting, R.string.intersting_desc));
        mListSplScreen.add(new OnBoardingInfor(R.drawable.creative, R.string.creative, R.string.creative_desc));
        mListSplScreen.add(new OnBoardingInfor(R.drawable.technical, R.string.technical, R.string.technical_desc));

        sliderAdapter = new OnBoardingSliderAdapter(this, mListSplScreen);
        mSlideViewPager.setAdapter(sliderAdapter);


        addDotIndicators(0);
        backBtn.setEnabled(false);
        backBtn.setVisibility(View.INVISIBLE);
        backBtn.setText("");

        mSlideViewPager.addOnPageChangeListener(viewListener);


        //OnClickListener
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(currentPage + 1);
                currentPage += 1;
                if (currentPage == mListSplScreen.size() + 1) {

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

                    Intent intent = new Intent(OnBoardingActivity.this, PreBeginActivity.class);
                    startActivity(intent);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(getString(R.string.isStarted),Boolean.TRUE);
                    editor.commit();

                    finish();

                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSlideViewPager.setCurrentItem(currentPage - 1);


            }
        });
    }
    public void addDotIndicators(int position) {

        mDots = new TextView[mListSplScreen.size()];
        mDotLayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.tranparentblue));

            mDotLayout.addView(mDots[i]);
        }

        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.blue));
        }
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDotIndicators(position);
            currentPage = position;
            if (currentPage == 0) {

                nextBtn.setEnabled(true);
                backBtn.setEnabled(false);
                backBtn.setVisibility(View.INVISIBLE);
                nextBtn.setText("Next");
                backBtn.setText("");

            } else if (currentPage == mListSplScreen.size() - 1) {

                nextBtn.setEnabled(true);
                backBtn.setEnabled(true);
                backBtn.setVisibility(View.VISIBLE);
                nextBtn.setText("Finish");
                backBtn.setText("Back");

            } else {
                nextBtn.setEnabled(true);
                backBtn.setEnabled(true);
                backBtn.setVisibility(View.VISIBLE);
                nextBtn.setText("Next");
                backBtn.setText("Back");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}