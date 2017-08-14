package com.shiyue.mhxy.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.text.TextUtils;

public class InitMessage {

	private boolean result;
	private String message;

	private String WeChat = "";
	private String QQ = "";
	private String FloatBall = "";
	private String uid = "";
	private String userName = "";
	private String change_password_brief = "";
	private String kf_qq_number = "";
	private String identify_code_brief = "";
	private String user_agreement_status = "";

//	// 服务器返回false时默认显示
//	private boolean clientFloat = true;
//
//	private boolean serviceFloat = true;
//	private boolean giftFloat = true;
//	private boolean moregameFloat = true;
//	public boolean boxFloat = true;

	// 是否自动安装客户端，默认是不安装
//	private boolean installApp = false;

	private String appNoticeDays = "";
	public List<Integer> appNoticeDayList;

	public InitMessage() {
		appNoticeDayList = new ArrayList<Integer>();
	}

	private String getAppNoticeDays() {
		return appNoticeDays;
	}

	public void setAppNoticeDays(String appNoticeDays) {
		this.appNoticeDays = appNoticeDays;
		// 同时设置appNoticeDays
		// 空的话不处理
		if (!TextUtils.isEmpty(appNoticeDays)) {
			// 分隔
			List<String> strList = Arrays.asList(appNoticeDays.split(","));

			try {
				for (String str : strList) {
					appNoticeDayList.add(Integer.parseInt(str.trim()));
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

		}
	}

	public List<Integer> getAppNoticeDayList() {
		return appNoticeDayList;
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

	public String getWeChat() {
		return WeChat;
	}

	public void setWeChat(String WeChat) {
		this.WeChat = WeChat;
	}

	public String getFloatBall() {
		return FloatBall;
	}

	public void setFloatBall(String FloatBall) {
		this.FloatBall = FloatBall;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
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

	public String getChangePwdBrief() {
		return change_password_brief;
	}

	public void setChangePwdBrief(String change_password_brief) {
		this.change_password_brief = change_password_brief;
	}
	public String getkfQQ() {
		return kf_qq_number;
	}

	public void setkfQQ(String kf_qq_number) {
		this.kf_qq_number = kf_qq_number;
	}

	public String getCodeBrief() {
		return identify_code_brief;
	}

	public void setCodeBrief(String identify_code_brief) {
		this.identify_code_brief = identify_code_brief;
	}
	public String getAgreement() {
		return user_agreement_status;
	}

	public void setAgreement(String user_agreement_status) {
		this.user_agreement_status = user_agreement_status;
	}

//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}








	@Override
	public String toString() {
		return "InitMessage [result=" + result + ", message=" + message
				+ ", weChat=" + WeChat + ", QQ=" + QQ + ", uid=" + uid
				+ ", userName=" + userName + ", kf_qq_number=" + kf_qq_number
				+ ", change_password_brief=" + change_password_brief + ", floatBall=" + FloatBall
				+ ", identify_code_brief=" + identify_code_brief +  ", user_agreement_status=" + user_agreement_status
				+ ", appNoticeDayList=" + appNoticeDayList + "]";
	}

}
