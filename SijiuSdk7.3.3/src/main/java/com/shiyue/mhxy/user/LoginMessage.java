package com.shiyue.mhxy.user;

/**
 * 登录信息对象.包含{@link #getResult()}返回结果,
 * {@link #getMessage()}服务器消息,
 * {@link #getTimestamp()}服务器当前时间（时间戳）,
 * {@link #getUid()}登录用户UID,
 * {@link #getUserName()}登录用户名,
 * {@link #getSign()}签名 方法
 * 
 * 
 */
public class LoginMessage {

//		loginMsg.setTs(jsonObject.getString("ts"));
//			loginMsg.setPhoneNumber(jsonObject.getString("phone_number"));
//			loginMsg.setAccount(jsonObject.getString("account"));
//			loginMsg.setAccountName(jsonObject.getString("account_name"));
//	loginMsg.setTick
	private boolean result;//

	private String message; // 错误消息

	private String timestamp = ""; // 服务器当前时间（时间戳）

	private String uid = "";// 登录用户UID

	private String userName = ""; // 登录用户名

	private String phonenumber = ""; // 签名
	
	private String token = ""; // 签名
	private String logintick = ""; // 登录令牌
	private int code; //服务器返回码
	
	private String sdktoken = ""; // 签名
	
	private String verifySign = "";
	
	private int userType ; //1游客用户  2已修改过账户  0普通用户

	
	public LoginMessage() {

	}


	/**
	 * @return boolean 返回结果
	 */
	public String getLoginTick() {
		return logintick;
	}

	public void setLoginTick(String logintick) {
		this.logintick = logintick;
	}
	/**
	 * @return boolean 返回结果
	 */
	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	/**
	 * 
	 * @return String 服务器消息
	 */
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return String 服务器当前时间（时间戳）
	 */
	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return String 登录用户UID
	 */
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return String 登录用户名
	 */
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

//	/**
//	 * @return String 签名
//	 */
	public String getPhoneNumber() {
		// return phonenumber;
		return phonenumber;
	}

	public void setPhoneNumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	@Override
	public String toString() {
		return "LoginMessage [result=" + result + ", message=" + message
				+ ", timestamp=" + timestamp + ", uid=" + uid +",code="+code+ ",loginTick="+logintick+", userName="+
				 userName + ", phonenumber=" + phonenumber + "]";
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code=code ;
	}

//	public String getSdktoken() {
//		return sdktoken;
//	}
//
//	public void setSdktoken(String sdktoken) {
//		this.sdktoken = sdktoken;
//	}
//
//	public String getVerifySign() {
//		return verifySign;
//	}
//
//	public void setVerifySign(String verifySign) {
//		this.verifySign = verifySign;
//	}
//
//	public int getUserType() {
//		return userType;
//	}
//
//	public void setUserType(int userType) {
//		this.userType = userType;
//	}

}
