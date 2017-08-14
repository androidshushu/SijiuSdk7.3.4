package com.shiyue.mhxy.user;

public class Noticed {
	//"Result":true,
	//	"Msg":"\u6210\u529f",
	public  boolean result;
	public String msg;
	public String url;
	public String show_type;
	public String tiele;
	public String getTiele() {
		return tiele;
	}
	public void setTiele(String tiele) {
		this.tiele = tiele;
	}
	public String getShow_type() {
		return show_type;
	}
	public void setShow_type(String show_type) {
		this.show_type = show_type;
	}
	public boolean isResult() {
		return result;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Noticed [result=" + result + ", msg=" + msg + ", url=" + url
				+ ", show_type=" + show_type + "]";
	}
	
}
