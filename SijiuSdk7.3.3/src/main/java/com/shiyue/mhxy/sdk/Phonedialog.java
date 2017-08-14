package com.shiyue.mhxy.sdk;

import com.shiyue.mhxy.config.AppConfig;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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
	private TextView tv_dilog_qq;
	private TextView tv_dilog_phone;

	public Phonedialog(Context mcontext, int theme, int view, String text,
			String from, Phonelistener mlistener) {
		super(mcontext, theme);
		// TODO Auto-generated constructor stub
		this.context = mcontext;
		this.mviews = view;
		this.listener = mlistener;
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
		tv_dilog_qq = (TextView) findViewById(AppConfig.resourceId(context,
				"tv_dilog_qq", "id"));
		tv_dilog_phone = (TextView) findViewById(AppConfig.resourceId(context,
				"tv_dilog_phone", "id"));
		String textString = text.substring(text.lastIndexOf("：")+1, text.length());
		String moblie = from.substring(from.lastIndexOf("：")+1, from.length());
		tv_dilog_qq.setText("客服QQ："+textString);
		tv_dilog_phone.setText("客服电话："+moblie);
//		if ("get_success".equals(from)) {
//			phoneButton.setText("复制");
//			dialogtext.setText(text);
//		} else if ("get_fail".equals(from)) {
//			phoneButton.setText("关闭");
//			dialogtext.setText(text);
//		} else if ("alipay".equals(from)) {
//			phoneButton.setText("关闭");
//			cancelButton.setVisibility(View.GONE);
//			dialogtext.setText(text);
//		} else if ("auto_login".equals(from)) {
//			dialogtext.setText(text);
//			phoneButton.setText("取消");
//			cancelButton.setVisibility(View.GONE);
//		}
		cancelButton.setOnClickListener(this);
		phoneButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		listener.onClick(v, text, from);
	}

//	public void setText(String text) {
//		dialogtext.setText(text);
//	}

	/**
	 * 设置按键不能点击
	 */
	public void setEnable(boolean t) {
		phoneButton.setEnabled(t);
		phoneButton.setClickable(t);
		phoneButton.setBackgroundDrawable(context.getResources().getDrawable(
				AppConfig.resourceId(context, "sjgraybtn_style", "drawable")));
	}

}
