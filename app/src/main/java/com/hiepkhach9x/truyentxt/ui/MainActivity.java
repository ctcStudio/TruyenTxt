package com.hiepkhach9x.truyentxt.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hiepkhach9x.baseTruyenHK.ui.BaseReadActivity;
import com.hiepkhach9x.baseTruyenHK.view.EBookViewPagerView;
import com.hiepkhach9x.truyentxt.R;
import com.hiepkhach9x.truyentxt.adapter.CustomDrawerAdapter;
import com.hiepkhach9x.truyentxt.adapter.SlidePagerAdapter;
import com.hiepkhach9x.truyentxt.entities.DrawerItem;
import com.hiepkhach9x.baseTruyenHK.utils.Config;
import com.hiepkhach9x.truyentxt.utils.LogUtils;
import com.hiepkhach9x.truyentxt.utils.SystemUiHider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseReadActivity implements ReadFragment.OnReadCallback {

    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final boolean TOGGLE_ON_CLICK = true;
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_LAYOUT_IN_SCREEN_OLDER_DEVICES;
    private SystemUiHider mSystemUiHider;

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mLeftMenu;
    private ActionBar mActionBar;
    private EBookViewPagerView mViewPager;
    private SlidePagerAdapter mPagerAdapter;
    private View controlsView;
    private TextView mBottomPosition;
    private TextView mBottomName;
    private List<String> listPage;

    private CustomDrawerAdapter mDrawerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initHideFullscreen();
        listPage = mBookData.getPages();
        if (listPage == null) {
            listPage = new ArrayList<>();
        }
        if (mPagerAdapter == null) {
            mPagerAdapter = new SlidePagerAdapter(getSupportFragmentManager(), listPage);
        }
        mViewPager.setAdapter(mPagerAdapter);

        mBottomName.setText(mBookData.getAuthor());
        mBottomPosition.setText((mViewPager.getCurrentItem() + 1) + "/" + listPage.size());
    }

    private void initView() {
        //
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mBottomPosition = (TextView) findViewById(R.id.position);
        mBottomName = (TextView) findViewById(R.id.name_chap);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLeftMenu = (ListView) findViewById(R.id.left_menu);
        mViewPager = (EBookViewPagerView) findViewById(R.id.viewContent);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBottomPosition.setText((position + 1) + "/" + listPage.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        /***************************************************************/
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
        /***************************************************************/
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                mToolbar,
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        /*****************************************************************/
        mDrawerAdapter = new CustomDrawerAdapter(this, R.layout.item_navigation, getListDrawerItem());
        mLeftMenu.setAdapter(mDrawerAdapter);
        mLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemName = mDrawerAdapter.getItem(position).getItemName();
                if (getString(R.string.common_store).equals(itemName)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(Config.URL_MARKET));
                    startActivity(intent);
                } else if (getString(R.string.common_about).equals(itemName)) {

                } else if (getString(R.string.common_fantasy).equals(itemName)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(Config.URL_MARKET));
                    startActivity(intent);
                } else if (getString(R.string.common_action).equals(itemName)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(Config.URL_MARKET));
                    startActivity(intent);
                } else if (getString(R.string.common_romantic).equals(itemName)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(Config.URL_MARKET));
                    startActivity(intent);
                }
            }
        });
    }

    private void initHideFullscreen() {
        controlsView = findViewById(R.id.fullscreen_content_controls);
        View contentView = findViewById(R.id.fullscreen_content);
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }
                        if (visible) {
                            mActionBar.show();
                        } else {
                            mActionBar.hide();
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

//        Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("CLICK");
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    private ArrayList<DrawerItem> getListDrawerItem() {
        ArrayList<DrawerItem> drawerItems = new ArrayList<>();
        drawerItems.add(new DrawerItem(getString(R.string.app_name)));
        drawerItems.add(new DrawerItem(getString(R.string.common_store), R.drawable.ic_store));
        drawerItems.add(new DrawerItem(getString(R.string.common_about), R.drawable.ic_about));
        drawerItems.add(new DrawerItem(getString(R.string.common_more_book)));
        drawerItems.add(new DrawerItem(getString(R.string.common_fantasy), R.drawable.ic_tienhiep));
        drawerItems.add(new DrawerItem(getString(R.string.common_action), R.drawable.ic_kiemhiep));
        drawerItems.add(new DrawerItem(getString(R.string.common_romantic), R.drawable.ic_ic_ngontinh));
        return drawerItems;
    }

    @Override
    public void splitBookStart() {
        showLoading();
    }

    @Override
    public void splitBookProcessUpdatePercent(int percent) {

    }

    @Override
    public void splitBookFinish(List<String> lstPage) {
        hideLoading();
        Toast.makeText(this, "number page: " + lstPage.size(), Toast.LENGTH_SHORT).show();
        listPage = lstPage;
        mPagerAdapter.setListPage(listPage);
    }

    @Override
    public void splitBookError(List<String> lstPage) {

    }

    @Override
    public void onContentClick() {
        if (TOGGLE_ON_CLICK) {
            mSystemUiHider.toggle();
        } else {
            mSystemUiHider.show();
        }
    }
}
