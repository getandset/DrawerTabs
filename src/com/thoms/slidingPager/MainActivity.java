package com.thoms.slidingPager;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.security.PublicKey;

public class MainActivity extends ActionBarActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener{

    private SearchView searchView;
    private DrawerLayout mDrawerKayout;
    private ListView mLeftList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mPageTitle;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mDrawerKayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mLeftList = (ListView)findViewById(R.id.drawer_list_view);
        String[] titles = getResources().getStringArray(R.array.drawer_items);
        mLeftList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item, titles));
        mLeftList.setOnItemClickListener(this);
        mDrawerToggle = new SimpleDrawerToggleListener(this, mDrawerKayout, R.drawable.ic_drawer,
                R.string.open, R.string.close);
        mDrawerKayout.setDrawerListener(mDrawerToggle);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        if (savedInstanceState==null) {
            selectDrawerItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_action, menu);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setQueryHint("search");

         ShareActionProvider shareProvider = (ShareActionProvider)
                MenuItemCompat.getActionProvider(menu.findItem(R.id.share));
        Intent intent = getDefaultIntent();
        shareProvider.setShareIntent(intent);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mDrawerKayout.isDrawerOpen(mLeftList)) {
            MenuItem menuItem = menu.findItem(R.id.search);
            menuItem.setVisible(false);
            menuItem = menu.findItem(R.id.share);
            menuItem.setVisible(false);
            menuItem = menu.findItem(R.id.favorite);
            menuItem.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClick(View v) {
        if (v==searchView) {
            searchView.setQueryHint("search");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectDrawerItem(position);

    }

    private void selectDrawerItem (int pos) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (pos) {
            case 0:
                ft.replace(R.id.root, new SlidingFragment());
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.root, new WorkingFragment());
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.root, new ImportantFragment());
                ft.commit();
                break;
        }
        mLeftList.setItemChecked(pos, true);
        mDrawerKayout.closeDrawer(mLeftList);
        mPageTitle = getResources().getStringArray(R.array.drawer_items)[pos];
    }

    private Intent getDefaultIntent () {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        return intent;
    }

    class SimpleDrawerToggleListener extends ActionBarDrawerToggle {
        public SimpleDrawerToggleListener(Activity activity, DrawerLayout drawerLayout,
                                          int drawerImageRes, int openDrawerContentDescRes,
                                          int closeDrawerContentDescRes) {
            super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes,
                    closeDrawerContentDescRes);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            getSupportActionBar().setTitle(mPageTitle);
            invalidateOptionsMenu();
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            getSupportActionBar().setTitle("Sliding");
            invalidateOptionsMenu();
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            super.onDrawerStateChanged(newState);
        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
        }

    }
}
