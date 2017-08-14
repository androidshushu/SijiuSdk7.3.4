package com.shiyue.mhxy.user;

import com.shiyue.mhxy.config.AppConfig;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class WecomeBackToast extends Toast {

	Context context;
	View mNextView;
	int mDuration;

	public WecomeBackToast(Context context) {
		super(context);
		this.context = context;

	}

	@Override
	public void setView(View view) {
		// TODO Auto-generated method stub
		super.setView(view);
	}

	public void makeText(CharSequence text, int duration) {
		View v = LayoutInflater.from(context).inflate(
				AppConfig.resourceId(context, "sjmytoast", "layout"), null);
		TextView tv = (TextView) v.findViewById(AppConfig.resourceId(context,
				"name", "id"));
		tv.setText(text);
		setView(v);
		setDuration(duration);
	}

}
