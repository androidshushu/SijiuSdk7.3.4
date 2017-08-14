package com.shiyue.mhxy.user;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.shiyue.mhxy.common.BaseActivity;
import com.shiyue.mhxy.config.AppConfig;

public class Sy_PhloginActivity extends BaseActivity implements  OnClickListener {

    private Sy_Ph_registFragment ph_regist_fragment=null;

     private ImageView lg_back;

	@Override
	protected void onCreate(Bundle savedInstanceState ) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

			setContentView(AppConfig.resourceId(this, "syphlogin_land", "layout"));
		lg_back = (ImageView) findViewById(AppConfig.resourceId(this, "login_back",
				"id"));
		setPhRegistFragment();
		lg_back.setOnClickListener(this);

	}

	private void setPhRegistFragment() {

		FragmentManager fm = getFragmentManager();
		// 开启Fragment事务
		FragmentTransaction transaction = fm.beginTransaction();
		ph_regist_fragment=new Sy_Ph_registFragment();

		Bundle bundle=new Bundle();
		bundle.putString("type","1");
        ph_regist_fragment.setArguments(bundle);
		transaction.replace( AppConfig.resourceId(this, "frg_regist_fragment", "id"),ph_regist_fragment);
//		transaction.replace(R.id.frg_regist_fragment,ph_regist_fragment);
		transaction.commit();


	}


	@Override
	public void onClick(View view) {
		if (view.getId() == AppConfig.resourceId(this, "login_back", "id")) {
			   Sy_PhloginActivity.this.finish();
		}
	}
}
