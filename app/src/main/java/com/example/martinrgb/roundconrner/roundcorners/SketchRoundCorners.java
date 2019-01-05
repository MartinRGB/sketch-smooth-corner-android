package com.example.martinrgb.roundconrner.roundcorners;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class SketchRoundCorners extends View {

    private float WIDTH = 400;
    private float HEIGHT = 400;
    private float SKETCH_ROUND_RECT_RADIUS = 100f;
    private float mCenterX = 0;
    private float mCenterY = 0;
    private Paint mPaint;
    private boolean ROUND_TL = true,ROUND_TR = true,ROUND_BL = true,ROUND_BR = true;


    public SketchRoundCorners(Context context, @Nullable AttributeSet attrs) {
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
        mPaint.setStrokeWidth(9);
        mPaint.setColor(Color.rgb(50,228,214));
    }

    public float getRoundRadius() {
        return SKETCH_ROUND_RECT_RADIUS;
    }

    public void setRoundRadius(float radius){
        this.SKETCH_ROUND_RECT_RADIUS = getRadiusInMaxRange(WIDTH,HEIGHT,radius);
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
        Path path = SketchRoundedRect(0, 0, WIDTH , HEIGHT , SKETCH_ROUND_RECT_RADIUS,SKETCH_ROUND_RECT_RADIUS,
                ROUND_TL,ROUND_TR,ROUND_BL,ROUND_BR);
        mPaint.setAntiAlias(true);
        canvas.drawPath(path,mPaint);

    }
    public Path SketchRoundedRect(
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
            //Sketch_Round_Rect-top-right corner
            path.cubicTo(right, top+ry-getSKRoundRadius(ry),right-rx+getSKRoundRadius(rx),top,right-rx, top);
        else{
            path.rLineTo(0, -ry);
            path.rLineTo(-rx,0);
        }
        //RealPos = (right-rx-widthMinusCorners,top)
        path.rLineTo(-widthMinusCorners, 0);
        if (tl)
            //Sketch_Round_Rect-top-left corner
            path.cubicTo(right-rx-widthMinusCorners-getSKRoundRadius(rx), top,left,top+ry-getSKRoundRadius(ry),left,top+ry);
        else{
            path.rLineTo(-rx, 0);
            path.rLineTo(0,ry);
        }
        //RealPos = (left,top + ry + heightMinusCorners)
        path.rLineTo(0, heightMinusCorners);

        if (bl)
            //Sketch_Round_Rect-bottom-left corner
            path.cubicTo(left, top + ry + heightMinusCorners + getSKRoundRadius(ry),left+rx - getSKRoundRadius(rx),bottom,left+rx,bottom);
        else{
            path.rLineTo(0, ry);
            path.rLineTo(rx,0);
        }
        //RealPos = (left + rx + widthMinusCorners,bottom)
        path.rLineTo(widthMinusCorners, 0);
        if (br)
            //Sketch_Round_Rect-bottom-right corner
            path.cubicTo(left + rx + widthMinusCorners + getSKRoundRadius(rx), bottom,right,bottom-ry + getSKRoundRadius(ry),right,bottom-ry);
        else{
            path.rLineTo(rx,0);
            path.rLineTo(0, -ry);
        }

        path.rLineTo(0, -heightMinusCorners);

        path.close();//Given close, last lineto can be removed.

        return path;
    }
    private float getSKRoundRadius(float radius){
        return radius/2+radius/60*(float)Math.PI;
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