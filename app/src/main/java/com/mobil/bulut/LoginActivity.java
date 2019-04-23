package com.mobil.bulut;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mobil.bulut.Adapter.CustomersAdapter;
import com.mobil.bulut.Database.DatabaseHelper;
import com.mobil.bulut.Helpers.NavigationHelper;
import com.mobil.bulut.Helpers.WheelView;
import com.mobil.bulut.Models.BaglantiKodu;
import com.mobil.bulut.Models.Customers;
import com.mobil.bulut.Models.LoginResponse;
import com.mobil.bulut.Services.Api;
import com.mobil.bulut.Services.ApiRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    int screenSizeWidth, screenSizeHeight;

    @BindView(R.id.imageArea)                   RelativeLayout imageArea;
    @BindView(R.id.veritabaniKurulumuAlani)     RelativeLayout veritabaniKurulumuAlani;
    @BindView(R.id.girisEkraniAlani)            RelativeLayout girisEkraniAlani;
    @BindView(R.id.kayitliBaglantiAlani)        RelativeLayout kayitliBaglantiAlani;

    @BindView(R.id.recordedBtn)                 ImageView recordedBtn;
    @BindView(R.id.connectionBtn)               ImageView connectionBtn;

    @BindView(R.id.edtCode)                     EditText edtCode;

    @BindView(R.id.edtPassword)                 EditText edtPassword;

    @BindView(R.id.txtUsername)                 TextView txtUsername;

    @BindView(R.id.logo)                        RelativeLayout logo;

    @BindView(R.id.customersRecyclerView)       RecyclerView customersRecyclerView;

    @BindView(R.id.wheelScreen)                 RelativeLayout wheelScreen;

    @BindView(R.id.main_wv)                 WheelView wva;

    private static final String TAG = LoginActivity.class.getSimpleName();

    String selectedScreen = "giris";

    private DatabaseHelper db;

    public static String[] CUSTOMDATA = null;

    private List<Customers> customers = new ArrayList<>();

    private CustomersAdapter customersAdapter;

    public static LoginActivity loginActivity;

    String versionName;
    String android_id;

    String selectedUserName = null;
    String selectedUuid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        versionName = BuildConfig.VERSION_NAME; //App version
        android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        Log.v("androidName", versionName+ " : "+ android_id);

        loginActivity = LoginActivity.this;

        changeStatusColor(ContextCompat.getColor(loginActivity, R.color.colorPrimary));

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        screenSizeWidth = metrics.widthPixels;

        imageArea.getLayoutParams().height = (int) ((screenSizeWidth/10)*4.5);
        logo.getLayoutParams().height = screenSizeWidth/3;

        customersAdapter = new CustomersAdapter(this, customers, loginActivity);
        customersRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        customersRecyclerView.setAdapter(customersAdapter);

        customersRecyclerView.setHasFixedSize(true);
        customersRecyclerView.setItemViewCacheSize(20);
        customersRecyclerView.setDrawingCacheEnabled(true);
        customersRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        db = new DatabaseHelper(LoginActivity.this);

        wva.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "selectedIndex: " + selectedIndex + ", item: " + item);

                txtUsername.setText(customers.get(selectedIndex-1).getUsername());
                selectedUserName = customers.get(selectedIndex-1).getUsername();
                selectedUuid = customers.get(selectedIndex-1).getUuid();

            }
        });

        loadData();

    }

    public void loadData(){

        customers.clear();
        customers.addAll(db.getAllCustomers());
        customersAdapter.notifyDataSetChanged();

        Log.v("ataberk", customers.get(0).getUuid()+"");

        checkCustomers();

    }

    @OnClick(R.id.recordedBtn)
    public void recordedBtnClick(){

        if(customers.size()!=0){
            if(selectedScreen.equals("giris")){
                selectedScreen = "kayitli";
            }else {
                selectedScreen = "giris";
            }

            changeScreen();
        }

    }

    @OnClick(R.id.connectionBtn)
    public void connectionBtnClick(){

        manuelEkle();

        /*
        if(customers.size()!=0){

            if(selectedScreen.equals("giris")){
                selectedScreen = "veritabani";
            }else {
                selectedScreen = "giris";
            }

            changeScreen();
        }

        */

    }

    public void changeScreen(){

        veritabaniKurulumuAlani.setVisibility(View.GONE);
        girisEkraniAlani.setVisibility(View.GONE);
        kayitliBaglantiAlani.setVisibility(View.GONE);

        switch (selectedScreen){
            case "veritabani":
                veritabaniKurulumuAlani.setVisibility(View.VISIBLE);
                break;
            case "giris":
                girisEkraniAlani.setVisibility(View.VISIBLE);
                break;
            case "kayitli":
                kayitliBaglantiAlani.setVisibility(View.VISIBLE);
                break;
        }

    }

    @OnClick(R.id.addCustomerBtn)
    public void addCustomerBtnClick(){

        if(!edtCode.getText().toString().equals("")){
            String code = edtCode.getText().toString();
            getBaglantiKodu(code);
        }

    }

    private void getBaglantiKodu(String code) {

        String url = Api.service_URL_BAGLANTI_KODU+code;

        ApiRequest.getInstance().fetch(true, Request.Method.GET, url, null, "Hata Oluştu", this, new com.android.volley.Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Log.w("getBaglantiKodu", response.toString());
                try {
                    BaglantiKodu model = gson.fromJson(response.toString(), BaglantiKodu.class);

                    if(model.getSuccess()){
                        insertCustomer(model);
                    }else {
                        Toast.makeText(getApplicationContext(),model.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.v("getFieldsError", error.getMessage());
            }
        });

    }

    public void insertCustomer(BaglantiKodu model){

        String firma = model.getFirma();
        String uuid = model.getUuid();
        String username = model.getUsername();

        long firstId = db.insertCustomer(firma,uuid,username);

        customers.clear();
        customers.addAll(db.getAllCustomers());

        edtCode.setText("");

        checkCustomers();

    }

    public void deleteCustomer(int position){
        db.deleteBeacon(customers.get(position));
        customers.remove(position);
        customersAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.kayitliBaglantiBtn)
    public void kayitliBaglantiBtnClick(){
        checkCustomers();

    }

    public void manuelEkle(){

        String firma = "cemka";
        String uuid = "43242342";
        String username = "ataberk kocaman";

        long firstId = db.insertCustomer(firma,uuid,username);

        customers.clear();
        customers.addAll(db.getAllCustomers());

        checkCustomers();

    }


    public void checkCustomers(){

        CUSTOMDATA = new String[customers.size()];


        Log.v("CUSTOMDATA_SIZE", CUSTOMDATA.length+"---");

        for (int i = 0; i <customers.size() ; i++) {
            CUSTOMDATA[i] = customers.get(i).getFirma();
        }

        wva.setOffset(1);
        wva.setItems(Arrays.asList(CUSTOMDATA));

        if(customers.size()==0){
            selectedScreen = "veritabani";
            changeScreen();
        }else if(customers.size()==1){
            txtUsername.setText(customers.get(0).getUsername());
            selectedUserName = customers.get(0).getUsername();
            selectedUuid = customers.get(0).getUuid();
            wheelScreen.setVisibility(View.GONE);
            selectedScreen = "giris";
            changeScreen();
        }else {
            txtUsername.setText(customers.get(0).getUsername());
            selectedUserName = customers.get(0).getUsername();
            selectedUuid = customers.get(0).getUuid();
            wheelScreen.setVisibility(View.VISIBLE);
            selectedScreen = "giris";
            changeScreen();
        }


    }

    @OnClick(R.id.loginBtn)
    public void loginBtnClick(){

        if(!edtPassword.getText().toString().equals("")){

            String password = edtPassword.getText().toString();
            getLoginRepsonse(selectedUserName, password, selectedUuid);

        }

    }

    private void getLoginRepsonse(String username, String password, String uuid) {

        String meme = "username="+username+"&password="+password+"&deviceid="+android_id+"&version="+versionName+"&os=Android&uuid="+uuid;
        String url = Api.service_URL_LOGIN+meme;

        Log.v("urlHoho",url);

        ApiRequest.getInstance().fetch(true, Request.Method.GET, url, null, "Hata Oluştu", this, new com.android.volley.Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Log.w("getBaglantiKodu", response.toString());
                try {
                    LoginResponse model = gson.fromJson(response.toString(), LoginResponse.class);

                    if(model.getSuccess()){
                       gotoMain();
                    }else {
                        Toast.makeText(getApplicationContext(),model.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.v("getFieldsError", error.getMessage());
            }
        });

    }

    public void gotoMain(){
        NavigationHelper.shared.MainActivity(this);
        finish();
    }

}
