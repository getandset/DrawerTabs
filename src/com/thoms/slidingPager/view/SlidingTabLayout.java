package com.thoms.slidingPager.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import java.util.jar.Attributes;

/**
 * Created by pocouser on 14-3-10.
 */
public class SlidingTabLayout extends HorizontalScrollView {

    private static final int TAB_VIEW_PADDING = 16;
    private static final int TAB_VIEW_TEXT_SIZE = 12;

    private ViewPager mViewPager;
    private SlidingTabStrip mTabStrip;
    private ViewPager.OnPageChangeListener mViewPagerPageChangeListener;
    private int mState;

    public SlidingTabLayout (Context context) {
        this(context, null);
    }

    public SlidingTabLayout (Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public SlidingTabLayout (Context context, AttributeSet attrs, int diffStyle) {
        super(context, attrs, diffStyle);
        mTabStrip = new SlidingTabStrip(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(mTabStrip, params);
        mViewPagerPageChangeListener = new InternalPageChangeListener();
    }

    public void setViewPager (ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.setOnPageChangeListener(mViewPagerPageChangeListener);
        mTabStrip.removeAllViews();
        populateTabs();
    }

    private void populateTabs () {
        PagerAdapter adapter = mViewPager.getAdapter();
        int pageCount = mViewPager.getAdapter().getCount();
        for (int i = 0 ; i<pageCount; i++) {
            TextView textView = createDefaultTextView();
            textView.setText(adapter.getPageTitle(i));
            textView.setOnClickListener(new TabOnClickListener());
            mTabStrip.addView(textView);
        }
    }

    private TextView createDefaultTextView () {
        TextView textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        int padding = (int)(TAB_VIEW_PADDING*getResources().getDisplayMetrics().density);
        textView.setPadding(padding, padding, padding, padding);
        return textView;
    }

    private void scrollTab (int tabIndex, int offset) {
        int tabsCount = mTabStrip.getChildCount();
        if (tabIndex<0 || tabIndex>tabsCount) {
            return;
        }
        View tabView = mTabStrip.getChildAt(tabIndex);
        int scrollOffset = tabView.getLeft()+offset;
        scrollTo(scrollOffset, 0);
    }



   class InternalPageChangeListener implements ViewPager.OnPageChangeListener {
       @Override
       public void onPageScrolled(int pos, float posOffset, int posOffsetPx) {
           Log.i("SlidLayout","pagePos"+pos);
           int childCount = mViewPager.getAdapter().getCount();
           if (pos<0 || pos>=childCount || childCount==0) {
               return;
           }
           mTabStrip.onPageScroll(pos, posOffset);
           View tabView = mTabStrip.getChildAt(pos);
           int extraOffset = 0;
           if (tabView!=null) {
               extraOffset = (int)(tabView.getWidth()*posOffset);
           }
           scrollTab(pos, extraOffset);
       }

       @Override
       public void onPageSelected(int pos) {
           if (mState==ViewPager.SCROLL_STATE_IDLE) {
               scrollTab(pos, 0);
               mTabStrip.onPageScroll(pos, 0);
           }
       }

       @Override
       public void onPageScrollStateChanged(int state) {
           mState = state;
       }
   }

    class TabOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            int tabsCount = mTabStrip.getChildCount();
            for (int i = 0 ;i<tabsCount; i++) {
                View child = mTabStrip.getChildAt(i);
                if (v==child) {
                    mViewPager.setCurrentItem(i);
                    break;
                }
            }
        }
    }


}
