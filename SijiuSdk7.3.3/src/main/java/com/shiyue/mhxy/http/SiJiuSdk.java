package com.shiyue.mhxy.http;

import java.util.HashMap;


import com.shiyue.mhxy.config.WebApi;
import com.shiyue.mhxy.utils.DeviceInfo;


import android.content.Context;


public class SiJiuSdk {

	private static SiJiuSdk instance;
	public static String LOGTAG = "SiJiuSDK";
	private static int DEVICE = 2;// 安卓设备

	DeviceInfo deviceInfo;

	private SiJiuSdk() {

	}

	public static SiJiuSdk get() {
		if (instance == null) {
			instance = new SiJiuSdk();
		}
		return instance;
	}

	/**
	 * 初始化接口
	 */
	
	public ApiAsyncTask startInit(Context context, int appId, String appKey,
			String ver, int isAutoReg, ApiRequestListener listener) {

		if (deviceInfo == null) {
			deviceInfo = new DeviceInfo(context);
		}

		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("appId", appId + "");
		params.put("ver", ver + "");
		params.put("isAutoReg", isAutoReg + "");

		params.put("device", DEVICE + "");
		params.put("udid", deviceInfo.getUuid() + "");
		params.put("imeiId", deviceInfo.getImei() + "");
		params.put("systemId", deviceInfo.getSystemId() + "");
		params.put("serialId", deviceInfo.getSerialId() + "");
		params.put("mobile", deviceInfo.getNativePhoneNumber() + "");
		params.put("systemInfo", deviceInfo.getSystemInfo() + "");
		params.put("requestId", System.currentTimeMillis() + "");

		return WebApi.startThreadRequest(WebApi.ACTION_INIT, listener, params,
				appKey);
	}





	
	

}
