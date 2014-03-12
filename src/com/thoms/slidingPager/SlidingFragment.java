package com.thoms.slidingPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.thoms.slidingPager.view.SlidingTabLayout;

/**
 * Created by pocouser on 14-3-10.
 */
public class SlidingFragment extends Fragment{

    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sliding_page, container, false);
        mViewPager = (ViewPager)view.findViewById(R.id.view_pager);
        mSlidingTabLayout = (SlidingTabLayout)view.findViewById(R.id.sliding_layout);
        mViewPager.setAdapter(new SlidingAdapter(getFragmentManager()));
        mSlidingTabLayout.setViewPager(mViewPager);
        return view;
    }


    private class SlidingAdapter extends FragmentStatePagerAdapter {

        public SlidingAdapter (FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return PagerFragment.newInstance(i);
        }

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Item"+position;
        }
    }
}
