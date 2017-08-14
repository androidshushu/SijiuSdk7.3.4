package com.shiyue.mhxy.pay;

import android.app.Activity;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.shiyue.mhxy.config.AppConfig;
import com.shiyue.mhxy.config.WebApi;
import com.shiyue.mhxy.utils.SecurityUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;


public class Sy_PaymentActivity extends Activity implements OnClickListener {


	private WebView web_pay;
	private String sign;
	private HashMap hs;
	private HashMap params;
	public SjyxPaymentInfo payInfo;
	private String urlparams;
	private ImageView tv_back;
	private TipsDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(AppConfig.resourceId(this, "sy_pay", "layout"));

		init();
		dialog = new TipsDialog(this, AppConfig.resourceId(this, "Sj_MyDialog", "style"), new TipsDialog.DialogListener()
		{
			public void onClick()
			{
			}
		});
		this.dialog.setCancelable(true);
		sign = getSign(hs, AppConfig.appKey);
		params.put("sign", sign);
		urlparams = qureyStringWithparam(params);
		payView(WebApi.ACTION_PAY + urlparams);

	}




	private void init() {
		web_pay=new WebView(this);
		web_pay = (WebView) findViewById(AppConfig.resourceId(this, "web_pay", "id"));
		tv_back= (ImageView) findViewById(AppConfig.resourceId(this, "iv_close", "id"));
	    payInfo = getIntent().getParcelableExtra("sj_pay_info");
         tv_back.setOnClickListener(this);

	     hs = new HashMap<String, Object>();
		params= new HashMap<String, Object>();

		hs.put("account_id", payInfo.getAccounId()+ "");

		hs.put("amount", payInfo.getAmount() + "");

		if(payInfo.getAppId()==0) {

			hs.put("app_id", AppConfig.appId+ "");
		}else{
			hs.put("app_id", payInfo.getAppId()+ "");
		}

			hs.put("channel_id", AppConfig.ver_id+ "");


		hs.put("bundle_ver", "9.9.9"+ "");
		hs.put("dev_str", AppConfig.imei+ "");
		hs.put("exdata", payInfo.getExtraInfo()+ "");
		hs.put("notify_url", payInfo.getNotifyUrl()+ "");
		hs.put("product_id", payInfo.getProductId()+ "");
		hs.put("srv_id", payInfo.getServerId()+ "");
		hs.put("role_id", payInfo.getRoleid()+ "");
		hs.put("subject", payInfo.getSubject()+ "");
		hs.put("ts", System.currentTimeMillis()/1000 + "");
		hs.put("ver", "1.0.0"+ "");
    if(AppConfig.isTest){

	 Log.d("shiyue_payparams",hs.toString());
    }
            params=hs;
	}

	private void payView(String url) {

		web_pay.loadUrl(url);
		WebSettings webSettings=web_pay.getSettings();
		webSettings.setJavaScriptEnabled(true);
		web_pay.addJavascriptInterface(new AndroidJS(), "androidjs");
//覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网
		// 页用WebView打开
		this.web_pay.setWebChromeClient(new WebChromeClient()
		{
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
					if (Sy_PaymentActivity.this.dialog != null)
						Sy_PaymentActivity.this.dialog.dismiss();
				}
				else {
					Sy_PaymentActivity.this.dialog.show();
				}
				super.onProgressChanged(view, newProgress);
			}
		});
		this.web_pay.setWebViewClient(new WebViewClient()
		{
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}

			public void onPageFinished(WebView view, String url)
			{
				super.onPageFinished(view, url);
			}

//		web_pay.setWebViewClient(new WebViewClient(){
//			@Override
//			public void onPageStarted(WebView view, String url, Bitmap favicon) {
//				super.onPageStarted(view, url, favicon);
//				dialog=new TipsDialog(Sy_PaymentActivity.this, AppConfig.resourceId(Sy_PaymentActivity.this, "Sj_MyDialog", "style"), new  TipsDialog.DialogListener(){
//
//					@Override
//					public void onClick() {
//
//					}
//
//				});
//				dialog.show();
//				dialog.setCancelable(true);
//			}
//
//			@Override
//			public void onPageFinished(WebView view, String url) {
//				super.onPageFinished(view, url);
//				if(dialog!=null){
//					dialog.dismiss();
//				}
//
//			}

			@Override
			public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

				return super.shouldInterceptRequest(view, url);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				if(AppConfig.isTest){
//					Log.e("shiyue_payUrl", "访问的url地址：" + url);
				}

				if(url.startsWith("weixin://wap/pay?")){
					try {
					Intent intent =new Intent();
					intent.setAction(Intent.ACTION_VIEW);
					intent.setData(Uri.parse(url));
                           startActivity(intent);

					}catch (ActivityNotFoundException e){
						view.loadUrl(url);
						Toast.makeText(Sy_PaymentActivity.this,"请安装最新版本微信！",Toast.LENGTH_SHORT).show();
					}
				}
//				else if (parseScheme(url)) {
//					try {
//						Intent intent;
//						intent = Intent.parseUri(url,
//								Intent.URI_INTENT_SCHEME);
//						intent.addCategory("android.intent.category.BROWSABLE");
//						intent.setComponent(null);
//						// intent.setSelector(null);
//						startActivity(intent);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
				else {
					view.loadUrl(url);
				}
				return true;
			}


		});


	}

