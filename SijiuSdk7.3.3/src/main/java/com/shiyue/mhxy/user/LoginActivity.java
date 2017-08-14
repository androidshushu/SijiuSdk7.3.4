package com.shiyue.mhxy.user;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shiyue.mhxy.common.BaseActivity;
import com.shiyue.mhxy.common.LoginMessageInfo;
import com.shiyue.mhxy.common.Phonedialog;
import com.shiyue.mhxy.common.Phonedialog.Phonelistener;
import com.shiyue.mhxy.common.Syyx;
import com.shiyue.mhxy.common.TipsDialog;
import com.shiyue.mhxy.common.TipsDialog.DialogListener;
import com.shiyue.mhxy.config.AppConfig;
import com.shiyue.mhxy.http.ApiAsyncTask;
import com.shiyue.mhxy.http.ApiRequestListener;
import com.shiyue.mhxy.sdk.SiJiuSDK;
import com.shiyue.mhxy.utils.rsa.Sy_Seference;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendAuth.Req;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private ImageView upBtn;
	private ImageView phone_login;
	private ImageView qq_login;
	private ImageView wx_login;
	private ImageView password_show;
	private ImageView user_icon;
	private PopupAdapter adapter;
	private Button registerBtn;
	private TextView find_pwd;
	private Button loginBtn;
	private PopupWindow popupWindow;
	private ListView myListView;
	private RelativeLayout rl;
	private Rect mRectSrc;
	private Timer timer;
	private String userName = ""; private String passWord = "";
	private EditText user;
	private EditText pwd;
	private ApiAsyncTask loginTask;
	private ApiAsyncTask thirdRegistTask;
	private ApiAsyncTask thirdQueryTask;
	private ApiAsyncTask tickloginTask;
	private TipsDialog dialog;
	private Phonedialog ddialog;
	private Phonedialog pdialog;
	private boolean flag = true;
	private boolean isrotate = false;
	private boolean ispopshow = false;
	private boolean eye_ischeck = false;
	private int j = 1;
    private int newLoginTimes;
	private int q = 1;
	private final int FLAG_AUTO_LOGIN = 51;
	private final int FLAG_DELET_USERINFO = 53;
	private String loginToken = "";
	private String loginTick = "";
	private final int FLAG_AUTO_LOGIN_HTTP = 52;
	private final int CLOSE_ANIM = 54;
	public static LoginActivity _instance = null;
	private IWXAPI api;
	private String qqaccess;
	private String qqexpires;
	private String openid = "";

	private String loginType = "4";
	private String getNmae;
	private String getPwd;
	private String getNickName;
	private String thuserName;
	private String thpassword;
	private Tencent mTencent;
	private BaseUiListener qqlistener;

//    private Sy_Seference sy_seference;


