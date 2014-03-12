package com.thoms.slidingPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by pocouser on 14-3-12.
 */
public class WorkingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_content, container, false);
        TextView textView = (TextView)view.findViewById(R.id.page_text);
        textView.setText("Working");
        return view;
    }
}
