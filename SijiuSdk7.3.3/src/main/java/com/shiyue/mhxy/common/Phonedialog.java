package com.shiyue.mhxy.common;

import com.shiyue.mhxy.config.AppConfig;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class Phonedialog extends Dialog implements OnClickListener {

	private Context context;
	private int mviews;
	private Button cancelButton, phoneButton;
	private Phonelistener listener;
	private String text = " ", from = "";
	private TextView dialogtext;
	private boolean flag = true;
	private final int AUTO_LOGIN = 500;
	private int q = 1;
	public Phonedialog(Context mcontext, int theme, int view, String text,
			String from, Phonelistener phonelistener) {
		super(mcontext, theme);
		// TODO Auto-generated constructor stub
		this.context = mcontext;
		this.mviews = view;
		this.listener = phonelistener;
		this.text = text;
		this.from = from;
	}

	public interface Phonelistener {
		public void onClick(View view, String text, String from);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(mviews);

		cancelButton = (Button) findViewById(AppConfig.resourceId(context,
				"dialog_cancel", "id"));
		phoneButton = (Button) findViewById(AppConfig.resourceId(context,
				"dialog_phone", "id"));
		dialogtext = (TextView) findViewById(AppConfig.resourceId(context,
				"tv_dilog_qq", "id"));
		if ("get_success".equals(from)) {
			phoneButton.setText("复制");
			dialogtext.setText(text);
		} else if ("get_fail".equals(from)) {
			phoneButton.setText("关闭");
			dialogtext.setText(text);
		} else if ("del_userinfo".equals(from)) {
			phoneButton.setText("确定");
			dialogtext.setText(text);
		}else if("saveaccount".equals(from)){
			phoneButton.setText("保存");
			dialogtext.setText(text);
		}
		else if ("alipay".equals(from)) {
			phoneButton.setText("关闭");
			cancelButton.setVisibility(View.GONE);
			dialogtext.setText(text);
		} else if ("auto_login".equals(from)) {

			// 自动登录线程
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					while (flag) {
						myHandler.sendEmptyMessage(AUTO_LOGIN);
						try {
							Thread.sleep(900);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
		cancelButton.setOnClickListener(this);
		phoneButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		listener.onClick(v, text, from);
	}

	public void setText(String text) {
		dialogtext.setText(text);
	}

	/**
	 * 设置按键不能点击
	 */
	public void setEnable(boolean t) {
		phoneButton.setEnabled(t);
		phoneButton.setClickable(t);
		phoneButton.setBackgroundDrawable(context.getResources().getDrawable(
				AppConfig.resourceId(context, "sjgraybtn_style", "drawable")));
	}

	private Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case AUTO_LOGIN:
//					dialogtext.setText(text);
//			phoneButton.setText("取消");
			cancelButton.setVisibility(View.GONE);
					if (q >= 0) {
						phoneButton.setText("(切换账号" + q + "s)");
					} else {
//					findViewById(AppConfig.resourceId(
//							LoginActivity.this, "linear", "id")).setVisibility(View.GONE);
//				//pdialog.setEnable(false);
////					pdialog.setText("49you帐号 jjjyyyy");
//				myHandler.sendEmptyMessage(FLAG_AUTO_LOGIN_HTTP);
						flag = false;
					}

					q--;
					break;
			}}
	};
}
