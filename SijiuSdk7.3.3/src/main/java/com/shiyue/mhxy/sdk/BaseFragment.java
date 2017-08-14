package com.shiyue.mhxy.sdk;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.support.v4.app.Fragment;
import android.widget.Toast;

@SuppressLint("NewApi")
public class BaseFragment extends Fragment {

	private static Toast mToast;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
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

	/**
	 * toast 提示信息
	 * 
	 * @param msg
	 */
	public void showMsg(String msg) {
		try {
			Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void showToast(Context context, String text, int duration) {  
        if (mToast == null) {  
            mToast = Toast.makeText(context, text, duration);  
        } else {  
            mToast.setText(text);  
//            mToast.setDuration(duration);  
        }  
  
        mToast.show();  
        }
	
}
