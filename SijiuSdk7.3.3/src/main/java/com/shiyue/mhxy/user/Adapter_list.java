package com.shiyue.mhxy.user;

import java.util.ArrayList;
import java.util.List;

import com.shiyue.mhxy.user.Order.OrderItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adapter_list extends BaseAdapter {
	private Context mcontext;
	private List<OrderItem> list = new ArrayList<OrderItem>();
	private LayoutInflater mInflater;
	private int Multiple;
	private String gameMoney = "";
	
	public Adapter_list(Context context,List<OrderItem> list ,int Multiple, String gameMoney){
		if(context !=null){
			this.mcontext = context;
			this.list = list;
			this.mInflater = LayoutInflater.from(context);
			this.Multiple = Multiple;
			this.gameMoney = gameMoney;
		}						
	}
	
	class Holder{
		public TextView state,price,billno,time,records_pay_type,name;
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
		Holder holder;
		if (view == null) {
			view = mInflater.inflate(
					mcontext.getResources().getIdentifier("sjrecords_item",
							"layout", mcontext.getPackageName()), null);
			holder = new Holder();
			holder.name = (TextView) view.findViewById(mcontext
					.getResources().getIdentifier("item_name", "id",
							mcontext.getPackageName()));
			holder.price = (TextView) view
					.findViewById(mcontext.getResources().getIdentifier(
							"item_pay", "id", mcontext.getPackageName()));
			holder.state = (TextView) view.findViewById(mcontext.getResources()
					.getIdentifier("item_state", "id",
							mcontext.getPackageName()));
			holder.billno = (TextView) view.findViewById(mcontext
					.getResources().getIdentifier("item_order", "id",
							mcontext.getPackageName()));
			holder.time = (TextView) view
					.findViewById(mcontext.getResources().getIdentifier(
							"item_time", "id", mcontext.getPackageName()));
			holder.records_pay_type = (TextView) view.findViewById(mcontext
					.getResources().getIdentifier("records_pay_type", "id",
							mcontext.getPackageName()));
			view.setTag(holder);
		}
		holder = (Holder)view.getTag();
		holder.price.setText(list.get(position).getAmount()+"");
		holder.name.setText(list.get(position).getGameName());
		holder.state.setText(list.get(position).getOrderStatus());
		holder.billno.setText(list.get(position).getBillno());
		holder.records_pay_type.setText(list.get(position).getPayTypeName());
		holder.time.setText(list.get(position).getCreateDate());
		return view;
	}

}
