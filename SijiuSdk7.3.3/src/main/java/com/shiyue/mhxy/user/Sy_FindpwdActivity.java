package com.shiyue.mhxy.user;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.shiyue.mhxy.common.BaseActivity;

import com.shiyue.mhxy.config.AppConfig;

import com.shiyue.mhxy.http.ApiAsyncTask;


import java.util.HashMap;
import java.util.List;

public class Sy_FindpwdActivity extends BaseActivity implements OnClickListener {

	private ApiAsyncTask telephoneTask;
	private ApiAsyncTask findqTask;
	private TextView tv_findpwd;
	private FrameLayout fl_findpwd;
	private ImageView back;
	private List<String> strings;
    private Sy_pwdFragment_f pwdfrg_f;
	private Sy_pwdFragment_s pwdfrg_s;
	private Sy_pwdFragment_t pwdfrg_t;
//	private final int TELEPHONE = 41;
    private  String account="";
	private  String  phonenum="";
	private  String  authcode="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
			setContentView(AppConfig
					.resourceId(this, "sy_findpwd_land", "layout"));
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		tv_findpwd = (TextView) findViewById(AppConfig
				.resourceId(this, "tv_findpwd", "id"));
		fl_findpwd = (FrameLayout) findViewById(AppConfig.resourceId(this, "fl_findpwd", "id"));
		back= (ImageView) findViewById(AppConfig.resourceId(this, "imbtn_back_login", "id"));

		Intent intent=getIntent();
		account=intent.getStringExtra("account");
		back.setOnClickListener(this);
		setDefaultFragment();
	}

	private void setDefaultFragment() {

		frg_f();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == AppConfig.resourceId(this, "imbtn_back_login", "id")) {
			Sy_FindpwdActivity.this.finish();
		}
	}






public String getAccount(){

		return  account;

	}
	public String getCode(){

		return  authcode;

	}

	public String getPhonenum(){

		return  phonenum;

	}

	//忘记密码（1/3）frgment
	private void  frg_f(){
		FragmentManager fm = getFragmentManager();
		// 开启Fragment事务
		FragmentTransaction transaction = fm.beginTransaction();
		pwdfrg_f=new Sy_pwdFragment_f();
		transaction.replace( AppConfig.resourceId(this, "fl_findpwd", "id"),pwdfrg_f);
//		transaction.replace(R.id.frg_regist_fragment,ph_regist_fragment);
		transaction.commit();
	}
	//忘记密码（2/3）frgment
	private void  frg_s(){
		FragmentManager fm = getFragmentManager();
		// 开启Fragment事务
		FragmentTransaction transaction = fm.beginTransaction();
		pwdfrg_s=new Sy_pwdFragment_s();
		transaction.replace( AppConfig.resourceId(this, "fl_findpwd", "id"),pwdfrg_s);
//		transaction.replace(R.id.frg_regist_fragment,ph_regist_fragment);
		transaction.commit();
	}
	//忘记密码（3/3）frgment
	private void  frg_t(){
		FragmentManager fm = getFragmentManager();
		// 开启Fragment事务
		FragmentTransaction transaction = fm.beginTransaction();
		pwdfrg_t=new Sy_pwdFragment_t();
		transaction.replace( AppConfig.resourceId(this, "fl_findpwd", "id"),pwdfrg_t);
//		transaction.replace(R.id.frg_regist_fragment,ph_regist_fragment);
		transaction.commit();
	}
	public Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {

			case AppConfig.FINDPWD_F:
			HashMap hs= (HashMap) msg.obj;
				phonenum= (String) hs.get("phone");
				account= (String) hs.get("account");

				tv_findpwd.setText("找回密码(2/3)");

				frg_s();
				break;
				case AppConfig.FINDPWD_S:
					HashMap hashMap= (HashMap) msg.obj;

					account= (String) hashMap.get("account");
                    authcode=(String)hashMap.get("auth_code");
					tv_findpwd.setText("找回密码(3/3)");

					frg_t();
					break;
				case AppConfig.FINDPWD_T:
					Sy_FindpwdActivity.this.finish();
					break;
			case AppConfig.FLAG_REQUEST_ERROR:
				showMsg((String) msg.obj);
				break;
			case AppConfig.FLAG_FINDQ_SUCCESS:
				ResultAndMessage resultfind = (ResultAndMessage) msg.obj;
				strings=resultfind.getDatas();
				if (strings!=null) {
//					findDialog.setwenti(strings.get(0));
				}
				break;
			}
		}
	};

//	@Override
//	protected void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//		if (dialog != null) {
//			dialog.dismiss();
//		}
//		if (findDialog != null) {
//			findDialog.dismiss();
//		}
//		if (pdialog != null) {
//			pdialog.dismiss();
//		}
//	}
	
//	public void sendDataobj(int num, Object data, Handler callback) {
//		Message msg = callback.obtainMessage();
//		msg.what = num;
//		msg.obj = data;
//		msg.sendToTarget();
//	}
}
