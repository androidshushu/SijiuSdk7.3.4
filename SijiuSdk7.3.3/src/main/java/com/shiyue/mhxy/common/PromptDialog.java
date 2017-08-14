package com.shiyue.mhxy.common;

import com.shiyue.mhxy.config.AppConfig;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PromptDialog extends Dialog implements OnClickListener {

	private View mView;
	private TextView contentText;
	private Context mContext;
	private String mText;
	private Button button,button_e;
	private PromptListener mListener;

	public PromptDialog(Context context, int theme, String text,
			PromptListener listener) {
		super(context, theme);
		this.mContext = context;
		this.mText = text;
		this.mListener = listener;
		this.mView = LayoutInflater.from(context).inflate(
				AppConfig.resourceId(context, "sjrecharge_prompt", "layout"),
				null);

	}

	public interface PromptListener {
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
				"result", "id"));
		contentText.setText(Html.fromHtml(mText));
		button.setOnClickListener(this);
		button_e.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		mListener.onClick(v);
	}

}
