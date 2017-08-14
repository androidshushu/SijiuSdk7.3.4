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

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.shiyue.mhxy.common.TipsDialog;
import com.shiyue.mhxy.config.AppConfig;
import com.shiyue.mhxy.http.ApiAsyncTask;
import com.shiyue.mhxy.http.ApiRequestListener;
import com.shiyue.mhxy.sdk.SiJiuSDK;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sy_pwdFragment_f.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sy_pwdFragment_f#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sy_pwdFragment_f extends Fragment implements View.OnClickListener {

    private TextView tv_findpwd_phone;
    private EditText edit_findpwd_frg;
    private ImageView iv_cancel;
private Button btn_findpwd_nextf;
    private TipsDialog dialog;
    private View view;
    private Handler handler;
    private Handler phonehandler;
    private String user="";
    private String usera="";
    private ApiAsyncTask getphonetask;
    private boolean isnext=false;
private String phonenum="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(AppConfig.resourceId(getActivity(), "sy_fpwd_fragment_f", "layout"), container, false);
        Sy_FindpwdActivity activity= (Sy_FindpwdActivity) getActivity();
        handler=activity.handler;

        user=activity.getAccount();
        init();

        return view;
    }

    private void init() {

        // TODO Auto-generated method stub
        tv_findpwd_phone = (TextView) view.findViewById(AppConfig.resourceId(getActivity(), "tv_findpwd_phone", "id"));
        iv_cancel = (ImageView) view.findViewById(AppConfig.resourceId(getActivity(), "iv_cancel", "id"));
        btn_findpwd_nextf = (Button) view.findViewById(AppConfig.resourceId(getActivity(), "btn_findpwd_nextf", "id"));
        edit_findpwd_frg = (EditText) view.findViewById(AppConfig.resourceId(getActivity(), "edit_findpwd_frg", "id"));
        iv_cancel.setOnClickListener(this);
        btn_findpwd_nextf.setOnClickListener(this);
        tv_findpwd_phone.setVisibility(View.GONE);
        edit_findpwd_frg.setText(user);
        if(!user.equals("")){
            getphonebyuser();

        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == AppConfig.resourceId(getActivity(), "iv_cancel", "id")) {

            edit_findpwd_frg.setText("");
        } else if (v.getId() == AppConfig.resourceId(getActivity(), "btn_findpwd_nextf", "id")) {
            if(edit_findpwd_frg.getText().toString().equals("")){
                showMsg("请先输入账号或者手机号");

            }else{
                dialog=new TipsDialog(getActivity(), AppConfig.resourceId(getActivity(), "Sj_MyDialog", "style"), new  TipsDialog.DialogListener(){

                    @Override
                    public void onClick() {
                        if (getphonetask != null) {
                            getphonetask.cancel(true);
                        }
                    }

                });
                dialog.show();
                dialog.setCancelable(false);
                isnext=true;
                getphonebyuser();
            }
            }

    }




    private void getphonebyuser(){
        usera= edit_findpwd_frg.getText().toString();
        getphonetask = SiJiuSDK.get().startFindPwdInfo(getActivity(), AppConfig.appId,
                AppConfig.appKey, "name", usera, AppConfig.ver_id,
                new ApiRequestListener() {

                    @Override
                    public void onSuccess(Object obj) {
                        // TODO Auto-generated method stub
//
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        if(obj!=null){

                            ResultAndMessage msg = (ResultAndMessage) obj;
                            boolean result = msg.getResult();
                            String message = msg.getMessage();
//                        String data = msg.getData();
                            if (result) {

                                phonenum= msg.getData();

                                sendData(AppConfig.FLAG_SUCCESS, message, myHandler);

                                if(isnext){
                                    if (!phonenum.equals("")){
                                        Message ms = handler.obtainMessage();
                                        ms.what = AppConfig.FINDPWD_F;
                                        HashMap hashMap=new HashMap();
                                        hashMap.put("phone",phonenum);
                                        hashMap.put("account",usera);
                                        ms.obj = hashMap;
                                        handler.sendMessage(ms);
                                        isnext=false;
                                    }else{
                                        sendData(AppConfig.FLAG_IS_NOTBANGDING,AppConfig.initMap.get("changpwd"), myHandler);
                                    }
                                }
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
//						dialog.dismiss();
                        sendData(AppConfig.FLAG_REQUEST_ERROR, "网络连接失败，请检查您的网络连接!",
                                myHandler);
                    }
                });
    }






    /**
     * toast 提示信息
     *
     * @param msg
     */
    public void showMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }



    public void sendData(int num, Object data, Handler callback) {
        Message msg = callback.obtainMessage();
        msg.what = num;
        msg.obj = data;
        msg.sendToTarget();
    }
    private  Handler myHandler= new Handler() {

        @Override
        public void handleMessage(Message msg) {
            try {
                switch (msg.what) {
                    case AppConfig.FLAG_IS_NOTBANGDING:
                        showMsg((String) msg.obj);
                        break;
                    case AppConfig.FLAG_SUCCESS:

                        if(!phonenum.equals("")) {
                            tv_findpwd_phone.setText("绑定手机号： "+phonenum);
                            tv_findpwd_phone.setVisibility(view.VISIBLE);
                        }else{


                        }
                     break;
                    case AppConfig.FLAG_FAIL:
                        String result = (String) msg.obj;
                        showMsg(result);
                        break;
                    case AppConfig.FLAG_REQUEST_ERROR:
                        showMsg("网络连接失败，请检查您的网络连接");
                        break;
                }

            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    };
}
