package com.example.martinrgb.roundconrner;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.martinrgb.roundconrner.customview.SmoothCornersImage;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;

public class PrototypeActivity extends AppCompatActivity {

    private SmoothCornersImage smoothCornersImage;

    private static final SpringConfig mconfigScale = SpringConfig.fromOrigamiTensionAndFriction(60, 16);

    private static final SpringConfig mconfigAlpha = SpringConfig.fromOrigamiTensionAndFriction(80, 12);

    private static final SpringConfig mconfigNav = SpringConfig.fromOrigamiTensionAndFriction(100, 12);
    private SpringSystem mSpringSystem;

    private Spring mSpring,mAlphaSpring,mNavSpring;
    private boolean isShowDetail = false;

    private float initW,initH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deleteBars();
        setContentView(R.layout.activity_prototype);

        smoothCornersImage = findViewById(R.id.smooth_iv);


        getDisplayPoint();
        setSpringSystem();
        findViewById(R.id.smooth_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isShowDetail){
                    mSpring.setEndValue(1);
                    mAlphaSpring.setEndValue(1);
                    mNavSpring.setEndValue(1);

                }
                else{
                    mSpring.setEndValue(0);
                    mAlphaSpring.setEndValue(0);
                    mNavSpring.setEndValue(0);
                }
                isShowDetail = !isShowDetail;
            }
        });


    }



    private void setSpringSystem() {
        SpringSystem mSpringSystem = SpringSystem.create();
        mSpring = mSpringSystem.createSpring();
        mSpring.setSpringConfig(mconfigScale);
        mSpring.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring mSpring) {
                float value = (float) mSpring.getCurrentValue();

                float XVal = (float) SpringUtil.mapValueFromRangeToRange(value, 0, 1, 0, -19);
                float YVal = (float) SpringUtil.mapValueFromRangeToRange(value, 0, 1, 0, -918);
                float widthVal = (float) SpringUtil.mapValueFromRangeToRange(value, 0, 1, 1005, sWidth);
                float heightVal = (float) SpringUtil.mapValueFromRangeToRange(value, 0, 1, 420, sWidth);
                float radiusVal = (float) SpringUtil.mapValueFromRangeToRange(value, 0, 1, 60, 0);
                float scaleVal = (float) SpringUtil.mapValueFromRangeToRange(value, 0, 1, 1, 1.1);

                float reverseHeightVal = (float) SpringUtil.mapValueFromRangeToRange(value, 0, 1, (sWidth - 420)/2, 0);

                smoothCornersImage.setRectWidth(widthVal);
                smoothCornersImage.setRectHeight(heightVal);
                smoothCornersImage.getLayoutParams().height = (int)heightVal;
                smoothCornersImage.getLayoutParams().width = (int)widthVal;
                smoothCornersImage.setTranslationX(XVal);
                smoothCornersImage.setTranslationY(YVal);
                smoothCornersImage.setRoundRadius(radiusVal);
                smoothCornersImage.setScaleX(scaleVal);
                smoothCornersImage.requestLayout();

                findViewById(R.id.smooth_text_iv).setTranslationY(-(heightVal-420)/2);
                findViewById(R.id.detail_text_iv).setTranslationY(reverseHeightVal);


                float transVal = (float) SpringUtil.mapValueFromRangeToRange(value, 0, 1, 0, 1170);

                findViewById(R.id.top_iv).setTranslationY(-transVal);
                findViewById(R.id.bottom_iv).setTranslationY(transVal);

            }
        });


        mAlphaSpring = mSpringSystem.createSpring();
        mAlphaSpring.setSpringConfig(mconfigAlpha);
        mAlphaSpring.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring mSpring) {
                float value = (float) mSpring.getCurrentValue();
                float alphaVal = (float) SpringUtil.mapValueFromRangeToRange(value, 0, 1, 1, 0);
                float reverseAlpha = (float) SpringUtil.mapValueFromRangeToRange(value, 0.5, 1, 0, 1);

                findViewById(R.id.top_iv).setAlpha(alphaVal);
                findViewById(R.id.bottom_iv).setAlpha(alphaVal);
                findViewById(R.id.smooth_text_iv).setAlpha(alphaVal);
                findViewById(R.id.detail_text_iv).setAlpha(reverseAlpha);
            }
        });

        mNavSpring = mSpringSystem.createSpring();
        mNavSpring.setSpringConfig(mconfigAlpha);
        mNavSpring.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring mSpring) {
                float value = (float) mSpring.getCurrentValue();
                float transVal = (float) SpringUtil.mapValueFromRangeToRange(value, 0, 1, 0, 300);

                findViewById(R.id.nav_iv).setTranslationY(transVal);
            }
        });

    }

    private float sWidth,sHeight;
    private void getDisplayPoint() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        sWidth = (float)point.x;
        sHeight = (float)point.y;
    }

    private void deleteBars(){
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
    }

}
