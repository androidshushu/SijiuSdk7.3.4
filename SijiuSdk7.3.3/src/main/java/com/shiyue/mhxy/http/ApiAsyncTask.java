package com.shiyue.mhxy.http;


public abstract class ApiAsyncTask extends Thread {

	public ApiAsyncTask() {

	}

	

	@Override
	public abstract void run();

	public abstract void cancel(boolean b);

}
