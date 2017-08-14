package com.shiyue.mhxy.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.shiyue.mhxy.utils.rsa.Sy_Seference;
import java.util.ArrayList;
import java.util.HashMap;


public class PopupAdapter extends BaseAdapter implements View.OnClickListener {




	 private Context context;
	private LayoutInflater layoutInflater;
    private  ArrayList list;
    private  MyClickListener listener;
    private SharedPreferences sp;
	private Sy_Seference seference;
	private HashMap hs;
//	public PopupAdapter(){
//
//	}
//	public PopupAdapter(Context context){
//		this.context=context;
//
//	}
public interface MyClickListener{
    public void clickListener(View v);
}
	public void refresh(ArrayList list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public PopupAdapter(Context context,ArrayList list,MyClickListener listener){
	this.context=context;
	this.list=list;
    this.listener=listener;
	this.layoutInflater = LayoutInflater.from(context);
	seference=new Sy_Seference(context);
		if(list==null){
			list=new ArrayList();
		}
}


	class Holder{
		TextView tv;
		ImageView imageView;
		ImageView ibtn;
        void setId(int position){
            tv.setId(position);
            ibtn.setId(position);
            imageView.setId(position);
        }
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int position) {
		if(position >= getCount()){
			return null;
		}
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		Holder holder=null;
		if (view == null) {
//			convertView = LayoutInflater.from(context).inflate(R.layout.test, parent, false);
			view = layoutInflater.inflate(
					context.getResources().getIdentifier("sy_item_count_list",
							"layout", context.getPackageName()), arg2,false);

			holder = new Holder();
		RelativeLayout ll = (RelativeLayout) view.findViewById(context
					.getResources().getIdentifier("subject_ll", "id",
							context.getPackageName()));
//用于动态设置item的高度，以便按百分比控制内容大小
			ViewGroup.LayoutParams linearParams = ll
					.getLayoutParams();
			DisplayMetrics dm2 = context.getResources().getDisplayMetrics();

			linearParams.height = (int) (dm2.heightPixels*0.096);
////			linearParams.gravity = Gravity.CENTER_VERTICAL;
			ll.setLayoutParams(linearParams);



			holder.imageView = (ImageView) view
					.findViewById(context.getResources().getIdentifier(
							"user_icon", "id", context.getPackageName()));
			holder.tv = (TextView) view.findViewById(context
					.getResources().getIdentifier("list_tv", "id",
							context.getPackageName()));
			holder.ibtn = (ImageView) view.findViewById(context.getResources()
					.getIdentifier("list_ibtn", "id",
							context.getPackageName()));


			view.setTag(holder);
		}else {
            holder = (Holder) view.getTag();

        }
        if(holder!=null){
//             view.setId(position);
//			sp=context.getSharedPreferences(list.get(position)+"",0);
//			sp.g

			String loginType;
			hs=seference.getUserInfo(list.get(position)+"");
			loginType=(String)hs.get("loginType");
            holder = (Holder)view.getTag();
             holder.setId(position);
			if (loginType.equals("1")) {
				String userName = (String)this.hs.get("mnickName");
				holder.tv.setText(userName + "");
				holder.imageView.setImageResource(this.context.getResources().getIdentifier("qq_red", "drawable", this.context.getPackageName()));
			} else if (loginType.equals("2")) {
				String userName = (String)this.hs.get("mnickName");
				holder.tv.setText(userName + "");
				holder.imageView.setImageResource(this.context.getResources().getIdentifier("wechat", "drawable", this.context.getPackageName()));
			}
			else if (loginType.equals("4")) {
				String userName = (String)this.hs.get("userName");
				holder.tv.setText(userName + "");
				holder.imageView.setImageResource(this.context.getResources().getIdentifier("sy_user_round", "drawable", this.context.getPackageName()));
			} else if (loginType.equals("5")) {
				String userName = (String)this.hs.get("userName");
				holder.tv.setText(userName + "");
				holder.imageView.setImageResource(this.context.getResources().getIdentifier("sy_phone_round", "drawable", this.context.getPackageName()));
			}
            holder.tv.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    return false;
                }
            });

//            holder.imageView.setText(list.get(position)+"");
            holder.ibtn.setOnClickListener(this);
            holder.ibtn.setTag(position);
        }




		return view;
	}

    @Override
    public void onClick(View view) {
       listener.clickListener(view);

    }
}
