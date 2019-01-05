package com.example.martinrgb.roundconrner.roundcorners;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class AndroidRoundCorners extends View {

    private float WIDTH = 400;
    private float HEIGHT = 400;
    private float ROUND_RECT_RADIUS = 100f;
    private float mCenterX = 0;
    private float mCenterY = 0;
    private Paint mPaint;
    private Paint mClearPaint;
    private boolean ROUND_TL = true,ROUND_TR = true,ROUND_BL = true,ROUND_BR = true;

    public AndroidRoundCorners(Context context, @Nullable AttributeSet attrs) {
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
        mPaint.setColor(Color.rgb(21,52,208));
    }

    public void setRoundRadius(float radius){
        this.ROUND_RECT_RADIUS = getRadiusInMaxRange(WIDTH,HEIGHT,radius);
        this.invalidate();
    }

    public float getRoundRadius() {
        return ROUND_RECT_RADIUS;
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
        canvas.translate(mCenterX-WIDTH/2, mCenterY-HEIGHT/2);
        mPaint.setAntiAlias(true);

        mClearPaint = new Paint();
        mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        drawAndroidRoundRect(0,0,WIDTH,HEIGHT,ROUND_RECT_RADIUS,ROUND_RECT_RADIUS,ROUND_TL,ROUND_TR,ROUND_BL,ROUND_BR,canvas);

    }

    public void drawAndroidRoundRect(
            float left, float top, float right, float bottom, float rx, float ry,
            boolean tl, boolean tr, boolean bl, boolean br,Canvas canvas
    ){
        float w = right - left;
        float h = bottom - top;
        final Rect rect = new Rect((int)left, (int)top, (int)right,(int)bottom);
        final RectF rectF = new RectF(rect);
        canvas.drawRoundRect(rectF,rx,ry, mPaint);

        if(!tl){
            canvas.drawRect(0, 0, w/2, h/2, mClearPaint);
            Path path = new Path();
            path.moveTo(0,h/2);
            path.lineTo(0,0);
            path.lineTo(w/2,0);
            canvas.drawPath(path,mPaint);
        }
        if(!tr){
            canvas.drawRect(w/2, 0, w, h/2, mClearPaint);
            Path path = new Path();
            path.moveTo(w/2,0);
            path.lineTo(w,0);
            path.lineTo(w,h/2);
            canvas.drawPath(path,mPaint);
        }

        if(!bl){
            canvas.drawRect(0, h/2, w/2, h, mClearPaint);
            Path path = new Path();
            path.moveTo(0,h/2);
            path.lineTo(0,h);
            path.lineTo(w/2,h);
            canvas.drawPath(path,mPaint);
        }

        if(!br){
            canvas.drawRect(w/2, h/2, w, h, mClearPaint);
            Path path = new Path();
            path.moveTo(w/2,h);
            path.lineTo(w,h);
            path.lineTo(w,h/2);
            canvas.drawPath(path,mPaint);
        }

    }

    public float getMAXRadius(float width,float height){
        float minBorder;
        if(width > height){
            minBorder = height;
        }
        else{
            minBorder = width;
        }
        return minBorder/2;
    }
    private float getRadiusInMaxRange(float width,float height,float radius){
        float realRadius = Math.min(radius,getMAXRadius(width,height));
        return realRadius;
    }
}