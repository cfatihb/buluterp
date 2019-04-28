package com.mobil.bulut.Activity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.mobil.bulut.BaseActivity;
import com.mobil.bulut.BaseFragment;
import com.mobil.bulut.BuildConfig;
import com.mobil.bulut.Fragment.FastOrderFragment;
import com.mobil.bulut.Fragment.HomeFragment;
import com.mobil.bulut.Fragment.OrdersFragment;
import com.mobil.bulut.Fragment.RegionFragment;
import com.mobil.bulut.Helpers.CacheDatas;
import com.mobil.bulut.Helpers.CemAnimationHelper;
import com.mobil.bulut.Helpers.NavigationHelper;
import com.mobil.bulut.Helpers.OnSwipeListener;
import com.mobil.bulut.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener, View.OnTouchListener{

    @BindView(R.id.leftMenuArea)            RelativeLayout leftMenuArea;
    @BindView(R.id.mainLayout)              RelativeLayout mainLayout;
    @BindView(R.id.doubleMain)              RelativeLayout doubleMain;
    @BindView(R.id.allLayout)               RelativeLayout allLayout;
    @BindView(R.id.versionNameTxt)          TextView versionNameTxt;

    @BindView(R.id.menuBtn)                 ImageView menuBtn;
    @BindView(R.id.calendarBtn)             ImageView calendarBtn;

    //TabHost definition
    public static MainActivity sharedInstance;
    public static BaseFragment tabFragments[] = null;
    public static View tabViews[];
    private FragmentTabHost mTabHost;
    int lastSelectedTab = 0;
    private int willRefreshTab = -1;
    int fleetTabSize = 4;
    int currentTabHost = 0;

    public static MainActivity mainActivity;

    Boolean leftMenu = false;
    int leftMenuSize = 0;

    int screenSizeWidth, screenSizeHeight;

    RelativeLayout.LayoutParams leftMenuAreaParams;
    RelativeLayout.LayoutParams mainLayoutParams;

    GestureDetector gestureDetector = null;

    String versionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainActivity = MainActivity.this;

        if (sharedInstance != null)
            sharedInstance.finish();
        sharedInstance = this;

        tabViews = new View[fleetTabSize];
        tabFragments = new BaseFragment[fleetTabSize];

        versionName = BuildConfig.VERSION_NAME; //App version
        versionNameTxt.setText("Sürüm : "+versionName);

        changeStatusColor(ContextCompat.getColor(mainActivity, R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        screenSizeWidth = metrics.widthPixels;
        screenSizeHeight = metrics.heightPixels;

        leftMenuSize = (screenSizeWidth / 3) * 2;

        leftMenuAreaParams = (RelativeLayout.LayoutParams) leftMenuArea.getLayoutParams();
        leftMenuAreaParams.setMargins(-leftMenuSize, 0, 0, 0);
        leftMenuArea.setLayoutParams(leftMenuAreaParams);

        mainLayoutParams = (RelativeLayout.LayoutParams) mainLayout.getLayoutParams();

        leftMenuArea.getLayoutParams().width = leftMenuSize;

        mainLayout.getLayoutParams().width = screenSizeWidth;
        mainLayout.getLayoutParams().height = screenSizeHeight;

        Log.v("leftMenuWidth", leftMenuArea.getWidth()+" -- "+mainLayout.getWidth()+" - "+screenSizeWidth);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) leftMenuArea.getLayoutParams();

        params.topMargin = 0;
        params.leftMargin = -leftMenuSize;
        params.rightMargin = 0;
        params.bottomMargin = 0;
        params.height = screenSizeHeight;
        leftMenuArea.setLayoutParams(params);

        CemAnimationHelper.translateXAnimation(leftMenuArea,0,0, 0,-leftMenuSize);
        CemAnimationHelper.translateXAnimation(mainLayout,0,0, leftMenuSize,0);

        gestureDetector=new GestureDetector(this,new OnSwipeListener(){

            @Override
            public boolean onSwipe(Direction direction) {
                if (direction==Direction.left){
                    Log.d("gestureDetector", "onSwipe: up");

                    if(leftMenu)
                        scrollView();
                }

                if (direction==Direction.right){
                    //do your stuff
                    Log.d("gestureDetector", "onSwipe: down");

                    if(!leftMenu)
                        scrollView();
                }

                return true;
            }


        });
        doubleMain.setOnTouchListener(this);

        initializeTabHost();

    }

    private void initializeTabHost() {

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        mTabHost.setOnTabChangedListener(this);

        addTab("tab1", R.drawable.ic_cancel_icon, HomeFragment.class, 0);
        addTab("tab2", R.drawable.ic_cancel_icon, RegionFragment.class, 1);
        addTab("tab3", R.drawable.ic_cancel_icon, FastOrderFragment.class, 2);
        addTab("tab4", R.drawable.ic_cancel_icon, OrdersFragment.class, 3);

        mTabHost.getTabWidget().getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTabHost.setCurrentTab(0);
            }
        });

    }

    public void addTab(String tag, int resId, Class<?> fragment, int position) {
        mTabHost.addTab(mTabHost.newTabSpec(tag).setIndicator(createTabView(resId, position)), fragment, null);
    }

    public View createTabView(int resId, int position) {

        View view = LayoutInflater.from(this).inflate(R.layout.menu_tab, null);

        ImageView iView = (ImageView) view.findViewById(R.id.tab_img);
        iView.setImageResource(resId);

        TextView messageCount = (TextView) view.findViewById(R.id.messageCount);
        TextView txt = (TextView) view.findViewById(R.id.tab_txt);

        switch (position) {
            case 0:
                txt.setText("A1");
                break;
            case 1:
                txt.setText("A1");
                break;
            case 2:
                txt.setText("A1");
                break;
            case 3:
                txt.setText("A1");
                break;
        }

        txt.setVisibility(View.GONE);

        tabViews[position] = view;
        return view;
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (mTabHost.getCurrentTab() == fleetTabSize) {
            mTabHost.setCurrentTab(lastSelectedTab);
        }

        if (willRefreshTab >= 0) {
            mTabHost.setCurrentTab(willRefreshTab);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tabFragments[willRefreshTab].refresh();
                    willRefreshTab = -1;
                }
            }, 1000);
        }
    }

    @Override
    public void onTabChanged(String tabId) {

        int currentTabIndex = mTabHost.getCurrentTab();
        currentTabHost = currentTabIndex;

        for (int i = 0; i < fleetTabSize; i++) {
            if (tabViews[i] != null) {

                ImageView img = (ImageView) tabViews[i].findViewById(R.id.tab_img);
                TextView txtV = (TextView) tabViews[i].findViewById(R.id.tab_txt);

                int passiveColor = Color.parseColor("#bfbfbf");
                txtV.setTextColor(passiveColor);
                txtV.setTypeface(null, Typeface.NORMAL);

                switch (i) {
                    case 0:
                        img.setImageResource(R.drawable.ic_cancel_icon);
                        break;
                    case 1:
                        img.setImageResource(R.drawable.ic_cancel_icon);
                        break;
                    case 2:
                        img.setImageResource(R.drawable.ic_cancel_icon);
                        break;
                    case 3:
                        img.setImageResource(R.drawable.ic_cancel_icon);
                        break;
                }

            }
        }

        lastSelectedTab = currentTabIndex;
        ImageView img = (ImageView) tabViews[currentTabIndex].findViewById(R.id.tab_img);
        TextView txtV = (TextView) tabViews[currentTabIndex].findViewById(R.id.tab_txt);

        int activeColor = Color.parseColor("#7159f9");
        txtV.setTextColor(activeColor);

        txtV.setTextColor(activeColor);
        txtV.setTypeface(null, Typeface.NORMAL);

        TextView messageCount = (TextView) tabViews[currentTabIndex].findViewById(R.id.messageCount);
        messageCount.setVisibility(View.GONE);

      //  toolbarIcon(currentTabIndex);
    }

    public void scrollView(){

        Log.v("scrollView", "asdadasd");

        if(!leftMenu){
            CemAnimationHelper.translateXAnimation(leftMenuArea,300,20, -leftMenuSize,0);
            CemAnimationHelper.translateXAnimation(mainLayout,300,20, 0,leftMenuSize);
            leftMenu = true;
        }else {
            CemAnimationHelper.translateXAnimation(leftMenuArea,300,20, 0,-leftMenuSize);
            CemAnimationHelper.translateXAnimation(mainLayout,300,20, leftMenuSize,0);
            leftMenu = false;
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        Log.d("onConfiguration", "config changed");

        super.onConfigurationChanged(newConfig);

        int orientation = newConfig.orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT){

            mainLayout.getLayoutParams().width = screenSizeWidth;
            mainLayout.getLayoutParams().height = screenSizeHeight;

            Log.d("tag", "Portrait");

        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE){

            mainLayout.getLayoutParams().width = screenSizeHeight;
            mainLayout.getLayoutParams().height = screenSizeWidth;

            Log.d("tag", "Landscape");

        } else
            Log.w("tag", "other: " + orientation);

    }

    @OnClick(R.id.menuBtn)
    public void menuBtnClick(){
        scrollView();
    }

    @OnClick(R.id.calendarBtn)
    public void calendarBtnClick(){

    }

    @OnClick(R.id.homeBtn)
    public void homeBtnClick(){

        mTabHost.setCurrentTab(0);
        scrollView();

    }

    @OnClick(R.id.regionBtn)
    public void regionBtnClick(){

        mTabHost.setCurrentTab(1);
        scrollView();
    }

    @OnClick(R.id.fastOrderBtn)
    public void fastOrderBtnClick(){

        mTabHost.setCurrentTab(2);
        scrollView();
    }

    @OnClick(R.id.ordersBtn)
    public void ordersBtnClick(){

        mTabHost.setCurrentTab(3);
        scrollView();
    }

    @OnClick(R.id.themeBtn)
    public void themeBtnClick(){
    }

    @OnClick(R.id.logoutBtn)
    public void logoutBtnClick(){

        NavigationHelper.shared.LoginActivity(this);
        CacheDatas.clearAll();
        finish();
    }
}
