package com.shiyue.mhxy.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeSet;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

public class CrashHandle implements UncaughtExceptionHandler {

	public static final String TAG = "CrashHandle";
	// 系统默认的UncaughtException处理类
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	private static CrashHandle instance = new CrashHandle();
	private Context mcontext;
	/* 使用Properties 来保存设备的信息和错误堆栈信息 */
	private Properties mDeviceCrashInfo = new Properties();
	// 存储设备信息
	private Map<String, String> mDevInfos = new HashMap<String, String>();
	private SimpleDateFormat formatdate = new SimpleDateFormat(
			"yyyy-MM-dd-HH-mm-ss");

	private CrashHandle() {

	}// 保证只有一个实例

	public static CrashHandle getInstance() {
		if (instance == null)
			instance = new CrashHandle();
		return instance;
	}

	public void init(Context context) {
		mcontext = context;
		// 获得默认的handle
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 重新设置handle
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	public void uncaughtException(Thread thread, Throwable ex) {
		// TODO Auto-generated method stub
		// 如果用户没有处理则让系统默认的异常处理器来处理
		if (!handleException(ex) && mDefaultHandler != null) {
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Log.e(TAG, "error");
			}
			// 结束程序
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}

	// Throwable 包含了其线程创建时线程执行堆栈的快照
	private boolean handleException(Throwable ex) {
		// TODO Auto-generated method stub
		if (ex == null) {
			return false;
		}
		final String msg = ex.getLocalizedMessage();
		new Thread() {
			public void run() {
				Looper.prepare();
		//	Toast.makeText(mcontext, "msg" + msg, Toast.LENGTH_LONG).show();
				Looper.loop();
			}
		}.start();
		// 收集设备信息
		collectDeviceInfo(mcontext);
		// 保存信息
		saveCrashInfo2File(ex);
		// 发送错误报告
		sendCrashReportsToServer(mcontext);
		return true;
	}

	public void sendPreviousReportsToServer() {
		sendCrashReportsToServer(mcontext);
	}

	private void sendCrashReportsToServer(Context mcontext) {
		String[] crFiles = getCrashReportFiles(mcontext);
		if (crFiles != null && crFiles.length > 0) {
			TreeSet<String> sortedFiles = new TreeSet<String>();
			sortedFiles.addAll(Arrays.asList(crFiles));
			for (String fileName : sortedFiles) {
				File cr = new File(mcontext.getFilesDir(), fileName);
				postReport(cr);
				// cr.delete();
			}
		}
	}

	private void postReport(File cr) {
		// TODO 使用HTTP Post 发送错误报告到服务器
	}

	private static final String CRASH_REPORTER_EXTENSION = ".cr";

	private String[] getCrashReportFiles(Context mcontext) {
		File filesDir = mcontext.getFilesDir();
		FilenameFilter filter = new FilenameFilter() {

			public boolean accept(File dir, String filename) {
				// TODO Auto-generated method stub
				return filename.endsWith(CRASH_REPORTER_EXTENSION);
			}
		};
		return filesDir.list(filter);
	}

	private String saveCrashInfo2File(Throwable ex) {
		// TODO Auto-generated method stub
		StringBuffer buffer = new StringBuffer();
		for (Map.Entry<String, String> entry : mDevInfos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			buffer.append(key + "=" + value + '\n');
		}
		// 可以用其回收在字符串缓冲区中的输出来构造字符串
		Writer writer = new StringWriter();
		// 向文本输出流打印对象的格式化表示形式
		PrintWriter printWriter = new PrintWriter(writer);
		// 将此 throwable 及其追踪输出至标准错误流
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			// 异常链
			cause.printStackTrace();
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		buffer.append(result);
		try {
			long timestamp = System.currentTimeMillis();
			String time = formatdate.format(new Date(timestamp));

			String fileName = "crash-" + time + "-" + timestamp + ".log";
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				String path = "/sdcard/crash/";
				File file = new File(path);
				if (!file.exists()) {
					file.mkdirs();
				}
				FileOutputStream trace = mcontext.openFileOutput(path
						+ fileName, Context.MODE_PRIVATE);
				mDeviceCrashInfo.store(trace, "");
				trace.flush();
				trace.close();
				// output 针对内存来说的 output到file中
				FileOutputStream fos = new FileOutputStream(path + fileName);
				fos.write(buffer.toString().getBytes());
				fos.flush();
				fos.close();
			}
			return fileName;
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing file...", e);
		}
		return null;
	}

	private void collectDeviceInfo(Context ctx) {

		// TODO Auto-generated method stub
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null"
						: pi.versionName;
				String versionCode = pi.versionCode + "";
				mDevInfos.put("versionName", versionName);
				mDevInfos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			Log.v(TAG, "NameNotFoundException");
		}
		// 使用反射 获得Build类的所有类里的变量
		// Class 代表类在运行时的一个映射
		// 在Build类中包含各种设备信息,
		// 例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
		// 具体信息请参考后面的截图

		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				// get方法返回指定对象上此 Field 表示的字段的值
				mDevInfos.put(field.getName(), field.get(null).toString());
				Log.v(TAG, field.getName() + ":" + field.get(null).toString());
			} catch (Exception e) {
				Log.e(TAG, "an error occured when collect crash info", e);
			}
		}
	}

	// 使用HTTP服务之前，需要确定网络畅通，我们可以使用下面的方式判断网络是否可用
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager mgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info = mgr.getAllNetworkInfo();
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}
}