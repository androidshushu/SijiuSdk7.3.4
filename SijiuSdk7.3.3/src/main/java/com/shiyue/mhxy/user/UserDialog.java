package com.shiyue.mhxy.user;

import com.shiyue.mhxy.config.AppConfig;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
public class UserDialog extends Dialog implements OnClickListener{
	private Context context;
	private int theme;
	private int view;
	private Button cancelBtn;
	private Button confirmBtn;
	public UserDialog(Context context, int theme,int view) {
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
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		cancelBtn = (Button) findViewById(AppConfig.resourceId(context, "cancel", "id"));
		confirmBtn = (Button) findViewById(AppConfig.resourceId(context, "confirm", "id"));
		cancelBtn.setOnClickListener(this);
		confirmBtn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==AppConfig.resourceId(context, "cancel", "id")){
			dismiss();
		}else if(v.getId()==AppConfig.resourceId(context, "confirm", "id")){
			downDialog();
			dismiss();
		}
		
	}

	
	 
	private void downDialog() {
		
		UserDetermineDialog userdialog =new UserDetermineDialog(context,AppConfig.resourceId(
				context, "Sj_MyDialog", "style"), AppConfig.resourceId(
						context, "sjuser_determine", "layout"));
		userdialog.setCancelable(false);
		userdialog.show();
	}

	

}
