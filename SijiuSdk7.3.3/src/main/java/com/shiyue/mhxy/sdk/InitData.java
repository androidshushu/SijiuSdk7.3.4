package com.shiyue.mhxy.sdk;

import java.util.Calendar;
import java.util.List;

import com.shiyue.mhxy.common.InitListener;
import com.shiyue.mhxy.common.Syyx;
import com.shiyue.mhxy.common.TipsDialog;
import com.shiyue.mhxy.common.TipsDialog.DialogListener;
import com.shiyue.mhxy.config.AppConfig;
import com.shiyue.mhxy.http.ApiAsyncTask;
import com.shiyue.mhxy.http.ApiRequestListener;

import com.shiyue.mhxy.user.InitMessage;
import com.shiyue.mhxy.user.UserInfo;
import com.shiyue.mhxy.utils.Seference;
import com.shiyue.mhxy.wight.Exitdialog;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class InitData {

	private InitListener listener;
	private Context context;
	private ApiAsyncTask iniTask; // 初始化
	private ApiAsyncTask updateTask; // 更新
	private String ver_id;
	private Seference seference;
	private int isAutoReg;
	private UserInfo userInfo;
	private String type = "";
	private final static int UPDATE_SUCCESS = 32;
	private final static int UPDATE_FAILED = 33;
	private final static int INIT = 34;
	private final static int UPDATE = 35;
	private final static int CACHE_APK = 36;
	private final static int WAIT = 37;
	private final static int INIT_SUCCESS = 38;
	private final static int FLAG_PUSH = 103;
	private TipsDialog dialog = null;
	private boolean isOpen = true; // 是否打开浮点
//	private boolean isUpdate = false; // 是否在线更新
	private boolean allSend = true; // 浮点是开启，但是服务端关闭所有的选项
	private boolean timeControl = false; // 时间控制
	public static boolean clientIsTimeDisplay = false; // 时间影响端的显示,默认不显示
	private Calendar calendar = Calendar.getInstance();

	private static Exitdialog exitdialog;
	public InitData(Context context, String ver_id,
					 InitListener listener) {
		this.context = context;
//		this.isUpdate = update;
		this.ver_id = ver_id;
		this.listener = listener;
//		this.isOpen = point;
		seference = new Seference(context);
		userInfo = new UserInfo();
		handler.sendEmptyMessage(INIT);
		showDialog();
	}
    public InitData(Context context, String ver_id,String type,
                    InitListener listener) {
        this.context = context;
//		this.isUpdate = update;
        this.ver_id = ver_id;
        this.type=type;
        this.listener = listener;
//		this.isOpen = point;
        seference = new Seference(context);
        userInfo = new UserInfo();
        handler.sendEmptyMessage(INIT);
        showDialog();
    }

	/**
	 * 弹出dialog
	 */
	public void showDialog() {
		Log.i("kk", "---exit--");


		// 弹出dialog
		dialog = new TipsDialog(context, AppConfig.resourceId(context,
				"Sj_MyDialog", "style"), new DialogListener() {

			@Override
			public void onClick() {

			}
		});
		dialog.setCancelable(false);
		dialog.show();
	}

	/**
	 * 取消dialog
	 */
	public void disDialog() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	public void Init() {
		initHttp();
	}



	/**
	 * http请求，初始化接口
	 */
	public void initHttp() {
		iniTask = SiJiuSDK.get().startInit(context, AppConfig.appId,
				AppConfig.appKey, ver_id, new ApiRequestListener() {
					@Override
					public void onSuccess(Object obj) {
						Log.d("kk",obj+"");
						if (obj != null) {
							InitMessage result = (InitMessage) obj;
							if (result.getResult()) {

								//保存通用的配置信息
//								AppConfig.syAppId=AppConfig.appId;
//								AppConfig.syAppKey=AppConfig.appKey;
//								AppConfig.syVer_id=ver_id;
								//根据初始化是否成功决定其他功能是否能使用
                                AppConfig.initResult=true;

								sendData(INIT_SUCCESS, obj, handler);

							} else {
								sendData(AppConfig.FLAG_FAIL,
										result.getMessage(), handler);
							}
						}
						else {
							AppConfig.initResult=false;
							sendData(AppConfig.FLAG_FAIL, "获取数据失败!", handler);
						}
					}

					@Override
					public void onError(int statusCode) {
						// TODO Auto-generated method stub
						sendData(AppConfig.FLAG_FAIL, "网络连接失败，请检查您的网络连接!",
								handler);
					}
				});
	}

	/**
	 * 更新接口
	 */
//	public void updateVersion() {
//		Properties properties = new Properties();
//		try {
//			properties.load(context.getAssets().open("sijiu.properties"));
//			local_ver = properties.getProperty("version");
//			String agent = properties.getProperty("agent");
//			if ("".equals(ver_id)) {
//				updateHttp(agent);
//			} else {
//				updateHttp(ver_id);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 更新http请求
//	 */
//	public void updateHttp(String ver) {
//		updateTask = SiJiuSDK.get().startUpdateApp(context, AppConfig.appId,
//				AppConfig.appKey, ver, new ApiRequestListener() {
//					@Override
//					public void onSuccess(Object obj) {
//						if (obj != null) {
//							UpdateApp result = (UpdateApp) obj;
//							if (result.getResult()) {
//								sendData(UPDATE_SUCCESS, obj, handler);
//							} else {
//								sendData(AppConfig.FLAG_FAIL,
//										result.getMessage(), handler);
//							}
//						} else {
//							sendData(AppConfig.FLAG_REQUEST_ERROR, "请求错误!",
//									handler);
//						}
//					}
//
//					@Override
//					public void onError(int statusCode) {
//						// TODO Auto-generated method stub
//						sendData(AppConfig.FLAG_REQUEST_ERROR,
//								"网络连接失败，请检查您的网络连接!", handler);
//					}
//				});
//	}

	/**
	 * turn to updateActivity
	 */
//	public void turnToUpdate(String url, String version) {
//		// 首先比较本地和服务端的版本，如果低于服务端的，则需升级
//
//		if (!local_ver.equals(version) && !"".equals(url)) {
//			Intent intent = new Intent();
//			intent.putExtra("apk_url", url);
//			intent.setClass(context, UpdateActivity.class);
//			context.startActivity(intent);
//			listener.Success("update");
//		} else {
//			if (isOpen && allSend)
//				Syyx.assitiveTouch(context);
//			listener.Success("success");
//		}
//	}

	/**
	 * 接口返回数据处理
	 */
	public void sendData(int num, Object data, Handler callback) {
		Message msg = callback.obtainMessage();
		msg.what = num;
		msg.obj = data;
		msg.sendToTarget();
	}

//	/**
//	 * 把assets下的apk写入到cache里面
//	 */
//	public void toCache() {
//		File cacheDir = context.getCacheDir();
//		final String cachePath = cacheDir.getAbsolutePath() + "/temp.apk";
//		AppConfig.isApkCacheExit = retrieveApkFromAssets(context,
//				"HeepayService.apk", cachePath);
//		handler.sendEmptyMessage(WAIT);
//	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
//				case UPDATE_SUCCESS:
//					UpdateApp result = (UpdateApp) msg.obj;
//					String url = result.getUrl();
//					String version = result.getVersions();
//					String type = result.getType();
//					String updata = result.getUpdatetip();
//					contrastUpdate(type, updata, url, version);
//
//					// turnToUpdate(url, version);
//					// listener.Success("success");
//					break;
				case AppConfig.FLAG_REQUEST_ERROR:
					disDialog();
					Toast.makeText(context, (String) msg.obj, Toast.LENGTH_SHORT)
							.show();
					break;
				case INIT:
					Init();
					break;

				case INIT_SUCCESS:
					InitMessage init = (InitMessage) msg.obj;
					setInit(init);
                    if(type.equals("reinit")){
                        Message message = new Message();
                        message.what = 4;
                        Syyx.handler.sendMessage(message);
                    }
					break;
				case AppConfig.FLAG_FAIL:
					disDialog();
					Toast.makeText(context, (String) msg.obj, Toast.LENGTH_SHORT)
							.show();
					listener.fail("fail");
					break;

			}
		}
	};

	/**
	 * 赋值初始化信息
	 */
	public void setInit(InitMessage result) {

		try {


			AppConfig.initMap.put("floatball",
					String.valueOf(result.getFloatBall()));

			AppConfig.initMap.put("wechat",
					String.valueOf(result.getWeChat()));
			AppConfig.initMap.put("qq",
					String.valueOf(result.getQQ()));
			AppConfig.initMap.put("kfqq",
					String.valueOf(result.getkfQQ()));
			AppConfig.initMap.put("changpwd",
					String.valueOf(result.getChangePwdBrief()));
			AppConfig.initMap.put("identifybrief",
					String.valueOf(result.getCodeBrief()));
			AppConfig.isAgreement=result.getAgreement();
			AppConfig.QQ = result.getQQ();
			AppConfig.WeChat = result.getWeChat();
//			AppConfig.initMap.put("isagreement",
//					String.valueOf(result.getAgreement()));
//			AppConfig.isAgreement=result.getAgreement();
			disDialog();
			// 首先判断本地是否保存时间内容，然后对比内容是否改变
//			List<Integer> list = result.getAppNoticeDayList();
//			justiceTime(list);
//			// 获取提示安装包应用的时间
//			if (!timeControl) {
//				// false 时间内容相同，使用seference记录的时间,然后取出哪一天显示
//				String localTime = String.valueOf(calendar
//						.get(Calendar.DAY_OF_MONTH));
//				for (int i = 0; i < list.size(); i++) {
//					if (seference.getPreferenceData("isDiaplayTime",
//							String.valueOf(list.get(i))).equals("No")) {
//						clientIsTimeDisplay = (seference.getPreferenceData(
//								"noticeTime", String.valueOf(list.get(i)))
//								.equals(localTime)) ? true : false;
//						seference.savePreferenceData("isDiaplayTime",
//								String.valueOf(list.get(i)), "Yes");
//						break;
//					}
//				}
//			} else {
//				// true 重新计算时间
//				caculateTime(list);
//			}
//	1
//			InstallApkFromAssets installApk = new InstallApkFromAssets(context);
//			AppConfig.isAppExit = installApk.isMobile_spExist();
//			if ("false".equals(String.valueOf(result.getClientFloat()))
//					&& "false".equals(String.valueOf(result.getGiftFloat()))
//					&& "false"
//							.equals(String.valueOf(result.getMoregameFloat()))
//					&& "false".equals(String.valueOf(result.getServiceFloat()))
//					&& "false".equals(String.valueOf(result.getFloatBall()))) {
//				allSend = false;
//			} else {
//				allSend = true;
//			}
//
//			 handler.sendEmptyMessage(FLAG_PUSH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 判断时间
	private void justiceTime(List<Integer> content) {
		String time = seference.getPreferenceData("noticeTimeContent", "time");
		if (time.length() != 0) {
			// 保存了数据，需要对比
			timeControl = time.equals(content.toString()) ? false : true;
		} else {
			// 没有保存时间，第一次进来
			timeControl = true;
		}
	}

	// 应用包安装包提示时间的计算
//	private void caculateTime(List<Integer> time) {
//		String localTime = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
//		// System.out
//		// .println("----------------------------localTime:" + localTime);
//		seference.savePreferenceData("noticeTimeContent", "time",
//				time.toString());
//		for (int i = 0; i < time.size(); i++) {
//			String noticeTime = String.valueOf(calendar
//					.get(Calendar.DAY_OF_MONTH) + time.get(i));
//			// noticeTime存储时间
//			seference.savePreferenceData("noticeTime",
//					String.valueOf(time.get(i)), noticeTime);
//			// 存放对应时间列表是否已经显示，默认初始化全部未显示
//			if (i == 0) {
//				seference.savePreferenceData("isDiaplayTime",
//						String.valueOf(time.get(i)), "Yes");
//				clientIsTimeDisplay = true;
//			} else
//				seference.savePreferenceData("isDiaplayTime",
//						String.valueOf(time.get(i)), "No");
//		}
//	}

//	/**
//	 * 与本地配置文件对比
//	 *
//	 * @param type
//	 *            更新类型，0选择性， 1 强制性
//	 * @param update
//	 *            跟新提示信息
//	 * @param url
//	 *            下载地址
//	 * @param version版本
//	 */
//	public void contrastUpdate(String type, String update, String url,
//							   String version) {
//
//		if (!local_ver.equals(version) && !"".equals(url)) {
//			if (type.equals("0")) {
//				SelectUpdata(update, url);
//				if (isOpen && allSend)
//					Syyx.assitiveTouch(context);
//				listener.Success("success");
//			} else {
//				CoerceUpdata(update, url);
//				listener.Success("update");
//			}
//		} else {
//			if (isOpen && allSend)
//				Syyx.assitiveTouch(context);
//			listener.Success("success");
//		}
//
//	}

//	/**
//	 * 调用浏览器下载
//	 */
//	public void Downloadwebview(String url) {
//		Intent viewIntent = new Intent("android.intent.action.VIEW",
//				Uri.parse(url));
//		context.startActivity(viewIntent);
//	}

//	/**
//	 * 选择性更新 text 内容 app_url 下载地址
//	 */
//	public void SelectUpdata(String text, final String app_url) {
//		data = new UpdataDialog(context, AppConfig.resourceId(context,
//				"Sj_MyDialog", "style"), text, false, new UpdataListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if (v.getId() == AppConfig.resourceId(context, "button_updata",
//						"id")) {
//					Downloadwebview(app_url);
//					data.dismiss();
//				} else if (v.getId() == AppConfig.resourceId(context,
//						"next_button_updata", "id")) {
//					data.dismiss();
//					// callBack("close2");
//					// PaymentActivity.this.finish();
//
//				}
//			}
//		});
//		data.setCancelable(false);
//		data.show();
//	}

//	/**
//	 * 强制更新 text 内容 app_url 下载地址
//	 */
//	public void CoerceUpdata(String text, final String app_url) {
//		coerce = new UpdataDialog(context, AppConfig.resourceId(context,
//				"Sj_MyDialog", "style"), text, true, new UpdataListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if (v.getId() == AppConfig.resourceId(context, "button_updata",
//						"id")) {
//					// String app_url =
//					// "http://attachments.49app.com/download/wzzx/android/49app_wzzx.apk";
//					Downloadwebview(app_url);
//					// coerce.dismiss();
//
//				} else if (v.getId() == AppConfig.resourceId(context,
//						"next_button_updata", "id")) {
//					coerce.dismiss();
//					// callBack("close2");
//					// PaymentActivity.this.finish();
//
//				}
//			}
//		});
//		coerce.setCancelable(false);
//		coerce.show();
//	}
}
