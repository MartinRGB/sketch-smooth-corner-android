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

import com.example.martinrgb.roundconrner.smoothcorners.SquircleSmoothCorners;

public class SquircleActivity extends AppCompatActivity {

    private RelativeLayout mainLayout;
    private FrameLayout canvasLayout;
    private LinearLayout uiLayout;
    private int screenWidth,screenHeight;
    private float maxWidthRatio = 10.8f;

    private SeekBar widthBar,radiusBar,heightBar;
    private TextView widthText,radiusText,heightText;
    private SquircleSmoothCorners squircleSmoothCorners;
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
        setContentView(R.layout.activity_squircle);
        mainLayout = findViewById(R.id.main_layout);
        canvasLayout = findViewById(R.id.canvas_layout);
        uiLayout = findViewById(R.id.ui_layout);
        getDisplayPoint();
        initViewNValue();
        initSeekBarkControl();
        initSquareCheckBox();
    }

    private void initViewNValue(){

        squircleSmoothCorners = findViewById(R.id.squirclesmoothcorners);
        smoothWidth = squircleSmoothCorners.getRectWidth();
        smoothHeight = squircleSmoothCorners.getRectHeight();
        smoothRadius = squircleSmoothCorners.getRoundRadius();

        tl_check = findViewById(R.id.tl_check);
        tr_check = findViewById(R.id.tr_check);
        bl_check = findViewById(R.id.bl_check);
        br_check = findViewById(R.id.br_check);
        sq_check = findViewById(R.id.ratio_check);

        widthBar = findViewById(R.id.width);
        radiusBar = findViewById(R.id.radius);
        heightBar = findViewById(R.id.height);
        widthText = findViewById(R.id.width_text);
        radiusText = findViewById(R.id.radius_text);
        heightText = findViewById(R.id.height_text);

    }

    private void initSeekBarkControl(){

        widthText.setText("Width: " + String.valueOf(squircleSmoothCorners.getRectWidth()) +"f");
        heightText.setText("Height: "+String.valueOf(squircleSmoothCorners.getRectHeight() + "f"));
        radiusText.setText("Radius: "+String.valueOf(squircleSmoothCorners.getRoundRadius() + "f"));

        widthBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                smoothWidth = progress * maxWidthRatio;
                float maxVal = Math.max(smoothWidth,smoothHeight);
                if(squircleSmoothCorners.isSquare()){
                    widthText.setText("Width: " + String.valueOf(maxVal) +"f");
                    heightText.setText("Height: " + String.valueOf(maxVal) +"f");
                    heightBar.setProgress(progress);
                    squircleSmoothCorners.setRectWidth(maxVal);
                    squircleSmoothCorners.setRectHeight(maxVal);
                }
                else{
                    widthText.setText("Width: " + String.valueOf(smoothWidth) +"f");
                    squircleSmoothCorners.setRectWidth(smoothWidth);
                }
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
                if(squircleSmoothCorners.isSquare()){
                    widthText.setText("Width: " + String.valueOf(maxVal) +"f");
                    heightText.setText("Height: " + String.valueOf(maxVal) +"f");
                    widthBar.setProgress(progress);
                    squircleSmoothCorners.setRectWidth(maxVal);
                    squircleSmoothCorners.setRectHeight(maxVal);
                }
                else{
                    heightText.setText("Height: " + String.valueOf(smoothHeight) +"f");
                    squircleSmoothCorners.setRectHeight(smoothHeight);
                }
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
                smoothRadius = progress/4f + 2.0f;
                radiusText.setText("Radius: " + String.valueOf((smoothRadius)) +"f");
                squircleSmoothCorners.setRoundRadius(smoothRadius);
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
                squircleSmoothCorners.setRectRoundEnable(tl,tr,bl,br);
            }
        });

        tr_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                tr = isChecked;
                squircleSmoothCorners.setRectRoundEnable(tl,tr,bl,br);
            }
        });

        bl_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bl = isChecked;
                squircleSmoothCorners.setRectRoundEnable(tl,tr,bl,br);
            }
        });

        br_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                br= isChecked;
                squircleSmoothCorners.setRectRoundEnable(tl,tr,bl,br);
            }
        });

        sq_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    squircleSmoothCorners.setIsSquare(true);
                }
                else{
                    squircleSmoothCorners.setIsSquare(false);
                }
            }
        });
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
