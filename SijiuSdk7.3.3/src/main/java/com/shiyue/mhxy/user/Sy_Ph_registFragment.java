package com.shiyue.mhxy.user;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shiyue.mhxy.config.AppConfig;
import com.shiyue.mhxy.http.ApiAsyncTask;
import com.shiyue.mhxy.http.ApiRequestListener;
import com.shiyue.mhxy.sdk.SiJiuSDK;

import static com.shiyue.mhxy.user.LoginActivity._instance;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sy_Ph_registFragment} interface
 * to handle interaction events.
 * Use the {@link Sy_Ph_registFragment} factory method to
 * create an instance of this fragment.
 */
public class Sy_Ph_registFragment extends Fragment implements View.OnClickListener {

    private ImageView cb_ischeck_ph;
    private TextView tv_agree_ph;
    private Button btn_ph_register;
    private EditText edit_idfcode;
    private EditText  edit_ph;
    private  Button btn_idfcode;
    private boolean flag = false;
    private  View view;
    private boolean isagree= true;
    private String appKey = "";
    private String verId = "";
    private int appId;
    private String phone = "";
    private String authcode = "";
    private   LoginMessage loginmsg;
    private int j = 0;
    private ApiAsyncTask codeTask;
    private ApiAsyncTask phTask;
    private String type="0";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        view = inflater.inflate(AppConfig.resourceId(getActivity(), "sy_phregist_fragment", "layout"), container, false);
//        mLeftMenu = (ImageButton) view.findViewById(R.id.id_title_left_btn);
        Bundle bundle=getArguments();
        type=bundle.getString("type");
        init();
        appId = AppConfig.appId;
        appKey = AppConfig.appKey;
        verId=AppConfig.ver_id;

