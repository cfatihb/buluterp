package com.mobil.bulut.Activity;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobil.bulut.Adapter.MainAdapter;
import com.mobil.bulut.BuildConfig;
import com.mobil.bulut.Helpers.CacheDatas;
import com.mobil.bulut.Helpers.CemAnimationHelper;
import com.mobil.bulut.Helpers.NavigationHelper;
import com.mobil.bulut.Helpers.OnSwipeListener;
import com.mobil.bulut.Models.LoginResponse;
import com.mobil.bulut.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mobil.bulut.Services.Api.service_URL_FIRMA;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener  {

    MainActivity mainActivity = null;

    private static final int STORAGE_REQUEST_CODE = 1;

    @BindView(R.id.leftMenuArea)            RelativeLayout leftMenuArea;
    @BindView(R.id.mainLayout)              RelativeLayout mainLayout;
    @BindView(R.id.doubleMain)              RelativeLayout doubleMain;
    @BindView(R.id.allLayout)               RelativeLayout allLayout;
    @BindView(R.id.versionNameTxt)          TextView versionNameTxt;

    @BindView(R.id.mainRecyclerView)        RecyclerView mainRecyclerView;

    @BindView(R.id.bgImage)                 ImageView bgImage;
    @BindView(R.id.toolbar)                 RelativeLayout toolbar;

    @BindView(R.id.menuBtn)                 ImageView menuBtn;
    @BindView(R.id.calendarBtn)             ImageView calendarBtn;

    Boolean leftMenu = false;

    public static MainActivity sharedInstance;

    int leftMenuSize = 0;

    int screenSizeWidth, screenSizeHeight;

    RelativeLayout.LayoutParams leftMenuAreaParams;
    RelativeLayout.LayoutParams mainLayoutParams;

    GestureDetector gestureDetector = null;

    String versionName;

    List<String> menuler = new ArrayList<String>();
    List<String> finans = new ArrayList<String>();
    List<String> genel = new ArrayList<String>();
    List<String> yonetim = new ArrayList<String>();
    List<String> sevkiyat = new ArrayList<String>();
    List<String> hammadde = new ArrayList<String>();
    List<String> disticaret = new ArrayList<String>();
    List<String> laboratuList = new ArrayList<String>();
    List<String> uretim = new ArrayList<String>();
    List<String> prim = new ArrayList<String>();
    List<String> diger = new ArrayList<String>();
    List<String> yardimci = new ArrayList<String>();
    List<String> ayarlar = new ArrayList<String>();

    MainAdapter mainAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainActivity = MainActivity.this;

        versionName = BuildConfig.VERSION_NAME; //App version
        versionNameTxt.setText("Sürüm : "+versionName);

        if (sharedInstance != null)
            sharedInstance.finish();
        sharedInstance = this;

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

        mainAdapter = new MainAdapter(getApplicationContext(), menuler);

        mainRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mainRecyclerView.setAdapter(mainAdapter);

        mainRecyclerView.setHasFixedSize(true);
        mainRecyclerView.setItemViewCacheSize(20);
        mainRecyclerView.setDrawingCacheEnabled(true);
        mainRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        getMenus();

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    public void changeStatusColor(int color){
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(color);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_REQUEST_CODE) {

            int granted = android.content.pm.PackageManager.PERMISSION_GRANTED;
            if (grantResults[0] == granted) {
            }
        }
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
    public void onResume() {
        super.onResume();
        Log.v("onResume", "onResumeStart");
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
            mainRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE){

            mainLayout.getLayoutParams().width = screenSizeHeight;
            mainLayout.getLayoutParams().height = screenSizeWidth;

            Log.d("tag", "Landscape");
            mainRecyclerView.setLayoutManager(new GridLayoutManager(this, 8));

        } else
            Log.w("tag", "other: " + orientation);

    }

    public void getMenus(){

        LoginResponse loginResponse = CacheDatas.loginResponse;

        menuler.addAll(Arrays.asList(loginResponse.getMenuler().getAnaMenu().split(",")));
        finans.addAll(Arrays.asList(loginResponse.getMenuler().getFinans().split(",")));

        mainAdapter.notifyDataSetChanged();

        String userId = loginResponse.getKullaniciDetay().getUserID();
        String uuId = CacheDatas.uuid;

        String url = service_URL_FIRMA+userId+"&uuid="+uuId;
        Log.v("firmaUrl", url);

        String a1 = "http://www.bulut-erp.com:7171/Firmalar/Firmalar?userid=2&uuid=123";
        String a2 = "http://www.bulut-erp.com:7171/CariTip/CariTip?yillarid=12&yil=2019&firmakod=216&userid=2&uuid=123";

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
    }

    @OnClick(R.id.regionBtn)
    public void regionBtnClick(){
    }

    @OnClick(R.id.fastOrderBtn)
    public void fastOrderBtnClick(){
    }

    @OnClick(R.id.ordersBtn)
    public void ordersBtnClick(){
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