//    private  int a =0;
	private Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case AppConfig.FLAG_SHOW_POPWINDOW:
					LoginActivity.this.rl.getGlobalVisibleRect(LoginActivity.this.mRectSrc);
					int x = LoginActivity.this.mRectSrc.centerX() - LoginActivity.this.mRectSrc.width() / 2;
					int y = LoginActivity.this.mRectSrc.centerY() + LoginActivity.this.mRectSrc.height() / 2;
					LoginActivity.this.popupWindow.showAtLocation(LoginActivity.this.findViewById(AppConfig.resourceId(LoginActivity.this, "linear", "id")), 0, x, y);

					break;
				case AppConfig.FLAG_SUCCESS:
					if (LoginActivity.this.pdialog != null) {
						LoginActivity.this.pdialog.dismiss();
					}
					LoginActivity.this.showLoginFinish((String)msg.obj, 0, 50, 0);
					LoginActivity.this.finish();
					break;
				case AppConfig.FLAG_FAIL:
					if (LoginActivity.this.dialog != null) {
						LoginActivity.this.dialog.dismiss();
					}
					if (LoginActivity.this.pdialog != null) {
						LoginActivity.this.pdialog.dismiss();
						LoginActivity.this.findViewById(AppConfig.resourceId(LoginActivity.this, "linear", "id"))
								.setVisibility(View.VISIBLE);
					}

					String result = (String)msg.obj;

					LoginActivity.this.showMsg(result);
					break;
				case AppConfig.FLAG_REQUEST_ERROR:
					if (dialog != null) {
						dialog.dismiss();
					}
					if (pdialog != null) {
						pdialog.dismiss();
						findViewById(AppConfig.resourceId(
								LoginActivity.this, "linear", "id")).setVisibility(View.VISIBLE);
					}
					showMsg("网络连接失败，请检查您的网络连接");
					break;

				case FLAG_AUTO_LOGIN:

					LoginActivity.this.pdialog.setText(LoginActivity.this.user.getText().toString() + "正在登陆中...");
					findViewById(AppConfig.resourceId(
							LoginActivity.this, "linear", "id")).setVisibility(View.GONE);
					pdialog.setText(user.getText().toString()+"正在登陆中...");
					if (q <0) {

						pdialog.setEnable(false);
						mHandler.sendEmptyMessage(FLAG_AUTO_LOGIN_HTTP);
						flag = false;
					}
					q--;
					break;
				case FLAG_AUTO_LOGIN_HTTP:
					userName = user.getText().toString();
					passWord = pwd.getText().toString();
					ticklogin();
					break;
				case  FLAG_DELET_USERINFO:
					if(sy_seference.getAccountList(false)==null){
						popupWindow.dismiss();

					}else {
						adapter.refresh(sy_seference.getAccountList(true));
					}

					break;
				case CLOSE_ANIM:
					if(isrotate){
						isrotate=false;
						ObjectAnimator animator = ObjectAnimator.ofFloat(upBtn,"rotation",180,360);
						animator.setDuration(500);
						animator.start();
					}
					break;
				case AppConfig.THIRD_LOGIN:
					HashMap hs = (HashMap)msg.obj;

					String th_username = (String)hs.get("username");
					String th_password = (String)hs.get("password");
					String th_nickname = (String)hs.get("nickname");
					String th_logintype = (String)hs.get("logintype");
					LoginActivity.this.login(th_username, th_password, th_logintype, th_nickname);
			}
		}
	};

	public Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			try {
				switch (msg.what) {
					case 1:
						if (LoginActivity.this.pdialog != null) {
							LoginActivity.this.pdialog.dismiss();
						}
						LoginActivity.this.showLoginFinish(LoginActivity.this.userName, 0, 50, 0);
						LoginActivity.this.finish();

						break;
					case AppConfig.REGIST_SUCCESS:
						HashMap hasMap = (HashMap)msg.obj;
						LoginActivity.this.userName = ((String)hasMap.get("user"));
						LoginActivity.this.passWord = ((String)hasMap.get("password"));
						LoginActivity.this.user.setText(LoginActivity.this.userName);
						LoginActivity.this.pwd.setText(LoginActivity.this.passWord);
						Log.d("shiyue",userName+"###"+passWord);
						if (BaseActivity.isMobileNO(LoginActivity.this.userName))
							LoginActivity.this.login(LoginActivity.this.userName, LoginActivity.this.passWord, "5", "");
						else {
							LoginActivity.this.login(LoginActivity.this.userName, LoginActivity.this.passWord, "4", "");
						}

						LoginActivity.this.dialog = new TipsDialog(LoginActivity.this, AppConfig.resourceId(LoginActivity.this, "Sj_MyDialog", "style"), new TipsDialog.DialogListener()
						{
							public void onClick()
							{
								if (LoginActivity.this.loginTask != null)
									LoginActivity.this.loginTask.cancel(true);
							}
						});
						LoginActivity.this.dialog.show();
						LoginActivity.this.dialog.setCancelable(false);

						break;
					case AppConfig.PH_LOGIN_SUCCESS:
						LoginMessage loginmsg = (LoginMessage)msg.obj;
						String result = "";
						String timeStamp = loginmsg.getTimestamp();
						String uid = String.valueOf(loginmsg.getUid());
						String username = loginmsg.getUserName();
						String phone = loginmsg.getPhoneNumber();

						String loginmsgMessage = loginmsg.getMessage();
						LoginActivity.this.loginTick = loginmsg.getLoginTick();

						if (loginmsg.getResult()) {
							result = "success";


							LoginActivity.this.sy_seference.savePreferenceData(uid, "", phone, LoginActivity.this.loginTick, username, "5", "",LoginActivity.this.sy_seference.getLogintimes()+1);

							LoginActivity.this.wrapaLoginInfo(result, loginmsgMessage, username, uid, timeStamp, LoginActivity.this.loginTick);
							AppConfig.clear();
							AppConfig.saveMap(username, "", uid);
							AppConfig.isLogin = true;
						} else {
							result = "fail";
							LoginActivity.this.wrapaLoginInfo(result, loginmsgMessage, username, uid, timeStamp, LoginActivity.this.loginTick);
						}

						LoginActivity.this.showLoginFinish(phone, 0, 50, 0);
						LoginActivity.this.finish();
						break;
					case AppConfig.THIRD_REGIST:
						HashMap hashMap = (HashMap)msg.obj;
						LoginActivity.this.thuserName = ((String)hashMap.get("userName"));
						LoginActivity.this.thpassword = ((String)hashMap.get("password"));
						LoginActivity.this.loginType = ((String)hashMap.get("loginType"));
						LoginActivity.this.openid = ((String)hashMap.get("openid"));
						String nickname = (String)hashMap.get("nickname");

						if (LoginActivity.this.sy_seference.isAccountSave(LoginActivity.this.openid)) {
							HashMap map = LoginActivity.this.sy_seference.getUserInfo(LoginActivity.this.openid);
							LoginActivity.this.userName = ((String)map.get("userName"));
							LoginActivity.this.loginType = ((String)map.get("loginType"));
							LoginActivity.this.passWord = ((String)map.get("pwd"));

							if (LoginActivity.this.loginType.equals("1"))
								LoginActivity.this.user_icon.setImageResource(LoginActivity.this.getResources().getIdentifier("qq_red", "drawable", LoginActivity.this.getPackageName()));
							else {
								LoginActivity.this.user_icon.setImageResource(LoginActivity.this.getResources().getIdentifier("wechat", "drawable", LoginActivity.this.getPackageName()));
							}

							LoginActivity.this.user.setText(nickname);
							LoginActivity.this.pwd.setText(LoginActivity.this.passWord);
							LoginActivity.this.pwd.setFocusable(false);
							LoginActivity.this.pwd.setClickable(false);

							LoginActivity.this.dialog = new TipsDialog(LoginActivity.this, AppConfig.resourceId(LoginActivity.this, "Sj_MyDialog", "style"), new TipsDialog.DialogListener()
							{
								public void onClick()
								{
									if (LoginActivity.this.loginTask != null)
										LoginActivity.this.loginTask.cancel(true);
								}
							});
							LoginActivity.this.dialog.show();
							LoginActivity.this.dialog.setCancelable(false);
							LoginActivity.this.login(LoginActivity.this.userName, LoginActivity.this.passWord, LoginActivity.this.loginType, nickname);
						}
						else {
							LoginActivity.this.dialog = new TipsDialog(LoginActivity.this, AppConfig.resourceId(LoginActivity.this, "Sj_MyDialog", "style"), new TipsDialog.DialogListener()
							{
								public void onClick()
								{
									if (LoginActivity.this.loginTask != null)
										LoginActivity.this.loginTask.cancel(true);
								}
							});
							LoginActivity.this.dialog.show();
							LoginActivity.this.dialog.setCancelable(false);
							LoginActivity.this.thirdquery(LoginActivity.this.openid, LoginActivity.this.loginType, nickname);
						}
						break;
				}
			}
			catch (Exception localException)
			{
			}
		}
	};

	@SuppressLint({"NewApi"})
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		_instance = this;
		setContentView(AppConfig.resourceId(this, "sylogin_main_land", "layout"));

		this.timer = new Timer();
		this.mTencent = Tencent.createInstance(AppConfig.QQAPP_ID, getApplicationContext());
		init();
		if (this.sy_seference.getAccountList(false) != null)
		{
			HashMap map = this.sy_seference.getUserInfo(this.sy_seference.getAccountList(true).get(0) + "");
			this.userName = ((String)map.get("userName"));
			this.loginType = ((String)map.get("loginType"));
			this.passWord = ((String)map.get("pwd"));
			this.getNickName = ((String)map.get("mnickName"));

			if (this.loginType.equals("1"))
			{
				setLoginView("qq_red", this.getNickName, this.passWord, false, false, false);
			}
			else if (this.loginType.equals("2"))
			{
				setLoginView("wechat", this.getNickName, this.passWord, false, false, false);
			}
			if (this.loginType.equals("4"))
			{
				setLoginView("sy_user_round", this.userName, this.passWord, true, true, true);
			} else if (this.loginType.equals("5"))
			{
				setLoginView("sy_phone_round", this.userName, this.passWord, true, true, true);
			}

			this.loginToken = ((String)map.get("longinToken"));

			if (!AppConfig.isLogin)
				autoLogin();
		}
	}

	private void setLoginView(String image, String username, String password, boolean pwd_show_focus, boolean pwd_show_click, boolean pwd_focus)
	{
		this.user_icon.setImageResource(getResources().getIdentifier(image, "drawable", getPackageName()));
		this.user.setText(username);
		this.pwd.setText(password);
		this.password_show.setFocusable(pwd_show_focus);
		this.password_show.setClickable(pwd_show_click);

		this.pwd.setFocusableInTouchMode(pwd_focus);
	}

	/**
	 * 自动登录
	 */
	public void autoLogin()
	{
		this.pdialog = new Phonedialog(this, AppConfig.resourceId(this, "Sj_Transparent", "style"),
				AppConfig.resourceId(this, "sy_logindialog", "layout"),
				"", "auto_login", new Phonedialog.Phonelistener()
		{
			public void onClick(View view, String text, String from)
			{
				if (view.getId() == LoginActivity.this.getResources().getIdentifier("dialog_phone", "id", LoginActivity.this
						.getPackageName()))
				{
					LoginActivity.this.flag = false;
					LoginActivity.this.findViewById(AppConfig.resourceId(LoginActivity.this, "linear", "id"))
							.setVisibility(View.VISIBLE);

					LoginActivity.this.pdialog.dismiss();
				}
			}
		});

		pdialog.setCancelable(false);
		pdialog.show();
		//下面这个文字不是最终控制显示的，以线程里面的为准

		pdialog.setText("自动登陆游戏中....");
		// 自动登录线程
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (flag) {
					mHandler.sendEmptyMessage(FLAG_AUTO_LOGIN);
					try {
						Thread.sleep(900);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();

	}

	private void init()
	{
		this.upBtn = ((ImageView)findViewById(AppConfig.resourceId(this, "user_up", "id")));

		this.registerBtn = ((Button)findViewById(AppConfig.resourceId(this, "register", "id")));

		this.user_icon = ((ImageView)findViewById(AppConfig.resourceId(this, "user_icon", "id")));

		this.find_pwd = ((TextView)findViewById(AppConfig.resourceId(this, "find_pwd", "id")));

		this.phone_login = ((ImageView)findViewById(AppConfig.resourceId(this, "phone_login", "id")));

		this.qq_login = ((ImageView)findViewById(AppConfig.resourceId(this, "qq_login", "id")));

		this.wx_login = ((ImageView)findViewById(AppConfig.resourceId(this, "wx_login", "id")));

		this.loginBtn = ((Button)findViewById(AppConfig.resourceId(this, "login", "id")));

		this.user = ((EditText)findViewById(AppConfig.resourceId(this, "edit_user", "id")));

		this.pwd = ((EditText)findViewById(AppConfig.resourceId(this, "edit_pwd", "id")));

		this.password_show = ((ImageView)findViewById(AppConfig.resourceId(this, "password_show", "id")));

		if (AppConfig.QQ.equals("0")) {
			this.qq_login.setVisibility(View.GONE);
		}
		if (AppConfig.WeChat.equals("0")) {
			this.wx_login.setVisibility(View.GONE);
		}
		this.upBtn.setOnClickListener(this);
		this.registerBtn.setOnClickListener(this);
		this.phone_login.setOnClickListener(this);
		this.qq_login.setOnClickListener(this);
		this.wx_login.setOnClickListener(this);
		this.find_pwd.setOnClickListener(this);
		this.loginBtn.setOnClickListener(this);
		this.user.setOnClickListener(this);
		this.password_show.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if (v.getId() == AppConfig.resourceId(this, "register", "id")) {
			changePage(this, Sy_RegisterActivity.class);
		}
		else if (v.getId() == AppConfig.resourceId(this, "find_pwd", "id")) {
			Intent intent = new Intent(this, Sy_FindpwdActivity.class);
			if ((this.loginType.equals("1")) || (this.loginType.equals("2")))
				intent.putExtra("account", "");
			else {
				intent.putExtra("account", this.user.getText().toString());
			}

			startActivity(intent);
		}
		else if (v.getId() == AppConfig.resourceId(this, "edit_user", "id"))
		{
			if (!this.loginType.equals("4")) {
				resetLoginView();
			}

		}
		else if (v.getId() == AppConfig.resourceId(this, "phone_login", "id"))
		{
			Intent intent = new Intent(this, Sy_PhloginActivity.class);
			startActivity(intent);
		}
		else if (v.getId() == AppConfig.resourceId(this, "qq_login", "id")) {
			this.mTencent = Tencent.createInstance(AppConfig.QQAPP_ID, getApplicationContext());
			if (!this.mTencent.isSessionValid())
			{
				this.qqlistener = new BaseUiListener();
				this.mTencent.login(this, "all", this.qqlistener);
			}

		}
		else if (v.getId() == AppConfig.resourceId(this, "wx_login", "id"))
		{
			if (!isWeixinAvilible(this)) {
				showMsg("您还未安装微信客户端");
				return;
			}

			this.api = WXAPIFactory.createWXAPI(this, AppConfig.WXAPP_ID, false);
			this.api.registerApp(AppConfig.WXAPP_ID);

			AppConfig.uuid = UUID.randomUUID().toString();
			SendAuth.Req req = new SendAuth.Req();
			req.scope = "snsapi_userinfo";
			req.state = AppConfig.uuid;
			this.api.sendReq(req);
		}
		else if (v.getId() == AppConfig.resourceId(this, "login", "id"))
		{
			if (!verfy(this.user, this.pwd))
			{
				if (this.loginType.equals("1")) {
					this.passWord = this.pwd.getText().toString();
					login(this.userName, this.passWord, "1", this.getNickName);
				} else if (this.loginType.equals("2")) {
					this.passWord = this.pwd.getText().toString();
					login(this.userName, this.passWord, "2", this.getNickName);
				} else if (this.loginType.equals("4")) {
					this.userName = this.user.getText().toString();
					this.passWord = this.pwd.getText().toString();
					login(this.userName, this.passWord, "4", "");
				} else if (this.loginType.equals("5")) {
					this.userName = this.user.getText().toString();
					this.passWord = this.pwd.getText().toString();
					login(this.userName, this.passWord, "5", "");
				}
				this.dialog = new TipsDialog(this, AppConfig.resourceId(this, "Sj_MyDialog", "style"), new TipsDialog.DialogListener()
				{
					public void onClick()
					{
						if (LoginActivity.this.loginTask != null)
							LoginActivity.this.loginTask.cancel(true);
					}
				});
				this.dialog.show();
				this.dialog.setCancelable(false);
			}

		}
		else if (v.getId() == AppConfig.resourceId(this, "user_up", "id")) {
			if (!sy_seference.getAccountArray().equals("")) {

				inipopWindow();
				timer.schedule(new myPopupWindow(), 5);

				if(eye_ischeck){
					eye_ischeck=false;
					password_show.setBackgroundResource(this.getResources().getIdentifier("sy_eye", "drawable", this.getPackageName()));
					pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}
				if(!isrotate){
					isrotate=true;
					ObjectAnimator animator = ObjectAnimator.ofFloat(upBtn,"rotation",0,180);
					animator.setDuration(500);
					animator.start();
				}
			} else {
				showMsg("亲,快点登陆吧!");
			}
		}
		else if(v.getId() == AppConfig.resourceId(this, "password_show", "id")){
			if(!eye_ischeck) {
				eye_ischeck=true;
				password_show.setBackgroundResource(this.getResources().getIdentifier("sy_eye_green", "drawable", this.getPackageName()));
				pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
			}else{
				eye_ischeck=false;
				password_show.setBackgroundResource(this.getResources().getIdentifier("sy_eye", "drawable", this.getPackageName()));
				pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			}

		}
		else if (v.getId() == AppConfig.resourceId(this, "back", "id")) {
			this.finish();
		}
	}

	private void resetLoginView() {
		this.user_icon.setImageResource(getResources().getIdentifier("sy_user_round", "drawable", getPackageName()));
		this.user.setText("");
		this.pwd.setText("");
		this.loginType = "4";
		this.password_show.setFocusable(true);
		this.password_show.setClickable(true);
		this.pwd.setFocusableInTouchMode(true);
	}

	private void inipopWindow()
	{
		this.mRectSrc = new Rect();
		this.rl = ((RelativeLayout)findViewById(AppConfig.resourceId(this, "pop", "id")));

		this.rl.getGlobalVisibleRect(this.mRectSrc);
		View contentView = LayoutInflater.from(this).inflate(
				AppConfig.resourceId(this, "sjpopwindow_land", "layout"),
				null);
		contentView.setOnTouchListener(new View.OnTouchListener()
		{
			public boolean onTouch(View v, MotionEvent event)
			{
				if (LoginActivity.this.popupWindow.isShowing()) {
					LoginActivity.this.popupWindow.dismiss();
				}
				return false;
			}
		});
		this.adapter = new PopupAdapter(this, this.sy_seference.getAccountList(true), new PopupAdapter.MyClickListener()
		{
			public void clickListener(View v) {
				final String a = LoginActivity.this.sy_seference.getAccountList(true).get(((Integer)v.getTag()).intValue()) + "";

				LoginActivity.this.ddialog = new Phonedialog(LoginActivity.this, AppConfig.resourceId(LoginActivity.this, "Sj_Transparent", "style"),
						AppConfig.resourceId(LoginActivity.this, "sy_logindialog", "layout"),
						"", "del_userinfo", new Phonedialog.Phonelistener()
				{
					public void onClick(View view, String text, String from)
					{
						if (view.getId() == LoginActivity.this.getResources().getIdentifier("dialog_phone", "id", LoginActivity.this
								.getPackageName())) {
							LoginActivity.this.mHandler.sendEmptyMessage(53);

							HashMap map = LoginActivity.this.sy_seference.getUserInfo(a);
							String name = (String)map.get("userName");
							String mnickname = (String)map.get("mnickName");
							if (LoginActivity.this.user.getText().toString().equals(name))
								LoginActivity.this.resetLoginView();
							else if (LoginActivity.this.user.getText().toString().equals(mnickname)) {
								LoginActivity.this.resetLoginView();
							}
							LoginActivity.this.sy_seference.removeAccount(a);
							LoginActivity.this.ddialog.dismiss();
						} else if (view.getId() == LoginActivity.this.getResources().getIdentifier("dialog_cancel", "id", LoginActivity.this
								.getPackageName())) {
							LoginActivity.this.ddialog.dismiss();
						}
					}
				});
				LoginActivity.this.ddialog.setCancelable(false);
				LoginActivity.this.ddialog.show();
				LoginActivity.this.ddialog.setText("是否删除账号记录");
			}
		});
		this.myListView = ((ListView)contentView.findViewById(
				AppConfig.resourceId(this, "poplist", "id")));

		this.myListView.setAdapter(this.adapter);
		this.myListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				HashMap hs = LoginActivity.this.sy_seference.getUserInfo(LoginActivity.this.sy_seference.getAccountList(true).get(position) + "");
				LoginActivity.this.userName = ((String)hs.get("userName"));
				LoginActivity.this.loginType = ((String)hs.get("loginType"));
				LoginActivity.this.getPwd = ((String)hs.get("pwd"));
				LoginActivity.this.getNickName = ((String)hs.get("mnickName"));

				LoginActivity.this.openid = ((String)hs.get("account"));
				if (LoginActivity.this.loginType.equals("1"))
				{
					LoginActivity.this.setLoginView("qq_red", LoginActivity.this.getNickName, LoginActivity.this.getPwd, false, false, false);
				}
				else if (LoginActivity.this.loginType.equals("2")) {
					LoginActivity.this.setLoginView("wechat", LoginActivity.this.getNickName, LoginActivity.this.getPwd, false, false, false);
				}
				if (LoginActivity.this.loginType.equals("4"))
				{
					LoginActivity.this.setLoginView("sy_user_round", LoginActivity.this.userName, LoginActivity.this.getPwd, true, true, true);
				} else if (LoginActivity.this.loginType.equals("5"))
				{
					LoginActivity.this.setLoginView("sy_phone_round", LoginActivity.this.userName, LoginActivity.this.getPwd, true, true, true);
				}
				LoginActivity.this.popupWindow.dismiss();
			}
		});
		this.popupWindow = new PopupWindow(contentView, this.mRectSrc.width(), this.mRectSrc
				.height() * 4);
		this.popupWindow.setFocusable(true);

		this.popupWindow.setAnimationStyle(AppConfig.resourceId(this, "Sj_mypopwindow_anim_style", "style"));

		this.popupWindow.setWidth(this.mRectSrc.width());
		this.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener()
		{
			public void onDismiss() {
				LoginActivity.this.mHandler.sendEmptyMessage(CLOSE_ANIM);
			}
		});
	}

	protected void onResume()
	{
		super.onResume();
	}

	protected void onDestroy()
	{
		super.onDestroy();
		this.mTencent.logout(this);
		if (this.dialog != null) {
			this.dialog.dismiss();
		}
		if (this.pdialog != null) {
			this.pdialog.dismiss();
		}
		if (this.ddialog != null)
			this.ddialog.dismiss();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		Tencent.onActivityResultData(requestCode, resultCode, data, this.qqlistener);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void wrapaLoginInfo(String result, String msg, String userName, String uid, String timeStamp, String token) {
		LoginMessageInfo login = new LoginMessageInfo();
		login.setMessage(msg);
		login.setResult(result);
		login.setToken(token);
		login.setUid(uid);
		login.setTimestamp(timeStamp);

		login.setUserName(userName);
		Message mssg = new Message();
		mssg.obj = login;
		mssg.what = 1;
		Syyx.handler.sendMessage(mssg);
	}

	private void login(final String user, final String pass, final String logintype, final String nickName)
	{
		this.loginTask = SiJiuSDK.get().startLogon(this, AppConfig.appId, AppConfig.appKey, user, pass, AppConfig.ver_id,new ApiRequestListener()
		{
			public void onSuccess(Object obj)
					// TODO: 2017/8/10  登录成功回调
			{
				if (LoginActivity.this.dialog != null) {
					LoginActivity.this.dialog.dismiss();
				}
				if (obj != null) {
					LoginMessage loginmsg = (LoginMessage)obj;
					String result = "";
					String timeStamp = loginmsg.getTimestamp();
					String uid = String.valueOf(loginmsg.getUid());
					String username = loginmsg.getUserName();
					String message = loginmsg.getMessage();
					String msg = loginmsg.getMessage();
					LoginActivity.this.loginTick = loginmsg.getLoginTick();
					String phone = loginmsg.getPhoneNumber();

					AppConfig.isLogin = true;
					if (loginmsg.getResult()) {
						result = "success";
						AppConfig.clear();

						LoginActivity.this.wrapaLoginInfo(result, msg, username, uid, timeStamp, LoginActivity.this.loginTick);
						String showname="";


                        try {
                            j = Integer.parseInt(LoginActivity.this.sy_seference.getUserInfo(uid).get("loginTimes") + "");
                            Log.d("login3=",j+"");


                        }catch (Exception e){
                            e.printStackTrace();
                            j=0;

                        }

                         newLoginTimes = j + 1;



                        if (logintype.equals("1")) {
							 showname = nickName;
							LoginActivity.this.sy_seference.savePreferenceData(LoginActivity.this.openid, pass, phone, LoginActivity.this.loginTick, username, logintype, nickName + "",newLoginTimes+"");
						} else if (logintype.equals("2"))
						{
							showname = nickName;
							LoginActivity.this.sy_seference.savePreferenceData(LoginActivity.this.openid, pass, phone, LoginActivity.this.loginTick, username, logintype, nickName + "",newLoginTimes+"");
						} else {
							showname = username;
							if (BaseActivity.isMobileNO(LoginActivity.this.userName))
								LoginActivity.this.sy_seference.savePreferenceData(uid, pass, phone, LoginActivity.this.loginTick, username, "5", nickName + "",newLoginTimes+"");
							else {


								LoginActivity.this.sy_seference.savePreferenceData(uid, pass, phone, LoginActivity.this.loginTick, username, "4", nickName + "", newLoginTimes+"");


							}
						}

						AppConfig.saveMap(LoginActivity.this.userName, LoginActivity.this.passWord, uid);

//                        HashMap hs = LoginActivity.this.sy_seference.getUserInfo(uid);
//                        String str = (String) hs.get("loginTimes");
//                        a = Integer.parseInt(str);

                        //保存登录成功后的数据
                        //跳转到绑定手机号页面
                        // TODO: 2017/8/12 把账号传递过去给绑定界面
                        Log.d("LoginA_uid=",showname);
                        if ((newLoginTimes%5)==0) {

                            Intent intents = new Intent();
                            Bundle myAccount = new Bundle();
                            myAccount.putString("key_account",showname);
                            intents.putExtras(myAccount);
                            intents.setClass(LoginActivity.this, Sy_BindPhoneActivity.class);
                            startActivity(intents);

                        }

						LoginActivity.this.sendData(AppConfig.FLAG_SUCCESS, showname, LoginActivity.this.mHandler);

					} else {
						result = "fail";

						LoginActivity.this.wrapaLoginInfo(result, msg, username, uid, timeStamp, LoginActivity.this.loginTick);
						LoginActivity.this.sendData(AppConfig.FLAG_FAIL, message, LoginActivity.this.mHandler);
					}
				} else {
					LoginActivity.this.sendData(AppConfig.FLAG_FAIL, "获取数据失败!", LoginActivity.this.mHandler);
				}
			}

			public void onError(int statusCode)
			{
				LoginActivity.this.sendData(AppConfig.FLAG_REQUEST_ERROR, "", LoginActivity.this.mHandler);
			}
		});
	}

	private void thirdquery(final String openid, final String logintype, final String nickname)
	{
		this.thirdQueryTask = SiJiuSDK.get().thirdQuery(this, this.appId, this.appKey, logintype, openid, AppConfig.ver_id, new ApiRequestListener()
		{
			public void onSuccess(Object obj)
			{
				if (LoginActivity.this.dialog != null) {
					LoginActivity.this.dialog.dismiss();
				}
				if (obj != null) {
					ResultAndMessage msg = (ResultAndMessage)obj;
					boolean result = msg.getResult();
					if (result) {
						if (logintype.equals("1")) {
							String accountname = msg.getAccountname();
							LoginActivity.this.login(accountname, "QQ_" + openid, logintype, nickname);
						} else {
							String accountname = msg.getAccountname();
							LoginActivity.this.login(accountname, "Wechat_" + openid, logintype, nickname);
						}

					}
					else if (logintype.equals("1"))
						LoginActivity.this.thirdregist(LoginActivity.this.thuserName, LoginActivity.this.thpassword, openid, nickname, logintype);
					else
						LoginActivity.this.thirdregist(LoginActivity.this.thuserName, LoginActivity.this.thpassword, openid, nickname, logintype);
				}
				else
				{
					LoginActivity.this.sendData(1, "获取数据失败!", LoginActivity.this.mHandler);
				}
			}

			public void onError(int statusCode)
			{
				LoginActivity.this.sendData(2, "", LoginActivity.this.mHandler);
			}
		});
	}

	private void thirdregist(final String username, final String password, final String openid, final String nickname, final String logintype)
	{
		this.thirdRegistTask = SiJiuSDK.get().thirdRegist(this, this.appId, this.appKey, username, password, logintype, openid, AppConfig.ver_id, new ApiRequestListener()
		{
			public void onSuccess(Object obj)
			{
				if (LoginActivity.this.dialog != null) {
					LoginActivity.this.dialog.dismiss();
				}
				if (obj != null) {
					ResultAndMessage msg = (ResultAndMessage)obj;
					boolean result = msg.getResult();
					if (result)
					{
						HashMap hasmap = new HashMap();
						hasmap.put("username", username);
						hasmap.put("password", password);
						hasmap.put("openid", openid);
						hasmap.put("nickname", nickname);
						hasmap.put("logintype", logintype);

						LoginActivity.this.sendData(AppConfig.THIRD_LOGIN , hasmap, LoginActivity.this.mHandler);
					}
					else {
						LoginActivity.this.sendData(AppConfig.FLAG_FAIL, msg.getMessage(), LoginActivity.this.mHandler);
					}
				}
				else {
					LoginActivity.this.sendData(AppConfig.FLAG_FAIL, "获取数据失败!", LoginActivity.this.mHandler);
				}
			}

			public void onError(int statusCode)
			{
				LoginActivity.this.sendData(AppConfig.FLAG_REQUEST_ERROR, "", LoginActivity.this.mHandler);
			}
		});
	}



	private void ticklogin() {
		tickloginTask = SiJiuSDK.get().startTickLogon(LoginActivity.this, appId,
				appKey, loginToken, verId,
				new ApiRequestListener() {

					@Override
					public void onSuccess(Object obj) {
						// TODO Auto-generated method stub
						// 取消进度条
						if (dialog != null) {
							dialog.dismiss();
						}
						if(obj!=null){

							LoginMessage loginmsg = (LoginMessage) obj;
							String result = "";
							String timeStamp = loginmsg.getTimestamp();
							String uid = String.valueOf(loginmsg.getUid());
							String username = loginmsg.getUserName();
							String message = loginmsg.getMessage();

							String msg = loginmsg.getMessage();
							loginTick = loginmsg.getLoginTick();
							String phone=loginmsg.getPhoneNumber();
							AppConfig.isLogin=true;

							if (loginmsg.getResult()) {
								result = "success";
								AppConfig.clear();

								LoginActivity.this.wrapaLoginInfo(result, msg, username, uid, timeStamp, LoginActivity.this.loginTick);
								// 准备保存账号密码和uid

								AppConfig.saveMap(userName, passWord, uid);
								sendData(AppConfig.FLAG_SUCCESS, userName, mHandler);

							} else {
								result = "fail";
								callBack("fail", message, "", "", "","");
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										findViewById(AppConfig.resourceId(
												LoginActivity.this, "linear", "id")).setVisibility(View.VISIBLE);

										if (pdialog != null) {
											pdialog.dismiss();
										}
									}
								});
								wrapaLoginInfo(result, msg, username, uid, timeStamp, loginTick);
								sendData(AppConfig.FLAG_FAIL, message, mHandler);

							}
						}else{

							sendData(AppConfig.FLAG_FAIL, "获取数据失败!", mHandler);
						}

					}
					@Override
					public void onError(int statusCode) {
						// TODO Auto-generated method stub

						sendData(AppConfig.FLAG_REQUEST_ERROR, "", mHandler);

					}
				});
	}

	private class BaseUiListener
			implements IUiListener
	{
		private BaseUiListener()
		{
		}

		public void onCancel()
		{
		}

		public void onComplete(Object response)
		{
			Toast.makeText(LoginActivity.this.getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
			try
			{
				LoginActivity.this.openid = ((JSONObject)response).getString("openid");
				LoginActivity.this.qqaccess = ((JSONObject)response).getString("access_token");
				LoginActivity.this.qqexpires = ((JSONObject)response).getString("expires_in");
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}

			QQToken qqToken = LoginActivity.this.mTencent.getQQToken();
			LoginActivity.this.mTencent.setAccessToken(LoginActivity.this.qqaccess, LoginActivity.this.qqexpires);
			LoginActivity.this.mTencent.setOpenId(LoginActivity.this.openid);

			UserInfo info = new UserInfo(LoginActivity.this.getApplicationContext(), qqToken);

			info.getUserInfo(new IUiListener()
			{
				public void onComplete(Object response)
				{
					try
					{
						String nickname = ((JSONObject)response).getString("nickname");
						HashMap hashMap = new HashMap();
						String user = LoginActivity.this.ramrule();
						hashMap.put("userName", user);
						hashMap.put("password", "QQ_" + LoginActivity.this.openid);
						hashMap.put("openid", LoginActivity.this.openid);
						hashMap.put("nickname", nickname);
						hashMap.put("loginType", "1");
						Message ms = LoginActivity.this.handler.obtainMessage();
						ms.obj = hashMap;
						ms.what = AppConfig.THIRD_REGIST;
						LoginActivity.this.handler.sendMessage(ms);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				public void onCancel()
				{
				}

				public void onError(UiError e)
				{
					LoginActivity.this.showMsg("code:" + e.errorCode + ", msg:" + e.errorMessage + ", detail:" + e.errorDetail);
				}
			});
		}

		public void onError(UiError uiError)
		{
		}
	}

	private class myPopupWindow extends TimerTask
	{
		private myPopupWindow()
		{
		}

		public void run()
		{
			Message message = new Message();
			message.what = 3;
			LoginActivity.this.mHandler.sendMessage(message);
		}
	}
}
