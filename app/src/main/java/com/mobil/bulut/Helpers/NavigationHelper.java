package com.mobil.bulut.Helpers;

import android.app.Activity;
import android.content.Intent;

import com.mobil.bulut.Activity.LoginActivity;
import com.mobil.bulut.Activity.MainActivity;

public class NavigationHelper {

    public static NavigationHelper shared = new NavigationHelper();

    public void MainActivity(Activity activity) {
        Intent myIntent = new Intent(activity, MainActivity.class);
        activity.startActivity(myIntent);
    }

    public void LoginActivity(Activity activity) {
        Intent myIntent = new Intent(activity, LoginActivity.class);
        activity.startActivity(myIntent);
    }

}
