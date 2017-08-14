package com.shiyue.mhxy.user;

import com.shiyue.mhxy.config.AppConfig;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UserMessage extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(AppConfig.resourceId(getActivity(), "user_message", "layout"), container,false);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
}
