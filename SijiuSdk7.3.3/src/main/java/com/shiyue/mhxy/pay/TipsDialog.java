package com.shiyue.mhxy.pay;

import com.shiyue.mhxy.config.AppConfig;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class TipsDialog extends Dialog {

	private View mView;
	private TextView mtext;
	private Context mcontext;
	private DialogListener listener;

	public interface DialogListener {
		public void onClick();
	}

	public TipsDialog(Context context, int theme, DialogListener listener) {
		super(context, theme);
		this.listener = listener;
		this.mView = LayoutInflater.from(context).inflate(
				AppConfig.resourceId(context, "sjmyprogressbar", "layout"), null);
		this.mcontext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(mView);
		mtext = (TextView) findViewById(AppConfig.resourceId(mcontext, "text",
				"id"));
	}

	public void setText(String text) {
		mtext.setText(text);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			listener.onClick();
		}
		return super.onKeyDown(keyCode, event);
	}
}
