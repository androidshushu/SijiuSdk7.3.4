package com.shiyue.mhxy.sdk;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.shiyue.mhxy.config.AppConfig;
import com.shiyue.mhxy.wight.RoundProgress;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * 传递一个apkurl,进行更新 .code:startActivity(new Intent(this,
 * TestActivity.class).putExtra("apk_url", value));
 * 
 */
public class UpdateActivity extends Activity {

	private static String LOGTAG = "update activity";
	private static RoundProgress progressView = null;
	String downloadDir;
	static String fileAbsDir;// 下载文件的绝对路径

	static String fileDir;
	static String fileName;
	static String apkUrl;
	UpdateTask task = null;
	static UpdateActivity instance = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
		setContentView(getResources().getIdentifier("sjupdate_dialog_land", "layout", getPackageName()));
		progressView = (RoundProgress) findViewById(getResources().getIdentifier("progress", "id", getPackageName()));
		instance = this;
		//适配4.1系统以上，退出activity系统突然destroy activity
		if(savedInstanceState != null){
			task=(UpdateTask)getLastNonConfigurationInstance();
			task.attach(this);
			progressView.setProgress(task.getProgress());
		}
		if(AppConfig.isDownload){			
			apkUrl = getIntent().getStringExtra("apk_url");
			if (TextUtils.isEmpty(apkUrl)) {
	
				//apkUrl = "http://bs.baidu.com/appstore/apk_D7A3B1D87CE4AA45B35B15278AE3A2C1.apk";
			}	
			if (!Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState())) {
				Toast.makeText(this, "外置存储卡不存在，下载功能无法使用", Toast.LENGTH_LONG).show();
				finish();
			} else {
				// downloadDir = getCacheDir() + "/update";
				downloadDir = "/sdcard/update";
				fileDir = downloadDir + "/";
				fileAbsDir = downloadDir + "/"
						+ apkUrl.substring(apkUrl.lastIndexOf("/") + 1);
				fileName = apkUrl.substring(apkUrl.lastIndexOf("/") + 1);
				//new UpdateTask(this).execute();
				task = new UpdateTask(this);
				task.execute();
			}
		}

	}
	

	/**
	 * 保存对象
	 */
	@Override
	public Object onRetainNonConfigurationInstance() {
		task.detach();		
		return task;
	}


	static class UpdateTask extends AsyncTask<String, Integer, Boolean> {

//		private Context context;
		private UpdateActivity activity = null;
		private int progress=0;
		
		public UpdateTask(UpdateActivity activity){
			this.activity = activity;
		}

//		public UpdateTask(Context context) {
//			this.context = context;
//		}
		
		protected void detach() {
	    	activity = null;
	    }
		
		protected int getProgress() {
	    	return progress;
	    }
		
		protected void attach(UpdateActivity activity) {
	    	this.activity = activity;
	    }

		@Override
		protected void onCancelled() {

			super.onCancelled();
		}

		@Override
		protected void onPreExecute() {
			
			AppConfig.isDownload = false;
			// 创建目录
			File dir = new File(fileDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			// 删掉原文件
			File apkFile = new File(fileDir, fileName);
			if (apkFile.exists()) {

				apkFile.delete();
			}

			try {
				apkFile.createNewFile();
			} catch (IOException e) {

				e.printStackTrace();
			}
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(String... params) {

			HttpURLConnection connection = null;
			RandomAccessFile randomFile = null;
			InputStream is = null;
			File file = new File(fileDir, fileName);

			URL mUrl;
			long totalBytes = 0;
			try {
				mUrl = new URL(apkUrl);
				connection = (HttpURLConnection) mUrl.openConnection();
				connection.setConnectTimeout(5000);
				connection.setRequestMethod("GET");

				totalBytes = connection.getContentLength();
				randomFile = new RandomAccessFile(file, "rwd");
				randomFile.seek(0);
				is = connection.getInputStream();
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			long currentBytes = 0;
			try {

				byte[] buffer = new byte[4096];

				int count = 0;
				// 当前百分比
				int percent = 0;// 进度条
				int tempPercent = 0;//
				while ((count = is.read(buffer)) != -1) {
					randomFile.write(buffer, 0, count);
					currentBytes += count;

					// 计算百分比并更新ui
					tempPercent = (int) (currentBytes * 100 / totalBytes);
					if (tempPercent != percent) {
						percent = tempPercent;
						progress = percent;
						publishProgress(percent);
						Log.v(LOGTAG, "percent:" + percent);
					}

				}
				connection.disconnect();
				randomFile.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (currentBytes == totalBytes && totalBytes != 0) {
				// 下载成功完成
				return true;
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {

			if (result) {
				installApk(activity, fileAbsDir);
			}

			// 关闭
			//UpdateActivity.this.finish();
			UpdateActivity.instance.finish();
			AppConfig.isDownload = true;
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			progressView.setProgress(values[0]);
		}

	}
	
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}



	/**
	 * 普通安装
	 * 
	 * @param file
	 * @return
	 */
	public static boolean installApk(Context context, String path) {

		File file = new File(path);

		// 通过Intent安装APK文件
		Intent updateIntent = new Intent(Intent.ACTION_VIEW);
		updateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		updateIntent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		context.startActivity(updateIntent);
		return true;
	}
}
