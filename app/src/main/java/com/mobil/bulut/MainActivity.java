package com.mobil.bulut;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobil.bulut.Helpers.CemAnimationHelper;
import com.mobil.bulut.Helpers.OnSwipeListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener  {

    MainActivity mainActivity = null;

    private static final int STORAGE_REQUEST_CODE = 1;

    @BindView(R.id.leftMenuArea)            RelativeLayout leftMenuArea;
    @BindView(R.id.mainLayout)              RelativeLayout mainLayout;
    @BindView(R.id.doubleMain)              RelativeLayout doubleMain;

    @BindView(R.id.allLayout)               RelativeLayout allLayout;

    @BindView(R.id.versionNameTxt)          TextView versionNameTxt;

    Boolean leftMenu = false;

    public static MainActivity sharedInstance;

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

        versionName = BuildConfig.VERSION_NAME; //App version

        versionNameTxt.setText("Sürüm : "+versionName);

        if (sharedInstance != null)
            sharedInstance.finish();
        sharedInstance = this;

        changeStatusColor(ContextCompat.getColor(mainActivity, R.color.colorPrimary));

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
            CemAnimationHelper.translateXAnimation(leftMenuArea,300,310, -leftMenuSize,0);
            CemAnimationHelper.translateXAnimation(mainLayout,300,310, 0,leftMenuSize);
            leftMenu = true;
        }else {
            CemAnimationHelper.translateXAnimation(leftMenuArea,300,310, 0,-leftMenuSize);
            CemAnimationHelper.translateXAnimation(mainLayout,300,310, leftMenuSize,0);
            leftMenu = false;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v("onResume", "onResumeStart");
    }

}