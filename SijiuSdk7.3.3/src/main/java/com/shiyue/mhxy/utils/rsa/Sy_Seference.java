package com.shiyue.mhxy.utils.rsa;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.shiyue.mhxy.utils.Base64;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Sy_Seference
{
	public Context mcontext;
	private String account;
	private String pwd;
	private String phoneNum;
	private String loginTicket;
	private String userName;
	private String loginType;
	private String nickName;

	private static int i=0;
    private String logintimes ;

	public String getLogintimes() {
		return logintimes;
	}
	public void setLogintimes(String logintimes) {
		this.logintimes = logintimes;
	}


	public static final String ACCOUNT_FILE_NAME = "account_file_name";

	public Sy_Seference(Context context) {
		this.mcontext = context;
	}





	public void savePreferenceData( String account, String pwd,String phoneNum,String loginTicket,String userName,String loginType,String nickName,String logintimes) {
//		SharedPreferences.Editor sharedata = mcontext.getSharedPreferences(
//				filename, 0).edit();
//		sharedata.putString(key, value);
//		sharedata.commit();
		this.account=account;
		this.pwd=pwd;
		this.phoneNum=phoneNum;
		this.loginTicket=loginTicket;
		this.userName=userName;
		this.loginType=loginType;
		this.nickName = nickName;
		this.logintimes = logintimes;

        Log.d("loginTimes1=", this.logintimes);
		saveAccountArray(account);

	}

	private void saveUserInfo(String filename, String account, String pwd, String phoneNum, String loginTicket, String userName, String longType, String nickName, String loginTimes)
	{
		SharedPreferences.Editor userinfo = this.mcontext.getSharedPreferences(filename, 0)
				.edit();
		userinfo.putString("account", Base64.encode(account.getBytes()));
		userinfo.putString("pwd", Base64.encode(pwd.getBytes()));
		userinfo.putString("phoneNum", Base64.encode(phoneNum.getBytes()));
		userinfo.putString("longinToken", Base64.encode(loginTicket.getBytes()));
		userinfo.putString("userName", Base64.encode(userName.getBytes()));
		userinfo.putString("loginType", Base64.encode(longType.getBytes()));
		userinfo.putString("nickName", Base64.encode(nickName.getBytes()));
        userinfo.putString("loginTimes", Base64.encode(loginTimes .getBytes()));
		userinfo.commit();
	}

	public   HashMap getUserInfo(String account){
		String key=Base64.encode(account.getBytes());
		SharedPreferences userdata=mcontext.getSharedPreferences(key,0);
		String muid=userdata.getString("account","");
		String mpwd=userdata.getString("pwd","");
		String mphoneNum=userdata.getString("phoneNum","");
		String mlonginTicket=userdata.getString("longinToken","");
		String muserName=userdata.getString("userName","");
		String mloginType=userdata.getString("loginType","");
		String mnickName = userdata.getString("nickName", "");
        String mloginTimes = userdata.getString("loginTimes","");

		HashMap hs=new HashMap();
		hs.put("account",Base64.decode(muid));
		hs.put("pwd",Base64.decode(mpwd));
		hs.put("phoneNum",Base64.decode(mphoneNum));
		hs.put("longinToken",Base64.decode(mlonginTicket));
		hs.put("userName",Base64.decode(muserName));
		hs.put("loginType",Base64.decode(mloginType));
		hs.put("mnickName", Base64.decode(mnickName));
        hs.put("loginTimes", Base64.decode(mloginTimes));

        Log.d("loginTimes2=", Base64.decode(mloginTimes));
		return hs;

	}
	//拼接账号以便加密存储，每次存储都要排好序存储，用于历史账号查看
	public void saveAccountArray(String account){
		if(getAccountArray().equals("")){
			//第一次登录或者没有找到账号记录
			SharedPreferences.Editor data=mcontext.getSharedPreferences(
					ACCOUNT_FILE_NAME, 0).edit();

			data.putString("array",Base64.encode(account.getBytes()));
			data.commit();
			saveUserInfo(Base64.encode(account.getBytes()), account, this.pwd, this.phoneNum, this.loginTicket, this.userName, this.loginType, this.nickName, this.logintimes);
		}
		//存储之前判断是否已经存在的账号信息调整位置
		else if (getAccountList(false).contains(account) == true)
		{
			ArrayList a=getAccountList(false);

			a.remove(account);

			a.add(account);
			saveUserInfo(Base64.encode(account.getBytes()), account, this.pwd, this.phoneNum, this.loginTicket, this.userName, this.loginType, this.nickName, this.logintimes);

			SharedPreferences.Editor data=mcontext.getSharedPreferences(
					ACCOUNT_FILE_NAME, 0).edit();
			data.putString("array",Base64.encode(setAccountList(a).getBytes()));
			data.commit();

		}else{
//限制poupolwindow条数

			String arraylist=getAccountArray();
			String realaccount=arraylist+"#"+account;
			SharedPreferences.Editor data=mcontext.getSharedPreferences(
					ACCOUNT_FILE_NAME, 0).edit();
			data.putString("array",Base64.encode(realaccount.getBytes()));
			saveUserInfo(Base64.encode(account.getBytes()), account, this.pwd, this.phoneNum, this.loginTicket, this.userName, this.loginType, this.nickName, this.logintimes);
			data.commit();
		}

	}



	public  void removeAccount(String account){

		ArrayList a=getAccountList(false);

		a.remove(account);

		SharedPreferences.Editor data=mcontext.getSharedPreferences(
				ACCOUNT_FILE_NAME, 0).edit();
		data.putString("array",Base64.encode(setAccountList(a).getBytes()));
		data.commit();
	}
	public boolean isAccountSave(String account) {
		if (!getAccountArray().equals("")) {
			String temp = getAccountArray();
			if (temp.contains(account)) {
				return true;
			}
			return false;
		}

		return false;
	}
	public String getAccountArray(){
		String temp="";
		SharedPreferences data=mcontext.getSharedPreferences( ACCOUNT_FILE_NAME,0);
		temp=data.getString("array","");

		String arraylist=Base64.decode(temp);
		return arraylist;
	}

	//获得账号数组形式的列表（字符串转数组）
	//通过参数确认是否需要翻转排序输出
	public ArrayList getAccountList(boolean isreverse){
		if(!getAccountArray().equals("")){
			String temp=getAccountArray();

			String [] arrString=temp.split("#");
			ArrayList b = new ArrayList();
			for(int i=0;i<=arrString.length-1;i++){
				b.add(arrString[i]);
			}
			if(isreverse) {
				ArrayList c = b;
				Collections.reverse(c);
				return c;
			}else{
				return  b;
			}

		}else{
			return  null;
		}
	}
	//数组转换字符串存储
	private String setAccountList(ArrayList list) {
		String temp = "";
		for (int i = 0; i <= list.size() - 1; i++) {
			temp = temp + (String) list.get(i) + "#";

		}
		if (!temp.equals("")) {
			temp = temp.substring(0, temp.length() - 1);
		}

		return temp;
	}


	public String getPreferenceData(String filename, String key) {
		String temp = "";
		SharedPreferences sharedata = mcontext
				.getSharedPreferences(filename, 0);
		temp = sharedata.getString(key, "");
		return temp;
	}




}
