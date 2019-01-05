package com.example.martinrgb.roundconrner;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.martinrgb.roundconrner.smoothcorners.SketchSmoothCorners;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;

public class AnimationActivity extends AppCompatActivity {

    private SketchSmoothCorners sketchSmoothCorners;

    private static final SpringConfig mconfigScale = SpringConfig.fromOrigamiTensionAndFriction(60, 16);
    private static final SpringConfig mconfigRadius = SpringConfig.fromOrigamiTensionAndFriction(30, 15);
    private SpringSystem mSpringSystem;
    private Spring mSpring,mSpringRadius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deleteBars();
        setContentView(R.layout.activity_animation);

        sketchSmoothCorners = findViewById(R.id.sketchsmoothcorners);


        getDisplayPoint();
        setSpringSystem();
        findViewById(R.id.canvas_layout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    mSpring.setEndValue(1);
                    mSpringRadius.setEndValue(1);
                    return true;
                }

                if(event.getAction() == MotionEvent.ACTION_UP){
                    mSpring.setEndValue(0);
                    mSpringRadius.setEndValue(0);
                    return true;
                }
                return false;
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
                float widthVal = (float) SpringUtil.mapValueFromRangeToRange(value, 0, 1, 400, sWidth);
                float heightVal = (float) SpringUtil.mapValueFromRangeToRange(value, 0, 1, 400, sHeight);


                sketchSmoothCorners.setRectWidth(widthVal);
                sketchSmoothCorners.setRectHeight(heightVal);
            }
        });

        mSpringRadius = mSpringSystem.createSpring();
        mSpringRadius.setSpringConfig(mconfigRadius);
        mSpringRadius.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring mSpring) {
                float value = (float) mSpring.getCurrentValue();
                float radiusVal = (float) SpringUtil.mapValueFromRangeToRange(value, 0, 1, 100, 0);

                sketchSmoothCorners.setRoundRadius(radiusVal);
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
