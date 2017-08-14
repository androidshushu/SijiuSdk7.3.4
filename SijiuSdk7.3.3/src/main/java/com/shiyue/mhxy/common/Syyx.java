package com.shiyue.mhxy.common;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Timer;


import android.app.Activity;

import android.content.Context;

import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;

import android.util.Log;

import android.view.View;

import android.view.WindowManager;
import com.shiyue.mhxy.config.AppConfig;
import com.shiyue.mhxy.config.WebApi;
import com.shiyue.mhxy.http.ApiAsyncTask;
import com.shiyue.mhxy.pay.SjyxPaymentInfo;
import com.shiyue.mhxy.pay.Sy_PaymentActivity;
import com.shiyue.mhxy.sdk.InitData;
import com.shiyue.mhxy.user.LoginActivity;
import com.shiyue.mhxy.user.LoginInfo;
import com.shiyue.mhxy.user.SetExtData;
import com.shiyue.mhxy.user.UserInfo;
import com.shiyue.mhxy.utils.Seference;
import com.shiyue.mhxy.wight.Exitdialog;
import com.shiyue.mhxy.wight.Exitdialog.Exitdialoglistener;


import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import static android.content.Context.TELEPHONY_SERVICE;

public class Syyx {
	public static final int LOGIN_RESULT_CODE = 100;
	public static final int PAY_RESULT_CODE = 101;
	public static final int INIT_CODE = 102;
	public static final int ADDPAY = 103;
	public static final int PINGTAIBIPAY = 104;
//	public static MyTextView icon;
	public static boolean isShow = true;
	public static boolean iswelcom = true;
	public static Timer timer;
	public int initcount=1;
	private static Context initcontext;
	private static Activity logincontext;
	private static InitListener initListener;
	private static ApiListenerInfo mlistenerInfo;
	private  static LoginInfo mloginInfo;
	public boolean relogin=false;
	public static ApiListenerInfo apiListenerInfo;
	public static UserApiListenerInfo userlistenerinfo;
	private static ApiAsyncTask Datatask;
	private static ApiAsyncTask logoutTask;
	private static Exitdialog exitdialog;
	
	public static Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			try {
				switch (msg.what) {
					case 1:

						apiListenerInfo.onSuccess(msg.obj);
						break;
					case 2:
						try {
							apiListenerInfo.onSuccess(msg.obj);
						} catch (Exception e) {
							// TODO: handle exception
						}
						break;
					case 3:
						try {
							userlistenerinfo.onLogout(msg.obj);
						} catch (Exception e) {
							// TODO: handle exception
						}
						break;
					case 4:
						try {
             Syyx.login(logincontext,mloginInfo,apiListenerInfo);
						} catch (Exception e) {
							// TODO: handle exception
						}
						break;
//					case 5:
//                    new Thread(new Runnable() {
//						@Override
//						public void run() {
//
//							try {
//								logoutHttp();
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
//					}).start();


//						break;
				}



			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	};

