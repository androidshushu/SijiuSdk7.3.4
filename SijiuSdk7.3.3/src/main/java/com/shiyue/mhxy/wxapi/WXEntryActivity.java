package com.shiyue.mhxy.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import com.shiyue.mhxy.config.AppConfig;
import com.shiyue.mhxy.user.LoginActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendAuth.Resp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Random;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class WXEntryActivity extends Activity
		implements IWXAPIEventHandler
{
	private IWXAPI api;
	private BaseResp resp = null;
 private String result="";
	private String GetCodeRequest = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	private String GetUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(AppConfig.resourceId(this, "wxlayout", "layout"));

		this.api = WXAPIFactory.createWXAPI(this,AppConfig.WXAPP_ID, false);
		this.api.registerApp(AppConfig.WXAPP_ID);
		try
		{
			boolean result = this.api.handleIntent(getIntent(), this);
			if (!result) {
				finish();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void onNewIntent(Intent intent)
	{
		super.onNewIntent(intent);

		setIntent(intent);
		this.api.handleIntent(intent, this);
	}

	public void onReq(BaseReq req)
	{
		finish();
	}

	public void onResp(BaseResp resp)
	{
		if (resp != null)
			this.resp = resp;

		switch (resp.errCode) {
			case 0:
				 result = "授权成功";
				SendAuth.Resp regResp = (SendAuth.Resp)resp;
				if (!regResp.state.equals(AppConfig.uuid)) {
					return;
				}
				String code = regResp.code;

				String get_access_token = getCodeRequest(code);

				new AsyncTask<String, Void, String>() {

					@Override
					protected String doInBackground(String... params) {

						String url = params[0];

						return getHttpClient(url);
					}
					@Override
					protected void onPostExecute(String result) {
						super.onPostExecute(result);

						try {
							if (AppConfig.isTest) {
								Log.d("shiyue", result);
							}

							if (!result.equals("")) {
								JSONObject js = new JSONObject(result);

								String access_token = js
										.getString("access_token");

								String openid = js.getString("openid");
								String get_user_info_url = WXEntryActivity.this.getUserInfo(access_token, openid);

								WXEntryActivity.this.getUserInfo(get_user_info_url);
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}.execute(get_access_token);


				break;
			case -2:
				result = "用户取消授权";
				break;
			case -4:
				result = "拒绝授权";
				break;
			case -3:
			case -1:
			default:
				result = "未知错误";
		}

		finish();
		Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
	}

	private void getUserInfo(String user_info_url)
	{	new AsyncTask<String, Void, String>() {

		@Override
		protected String doInBackground(String... params) {

			String url = params[0];

			return getHttpClient(url);
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (!result.equals("")) {
				if (AppConfig.isTest) {
					Log.d("shiyue", result + "22");
				}

				JSONObject response = null;
				try {
					response = new JSONObject(result);
					String openid = response.getString("openid");
					String nickname = response.getString("nickname");
					String loginType = "2";
					Message ms = LoginActivity._instance.handler.obtainMessage();
					HashMap hashMap = new HashMap();
					String user = WXEntryActivity.this.ramrule();
					hashMap.put("userName", user);
					hashMap.put("password", "Wechat_" + openid);
					hashMap.put("openid", openid);
					hashMap.put("nickname", nickname);
					hashMap.put("loginType", loginType);
					ms.obj = hashMap;
					ms.what =AppConfig.THIRD_REGIST;
					LoginActivity._instance.handler.sendMessage(ms);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}.execute(user_info_url);
//		new AsyncTask()
//		{
//			protected String doInBackground(String[] params)
//			{
//				String url = params[0];
//
//				return WXEntryActivity.getHttpClient(url);
//			}
//
//			protected void onPostExecute(String result) {
//				super.onPostExecute(result);
//				if (!result.equals("")) {
//					if (AppConfig.isTest) {
//						Log.d("shiyue", result + "22");
//					}
//
//					JSONObject response = null;
//					try {
//						response = new JSONObject(result);
//						String openid = response.getString("openid");
//						String nickname = response.getString("nickname");
//						String loginType = "2";
//						Message ms = LoginActivity._instance.handler.obtainMessage();
//						HashMap hashMap = new HashMap();
//						String user = WXEntryActivity.this.ramrule();
//						hashMap.put("userName", user);
//						hashMap.put("password", "Wechat_" + openid);
//						hashMap.put("openid", openid);
//						hashMap.put("nickname", nickname);
//						hashMap.put("loginType", loginType);
//						ms.obj = hashMap;
//						ms.what = 504;
//						LoginActivity._instance.handler.sendMessage(ms);
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//
//				.execute(new String[] { user_info_url });
	}

	private String ramrule()
	{
		String strAll = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String strCp = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String strNum = "0123456789";
		String userName = "";
		String passWord = "";
		Random random = new Random();

		for (int i = 0; i < 8; i++)
		{
			String oneChar = "";
			if (i < 2) {
				int rd = random.nextInt(26);

				oneChar = strCp.substring(rd, rd + 1);
			} else {
				int rd = random.nextInt(10);
				oneChar = strNum.substring(rd, rd + 1);
			}

			int pd = random.nextInt(62);
			String sedChar = strAll.substring(pd, pd + 1);

			userName = new StringBuilder().append(userName).append(oneChar).toString();
			passWord = new StringBuilder().append(passWord).append(sedChar).toString();
		}
		return userName;
	}

	private String getCodeRequest(String code)
	{
		String result = null;
		this.GetCodeRequest = this.GetCodeRequest.replace("APPID",
				urlEnodeUTF8(AppConfig.WXAPP_ID));

		this.GetCodeRequest = this.GetCodeRequest.replace("SECRET",
				urlEnodeUTF8(AppConfig.WXAPP_SECRET));

		this.GetCodeRequest = this.GetCodeRequest.replace("CODE", urlEnodeUTF8(code));
		result = this.GetCodeRequest;
		return result;
	}

	private String getUserInfo(String access_token, String openid)
	{
		String result = null;
		this.GetUserInfo = this.GetUserInfo.replace("ACCESS_TOKEN",
				urlEnodeUTF8(access_token));

		this.GetUserInfo = this.GetUserInfo.replace("OPENID", urlEnodeUTF8(openid));
		result = this.GetUserInfo;
		return result;
	}

	private String urlEnodeUTF8(String str) {
		String result = str;
		try {
			result = URLEncoder.encode(str, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getHttpClient(String httpUrl)
	{
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(httpUrl);
			HttpResponse response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 200) {
				InputStream ins = response.getEntity().getContent();

				BufferedReader br = new BufferedReader(new InputStreamReader(ins, "utf-8"));

				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

				return sb.toString();
			}
		}
		catch (ClientProtocolException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}