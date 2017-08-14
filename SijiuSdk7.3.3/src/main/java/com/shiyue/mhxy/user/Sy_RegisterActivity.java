package com.shiyue.mhxy.user;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiyue.mhxy.common.BaseActivity;
import com.shiyue.mhxy.config.AppConfig;
import com.shiyue.mhxy.support.PercentRelativeLayout;


public class Sy_RegisterActivity extends BaseActivity implements OnClickListener {

	private TextView btn_regist;
	private TextView btn_ph_regist;
	private ImageView imbtn_back_regist;
	private ImageView view_anim;
	private Sy_registFragment regist_fragment=null;
    private Sy_Ph_registFragment ph_regist_fragment=null;
	private PercentRelativeLayout layout;
    private FrameLayout frameLayout;
	private float regitercenter=0.0f,phregistercenter=0.0f;
	private boolean isfirst=true;
	private boolean ismove=true;

	private boolean isphmove=false;
	@Override
	protected void onCreate(Bundle savedInstanceState ) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
			setContentView(AppConfig.resourceId(this, "syregister_land", "layout"));
		init();
		setRegistFragment();

	}



	private void init() {
		// TODO Auto-generated method stub
		btn_regist = (TextView) findViewById(AppConfig.resourceId(this, "btn_regist", "id"));
		btn_ph_regist = (TextView) findViewById(AppConfig.resourceId(this, "btn_ph_regist", "id"));
		layout = (PercentRelativeLayout) findViewById(AppConfig.resourceId(this, "lilayout", "id"));
		imbtn_back_regist = (ImageView) findViewById(AppConfig.resourceId(this, "imbtn_back_regist", "id"));
		frameLayout= (FrameLayout) findViewById(AppConfig.resourceId(this, "frg_regist_fragment", "id"));
		view_anim=(ImageView) findViewById(AppConfig.resourceId(this, "view_anim", "id"));

		btn_regist.setOnClickListener(this);
		btn_ph_regist.setOnClickListener(this);
		imbtn_back_regist.setOnClickListener(this);



	}

	private void getViewLocation() {


//		Log.d("shiyue",+layout.getWidth()+"!!!"+layout.getHeight());
//		int[] location = new int[2];
////        view_anim.getLocationInWindow(location);
//		view_anim.getLocationOnScreen(location);
//		int x = location[0];
//		int y = location[1];
//		Log.d("shiyue",x+"*"+y+"left"+view_anim.getLeft()+"right"+view_anim.getRight()+"top"+view_anim.getTop()+"buttom"+view_anim.getBottom());
//
//		int[] location1 = new int[2];
//		btn_regist.getLocationInWindow(location1);
////		btn_regist.getLocationOnScreen(location);
//		int x1 = location1[0];
//		int y1 = location1[1];
		regitercenter= (float) ((float) layout.getWidth()*0.28);
//		Log.d("shiyue",x1+"*"+y1+"left"+btn_regist.getLeft()+"right"+btn_regist.getRight()+"top"+btn_regist.getTop()+"buttom"+btn_regist.getBottom()+"ffff="+regitercenter);
//
//		int[] location2 = new int[2];
//		btn_ph_regist.getLocationInWindow(location2);
////		btn_regist.getLocationOnScreen(location);
//		int x2 = location2[0];
//		int y2= location2[1];
		phregistercenter=(float) ((float) layout.getWidth()*0.68);

//		Log.d("shiyue",x2+"*"+y2+"left"+btn_ph_regist.getLeft()+"right"+btn_ph_regist.getRight()+"top"+btn_ph_regist.getTop()+"buttom"+btn_ph_regist.getBottom()+"ffff="+phregistercenter);
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == AppConfig.resourceId(this, "btn_regist", "id")) {

                   if(isphmove){
					   isphmove=false;
					   ismove=true;
					   anim(view_anim,phregistercenter,regitercenter,500);
				   }


			   setRegistFragment();

		}
		else if(v.getId() == AppConfig.resourceId(this, "btn_ph_regist", "id")){

			if(ismove){
				ismove=false;
				isphmove=true;
				anim(view_anim,regitercenter,phregistercenter,500);
			}

				setPhRegistFragment();

		}
 else if(v.getId()==AppConfig.resourceId(this, "imbtn_back_regist", "id")){

			this.finish();

		}

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if(isfirst){

			getViewLocation();
			anim(view_anim,0,regitercenter,100);
			isfirst=false;
		}

	}

	private void anim(ImageView imageView, float x, float y,int sec){

//		ObjectAnimator
//				.ofFloat(imageView, "translationX", 322, 805)
//				.setDuration(1000)//
//				.start();

		ObjectAnimator
				.ofFloat(imageView, "translationX", x, y)
				.setDuration(sec)//
				.start();
	}

	private void setPhRegistFragment() {

		FragmentManager fm = getFragmentManager();
		// 开启Fragment事务
		FragmentTransaction transaction = fm.beginTransaction();
		ph_regist_fragment=new Sy_Ph_registFragment();
		Bundle bundle=new Bundle();
		bundle.putString("type","0");
		ph_regist_fragment.setArguments(bundle);
		transaction.replace( AppConfig.resourceId(this, "frg_regist_fragment", "id"),ph_regist_fragment);
//		transaction.replace(R.id.frg_regist_fragment,ph_regist_fragment);
		transaction.commit();


		btn_ph_regist.setTextColor( this.getResources().getColor(
				AppConfig.resourceId(this, "sjwhite", "color")));
////
		btn_regist.setTextColor( this.getResources().getColor(
				AppConfig.resourceId(this, "sygray_white", "color")));

	}

	private void setRegistFragment() {

		FragmentManager fm = getFragmentManager();
		// 开启Fragment事务
		FragmentTransaction transaction = fm.beginTransaction();
		regist_fragment=new Sy_registFragment();
		transaction.replace( AppConfig.resourceId(this, "frg_regist_fragment", "id"),regist_fragment);
//		transaction.replace(R.id.frg_regist_fragment,ph_regist_fragment);
		transaction.commit();

		btn_regist.setTextColor( this.getResources().getColor(
		AppConfig.resourceId(this, "sjwhite", "color")));
		btn_ph_regist.setTextColor( this.getResources().getColor(
				AppConfig.resourceId(this, "sygray_white", "color")));
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		 isfirst=false;
	}
}
