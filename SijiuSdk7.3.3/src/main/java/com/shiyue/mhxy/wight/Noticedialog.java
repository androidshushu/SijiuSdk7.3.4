package com.shiyue.mhxy.wight;

import com.shiyue.mhxy.config.AppConfig;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.ImageView;
import android.widget.TextView;

public class Noticedialog extends Dialog implements
		android.view.View.OnClickListener {
	public Context mContext;
	public View mView;
	public WebView mWebvView;
	public NoticeListener mNoticeListener;
	public String mUrl;
	public ImageView mImageview;
	public TextView mTitle;
	public String tiele;

	public Noticedialog(Context context, int theme, String text,String title,
			NoticeListener listener) {
		super(context, theme);
		this.mContext = context;
		this.mUrl = text;
		this.mNoticeListener = listener;
		this.tiele = title;
		this.mView = LayoutInflater.from(context).inflate(
				AppConfig.resourceId(context, "sjnotice_dialog", "layout"),
				null);

		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		mNoticeListener.onClick(v);
	}

	public interface NoticeListener {
		public void onClick(View v);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	//	Log.i("kk","33");
		setContentView(mView);
		mImageview = (ImageView) findViewById(AppConfig.resourceId(mContext,
				"close", "id"));
		mWebvView = (WebView) findViewById(AppConfig.resourceId(mContext,
				"result_url", "id"));
		mWebvView.getSettings().setLayoutAlgorithm(
				LayoutAlgorithm.NARROW_COLUMNS);
		mWebvView.getSettings().setUseWideViewPort(true);
		WebSettings webSettings = mWebvView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		mWebvView.setBackgroundColor(0); // 设置背景色  
		
	//	mWebvView.loadDataWithBaseURL(null, mUrl, "text/html", "UTF-8", null);
		mWebvView.loadUrl(mUrl);
		mImageview.setOnClickListener(this);
		mTitle = (TextView)findViewById(AppConfig.resourceId(mContext,
				"sj_title", "id"));
		mTitle.setText(tiele);
	}

}
