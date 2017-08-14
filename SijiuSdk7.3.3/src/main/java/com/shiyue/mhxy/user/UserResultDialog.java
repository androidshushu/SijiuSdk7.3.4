package com.shiyue.mhxy.user;

import com.shiyue.mhxy.config.AppConfig;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class UserResultDialog extends Dialog implements OnClickListener {

	private View mView;
	private TextView contentText,tv_usermima;
	private Context mContext;
	private String userName ,password;
	private Button button;
	private UserResultListener mListener;

	public UserResultDialog(Context context, int theme, String userName, String password,
			UserResultListener listener) {
		super(context, theme);
		this.mContext = context;
		this.userName = userName;
		this.password = password;
		this.mListener = listener;
		this.mView = LayoutInflater.from(context).inflate(
				AppConfig.resourceId(context, "sjuser_result", "layout"),
				null);

	}

	public interface UserResultListener {
		public void onClick(View v);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(mView);
		button = (Button) findViewById(AppConfig.resourceId(mContext,
				"bt_user", "id"));
		contentText = (TextView) findViewById(AppConfig.resourceId(mContext,
				"tv_useryouke", "id"));
		tv_usermima = (TextView) findViewById(AppConfig.resourceId(mContext,
				"tv_userresult", "id"));
		contentText.setText("账号："+userName);
		tv_usermima.setText("密码："+password);
		button.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		mListener.onClick(v);
	}
	
}
