package com.example.martinrgb.roundconrner;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.martinrgb.roundconrner.roundcorners.QuadRoundCorners;
import com.example.martinrgb.roundconrner.roundcorners.AndroidRoundCorners;
import com.example.martinrgb.roundconrner.roundcorners.SketchRoundCorners;

public class RoundActivity extends AppCompatActivity {

    private RelativeLayout mainLayout;
    private FrameLayout canvasLayout;
    private LinearLayout uiLayout;
    private int screenWidth,screenHeight;
    private float maxWidthRatio = 10.8f;

    private SeekBar heightBar,widthBar,radiusBar;
    private TextView heightText,widthText,radiusText;
    private SketchRoundCorners sketchRoundCorners;
    private QuadRoundCorners quadRoundCorners;
    private AndroidRoundCorners androidRoundCorners;
    private Switch alertnativeSwitch,androidSwitch,sketchRoundSwitch;
    private float roundHeight,roundWidth,roundRadius;

    private CheckBox tl_check,tr_check,bl_check,br_check;
    private boolean tl = true,tr = true,bl = true,br = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_round);
        mainLayout = findViewById(R.id.main_layout);
        canvasLayout = findViewById(R.id.canvas_layout);
        uiLayout = findViewById(R.id.ui_layout);
        getDisplayPoint();
        initViewNValue();
        initSeekBarkControl();
        initSwitcher();
        initSquareCheckBox();
    }

    private void initViewNValue(){

        sketchRoundCorners = findViewById(R.id.sketchroundcorners);
        quadRoundCorners = findViewById(R.id.quadroundcorners);
        androidRoundCorners = findViewById(R.id.androidroundcorners);
        roundHeight = sketchRoundCorners.getRectHeight();
        roundWidth = sketchRoundCorners.getRectWidth();
        roundRadius = sketchRoundCorners.getRoundRadius();

        alertnativeSwitch = findViewById(R.id.alertnative_switch);
        androidSwitch = findViewById(R.id.android_switch);
        sketchRoundSwitch = findViewById(R.id.sketchround_switch);

        tl_check = findViewById(R.id.tl_check);
        tr_check = findViewById(R.id.tr_check);
        bl_check = findViewById(R.id.bl_check);
        br_check = findViewById(R.id.br_check);

        widthBar = findViewById(R.id.width);
        heightBar = findViewById(R.id.height);
        radiusBar = findViewById(R.id.radius);
        heightText = findViewById(R.id.height_text);
        widthText = findViewById(R.id.width_text);
        radiusText = findViewById(R.id.radius_text);

    }

    private void initSwitcher(){
        alertnativeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    quadRoundCorners.setVisibility(View.VISIBLE);
                }
                else{
                    quadRoundCorners.setVisibility(View.INVISIBLE);
                }
            }
        });

        androidSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    androidRoundCorners.setVisibility(View.VISIBLE);
                }
                else{
                    androidRoundCorners.setVisibility(View.INVISIBLE);
                }
            }
        });

        sketchRoundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sketchRoundCorners.setVisibility(View.VISIBLE);
                }
                else{
                    sketchRoundCorners.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    private void initSeekBarkControl(){

        heightText.setText("Height: " + String.valueOf(sketchRoundCorners.getRectHeight()) +"f");
        widthText.setText("Width: " + String.valueOf(sketchRoundCorners.getRectWidth()) +"f");
        radiusText.setText("Radius: "+String.valueOf(sketchRoundCorners.getRoundRadius()) +"f");

        widthBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                roundWidth = progress * maxWidthRatio;
                widthText.setText("Width: " + String.valueOf(roundWidth) +"f");
                sketchRoundCorners.setRectWidth(roundWidth);
                androidRoundCorners.setRectWidth(roundWidth);
                quadRoundCorners.setRectWidth(roundWidth);
                reComputeRadiusProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        heightBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                roundHeight = progress * maxWidthRatio;
                heightText.setText("Height: " + String.valueOf(roundHeight) +"f");
                sketchRoundCorners.setRectHeight(roundHeight);
                androidRoundCorners.setRectHeight(roundHeight);
                quadRoundCorners.setRectHeight(roundHeight);
                reComputeRadiusProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        radiusBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                roundRadius = progress/100.f* sketchRoundCorners.getMAXRadius(sketchRoundCorners.getRectWidth(), sketchRoundCorners.getRectHeight());
                radiusText.setText("Radius: " + String.valueOf(Math.round(roundRadius) +".0f"));
                sketchRoundCorners.setRoundRadius((roundRadius));
                androidRoundCorners.setRoundRadius((roundRadius));
                quadRoundCorners.setRoundRadius((roundRadius));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initSquareCheckBox(){
        tl_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tl = isChecked;
                sketchRoundCorners.setRectRoundEnable(tl,tr,bl,br);
                androidRoundCorners.setRectRoundEnable(tl,tr,bl,br);
                quadRoundCorners.setRectRoundEnable(tl,tr,bl,br);
            }
        });

        tr_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                tr = isChecked;
                sketchRoundCorners.setRectRoundEnable(tl,tr,bl,br);
                androidRoundCorners.setRectRoundEnable(tl,tr,bl,br);
                quadRoundCorners.setRectRoundEnable(tl,tr,bl,br);
            }
        });

        bl_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bl = isChecked;
                sketchRoundCorners.setRectRoundEnable(tl,tr,bl,br);
                androidRoundCorners.setRectRoundEnable(tl,tr,bl,br);
                quadRoundCorners.setRectRoundEnable(tl,tr,bl,br);
            }
        });

        br_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                br= isChecked;
                sketchRoundCorners.setRectRoundEnable(tl,tr,bl,br);
                androidRoundCorners.setRectRoundEnable(tl,tr,bl,br);
                quadRoundCorners.setRectRoundEnable(tl,tr,bl,br);
            }
        });
    }

    //TODO: More accurate seekbar control
    private void reComputeRadiusProgress(){
        float currentRadiusText = roundRadius/200.f* sketchRoundCorners.getMAXRadius(sketchRoundCorners.getRectWidth(), sketchRoundCorners.getRectHeight());
        float MinValue = sketchRoundCorners.getMAXRadius(sketchRoundCorners.getRectWidth(), sketchRoundCorners.getRectHeight());
        radiusText.setText("Radius: " + String.valueOf(Math.round(Math.min(currentRadiusText,MinValue)) +".0f"));

        if(roundRadius > sketchRoundCorners.getRectWidth()/2 || roundRadius > sketchRoundCorners.getRectHeight()/2){
            sketchRoundCorners.setRoundRadius((roundRadius));
            androidRoundCorners.setRoundRadius((roundRadius));
            quadRoundCorners.setRoundRadius((roundRadius));
        }
        else{
            //not work here
        }

    }

    // ##### program mark - Util #####
    private void getDisplayPoint() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        screenWidth = point.x;
        screenHeight = point.y;
        Log.e("inPX","the screen size is "+point.toString());
    }


}
