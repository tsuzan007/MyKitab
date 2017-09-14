package com.sujan.mykitaab.ViewClass.HelperClass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;


import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Created by macbookpro on 9/11/17.
 */

public class MyCustomImageView extends android.support.v7.widget.AppCompatImageView {

    private int balID = 0;
    Paint mPaint;
    Context context;
    float x;
    float y;
    OnTouchListener onTouchListener;

    public MyCustomImageView(Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    public MyCustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public MyCustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void init(@Nullable AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setFlags(ANTI_ALIAS_FLAG );


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(x-20,y-20,x+20,y+20,mPaint);


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();
        mPaint.setColor(Color.BLUE);
        invalidate();
        return super.dispatchTouchEvent(event);
    }




}
