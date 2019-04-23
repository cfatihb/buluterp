package com.mobil.bulut.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class MyPreferenceManager {

    private static final String PREF_NAME = "piblo_business";

    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_CUSTOMER_ID = "user_customer_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_SURNAME = "user_surname";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_ROLE = "user_role";
    private static final String KEY_USER_IMAGEURL = "user_image";
    private static final String KEY_USER_TOKEN = "user_token";

    private static final String KEY_CUSTOMER_ID = "customer_id";
    private static final String KEY_CUSTOMER_NAME = "customer_name";
    private static final String KEY_CUSTOMER_SHORTNAME = "customer_shortname";
    private static final String KEY_CUSTOMER_ADDRESS = "customer_address";
    private static final String KEY_CUSTOMER_PHONE = "customer_phone";

    public static final String KEY_SELECT_DEVICE = "select_device";
    public static final String KEY_DEVICE_REG_ID = "device_reg_id";

    public static final String KEY_CURRENCY = "currency";

    public static MyPreferenceManager myPreferenceManager;

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    private String TAG = MyPreferenceManager.class.getSimpleName();

    public MyPreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public static MyPreferenceManager getInstance(Context mContext) {
        if (myPreferenceManager == null) {
            myPreferenceManager = new MyPreferenceManager(mContext);
        }
        return myPreferenceManager;
    }

    public void setSelectDevice(String selectDevice) {
        editor.putString(KEY_SELECT_DEVICE, selectDevice);
        editor.commit();
    }

    public void setDeviceRegId(String regId) {
        editor.putString(KEY_DEVICE_REG_ID, regId);
        editor.commit();
    }

    public void setCurrency(String currency) {
        editor.putString(KEY_CURRENCY, currency);
        editor.commit();
    }

    public HashMap<String, String> getSelectDevice() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_SELECT_DEVICE, pref.getString(KEY_SELECT_DEVICE, null));
        return user;
    }

    public HashMap<String, String> getDeviceRegId() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_DEVICE_REG_ID, pref.getString(KEY_DEVICE_REG_ID, null));
        return user;
    }

    public HashMap<String, String> getCurrency() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_CURRENCY, pref.getString(KEY_CURRENCY, null));
        return user;
    }


    public void clear() {
        editor.clear();
        editor.commit();
    }
}
