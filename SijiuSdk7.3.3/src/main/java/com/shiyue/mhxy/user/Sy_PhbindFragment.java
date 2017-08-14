package com.shiyue.mhxy.user;


import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shiyue.mhxy.config.AppConfig;
import com.shiyue.mhxy.http.ApiAsyncTask;
import com.shiyue.mhxy.http.ApiRequestListener;
import com.shiyue.mhxy.sdk.SiJiuSDK;

//// TODO: 2017/8/9 绑定手机号的碎片
public class Sy_PhbindFragment extends Fragment implements View.OnClickListener {
    private TextView show_account;
    private EditText bind_phonenumber;
    private EditText bind_identify;
    private Button get_identity_btn;
    private Button bind_confirm;
    private BindMessage bindMessage;

    private View view;
    private boolean send=true;
    private Handler handler;
    private String appKey = "";
    private String verId = "";
    private int appId;

    private String account="";
    private String authcode = "";
    private String phonenum="";
    private ApiAsyncTask codeTask;
    private ApiAsyncTask bindTask;
    private boolean flag = false;
    private int j = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        account = bundle.getString("account_data");
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,

                             Bundle savedInstanceState) {
        view = inflater.inflate(AppConfig.resourceId(getActivity(),"sy__phbind_fragment","layout"),container,false);
//        Sy_BindPhoneActivity activity = (Sy_BindPhoneActivity)getActivity();
//        account = activity.getAccount();
//        Log.d("accountsss",account+"");
        init();

        appId = AppConfig.appId;
        appKey = AppConfig.appKey;
        verId=AppConfig.ver_id;

        return view;
    }

    private void init() {
        show_account = (TextView) view.findViewById(AppConfig.resourceId(getActivity(),"bind_show_account","id"));
        bind_phonenumber = (EditText) view.findViewById(AppConfig.resourceId(getActivity(),"bind_phonenumber","id"));
        bind_identify = (EditText) view.findViewById(AppConfig.resourceId(getActivity(),"bind_identify","id"));

        get_identity_btn = (Button) view.findViewById(AppConfig.resourceId(getActivity(),"bind_getidentify_button","id"));
        bind_confirm = (Button) view.findViewById(AppConfig.resourceId(getActivity(),"bind_confirm","id"));

        get_identity_btn.setOnClickListener(this);
        bind_confirm.setOnClickListener(this);
        show_account.setText(account);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == AppConfig.resourceId(getActivity(),"bind_getidentify_button","id")){
            if (bind_phonenumber.getText().toString().equals("")){
                showMsg("手机号不能为空!");
            }else if (isMobileNO(bind_phonenumber.getText().toString()) ){
                phonenum = bind_phonenumber.getText().toString();
                flag = true;

                getSmsCodeHttp();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (flag){
                            smshandler
                                    .sendEmptyMessage(AppConfig.FLAG_BIND_SMS);
                            try {
                                Thread.sleep(900);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();


            }else {
                showMsg("请输入正确的手机号码!");
            }
        }else if(v.getId() ==AppConfig.resourceId(getActivity(),"bind_confirm","id")){
            if (bind_phonenumber.getText().toString().equals("")){
                showMsg("手机号不能为空!");
            }else if (bind_identify.getText().toString().equals("")){
                showMsg("验证码不能为空！");
            }else if (isMobileNO(bind_phonenumber.getText().toString()) ){
                bindPhonenumber();

            }else {
                showMsg("请输入正确的手机号码!");
            }
            }

    }

    // TODO: 2017/8/9 发送短信验证码的请求
    /**
     * http请求,获取短信验证码请求
     * 请求类型
     * 手机注册              sms_type:phone_reg_login
     手机验证码登录   sms_type: phone_reg_login
     密码重置              sms_type: findpwd_auth
     手机绑定              sms_type: phone_bind_auth
     */

    public void getSmsCodeHttp() {
        codeTask = SiJiuSDK.get().startGetCodeBoundPhone(getActivity(), appId,appKey,
                AppConfig.ver_id,"phone_bind_auth" ,phonenum, new ApiRequestListener() {

                    @Override
                    public void onSuccess(Object obj) {
                        if(obj!=null){
                            Log.d("getCode_success_obj=",obj+"");

                            ResultAndMessage msg = (ResultAndMessage) obj;
                            boolean result = msg.getResult();
                            String message = msg.getMessage();

                            // TODO Auto-generated method stub

                            if(result){
                                Log.d("getCode_success=",result+"");
//                                sendData(AppConfig.FLAG_MSG_CODE, message, myHandler);
                            } else {
                                sendData(AppConfig.FLAG_BIND_FAIL, message, smshandler);
                            }
                        }else{
                            sendData(AppConfig.FLAG_BIND_FAIL, "获取短信验证失败!", smshandler);
                        }


                    }

                    @Override
                    public void onError(int statusCode) {
                        // TODO Auto-generated method stub
                        sendData(AppConfig.FLAG_BIND_REQUEST_ERROR, "链接出错，请重试!",
                                smshandler);
                    }
                });
    }
    /**
     * 接口返回数据处理
     */
    public void sendData(int num, Object data, Handler callback) {
        Message msg = callback.obtainMessage();
        msg.what = num;
        msg.obj = data;
        msg.sendToTarget();
    }
    /**
     * http请求，发送json，发送请求账号绑定手机号
     */
    private void bindPhonenumber() {
        authcode  = bind_identify.getText().toString();
        phonenum =  bind_phonenumber.getText().toString();

        bindTask = SiJiuSDK.get().startbindphone(getActivity(), AppConfig.appId,
                AppConfig.appKey, phonenum, authcode, AppConfig.ver_id,
                new ApiRequestListener() {
                    @Override
                    public void onSuccess(Object obj) {
                        Log.d("bindPhone_success_obj=",obj+"");

                        // 取消进度条
                        if (obj != null){
                            bindMessage = (BindMessage)obj;
                            boolean result = bindMessage.getResult();
                            String message = bindMessage.getMessage();
                            if (result){
                               sendData(AppConfig.FLAG_BIND_SUCCESS,message,smshandler);
                            } else {
                                sendData(AppConfig.FLAG_BIND_FAILS,"绑定失败",smshandler);
                            }

                        }else {
                           sendData(AppConfig.FLAG_BIND_FAIL,"获取数据失败！",smshandler);
                        }
                    }

                    @Override
                    public void onError(int statusCode) {
                        sendData(AppConfig.FLAG_BIND_REQUEST_ERROR,"",smshandler);
                    }
                });

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
     * show message
     * @param msg
     */
    public void showMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * handle message to reset some text
     */

    private  Handler smshandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case AppConfig.FLAG_BIND_SMS:
                    if ((60-j)==0){
                    get_identity_btn.setClickable(true);
                    flag = false;
                    get_identity_btn.setText("获取验证码");
                    j = 0;
                }else {
                        get_identity_btn.setClickable(false);
                    get_identity_btn.setText((60-j)+"s");
                }
                j++;
                break;
                case AppConfig.FLAG_BIND_FAIL:
                    String result = (String) msg.obj;
                    showMsg(result);
                    break;
                case AppConfig.FLAG_BIND_FAILS:
                    showMsg("绑定失败");
//                    getActivity().finish();
                case AppConfig.FLAG_BIND_SUCCESS:
                        showMsg("绑定成功！");
                        getActivity().finish();

                    break;
                case AppConfig.FLAG_BIND_REQUEST_ERROR:
                    showMsg("网络连接失败，请检查您的网络连接");
                    break;

            }

        }
    };




}
