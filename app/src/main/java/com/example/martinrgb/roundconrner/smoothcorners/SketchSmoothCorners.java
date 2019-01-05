package com.example.martinrgb.roundconrner.smoothcorners;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class SketchSmoothCorners extends View {

    private float WIDTH = 400;
    private float HEIGHT = 400;
    private float SKETCH_ROUND_RECT_RADIUS = 100f;
    private float mCenterX = 0;
    private float mCenterY = 0;
    private Paint mPaint;
    private boolean ROUND_TL = true,ROUND_TR = true,ROUND_BL = true,ROUND_BR = true;
    private boolean isSquare = true;

    public SketchSmoothCorners(Context context, @Nullable AttributeSet attrs) {
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
            if(SKETCH_ROUND_RECT_RADIUS == WIDTH/2){
                canvas.translate(mCenterX-WIDTH/2, mCenterY-HEIGHT/2);
                drawAndroidRoundRect(0,0,WIDTH,HEIGHT,SKETCH_ROUND_RECT_RADIUS,SKETCH_ROUND_RECT_RADIUS,ROUND_TL,ROUND_TR,ROUND_BL,ROUND_BR,canvas);
            }
            else{
                path = SketchRealSmoothRect(0, 0, WIDTH , WIDTH , SKETCH_ROUND_RECT_RADIUS,SKETCH_ROUND_RECT_RADIUS,
                        ROUND_TL,ROUND_TR,ROUND_BL,ROUND_BR);
                canvas.drawPath(path,mPaint);
            }
        }
        else{

            if(SKETCH_ROUND_RECT_RADIUS == Math.min(WIDTH,HEIGHT)/2){
                canvas.translate(mCenterX-WIDTH/2, mCenterY-HEIGHT/2);
                drawAndroidRoundRect(0,0,WIDTH,HEIGHT,SKETCH_ROUND_RECT_RADIUS,SKETCH_ROUND_RECT_RADIUS,ROUND_TL,ROUND_TR,ROUND_BL,ROUND_BR,canvas);
            }
            else{
                path = SketchRealSmoothRect(0, 0, WIDTH , HEIGHT , SKETCH_ROUND_RECT_RADIUS,SKETCH_ROUND_RECT_RADIUS,
                        ROUND_TL,ROUND_TR,ROUND_BL,ROUND_BR);
                canvas.drawPath(path,mPaint);
            }

        }


    }

    public Path SketchRealSmoothRect(
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

        float r = rx;

        float vertexRatio;
        if(r/Math.min(width/2,height/2) > 0.5){
            float percentage = ((r/Math.min(width/2,height/2)) - 0.5f)/0.4f;
            float clampedPer = Math.min(1,percentage);
            vertexRatio = 1.f - (1 - 1.104f/1.2819f)*clampedPer;
        }
        else{
            vertexRatio = 1.f;
        }

        float controlRatio;
        if(r/Math.min(width/2,height/2) > 0.6){
            float percentage = ((r/Math.min(width/2,height/2)) - 0.6f)/0.3f;
            float clampedPer = Math.min(1,percentage);
            controlRatio = 1 + (0.8717f/0.8362f - 1)*clampedPer;
        }
        else{
            controlRatio = 1;
        }

        path.moveTo(posX + width/2 , posY);
        if(!tr){
            path.lineTo(posX + width, posY);
        }
        else{

            path.lineTo(posX + Math.max(width/2,width - r/100.0f*128.19f*vertexRatio), posY);
            path.cubicTo(posX + width - r/100.f*83.62f*controlRatio, posY,posX + width - r/100.f*67.45f,posY + r/100.f*4.64f, posX + width - r/100.f*51.16f, posY + r/100.f*13.36f);
            path.cubicTo(posX + width - r/100.f*34.86f, posY + r/100.f*22.07f,posX + width - r/100.f*22.07f,posY + r/100.f*34.86f, posX + width - r/100.f*13.36f, posY + r/100.f*51.16f);
            path.cubicTo(posX + width - r/100.f*4.64f, posY + r/100.f*67.45f,posX + width,posY + r/100.f*83.62f*controlRatio, posX + width, posY + Math.min(height/2,r/100.f*128.19f*vertexRatio));
        }


        if(!br){
            path.lineTo(posX + width, posY + height);
        }
        else{
            path.lineTo(posX + width, posY + Math.max(height/2,height - r/100.f*128.19f*vertexRatio));
            path.cubicTo(posX + width, posY + height - r/100.f*83.62f*controlRatio,posX + width - r/100.f*4.64f,posY + height - r/100.f*67.45f, posX + width - r/100.f*13.36f, posY + height -  r/100.f*51.16f);
            path.cubicTo(posX + width - r/100.f*22.07f, posY + height - r/100.f*34.86f,posX + width - r/100.f*34.86f,posY + height - r/100.f*22.07f, posX + width - r/100.f*51.16f, posY + height - r/100.f*13.36f);
            path.cubicTo(posX + width - r/100.f*67.45f, posY + height - r/100.f*4.64f,posX + width - r/100.f*83.62f*controlRatio,posY + height, posX + Math.max(width/2,width - r/100.f*128.19f*vertexRatio), posY + height);

        }


        if(!bl){
            path.lineTo(posX, posY + height);
        }
        else{
            path.lineTo(posX + Math.min(width/2,r/100.f*128.19f*vertexRatio), posY + height);
            path.cubicTo(posX +  r/100.f*83.62f*controlRatio, posY + height,posX + r/100.f*67.45f,posY + height - r/100.f*4.64f, posX + r/100.f*51.16f, posY + height -  r/100.f*13.36f);
            path.cubicTo(posX +  r/100.f*34.86f, posY + height - r/100.f*22.07f,posX + r/100.f*22.07f,posY + height - r/100.f*34.86f, posX + r/100.f*13.36f, posY + height - r/100.f*51.16f);
            path.cubicTo(posX  + r/100.f*4.64f, posY + height - r/100.f*67.45f,posX ,posY + height - r/100.f*83.62f*controlRatio, posX , posY + Math.max(height/2,height - r/100.f*128.19f*vertexRatio));

        }

        if(!tl){
            path.lineTo(posX, posY);
        }
        else{
            path.lineTo(posX, posY + Math.min(height/2,r/100.f*128.19f*vertexRatio));
            path.cubicTo(posX, posY + r/100.f*83.62f*controlRatio,posX + r/100.f*4.64f,posY + r/100.f*67.45f, posX + r/100.f*13.36f, posY + r/100.f*51.16f);
            path.cubicTo(posX +  r/100.f*22.07f, posY + r/100.f*34.86f,posX + r/100.f*34.86f,posY +  r/100.f*22.07f, posX + r/100.f*51.16f, posY + r/100.f*13.36f);
            path.cubicTo(posX  + r/100.f*67.45f, posY +  r/100.f*4.64f,posX + r/100.f*83.62f*controlRatio,posY, posX + Math.min(width/2,r/100.f*128.19f*vertexRatio), posY);

        }



        path.close();

        return path;
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
            canvas.drawRect(0, 0, w/2, h/2, mPaint);
            Path path = new Path();
            path.moveTo(0,h/2);
            path.lineTo(0,0);
            path.lineTo(w/2,0);
            canvas.drawPath(path,mPaint);
        }
        if(!tr){
            canvas.drawRect(w/2, 0, w, h/2, mPaint);
            Path path = new Path();
            path.moveTo(w/2,0);
            path.lineTo(w,0);
            path.lineTo(w,h/2);
            canvas.drawPath(path,mPaint);
        }

        if(!bl){
            canvas.drawRect(0, h/2, w/2, h, mPaint);
            Path path = new Path();
            path.moveTo(0,h/2);
            path.lineTo(0,h);
            path.lineTo(w/2,h);
            canvas.drawPath(path,mPaint);
        }

        if(!br){
            canvas.drawRect(w/2, h/2, w, h, mPaint);
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
        return minBorder/2.f;
    }
    private float getRadiusInMaxRange(float width,float height,float radius){
        float realRadius = Math.min(radius,getMAXRadius(width,height));
        return realRadius;
    }
}