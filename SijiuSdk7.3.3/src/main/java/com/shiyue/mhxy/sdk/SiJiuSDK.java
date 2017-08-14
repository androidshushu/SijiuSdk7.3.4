package com.shiyue.mhxy.sdk;

import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.shiyue.mhxy.config.AppConfig;
import com.shiyue.mhxy.config.WebApi;
import com.shiyue.mhxy.http.ApiAsyncTask;
import com.shiyue.mhxy.http.ApiRequestListener;
import com.shiyue.mhxy.utils.DeviceInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 发起http请求类,调用{@link SiJiuSDK#get()}得到对象
 *
 * 接口列表------------------------------------------------------------------------
 * 初始化接口 :||| * {@link #startInit}
 * 注册接口 :{@link #startRegister} |||
 * 修改密码 :{@link #startChangePassword}
 * |||登录接口:*  {@link #startLogon}
 * //手机号注册 {@link #startPhoneregist}/
 *  |||令牌登录: {@link #startTickLogon}
 ///验证码验证接口 :{@link #startCheck_sms}|||
 找回密码信息接口: {@link #startFindPwdInfo} |||
 绑定手机获取验证码接口: {@link #startGetCodeBoundPhone}

 *
 *
 */

public class SiJiuSDK {

	private static SiJiuSDK instance;
	public static String LOGTAG = "SiJiuSDK";
	private static int DEVICE = 2;// 安卓设备

	DeviceInfo deviceInfo;

	private SiJiuSDK() {

	}

	public static SiJiuSDK get() {

		if (instance == null) {
			instance = new SiJiuSDK();
		}
		return instance;
	}

	/**********************************************************************/

	/**
	 * @param context
	 * @param appId
	 *            应用ID【INT型】
	 * @param appKey
	 *            应用KEY
	 * @param serverId
	 *            登录服务器标识
	 * @param userName
	 *            用户名【字符串，6-20位数字或英文】
	 * @param password
	 *            密码【字符串，6-16位】
	 * @param ver
	 *            渠道号【字符串】
	 *
	 * @param listener
	 *            回调.请求成功时回调{@link ApiRequestListener#onSuccess(Object)},
	 *            {@link Object}类型为 {@link com.sjsdk.bean.ResultAndMessage
	 *            ResultAndMessage},错误时回调{@link ApiRequestListener#onError(int)}
	 *            ,{@link int}表示{@link org.apache.http.HttpStatus}响应码.
	 * @return {@link ApiAsyncTask} (可调用{@link ApiAsyncTask#cancel(boolean)}
	 *         来取消请求)
	 */
	public ApiAsyncTask startRegister(Context context, int appId,
									  String appKey, String userName, String password,
									  String ver, ApiRequestListener listener) {
		JSONObject registJson=new JSONObject();
		try {
			registJson.put("dev_str",AppConfig.imei);
			registJson.put("password",password);
			registJson.put("account",userName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		if(!AppConfig.ad_id.equals("0")){
			params.put("ad_id", AppConfig.ad_id + "");
		}
		params.put("app_id", appId + "");
		params.put("channel_id",ver+ "");
		params.put("ts", System.currentTimeMillis()/1000 + "");
		params.put("data",registJson + "");
		return WebApi.startThreadRequest(WebApi.ACTION_REGISTER, listener,
				params, appKey);
	}
	/**********************************************************************/

	/**
	 * 修改密码
	 *
	 * @param context
	 * @param appId
	 *            应用ID【INT型】
	 * @param appKey
	 *            应用KEY
	 * @param uid
	 *            uid
	 * @param password
	 *            登录密码
	 * @param newPassword
	 *            新的密码【6-16位字符串】
	 * @param listener
	 *            回调.请求成功时回调{@link ApiRequestListener#onSuccess(Object)},
	 *            {@link Object}类型为 {@link com.sjsdk.bean.ResultAndMessage
	 *            ResultAndMessage},错误时回调{@link ApiRequestListener#onError(int)}
	 *            ,{@link int}表示{@link org.apache.http.HttpStatus}响应码.
	 * @return {@link ApiAsyncTask} (可调用{@link ApiAsyncTask#cancel(boolean)}
	 *         来取消请求)
	 */
	public ApiAsyncTask startChangePassword(Context context, int appId,
											String appKey,String ver , String password, String authcode,
											ApiRequestListener listener) {

		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("auth_code",authcode);
			jsonObject.put("password",password);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("app_id", appId + "");
		params.put("channel_id", ver + "");
		params.put("ts", System.currentTimeMillis()/1000 + "");
		params.put("data",jsonObject + "");
		return WebApi.startThreadRequest(WebApi.ACTION_CHANGEPASSWORD,
				listener, params, appKey);
	}



	/**
	 * @param context
	 * @param appId
	 *            应用ID【INT型】
	 * @param appKey
	 *            应用KEY
	 * @param serverId
	 *            登录服务器标识
	 * @param userName
	 *            用户名
	 * @param password
	 *            登录密码
	 * @param ver
	 *            渠道号【字符串】
	 *
	 * @param listener
	 *            回调.请求成功时回调 {@link ApiRequestListener#onSuccess(Object)},
	 *            {@link Object} 类型为{@link com.sjsdk.bean.LoginMessage
	 *            LoginMessage},错误时回调 {@link ApiRequestListener#onError(int)} ,
	 *            {@link int}表示 {@link org.apache.http.HttpStatus}响应码.
	 * @return {@link ApiAsyncTask} (可调用{@link ApiAsyncTask#cancel(boolean)}
	 *         来取消请求)
	 */
	public ApiAsyncTask startTickLogon(Context context, int appId, String appKey,
								   String login_token, String ver,
								   ApiRequestListener listener) {

		if (deviceInfo == null) {
			deviceInfo = new DeviceInfo(context);
		}

		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("dev_str",AppConfig.imei);
			jsonObject.put("login_token",login_token);

//			jsonObject.put("account",userName);


		} catch (JSONException e) {
			e.printStackTrace();
		}

		HashMap<String, Object> params = new HashMap<String, Object>();

		if(!AppConfig.ad_id.equals("0")){
			params.put("ad_id", AppConfig.ad_id + "");

		}
		params.put("app_id", appId + "");

		params.put("channel_id", ver + "");
		params.put("ts", System.currentTimeMillis()/1000 + "");

		// params.put("gameTime", gameTime + "");
		params.put("data",jsonObject + "");

		return WebApi.startThreadRequest(WebApi.ACTION_TICKLOGON, listener, params,
				appKey);
	}
	/**
	 * @param context
	 * @param appId
	 *            应用ID【INT型】
	 * @param appKey
	 *            应用KEY
	 * @param serverId
	 *            登录服务器标识
	 * @param userName
	 *            用户名
	 * @param password
	 *            登录密码
	 * @param ver
	 *            渠道号【字符串】
	 *
	 * @param listener
	 *            回调.请求成功时回调 {@link ApiRequestListener#onSuccess(Object)},
	 *            {@link Object} 类型为{@link com.sjsdk.bean.LoginMessage
	 *            LoginMessage},错误时回调 {@link ApiRequestListener#onError(int)} ,
	 *            {@link int}表示 {@link org.apache.http.HttpStatus}响应码.
	 * @return {@link ApiAsyncTask} (可调用{@link ApiAsyncTask#cancel(boolean)}
	 *         来取消请求)
	 */
	public ApiAsyncTask startLogon(Context context, int appId, String appKey,
								   String userName, String password, String ver,
								    ApiRequestListener listener) {

		if (deviceInfo == null) {
			deviceInfo = new DeviceInfo(context);
		}

        JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("dev_str",AppConfig.imei);
			jsonObject.put("password",password);

			jsonObject.put("account",userName);


		} catch (JSONException e) {
			e.printStackTrace();
		}

		HashMap<String, Object> params = new HashMap<String, Object>();

		if(!AppConfig.ad_id.equals("0")){
			params.put("ad_id", AppConfig.ad_id + "");

		}
		params.put("app_id", appId + "");

		params.put("channel_id", ver + "");
		params.put("ts", System.currentTimeMillis()/1000 + "");

		// params.put("gameTime", gameTime + "");

		params.put("data",jsonObject + "");


		return WebApi.startThreadRequest(WebApi.ACTION_LOGON, listener, params,
				appKey);
	}



	//开始处理手机号登录
	public ApiAsyncTask startphLogon(Context context, int appId, String appKey,
								   String phoneNum, String authCode, String ver,
								   ApiRequestListener listener) {

		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("dev_str",AppConfig.imei);
			jsonObject.put("auth_code",authCode);

			jsonObject.put("phone_number",phoneNum);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("app_id", appId + "");

		params.put("channel_id", ver + "");
		params.put("ts", System.currentTimeMillis()/1000 + "");

		// params.put("gameTime", gameTime + "");

		params.put("data",jsonObject + "");

		Log.d("shiyue_phlo",params.toString());
		return WebApi.startThreadRequest(WebApi.ACTION_PH_LOGON, listener, params,
				appKey);
	}
	/**
	 * 处理绑定手机
	 */
	public ApiAsyncTask startbindphone(Context context, int appId, String appKey,
									 String phoneNum, String authCode, String ver,
									 ApiRequestListener listener) {
		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("app_id", appId + "");
		params.put("channel_id", ver + "");
		params.put("ts", System.currentTimeMillis()/1000 + "");
        params.put("phone_number",phoneNum+"");
        params.put("bind_type","bind");
        params.put("auth_code",authCode + "");
		Log.d("shiyue_bind_phone",params.toString());
		return WebApi.startThreadRequest(WebApi.ACTION_BIND_PHONE, listener, params,
				appKey);
	}

	/**********************************************************************/

	/**********************************************************************/


	/**********************************************************************/

	/**
	 * 初始化接口
	 *
	 * @param context
	 * @param appId
	 *            应用ID【INT型】
	 * @param appKey
	 *            应用KEY
	 * @param ver
	 *            渠道号
	 * @param isAutoReg
	 *            是否自动注册【1-获取账号，0-不获取账号】
	 * @param listener
	 *            回调.请求成功时回调 {@link ApiRequestListener#onSuccess(Object)},
	 *            {@link Object} 类型为{@link com.sjsdk.bean.InitMessage
	 *            InitMessage},服务器没有数据时返回 null. 错误时回调
	 *            {@link ApiRequestListener#onError(int)} , {@link int}表示
	 *            {@link org.apache.http.HttpStatus}响应码.
	 * @return {@link ApiAsyncTask} (可调用{@link ApiAsyncTask#cancel(boolean)}
	 *         来取消请求)
	 */
	public ApiAsyncTask startInit(Context context, int appId, String appKey,
								  String ver, ApiRequestListener listener) {



		HashMap<String, Object> params = new HashMap<String, Object>();



		params.put("app_id", appId + "");
		params.put("channel_id", ver + "");
		params.put("ts", System.currentTimeMillis()/1000 + "");

		return WebApi.startThreadRequest(WebApi.ACTION_INIT, listener, params,
				appKey);
	}

	public ApiAsyncTask setRoleinfo(Context context, int appId, String appKey,
								  String ver,HashMap hs,int type, ApiRequestListener listener) {
		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("app_id", appId + "");
		params.put("channel_id", ver + "");
		params.put("ts", System.currentTimeMillis()/1000 + "");
		HashMap<String, Object> has = hs;
    if(type==AppConfig.ROLE_CREATE){
       params.put("role_name",hs.get("role_name"));
		params.put("project_id",hs.get("project_id"));
		params.put("role_id",hs.get("role_id"));
		params.put("account",hs.get("account"));
		params.put("srv_id",hs.get("srv_id"));
		params.put("srv_name",hs.get("srv_name"));
		params.put("type","role_create");

	}else if(type==AppConfig.ROLE_ENTER){
		params.put("role_name",hs.get("role_name"));
		params.put("project_id",hs.get("project_id"));
		params.put("role_id",hs.get("role_id"));
		params.put("account",hs.get("account"));
		params.put("srv_id",hs.get("srv_id"));
		params.put("srv_name",hs.get("srv_name"));
		params.put("role_level",hs.get("role_level"));
		params.put("type","role_login");
	}else if(type==AppConfig.ROLE_LEV){
		params.put("srv_id",hs.get("srv_id"));
		params.put("role_id",hs.get("role_id"));
		params.put("to_lev",hs.get("to_lev"));
		params.put("type","role_levup");
	}


		return WebApi.startThreadRequest(WebApi.ACTION_SETEXTDATA, listener, params,
				appKey);
	}


	/**

	 * 找回密码信息接口 找回密码界面信息接口 通过账号获取手机号
	 *
	 * @param context
	 * @param appId
	 *            应用ID
	 * @param appKey
	 *            应用KEY
	 * @param listener
	 *            回调.请求成功时回调{@link ApiRequestListener#onSuccess(Object)},
	 *            {@link Object}类型为 {@link com.sjsdk.bean.FindPwdInfo
	 *            FindPwdInfo},服务器没有数据时返回null,错误时回调
	 *            {@link ApiRequestListener#onError(int)} , {@link int}表示
	 *            {@link org.apache.http.HttpStatus}响应码.
	 * @return {@link ApiAsyncTask} (可调用{@link ApiAsyncTask#cancel(boolean)}
	 *         来取消请求)
	 */

	 public ApiAsyncTask startFindPwdInfo(Context context, int appId, String
	 appKey,String type,String type_date,String ver, ApiRequestListener listener) {

//	if (deviceInfo == null) { deviceInfo = new DeviceInfo(context);
//	}
		 JSONObject jsonObject=new JSONObject();
		 try {
			 jsonObject.put("type",type);
			 jsonObject.put("type_data",type_date);



		 } catch (JSONException e) {
			 e.printStackTrace();
		 }

		 HashMap<String, Object> params = new HashMap<String, Object>();

		 params.put("app_id", appId + "");

		 params.put("channel_id", ver + "");
		 params.put("ts", System.currentTimeMillis()/1000 + "");

		 // params.put("gameTime", gameTime + "");

		 params.put("data",jsonObject + "");

		 if(AppConfig.isTest){

			 Log.d("shiyue_phfindpwd",params.toString());
		 }



      return WebApi.startThreadRequest(WebApi.ACTION_FINDPWDINFO, listener,
	params, appKey); }


	/**
	 * 通过手机号码与账号进行密码找回，并发送密码信息至用户手机
	 *
	 * @param context
	 * @param appId
	 * @param appKey
	 *            应用KEY
	 * @param userName
	 *            用户名
	 * @param mobile
	 *            绑定的手机号码
	 * @param listener
	 *            回调.请求成功时回调{@link ApiRequestListener#onSuccess(Object)},
	 *            {@link Object}类型为 {@link com.sjsdk.bean.ResultAndMessage
	 *            ResultAndMessage},错误时回调{@link ApiRequestListener#onError(int)}
	 *            ,{@link int}表示{@link org.apache.http.HttpStatus}响应码.
	 * @return {@link ApiAsyncTask} (可调用{@link ApiAsyncTask#cancel(boolean)}
	 *         来取消请求)
	 */
	public ApiAsyncTask startPhoneregist(Context context, int appId,String appKey,String ver, String authcode,String pwd ,String mobile,
										  ApiRequestListener listener) {


		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("auth_code",authcode);
			jsonObject.put("phone_number",mobile);
			jsonObject.put("dev_str",AppConfig.imei);
			jsonObject.put("password",pwd);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("app_id", appId + "");

		params.put("channel_id", ver + "");
		params.put("ts", System.currentTimeMillis()/1000 + "");

		// params.put("gameTime", gameTime + "");

		params.put("data",jsonObject + "");

		Log.d("shiyue_phfindpwd",params.toString());


		return WebApi.startThreadRequest(WebApi.ACTION_PH_REGIST, listener,
				params, appKey);
	}

	/**
	 * 通过手机号码与账号进行密码找回，并发送密码信息至用户手机
	 *
	 * @param context
	 * @param appId
	 * @param appKey
	 *            应用KEY
	 * @param userName
	 *            用户名
	 * @param mobile
	 *            绑定的手机号码
	 * @param listener
	 *            回调.请求成功时回调{@link ApiRequestListener#onSuccess(Object)},
	 *            {@link Object}类型为 {@link com.sjsdk.bean.ResultAndMessage
	 *            ResultAndMessage},错误时回调{@link ApiRequestListener#onError(int)}
	 *            ,{@link int}表示{@link org.apache.http.HttpStatus}响应码.
	 * @return {@link ApiAsyncTask} (可调用{@link ApiAsyncTask#cancel(boolean)}
	 *         来取消请求)
	 */
//	public ApiAsyncTask startFindPassword(Context context, int appId,
//										  String appKey, String userName, String mobile,
//										  ApiRequestListener listener) {
//
//		if (deviceInfo == null) {
//			deviceInfo = new DeviceInfo(context);
//		}
//
//		HashMap<String, Object> params = new HashMap<String, Object>();
//
//		params.put("appId", appId + "");
//		params.put("mobile", mobile + "");
//		params.put("userName", userName + "");
//		// params.put("requestId", "016fc476-d22e-4b84-b09c-3a84eafb3e09");
//
//		params.put("requestId", System.currentTimeMillis() + "");
//
//		params.put("version", AppConfig.SJAPP_VER);
//		return WebApi.startThreadRequest(WebApi.ACTION_FINDPASSWORD, listener,
//				params, appKey);
//	}

	/**********************************************************************/

	/**
	 * 获取手机验证码
	 *
	 * @param context
	 * @param appId
	 * @param appKey
	 *            应用KEY
	 * @param verId
	 * @param mobile
	 *            绑定的手机号码
	 * @param listener
	 *            回调.请求成功时回调{@link ApiRequestListener#onSuccess(Object)},
	 *            {@link Object}类型为 {@link com.sjsdk.bean.ResultAndMessage
	 *            ResultAndMessage},错误时回调{@link ApiRequestListener#onError(int)}
	 *            ,{@link int}表示{@link org.apache.http.HttpStatus}响应码.
	 * @return {@link ApiAsyncTask} (可调用{@link ApiAsyncTask#cancel(boolean)}
	 *         来取消请求)
	 */

	public ApiAsyncTask startGetCodeBoundPhone(Context context, int appId,
											   String appKey, String verId,String sms_type, String mobile,
											   ApiRequestListener listener) {

//		if (deviceInfo == null) {
//			deviceInfo = new DeviceInfo(context);
//		}

		HashMap<String, Object> params = new HashMap<String, Object>();

		JSONObject jsonObject=new JSONObject();
		try {

			jsonObject.put("sms_type",sms_type);

			jsonObject.put("phone_number",mobile);


		} catch (JSONException e) {
			e.printStackTrace();
		}
		params.put("app_id", appId + "");
		params.put("channel_id", verId);
		params.put("ts", System.currentTimeMillis()/1000 + "");
		params.put("data", jsonObject + "");

//		params.put("version", AppConfig.SJAPP_VER);
        Log.i("+++++++++",params+"");
		return WebApi.startThreadRequest(WebApi.ACTION_GETCODEBOUNDPHONE,
				listener, params, appKey);
	}

	/**
	 * 获取找回密码验证码
	 *
	 * @param context
	 * @param appId
	 * @param appKey
	 *            应用KEY
	 * @param verId
	 * @param mobile
	 *            绑定的手机号码
	 * @param listener
	 *            回调.请求成功时回调{@link ApiRequestListener#onSuccess(Object)},
	 *            {@link Object}类型为 {@link com.sjsdk.bean.ResultAndMessage
	 *            ResultAndMessage},错误时回调{@link ApiRequestListener#onError(int)}
	 *            ,{@link int}表示{@link org.apache.http.HttpStatus}响应码.
	 * @return {@link ApiAsyncTask} (可调用{@link ApiAsyncTask#cancel(boolean)}
	 *         来取消请求)
	 */

	public ApiAsyncTask startGetCodeFPWD(Context context, int appId,
											   String appKey, String verId, String mobile,
											   ApiRequestListener listener) {



		HashMap<String, Object> params = new HashMap<String, Object>();





		params.put("app_id", appId + "");
		params.put("channel_id", verId);
		params.put("ts", System.currentTimeMillis()/1000 + "");
		params.put("account", mobile + "");

//		params.put("version", AppConfig.SJAPP_VER);
		return WebApi.startThreadRequest(WebApi.ACTION_GETCODEFPWD,
				listener, params, appKey);
	}

	/**
	 * 验证码验证
	 *
	 * @param context
	 * @param appId
	 * @param appKey
	 *            应用KEY
	 * @param verId
	 * @param mobile
	 *            绑定的手机号码

	 * @return {@link ApiAsyncTask} (可调用{@link ApiAsyncTask#cancel(boolean)}
	 *         来取消请求)
	 */

	public ApiAsyncTask startCheck_sms(Context context, int appId,
											   String appKey, String verId,String sms_type, String authcode ,String mobile,
											   ApiRequestListener listener) {

//		if (deviceInfo == null) {
//			deviceInfo = new DeviceInfo(context);
//		}

		HashMap<String, Object> params = new HashMap<String, Object>();

		JSONObject jsonObject=new JSONObject();
		try {

			jsonObject.put("sms_type",sms_type);
			jsonObject.put("auth_code",authcode);
			jsonObject.put("phone_number",mobile);


		} catch (JSONException e) {
			e.printStackTrace();
		}
		params.put("app_id", appId + "");
		params.put("channel_id", verId);
		params.put("ts", System.currentTimeMillis()/1000 + "");
		params.put("data", jsonObject + "");

//		params.put("version", AppConfig.SJAPP_VER);
		return WebApi.startThreadRequest(WebApi.ACTION_CHECKSMS,
				listener, params, appKey);
	}


	public ApiAsyncTask thirdRegist(Context context, int appId, String appKey, String account, String password, String oauth_type, String oauth_id, String ver, ApiRequestListener listener)
	{
		if (this.deviceInfo == null) {
			this.deviceInfo = new DeviceInfo(context);
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("dev_str", AppConfig.imei);
			jsonObject.put("password", password);
			jsonObject.put("account", account);
			jsonObject.put("oauth_id", oauth_id);
			jsonObject.put("oauth_type", oauth_type);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

		HashMap params = new HashMap();

		if (!AppConfig.ad_id.equals("0")) {
			params.put("ad_id", AppConfig.ad_id + "");
		}

		params.put("app_id", appId + "");

		params.put("channel_id", ver + "");
		params.put("ts", System.currentTimeMillis() / 1000L + "");

		params.put("data", jsonObject + "");

		return WebApi.startThreadRequest(WebApi.ACTION_THIRDREGIST, listener, params, appKey);
	}
	public ApiAsyncTask thirdQuery(Context context, int appId, String appKey, String oauth_type, String oauth_id, String ver, ApiRequestListener listener)
	{
		if (this.deviceInfo == null) {
			this.deviceInfo = new DeviceInfo(context);
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("dev_str", AppConfig.imei);
			jsonObject.put("oauth_id", oauth_id);
			jsonObject.put("oauth_type", oauth_type);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

		HashMap params = new HashMap();

		if (!AppConfig.ad_id.equals("0")) {
			params.put("ad_id", AppConfig.ad_id + "");
		}

		params.put("app_id", appId + "");

		params.put("channel_id", ver + "");
		params.put("ts", System.currentTimeMillis() / 1000L + "");

		params.put("data", jsonObject + "");

		return WebApi.startThreadRequest(WebApi.ACTION_THIRDQUERY, listener, params, appKey);
	}

	}



















