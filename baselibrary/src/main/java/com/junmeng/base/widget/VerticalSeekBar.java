package com.junmeng.base.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 垂直进度条
 * Created by hwj on 2015/12/10.
 */
public class VerticalSeekBar extends AppCompatSeekBar {
    private static final String TAG = "VerticalSeekBar";

    public VerticalSeekBar(Context context) {
        super(context);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    protected void onDraw(Canvas c) {
        //将SeekBar转转90度
        c.rotate(-90);
        //将旋转后的视图移动回来
        c.translate(-getHeight(), 0);
        Log.i(TAG, "getHeight()=" + getHeight());
        super.onDraw(c);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                int i = 0;
                //获取滑动的距离
                i = getMax() - (int) (getMax() * event.getY() / getHeight());
                //设置进度
                setProgress(i);
                Log.i(TAG, "Progress=" + getProgress());
                //每次拖动SeekBar都会调用
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                Log.i(TAG, "getWidth()=" + getWidth());
                Log.i(TAG, "getHeight()=" + getHeight());
                break;

            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }
}
