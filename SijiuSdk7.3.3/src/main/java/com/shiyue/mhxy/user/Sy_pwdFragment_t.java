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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sy_pwdFragment_t.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sy_pwdFragment_t#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sy_pwdFragment_t extends Fragment implements View.OnClickListener {

    private EditText edit_setpwd_t;
    private EditText edit_vtfpwd_t;
    private TextView tv_account_t;
    private Button btn_setpwd;

    private View view;

    private Handler handler;
    private String account,password = "";
    private String authcode = "";
    private ApiAsyncTask changepwdTask;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(AppConfig.resourceId(getActivity(), "sy_fpwd_fragment_t", "layout"), container, false);
        Sy_FindpwdActivity activity = (Sy_FindpwdActivity) getActivity();
        handler = activity.handler;
        account=activity.getAccount();
       authcode=activity.getCode();
        init();

        return view;
    }



    private void init() {

        // TODO Auto-generated method stub
        edit_setpwd_t = (EditText) view.findViewById(AppConfig.resourceId(getActivity(), "edit_setpwd_t", "id"));
        edit_vtfpwd_t = (EditText) view.findViewById(AppConfig.resourceId(getActivity(), "edit_vtfpwd_t", "id"));
        tv_account_t = (TextView) view.findViewById(AppConfig.resourceId(getActivity(), "tv_account_t", "id"));
        btn_setpwd = (Button) view.findViewById(AppConfig.resourceId(getActivity(), "btn_setpwd", "id"));


        btn_setpwd.setOnClickListener(this);
        tv_account_t.setText(account);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == AppConfig.resourceId(getActivity(), "btn_setpwd", "id")) {

            if(edit_setpwd_t.getText().toString().equals("")){
                showMsg("密码不能为空");
            }else if(edit_vtfpwd_t.getText().toString().equals("")){
                showMsg("密码不能为空");
            }else if(edit_setpwd_t.getText().toString().equals(edit_vtfpwd_t.getText().toString())){
                password=edit_setpwd_t.getText().toString();
                changepwd();
            }else{
                showMsg("两次输入的密码不一致，请重新输入");
            }


        }
    }

    private void changepwd() {

        changepwdTask = SiJiuSDK.get().startChangePassword(getActivity(), AppConfig.appId,AppConfig.appKey,
                AppConfig.ver_id ,password,authcode, new ApiRequestListener() {

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


                case AppConfig.FLAG_SUCCESS:
                    String result0="";
                    result0 = (String) msg.obj;
                    showMsg(result0);
                    Message ms = handler.obtainMessage();
                    ms.what = AppConfig.FINDPWD_T;


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
