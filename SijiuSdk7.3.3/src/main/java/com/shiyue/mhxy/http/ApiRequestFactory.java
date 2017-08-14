package com.shiyue.mhxy.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.util.Log;

import com.shiyue.mhxy.config.AppConfig;
import com.shiyue.mhxy.utils.SecurityUtils;

import com.shiyue.mhxy.utils.rsa.SjDes3;

/**
 * 获取API请求内容 的工厂方法
 */
public class ApiRequestFactory {

	public static final boolean DEBUG = true;
	public static final String LOGTAG = "ApiRequestFactory";

	/**
	 *
	 * 获取Web Api HttpRequest
	 *
	 * @param url
	 * @param httpType
	 * @param entity
	 * @param config
	 * @return
	 */

	public static HttpUriRequest getRequest(String url, String httpType,
											HashMap<String, Object> param, String appKey) {
		// 得到hashmap的key集合，进行排序
		Object[] keyArr = param.keySet().toArray();
		Arrays.sort(keyArr);
		// http get
		if (httpType.equals("get")) {

//应该是构造加密前数据
			final ArrayList<NameValuePair> postParams = new ArrayList<NameValuePair>();
//准备传给服务端数据
			final ArrayList<NameValuePair> postParams2 = new ArrayList<NameValuePair>();
			StringBuilder md5Str = new StringBuilder();
			// StringBuilder des5Str = new StringBuilder();
			StringBuilder urlBuilderData = new StringBuilder();
			StringBuilder urlBuilder = new StringBuilder();
			for (Object key : keyArr) {
				String value = (String) param.get(key);
				try {
					// postParams.add(new BasicNameValuePair((String) key,
					// URLEncoder.encode(value, "UTF-8")));
					postParams.add(new BasicNameValuePair((String) key, value));
					urlBuilder.append(key).append("=")
							.append(URLEncoder.encode(value, "UTF-8"))
							.append("&");

					md5Str.append(value);

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

			}
			urlBuilder.append(appKey);
			// try {
			// urlBuilderData.append("appKey=").append(URLEncoder.encode(appKey,
			// "UTF-8")).append("&");
			// } catch (UnsupportedEncodingException e1) {
			// e1.printStackTrace();
			// }
			//SHA-1
			String md5ResultString = SecurityUtils.encryptToSHA(urlBuilder.toString());
			postParams.add(new BasicNameValuePair("sign", md5ResultString));
			// Log.i("kk", md5ResultString);
			urlBuilderData.append("sign=").append(md5ResultString);
			try {
				urlBuilder.append("sign").append("=")
						.append(URLEncoder.encode(md5ResultString, "UTF-8"));
			} catch (UnsupportedEncodingException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String data = null;
			try {
				//des3加密
				data = SjDes3.encode(urlBuilder.toString().trim());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			/*
			 * try { byte[] encryptData=RSAUtils.encryptByPublicKey(
			 * postParams.toString().getBytes("utf-8"), RSAUtils.getPublicKey())
			 * ; // //String s=new String(encryptData,"utf-8"); String
			 * s=Base64Utils.encode(encryptData);
			 * urlBuilder.append(Base64Utils.encode(encryptData));
			 * urlBuilder.append
			 * (URLEncoder.encode(Base64Utils.encode(encryptData)));
			 *
			 * } catch (UnsupportedEncodingException e) { // TODO Auto-generated
			 * catch block e.printStackTrace(); } catch (Exception e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */
//			postParams2.add(new BasicNameValuePair("_appId", AppConfig.appId
//					+ ""));
//			postParams2.add(new BasicNameValuePair("_datas", data));
//			postParams2.add(new BasicNameValuePair("_sign", "1"));
//			postParams2.add(new BasicNameValuePair("_device", "0"));
//			postParams2.add(new BasicNameValuePair("_type", "0"));
			HttpPost httpPost = new HttpPost(url);

			try {

				httpPost.setEntity(new UrlEncodedFormEntity(postParams,
						HTTP.UTF_8));
				Log.d("postParams=",postParams.toString());
				Log.d("postParams2=",postParams2.toString());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			// Log.i("kk", "urlBuilderData.toString()"+urlBuilder.toString());
			// Log.i("kk",
			// "postParams.toString()"+postParams.toString()+"appkey"+appKey);
			// Log.i("kk", " postParams2.toString()"+ postParams2.toString());
			// Log.i("kk", "data "+data);
			// Log.i("kk","secretKey"+SjDes3.secretKey);
			return httpPost;

		}

		// http post
		if (httpType.equals("post")) {
			// 请求参数
			final ArrayList<NameValuePair> postParams = new ArrayList<NameValuePair>();
			final ArrayList<NameValuePair> postParams2 = new ArrayList<NameValuePair>();
			StringBuilder md5Str = new StringBuilder();
			// StringBuilder des5Str = new StringBuilder();
			StringBuilder urlBuilderData = new StringBuilder();
			StringBuilder urlBuilder = new StringBuilder();
			for (Object key : keyArr) {
				String value = (String) param.get(key);
				try {
					// postParams.add(new BasicNameValuePair((String) key,
					// URLEncoder.encode(value, "UTF-8")));

					postParams.add(new BasicNameValuePair((String) key, value));
					urlBuilder.append(key).append("=")
							.append(URLEncoder.encode(value, "UTF-8"))
							.append("&");
					// urlBuilderData.append(key).append("=").append(URLEncoder.encode(value,
					// "UTF-8")).append("&");
					// // md5Str.append(URLEncoder.encode(value, "UTF-8"));
					//if (!key.equals("rolename") && !key.equals("level")) {
					md5Str.append(value);
					// Log.i("kk","value"+ value);
					// des5Str.append(value);
					//	}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

			}
			urlBuilder.deleteCharAt(urlBuilder.length()-1);
			urlBuilder.append(appKey);


			String md5ResultString = SecurityUtils.encryptToSHA(urlBuilder.toString());
			postParams.add(new BasicNameValuePair("sign", md5ResultString));
			// Log.i("kk", md5Str.toString());
			urlBuilderData.append("sign=").append(md5ResultString);
			try {
				urlBuilder.append("sign").append("=")
						.append(URLEncoder.encode(md5ResultString, "UTF-8"));
			} catch (UnsupportedEncodingException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String data = null;
			try {
				data = SjDes3.encode(urlBuilder.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			/*
			 * try { byte[] encryptData=RSAUtils.encryptByPublicKey(
			 * postParams.toString().getBytes("utf-8"), RSAUtils.getPublicKey())
			 * ; // //String s=new String(encryptData,"utf-8"); String
			 * s=Base64Utils.encode(encryptData);
			 * urlBuilder.append(Base64Utils.encode(encryptData));
			 * urlBuilder.append
			 * (URLEncoder.encode(Base64Utils.encode(encryptData)));
			 *
			 * } catch (UnsupportedEncodingException e) { // TODO Auto-generated
			 * catch block e.printStackTrace(); } catch (Exception e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */
//			postParams2.add(new BasicNameValuePair("_appId", AppConfig.appId
//					+ ""));
//			postParams2.add(new BasicNameValuePair("_datas", data));
//			postParams2.add(new BasicNameValuePair("_sign", "1"));
//			postParams2.add(new BasicNameValuePair("_device", "0"));
//			postParams2.add(new BasicNameValuePair("_type", "1"));
			HttpPost httpPost = new HttpPost(url);

			try {

				httpPost.setEntity(new UrlEncodedFormEntity(postParams,
						HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if(AppConfig.isTest){

				Log.d("shiyuepostParams=",postParams.toString());

				Log.d("shiyue", "urlBuilderData.toString()"+urlBuilder.toString());
			}

			//Log.i("kk", "postParams.toString()"+postParams.toString()+"appkey"+appKey);
			// Log.i("kk", " postParams2.toString()"+ postParams2.toString());
			return httpPost;
		}

		return null;

	}
}
