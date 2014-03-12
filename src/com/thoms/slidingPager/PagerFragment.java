package com.thoms.slidingPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by pocouser on 14-3-10.
 */
public class PagerFragment  extends Fragment{


    public static Fragment newInstance (int pos) {
        PagerFragment fm = new PagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        fm.setArguments(bundle);
        return fm;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View)inflater.inflate(R.layout.page_content, container, false);
        TextView textView = (TextView)view.findViewById(R.id.page_text);
        int pos = getArguments().getInt("pos");
        textView.setText("Page"+pos);
        return view;
    }
}
