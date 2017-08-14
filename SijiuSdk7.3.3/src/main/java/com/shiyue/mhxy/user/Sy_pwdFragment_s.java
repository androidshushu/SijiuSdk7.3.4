package com.shiyue.mhxy.user;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sy_pwdFragment_s.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sy_pwdFragment_s#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sy_pwdFragment_s extends Fragment implements View.OnClickListener {

    private TextView tv_findpwd_account_s;
    private TextView tv_findpwd_phone_s;
    private EditText edit_findpwd_authcode;
    private Button btn_findpwd_nexts;
    private Button btn_findpwd_authcode;
    private View view;
    private boolean send=true;
    private Handler handler;
    private String account = "";
    private String authcode = "";
    private String phonenum = "";
    private ApiAsyncTask coderTask;
    private ApiAsyncTask ideTask;
    private int j = 0;
    private boolean flag = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(AppConfig.resourceId(getActivity(), "sy_fpwd_fragment_s", "layout"), container, false);
        Sy_FindpwdActivity activity = (Sy_FindpwdActivity) getActivity();
        handler = activity.handler;
        account=activity.getAccount();
        phonenum=activity.getPhonenum();
        init();

        return view;
    }


    private void init() {

        // TODO Auto-generated method stub
        tv_findpwd_account_s = (TextView) view.findViewById(AppConfig.resourceId(getActivity(), "tv_findpwd_account_s", "id"));
        tv_findpwd_phone_s = (TextView) view.findViewById(AppConfig.resourceId(getActivity(), "tv_findpwd_phone_s", "id"));
        btn_findpwd_authcode = (Button) view.findViewById(AppConfig.resourceId(getActivity(), "btn_findpwd_authcode", "id"));
        btn_findpwd_nexts = (Button) view.findViewById(AppConfig.resourceId(getActivity(), "btn_findpwd_nexts", "id"));
        edit_findpwd_authcode = (EditText) view.findViewById(AppConfig.resourceId(getActivity(), "edit_findpwd_authcode", "id"));

//        pwdEdit = (EditText) findViewById(AppConfig.resourceId(this, "register_edit_pwd", "id"));
        btn_findpwd_authcode.setOnClickListener(this);
        btn_findpwd_nexts.setOnClickListener(this);
      tv_findpwd_account_s.setText(account);
        tv_findpwd_phone_s.setText(phonenum);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == AppConfig.resourceId(getActivity(), "btn_findpwd_authcode", "id")) {
                    flag=true;
            getCodeHttp();
            btn_findpwd_authcode.setClickable(false);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub

                    while (flag) {
//                        if(!send){
//                            j=60;
//
//                        }
                        myHandler
                                .sendEmptyMessage(AppConfig.FLAG_PROGRESS_TAG);
                        try {
                            Thread.sleep(900);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } else if (v.getId() == AppConfig.resourceId(getActivity(), "btn_findpwd_nexts", "id")) {
            identifyCode();
        }
    }

    private void identifyCode() {
        String auth_code=edit_findpwd_authcode.getText().toString();
        authcode=auth_code;
        ideTask = SiJiuSDK.get().startCheck_sms(getActivity(), AppConfig.appId,AppConfig.appKey,
                AppConfig.ver_id,"findpwd_auth" ,auth_code,phonenum, new ApiRequestListener() {

                    @Override
                    public void onSuccess(Object obj) {
                        ResultAndMessage msg = (ResultAndMessage) obj;
                        boolean result = msg.getResult();
                        String message = msg.getMessage();
                        // TODO Auto-generated method stub
                        if (obj != null) {
                            if(result){
                                sendData(AppConfig.FLAG_SUCCESS, message, myHandler);
                            }else{

                                sendData(AppConfig.FLAG_FAIL, message, myHandler);
//                                send=false;
                            }

                        } else {
                            sendData(AppConfig.FLAG_FAIL, "获取数据失败!", myHandler);
                        }
                    }

                    @Override
                    public void onError(int statusCode) {
                        // TODO Auto-generated method stub
                        sendData(AppConfig.FLAG_REQUEST_ERROR, "链接出错，请重试!",
                                myHandler);
                    }
                });

    }


    /**
     * http请求,获取短信验证码请求
     * 请求类型
     * 手机注册              sms_type:phone_reg_login
     手机验证码登录   sms_type: phone_reg_login
     密码重置              sms_type: findpwd_auth
     手机绑定              sms_type: phone_bind_auth
     */

    public void getCodeHttp() {
        coderTask = SiJiuSDK.get().startGetCodeFPWD(getActivity(), AppConfig.appId,AppConfig.appKey,
                AppConfig.ver_id,phonenum, new ApiRequestListener() {

                    @Override
                    public void onSuccess(Object obj) {
                        if (obj != null) {
                            ResultAndMessage msg = (ResultAndMessage) obj;
                            boolean result = msg.getResult();
                            String message = msg.getMessage();
                            // TODO Auto-generated method stub

                            if(result){
                                //                            sendData(AppConfig.FLAG_MSG_CODE, message, myHandler);
                            } else {
//                                flag=false;
                                sendData(AppConfig.FLAG_FAIL, message, myHandler);
                            }
                        }else{
                            sendData(AppConfig.FLAG_FAIL, "获取数据失败!", myHandler);
                        }


                    }

                    @Override
                    public void onError(int statusCode) {
                        // TODO Auto-generated method stub
                        sendData(AppConfig.FLAG_REQUEST_ERROR, "链接出错，请重试!",
                                myHandler);
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
     * toast 提示信息
     *
     * @param msg
     */
    public void showMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case AppConfig.FLAG_PROGRESS_TAG:
                    if ((60 - j) == 0) {
                        btn_findpwd_authcode.setClickable(true);
                        flag = false;
                        btn_findpwd_authcode.setText("获取验证码");
                        j = 0;
                    } else {
                        btn_findpwd_authcode.setText((60 - j) + "s");
                    }
                    j++;
                    break;
                case AppConfig.FLAG_SUCCESS:
                    Message ms = handler.obtainMessage();
                    ms.what = AppConfig.FINDPWD_S;
                    HashMap hashMap=new HashMap();
//                    hashMap.put("phone",phonenum);
                    hashMap.put("account",account);
                    hashMap.put("auth_code",authcode);

                    ms.obj = hashMap;
                    handler.sendMessage(ms);
                    break;
                case AppConfig.FLAG_FAIL:
                    String result = (String) msg.obj;

                    showMsg(result);
                    break;
                case AppConfig.FLAG_REQUEST_ERROR:
                    showMsg("网络连接失败，请检查您的网络连接");
                    break;
            }

        }
    };

}
