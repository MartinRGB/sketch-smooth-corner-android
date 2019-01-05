package com.example.martinrgb.roundconrner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deleteBars();
        setContentView(R.layout.activity_main);
    }

    public void transitionToRound(View v) {startActivity(new Intent(this, RoundActivity.class));}
    public void transitionToSquircle(View v) {startActivity(new Intent(this, SquircleActivity.class));}
    public void transitionToSuperellipse(View v) {startActivity(new Intent(this, SuperellipseActivity.class));}
    public void transitionToSmooth(View v) {startActivity(new Intent(this, SmoothActivity.class));}
    public void transitionToAnimation(View v) {startActivity(new Intent(this, AnimationActivity.class));}

    private void deleteBars(){
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
    }

}
