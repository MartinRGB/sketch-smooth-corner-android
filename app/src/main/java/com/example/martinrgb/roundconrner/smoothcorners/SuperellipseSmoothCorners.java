package com.example.martinrgb.roundconrner.smoothcorners;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class SuperellipseSmoothCorners extends View {

    private float WIDTH = 400;
    private float HEIGHT = 400;
    private float SKETCH_ROUND_RECT_RADIUS = 50f;
    private float mCenterX = 0;
    private float mCenterY = 0;
    private Paint mPaint;
    private boolean ROUND_TL = true,ROUND_TR = true,ROUND_BL = true,ROUND_BR = true;
    private boolean isSquare = true;

    public SuperellipseSmoothCorners(Context context, @Nullable AttributeSet attrs) {
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
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(12);
        mPaint.setColor(Color.rgb(253,86,85));
    }

    public float getRoundRadius() {
        return SKETCH_ROUND_RECT_RADIUS;
    }

    public void setRoundRadius(float radius){
        this.SKETCH_ROUND_RECT_RADIUS = radius;
        this.invalidate();
    }

    public float getRectWidth() {
        return WIDTH;
    }

    public void setRectWidth(float WIDTH) {
        this.WIDTH = WIDTH;
        this.invalidate();
    }

    public float getRectHeight() {
        return HEIGHT;
    }

    public void setRectHeight(float HEIGHT) {
        this.HEIGHT = HEIGHT;
        this.invalidate();
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
        Path path;
        if(isSquare){
            path = SketchAlSmoothRect(0, 0, WIDTH , WIDTH , SKETCH_ROUND_RECT_RADIUS,SKETCH_ROUND_RECT_RADIUS,
                    ROUND_TL,ROUND_TR,ROUND_BL,ROUND_BR);
            canvas.drawPath(path,mPaint);
        }
        else{
            path = SketchAlSmoothRect(0, 0, WIDTH , HEIGHT , SKETCH_ROUND_RECT_RADIUS,SKETCH_ROUND_RECT_RADIUS,
                    ROUND_TL,ROUND_TR,ROUND_BL,ROUND_BR);
        }

        canvas.drawPath(path,mPaint);

    }

    public Path SketchAlSmoothRect(
            float left, float top, float right, float bottom, float rx, float ry,
            boolean tl, boolean tr, boolean bl, boolean br
    ){

        Path path = new Path();
        if (rx < 0) rx = 0;
        if (ry < 0) ry = 0;
        float width = right - left;
        float height = bottom - top;
        float posX = mCenterX - width/2;
        float posY = mCenterY - height/2;
        if(width > height){
            rx *= height/width;
        }
        else{
            ry *= width/height;
        }

        for(float i = 0.f;i<360;i ++){
            float angle = (float)(i * 2.f * Math.PI / 360.0);
            float cosX = (float) Math.cos(angle);
            float x = (float)Math.pow(Math.abs(cosX),rx/100.f)*50.f*Math.abs(cosX + 0.0000000001f)/(cosX + 0.0000000001f) + 50.f;
            float sinY = (float) Math.sin(angle);
            float y = (float)Math.pow(Math.abs(sinY),ry/100.f)*50.f*Math.abs(sinY +  0.0000000001f)/(sinY +  0.0000000001f) + 50.f;
            float percentX = x/100.f;
            float percentY = y/100.f;

            if (i == 0)
                path.moveTo(percentX*width+posX, percentY*height+posY);
            else if(!br && i<45){
                path.lineTo(width+posX, height+posY);
            }
            else if(!br && i>=45 && i<90){
                path.lineTo(posX+width/2, height+posY);
            }
            else if(!bl && i>=90 && i<135){
                path.lineTo(posX, height+posY);
            }
            else if(!bl && i>=135 && i<180){
                path.lineTo(posX, height/2+posY);
            }
            else if(!tl && i>=180 && i<225){
                path.lineTo(posX, posY);
            }
            else if(!tl && i>=225 && i<270){
                path.lineTo(posX+width/2, posY);
            }
            else if(!tr && i>=270 && i<315){
                path.lineTo(posX+width, posY);
            }
            else if(!tr && i>=315 && i<360){
                path.lineTo(posX+width, posY+height/2);
            }
            else
                path.lineTo(percentX*width+posX, percentY*height+posY);

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
        return minBorder/2.f;
    }
    private float getRadiusInMaxRange(float width,float height,float radius){
        float realRadius = Math.min(radius,getMAXRadius(width,height));
        return realRadius;
    }
}