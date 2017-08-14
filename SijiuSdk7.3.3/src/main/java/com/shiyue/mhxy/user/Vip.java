package com.shiyue.mhxy.user;

public class Vip {
private boolean result;
private String msg;
public boolean isResult() {
	return result;
}
@Override
public String toString() {
	return "Vip [result=" + result + ", msg=" + msg + "]";
}
public void setResult(boolean result) {
	this.result = result;
}
public String getMsg() {
	return msg;
}
public void setMsg(String msg) {
	this.msg = msg;
}
}
