package com.shiyue.mhxy.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;

import com.shiyue.mhxy.config.WebApi;

import android.util.Log;

/**
 * API 响应结果解析工厂类，所有的API响应结果解析需要在此完成。
 * 
 */
public class ApiResponseFactory {

	public static final boolean DEBUG = false;
	public static final String LOGTAG = "ApiResponseFactory";

	/**
	 * 处理response
	 * 
	 * 
	 * @param
	 * @param response
	 * @return
	 */
	public static Object handleResponse(String webApi, HttpResponse response) {

		String data = inputStreamToString(HttpUtils
				.getInputStreamResponse(response));
		data = clearBom(data);

		Object result = null;

		try {
			// Log.i ("kk", "data"+data);
			if (webApi.equals(WebApi.ACTION_INIT)) {
				// Log.i ("kk", "data"+data);
				result = JSONParse.parseInitMsg(data);
			} else if (webApi.equals(WebApi.ACTION_LOGON)) {
				result = JSONParse.parseLogon(data);
				 Log.i ("kk", "data"+data);
			} else if (webApi.equals(WebApi.ACTION_PH_LOGON)) {
				result = JSONParse.parsePhLogon(data);
				Log.i ("kk", "data"+data);
			}else if (webApi.equals(WebApi.ACTION_TICKLOGON)) {
				result = JSONParse.parsePhLogon(data);
				Log.i ("kk", "data"+data);
			} else if (webApi.equals(WebApi.ACTION_CHECKSMS)) {
				result = JSONParse.parseResultAndMessage(data);
				Log.i ("kk", "data"+data);
			}else if (webApi.equals(WebApi.ACTION_GETCODEFPWD)) {
				result = JSONParse.parseResultAndMessage(data);
				Log.i ("kk", "data"+data);
			} else if (webApi.equals(WebApi.ACTION_REGISTER)) {
				result = JSONParse.parseResultAndMessage(data);

			}   else if (webApi.equals(WebApi.ACTION_SETEXTDATA)) {
			   result = JSONParse.parseResultAndMessage(data);

		     } else if (webApi.equals(WebApi.ACTION_PH_REGIST)) {
				result = JSONParse.parseResultAndMessage(data);

			} else if (webApi.equals(WebApi.ACTION_FINDPWDINFO)) {
				result = JSONParse.parseAccountInfo(data);
			} else if (webApi.equals(WebApi.ACTION_CHANGEPASSWORD)) {
				result = JSONParse.parseResultAndMessage(data);
			} else if (webApi.equals(WebApi.ACTION_GETCODEBOUNDPHONE)) {
				result = JSONParse.parseResultAndMessage(data);
			}
			else if (webApi.equals(WebApi.ACTION_THIRDREGIST)) {
				result = JSONParse.parseResultAndMessage(data);
			}
			else if (webApi.equals(WebApi.ACTION_THIRDQUERY)) {
				result = JSONParse.parseOauthInfo(data);
			}else if (webApi.equals(WebApi.ACTION_BIND_PHONE)){
				result = JSONParse.parseBindPhoneMessage(data);
				Log.d("shushushu12","data="+result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	private static String inputStreamToString(InputStream in) {

		try {
			StringBuilder builder = new StringBuilder();
			BufferedReader bufferedReader2 = new BufferedReader(
					new InputStreamReader(in));

			for (String s = bufferedReader2.readLine(); s != null; s = bufferedReader2
					.readLine()) {
				builder.append(s);
			}

			return builder.toString();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return "";
	}

	private static String clearBom(String data) {
		if (data.startsWith("\ufeff")) {
			return data.substring(1);
		}
		return data;
	}

}
