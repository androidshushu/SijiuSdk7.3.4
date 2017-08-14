package com.shiyue.mhxy.pay;

import android.os.Parcel;
import android.os.Parcelable;

public class SjyxPaymentInfo implements Parcelable{
	private int appId;
	private String appKey;



	private String roleid;//角色id
	private String serverId;	//区服id	
	private String subject; 	//支付描述
	private String amount;		//金额
	private String extraInfo;	//扩展信息
	private String notify_url;	//回调地址
	private String product_id; //商品id



	private String account_id; //用户uid


	private String agent;		//渠道id
	private String isTest;		//是否是test的参数



	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public void setAccountId(String account_id) {
		this.account_id = account_id;
	}
	public String getAccounId() {
		return account_id;
	}
	public String getProductId() {
		return product_id;
	}
	public void setProductId(String product_id) {
		this.product_id = product_id;
	}
	public String getNotifyUrl() {
		return notify_url;
	}
	public void setNotifyUrl(String notify_url) {
		this.notify_url = notify_url;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
//	public String getOritation() {
//		return "landscape";
//	}
//	public void setOritation(String oritation) {
//		this.oritation = oritation;
//	}
	public String getExtraInfo() {
		return extraInfo;
	}
	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}
	
//	public String getBillNo() {
//		return billNo;
//	}
//	public void setBillNo(String billNo) {
//		this.billNo = billNo;
//	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}	
	public String getSubject() {
		return subject+"" ;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
//	public String getUid() {
//		return uid;
//	}
//	public void setUid(String uid) {
//		this.uid = uid;
//	}
	public String getIsTest() {
		return "";
	}
	public void setIsTest(String isTest) {
		this.isTest = isTest;
	}
//	public String getGameMoney() {
//		return gameMoney;
//	}
//	public void setGameMoney(String gameMoney) {
//		this.gameMoney = gameMoney;
//	}
	
//	public int getMultiple() {
//		return multiple;
//	}
//	public void setMultiple(int multiple) {
//		this.multiple = multiple;
//	}
//	@Override
//	public int describeContents() {
//		// TODO Auto-generated method stub
//		return 0;
//	}

//	public String getRolename() {
//		return rolename;
//	}
//	public void setRolename(String rolename) {
//		this.rolename = rolename;
//	}
//
//	public String getLevel() {
//		return level;
//	}
//	public void setLevel(String level) {
//		this.level = level;
//	}
//	public String getGameuid() {
//		return gameuid;
//	}
//	public void setGameuid(String gameuid) {
//		this.gameuid = gameuid;
//	}
//	public String getRoleid() {
//		return roleid;
//	}
//	public void setRoleid(String roleid) {
//		this.roleid = roleid;
//	}
//	public String getProductname() {
//		return productname;
//	}
//	public void setProductname(String productname) {
//		this.productname = productname;
////	}
	public static Creator<SjyxPaymentInfo> getCreator() {
		return CREATOR;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		// TODO Auto-generated method stub
		parcel.writeInt(appId);
		parcel.writeString(appKey);
		parcel.writeString(serverId);
		parcel.writeString(amount);
		parcel.writeString(account_id);
		parcel.writeString(notify_url);
		parcel.writeString(product_id);
		parcel.writeString(roleid);
		parcel.writeString(extraInfo);
		parcel.writeString(subject);
		parcel.writeString(isTest);

	}
	
	public static final Creator<SjyxPaymentInfo> CREATOR = new Creator<SjyxPaymentInfo>() {

		@Override
		public SjyxPaymentInfo createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			SjyxPaymentInfo payInfo = new SjyxPaymentInfo();



			payInfo.appId = source.readInt();
			payInfo.appKey = source.readString();
			payInfo.serverId = source.readString();
			payInfo.amount = source.readString();
			payInfo.account_id = source.readString();
			payInfo.notify_url = source.readString();
			payInfo.product_id = source.readString();
			payInfo.roleid=source.readString();
			payInfo.extraInfo = source.readString();
			payInfo.subject = source.readString();
			payInfo.isTest = source.readString();

			return payInfo;
		}

		@Override
		public SjyxPaymentInfo[] newArray(int size) {
			// TODO Auto-generated method stub
			return new SjyxPaymentInfo[size];
		}
	};
	
	
}
