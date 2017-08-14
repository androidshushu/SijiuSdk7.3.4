//package com.shiyue.mhxy.user;
//
//import com.shiyue.mhxy.common.Sjyx;
//import com.shiyue.mhxy.config.AppConfig;
//import com.shiyue.mhxy.http.ApiAsyncTask;
//import com.shiyue.mhxy.http.ApiRequestListener;
//import com.shiyue.mhxy.sdk.SiJiuSDK;
//import com.shiyue.mhxy.user.UserModifyDialog.ResultListener;
//import com.shiyue.mhxy.user.UserResultDialog.UserResultListener;
//import com.shiyue.mhxy.utils.Seference;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.View;
//import android.view.WindowManager;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class UserCenterMainActivity extends Activity implements OnClickListener {
//	private Button gobackbut;// user_goback
//	private ImageButton  amendIbut;
//	private RelativeLayout userRl,user_safety_info,user_money_info,user_sms_info;
//	private TextView user_safety_icon,user_safety_text,tv_youke,tv_youkea,tv_youkeb;
//	private LinearLayout ll_youke;
//	private ApiAsyncTask bdTask;
//	private ApiAsyncTask userModifyTask;
//	private ApiAsyncTask bdmTask;
//	private UserModifyDialog userModifyDialog;
//	private UserResultDialog userResultDialog;
//	private String isBound="0";
//	private String userName ,password,newuserName ,newpassword;
//	private Seference seference;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
//		setContentView(AppConfig.resourceId(this, "user_center_main", "layout"));
//		seference = new Seference(this);
//		checkBoundPhoneHttp();
//		inintView();
//	}
//
//	private void inintView() {
//		// TODO Auto-generated method stub
//		gobackbut = (Button) findViewById(AppConfig.resourceId(this, "user_goback", "id"));
//		amendIbut = (ImageButton) findViewById(AppConfig.resourceId(this, "amend_usre", "id"));
//		userRl= (RelativeLayout) findViewById(AppConfig.resourceId(this, "user_info","id"));
//		user_safety_info= (RelativeLayout) findViewById(AppConfig.resourceId(this, "user_safety_info","id"));
//		user_money_info= (RelativeLayout) findViewById(AppConfig.resourceId(this, "user_money_info","id"));
//		user_sms_info= (RelativeLayout) findViewById(AppConfig.resourceId(this, "user_sms_info","id"));
//		user_safety_icon= (TextView) findViewById(AppConfig.resourceId(this, "user_safety_icon","id"));
//		user_safety_text= (TextView) findViewById(AppConfig.resourceId(this, "user_safety_text","id"));
//		tv_youkea= (TextView) findViewById(AppConfig.resourceId(this, "tv_youkea","id"));
//		tv_youkeb= (TextView) findViewById(AppConfig.resourceId(this, "tv_youkeb","id"));
//		tv_youke= (TextView) findViewById(AppConfig.resourceId(this, "tv_youke","id"));
//		ll_youke= (LinearLayout) findViewById(AppConfig.resourceId(this, "ll_youke","id"));
//
//		userName = AppConfig.loginMap.get("user");
//		password = AppConfig.loginMap.get("pwd");
//		tv_youke.setText("账号为："+userName);
//		if (AppConfig.userType==1) {
//			tv_youkea.setVisibility(View.VISIBLE);
//			tv_youkeb.setVisibility(View.VISIBLE);
//			amendIbut.setVisibility(View.VISIBLE);
//		}else {
//			tv_youkea.setVisibility(View.GONE);
//			tv_youkeb.setVisibility(View.GONE);
//			amendIbut.setVisibility(View.GONE);
//
//		}
//
//		gobackbut.setOnClickListener(this);
//		amendIbut.setOnClickListener(this);
//		userRl.setOnClickListener(this);
//		user_safety_info.setOnClickListener(this);
//		user_money_info.setOnClickListener(this);
//		user_sms_info.setOnClickListener(this);
//	}
//
//	@Override
//	protected void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//		Sjyx.isShow = true;
////		Sjyx.icon.setVisibility(View.VISIBLE);
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		if(v.getId()==AppConfig.resourceId(this, "amend_usre", "id")){
////			downDialog();
//			userModify();
//
//		} else if(v.getId()==AppConfig.resourceId(this, "user_info","id")){
//
////			Intent user=new Intent(UserCenterMainActivity.this,UserCenterActivity.class);
////			user.putExtra("UserCenter", "info");
////			user.putExtra("isBound", isBound);
////			startActivity(user);
//		} else if(v.getId()==AppConfig.resourceId(this, "user_safety_info","id")){
//
////			Intent user=new Intent(UserCenterMainActivity.this,UserCenterActivity.class);
////			user.putExtra("UserCenter", "safety");
////			user.putExtra("isBound", isBound);
////			startActivity(user);
//		} else if(v.getId()==AppConfig.resourceId(this, "user_money_info","id")){
//
////			Intent user=new Intent(UserCenterMainActivity.this,UserCenterActivity.class);
////			user.putExtra("UserCenter", "money");
////			user.putExtra("isBound", isBound);
////			startActivity(user);
//		} else if(v.getId()==AppConfig.resourceId(this, "user_sms_info","id")){
//
////			Intent user=new Intent(UserCenterMainActivity.this,UserCenterActivity.class);
////			user.putExtra("UserCenter", "sms");
////			user.putExtra("isBound", isBound);
////			startActivity(user);
//
//		}else if(v.getId()==AppConfig.resourceId(this, "user_goback", "id")){
//			finish();
//			Sjyx.isShow = true;
////			Sjyx.icon.setVisibility(View.VISIBLE);
//		}
//	}
//
//	private void downDialog() {
//
//		UserDialog dialog = new UserDialog(this,AppConfig.resourceId(
//				this, "Sj_MyDialog", "style"), AppConfig.resourceId(
//						this, "sjuser_revamp", "layout"));
//		dialog.setCancelable(false);
//		dialog.show();
//	}
//
//	public void sendData(int num, Object data, Handler callback) {
//		Message msg = callback.obtainMessage();
//		msg.what = num;
//		msg.obj = data;
//		msg.sendToTarget();
//	}
///*
// * 检查是否绑定号码
// */
//	public void checkBoundPhoneHttp() {
//		bdTask = SiJiuSDK.get().startCheckBoundPhone(this, AppConfig.appId, AppConfig.appKey,AppConfig.uid,
//				 new ApiRequestListener() {
//
//					@Override
//					public void onSuccess(Object obj) {
//						if (obj != null) {
//							sendData(AppConfig.FLAG_SUCCESS, obj, myHandler);
//						} else {
//							sendData(AppConfig.FLAG_FAIL, "获取数据失败!", myHandler);
//						}
//					}
//
//					@Override
//					public void onError(int statusCode) {
//						sendData(AppConfig.FLAG_REQUEST_ERROR, "链接出错，请重试!",
//								myHandler);
//					}
//				});
//	}
///**
// * 检查是否绑定密保
// */
//	public void checkAnswerHttp() {
//		bdmTask = SiJiuSDK.get().startCheckAnswer(this, AppConfig.appId, AppConfig.appKey,AppConfig.uid,
//				 new ApiRequestListener() {
//
//					@Override
//					public void onSuccess(Object obj) {
//						if (obj != null) {
//							sendData(AppConfig.FLAG_YEEPAY_SUCCESS, obj, myHandler);
//						} else {
//							sendData(AppConfig.FLAG_FAIL, "获取数据失败!", myHandler);
//						}
//					}
//
//					@Override
//					public void onError(int statusCode) {
//						sendData(AppConfig.FLAG_REQUEST_ERROR, "链接出错，请重试!",
//								myHandler);
//					}
//				});
//	}
//	/*
//	 * 弹出修改游客账号密码窗
//	 */
//	private void userModify() {
//		userModifyDialog=new UserModifyDialog(this, AppConfig.resourceId(
//				this, "Sj_MyDialog", "style"), userName, password, new ResultListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if(v.getId()==AppConfig.resourceId(getApplicationContext(), "resultbutton_e", "id")){
//					String u=userModifyDialog.getusername();
//					String p=userModifyDialog.getpassword();
//					String pt=userModifyDialog.getpasswordto();
//					if (p.equals(pt)) {
//						userModifyHttp(u, p, p);
//					}else {
//						Toast.makeText(getApplicationContext(), "两次密码不一致", Toast.LENGTH_SHORT).show();
//					}
//
//				}else if(v.getId()==AppConfig.resourceId(getApplicationContext(), "resultbutton", "id")){
//					userModifyDialog.dismiss();
//				}
//
//			}
//		});
//		userModifyDialog.setCancelable(false);
//		userModifyDialog.show();
//
//	}
///**
// * 修改账号及密码
// * @param username账号
// * @param password密码
// * @param newPassword重复密码
// */
//	private void userModifyHttp(String username,String password,String newPassword) {
//		userModifyTask = SiJiuSDK.get().startuserModify(this, AppConfig.appId, username, password, newPassword,
//				AppConfig.appKey,AppConfig.uid, new ApiRequestListener() {
//
//					@Override
//					public void onSuccess(Object obj) {
//						if (obj != null) {
//
//							ResultAndMessage result = (ResultAndMessage) obj;
//							if (result.getResult()) {
//								sendData(AppConfig.FLAG_USERMODIFY_SUCCESS, obj, myHandler);
//							}else {
//								sendData(AppConfig.FLAG_FAIL, result.getMessage(), myHandler);
//							}
//						} else {
//							sendData(AppConfig.FLAG_FAIL, "获取数据失败!", myHandler);
//						}
//					}
//
//					@Override
//					public void onError(int statusCode) {
//						sendData(AppConfig.FLAG_REQUEST_ERROR, "链接出错，请重试!",
//								myHandler);
//					}
//				});
//	}
//	/**
//	 * 弹出修改结果窗
//	 */
//	private void userModifyResult() {
//		userResultDialog=new UserResultDialog(this, AppConfig.resourceId(
//				this, "Sj_MyDialog", "style"), userName, password, new UserResultListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if(v.getId()==AppConfig.resourceId(getApplicationContext(), "bt_user", "id")){
//					tv_youkea.setVisibility(View.GONE);
//					tv_youkeb.setVisibility(View.GONE);
//					amendIbut.setVisibility(View.GONE);
//					tv_youke.setText("账号为："+userName);
//					userResultDialog.dismiss();
//				}
//
//			}
//		});
//		userResultDialog.setCancelable(false);
//		userResultDialog.show();
//	}
//
//	private Handler myHandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			// TODO Auto-generated method stub
//			switch (msg.what) {
//			case AppConfig.FLAG_SUCCESS:
//				ResultAndMessage resultCode = (ResultAndMessage) msg.obj;
//				if (resultCode.getResult()) {
//					user_safety_icon.setVisibility(View.GONE);
//					user_safety_text.setVisibility(View.VISIBLE);
//					user_safety_text.setText("已绑定手机");
//				}else {
//					checkAnswerHttp();
//				}
//
//				break;
//			case AppConfig.FLAG_USERMODIFY_SUCCESS:
//				userName=userModifyDialog.getusername();
//				password=userModifyDialog.getpassword();
//				userModifyDialog.dismiss();
//				seference.saveAccount(userName, password, AppConfig.uid);
//				AppConfig.saveMap(userName, password, AppConfig.uid);
//				AppConfig.userType=2;
//				AppConfig.userName=userName;
//				userModifyResult();
//
//				break;
//
//			case AppConfig.FLAG_FAIL:
//				String message =(String)msg.obj;
//				Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//
//				break;
//			case AppConfig.FLAG_YEEPAY_SUCCESS:
//				ResultAndMessage resultbd = (ResultAndMessage) msg.obj;
//				if (resultbd.getResult()) {
//					user_safety_icon.setVisibility(View.GONE);
//					user_safety_text.setVisibility(View.VISIBLE);
//					user_safety_text.setText("已绑定密保");
//				}else {
//					user_safety_icon.setVisibility(View.VISIBLE);
//					user_safety_text.setVisibility(View.VISIBLE);
//					user_safety_text.setText("存在安全风险");
//				}
//
//				break;
//			}
//
//		}
//	};
//
//}
