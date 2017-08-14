package com.shiyue.mhxy.common;

public class LoginMessageInfo {

	private String  result;//

	private String message; // 错误消息

	private String timestamp = ""; // 服务器当前时间（时间戳）

	private String uid = "";// 登录用户UID

	private String userName = ""; // 登录用户名

	private String sign = ""; // 签名
	
	private String token = ""; // 签名
	
	private String verifySign = "";
	
	private int userType ; //1游客用户  2已修改过账户  0普通用户


	/**
	 * @return boolean 返回结果
	 */
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
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

	/**
	 * @return String 签名
	 */
//	public String getSign() {
//		// return sign;
//		return sign;
//	}
//
//	public void setSign(String sign) {
//		this.sign = sign;
//	}

	@Override
	public String toString() {
		return "LoginMessage [result=" + result + ", message=" + message
				+ ", timestamp=" + timestamp + ", uid=" + uid + ", userName="
				+ userName + ", sign=" + sign + "]";
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getVerifySign() {
		return verifySign;
	}

	public void setVerifySign(String verifySign) {
		this.verifySign = verifySign;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

}
