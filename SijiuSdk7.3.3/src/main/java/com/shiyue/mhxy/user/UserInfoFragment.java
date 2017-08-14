//package com.shiyue.mhxy.user;
//
//import java.util.Properties;
//
//import com.shiyue.mhxy.common.PromptDialog;
//import com.shiyue.mhxy.common.Sjyx;
//import com.shiyue.mhxy.common.TipsDialog;
//import com.shiyue.mhxy.common.PromptDialog.PromptListener;
//import com.shiyue.mhxy.common.TipsDialog.DialogListener;
//import com.shiyue.mhxy.config.AppConfig;
//import com.shiyue.mhxy.news.GiftListDialog;
//import com.shiyue.mhxy.news.GiftListDialog.Phonelistener;
//import com.shiyue.mhxy.http.ApiAsyncTask;
//import com.shiyue.mhxy.http.ApiRequestListener;
//import com.shiyue.mhxy.pay.AliPayMessage;
//import com.shiyue.mhxy.sdk.SiJiuSDK;
//import com.sijiu7.update.UpdataDialog;
//import com.sijiu7.update.UpdataDialog.UpdataListener;
//import com.sijiu7.update.UpdateApp;
//import com.shiyue.mhxy.user.UserModifyDialog.ResultListener;
//import com.shiyue.mhxy.user.UserResultDialog.UserResultListener;
//import com.shiyue.mhxy.utils.Seference;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//public class UserInfoFragment extends Fragment implements OnClickListener{
//
//	private View iview;
//	private RelativeLayout user_info,rl_poss,rl_zenbi,user_pingtai ,user_vip,user_update;
//	private LinearLayout ll_password,ll_youke,ll_user;
//	private EditText et_yuanpassword,et_newpassword,et_twopassword;
//	private TextView tv_zhanghao,tv_zenbi,tv_zhanghaop,tv_zengbiyue,tv_pingtaibiyue,tv_pingtaibi_info_desc,tv_youke,tv_youkea,tv_youkeb;
//	private TextView ll_user_a,user_number;
//	private Button button_b,button_t;
//	private ImageButton  amendIbut;
//	private TipsDialog dialog;
//	private ApiAsyncTask modifyTask;
//	private ApiAsyncTask getAddTask;
//	private ApiAsyncTask userModifyTask;
//	private Seference seference;
//	private String yuanpassword,newPassWord,twopassword;
//	private String uid = "";
//	private String appKey = "";
//	private int appId;
//	private ApiAsyncTask task;
//	private ApiAsyncTask updatetask;
//	private PromptDialog promptDialog;
//	private UserModifyDialog userModifyDialog;
//	private String userName ,password,newuserName ,newpassword;
//	private UserResultDialog userResultDialog;
//	private Boolean pingbingbt = true;
//	private GiftListDialog pdialog;
//	private Boolean vipbt=true;
//	private UpdataDialog data;
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//        iview=inflater.inflate(AppConfig.resourceId(getActivity(),"sjuser_info_fragment", "layout"), container,false);
//        initView();
//        initData();
//        getMyAdd("0");
//        getMyAdd("1");
//        if(	AppConfig.loginMap.get("uid")==null){
//        	//Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
//        	userWithoutUid();
//        }
//		return iview;
//	}
//
//	private void initView() {
//		user_info=(RelativeLayout) iview.findViewById(AppConfig.resourceId(getActivity(),"user_info", "id"));
//		rl_poss=(RelativeLayout) iview.findViewById(AppConfig.resourceId(getActivity(),"rl_pass", "id"));
//		rl_zenbi=(RelativeLayout) iview.findViewById(AppConfig.resourceId(getActivity(),"rl_zenbi", "id"));
//		user_vip=(RelativeLayout) iview.findViewById(AppConfig.resourceId(getActivity(),"user_vip", "id"));
//		ll_password=(LinearLayout) iview.findViewById(AppConfig.resourceId(getActivity(),"ll_password", "id"));
//		ll_youke=(LinearLayout) iview.findViewById(AppConfig.resourceId(getActivity(),"ll_youke", "id"));
//		user_pingtai=(RelativeLayout) iview.findViewById(AppConfig.resourceId(getActivity(),"user_pingtai", "id"));
//		button_b=(Button) iview.findViewById(AppConfig.resourceId(getActivity(),"button_b", "id"));
//		button_t=(Button) iview.findViewById(AppConfig.resourceId(getActivity(),"button_t", "id"));
//		et_yuanpassword=(EditText) iview.findViewById(AppConfig.resourceId(getActivity(),"et_yuanpassword", "id"));
//		et_newpassword=(EditText) iview.findViewById(AppConfig.resourceId(getActivity(),"et_newpassword", "id"));
//		et_twopassword=(EditText) iview.findViewById(AppConfig.resourceId(getActivity(),"et_twopassword", "id"));
//		tv_zhanghao=(TextView) iview.findViewById(AppConfig.resourceId(getActivity(),"tv_zhanghao", "id"));
//		tv_zhanghaop=(TextView) iview.findViewById(AppConfig.resourceId(getActivity(),"tv_zhanghaop", "id"));
//		tv_zenbi=(TextView) iview.findViewById(AppConfig.resourceId(getActivity(),"tv_zenbi", "id"));
//		tv_zengbiyue = (TextView) iview.findViewById(AppConfig.resourceId(getActivity(),"zengbiyue", "id"));
//		tv_pingtaibiyue = (TextView) iview.findViewById(AppConfig.resourceId(getActivity(),"pingtaibiyue", "id"));
//		tv_pingtaibi_info_desc = (TextView) iview.findViewById(AppConfig.resourceId(getActivity(),"pingtaibi_info_desc", "id"));
//		amendIbut = (ImageButton)iview. findViewById(AppConfig.resourceId(getActivity(), "amend_usre", "id"));
//		tv_youkea= (TextView)iview. findViewById(AppConfig.resourceId(getActivity(), "tv_youkea","id"));
//		tv_youkeb= (TextView)iview. findViewById(AppConfig.resourceId(getActivity(), "tv_youkeb","id"));
//		tv_youke= (TextView)iview. findViewById(AppConfig.resourceId(getActivity(), "tv_youke","id"));
//		ll_youke= (LinearLayout)iview. findViewById(AppConfig.resourceId(getActivity(), "ll_youke","id"));
//		ll_user= (LinearLayout)iview. findViewById(AppConfig.resourceId(getActivity(), "ll_user","id"));
//		ll_user_a =(TextView)iview. findViewById(AppConfig.resourceId(getActivity(), "ll_user_a","id"));
//		user_number = (TextView)iview. findViewById(AppConfig.resourceId(getActivity(), "user_number","id"));
//		user_update = (RelativeLayout) iview.findViewById(AppConfig.resourceId(getActivity(), "user_update", "id"));
//		user_update.setOnClickListener(this);
//		user_number.setOnClickListener(this);
//		tv_pingtaibi_info_desc.setOnClickListener(this);
//		user_info.setOnClickListener(this);
//		rl_poss.setOnClickListener(this);
//		user_pingtai.setOnClickListener(this);
//		button_b.setOnClickListener(this);
//		button_t.setOnClickListener(this);
//		amendIbut.setOnClickListener(this);
//        user_vip.setOnClickListener(this);
//		userName = AppConfig.loginMap.get("user");
//		password = AppConfig.loginMap.get("pwd");
//		tv_youke.setText("账号："+userName);
//		ll_user_a.setText("账号："+userName);
//		if (AppConfig.userType==1) {
//			tv_youkea.setVisibility(View.VISIBLE);
//			tv_youkeb.setVisibility(View.VISIBLE);
//			amendIbut.setVisibility(View.VISIBLE);
//		}else {
//			tv_youkea.setVisibility(View.GONE);
//			tv_youkeb.setVisibility(View.GONE);
//			amendIbut.setVisibility(View.GONE);
//			ll_youke.setVisibility(View.GONE);
//			ll_user.setVisibility(View.VISIBLE);
//			ll_user_a.setBackgroundDrawable(getResources().getDrawable(AppConfig.resourceId(getActivity(),"sjgift_list_head_style", "drawable")));
//			ll_user.setBackgroundColor(getResources().getColor(AppConfig.resourceId(getActivity(),"sjwhite", "color")));
//		    ll_user_a.setTextColor(getResources().getColor(AppConfig.resourceId(getActivity(), "sjwhite", "color")));
//		}
//	}
//
//	private void initData() {
//		seference = new Seference(getActivity());
//		appId = AppConfig.appId;
//		appKey = AppConfig.appKey;
//		uid = AppConfig.uid;
//		if (!uid.equals("")) {
//			tv_zhanghao.setText("账号："+AppConfig.userName);
//			tv_zhanghaop.setText("账号："+AppConfig.userName);
//		} else {
//			tv_zhanghao.setText("账号："+seference.getPreferenceData(
//					Seference.ACCOUNT_FILE_NAME, Seference.ACCOUNT_1));
//			tv_zhanghaop.setText("账号："+seference.getPreferenceData(
//					Seference.ACCOUNT_FILE_NAME, Seference.ACCOUNT_1));
//		}
////		tv_zhanghao.setText("账号："+uid);
//	}
//
//	@Override
//	public void onClick(View v) {
//		if (v.getId() == AppConfig.resourceId(getActivity(),"pingtaibi_info_desc", "id")) {
//			/*user_info.setVisibility(View.GONE);
//			rl_poss.setVisibility(View.GONE);
//			user_pingtai.setVisibility(View.GONE);
//			rl_zenbi.setVisibility(View.VISIBLE);
//			getMyAdd("0");*/
//			if(pingbingbt){
//			getPlatformDesc();
//			pingbingbt=false;
//			}
//		}else if (v.getId() == AppConfig.resourceId(getActivity(),"rl_pass", "id")) {
//			user_info.setVisibility(View.GONE);
//			rl_poss.setVisibility(View.GONE);
//			user_pingtai.setVisibility(View.GONE);
//			user_update.setVisibility(View.GONE);
//			user_vip.setVisibility(View.GONE);
//			ll_youke.setVisibility(View.GONE);
//			ll_password.setVisibility(View.VISIBLE);
//			ll_user.setVisibility(View.GONE);
//			tv_zhanghaop.setText("账号为："+AppConfig.userName);
//		}else if (v.getId() == AppConfig.resourceId(getActivity(),"amend_usre", "id")) {
//		/*	user_info.setVisibility(View.GONE);
//			rl_poss.setVisibility(View.GONE);
//			user_pingtai.setVisibility(View.GONE);
//			rl_zenbi.setVisibility(View.VISIBLE);
//			getMyAdd("1");*/
//			userModify();
//		}else if(v.getId() == AppConfig.resourceId(getActivity(),"user_number", "id"))
//		{
//		Message mssg=new Message();
//		mssg.obj="切换账号";
//		mssg.what=3;
//		Sjyx.handler.sendMessage(mssg);
//		getActivity().finish();
//		}
//		else if (v.getId() == AppConfig.resourceId(getActivity(),"button_b", "id")) {
//			user_info.setVisibility(View.VISIBLE);
//			rl_poss.setVisibility(View.VISIBLE);
//			user_vip.setVisibility(View.VISIBLE);
//			user_pingtai.setVisibility(View.VISIBLE);
//			user_update .setVisibility(View.VISIBLE);
//			ll_password.setVisibility(View.GONE);
//			if (AppConfig.userType==1) {
//			ll_youke.setVisibility(View.VISIBLE);}
//			else{
//			ll_user.setVisibility(View.VISIBLE);}
//		}else if (v.getId() == AppConfig.resourceId(getActivity(),"user_vip", "id")) {
//			//Toast.makeText(getActivity(), "此功能暂时未开通", Toast.LENGTH_SHORT).show();
//			if(vipbt){
//				Intent inteen = new Intent(getActivity(),Vipactivity.class);
//				startActivity(inteen);
//				vipbt = false;
//				}
//		}else if(v.getId()==AppConfig.resourceId(getActivity(), "user_update", "id")){
//			//检查更新
//			showDialog() ;
//			userUpdate();
//
//		}
//		else if (v.getId() == AppConfig.resourceId(getActivity(),"button_t", "id")) {
//			if (isboolean()) {
//				showDialog() ;
//				modify();
//				/*dialog = new TipsDialog(getActivity(), AppConfig.resourceId(getActivity(),
//						"Sj_MyDialog", "style"), new DialogListener() {
//					@Override
//					public void onClick() {
//						if (modifyTask != null) {
//							modifyTask.cancel(true);
//						}
//					}
//				});
//				dialog.show();
//				dialog.setCancelable(true);*/
//			//	showDialog() ;
//			}
//
//		}
//	}
//	/**
//	 * 密码判断
//	 * @return
//	 */
//	private boolean isboolean() {
//		yuanpassword = et_yuanpassword.getText().toString();
//		newPassWord = et_newpassword.getText().toString();
//		twopassword = et_twopassword.getText().toString();
//		if (yuanpassword==null||yuanpassword.equals("")) {
//			Toast.makeText(getActivity(), "请输入原密码", Toast.LENGTH_SHORT).show();
//			return false;
//		}
//		if (!yuanpassword.equals(AppConfig.loginMap.get("pwd"))) {
//			Toast.makeText(getActivity(), "原密码不正确", Toast.LENGTH_SHORT).show();
//			return false;
//		}
//		if (newPassWord==null||newPassWord.equals("")) {
//			Toast.makeText(getActivity(), "请输入新密码", Toast.LENGTH_SHORT).show();
//			return false;
//		}
//		if (!newPassWord.equals(twopassword)) {
//			Toast.makeText(getActivity(), "再输入密码不正确", Toast.LENGTH_SHORT).show();
//			et_twopassword.setText("");
//			return false;
//		}
//		return true;
//
//	}
///**
// * 查询平台币赠币余额
// * @param type 0赠币 1平台币
// */
//	private void getMyAdd(final String type) {
//		 getAddTask=SiJiuSDK.get().getMyAdd(getActivity(), appId, appKey,
//					uid,type, new ApiRequestListener() {
//
//						@Override
//						public void onSuccess(Object obj) {
//							// TODO Auto-generated method stub
//							if (obj != null) {
//								AliPayMessage result = (AliPayMessage) obj;
//								if (result.getResult()) {
//									if(type.equals("1")){
//										sendData(AppConfig.FLAG_GETPINGTAIBI_SUCCESS, result.getAddMoney(), myHandler);
//									}else {
//										sendData(AppConfig.FLAG_GETADD_SUCCESS, result.getAddMoney(), myHandler);
//									}
//
//								} else {
//										sendData(AppConfig.FLAG_RECHARGE_FAILED,
//												result.getMessage(), myHandler);
//								}
//							} else {
//								sendData(AppConfig.FLAG_FAIL, "网络连接失败，请检查您的网络连接", myHandler);
//							}
//						}
//
//						@Override
//						public void onError(int statusCode) {
//							// TODO Auto-generated method stub
//							sendData(AppConfig.FLAG_FAIL, "网络连接失败，请检查您的网络连接", myHandler);
//						}
//					});
//	}
//	/**
//	 * 修改密码
//	 */
//	private void modify() {
//		// TODO Auto-generated method stub
//		modifyTask = SiJiuSDK.get().startChangePassword(getActivity(), appId, appKey,
//				AppConfig.loginMap.get("uid"), AppConfig.loginMap.get("pwd"),
//				newPassWord, new ApiRequestListener() {
//
//					@Override
//					public void onSuccess(Object obj) {
//						dialog.dismiss();
//						ResultAndMessage msg = (ResultAndMessage) obj;
//						boolean result = msg.getResult();
//						String message = msg.getMessage();
//						if (result) {
//							// 准备保存账号和修改的密码和uid
//							seference.saveAccount(
//									AppConfig.loginMap.get("user"),
//									newPassWord, AppConfig.loginMap.get("uid"));
//							// creatFile.saveUserInfo(AppConfig.loginMap.get("user"),
//							// newPassWord, AppConfig.loginMap.get("uid"));
//							// 顺便替换临时的loginMap
//							AppConfig.loginMap.put("pwd", newPassWord);
//							sendData(AppConfig.FLAG_SUCCESS, message, myHandler);
//							// //修改的密码保存到临时的tempMap
//							// AppConfig.tempMap.put("user",
//							// AppConfig.loginMap.get("user"));
//							// AppConfig.tempMap.put("password", newPassWord);
//						} else {
//							sendData(AppConfig.FLAG_FAIL, message, myHandler);
//						}
//
//					}
//
//					@Override
//					public void onError(int statusCode) {
//						dialog.dismiss();
//						sendData(AppConfig.FLAG_REQUEST_ERROR, "网络连接失败，请检查您的网络连接",
//								myHandler);
//					}
//				});
//	}
//
//	/**
//	 * 接口返回数据处理
//	 */
//	public void sendData(int num, Object data, Handler callback) {
//		Message msg = callback.obtainMessage();
//		msg.what = num;
//		msg.obj = data;
//		msg.sendToTarget();
//	}
//
//	private Handler myHandler = new Handler() {
//
//		@Override
//		public void handleMessage(Message msg) {
//			String result;
//			try{
//			switch (msg.what) {
//
//			case AppConfig.FLAG_SUCCESS:
//				result = (String) msg.obj;
//				Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
//				et_yuanpassword.setText("");
//				et_newpassword.setText("");
//				et_twopassword.setText("");
//				user_info.setVisibility(View.VISIBLE);
//				rl_poss.setVisibility(View.VISIBLE);
//				user_vip.setVisibility(View.VISIBLE);
//				user_pingtai.setVisibility(View.VISIBLE);
//				user_update.setVisibility(View.VISIBLE);
//				ll_password.setVisibility(View.GONE);
//				if (AppConfig.userType==1) {
//					ll_youke.setVisibility(View.VISIBLE);}
//					else{
//					ll_user.setVisibility(View.VISIBLE);}
//				break;
//			case AppConfig.FLAG_FAIL:
//			   result = (String) msg.obj;
//				Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
//				break;
//			case AppConfig.FLAG_REQUEST_ERROR:
//				 result = (String) msg.obj;
//				Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
//				break;
//			case AppConfig.FLAG_GETADD_SUCCESS:
//				 result = (String) msg.obj;
//				tv_zengbiyue.setText("您的赠宝余额："+result);
//				break;
//			case AppConfig.FLAG_GETPINGTAIBI_SUCCESS:
//				 result = (String) msg.obj;
//				tv_pingtaibiyue.setText("您的平台币余额："+result);
//				break;
//			case AppConfig.FLAG_PLATFORMDESC_SUCCESS:
//				ResultAndMessage rm=(ResultAndMessage) msg.obj;
//				String text =rm.getData();
//				showPrompt(text);
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
//			case AppConfig.UPDATE_SUCCESS:
//				UpdateApp msgupdate = (UpdateApp) msg.obj;
//				if(msgupdate.getResult()){
//					//Toast.makeText(getActivity(), msgupdate.getMessage(), Toast.LENGTH_SHORT).show();
//					SelectUpdata( msgupdate.getUpdatetip(),msgupdate.getUrl());
//				}else{
//					Toast.makeText(getActivity(), msgupdate.getMessage(), Toast.LENGTH_SHORT).show();
//				}
//			}
//		}catch (Exception e) {
//			// TODO: handle exception
//		}}
//	};
//
//	/**
//	 * 获取平台币说明
//	 * @param task
//	 */
//
//	public void getPlatformDesc() {
//
//		task = SiJiuSDK.get().startPlatformDesc(getActivity(), appId, appKey,
//				new ApiRequestListener() {
//
//					@Override
//					public void onSuccess(Object obj) {
//						// TODO Auto-generated method stub
//						if (obj != null) {
//							sendData(AppConfig.FLAG_PLATFORMDESC_SUCCESS, obj, myHandler);
//						} else {
//							sendData(AppConfig.FLAG_FAIL, "网络连接失败，请检查您的网络连接", myHandler);
//						}
//					}
//
//					@Override
//					public void onError(int statusCode) {
//						// TODO Auto-generated method stub
//						sendData(AppConfig.FLAG_FAIL, "网络连接失败，请检查您的网络连接",myHandler);
//					}
//
//				});
//
//	}
//	/**
//	 * 平台币说明框
//	 * @param text
//	 */
//		private void showPrompt(String text) {
//			promptDialog = new PromptDialog(getActivity(), AppConfig.resourceId(getActivity(),
//					"Sj_MyDialog", "style"), text,
//					new PromptListener() {
//
//						@Override
//						public void onClick(View v) {
//
//							if (v.getId() == AppConfig.resourceId(
//									getActivity(), "resultbutton", "id")) {
//								pingbingbt= true;
//								promptDialog.dismiss();
//
//							} else if (v.getId() == AppConfig
//									.resourceId(getActivity(),
//											"resultbutton_e", "id")) {
//								pingbingbt= true;
//								promptDialog.dismiss();
//
//							}
//						}
//					});
//
//			promptDialog.setCancelable(false);
//			promptDialog.show();
//		}
//		/*
//		 * 弹出修改游客账号密码窗
//		 */
//		private void userModify() {
//			userModifyDialog=new UserModifyDialog(getActivity(), AppConfig.resourceId(
//					getActivity(), "Sj_MyDialog", "style"), userName, password, new ResultListener() {
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					if(v.getId()==AppConfig.resourceId(getActivity(), "resultbutton_e", "id")){
//						String u=userModifyDialog.getusername();
//						String p=userModifyDialog.getpassword();
//						String pt=userModifyDialog.getpasswordto();
//						if (p.equals(pt)) {
//							userModifyHttp(u, p, p);
//						}else {
//							Toast.makeText(getActivity(), "两次密码不一致", Toast.LENGTH_SHORT).show();
//						}
//
//					}else if(v.getId()==AppConfig.resourceId(getActivity(), "resultbutton", "id")){
//						userModifyDialog.dismiss();
//					}
//
//				}
//			});
//			userModifyDialog.setCancelable(false);
//			userModifyDialog.show();
//
//		}
//		/**
//		 * 修改账号及密码
//		 * @param username账号
//		 * @param password密码
//		 * @param newPassword重复密码
//		 */
//			private void userModifyHttp(String username,String password,String newPassword) {
//				userModifyTask = SiJiuSDK.get().startuserModify(getActivity(), AppConfig.appId, username, password, newPassword,
//						AppConfig.appKey,AppConfig.uid, new ApiRequestListener() {
//
//							@Override
//							public void onSuccess(Object obj) {
//								if (obj != null) {
//
//									ResultAndMessage result = (ResultAndMessage) obj;
//									if (result.getResult()) {
//										sendData(AppConfig.FLAG_USERMODIFY_SUCCESS, obj, myHandler);
//									}else {
//										sendData(AppConfig.FLAG_FAIL, result.getMessage(), myHandler);
//									}
//								} else {
//									sendData(AppConfig.FLAG_FAIL, "获取数据失败!", myHandler);
//								}
//							}
//
//							@Override
//							public void onError(int statusCode) {
//								sendData(AppConfig.FLAG_REQUEST_ERROR, "链接出错，请重试!",
//										myHandler);
//							}
//						});
//			}
//			/**
//			 * 弹出修改结果窗
//			 */
//			private void userModifyResult() {
//				userResultDialog=new UserResultDialog(getActivity(), AppConfig.resourceId(
//						getActivity(), "Sj_MyDialog", "style"), userName, password, new UserResultListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						if(v.getId()==AppConfig.resourceId(getActivity(), "bt_user", "id")){
//							tv_youkea.setVisibility(View.GONE);
//							tv_youkeb.setVisibility(View.GONE);
//							amendIbut.setVisibility(View.GONE);
//							tv_youke.setText("账号为："+userName);
//							ll_user_a.setText("账号为："+userName);
//							if (AppConfig.userType==1) {
//								ll_youke.setVisibility(View.VISIBLE);}
//								else{
//								ll_user.setVisibility(View.VISIBLE);
//								ll_youke.setVisibility(View.GONE);
//								ll_user_a.setBackgroundDrawable(getResources().getDrawable(AppConfig.resourceId(getActivity(),"sjgift_list_head_style", "drawable")));
//								ll_user.setBackgroundColor(getResources().getColor(AppConfig.resourceId(getActivity(),"sjwhite", "color")));
//								 ll_user_a.setTextColor(getResources().getColor(AppConfig.resourceId(getActivity(), "sjwhite", "color")));
//								}
//							userResultDialog.dismiss();
//						}
//
//					}
//				});
//				userResultDialog.setCancelable(false);
//				userResultDialog.show();
//			}
//
//			/**
//			 * 用户没有登录
//			 */
//			public void userWithoutUid() {
//				String tag = "uid is empty";
//				String info = "您的账号未登录，请先登录！";
//				pdialog = new GiftListDialog(getActivity(), AppConfig.resourceId(
//						getActivity(), "Sj_MyDialog", "style"), AppConfig.resourceId(
//
//						getActivity(), "sjgift_dialog", "layout"), info, tag,
//						new Phonelistener() {
//
//							@Override
//							public void onClick(View view, String text, String from) {
//								// TODO Auto-generated method stub
//								if (view.getId() == AppConfig.resourceId(getActivity(),
//										"gift_dialog_close", "id")) {
//									pdialog.dismiss();
//									getActivity().finish();
//									Sjyx.isShow = true;
//									Sjyx.icon.setVisibility(View.VISIBLE);
//								}
//							}
//						});
//				pdialog.show();
//			}
//			@Override
//			public void onResume() {
//				// TODO Auto-generated method stub
//				super.onResume();
//				vipbt = true;
//			}
//		/**
//		 * 检查更新
//		 *
//		 */
//			private void userUpdate(){
//				Properties properties = new Properties();
//				String local_ver="";
//				try {
//					properties.load(getActivity().getAssets().open("sijiu.properties"));
//					local_ver = properties.getProperty("version");
//				updatetask = SiJiuSDK.get().startUpdate(getActivity(), AppConfig.appId, AppConfig.appKey,AppConfig.ver_id,AppConfig.uid,local_ver,new ApiRequestListener() {
//
//					@Override
//					public void onSuccess(Object obj) {
//						// TODO Auto-generated method stub
//						if (obj != null) {
//							dialog.dismiss();
//							UpdateApp result = (UpdateApp) obj;
//							if (result.getResult()) {
//								sendData(AppConfig.UPDATE_SUCCESS, obj, myHandler);
//							}else {
//								sendData(AppConfig.FLAG_FAIL, result.getMessage(), myHandler);
//							}
//						} else {
//							dialog.dismiss();
//							sendData(AppConfig.FLAG_FAIL, "获取数据失败!", myHandler);
//						}
//					}
//
//					@Override
//					public void onError(int statusCode) {
//						// TODO Auto-generated method stub
//						dialog.dismiss();
//						sendData(AppConfig.FLAG_REQUEST_ERROR, "链接出错，请重试!",
//								myHandler);
//					}
//				});}catch(Exception e){}
//			}
//			public void showDialog() {
//				dialog = new TipsDialog(getActivity(), AppConfig.resourceId(getActivity(), "Sj_MyDialog", "style") ,
//						new DialogListener() {
//
//							@Override
//							public void onClick() {
//								// TODO Auto-generated method stub
//
//							}
//						});
//				dialog.setCancelable(false);
//				dialog.show();
//			}
//			/**
//			 * 选择性更新 text 内容 app_url 下载地址
//			 */
//			public void SelectUpdata(String text, final String app_url) {
//				data = new UpdataDialog(getActivity(), AppConfig.resourceId(getActivity(),
//						"Sj_MyDialog", "style"), text, false, new UpdataListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						if (v.getId() == AppConfig.resourceId(getActivity(), "button_updata",
//								"id")) {
//							//Downloadwebview(app_url);
//							Intent intent = new Intent(Intent.ACTION_VIEW,
//									Uri.parse(app_url));
//							startActivity(intent);
//							data.dismiss();
//						} else if (v.getId() == AppConfig.resourceId(getActivity(),
//								"next_button_updata", "id")) {
//							data.dismiss();
//							// callBack("close2");
//							// PaymentActivity.this.finish();
//
//						}
//					}
//				});
//				data.setCancelable(false);
//				data.show();
//			}
//}
