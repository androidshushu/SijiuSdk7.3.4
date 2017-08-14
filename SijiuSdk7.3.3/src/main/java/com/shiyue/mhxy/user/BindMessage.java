package com.shiyue.mhxy.user;

/**
 * Created by shushu on 2017/8/10.
 */

public class BindMessage {
    private boolean result;//
    private String message = "";//描述信息
    private String timestamp ="";//服务器当前时间（时间戳）
    private String uid="";//用户登录uid账号
    private String userName="";//登录用户名
    private String phonenumber="";//手机号
    private String token = "";//签名
    private String logintick = "";//客户端与SDK，MD5加密字符串
    private int code;//服务器返回码
    @Override
    public String toString() {
        return "BindMessage{" +
                "result=" + result +
                ", message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", uid='" + uid + '\'' +
                ", userName='" + userName + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", token='" + token + '\'' +
                ", logintick='" + logintick + '\'' +
                ", code=" + code +
                '}';
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogintick() {
        return logintick;
    }

    public void setLogintick(String logintick) {
        this.logintick = logintick;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }






}
