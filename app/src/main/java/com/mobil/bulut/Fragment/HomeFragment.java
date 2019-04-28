package com.mobil.bulut.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobil.bulut.Adapter.MainAdapter;
import com.mobil.bulut.BaseFragment;
import com.mobil.bulut.Helpers.CacheDatas;
import com.mobil.bulut.Models.LoginResponse;
import com.mobil.bulut.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mobil.bulut.Services.Api.service_URL_FIRMA;

public class HomeFragment extends BaseFragment {

    View view = null;

    @BindView(R.id.mainRecyclerView)        RecyclerView mainRecyclerView;

    MainAdapter mainAdapter;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            ButterKnife.bind(this, view);

            mainAdapter = new MainAdapter(getActivity().getApplicationContext(), menuler);

            mainRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
            mainRecyclerView.setAdapter(mainAdapter);

            mainRecyclerView.setHasFixedSize(true);
            mainRecyclerView.setItemViewCacheSize(20);
            mainRecyclerView.setDrawingCacheEnabled(true);
            mainRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

            getMenus();

        }

        return view;
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        Log.d("onConfiguration", "config changed");

        super.onConfigurationChanged(newConfig);

        int orientation = newConfig.orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT){

            Log.d("tag", "Portrait");
            mainRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE){

            Log.d("tag", "Landscape");
            mainRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 8));

        } else
            Log.w("tag", "other: " + orientation);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
