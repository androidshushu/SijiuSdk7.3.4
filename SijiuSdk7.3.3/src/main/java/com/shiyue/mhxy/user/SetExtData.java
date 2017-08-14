package com.shiyue.mhxy.user;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.shiyue.mhxy.config.AppConfig;
import com.shiyue.mhxy.http.ApiAsyncTask;
import com.shiyue.mhxy.http.ApiRequestListener;
import com.shiyue.mhxy.sdk.SiJiuSDK;

import java.util.HashMap;


public class SetExtData {


	private Context context;

	private ApiAsyncTask setRoleInfo;
    private HashMap hashMap;
	private int type = 1;
	private final static int INIT_SUCCESS = 38;



	public SetExtData(Context context, HashMap hs,int type) {
		this.context = context;
        this.hashMap=hs;
		this.type=type;

         sendRoleInfo(context,hs,type);
	}






	/**
	 * http请求，初始化接口
	 */
	public void sendRoleInfo(Context context,HashMap hs,int type) {



		setRoleInfo = SiJiuSDK.get().setRoleinfo(context, AppConfig.appId,
				AppConfig.appKey, AppConfig.ver_id,hs,type, new ApiRequestListener() {
					@Override
					public void onSuccess(Object obj) {
						Log.d("kk",obj+"");
						if (obj != null) {
							ResultAndMessage result = (ResultAndMessage) obj;
							if(AppConfig.isTest){

								Log.d("shiyue_setextdata",result.getMessage()+"");
							}


						}
						else {

							sendData(AppConfig.FLAG_FAIL, "获取数据失败!", handler);
						}
					}

					@Override
					public void onError(int statusCode) {
						// TODO Auto-generated method stub
						sendData(AppConfig.FLAG_REQUEST_ERROR, "网络连接失败，请检查您的网络连接!",
								handler);
					}
				});
	}


	/**
	 * 接口返回数据处理
	 */
	public void sendData(int num, Object data, Handler callback) {
		Message msg = callback.obtainMessage();
		msg.what = num;
		msg.obj = data;
		msg.sendToTarget();
	}


	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

				case AppConfig.FLAG_REQUEST_ERROR:

					Toast.makeText(context, (String) msg.obj, Toast.LENGTH_SHORT)
							.show();
							break;

			}
		}
	};

	/**
	 * 赋值初始化信息
	 */



}