//	public boolean parseScheme(String url) {
//		if (url.contains("platformapi/startapp")) {
//
//			return true;
//		} else if ((Build.VERSION.SDK_INT >=Build.VERSION_CODES.BASE)
//				&& (url.contains("platformapi") && url.contains("startapp"))) {
//			return true;
//		} else {
//			return false;
//		}
//	}
	@Override
	public void onClick(View v) {
		if (v.getId() == AppConfig.resourceId(this, "iv_close", "id")) {
			sendData(1, "", myHandler);
		}

	}

	private String  getSign(HashMap<String, Object> param,String key) {
		String sort=qureyStringWithparam(param);
		return SecurityUtils.encryptToSHA(sort+key);
	}
	// 得到hashmap的key集合，进行排序拼接
	private String qureyStringWithparam(HashMap<String, Object> param) {

		Object[] keyArr = param.keySet().toArray();
		Arrays.sort(keyArr);
		StringBuilder urlBuilder = new StringBuilder();
		for (Object key : keyArr) {
			String value = (String) param.get(key);
			try {
//				postParams.add(new BasicNameValuePair((String) key, value));
				urlBuilder.append(key).append("=")
						.append(URLEncoder.encode(value, "UTF-8"))
						.append("&");

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		}
		urlBuilder.deleteCharAt(urlBuilder.length()-1);

		return  urlBuilder.toString();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && web_pay.canGoBack()) {
			web_pay.goBack();// 返回前一个页面
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(web_pay!=null){
			web_pay.stopLoading();
			web_pay.removeAllViews();
			web_pay.destroy();
			web_pay = null;
		}
		if(dialog!=null){
			dialog.dismiss();
		}

	}

	public class AndroidJS {
		@JavascriptInterface
		public void messages(String message) {
			try {
				JSONObject json = new JSONObject(message);
				if(AppConfig.isTest){
					Log.d("shiyue_pay_datafromjs", json.toString());
				}

				String action=json.getString("action");
				if (action.equals("GoBack")){
					sendData(1, message, myHandler);

				}


			} catch (JSONException e) {
				e.printStackTrace();
			}


		}
	}
	private Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
				case 1:
			     Sy_PaymentActivity.this.finish();
					break;

			}

		}
	};
	public void sendData(int num, Object data, Handler callback) {
		Message msg = callback.obtainMessage();
		msg.what = num;
		msg.obj = data;
		msg.sendToTarget();
		//	Log.i("giftnews", "I am a sendData(接口返回数据处理)");
	}
}
