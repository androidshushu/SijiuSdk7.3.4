package com.shiyue.mhxy.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

public class DeviceInfo {

	protected static final String PREFS_FILE = "device_id.xml";
	protected static final String PREFS_DEVICE_ID = "device_id";

	// private Context context;
	private String nativePhoneNumber;// 当前设置的电话号码
	private String serialId = "";// sim序列号
	private String imei = "";// imei 唯一的设备ID：GSM手机的 IMEI 和 CDMA手机的 MEID. 
	private String systemId = "";// ANDROID_ID
	private String systemInfo = "";// 系统信息【格式：系统版本@手机型号】
	private String uuidString = null;// 设备唯一码
	private String systemVer;
	private String model;
	private String deviceScreen;
	private String appVersion;
	private String imsi="";//手机sim卡的串号
	private String channel;
	private String channel_tag;


	// private static UUID uuid;

	public DeviceInfo(Context context) {

		// this.context = context;
		getData(context);
		Properties properties = new Properties();
		try {
			properties.load(context.getAssets().open("sijiu.properties"));
	 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 channel = properties.getProperty("agent");
	     channel_tag=properties.getProperty("channel_tag");
         appVersion=properties.getProperty("version");
	}
	
  

	public String getChannel() {
		return channel;
	}



	public String getChannel_tag() {
		return channel_tag;
	}



	private void getData(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		nativePhoneNumber = telephonyManager.getLine1Number();

		if (TextUtils.isEmpty(nativePhoneNumber)) {
			nativePhoneNumber = "+0000";
		}
		// if(nativePhoneNumber.contains(cs))
		  imei = telephonyManager.getDeviceId();
		  serialId = telephonyManager.getSimSerialNumber();
	     imsi = telephonyManager.getSubscriberId();
		// systemInfo
		systemVer = android.os.Build.VERSION.RELEASE;
		model = "";
		try {
			model = URLEncoder.encode(Build.MODEL, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		systemInfo = systemVer + "@" + model;

		// systemId
		systemId = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);

		if (uuidString == null) {

			final SharedPreferences prefs = context.getSharedPreferences(
					PREFS_FILE, 0);
			uuidString = prefs.getString(PREFS_DEVICE_ID, null);

			if (uuidString == null) {

				// Use the Android ID unless it's broken, in which case
				// fallback on deviceId,
				// unless it's not available, then fallback on a random
				// number which we store
				// to a prefs file
				try {
					if (!"9774d56d682e549c".equals(systemId)) {
						uuidString = UUID.nameUUIDFromBytes(
								systemId.getBytes("utf8")).toString();
					} else {

						uuidString = imei != null ? UUID.nameUUIDFromBytes(
								imei.getBytes("utf8")).toString() : UUID
								.randomUUID().toString();
					}
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}

			}

			// Write the value out to the prefs file
			prefs.edit().putString(PREFS_DEVICE_ID, uuidString).commit();

		}
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		if (context instanceof Activity) {
			((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		}
		int W = mDisplayMetrics.widthPixels;
		int H = mDisplayMetrics.heightPixels;
		deviceScreen=W+"*"+H;

	}
	public String getImsi(){
		return imsi;
	}
	
	
	/**
	 * 获取版本号
	 * @param context
	 * @return
	 */
	public String getVersion(Context context) {
	    try {
	        PackageManager manager = context.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
	        String version = info.versionName;
	        return version;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "";
	    }
	}

	public String getNativePhoneNumber() {
		return nativePhoneNumber;
	}

	public String getSerialId() {
		return serialId;
	}

	public String getImei() {
		return imei;
	}

	public String getSystemId() {
		return systemId;
	}

	public String getUuid() {
		return uuidString;
	}

	public String getSystemInfo() {
		return systemInfo;
	}

	public String getSystemVer() {
		return systemVer;
	}

	public void setSystemVer(String systemVer) {
		this.systemVer = systemVer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDeviceScreen() {
		return deviceScreen;
	}

	public void setDeviceScreen(String deviceScreen) {
		this.deviceScreen = deviceScreen;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

}
