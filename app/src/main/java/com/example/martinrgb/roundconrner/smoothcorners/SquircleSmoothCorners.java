package com.example.martinrgb.roundconrner.smoothcorners;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class SquircleSmoothCorners extends View {

    private float WIDTH = 400;
    private float HEIGHT = 400;
    private float SKETCH_SMOOTH_RECT_RADIUS = 4.f;
    private float mCenterX = 0;
    private float mCenterY = 0;
    private Paint mPaint;
    private float iterationPrecision = 0.25f;
    private boolean ROUND_TL = true,ROUND_TR = true,ROUND_BL = true,ROUND_BR = true;
    private boolean isSquare = true;

    public SquircleSmoothCorners(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterX = w * 1.0f / 2;
        mCenterY = h * 1.0f / 2;
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(12);
        mPaint.setColor(Color.rgb(253,86,85));
    }

    public float getRoundRadius() {
        return SKETCH_SMOOTH_RECT_RADIUS;
    }

    public void setRoundRadius(float radius){
        this.SKETCH_SMOOTH_RECT_RADIUS = radius;
        this.invalidate();
    }

    public float getRectHeight() {
        return HEIGHT;
    }

    public void setRectHeight(float HEIGHT) {
        this.HEIGHT = HEIGHT;
        this.invalidate();
    }

    public float getRectWidth() {
        return WIDTH;
    }

    public void setRectWidth(float WIDTH) {
        this.WIDTH = WIDTH;
        this.invalidate();
    }

    public float getIterationPrecision() {
        return iterationPrecision;
    }

    public void setIterationPrecision(float iterationPrecision) {
        this.iterationPrecision = 1/iterationPrecision;
    }

    public boolean isSquare() {
        return isSquare;
    }

    public void setIsSquare(boolean square) {
        isSquare = square;
        this.invalidate();
    }

    public void setRectRoundEnable(boolean tl,boolean tr,boolean bl,boolean br){
        this.ROUND_TL = tl;
        this.ROUND_TR = tr;
        this.ROUND_BL = bl;
        this.ROUND_BR = br;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(0, 0);
        mPaint.setAntiAlias(true);
        //Path path = SketchSmoothRect(0, 0, LENGTH , LENGTH , SKETCH_SMOOTH_RECT_RADIUS, ROUND_TL,ROUND_TR,ROUND_BL,ROUND_BR);

        if(isSquare){
            Path path = SketchSquareSmoothRect(0, 0, WIDTH , WIDTH , SKETCH_SMOOTH_RECT_RADIUS, ROUND_TL,ROUND_TR,ROUND_BL,ROUND_BR);
            canvas.drawPath(path,mPaint);
        }
        else{
            Path path;
            if(WIDTH >= HEIGHT){
                path = SketchWidthSmoothRect(0, 0, WIDTH , HEIGHT , SKETCH_SMOOTH_RECT_RADIUS, ROUND_TL,ROUND_TR,ROUND_BL,ROUND_BR);
            }
            else{
                path = SketchHeightSmoothRect(0, 0, WIDTH , HEIGHT , SKETCH_SMOOTH_RECT_RADIUS, ROUND_TL,ROUND_TR,ROUND_BL,ROUND_BR);
            }
            canvas.drawPath(path,mPaint);
        }

    }

    public Path SketchSquareSmoothRect(
            float left, float top, float right, float bottom, float radius,boolean tl, boolean tr, boolean bl, boolean br
    ){
        Path path = new Path();

        float m = radius;
        if (radius > 140) m = 140;
        if (radius < 0.00000000001) m = 0.00000000001f;
        float width = right - left;
        float height = bottom - top;
        float rx = width / 2;
        float ry = height / 2;
        float r = rx>ry? ry:rx;
        float w = width / 2;
        float h = height / 2;


        float startX = (-rx) + mCenterX;
        float startY = (float)(Math.pow(Math.abs(Math.pow(ry,m)-Math.pow(Math.abs(-ry),m)),1/m)) + mCenterY;
        path.moveTo(startX, startY);

        for (float i = 1; i < (2*r); i ++) {
            float x = (i-rx) + mCenterX;
            float y = (float)(Math.pow(Math.abs(Math.pow(ry,m)-Math.pow(Math.abs(i-ry),m)),1/m)) + mCenterY;

             if(!bl && i<r){
                 path.lineTo(mCenterX-w, mCenterY+h);
             }
             else if(!br && i>= r){
                 path.lineTo(mCenterX+w, mCenterY+h);
             }
             else{
                 path.lineTo(x, y);
             }

        }

        for (float i = (2*r); i < (4*r); i++) {
            float x = (3*rx-i) + mCenterX;
            float y = (float)(-Math.pow(Math.abs(Math.pow(ry,m)-Math.pow(Math.abs(3*ry-i),m)),1/m)) + mCenterY;

            if(!tr && i<3*r){
                path.lineTo(mCenterX+w, mCenterY-h);
            }
            else if(!tl && i>= 3*r){
                path.lineTo(mCenterX-w, mCenterY-h);
            }
            else{
                path.lineTo(x, y);
            }
        }

        path.close();
        return path;
    }


    public Path SketchWidthSmoothRect(float left, float top, float right, float bottom, float radius,boolean tl, boolean tr, boolean bl, boolean br){
        Path path = new Path();

        float m = radius;
        if (radius > 140) m = 140;
        if (radius < 0.00000000001) m = 0.00000000001f;
        float width = right - left;
        float height = bottom - top;
        float rx = width / 2;
        float ry = height / 2;

        float ratioY = ry/rx;

        float startX = (-rx)  + mCenterX;
        float startY = (float)(Math.pow(Math.abs(Math.pow(ry,m)-Math.pow(Math.abs(-ry),m)),1/m))*(ratioY) + mCenterY;
        path.moveTo(startX, startY);

        //bl
        for (float i = 0; i < ry; i+=iterationPrecision) {

            float x = (i-rx) + mCenterX;
            float y = (float)(Math.pow(Math.abs(Math.pow(rx,m)-Math.pow(Math.abs(i*(1/ratioY)-rx),m)),1/m))*(ratioY)+ mCenterY;

            if(!bl){
                path.lineTo(mCenterX-rx, mCenterY+ry);
            }
            else{
                path.lineTo(x, y);
            }

        }

        //br
        for (float i = ry; i > 0; i-=iterationPrecision) {
            float x = (rx-i)  + mCenterX;
            float y = (float)(Math.pow(Math.abs(Math.pow(rx,m)-Math.pow(Math.abs(rx-i*(1/ratioY)),m)),1/m))*(ratioY)+ mCenterY;

            if(!br){
                path.lineTo(mCenterX+rx, mCenterY+ry);
            }
            else{
                path.lineTo(x, y);
            }
        }

        //tr
        for (float i = 0; i < rx; i+=iterationPrecision) {
            float x = (rx-i)*(ratioY)  + mCenterX + (rx -ry);
            float y = (float)(-Math.pow(Math.abs(Math.pow(rx,m)-Math.pow(Math.abs(rx-i),m)),1/m))*(ratioY)  + mCenterY;

            if(!tr){
                path.lineTo(mCenterX + rx,mCenterY-ry);
            }
            else{
                path.lineTo(x, y);
            }
        }

        //tr
        for (float i = rx; i > 0; i-=iterationPrecision) {
            float x = (i - rx)*(ratioY)  + mCenterX - (rx -ry);
            float y = (float)(-Math.pow(Math.abs(Math.pow(rx,m)-Math.pow(Math.abs(i - rx),m)),1/m))*(ratioY)  + mCenterY;

            if(!tl){
                path.lineTo(mCenterX-rx, mCenterY-ry);
            }
            else{
                path.lineTo(x, y);
            }
        }

        path.close();
        return path;
    }

    public Path SketchHeightSmoothRect(float left, float top, float right, float bottom, float radius,boolean tl, boolean tr, boolean bl, boolean br){
        Path path = new Path();

        float m = radius;
        if (radius > 140) m = 140;
        if (radius < 0.00000000001) m = 0.00000000001f;
        float width = right - left;
        float height = bottom - top;
        float rx = width / 2;
        float ry = height / 2;


        float ratioY = ry/rx;

        float startX = (-rx)  + mCenterX;
        float startY = (float)(Math.pow(Math.abs(Math.pow(ry,m)-Math.pow(Math.abs(-ry),m)),1/m)) + mCenterY;
        path.moveTo(startX, startY);

        //bl
        for (float i = 0; i < rx; i+=iterationPrecision) {

            float x = (i-rx) + mCenterX;
            float y =  (float)(Math.pow(Math.abs(Math.pow(rx,m)-Math.pow(Math.abs(i-rx),m)),1/m))+ mCenterY + (ry-rx);

            if(!bl){
                path.lineTo(mCenterX-rx, mCenterY+ry);
            }
            else{
                path.lineTo(x, y);
            }

        }

        //br
        for (float i = rx; i > 0; i-=iterationPrecision) {
            float x = (rx-i)  + mCenterX;
            float y = (float)(Math.pow(Math.abs(Math.pow(rx,m)-Math.pow(Math.abs(rx-i),m)),1/m))+ mCenterY + (ry-rx);

            if(!br){
                path.lineTo(mCenterX+rx, mCenterY+ry);
            }
            else{
                path.lineTo(x, y);
            }
        }

        //tr
        for (float i = 0; i < rx; i+=iterationPrecision) {
            float x = (rx-i)  + mCenterX;
            float y = (float)(-Math.pow(Math.abs(Math.pow(rx,m)-Math.pow(Math.abs(rx-i),m)),1/m)) + mCenterY - (ry-rx);

            if(!tr){
                path.lineTo(mCenterX + rx,mCenterY-ry);
            }
            else{
                path.lineTo(x, y);
            }
        }

        //tr
        for (float i = rx; i > 0; i-=iterationPrecision) {
            float x = (i - rx)  + mCenterX;
            float y = (float)(-Math.pow(Math.abs(Math.pow(rx,m)-Math.pow(Math.abs(i - rx),m)),1/m))  + mCenterY - (ry-rx);

            if(!tl){
                path.lineTo(mCenterX-rx, mCenterY-ry);
            }
            else{
                path.lineTo(x, y );
            }
        }


        path.close();
        return path;
    }

    public float getMAXRadius(float width,float height){
        float minBorder;
        if(width > height){
            minBorder = height;
        }
        else{
            minBorder = width;
        }
        return minBorder/4.f;
    }
    private float getRadiusInMaxRange(float width,float height,float radius){
        float realRadius = Math.min(radius,getMAXRadius(width,height));
        return realRadius;
    }
}