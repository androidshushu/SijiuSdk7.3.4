package com.shiyue.mhxy.config;

import android.content.Context;
import android.content.res.Resources;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AppConfig
{
	public static final int FLAG_SUCCESS = 0;
	public static final int FLAG_FAIL = 1;
	public static final int FLAG_REQUEST_ERROR = 2;
	public static final int FLAG_SHOW_POPWINDOW = 3;
	public static final int FLAG_PROGRESS_TAG = 6;
	public static final int FLAG_INIT_FAIL = 11;
	public static final int FLAG_INIT_SUCCESS = 12;
	public static final int FLAG_YEEPAY_SUCCESS = 13;
	public static final int FLAG_YEEPAYONEKEY_SUCCESS = 14;
	public static final int FLAG_TIANXIA_SUCCESS = 15;
	public static final int FLAG_UNION_SUCCESS = 16;
	public static final int FLAG_YEEPAYWEB_SUCCESS = 17;
	public static final int FLAG_YEEPAYLIST_SUCCESS = 18;
	public static final int FLAG_ALIPAYSIJIU_SUCCESS = 19;
	public static final int FLAG_ALIPAYQUICK_SUCCESS = 20;
	public static final int FLAG_RECHARGE_FAILED = 21;
	public static final int FLAG_ADDPAY_SUCCESS = 33;
	public static final int FLAG_GETADD_SUCCESS = 34;
	public static final int FLAG_GETPINGTAIBI_SUCCESS = 102;
	public static final int FLAG_SMS_SUCCESS = 22;
	public static final int FLAG_IS_NOTBANGDING = 501;
	public static final int REGIST_SUCCESS = 502;
	public static final int PH_LOGIN_SUCCESS = 503;
	public static final int THIRD_REGIST = 504;
	public static final int THIRD_LOGIN = 505;
	public static final int FLAG_PAY_CHANGLE_UI = 100;
	public static final int FLAG_PINGTAIBI = 101;
	public static final int RQF_PAY = 23;
	public static final int TIANXIA = 24;
	public static final int SMS_STATE_INFO = 25;
	public static final int FLAG_MO9PAY_SUCCESS = 26;
	public static final int Mo9 = 27;
	public static final int YEEPAYWEB = 28;
	public static final int FLAG_ALIPAYWEB_SUCCESS = 29;
	public static final int ALIPAYWEB = 30;
	public static final int FLAG_TENPAYWEB_SUCCESS = 31;
	public static final int TENPAY = 32;
	public static final int FLAG_PLATFORMDESC_SUCCESS = 35;
	public static final int FLAG_FINDQ_SUCCESS = 36;
	public static final int FLAG_USERMODIFY_SUCCESS = 37;
	public static final int FINDPWD_F = 201;
	public static final int FINDPWD_S = 202;
	public static final int FINDPWD_T = 203;
	public static final int ROLE_CREATE = 301;
	public static final int ROLE_ENTER = 302;
	public static final int ROLE_LEV = 303;
	public static final int FLAG_BIND_FAIL =401;
    public static final int FLAG_BIND_FAILS =404;
    public static final int FLAG_BIND_SUCCESS = 400;
	public static final int FLAG_BIND_REQUEST_ERROR= 402;
	public static final int FLAG_BIND_SMS = 403;
//	public static final int BANGDING_SUCCESS = 38;
//	public static final int FLAG_PAYDATE_SUCCESS = 39;
//	public static final int FLAG_VIPDATE_SUCCESS = 40;
//	public static final int WECHAR_SUCCESS = 41;
//	public static final int KEY_SUCCESS = 42;
//	public static final int UPDATE_SUCCESS = 43;
//	public static final int WECHATWAP = 44;
//	public static final int PAY1_SUCCESS = 45;
//	public static final int PAY2_SUCCESS = 46;
//	public static final String SJAPP_VER = "7.3";
	public static final String SJAPP_NAME = "";
	public static final String GAME_TIME_ACTION = "com.game.time";
	public static int appId = 0;
	public static String appKey = "";
	public static String ver_id = "";
	public static String ad_id = "";
	public static String imei = "";
	public static String token = "";
	public static String timeStamp = "";
	public static String isAgreement = "0";
	public static String QQ = "0";
	public static String WeChat = "0";
//	public static final String WXAPP_ID = "wxe39c0447857f8d64";
//	public static final String WXAPP_SECRET = "e5732d575617d36953dc07232d11f48f";
//	public static final String QQAPP_ID = "101387230";
	public static String WXAPP_ID = "";
	public static String WXAPP_SECRET = "";
	public static String QQAPP_ID = "";
	public static String uuid = "";
	public static int userType = 0;
	public static String access_token = "";
	public static String verifySign = "";
	public static boolean LoginStatus = false;
	public static boolean isApkCacheExit = false;
	public static boolean isTest = false;
	public static boolean isLogin = false;
	public static boolean isFirst = true;
	public static boolean isDownload = true;
	public static String GAME_TIME = "GAME_TIME";

	public static boolean initResult = false;

	public static boolean ISPORTRAIT = false;
	public static String uid = "";
	public static String userName = "";
	public static Map<String, String> tempMap = new HashMap();
	public static Map<String, String> loginMap = new HashMap();
	public static Map<String, String> initMap = new HashMap();

	public static void saveMap(String user, String pwd, String uid ) {
		loginMap.put("user", user);
		loginMap.put("pwd", pwd);
		loginMap.put("uid", uid);
	}

	public static void clear()
	{
		tempMap.clear();
	}

	public static void clearCache() {
		tempMap.clear();
		loginMap.clear();
		initMap.clear();
		isFirst = true;
		isApkCacheExit = false;
	}

	public static List<String> intersect(String[] a, String[] b) {
		List list = new ArrayList(Arrays.asList(b));
		list.retainAll(Arrays.asList(a));
		return list;
	}

	public static int resourceId(Context context, String name, String type) {
		return context.getResources().getIdentifier(name, type, context
				.getPackageName());
	}

	public static String GameTime(String begin, String end)
	{
		String time = "";
		try
		{
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.getDefault());
			Date beginDate = fmt.parse(begin);
			Date endDate = fmt.parse(end);
			long between = (endDate.getTime() - beginDate.getTime()) / 1000L;
			long minute = between / 60L;
			time = minute + "";
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}
}
