package com.example.martinrgb.roundconrner.roundcorners;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class QuadRoundCorners extends View {

    private float WIDTH = 400;
    private float HEIGHT = 400;
    private float ROUND_RECT_RADIUS = 100f;
    private float mCenterX = 0;
    private float mCenterY = 0;
    private Paint mPaint;
    private boolean ROUND_TL = true,ROUND_TR = true,ROUND_BL = true,ROUND_BR = true;

    public QuadRoundCorners(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(12);
        mPaint.setColor(Color.rgb(0,148,50));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterX = w * 1.0f / 2;
        mCenterY = h * 1.0f / 2;
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
        Path path = AlertnativeRoundRect(0, 0, WIDTH , HEIGHT , ROUND_RECT_RADIUS,ROUND_RECT_RADIUS,
                ROUND_TL, ROUND_TR, ROUND_BL, ROUND_BR);
        mPaint.setAntiAlias(true);
        canvas.drawPath(path,mPaint);

    }

    public Path AlertnativeRoundRect(
            float left, float top, float right, float bottom, float rx, float ry,
            boolean tl, boolean tr, boolean bl, boolean br
    ){
        Path path = new Path();
        if (rx < 0) rx = 0;
        if (ry < 0) ry = 0;
        float width = right - left;
        float height = bottom - top;
        if (rx > width / 2) rx = width / 2;
        if (ry > height / 2) ry = height / 2;
        float widthMinusCorners = (width - (2 * rx));
        float heightMinusCorners = (height - (2 * ry));

        //RealPos = (right,top + ry)
        path.moveTo(right, top + ry);
        if (tr)
            //Android_Round_Rect-top-right corner
            path.rQuadTo(0, -ry, -rx, -ry);
        else{
            path.rLineTo(0, -ry);
            path.rLineTo(-rx,0);
        }
        //RealPos = (right-rx-widthMinusCorners,top)
        path.rLineTo(-widthMinusCorners, 0);
        if (tl)
            //Android_Round_Rect-top-left corner
            path.rQuadTo(-rx, 0, -rx, ry);
        else{
            path.rLineTo(-rx, 0);
            path.rLineTo(0,ry);
        }
        //RealPos = (left,top + ry + heightMinusCorners)
        path.rLineTo(0, heightMinusCorners);

        if (bl)
            //Android_Round_Rect-bottom-left corner
            path.rQuadTo(0, ry, rx, ry);
        else{
            path.rLineTo(0, ry);
            path.rLineTo(rx,0);
        }
        //RealPos = (left + rx + widthMinusCorners,bottom)
        path.rLineTo(widthMinusCorners, 0);
        if (br)
            //Android_Round_Rect-bottom-right corner
            path.rQuadTo(rx, 0, rx, -ry);
        else{
            path.rLineTo(rx,0);
            path.rLineTo(0, -ry);
        }
        path.rLineTo(0, -heightMinusCorners);
        path.close();//Given close, last lineto can be removed.
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
        return minBorder/2;
    }
    private float getRadiusInMaxRange(float width,float height,float radius){
        float realRadius = Math.min(radius,getMAXRadius(width,height));
        return realRadius;
    }
}