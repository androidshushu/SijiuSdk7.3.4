package com.shiyue.mhxy.user;

import com.shiyue.mhxy.config.AppConfig;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserModifyDialog extends Dialog implements OnClickListener {

	private View mView;
	private TextView contentText,tv_usermima;
	private EditText edit_username,edit_password,edit_passwordto;
	private Context mContext;
	private String userName ,password;
	private Button button,button_e;
	private ResultListener mListener;

	public UserModifyDialog(Context context, int theme, String userName, String password,
			ResultListener listener) {
		super(context, theme);
		this.mContext = context;
		this.userName = userName;
		this.password = password;
		this.mListener = listener;
		this.mView = LayoutInflater.from(context).inflate(
				AppConfig.resourceId(context, "sjuser_modify", "layout"),
				null);

	}

	public interface ResultListener {
		public void onClick(View v);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(mView);
		button = (Button) findViewById(AppConfig.resourceId(mContext,
				"resultbutton", "id"));
		button_e = (Button) findViewById(AppConfig.resourceId(mContext,
				"resultbutton_e", "id"));
		contentText = (TextView) findViewById(AppConfig.resourceId(mContext,
				"tv_useryouke", "id"));
		tv_usermima = (TextView) findViewById(AppConfig.resourceId(mContext,
				"tv_usermima", "id"));
		edit_username = (EditText) findViewById(AppConfig.resourceId(mContext,
				"edit_username", "id"));
		edit_password = (EditText) findViewById(AppConfig.resourceId(mContext,
				"edit_password", "id"));
		edit_passwordto = (EditText) findViewById(AppConfig.resourceId(mContext,
				"edit_passwordto", "id"));
		contentText.setText("您的游客账号："+userName);
		tv_usermima.setText("初始密码："+password);
		button.setOnClickListener(this);
		button_e.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		mListener.onClick(v);
	}
	
	public String getusername(){
		return edit_username.getText().toString().trim();
	}

	public String getpassword(){
		return edit_password.getText().toString().trim();
	}
	
	public String getpasswordto(){
		return edit_passwordto.getText().toString().trim();
	}
}
