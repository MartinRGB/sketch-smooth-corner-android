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
import com.example.martinrgb.roundconrner.smoothcorners.SuperellipseSmoothCorners;


public class SuperellipseActivity extends AppCompatActivity {

    private RelativeLayout mainLayout;
    private FrameLayout canvasLayout;
    private LinearLayout uiLayout;
    private int screenWidth,screenHeight;
    private float maxWidthRatio = 10.8f;

    private SeekBar widthBar,radiusBar,heightBar;
    private TextView widthText,radiusText,heightText;
    private SuperellipseSmoothCorners superellipseSmoothCorners;
    private float smoothWidth,smoothRadius,smoothHeight;

    private CheckBox tl_check,tr_check,bl_check,br_check,sq_check;
    private boolean tl = true,tr = true,bl = true,br = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_superellipse);
        mainLayout = findViewById(R.id.main_layout);
        canvasLayout = findViewById(R.id.canvas_layout);
        uiLayout = findViewById(R.id.ui_layout);
        getDisplayPoint();
        initViewNValue();
        initSeekBarkControl();
        initSquareCheckBox();
    }

    private void initViewNValue(){

        superellipseSmoothCorners = findViewById(R.id.superellipsesmoothcorners);
        smoothWidth = superellipseSmoothCorners.getRectWidth();
        smoothRadius = superellipseSmoothCorners.getRoundRadius();
        smoothHeight = superellipseSmoothCorners.getRectHeight();

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

    }

    private void initSeekBarkControl(){

        widthText.setText("Width: " + String.valueOf(superellipseSmoothCorners.getRectWidth()) +"f");
        heightText.setText("Height: "+String.valueOf(superellipseSmoothCorners.getRectHeight()) +"f");
        radiusText.setText("Radius: "+String.valueOf(superellipseSmoothCorners.getRoundRadius()*2.f) +"f");

        widthBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                smoothWidth = progress * maxWidthRatio;
                float maxVal = Math.max(smoothWidth,smoothHeight);
                if(superellipseSmoothCorners.isSquare()){
                    widthText.setText("Width: " + String.valueOf(maxVal) +"f");
                    heightText.setText("Height: " + String.valueOf(maxVal) +"f");
                    heightBar.setProgress(progress);
                    superellipseSmoothCorners.setRectWidth(maxVal);
                    superellipseSmoothCorners.setRectHeight(maxVal);
                }
                else{
                    widthText.setText("Width: " + String.valueOf(smoothWidth) +"f");
                    superellipseSmoothCorners.setRectWidth(smoothWidth);
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
                if(superellipseSmoothCorners.isSquare()){
                    widthText.setText("Width: " + String.valueOf(maxVal) +"f");
                    heightText.setText("Height: " + String.valueOf(maxVal) +"f");
                    widthBar.setProgress(progress);
                    superellipseSmoothCorners.setRectWidth(maxVal);
                    superellipseSmoothCorners.setRectHeight(maxVal);
                }
                else{
                    heightText.setText("Height: " + String.valueOf(smoothHeight) +"f");
                    superellipseSmoothCorners.setRectHeight(smoothHeight);
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
                smoothRadius = progress/100.f*100.f;
                radiusText.setText("Radius: " + String.valueOf(Math.round(smoothRadius*1.f/100.f* superellipseSmoothCorners.getMAXRadius(superellipseSmoothCorners.getRectWidth(), superellipseSmoothCorners.getRectHeight()))) +".0f");
                superellipseSmoothCorners.setRoundRadius((smoothRadius));
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
                superellipseSmoothCorners.setRectRoundEnable(tl,tr,bl,br);
            }
        });

        tr_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                tr = isChecked;
                superellipseSmoothCorners.setRectRoundEnable(tl,tr,bl,br);
            }
        });

        bl_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bl = isChecked;
                superellipseSmoothCorners.setRectRoundEnable(tl,tr,bl,br);
            }
        });

        br_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                br= isChecked;
                superellipseSmoothCorners.setRectRoundEnable(tl,tr,bl,br);
            }
        });

        sq_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    superellipseSmoothCorners.setIsSquare(true);
                }
                else{
                    superellipseSmoothCorners.setIsSquare(false);
                }
            }
        });
    }

    //TODO: More accurate seekbar control
    private void reComputeRadiusProgress(){
        float currentRadiusText = smoothRadius/50.f* superellipseSmoothCorners.getMAXRadius(superellipseSmoothCorners.getRectWidth(), superellipseSmoothCorners.getRectHeight());
        float MinValue = superellipseSmoothCorners.getMAXRadius(superellipseSmoothCorners.getRectWidth(), superellipseSmoothCorners.getRectHeight())*1.f;
        radiusText.setText("Radius: " + String.valueOf(Math.round(Math.min(currentRadiusText,MinValue)) +".0f"));
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
