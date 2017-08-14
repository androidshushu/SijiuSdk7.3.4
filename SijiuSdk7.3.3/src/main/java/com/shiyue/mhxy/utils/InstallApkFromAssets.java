package com.shiyue.mhxy.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;

import com.shiyue.mhxy.config.AppConfig;

public class InstallApkFromAssets {
	private Context context;

	public InstallApkFromAssets(Context context) {
		this.context = context;
	}

	/**
	 * install apk from assets
	 */
	public void installApk() {
		boolean isMobile_spExist = isMobile_spExist();
		if (!isMobile_spExist) {
			File cacheDir = context.getCacheDir();
			final String cachePath = cacheDir.getAbsolutePath() + "/temp.apk";
			// 捆绑安装
			retrieveApkFromAssets(context, AppConfig.SJAPP_NAME, cachePath);
		}
	}

	/**
	 * 遍历程序列表，判断是否安装应用
	 * 
	 * @return
	 */
	public boolean isMobile_spExist() {
		PackageManager manager = context.getPackageManager();
		List<PackageInfo> pkgList = manager.getInstalledPackages(0);
		for (int i = 0; i < pkgList.size(); i++) {
			PackageInfo pI = pkgList.get(i);
			if (pI.packageName.equalsIgnoreCase("com.sijiu.gamebox"))
				return true;
		}
		return false;
	}

	/**
	 * 安装assets文件夹下的apk
	 * 
	 * @param context
	 *            上下文环境
	 * @param fileName
	 *            apk名称
	 * @param path
	 *            安装路径
	 * @return
	 */
	public boolean retrieveApkFromAssets(Context context, String fileName,
			String path) {
		boolean bRet = false;

		try {
			InputStream is = context.getAssets().open(fileName);

			File file = new File(path);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);

			byte[] temp = new byte[1024];
			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}

			fos.close();
			is.close();
			bRet = true;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return bRet;
	}

	public void installApp() {
		File cacheDir = context.getCacheDir();
		final String cachePath = cacheDir.getAbsolutePath() + "/temp.apk";
		chmod("777", cachePath);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.parse("file://" + cachePath),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * 获取权限
	 * 
	 * @param permission
	 *            权限
	 * @param path
	 *            路径
	 */
	public void chmod(String permission, String path) {
		try {
			String command = "chmod " + permission + " " + path;
			Runtime runtime = Runtime.getRuntime();
			runtime.exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void copyGameBoxApk(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				File dir = new File(Environment.getExternalStorageDirectory(), "49app");
				if(!dir.exists()){
					dir.mkdir();
				}
				File apk = new File(dir, "49gamebox.apk");
				//if(!apk.exists()){
					retrieveApkFromAssets(context, "49gamebox.apk", apk.getAbsolutePath());
				//}
			}
		}).start();
	}
	
	public void openOrInstallGameBox(){
		if(isMobile_spExist()){
//			Intent intent = context.getPackageManager()
//                    .getLaunchIntentForPackage("com.sijiu.gamebox");
//            context.startActivity(intent);
//			Intent intent = new Intent("com.sijiu.gamebox.sdk.open.gift");
//			intent.putExtra("game_id", AppConfig.gameId);
//			context.sendBroadcast(intent);
			try {
				Intent intent = new Intent("sdk_open_gift");
				intent.setClassName("com.sijiu.gamebox",
						"com.sijiu.gamebox.activity.MainActivity");
				context.startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
				installGameBox();
			}
		}else{
			installGameBox();
		}
	}
	
	public void installGameBox(){
		File dir = new File(Environment.getExternalStorageDirectory(), "49app");
		File file = new File(dir, "49gamebox.apk");
		if(file.exists()){
			Intent intent = new Intent(Intent.ACTION_VIEW);
	        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        intent.setDataAndType(Uri.fromFile(file),
	                "application/vnd.android.package-archive");
	        context.startActivity(intent);
		}
	}

}
