package com.example.martinrgb.roundconrner;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.martinrgb.roundconrner.smoothcorners.SketchSmoothCorners;


public class SmoothActivity extends AppCompatActivity {

    private RelativeLayout mainLayout;
    private FrameLayout canvasLayout;
    private LinearLayout uiLayout;
    private int screenWidth,screenHeight;
    private float maxWidthRatio = 10.8f;

    private SeekBar widthBar,radiusBar,heightBar;
    private TextView widthText,radiusText,heightText;
    private SketchSmoothCorners sketchSmoothCorners;
    private float smoothWidth,smoothHeight,smoothRadiusPercentage;

    private CheckBox tl_check,tr_check,bl_check,br_check,sq_check;
    private boolean tl = true,tr = true,bl = true,br = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_smooth);
        mainLayout = findViewById(R.id.main_layout);
        canvasLayout = findViewById(R.id.canvas_layout);
        uiLayout = findViewById(R.id.ui_layout);
        getDisplayPoint();
        initViewNValue();
        initSeekBarkControl();
        initSquareCheckBox();
    }

    private void initViewNValue(){

        sketchSmoothCorners = findViewById(R.id.sketchsmoothcorners);
        smoothWidth = sketchSmoothCorners.getRectWidth();
        smoothHeight = sketchSmoothCorners.getRectHeight();

        tl_check = findViewById(R.id.tl_check);
        tr_check = findViewById(R.id.tr_check);
        bl_check = findViewById(R.id.bl_check);
        br_check = findViewById(R.id.br_check);
        sq_check = findViewById(R.id.ratio_check);

        widthBar = findViewById(R.id.width);
        heightBar = findViewById(R.id.height);
        radiusBar = findViewById(R.id.radius);
        heightText = findViewById(R.id.height_text);
        widthText = findViewById(R.id.width_text);
        radiusText = findViewById(R.id.radius_text);


        smoothRadiusPercentage = radiusBar.getProgress();

    }

    private void initSeekBarkControl(){

        widthText.setText("Width: " + String.valueOf(sketchSmoothCorners.getRectWidth()) +"f");
        heightText.setText("Height: "+String.valueOf(sketchSmoothCorners.getRectHeight()) +"f");
        radiusText.setText("Radius: "+String.valueOf(sketchSmoothCorners.getRoundRadius()*1.f) +"f");

        widthBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                smoothWidth = progress * maxWidthRatio;
                float maxVal = Math.max(smoothWidth,smoothHeight);
                if(sketchSmoothCorners.isSquare()){
                    widthText.setText("Width: " + String.valueOf(maxVal) +"f");
                    heightText.setText("Height: " + String.valueOf(maxVal) +"f");
                    heightBar.setProgress(progress);
                    sketchSmoothCorners.setRectWidth(maxVal);
                    sketchSmoothCorners.setRectHeight(maxVal);
                }
                else{
                    widthText.setText("Width: " + String.valueOf(smoothWidth) +"f");
                    sketchSmoothCorners.setRectWidth(smoothWidth);
                }

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
                smoothHeight = progress * maxWidthRatio;
                float maxVal = Math.max(smoothWidth,smoothHeight);
                if(sketchSmoothCorners.isSquare()){
                    widthText.setText("Width: " + String.valueOf(maxVal) +"f");
                    heightText.setText("Height: " + String.valueOf(maxVal) +"f");
                    widthBar.setProgress(progress);
                    sketchSmoothCorners.setRectWidth(maxVal);
                    sketchSmoothCorners.setRectHeight(maxVal);
                }
                else{
                    heightText.setText("Height: " + String.valueOf(smoothHeight) +"f");
                    sketchSmoothCorners.setRectHeight(smoothHeight);
                }
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
                smoothRadiusPercentage = progress/100.f*100.f;
                radiusText.setText("Radius: " + String.valueOf(Math.round(smoothRadiusPercentage/100.f* sketchSmoothCorners.getMAXRadius(sketchSmoothCorners.getRectWidth(), sketchSmoothCorners.getRectHeight()))) +".0f");
                sketchSmoothCorners.setRoundRadius((smoothRadiusPercentage/100.f* sketchSmoothCorners.getMAXRadius(sketchSmoothCorners.getRectWidth(), sketchSmoothCorners.getRectHeight())));
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
                sketchSmoothCorners.setRectRoundEnable(tl,tr,bl,br);
            }
        });

        tr_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                tr = isChecked;
                sketchSmoothCorners.setRectRoundEnable(tl,tr,bl,br);
            }
        });

        bl_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bl = isChecked;
                sketchSmoothCorners.setRectRoundEnable(tl,tr,bl,br);
            }
        });

        br_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                br= isChecked;
                sketchSmoothCorners.setRectRoundEnable(tl,tr,bl,br);
            }
        });

        sq_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sketchSmoothCorners.setIsSquare(true);
                }
                else{
                    sketchSmoothCorners.setIsSquare(false);
                }
            }
        });
    }

    //TODO: More accurate seekbar control
    private void reComputeRadiusProgress(){
        float currentRadiusText = smoothRadiusPercentage/100.f* sketchSmoothCorners.getMAXRadius(sketchSmoothCorners.getRectWidth(), sketchSmoothCorners.getRectHeight());
        float MinValue = sketchSmoothCorners.getMAXRadius(sketchSmoothCorners.getRectWidth(), sketchSmoothCorners.getRectHeight())*1.f;
        radiusText.setText("Radius: " + String.valueOf(Math.round(Math.min(currentRadiusText,MinValue)) +".0f"));
        sketchSmoothCorners.setRoundRadius(Math.round(Math.min(currentRadiusText,MinValue)));
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
