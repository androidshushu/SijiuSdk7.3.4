package com.shiyue.mhxy.user;

import android.os.Parcel;
import android.os.Parcelable;

public class LoginInfo implements Parcelable{
	
	private int appid ;
	private String appkey = "";
	private String agent = "";
	private String server_id = "";
	private String oritation = "";

	
	public int getAppid() {
		return appid;
	}
	public void setAppid(int appid) {
		this.appid = appid;
	}
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getServer_id() {
		return server_id;
	}
	public void setServer_id(String server_id) {
		this.server_id = server_id;
	}
		
	public String getOritation() {
		return oritation;
	}
	public void setOritation(String oritation) {
		this.oritation = oritation;
	}
	
	
	
	@Override
	public String toString() {
		return "LoginInfo [appid=" + appid + ", appkey=" + appkey
				+ ", agent=" + agent + ", server_id=" + server_id
				+ ", oritation=" + oritation + ", toString()="
				+ super.toString() + "]";
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		// TODO Auto-generated method stub
		parcel.writeInt(appid);
		parcel.writeString(appkey);
		parcel.writeString(agent);
		parcel.writeString(server_id);
		parcel.writeString(oritation);
		
	}
	
	public static final Creator<LoginInfo> CREATOR = new Creator<LoginInfo>() {

		@Override
		public LoginInfo createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			LoginInfo loginInfo = new LoginInfo();
			loginInfo.appid = source.readInt();
			loginInfo.appkey = source.readString();
			loginInfo.agent = source.readString();
			loginInfo.server_id = source.readString();
			loginInfo.oritation = source.readString();
			return loginInfo;
		}

		@Override
		public LoginInfo[] newArray(int size) {
			// TODO Auto-generated method stub
			return new LoginInfo[size];
		}
	};
}
