package com.mobil.bulut;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mobil.bulut.Activity.MainActivity;
import com.mobil.bulut.Helpers.MyPreferenceManager;

import java.lang.reflect.Field;

public class BaseFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "BaseFragment";
    public Gson gson;
    public MyPreferenceManager myPreferenceManager;
    public Activity mActivity;
    public Resources resources;

    ProgressDialog progressDialog = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (Activity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (MainActivity.sharedInstance == null && this.getActivity().getClass() == MainActivity.class)
            restartApp();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);

        myPreferenceManager = MyPreferenceManager.getInstance(mActivity);
        resources = getResources();
        gson = new Gson();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return null;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void willBecomeVisible() {

    }

    public void goToFragment(BaseFragment fragment) {
    }

    public void tabSelectedSecondTime() {
    }

    public void refresh() {
    }

    public void onBackPressed() {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void restartApp() {
        getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();

    }

    @Override
    public void onClick(View v) {

    }

    public void showProgressDialog(String message){
        progressDialog.dismiss();
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void hideProgressDialog(){
        progressDialog.dismiss();
    }

    public void txtBold(TextView textView){
        textView.setTypeface(null, Typeface.BOLD);
    }


}