        return view;

    }

    private void init() {
        cb_ischeck_ph = (ImageView) view.findViewById(AppConfig.resourceId(getActivity(), "cb_ischeck_ph", "id"));
        tv_agree_ph = (TextView)view.findViewById(AppConfig.resourceId(getActivity(), "tv_agree_ph", "id"));
        btn_ph_register = (Button) view.findViewById(AppConfig.resourceId(getActivity(), "btn_ph_register", "id"));
        edit_idfcode = (EditText) view.findViewById(AppConfig.resourceId(getActivity(), "edit_idfcode", "id"));
        edit_ph = (EditText) view.findViewById(AppConfig.resourceId(getActivity(), "edit_ph_fragment", "id"));
        btn_idfcode = (Button) view.findViewById(AppConfig.resourceId(getActivity(), "btn_idfcode", "id"));
//        cb_check = (ImageButton) view.findViewById(AppConfig.resourceId(getActivity(), "register_edit_user", "id"));
//        pwdEdit = (EditText) findViewById(AppConfig.resourceId(this, "register_edit_pwd", "id"));
        if(AppConfig.isAgreement.equals("0")){
            cb_ischeck_ph.setVisibility(view.GONE);
            tv_agree_ph.setVisibility(View.GONE);
        }
        //控制按钮登录还是注册
        if(type.equals("1")){
            btn_ph_register.setText("登录");
        }else {
            btn_ph_register.setText("注册");
        }
        cb_ischeck_ph.setOnClickListener(this);
        tv_agree_ph.setOnClickListener(this);
        btn_ph_register.setOnClickListener(this);
        edit_idfcode.setOnClickListener(this);
        edit_ph.setOnClickListener(this);
        btn_idfcode.setOnClickListener(this);
//        cb_check.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() ==AppConfig.resourceId(getActivity(), "btn_idfcode", "id")) {
            if(isMobileNO(edit_ph.getText().toString())){


                    phone = edit_ph.getText().toString();
                    flag = true;

                    btn_idfcode.setClickable(false);
                    btn_idfcode.setText("60s");
;
                    getCodeHttp();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            while (flag) {
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
            }else{

                showMsg("请输入正确的手机号码");
            }


        }
        else if (v.getId() ==AppConfig.resourceId(getActivity(), "btn_ph_register", "id")){
            if(!edit_idfcode.getText().toString().equals("")){
                if(isMobileNO(edit_ph.getText().toString())) {
                    if(isagree) {
                       phlogin();
                    }else{
                        showMsg("请认真阅读并同意诗悦网络条款");
                    }

                }else{

                    showMsg("请输入正确的手机号码");
                }

            }else{

                showMsg("验证码不能为空");

            }

        }   else if (v.getId() == AppConfig.resourceId(getActivity(), "tv_agree_ph", "id")) {
            Intent intent=new Intent(getActivity(),AgreeActivity.class);
            startActivity(intent);

        }
        else if (v.getId() == AppConfig.resourceId(getActivity(), "cb_ischeck_ph", "id")) {
            if (!isagree) {
                isagree = true;
                cb_ischeck_ph.setBackgroundResource(this.getResources().getIdentifier("sy_cb_checked", "drawable", this.getActivity().getPackageName()));

            } else {
                isagree = false;
                cb_ischeck_ph.setBackgroundResource(this.getResources().getIdentifier("sy_cb_unchecked", "drawable", this.getActivity().getPackageName()));

            }

        }
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
     * toast 提示信息
     *
     * @param msg
     */
    public void showMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
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
        codeTask = SiJiuSDK.get().startGetCodeBoundPhone(getActivity(), appId,appKey,
                AppConfig.ver_id,"phone_reg_login" ,phone, new ApiRequestListener() {

                    @Override
                    public void onSuccess(Object obj) {
                        if(obj!=null){
                            ResultAndMessage msg = (ResultAndMessage) obj;
                            boolean result = msg.getResult();
                            String message = msg.getMessage();
                            // TODO Auto-generated method stub
                            if(result){
//                                sendData(AppConfig.FLAG_MSG_CODE, message, myHandler);
                            } else {
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



    private void phlogin() {
        authcode=edit_idfcode.getText().toString();
        phone=edit_ph.getText().toString();
        phTask = SiJiuSDK.get().startphLogon(getActivity(), AppConfig.appId,
                AppConfig.appKey, phone, authcode, AppConfig.ver_id,
                new ApiRequestListener() {

                    @Override
                    public void onSuccess(Object obj) {
                        // TODO Auto-generated method stub
                        // 取消进度条
                   if(obj!=null){

                       loginmsg= (LoginMessage) obj;

                       boolean result = loginmsg.getResult();
                       String message = loginmsg.getMessage();

                       if (result) {

                               sendData(AppConfig.PH_LOGIN_SUCCESS, message, myHandler);

                       } else {

                           if(loginmsg.getCode()==1101){

                               Intent intent=new Intent(getActivity(),SetpwdActivity.class);
                               intent.putExtra("authcode",edit_idfcode.getText().toString());
                               intent.putExtra("phonenum",edit_ph.getText().toString());

                               startActivity(intent);
                               getActivity().finish();
                           }else {
                               sendData(AppConfig.FLAG_FAIL, message, myHandler);

                           }// showMsg(msg);}
                       }

             }else{
                       sendData(AppConfig.FLAG_FAIL, "获取数据失败!", myHandler);
                   }

                    }


                    @Override
                    public void onError(int statusCode) {
                        // TODO Auto-generated method stub
                        sendData(AppConfig.FLAG_REQUEST_ERROR, "", myHandler);
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


    private Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case AppConfig.FLAG_PROGRESS_TAG:
                    if ((60 - j) == 0) {
                        btn_idfcode.setClickable(true);
                        flag = false;
                        btn_idfcode.setText("获取验证码");
                        j = 0;
                    } else {
                        btn_idfcode.setText((60 - j) + "s");
                    }
                    j++;
                    break;
//                case AppConfig.FLAG_SUCCESS:
//
//
//                    break;
                case AppConfig.FLAG_FAIL:
                    String result = (String) msg.obj;

                    showMsg(result);
                    break;
                case AppConfig.PH_LOGIN_SUCCESS:
                    Message ms = _instance.handler.obtainMessage();
                    ms.what =AppConfig.PH_LOGIN_SUCCESS;
                    //返回登录信息给接口
                    if(loginmsg!=null){
                        ms.obj = loginmsg;
                        _instance.handler.sendMessage(ms);
                        getActivity().finish();
                    }


                    break;
                case AppConfig.FLAG_REQUEST_ERROR:
                    showMsg("网络连接失败，请检查您的网络连接");
                    break;
            }

        }
    };

}


