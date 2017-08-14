package com.shiyue.mhxy.http;

import android.util.Log;
import com.shiyue.mhxy.config.AppConfig;
import com.shiyue.mhxy.user.BindMessage;
import com.shiyue.mhxy.user.InitMessage;
import com.shiyue.mhxy.user.LoginMessage;
import com.shiyue.mhxy.user.Noticed;
import com.shiyue.mhxy.user.Order;
import com.shiyue.mhxy.user.Order.OrderItem;
import com.shiyue.mhxy.user.ResultAndMessage;
import com.shiyue.mhxy.user.Vip;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParse
{
	private static final String LOGTAG = "JSONParse";
	private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC04bV191VtLj2uzVEYKqPaIGp7\r17l+ixYRPflXvlvtR+3CAZ90z/R94z029eyMI8NRBFWjuJA4eL3z8E2mw0ow+vmc\rWW5oMZgGks9qhACPgn9d5scm+VGZE6jy/M0qqH8xMDwaHNMsOoNfSGvG/342Fq/E\rjxD5h37+lPaex5M12QIDAQAB\r";

	public static Object parseInitMsg(String data)
			throws JSONException
	{
		InitMessage result = new InitMessage();
		JSONObject jsonObject = new JSONObject(data);
		if (AppConfig.isTest)
		{
			Log.d("shiyue_InitJsonData=", jsonObject.toString());
		}

		boolean r = jsonObject.getBoolean("success");

		result.setResult(r);

		result.setMessage(jsonObject.getString("message"));

		if (r) {
			JSONObject configInfo = jsonObject.getJSONObject("config_info");
			JSONObject appConfig = configInfo.getJSONObject("app_config");
			result.setWeChat(appConfig.getString("oauth_wechat_status"));
			result.setFloatBall(appConfig.getString("float_ball_status"));
			result.setQQ(appConfig.getString("oauth_qq_status"));
			result.setAgreement(appConfig.getString("user_agreement_status"));

			JSONObject globalConfig = configInfo.getJSONObject("global_config");

			result.setkfQQ(globalConfig.getString("kf_qq_number"));
			result.setChangePwdBrief(globalConfig.getString("change_password_brief"));
			result.setCodeBrief(globalConfig.getString("identify_code_brief"));
		}

		return result;
	}

	public static Object parseLogon(String data)
			throws JSONException
	{
		LoginMessage loginMsg = new LoginMessage();
		JSONObject jsonObject = new JSONObject(data);

		boolean r = jsonObject.getBoolean("success");
		Log.d("shiyue_login=", data.toString());
		loginMsg.setResult(r);
		loginMsg.setMessage(jsonObject.getString("message"));

		if (r)
		{
			loginMsg.setTimestamp(jsonObject.getString("ts"));
			loginMsg.setUid(jsonObject.getString("account"));
			loginMsg.setUserName(jsonObject.getString("account_name"));
			loginMsg.setPhoneNumber(jsonObject.getString("phone_number"));
			loginMsg.setToken(jsonObject.getString("tick"));
			loginMsg.setLoginTick(jsonObject.getString("login_token"));
		}

		return loginMsg;
	}

	public static Object parsePhLogon(String data)
			throws JSONException
	{
		LoginMessage loginMsg = new LoginMessage();
		JSONObject jsonObject = new JSONObject(data);

		boolean r = jsonObject.getBoolean("success");

		if (AppConfig.isTest) {
			Log.d("shiyue_login=", data.toString());
		}
		loginMsg.setResult(r);
		loginMsg.setMessage(jsonObject.getString("message"));
		loginMsg.setCode(jsonObject.getInt("code"));

		if (r)
		{
			loginMsg.setTimestamp(jsonObject.getString("ts"));
			loginMsg.setUid(jsonObject.getString("account"));
			loginMsg.setUserName(jsonObject.getString("account_name"));
			loginMsg.setPhoneNumber(jsonObject.getString("phone_number"));
			loginMsg.setToken(jsonObject.getString("tick"));
			loginMsg.setLoginTick(jsonObject.getString("login_token"));
		}

		return loginMsg;
	}

	public static Object parseResultAndMessage(String data)
			throws JSONException
	{
		ResultAndMessage result = new ResultAndMessage();

		JSONObject jsonObject = new JSONObject(data);

		result.setMessage(jsonObject.getString("message"));
		boolean r = jsonObject.getBoolean("success");
		result.setResult(r);

		if (AppConfig.isTest)
		{
			Log.d("shiyue_Msg=", data);
		}

		return result;
	}

	public static Object parseAccountInfo(String data)
			throws JSONException
	{
		ResultAndMessage result = new ResultAndMessage();

		JSONObject jsonObject = new JSONObject(data);

		result.setMessage(jsonObject.getString("message"));
		boolean r = jsonObject.getBoolean("success");
		result.setResult(r);
		if (r) {
			JSONObject accinfo = jsonObject.getJSONObject("account_info");
			result.setData(accinfo.getString("phone_number"));
		}

		if (AppConfig.isTest)
		{
			Log.d("shiyue_registMsg=", data);
		}

		return result;
	}

	public static Object parseOauthInfo(String data)
			throws JSONException
	{
		ResultAndMessage result = new ResultAndMessage();

		JSONObject jsonObject = new JSONObject(data);
//		Log.d("shiyue_registMsg=", data);

		result.setMessage(jsonObject.getString("message"));
		boolean r = jsonObject.getBoolean("success");
		result.setResult(r);
		if (r) {
			JSONObject accinfo = jsonObject.getJSONObject("account_info");
			result.setAccountname(accinfo.getString("account_name"));
			result.setAccountid(accinfo.getString("account_id"));
		}

		if (AppConfig.isTest)
		{
			Log.d("shiyue_registMsg=", data);
		}

		return result;
	}

//	public static Object parseOrderList(String data)
//			throws JSONException
//	{
//		Order order = new Order();
//		order.orderList = new ArrayList();
//
//		JSONObject jsonObject = new JSONObject(data);
//
//		boolean r = jsonObject.getBoolean("Result");
//		order.setResult(r);
//		order.setMessage(jsonObject.getString("Msg"));
//
//		if (r) {
//			JSONObject dataObject = jsonObject.getJSONObject("Data");
//			JSONArray orderArray = dataObject.getJSONArray("Items");
//
//			for (int i = 0; i < orderArray.length(); i++)
//			{
//				JSONObject obj = (JSONObject)orderArray.opt(i);
//				Order tmp101_100 = order; tmp101_100.getClass(); Order.OrderItem item = new Order.OrderItem(tmp101_100);
//				item.setBillno(obj.getString("Billno"));
//				item.setPayTypeName(obj.getString("PayTypeName"));
//				item.setOrderStatus(obj.getString("OrderStatus"));
//				item.setCreateDate(obj.getString("CreateDate"));
//				item.setGameName(obj.getString("GameName"));
//				item.setAmount(obj.getInt("Amount"));
//
//				order.orderList.add(item);
//			}
//
//			order.setPageIndex(dataObject.getInt("PageIndex"));
//			order.setPageSize(dataObject.getInt("PageSize"));
//			order.setTotalItemCount(dataObject.getInt("TotalItemCount"));
//			order.setTotalPageCount(dataObject.getInt("TotalPageCount"));
//		}
//
//		return order;
//	}

	public static Object parsePlatformDesc(String data)
			throws JSONException
	{
		ResultAndMessage result = new ResultAndMessage();

		JSONObject jsonObject = new JSONObject(data);
		result.setMessage(jsonObject.getString("Msg"));
		boolean r = jsonObject.getBoolean("Result");
		result.setResult(r);
		if (r)
		{
			JSONObject js = jsonObject.getJSONObject("Data");
			result.setData(js.getString("content"));
		}

		return result;
	}

//	public static Object parseFindSecurityQuestion(String data)
//			throws JSONException
//	{
//		ResultAndMessage result = new ResultAndMessage();
//		List list = new ArrayList();
//
//		JSONObject jsonObject = new JSONObject(data);
//		result.setMessage(jsonObject.getString("Msg"));
//		boolean r = jsonObject.getBoolean("Result");
//		result.setResult(r);
//		if (r)
//		{
//			JSONArray orderArray = jsonObject.getJSONArray("Data");
//
//			for (int i = 0; i < orderArray.length(); i++) {
//				String obj = (String)orderArray.opt(i);
//				list.add(obj);
//			}
//			result.setDatas(list);
//		}
//
//		return result;
//	}

	private static boolean getBoolean(JSONObject result)
	{
		return false;
	}

	public static Object parseVip(String data)
			throws JSONException
	{
		Vip result = new Vip();
		JSONObject jsonObject = new JSONObject(data);

		boolean r = jsonObject.getBoolean("Result");

		result.setResult(r);
		result.setMsg(jsonObject.getString("Msg"));

		return result;
	}

	public static Object setnoticed(String data)
			throws JSONException
	{
		Noticed result = new Noticed();
		JSONObject jsonObject = new JSONObject(data);

		boolean r = jsonObject.getBoolean("Result");
		result.setResult(r);
		result.setMsg(jsonObject.getString("Msg"));

		if (r) {
			JSONObject dataObject = jsonObject.getJSONObject("Data");
			result.setUrl(dataObject.getString("url"));
			result.setShow_type(dataObject.getString("show_type"));
			result.setTiele(dataObject.getString("title"));
		}

		return result;
	}

	public static Object parseBindPhoneMessage(String data)
			throws JSONException {
		BindMessage bindMsg = new BindMessage();
		JSONObject jsonObject = new JSONObject(data);
		boolean r = jsonObject.getBoolean("success");
		Log.d("shiyue_bindresult=",data.toString());
		bindMsg.setResult(r);
		bindMsg.setMessage(jsonObject.getString("message"));
		if (r){
//			bindMsg.setTimestamp(jsonObject.getString("ts"));
//			bindMsg.setUid(jsonObject.getString("account"));
//			bindMsg.setUserName(jsonObject.getString("account_name"));
//			bindMsg.setLogintick(jsonObject.getString("tick"));
			bindMsg.setCode(jsonObject.getInt("code"));
		}
		Log.d("handle_resource=",bindMsg.toString());
		return bindMsg;
	}
}
