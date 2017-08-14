package com.shiyue.mhxy.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.shiyue.mhxy.common.BaseActivity;

import com.shiyue.mhxy.common.TipsDialog;
import com.shiyue.mhxy.config.AppConfig;
import com.shiyue.mhxy.http.ApiAsyncTask;
import com.shiyue.mhxy.http.ApiRequestListener;
import com.shiyue.mhxy.sdk.SiJiuSDK;

import java.util.HashMap;

import static com.shiyue.mhxy.user.LoginActivity._instance;


public class SetpwdActivity extends BaseActivity implements OnClickListener {

    private EditText edit_setpwd;
    private Button phlogin;
    private String phonenum = "";
    private String authcode = "";
    private ApiAsyncTask PhregTask;
    private ImageView back;
    private String pwd = "";
    private TipsDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

//        if (AppConfig.ISPORTRAIT) {
//            setContentView(AppConfig.resourceId(this, "sjlogin_main_port",
//                    "layout"));
//        } else {
            setContentView(AppConfig.resourceId(this, "syphloginpwd_land",
                    "layout"));
//        }

        Intent intent = getIntent();
        authcode = intent.getStringExtra("authcode");
        phonenum = intent.getStringExtra("phonenum");
        init();

	}




	private void init() {
		edit_setpwd = (EditText) findViewById(AppConfig.resourceId(this, "edit_setpwd",
				"id"));
		phlogin = (Button) findViewById(AppConfig.resourceId(this,
				"phlogin", "id"));
        back = (ImageView) findViewById(AppConfig.resourceId(this,
                "imbtn_back_login", "id"));
		phlogin.setOnClickListener(this);
        back.setOnClickListener(this);

	}

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == AppConfig.resourceId(this, "phlogin", "id")) {


            if (!matches(edit_setpwd.getText().toString())
                    || !matches(edit_setpwd.getText().toString())) {
                showMsg("账号和密码必须为6-20位数字、字母、下划线");

            }else{

                pwd = edit_setpwd.getText().toString();
                setpwd();
                dialog = new TipsDialog(this, AppConfig.resourceId(this, "Sj_MyDialog", "style"), new TipsDialog.DialogListener() {

                    @Override
                    public void onClick() {
                        if (PhregTask != null) {
                            PhregTask.cancel(true);
                        }

                    }

                });
            }
        }else if(v.getId() == AppConfig.resourceId(this, "imbtn_back_login", "id")){

            SetpwdActivity.this.finish();
        }



	}
	/**
	 * 正则匹配
	 * \\w{6,18}匹配所有字母、数字、下划线 字符串长度6到20（不含空格）
	 */
	private boolean matches(String text) {
		String format = "\\w{6,20}+";
		if (text.matches(format)) {
			return true;
		}
		return false;
	}

    private void setpwd() {

        PhregTask = SiJiuSDK.get().startPhoneregist(SetpwdActivity.this, AppConfig.appId,
                AppConfig.appKey, AppConfig.ver_id, authcode, pwd, phonenum,
                new ApiRequestListener() {

                    @Override
                    public void onSuccess(Object obj) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        ResultAndMessage msg = (ResultAndMessage) obj;
                        boolean result = msg.getResult();
                        String message = msg.getMessage();
//                        String data = msg.getData();
                        if (result) {

                            sendData(AppConfig.FLAG_SUCCESS, message, myHandler);
                        } else {
                            sendData(AppConfig.FLAG_FAIL, message, myHandler);
                        }
                    }

                    @Override
                    public void onError(int statusCode) {
                        // TODO Auto-generated method stub
//						dialog.dismiss();
                        sendData(AppConfig.FLAG_REQUEST_ERROR, "网络连接失败，请检查您的网络连接!",
                                myHandler);
                    }
                });

    }











    /**
     * 处理消息
     */
    private Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (dialog != null) {
                dialog.dismiss();
            }
            String result = (String) msg.obj;
            switch (msg.what) {
                case AppConfig.FLAG_SUCCESS:
                    showMsg("注册成功");
                    AppConfig.isFirst=true;
//                    AppConfig.isFirst = true;

                    AppConfig.tempMap.put("user", phonenum);
                    AppConfig.tempMap.put("password", pwd);

                    Message ms = _instance.handler.obtainMessage();
//                    ms.what =AppConfig.REGIST_SUCCESS;
//                    HashMap hashMap=new HashMap();
//                    hashMap.put("user",userName);
//                    hashMap.put("password",passWord);
//                    ms.obj = hashMap;
//                    _instance.handler.sendMessage(ms);
                    ms.what =AppConfig.REGIST_SUCCESS;
                    HashMap hashMap=new HashMap();
                    hashMap.put("user",phonenum);
                    hashMap.put("password",pwd);
                    ms.obj = hashMap;
                    _instance.handler.sendMessage(ms);

                    SetpwdActivity.this.finish();
                    break;
                case AppConfig.FLAG_FAIL:
                    showMsg(result);
                    break;
                case AppConfig.FLAG_REQUEST_ERROR:
                    showMsg(result);
                    break;
                default:
                    break;
            }
        }
    };
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
}
