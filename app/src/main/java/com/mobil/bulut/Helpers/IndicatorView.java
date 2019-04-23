package com.mobil.bulut.Helpers;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;

import com.wang.avi.AVLoadingIndicatorView;

public class IndicatorView {

    private AVLoadingIndicatorView indicatorView;
    private RelativeLayout background;

    public IndicatorView(Activity activity) {
        setIndicatorView(activity);
    }

    private void setIndicatorView(Activity activity) {
        background = new RelativeLayout(activity.getApplicationContext());
        indicatorView = new AVLoadingIndicatorView(activity.getApplicationContext());

        RelativeLayout.LayoutParams paramsBg = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );

        background.setBackgroundColor(Color.parseColor("#70000000"));
        background.setClickable(true);

        paramsBg.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        paramsBg.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        paramsBg.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        paramsBg.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        background.setLayoutParams(paramsBg);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(140, 140);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        indicatorView.setLayoutParams(params);

        background.addView(indicatorView);
        indicatorView.setIndicator("LineScalePulseOutIndicator");

        activity.addContentView(background, paramsBg);
    }

    public void show() {
        indicatorView.show();
        background.setVisibility(View.VISIBLE);
    }

    public void hide() {
        indicatorView.hide();
        background.setVisibility(View.GONE);
    }
}
