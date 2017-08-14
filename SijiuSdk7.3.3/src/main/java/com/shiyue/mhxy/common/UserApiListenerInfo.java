package com.shiyue.mhxy.common;

import android.os.Parcel;
import android.os.Parcelable;


public class UserApiListenerInfo implements  Parcelable{

	/*public   void onSuccess(Object obj){
		
	}*/


	/*public  void onError(int statusCode){
		
	}*/
    public void onLogout(Object obj) {
    	
    }
	@Override
	public int describeContents() {

		return 0;
	}


	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		
		
	}



	
}
