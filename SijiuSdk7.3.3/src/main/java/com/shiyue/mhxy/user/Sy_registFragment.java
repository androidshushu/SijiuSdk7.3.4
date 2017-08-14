package com.shiyue.mhxy.user;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.shiyue.mhxy.common.Phonedialog;
import com.shiyue.mhxy.common.TipsDialog;
import com.shiyue.mhxy.config.AppConfig;
import com.shiyue.mhxy.http.ApiAsyncTask;
import com.shiyue.mhxy.http.ApiRequestListener;
import com.shiyue.mhxy.sdk.SiJiuSDK;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import static com.shiyue.mhxy.user.LoginActivity._instance;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sy_registFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sy_registFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sy_registFragment extends Fragment implements View.OnClickListener {
    private ImageView iv_reflash;
    private ImageView iv_showpass;
    private Button btn_regist;
    private EditText ed_username;
    private EditText ed_pwd;
    private String userName = "", passWord = "";
    private TextView iv_agree;
    private ImageView cb_check;
    private boolean eye_ischeck = true;
    private boolean isagree= true;
    private View view;
    private Phonedialog pdialog;
    private String appKey = "";
    private String verId = "";
    private int appId;
    private boolean rf = true;
    private TipsDialog dialog;
//    private Handler handler;
    private ApiAsyncTask registerTask;
    //记录一键注册事件
    public static long thisTime;
    public static long lastTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(AppConfig.resourceId(getActivity(), "sy_regist_fragment", "layout"), container, false);
//        mLeftMenu = (ImageButton) view.findViewById(R.id.id_title_left_btn);

        init();
        appId = AppConfig.appId;
        appKey = AppConfig.appKey;
        verId = AppConfig.ver_id;
//        LoginActivity loginActivity=_instance;
//        handler=loginActivity.handler;

        return view;
    }

    private void init() {

        // TODO Auto-generated method stub
        iv_reflash = (ImageView) view.findViewById(AppConfig.resourceId(getActivity(), "iv_reflash_frg", "id"));
        iv_showpass = (ImageView) view.findViewById(AppConfig.resourceId(getActivity(), "iv_password_show_frg", "id"));
        btn_regist = (Button) view.findViewById(AppConfig.resourceId(getActivity(), "btn_regist", "id"));
        ed_username = (EditText) view.findViewById(AppConfig.resourceId(getActivity(), "edit_user_frg", "id"));
        ed_pwd = (EditText) view.findViewById(AppConfig.resourceId(getActivity(), "edit_pwd_frg", "id"));
        iv_agree = (TextView) view.findViewById(AppConfig.resourceId(getActivity(), "tv_agree", "id"));
        cb_check = (ImageView) view.findViewById(AppConfig.resourceId(getActivity(), "cb_ischeck", "id"));
//        pwdEdit = (EditText) findViewById(AppConfig.resourceId(this, "register_edit_pwd", "id"));
        if(AppConfig.isAgreement.equals("0")){
            cb_check.setVisibility(view.GONE);
            iv_agree.setVisibility(View.GONE);
        }
        iv_reflash.setOnClickListener(this);
        iv_showpass.setOnClickListener(this);
        btn_regist.setOnClickListener(this);
        ed_username.setOnClickListener(this);
        ed_pwd.setOnClickListener(this);
        iv_agree.setOnClickListener(this);
        cb_check.setOnClickListener(this);
        if (rf) {
            ramrule();
            rf = false;

        }

    }
    /*
 * 将时间戳转换为时间
 */
    public String stampToDate(long timeMillis){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == AppConfig.resourceId(getActivity(), "iv_reflash_frg", "id")) {
            ramrule();

        } else if (v.getId() == AppConfig.resourceId(getActivity(), "iv_password_show_frg", "id")) {

            if (!eye_ischeck) {
                eye_ischeck = true;
                iv_showpass.setBackgroundResource(this.getResources().getIdentifier("sy_eye_green", "drawable", this.getActivity().getPackageName()));
                ed_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                eye_ischeck = false;
                iv_showpass.setBackgroundResource(this.getResources().getIdentifier("sy_eye", "drawable", this.getActivity().getPackageName()));
                ed_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        } else if (v.getId() == AppConfig.resourceId(getActivity(), "btn_regist", "id")) {
            if(isagree) {
                //两分钟内不许重复注册。
                thisTime = System.currentTimeMillis();
                if ((thisTime-lastTime)>120000){
                    Log.i("shiyue","lastTime="+stampToDate(lastTime));
                    Log.i("shiyue","thistime="+stampToDate(thisTime));
                    verfyInfo();
                }else {
                    showMsg("你的注册操作过于频繁，请稍后再试。");
                }

            }else{
                showMsg("请认真阅读并同意诗悦网络条款");
            }
        }
        else if (v.getId() == AppConfig.resourceId(getActivity(), "tv_agree", "id")) {
            Intent intent=new Intent(getActivity(),AgreeActivity.class);
            startActivity(intent);

        }
        else if (v.getId() == AppConfig.resourceId(getActivity(), "cb_ischeck", "id")) {
            if (!isagree) {
                isagree = true;
                cb_check.setBackgroundResource(this.getResources().getIdentifier("sy_cb_checked", "drawable", this.getActivity().getPackageName()));

            } else {
                isagree = false;
                cb_check.setBackgroundResource(this.getResources().getIdentifier("sy_cb_unchecked", "drawable", this.getActivity().getPackageName()));

            }

        }
    }

    private void verfyInfo() {
        // TODO Auto-generated method stub
        if (!verfyRegister(ed_username, ed_pwd)) {
            userName = ed_username.getText().toString();
            passWord = ed_pwd.getText().toString();

            register();
            dialog = new TipsDialog(getActivity(), AppConfig.resourceId(getActivity(), "Sj_MyDialog", "style"), new TipsDialog.DialogListener() {

                @Override
                public void onClick() {
                    if (registerTask != null) {
                        registerTask.cancel(true);
                    }

                }

            });
            dialog.show();
            dialog.setCancelable(false);
        }
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
     * 判断输入框中的数据是否符合格式
     */
    public boolean verfyRegister(EditText user, EditText pwd) {

        if (user != null && pwd != null) {
//            if (user.getText() == null || "".equals(user.getText().toString())) {
//                showMsg("请输入账号!");
//                return true;
//            } else if (pwd.getText() == null
//                    || "".equals(pwd.getText().toString())) {
//                showMsg("请输入密码!");
//                return true;
//            }
          if (!matches(user.getText().toString())
                    || !matchespwd(pwd.getText().toString())) {
              if(!matches(user.getText().toString())){
                  showMsg("账号必须为首位为字母，长度为6-20位的数字、字母、下划线");
              }else if(!matchespwd(pwd.getText().toString())){
                  showMsg("密码必须为长度为6-18位的数字、字母、下划线");
              }


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

    private void register() {
        registerTask = SiJiuSDK.get().startRegister(getActivity(), appId, appKey,
                userName, passWord, verId, new ApiRequestListener() {

                    @Override
                    public void onSuccess(Object obj) {
                        lastTime = System.currentTimeMillis();
                        // TODO Auto-generated method stub
                        if(dialog!=null){
                            dialog.dismiss();
                        }

                        if(obj!=null) {
                            ResultAndMessage msg = (ResultAndMessage) obj;
                            boolean result = msg.getResult();
                            String message = msg.getMessage();
//                        String data = msg.getData();
                            if (result) {
                                Looper.prepare();
                                pdialog = new Phonedialog(getActivity(), AppConfig.resourceId(getActivity(),
                                        "Sj_Transparent", "style"), AppConfig.resourceId(getActivity(),
                                        "sy_logindialog", "layout"), "", "saveaccount", new Phonedialog.Phonelistener() {

                                    @Override
                                    public void onClick(View view, String text, String from) {
                                        if (view.getId() == getResources().getIdentifier(
                                                "dialog_phone", "id", getActivity().getPackageName())) {

                                            final GlobalScreenshot screenshot = new GlobalScreenshot(getActivity());
                                            screenshot.takeScreenshot(getActivity().getWindow().getDecorView(), new Runnable() {
                                                @Override
                                                public void run() {

                                                }
                                            }, new Runnable(){
                                               // 用于解决动画没播放完就关闭了界面导致报错
                                                @Override
                                                public void run() {
                                                    sendData(AppConfig.FLAG_SUCCESS, "", myHandler);
                                                }
                                            },true, true);

                                            if(pdialog!=null){
                                                pdialog.dismiss();
                                            }

                                        } else if (view.getId() == getResources().getIdentifier(
                                                "dialog_cancel", "id", getActivity().getPackageName())) {
                                            sendData(AppConfig.FLAG_SUCCESS, "", myHandler);
                                            if(pdialog!=null){
                                                pdialog.dismiss();
                                            }

                                        }
                                    }
                                });

                                pdialog.setCancelable(false);

                                pdialog.show();
                                //下面这个文字不是最终控制显示的，以线程里面的为准

                                pdialog.setText("是否将账号信息截图保存到系统相册");
                                Looper.loop();


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
//    //这种方法状态栏是空白，显示不了状态栏的信息
//    private void saveCurrentImage(View view)
//    {
////        //获取当前屏幕的大小
////        int width = getActivity().getWindow().getDecorView().getRootView().getWidth();
////        int height = getActivity().getWindow().getDecorView().getRootView().getHeight();
////        //生成相同大小的图片
////        Bitmap temBitmap = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 );
////        //找到当前页面的跟布局
////        View view =  getActivity().getWindow().getDecorView().getRootView();
////        //设置缓存
////        view.setDrawingCacheEnabled(true);
////        view.buildDrawingCache();
////        //从缓存中获取当前屏幕的图片
////        temBitmap = view.getDrawingCache();
//
//        //输出到sd卡
//        String sdCardPath =   Environment.getExternalStorageDirectory().getPath();
//        // 图片文件路径
//        String filePath = sdCardPath + File.separator + "screenshot.png";
//
//        SimpleDateFormat sdf = new SimpleDateFormat(
//                "yyyy-MM-dd_HH-mm-ss", Locale.CHINA);
//        String fname = "/sdcard/"+ sdf.format(new Date()) + ".png";
//        view.setDrawingCacheEnabled(true);
//        view.buildDrawingCache();
//        Bitmap bitmap = view.getDrawingCache();
//        if(bitmap != null) {
//            System.out.println("bitmap got!");
//            try{
//                FileOutputStream out = new FileOutputStream(fname);
//                bitmap.compress(Bitmap.CompressFormat.PNG,100, out);
//                System.out.println("file "  + fname + "output done.");
//            }catch(Exception e) {
//                e.printStackTrace();
//            }
//        }else{
//            System.out.println("bitmap is NULL!");
//        }
//    }



    //生成随机账号密码并填写上去
    private void ramrule() {
        String strAll = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String strCp = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String strNum = "0123456789";
        String userName = "";
        String passWord = "";
        Random random = new Random();
        //使用for循环得到6为字符
        for (int i = 0; i < 8; i++) {
            // 生成随机账号规则 随机两位大写字母加六位随季数字
            String oneChar = "";
            if (i < 2) {
                int rd = random.nextInt(26);

                oneChar = strCp.substring(rd, rd + 1);
            } else {
                int rd = random.nextInt(10);
                oneChar = strNum.substring(rd, rd + 1);
            }
//                       生成随机密码8为随机大小写数字组合
            int pd = random.nextInt(62);
            String sedChar = strAll.substring(pd, pd + 1);

            //循环加到8位
            userName += oneChar;
            passWord += sedChar;
        }
        ed_username.setText(userName);
        ed_pwd.setText(passWord);
    }
    /**
     * 正则匹配
     * \\w{6,18}匹配所有字母、数字、下划线 字符串长度6到20（不含空格）首字母英文
     */
    private boolean matches(String text) {
        String format = "^[a-zA-Z]\\w{5,19}$";
        if (text.matches(format)) {
            return true;
        }
        return false;
    }
    private boolean matchespwd(String text) {
        String format = "\\w{6,18}";
        if (text.matches(format)) {
            return true;
        }
        return false;
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
                    AppConfig.tempMap.put("user", userName);
                    AppConfig.tempMap.put("password", passWord);

                    Message ms = _instance.handler.obtainMessage();
                    ms.what =AppConfig.REGIST_SUCCESS;
                    HashMap hashMap=new HashMap();
                    hashMap.put("user",userName);
                    hashMap.put("password",passWord);
                    ms.obj = hashMap;
                    _instance.handler.sendMessage(ms);
                    if (pdialog != null) {
                        pdialog.dismiss();
                    }
                    getActivity().finish();
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

    @Override
    public void onDestroy() {
        if (dialog != null) {
            dialog.dismiss();
        }
        if (pdialog != null) {
            pdialog.dismiss();
        }
        super.onDestroy();
    }
}