	/**
	 * 初始化接口
	 * 
	 * @param appid
	 * @param appkey
	 * @param ver_id
	 *            渠道id
	 * @param 初始化浮点
	 */
	public static void initInterface(Context context, int appid, String appkey,
			String ver_id,boolean istest, InitListener listener) {
		try {
          AppConfig.isTest=istest;
			initcontext=context;
			initListener=listener;

			AppConfig.appId = appid;
			AppConfig.appKey = appkey;
			ver_id = "";// 渠道号写死，读配置文件
			if ("".equals(ver_id)) {
				// 如果传递过来的为空字符串，就需要自己读配置文件
				Properties properties = new Properties();
				properties.load(context.getAssets().open("sijiu.properties"));
				ver_id = properties.getProperty("agent");
				AppConfig.ver_id=ver_id;
				AppConfig.ad_id=properties.getProperty("ad_id");
				AppConfig.WXAPP_ID=properties.getProperty("wx_appid")+"";
				AppConfig.WXAPP_SECRET=properties.getProperty("wx_secret")+"";
				AppConfig.QQAPP_ID=properties.getProperty("qq_appid")+"";
				// flag = true;
			}
			AppConfig.imei =((TelephonyManager) context.getSystemService(TELEPHONY_SERVICE)).getDeviceId();
			new InitData(context, ver_id, listener);// point浮点的显示

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public static void sjInitInterface(Context context, int appid,
			String appkey, String ver_id, Boolean point, InitListener listener) {
		try {
//			boolean flag = true;
			AppConfig.appId = appid;
			AppConfig.appKey = appkey;
			if ("".equals(ver_id)) {
				// 如果传递过来的为空字符串，就需要自己读配置文件
				Properties properties = new Properties();
				properties.load(context.getAssets().open("sijiu.properties"));
				ver_id = properties.getProperty("agent");
				AppConfig.ver_id=ver_id;
				// flag = true;
			}
			new InitData(context, ver_id, listener);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

//	/**
//	 * 登录接口
//	 *
//	 * @param context
//	 *            上下文对象
//	 * @param loginInfo
//	 *            登录需要传递的参数
//	 */
//	public static void login(Activity activity, LoginInfo loginInfo) {
//		try {
//			// 判断游戏方传入的横竖屏参数
//			if (loginInfo.getOritation().equals("landscape")) {
//				AppConfig.ISPORTRAIT = false;
//			} else {
//				AppConfig.ISPORTRAIT = true;
//			}
//
//			// 判断游戏方是否传渠道参数
//			loginInfo.setAgent("");
//			if ("".equals(loginInfo.getAgent())) {
//				// 如果传递过来的为空字符串，就需要自己读配置文件
//				Properties properties = new Properties();
//				properties.load(activity.getAssets().open("sijiu.properties"));
//				loginInfo.setAgent(properties.getProperty("agent"));
//
//			}
//			Intent intent = new Intent(activity, LoginActivity.class);
//			Bundle bundle = new Bundle();
//			bundle.putParcelable("sj_login_info", loginInfo);
//			intent.putExtras(bundle);
//			activity.startActivityForResult(intent, LOGIN_RESULT_CODE);
//			// activity.startActivity(intent);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}

	/**
	 * 登录接口
	 * 
	 * @param context
	 *            上下文对象
	 * @param loginInfo
	 *            登录需要传递的参数
	 */
	public static void login(Activity context, LoginInfo loginInfo,
			ApiListenerInfo listener) {

		logincontext=context;
		mloginInfo=loginInfo;
		apiListenerInfo=listener;

		if(AppConfig.initResult) {
			try {
				// 判断游戏方传入的横竖屏参数
				if (loginInfo.getOritation().equals("landscape")) {
					AppConfig.ISPORTRAIT = false;
				} else {
					AppConfig.ISPORTRAIT = true;
				}
				// 判断游戏方是否传渠道参数
				loginInfo.setAgent("");
				if ("".equals(loginInfo.getAgent())) {
					// 如果传递过来的为空字符串，就需要自己读配置文件
					Properties properties = new Properties();
					properties.load(context.getAssets().open("sijiu.properties"));
					loginInfo.setAgent(properties.getProperty("agent"));
				}
				Intent intent = new Intent(context, LoginActivity.class);
				Bundle bundle = new Bundle();
				bundle.putParcelable("sj_login_info", loginInfo);
				apiListenerInfo = listener;
				// bundle.putSerializable("sj_listener", listener);
				intent.putExtras(bundle);
				// context.startActivity(intent);
				context.startActivityForResult(intent, LOGIN_RESULT_CODE);
				// activity.startActivity(intent);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else{

			new InitData(initcontext, AppConfig.ver_id, "reinit",initListener);

		}

	}

	public static void sjLogin(Activity context, LoginInfo loginInfo,
			ApiListenerInfo listener) {
		try {
			// 判断游戏方传入的横竖屏参数
			if (loginInfo.getOritation().equals("landscape")) {
				AppConfig.ISPORTRAIT = false;
			} else {
				AppConfig.ISPORTRAIT = true;
			}
			// 判断游戏方是否传渠道参数
			loginInfo.setAgent("");
			if ("".equals(loginInfo.getAgent())) {
				// 如果传递过来的为空字符串，就需要自己读配置文件
				Properties properties = new Properties();
				properties.load(context.getAssets().open("sijiu.properties"));
				loginInfo.setAgent(properties.getProperty("agent"));
			}
			Intent intent = new Intent(context, LoginActivity.class);
			Bundle bundle = new Bundle();
			bundle.putParcelable("sj_login_info", loginInfo);
			apiListenerInfo = listener;
			// / bundle.putSerializable("sj_listener", listener);
			intent.putExtras(bundle);
			// context.startActivity(intent);
			context.startActivityForResult(intent, LOGIN_RESULT_CODE);
			// activity.startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

//	/**
//	 * 充值接口
//	 */
//	public static void payment(Activity activity, SjyxPaymentInfo payInfo) {
//		try {
//			// 判断游戏厂家点击登录按钮没有
//			// if (AppConfig.loginMap.size() == 0) {
//			// Toast.makeText(activity, "请登录!", Toast.LENGTH_SHORT).show();
//			// return;
//			// }
//			// 判断游戏方是否传渠道参数
//			payInfo.setAgent("");
//			if ("".equals(payInfo.getAgent())) {
//				// 如果传递过来的为空字符串，就需要自己读配置文件
//				Properties properties = new Properties();
//				properties.load(activity.getAssets().open("sijiu.properties"));
//				payInfo.setAgent(properties.getProperty("agent"));
//			}
//			Intent intent = new Intent(activity, Sy_PaymentActivity.class);
//			Bundle bundle = new Bundle();
//			bundle.putParcelable("sj_pay_info", payInfo);
//			intent.putExtras(bundle);
//			Syyx.isShow = false;
//			if (Syyx.icon != null) {
//				// Syyx.icon.setVisibility(View.GONE);//导致有些游戏要点击两次才调出充值界面
//			}
//			activity.startActivityForResult(intent, PAY_RESULT_CODE);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}

	/**
	 * 充值接口
	 */
	public static void payment(Activity activity, SjyxPaymentInfo payInfo,
			ApiListenerInfo listener) {
		try {
			AppConfig.appId = payInfo.getAppId();
			AppConfig.appKey = payInfo.getAppKey();

			// 判断游戏方是否传渠道参数
			payInfo.setAgent("");
			if ("".equals(payInfo.getAgent())) {
				// 如果传递过来的为空字符串，就需要自己读配置文件
				Properties properties = new Properties();
				properties.load(activity.getAssets().open("sijiu.properties"));
				payInfo.setAgent(properties.getProperty("agent"));
			}
			Intent intent = new Intent(activity, Sy_PaymentActivity.class);
			Bundle bundle = new Bundle();
			bundle.putParcelable("sj_pay_info", payInfo);
			intent.putExtras(bundle);
			Syyx.isShow = false;
//			if (Syyx.icon != null) {
//				// Syyx.icon.setVisibility(View.GONE);
//			}
			apiListenerInfo = listener;
			activity.startActivityForResult(intent, PAY_RESULT_CODE);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/**
	 * 充值接口
	 */
	public static void sjPayment(Activity activity, SjyxPaymentInfo payInfo,
			ApiListenerInfo listener) {
		try {
			// 判断游戏厂家点击登录按钮没有
			// if (AppConfig.loginMap.size() == 0) {
			// Toast.makeText(activity, "请登录!", Toast.LENGTH_SHORT).show();
			// return;
			// }
			// 判断游戏方是否传渠道参数
			payInfo.setAgent("");
			if ("".equals(payInfo.getAgent())) {
				// 如果传递过来的为空字符串，就需要自己读配置文件
				Properties properties = new Properties();
				properties.load(activity.getAssets().open("sijiu.properties"));
				payInfo.setAgent(properties.getProperty("agent"));
			}
			Intent intent = new Intent(activity, Sy_PaymentActivity.class);
			Bundle bundle = new Bundle();
			bundle.putParcelable("sj_pay_info", payInfo);
			intent.putExtras(bundle);
			Syyx.isShow = false;
//			if (Syyx.icon != null) {
//				// Syyx.icon.setVisibility(View.GONE);
//			}
			apiListenerInfo = listener;
			activity.startActivityForResult(intent, PAY_RESULT_CODE);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/**
	 * assitive浮点
	 */
//	public static void assitiveTouch(Context context) {
//
//		final WindowManager wm = (WindowManager) context
//				.getApplicationContext().getSystemService(
//						context.getApplicationContext().WINDOW_SERVICE);
//		if (icon == null) {
//			// 适配不同屏幕的大小
//			Display display = wm.getDefaultDisplay();
//			DisplayMetrics metrics = new DisplayMetrics();
//			wm.getDefaultDisplay().getMetrics(metrics);
//			float dpi = metrics.density;
//			int state = 0; // 状态值，传入到类中
//
//			Rect frame = new Rect();
//			MyTextView.TOOL_BAR_HIGH = frame.top;
//			final WindowManager.LayoutParams params = MyTextView.params;
//			params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
//					| WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
//			params.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
//					| LayoutParams.FLAG_NOT_FOCUSABLE;
//			// 平板
//			if (dpi > 0 && dpi <= 1) {
//				params.width = 60;
//				params.height = 60;
//				state = 45;
//			}
//			if (dpi > 1 && dpi <= 1.5) {
//				params.width = 80;
//				params.height = 80;
//				state = 60;
//			}
//			// 三星n7000 红米 小米2
//			if (dpi > 1.5 && dpi <= 2) {
//				params.width = 100;
//				params.height = 100;
//				state = 80;
//			}
//
//			if (dpi > 2 && dpi <= 2.5) {
//				params.width = 110;
//				params.height = 110;
//				state = 110;
//			}
//			// 华为H60-L01
//			if (dpi > 2.5) {
//				params.width = 145;
//				params.height = 145;
//				state = 110;
//			}
//			// params.dimAmount = 0;
//			params.format = PixelFormat.RGBA_8888;
//			// params.alpha = 100;
//			params.gravity = Gravity.LEFT | Gravity.TOP;
//			// 以屏幕左上角为原点，设置x、y初始值
//			params.x = 0;
//			params.y = display.getHeight() / 2;
//			// Log.i("kk", state+"state");
//			icon = new MyTextView(context, state, wm);
//			wm.addView(icon, params);
//			final Handler handler = new Handler() {
//				public void handleMessage(Message msg) {
//					switch (msg.what) {
//					case 1:
//						if (icon != null) {
//							icon.upPothot();
//							wm.updateViewLayout(icon, params);
//						}
//					}
//					super.handleMessage(msg);
//				}
//			};
//
//			TimerTask task = new TimerTask() {
//				public void run() {
//					Message message = new Message();
//					message.what = 1;
//					handler.sendMessage(message);
//				}
//			};
//
//			timer = new Timer(true);
//			timer.schedule(task, 3000, 3000);
//		}
//	}

	/**
	 * 退出接口，记录一些参数
	 */
	public static void exit(final Activity context,
			final ExitListener exitlistener) {

		Log.i("kk", "---exit--");

		exitdialog = new Exitdialog(context, AppConfig.resourceId(context,
				"Sj_Transparent", "style"), new Exitdialoglistener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (v.getId() == AppConfig.resourceId(context, "dialog_exit",
						"id")) {

					saveUserToSd(context);
					WindowManager wm = (WindowManager) context
							.getApplicationContext()
							.getSystemService(
									context.getApplicationContext().WINDOW_SERVICE);
					//注销
					logout(exitlistener);
					exitlistener.ExitSuccess("exit");

				} else if (v.getId() == AppConfig.resourceId(context,
						"dialog_cancel", "id")) {

					exitlistener.fail("fail");
					exitdialog.dismiss();
				}
			}
		});

		// exitdialog.setCancelable(false);
		exitdialog.show();
//		Looper.loop();
	}

	public static void logout(final ExitListener exitlistener){
		new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... params) {

				String url = params[0];

				return getHttpClient(url);
			}
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if(AppConfig.isTest){
					Log.d("shiyue", "====== exit =====");
				}

				exitlistener.ExitSuccess("exit");
				//TODO 在这里的result 就是 loadJson(url)返回的结果
				//	String loginResult = loadJson(notifyURL);

				
			}

		}.execute(WebApi.ACTION_LOGOUT);

	}
	public static String getHttpClient(String httpUrl) {
		try {

			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(httpUrl);
			HttpResponse response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			//HttpURLConnection.HTTP_OK即200响应码
			if (statusCode == HttpURLConnection.HTTP_OK) {
				InputStream ins = response.getEntity().getContent();
				//将读取到的信息转换成utf-8编码
				BufferedReader br = new BufferedReader(new InputStreamReader(
						ins, "utf-8"));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

				return sb.toString();
			}
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return null;
	}
//		public static void logoutHttp() throws Exception {
//
//			// 新建一个URL对象
//			URL url = new URL(WebApi.ACTION_LOGOUT);
//			// 打开一个HttpURLConnection连接
//			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
//			// 设置连接超时时间
//			urlConn.setConnectTimeout(5 * 1000);
//			// 开始连接
//			urlConn.connect();
//			// 判断请求是否成功
//			if (urlConn.getResponseCode() == 200) {
//				// 获取返回的数据
////				byte[] data = readStream(urlConn.getInputStream());
//				Log.i("shiyue", "Get方式请求成功，返回数据如下：");
////				Log.i(TAG_GET, new String(data, "UTF-8"));
//			} else {
//				Log.i("shiyue", "Get方式请求失败");
//			}
//			// 关闭连接
//			urlConn.disconnect();
//
//	}

	/**
	 * 保存账号到sdcard
	 */
	public static void saveUserToSd(Context context) {
		List<HashMap<String, String>> contentList = new ArrayList<HashMap<String, String>>();
		Seference seference = new Seference(context);
		UserInfo creatFile = new UserInfo();
		contentList = seference.getContentList();
		String data = "";
		if (contentList == null) {
			return;
		} else {
			for (int i = 0; i < contentList.size(); i++) {
				String tempUser = contentList.get(i).get("account");
				String tempPwd = contentList.get(i).get("password");
				String tempUid = contentList.get(i).get("uid");
				data += tempUser + ":" + tempPwd + ":" + tempUid + "#";
			}
			creatFile.saveUserInfo("", "", "", data);
		}
	}


	/*
	 * 切换账号
	 */
	public static void setUserListener(UserApiListenerInfo listener) {
		userlistenerinfo = listener;
	}

	public static void onCreate(Activity activity) {
		CrashHandle handle = CrashHandle.getInstance();
		handle.init(activity.getApplicationContext());
		handle.sendPreviousReportsToServer();
	}

	public static void onstop(Activity activity) {

	}

	public static void onDestroy(Activity activity) {

	}

	public static void onResume(Activity activity) {
//		if (Syyx.icon != null) {
//			Syyx.isShow = true;
//			Syyx.icon.setVisibility(View.VISIBLE);
//		}
	}

	public static void onPause(Activity activity) {
	}

	public static void onRestart(Activity activity) {
	}

	public static void onNewIntent(Intent intent) {
	}

	/**
	 * 资源释放
	 * 
	 * @param context
	 */
	public static void applicationDestroy(Context context) {

	}



	public static void setExtData(Context context,HashMap hs,int type) {
		try {
			// Log.i("kk","上下文"+context);
//			boolean flag = true;
			//用于初始化失败再初始化

			new SetExtData(context, hs, type);// point浮点的显示

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
//	public static void setExtData(Context context, String Agent,
//			String scene_Id, String roleId, String roleName, String roleLevel,
//			String zoneId, String zoneName, String balance, String Vip,
//			String partyName) { // ,String spirit ,String score,String
//								// sword,String spiritScore,String mout ,String
//								// diamondblue,String diamondred ){
//		Agent = "";// 渠道写死，读取配置文件
//		if ("".equals(Agent)) {
//			// 如果传递过来的为空字符串，就需要自己读配置文件
//			Properties properties = new Properties();
//			try {
//				properties.load(context.getAssets().open("sijiu.properties"));
//
//				Agent = (properties.getProperty("agent"));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		setExtdataTask(context, Agent, scene_Id, roleId, roleName, roleLevel,
//				zoneId, zoneName, balance, Vip, partyName);
//	}

	public static void applicationInit(Context context) {

	}

	public static void onActivityResult(Activity activity, int requestCode,
			int resultCode, Intent data) {

	}

	/**
	 * 闪屏
	 * 
	 * @param activity
	 */
	public static void startWelcomanie(Activity activity) {
//		if (iswelcom) {
//			/*
//			 * Intent intent = new Intent(activity, WecomeActivity.class);
//			 * activity.startActivity(intent);
//			 */
//			final ViewGroup rootView = (ViewGroup) activity.getWindow()
//					.getDecorView();
//			LayoutInflater lf = activity.getLayoutInflater();
//			final ViewGroup coverView = (ViewGroup) lf.inflate(
//					AppConfig.resourceId(activity, "sjwecome", "layout"), null);
//			rootView.addView(coverView);
//			coverView.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					// 屏蔽点击
//				}
//			});
//			AlphaAnimation anim = new AlphaAnimation(1.0f, 1.0f);
//			anim.setDuration(3000);
//			anim.setAnimationListener(new AnimationListener() {
//				@Override
//				public void onAnimationStart(Animation animation) {
//
//				}
//
//				@Override
//				public void onAnimationEnd(Animation animation) {
//					coverView.clearAnimation();
//					rootView.removeView(coverView);
//				}
//
//				@Override
//				public void onAnimationRepeat(Animation animation) {
//
//				}
//			});
//			coverView.startAnimation(anim);
//			iswelcom = false;
//		}
	}

	/*
	 * 提交额外信息
	 */
//	private static void setExtdataTask(Context context, String agent,
//			String scene_Id, String roleId, String roleName, String roleLevel,
//			String zoneId, String zoneName, String balance, String Vip,
////			String partyName) {
//		Datatask = SiJiuSDK.get().startExtdata(context, AppConfig.appId,
//				AppConfig.appKey, AppConfig.uid, agent, scene_Id, roleId,
//				roleName, roleLevel, zoneId, zoneName, balance, Vip, partyName,
//				new ApiRequestListener() {
//
//					@Override
//					public void onSuccess(Object obj) {
//						// TODO Auto-generated method stub
//						if (obj != null) {
//							// sendData(AppConfig.FLAG_VIPDATE_SUCCESS, obj,
//							// myHandler);
//							Vip result = new Vip();
//							result = (Vip) obj;
//							Log.i("kk", result.getMsg() + result.isResult());
//						} else {
//							// sendData(AppConfig.FLAG_FAIL, "网络连接失败，请检查您的网络连接",
//							// myHandler);
//						}
//					}
//
//					@Override
//					public void onError(int statusCode) {
//						// TODO Auto-generated method stub
//						// sendData(AppConfig.FLAG_FAIL, "网络连接失败，请检查您的网络连接",
//						// myHandler);
//					}
//
//				});
//
//	}

	public static String exit(Activity context) {
		return "success";
	}
}