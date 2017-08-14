package com.shiyue.mhxy.config;

import java.util.HashMap;
import java.util.Map;

import com.shiyue.mhxy.http.ApiAsyncTask;
import com.shiyue.mhxy.http.ApiRequestListener;
import com.shiyue.mhxy.http.SiJiuApiTask;

public class WebApi {
	
	private static final String LOGTAG = "WebApi";
	
//	public static final String HOST = "https://api.shiyuegame.com/sdk";//正式
//	public static final String HOST = "http://sandbox.shiyuegame.com/sdk";//测试用/
	public static final String HOST= "http://e15s106987.iok.la/sdk";//杰
//	public static final String HOST7 = "http://e15s106987.iok.la/sdk";
//
	public static Map<String,String> HttpTypeMap=new HashMap<String,String>();
	/**
	 * 接口名称配置信息
	 */
	// webapi地址
	public static final String API_JAVA = HOST + "/api/v6/";
	//初始化接口

	public static final String ACTION_INIT=HOST+"/auth";
	public static final String ACTION_LOGON=HOST+"/account/login";
	public static final String ACTION_LOGOUT=HOST+"/account/logout";
	public static final String ACTION_TICKLOGON=HOST+"/account/login_by_token";
	public static final String ACTION_SETEXTDATA=HOST+"/statistics";
	public static final String ACTION_PH_LOGON=HOST+"/account/login_by_phone";
	public static final String ACTION_REGISTER=HOST + "/account/create";
	public static final String ACTION_FINDPWDINFO=HOST + "/account/get_account_info";
	public static final String ACTION_GETCODEBOUNDPHONE = HOST + "/phone/send_sms";
	public static final String ACTION_GETCODEFPWD=HOST + "/account/findpwd";
	public static final String ACTION_CHECKSMS=HOST + "/phone/check_sms";
	public static final String ACTION_PH_REGIST=HOST + "/account/phone_reg";
	public static final String ACTION_PAY=HOST + "/pay/wap?";
	public static final String ACTION_CHANGEPASSWORD=HOST + "/account/resetpwd";
	public static final String ACTION_THIRDREGIST = HOST +"/account/oauth_reg";
	public static final String ACTION_THIRDQUERY =HOST + "/oauth/get_account_info";
	public static final String ACTION_BIND_PHONE = HOST +"/account/bind/phone";


	
	/**
	 * 接口请求方式配置
	 */
    static{
		HttpTypeMap.put(ACTION_INIT, "post");
		HttpTypeMap.put(ACTION_LOGON, "post");
		HttpTypeMap.put(ACTION_LOGOUT, "get");
		HttpTypeMap.put(ACTION_PH_LOGON, "post");
    	HttpTypeMap.put(ACTION_REGISTER, "post");
		HttpTypeMap.put(ACTION_FINDPWDINFO, "post");
		HttpTypeMap.put(ACTION_CHECKSMS, "post");
		HttpTypeMap.put(ACTION_GETCODEBOUNDPHONE, "post");
		HttpTypeMap.put(ACTION_PH_REGIST, "post");
		HttpTypeMap.put(ACTION_TICKLOGON, "post");
		HttpTypeMap.put(ACTION_GETCODEFPWD, "post");
		HttpTypeMap.put(ACTION_SETEXTDATA, "post");
		HttpTypeMap.put(ACTION_CHANGEPASSWORD, "post");
		HttpTypeMap.put(ACTION_THIRDREGIST, "post");
		HttpTypeMap.put(ACTION_THIRDQUERY, "post");
		HttpTypeMap.put(ACTION_BIND_PHONE,"post");
		
    }
	
	/**
	 * 后台启动http连接，使用Thread实现
	 * 
	 * @param context
	 * @param action
	 * @param listener
	 * @param params
	 */
	public static ApiAsyncTask startThreadRequest(String webApi,
			ApiRequestListener listener, HashMap<String, Object> params,
			String appKey) {
            
		ApiAsyncTask task = new SiJiuApiTask(webApi, listener, params, appKey);
		task.start();

		return task;
	}

}
