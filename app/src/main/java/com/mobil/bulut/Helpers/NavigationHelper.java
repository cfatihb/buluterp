package com.mobil.bulut.Helpers;

import android.app.Activity;
import android.content.Intent;

import com.mobil.bulut.MainActivity;

public class NavigationHelper {

    public static NavigationHelper shared = new NavigationHelper();

    public void MainActivity(Activity activity) {
        Intent myIntent = new Intent(activity, MainActivity.class);
        activity.startActivity(myIntent);
    }

}
