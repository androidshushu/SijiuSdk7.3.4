package com.shiyue.mhxy.user;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.shiyue.mhxy.common.BaseActivity;
import com.shiyue.mhxy.config.AppConfig;
import com.shiyue.mhxy.http.ApiAsyncTask;

import java.util.List;

public class Sy_BindPhoneActivity extends BaseActivity implements View.OnClickListener {
    private ApiAsyncTask telephoneTask;
    private ImageView back;
    private FrameLayout bind_fragement;
    private List<String> strings;
    private Sy_PhbindFragment bindphonefragment;
    private String account = "" ;
    private String phonenum = "";
    private String authcode = "";
    private String accounts;

    public String getAccount() {
        return account;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public String getAuthcode() {
        return authcode;
    }

    @Override
    protected void onResume() {
        /**
         * 设置为横屏
         */
//        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Bundle myAccounts = this.getIntent().getExtras();
         accounts = myAccounts.getString("key_account");
//        Log.d("accounts=",accounts);
        setContentView(AppConfig
                .resourceId(this, "sy__bind_phone_activity", "layout"));
        init();
    }

    private void init() {
        bind_fragement = (FrameLayout) findViewById(AppConfig.resourceId(this,"bind_fragment","id"));
        back = (ImageView) findViewById(AppConfig.resourceId(this,"bind_back","id"));
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        back.setOnClickListener(this);
        setDefaultFragement();

    }

    private void setDefaultFragement() {
        bind_frag();
    }

    private void bind_frag() {
        FragmentManager fm = getFragmentManager();
        //开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        bindphonefragment = new Sy_PhbindFragment();
        Bundle bundle = new Bundle();
        bundle.putString("account_data",accounts);
        bindphonefragment.setArguments(bundle);
        transaction.replace(AppConfig.resourceId(this,"bind_fragment","id"),bindphonefragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == AppConfig.resourceId(this,"bind_back","id")){
            Sy_BindPhoneActivity.this.finish();
        }


    }
//    public Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//        }
//    }
}
