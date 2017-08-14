package com.shiyue.mhxy.user;

import com.shiyue.mhxy.config.AppConfig;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UserDetermineDialog extends Dialog implements OnClickListener {

	private Context context;
	private int theme;
	private int view;
	private Button  bacakbut;
	public UserDetermineDialog(Context context, int theme, int view ) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.theme = theme;
		this.view = view;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(view);
		inintView();
	}

	private void inintView() {
		// TODO Auto-generated method stub
		bacakbut = (Button) findViewById(AppConfig.resourceId(context, "determine_bt", "id"));
		bacakbut.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==AppConfig.resourceId(context, "determine_bt", "id")){
			dismiss();
		}
	
	}

}
