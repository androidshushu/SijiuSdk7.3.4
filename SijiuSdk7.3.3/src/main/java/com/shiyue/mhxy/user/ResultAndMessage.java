package com.shiyue.mhxy.user;

import java.util.List;

public class ResultAndMessage
{
	private boolean result;
	private String message;
	private String data;
	private String accountname;
	private String accountid = "";
	private List<String> datas;

	public boolean getResult()
	{
		return this.result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMessage()
	{
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getData()
	{
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getAccountname()
	{
		return this.accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getAccountid()
	{
		return this.accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public String toString() {
		return "ResultAndMessage [result=" + this.result + ", message=" + this.message + ", data=" + this.data + "]";
	}

	public List<String> getDatas()
	{
		return this.datas;
	}

	public void setDatas(List<String> datas) {
		this.datas = datas;
	}
}