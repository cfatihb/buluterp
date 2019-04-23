package com.mobil.bulut.Services;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobil.bulut.BuildConfig;
import com.mobil.bulut.Helpers.IndicatorView;
import com.mobil.bulut.Helpers.MyPreferenceManager;
import com.mobil.bulut.Helpers.NavigationHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ApiRequest extends Application {

    private static ApiRequest sharedInstance;

    private IndicatorView indicatorView;

    RequestQueue queue = null;
    Activity mActivity = null;

    StringRequest stringRequest = null;
    JsonObjectRequest jsonObjectRequest = null;

    private ApiRequest(){
        if (sharedInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static ApiRequest getInstance() {
        if (sharedInstance == null) {
            sharedInstance = new ApiRequest();
        }
        return sharedInstance;
    }

    private static HashMap<String, String> mHeaderParams = new HashMap<String, String>();
    private static String lang = (Locale.getDefault().getISO3Language().equals("tur")) ? "tr" : "en";

    public void fetch(Boolean progress, int method, String api, JSONObject postParams, String errorMessage, Activity activity, final Response.Listener listener, final Response.ErrorListener errorListener) {

        this.mActivity = activity;

        if(progress==true) {
            if (indicatorView == null) {
                indicatorView = new IndicatorView(mActivity);
            } else {
                indicatorView.hide();
                indicatorView = null;
                indicatorView = new IndicatorView(mActivity);
            }
            indicatorView.show();
        }

        if(mActivity !=null){

            if(queue==null){
                queue = Volley.newRequestQueue(mActivity.getApplicationContext());
            }

            switch (method) {
                case Request.Method.GET:
                    queue.add(Get(method,api,listener,errorListener, mActivity, errorMessage));
                    break;
                case Request.Method.POST:
                    queue.add(Post(method,api,postParams,listener,errorListener, mActivity,errorMessage));
                    break;
                case Request.Method.PUT:
                    queue.add(Post(method,api,postParams,listener,errorListener, mActivity,errorMessage));
                    break;
                case Request.Method.DELETE:
                    queue.add(Delete(method,api,listener,errorListener, mActivity, errorMessage));
                    break;
                default: break;
            }
        }
    }

    //TODO: Get İşlemi
    private StringRequest Get(int method, String api, final Response.Listener listener, final Response.ErrorListener errorListener, final Activity activity, final String errorMessage) {

        stringRequest = new StringRequest(method, api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onResponse(response);

                if (indicatorView == null) {
                    indicatorView = new IndicatorView(activity);

                }
                indicatorView.hide();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (indicatorView == null) {
                    indicatorView = new IndicatorView(activity);
                }
                indicatorView.hide();

                if (error.networkResponse != null) {
                    switch (error.networkResponse.statusCode) {
                        case 401:
                            MyPreferenceManager.getInstance(activity).clear();
                            NavigationHelper.shared.MainActivity(activity);
                            break;
                        case 402:
                            NavigationHelper.shared.MainActivity(activity);
                            break;
                    }
                }

            }
        }) {

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int statusCode = response.statusCode;
                Log.e("StatusCode",""+statusCode);
                return super.parseNetworkResponse(response);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                String userAgent = System.getProperty("http.agent");

                mHeaderParams.put("LANG", lang);

                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;
                versionName = versionName.replace("'","");
                mHeaderParams.put("VERSION", versionName);

                String model = Build.MODEL;
                mHeaderParams.put("MODEL", model);

                Log.v("apiHeaderLog", mHeaderParams.toString());

                return mHeaderParams;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0,-1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return stringRequest;
    }

    //TODO: Post İşlemi
    private JsonObjectRequest Post(int method, String api, JSONObject postParams, final Response.Listener listener, final Response.ErrorListener errorListener, final Activity activity, final String errorMessage) {

       jsonObjectRequest = new JsonObjectRequest(method, api, postParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
                if (indicatorView == null) {
                    indicatorView = new IndicatorView(activity);
                }
                indicatorView.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (indicatorView == null) {
                    indicatorView = new IndicatorView(activity);
                }
                indicatorView.hide();

                if (error.networkResponse != null) {
                    switch (error.networkResponse.statusCode) {
                        case 401:
                            MyPreferenceManager.getInstance(activity).clear();
                            NavigationHelper.shared.MainActivity(activity);
                            break;
                        case 402:
                            NavigationHelper.shared.MainActivity(activity);
                            break;
                    }
                }

              //  errorListener.onErrorResponse(error);
            }
        }) {


            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                    return Response.success(new JSONObject(jsonString),
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                String userAgent = System.getProperty("http.agent");

                mHeaderParams.put("LANG", lang);

                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;
                versionName = versionName.replace("'","");
                mHeaderParams.put("VERSION", versionName);

                String model = Build.MODEL;
                mHeaderParams.put("MODEL", model);

                Log.v("apiHeaderLog", mHeaderParams.toString());

                return mHeaderParams;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0,-1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return jsonObjectRequest;
    }

    //TODO: Delete İşlemi
    private StringRequest Delete(int method, String api, final Response.Listener listener, final Response.ErrorListener errorListener, final Activity activity, final String errorMessage) {

       stringRequest = new StringRequest(method, api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onResponse(response);

                if (indicatorView == null) {
                    indicatorView = new IndicatorView(activity);

                }
                indicatorView.hide();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorListener.onErrorResponse(error);
                if (indicatorView == null) {
                    indicatorView = new IndicatorView(activity);
                }
                indicatorView.hide();

                if (error.networkResponse != null) {
                    switch (error.networkResponse.statusCode) {
                        case 401:
                            MyPreferenceManager.getInstance(activity).clear();
                            NavigationHelper.shared.MainActivity(activity);
                            break;
                        case 402:
                            NavigationHelper.shared.MainActivity(activity);
                            break;
                    }
                }

            }
        }) {

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int statusCode = response.statusCode;
                Log.e("StatusCode",""+statusCode);
                return super.parseNetworkResponse(response);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                String userAgent = System.getProperty("http.agent");

                mHeaderParams.put("LANG", lang);

                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;
                versionName = versionName.replace("'","");
                mHeaderParams.put("VERSION", versionName);

                String model = Build.MODEL;
                mHeaderParams.put("MODEL", model);

                Log.v("apiHeaderLog", mHeaderParams.toString());

                return mHeaderParams;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0,-1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return stringRequest;
    }
}
