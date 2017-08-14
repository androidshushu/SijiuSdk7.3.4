package com.shiyue.mhxy.user;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiyue.mhxy.config.AppConfig;
import com.shiyue.mhxy.wight.Noticedialog.NoticeListener;

public class NoticeActivity extends Activity{
	//public View mView;
	public WebView mWebvView;
	public NoticeListener mNoticeListener;
	public String mUrl;
	public ImageView mImageview;
	public String title;
	public TextView mTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(AppConfig.resourceId(this, "sjnotice_main", "layout"));
		mUrl = this.getIntent().getStringExtra("url");
		title= this.getIntent().getStringExtra("title");
		mImageview = (ImageView) findViewById(AppConfig.resourceId(this,
				"close", "id"));
		mWebvView = (WebView) findViewById(AppConfig.resourceId(this,
				"result_url", "id"));
		mWebvView.getSettings().setLayoutAlgorithm(
				LayoutAlgorithm.NARROW_COLUMNS);
		mWebvView.getSettings().setUseWideViewPort(true);
		WebSettings webSettings = mWebvView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		mWebvView.setBackgroundColor(0);
	//	mWebvView.loadDataWithBaseURL(null, mUrl, "text/html", "UTF-8", null);
		mWebvView.loadUrl(mUrl);
		mTitle = (TextView)findViewById(AppConfig.resourceId(this,
				"sj_title", "id"));
		mTitle.setText(title);
		mImageview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	

}
