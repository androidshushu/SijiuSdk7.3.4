//package com.shiyue.mhxy.pay;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import android.text.TextUtils;
//
///**
// * 支付配置接口 .@l{@link #getId()} ID ,@li{@link #getParentId()}, 父级ID
// * {@link #getClientFlag()}客户端标识, {@link #getClassName()}支付方式名称 ,
// * {@link #getPayType()}, 支付平台{@link #getPayClass()} 支付方式（回传给服务端） ,
// *
// * {@link #getMoneyList()}可选金额--int类型集合，{@link #getClientFlag()},客户端标识
// * {@link #getIsFixMoney()}是否可自定义金额.
// *
// */
//public class PayConfig {
//
//	private String id = "";
//	private String parentId = "";// 父级ID
//	private String className = "";// 名称
//	private String payType = "";// 支付平台
//	private String payClass = "";// 支付方式（回传给服务端）
//	private String selectMoney = "";// 可选金额
//	private String clientFlag = "";// 客户端标识
//	private String remark = "";
//	private boolean isFixMoney = false;
//	private String isfixMoney ;
//	private String unit ;
//	private String rate ;
//	private String isClose;
//
//	public String getIsClose() {
//		return isClose;
//	}
//
//	public void setIsClose(String isClose) {
//		this.isClose = isClose;
//	}
//
//
//
//	public List<Integer> moneyList;
//
//	public PayConfig() {
//		moneyList = new ArrayList<Integer>();
//	}
//
//	/**
//	 * eg:"alipay_alipay"
//	 *
//	 * @return 客户端标识
//	 */
//	public String getClientFlag() {
//		return clientFlag;
//	}
//
//	/**
//	 * eg:"10,20,30,50,100,300,500"
//	 *
//	 * @return 可选金额
//	 */
//	private String getSelectMoney() {
//		return selectMoney;
//	}
//
//	public void setSelectMoney(String selectMoney) {
//		this.selectMoney = selectMoney;
//
//		// 设置money字符串的同时，初始化并设置moneyList
//
//		// 空的话不处理
//		if (!TextUtils.isEmpty(selectMoney)) {
//			// 分隔
//			List<String> strList = Arrays.asList(selectMoney.split(","));
//
//			try {
//				for (String str : strList) {
//					moneyList.add(Integer.parseInt(str.trim()));
//				}
//			} catch (NumberFormatException e) {
//				e.printStackTrace();
//			}
//
//		}
//
//	}
//
//	public void setClientFlag(String clientFlag) {
//		this.clientFlag = clientFlag;
//	}
//
//	/**
//	 * 可选金额
//	 *
//	 * @return
//	 */
//	public List<Integer> getMoneyList() {
//		return moneyList;
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getParentId() {
//		return parentId;
//	}
//
//	public void setParentId(String parentId) {
//		this.parentId = parentId;
//	}
//
//	public String getClassName() {
//		return className;
//	}
//
//	public void setClassName(String className) {
//		this.className = className;
//	}
//
//	public String getPayType() {
//		return payType;
//	}
//
//	public void setPayType(String payType) {
//		this.payType = payType;
//	}
//
//	public String getPayClass() {
//		return payClass;
//	}
//
//	public void setPayClass(String payClass) {
//		this.payClass = payClass;
//	}
//
//	public boolean getIsFixMoney() {
//		return isFixMoney;
//	}
//
//	public void setIsFixMoney(boolean isFixMoney) {
//		this.isFixMoney = isFixMoney;
//	}
//
//
//	public String getRemark() {
//		return remark;
//	}
//
//	public void setRemark(String remark) {
//		this.remark = remark;
//	}
//
//	@Override
//	public String toString() {
//		return "PayConfig [id=" + id + ", parentId=" + parentId
//				+ ", className=" + className + ", payType=" + payType
//				+ ", payClass=" + payClass + ", selectMoney=" + selectMoney
//				+ ", clientFlag=" + clientFlag + ", remark=" + remark
//				+ ", isFixMoney=" + isFixMoney + ", moneyList=" + moneyList
//				+ ", toString()=" + super.toString() + "]";
//	}
//
//	public List<PaySub> getPaySubs() {
//		return paySubs;
//	}
//
//	public void setPaySubs(List<PaySub> paySubs) {
//		this.paySubs = paySubs;
//	}
//
//	public String getIsfixMoney() {
//		return isfixMoney;
//	}
//
//	public void setIsfixMoney(String isfixMoney) {
//		this.isfixMoney = isfixMoney;
//	}
//
//	public String getUnit() {
//		return unit;
//	}
//
//	public void setUnit(String unit) {
//		this.unit = unit;
//	}
//
//	public String getRate() {
//		return rate;
//	}
//
//	public void setRate(String rate) {
//		this.rate = rate;
//	}
//
//
//
//}
