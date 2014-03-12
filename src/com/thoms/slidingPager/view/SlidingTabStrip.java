package com.thoms.slidingPager.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by pocouser on 14-3-10.
 */
public class SlidingTabStrip extends LinearLayout {

    private static final int DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 2;
    private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = 0x26;
    private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 8;
    private static final int DEFAULT_SELECTED_INDICATOR_COLOR = 0xFF33B5E5;

    private static final int DEFAULT_DIVIDER_THICKNESS_DIPS = 1;
    private static final byte DEFAULT_DIVIDER_COLOR_ALPHA = 0x20;
    private static final float DEFAULT_DIVIDER_HEIGHT = 0.5f;

    private int mCustomSelectedColor;
    private int mButtomIndicactorThicker;
    private Paint mSelectedIndicatorPaint;

    private int mCustomButtomColor;
    private int mButtomBorderThicker;
    private Paint mButtomBorderPaint;

    private int mDefaultDividerColor;
    private int mDividerThicker;
    private float mDividerHeight;
    private Paint mDividerPaint;

    private int mSelectedPos;
    private float mPosOffset;

    public SlidingTabStrip (Context context) {
        super(context);
        init(context);
    }

    public SlidingTabStrip (Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlidingTabStrip (Context context, AttributeSet attrs, int diffStyle) {
        super(context, attrs, diffStyle);
        init(context);
    }

    private void init (Context context) {
        setWillNotDraw(false);
        TypedValue outValue = new TypedValue();
        float density = getResources().getDisplayMetrics().density;
        context.getTheme().resolveAttribute(android.R.attr.colorForeground, outValue, false);
        int foreColor = outValue.data;
        mCustomButtomColor = setAlpha(foreColor,DEFAULT_BOTTOM_BORDER_COLOR_ALPHA);
        mButtomBorderThicker = (int)(DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS*density);
        mButtomBorderPaint = new Paint();
        mButtomBorderPaint.setColor(mCustomButtomColor);

        mDefaultDividerColor = setAlpha(foreColor, DEFAULT_DIVIDER_COLOR_ALPHA);
        mDividerHeight = DEFAULT_DIVIDER_HEIGHT;
        mDividerThicker = (int)(DEFAULT_DIVIDER_THICKNESS_DIPS*density);
        mDividerPaint = new Paint();
        mDividerPaint.setColor(mDefaultDividerColor);

        mCustomSelectedColor = DEFAULT_SELECTED_INDICATOR_COLOR;
        mButtomIndicactorThicker = (int)(SELECTED_INDICATOR_THICKNESS_DIPS*density);
        mSelectedIndicatorPaint = new Paint();
        mSelectedIndicatorPaint.setColor(mCustomSelectedColor);
    }

    private int setAlpha (int color, int alpha) {
       return Color.argb(alpha, Color.red(color), Color.blue(color), Color.green(color));

    }

    public void onPageScroll (int postion, float posOffset) {
        this.mSelectedPos = postion;
        this.mPosOffset = posOffset;
        invalidate();
    }

    public void setCustomSelectedColor(int color) {
        mCustomSelectedColor = color;
        invalidate();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int childCount = getChildCount();
        if (childCount>0) {
            View currentTitle = getChildAt(mSelectedPos);
            float left = currentTitle.getLeft();
            float right = currentTitle.getRight();
            View nextTitle = getChildAt(mSelectedPos+1);
            if (null!=nextTitle) {
            left = (nextTitle.getLeft()-currentTitle.getLeft())*mPosOffset+currentTitle.getLeft();
            right = (nextTitle.getRight()-currentTitle.getRight())*mPosOffset+currentTitle.getRight();
            }
            canvas.drawRect(left,height-mButtomIndicactorThicker,right,height, mSelectedIndicatorPaint);
        }
        //draw alone bottom line
        canvas.drawRect(getLeft(),height-mButtomBorderThicker,getRight(), height, mButtomBorderPaint);

        for (int i = 0; i<childCount-1; i++) {
            float dividerHeight = height*mDividerHeight;
            float startY = (height-dividerHeight)/2;
            View title = getChildAt(i);
            int right = title.getRight();
            canvas.drawLine(right, startY, right, startY+dividerHeight, mDividerPaint);
        }
    }
}
