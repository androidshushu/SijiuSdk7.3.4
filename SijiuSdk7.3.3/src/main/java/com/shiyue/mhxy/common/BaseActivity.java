package com.shiyue.mhxy.common;


import com.shiyue.mhxy.config.AppConfig;
import com.shiyue.mhxy.user.LoginInfo;
import com.shiyue.mhxy.user.UserInfo;
import com.shiyue.mhxy.user.WecomeBackToast;
import com.shiyue.mhxy.utils.Seference;
import com.shiyue.mhxy.utils.rsa.Sy_Seference;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class BaseActivity extends Activity {

	public LoginInfo loginInfo;
	public String appKey = "", serverId = "", verId = "";
	public int appId;
	public UserInfo creatFile;
	public Seference seference;
	public Sy_Seference sy_seference;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
		loginInfo = getIntent().getParcelableExtra("sj_login_info"); // 获取logininfo参数
		if (loginInfo != null) {
			initData();
		}
//		if (loginInfo.getOritation().equals("landscape")) {
//			// 横屏
//			//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//		} else {
//			// 竖屏
//			//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//		}


		// 初始化数据

	}

	private void initData() {
		// TODO Auto-generated method stub
		appId = AppConfig.appId;
		appKey = AppConfig.appKey;
//		serverId = loginInfo.getServer_id();
		verId = AppConfig.ver_id;
		creatFile = new UserInfo();
		seference = new Seference(this);
		sy_seference=new Sy_Seference(this);
	}

	/**
	 * 接口返回数据处理
	 */
	public void sendData(int num, Object data, Handler callback) {
		Message msg = callback.obtainMessage();
		msg.what = num;
		msg.obj = data;
		msg.sendToTarget();
		//	Log.i("giftnews", "I am a sendData(接口返回数据处理)");
	}

	/**
	 * 接口返回数据处理
	 */
	/*public void sendData(int num, String data, Handler callback) {
		Message msg = callback.obtainMessage();
		msg.what = num;
		msg.obj = data;
		msg.sendToTarget();
	}*/

	/**
	 * toast 提示信息
	 *
	 * @param msg
	 */
	public void showMsg(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Intent页面跳转
	 */
	public void changePage(Activity activity, Class<?> cls) {
		Intent intent = new Intent(activity, cls);
		Bundle bundle = new Bundle();
		bundle.putParcelable("sj_login_info", loginInfo);
		intent.putExtras(bundle);
		activity.startActivity(intent);
	}

	/**
	 * 显示登录成功
	 *
	 * @param userName
	 * @param xOffset
	 * @param yOffset
	 * @param duration
	 */
	public void showLoginFinish(String userName, int xOffset, int yOffset,
								int duration) {
		WecomeBackToast toast = new WecomeBackToast(this);
		toast.makeText(userName, duration);
		int mGravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
		toast.setGravity(mGravity, xOffset, yOffset);
		toast.show();
	}

	/**
	 * 回调信息
	 */
	public void callBack(String result, String msg, String timestamp,
						 String uid, String username,String token) {
		Intent intent = new Intent();
		intent.putExtra("result", result);
		intent.putExtra("msg", msg);
		intent.putExtra("timeStamp", timestamp);
		intent.putExtra("uid", uid);
		intent.putExtra("userName", username);
//		intent.putExtra("sign", sign);
		intent.putExtra("token", token);
		setResult(Syyx.LOGIN_RESULT_CODE, intent);
	}
	public String ramrule()
	{
		String strAll = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String strCp = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String strNum = "0123456789";
		String userName = "";
		String passWord = "";
		Random random = new Random();

		for (int i = 0; i < 8; i++)
		{
			String oneChar = "";
			if (i < 2) {
				int rd = random.nextInt(26);

				oneChar = strCp.substring(rd, rd + 1);
			} else {
				int rd = random.nextInt(10);
				oneChar = strNum.substring(rd, rd + 1);
			}

			int pd = random.nextInt(62);
			String sedChar = strAll.substring(pd, pd + 1);

			userName = userName + oneChar;
			passWord = passWord + sedChar;
		}
		return userName;
	}
	public static boolean isWeixinAvilible(Context context) {
		PackageManager packageManager = context.getPackageManager();
		List pinfo = packageManager.getInstalledPackages(0);
		if (pinfo != null) {
			for (int i = 0; i < pinfo.size(); i++) {
				String pn = ((PackageInfo)pinfo.get(i)).packageName;
				if (pn.equals("com.tencent.mm")) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK);
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 验证手机号格式
	 *
	 * @param mobiles
	 */
	public static boolean isMobileNO(String mobiles) {
		String telRegex = "13\\d{9}|14[57]\\d{8}|15[012356789]\\d{8}|18[01256789]\\d{8}|17[0678]\\d{8}";
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}
	/**
	 * 判断输入框中的数据是否符合格式
	 */
	public boolean verfy(EditText user, EditText pwd) {

		if (user != null && pwd != null) {
			if (user.getText() == null || "".equals(user.getText().toString())) {
				showMsg("请输入账号!");
				return true;
			} else if (pwd.getText() == null
					|| "".equals(pwd.getText().toString())) {
				showMsg("请输入密码!");
				return true;
			}
		}
		if (user == null) {
			if (pwd.getText() == null || "".equals(pwd.getText().toString())) {
				showMsg("请输入密码!");
				return true;
			}
		}

		if (pwd == null) {
			if (user.getText() == null || "".equals(user.getText().toString())) {
				showMsg("请输入账号!");
				return true;
			}
		}
		return false;
	}
}
