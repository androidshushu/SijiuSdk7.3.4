//package com.shiyue.mhxy.user;
//
//import java.util.List;
//
//import com.shiyue.mhxy.config.AppConfig;
//import com.shiyue.mhxy.sdk.BaseFragment;
//import com.shiyue.mhxy.http.ApiAsyncTask;
//import com.shiyue.mhxy.http.ApiRequestListener;
//import com.shiyue.mhxy.sdk.SiJiuSDK;
//import com.shiyue.mhxy.user.ListViewRefreshMore.OnRefreshLoadingMoreListener;
//import com.shiyue.mhxy.user.Order.OrderItem;
//
//import android.annotation.SuppressLint;
//import android.content.ClipboardManager;
//import android.content.Context;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.AdapterView.OnItemClickListener;
//
//public class UserRechagerRecord extends BaseFragment {
//
//	private View iview;
////	private ListView lv_record;
//	public int appId;
//	public String appKey = "";
//	public String uid = "";
//	public String gameMoney = "";
//	private ApiAsyncTask successTask;
//	private ListViewRefreshMore list;
//
//	private int pageIndex = 1;// 页索引。默认从1开始
//	private int pageSize = 5;
//	private boolean isRefresh = false;// 下拉刷新标记
//	private boolean isLoadMore = false;// 加载更多标记
////	private LinearLayout linearLayout;
//	private Adapter_list adapter;
//	private List<OrderItem> data;
//	private TextView noDataView;
//	public int multiple;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		iview=inflater.inflate(AppConfig.resourceId(getActivity(),"sjuser_record", "layout"), container,false);
//		initView();
//		initData();
//		HttpGet(pageIndex, pageSize);
//		return iview;
//	}
//
//	@Override
//	public void onDestroyView() {
//		super.onDestroyView();
//	}
//
//	private void initView() {
//		list=(ListViewRefreshMore) iview.findViewById(AppConfig.resourceId(getActivity(),"lv_record", "id"));
//		noDataView = (TextView)iview.findViewById(getResources().getIdentifier("records_no_data", "id", getActivity().getPackageName()));
//	}
//
//	private void initData() {
//		appId = AppConfig.appId;
//		appKey = AppConfig.appKe   y;
//
//	}
//
//	/**
//	 * 判断上拉下点击刷新
//	 */
//	public void isPull(List<OrderItem> dataList){
//		try {
//
//			if(isRefresh){
//				isRefresh = false;
//				list.onRefreshComplete();
//				list.onLoadMoreComplete(false);
//				loadData(dataList);
//				updateAdapter();
//			}else if(isLoadMore){
//				isLoadMore = false;
//				int size = dataList.size();
//				if(size != 0){
//					for(int i = 0; i<size; i++){
//						data.add(dataList.get(i));
//					}
//					updateAdapter();
//				}else {
//
////					Toast mToast = null;
//					showToast(getActivity(), "亲，没有更多数据了!", Toast.LENGTH_SHORT);
////					showMsg("亲，没有数据了!");
//				}
//				list.onLoadMoreComplete(false);
//			}else{
//				if(dataList.size() != 0)
//					loadData(dataList);
//
//				else{
//					list.setVisibility(View.GONE);
//				    noDataView.setVisibility(View.VISIBLE);
//					noDataView.setText("亲，现在还没有记录哦！");
//					//showMsg("亲，现在还没有记录哦!");
//				}
//
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 加载数据列表
//	 */
//	public void loadData(List<OrderItem> dataList){
//		data = dataList;
//		adapter = new Adapter_list(getActivity(), data, multiple, gameMoney);
//		list.setAdapter(adapter);
//		list.setOnItemClickListener(new OnItemClickListener() {
//
//			@SuppressLint("NewApi") @Override
//			public void onItemClick(AdapterView<?> arg0, View view, int position,
//					long arg3) {
//				try{
//				if (position==0) {
//					String bi=data.get(0).getBillno();
//					ClipboardManager cm =(ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
//					cm.setText(bi);
//					Toast.makeText(getActivity(), "订单号"+bi+"已复制到剪切板", Toast.LENGTH_SHORT).show();
//
//				}else {
//					String bi=data.get(position-1).getBillno();
//					ClipboardManager cm =(ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
//					cm.setText(bi);
//					Toast.makeText(getActivity(), "订单号"+bi+"已复制到剪切板", Toast.LENGTH_SHORT).show();
//
//				}}catch(Exception e){}
////				ImageView line = (ImageView)view.findViewById(getActivity().getResources().getIdentifier("records_item_line", "id", getActivity().getPackageName()));
////				RelativeLayout reLayout = (RelativeLayout)view.findViewById(getActivity().getResources().getIdentifier("records_relative", "id", getActivity().getPackageName()));
////				ImageView icon = (ImageView)view.findViewById(getActivity().getResources().getIdentifier("item_button", "id", getActivity().getPackageName()));
////				if(reLayout.getVisibility() == 0){
////					line.setVisibility(8);
////					icon.setBackgroundDrawable(getResources().getDrawable(getResources().getIdentifier("down", "drawable", getActivity().getPackageName())));
////					reLayout.setVisibility(8);
////				}else{
////					line.setVisibility(0);
////					icon.setBackgroundDrawable(getResources().getDrawable(getResources().getIdentifier("up", "drawable", getActivity().getPackageName())));
////					reLayout.setVisibility(0);
////				}
//
//			}
//		});
//
//		list.setOnRefreshListener(new OnRefreshLoadingMoreListener() {
//
//			@Override
//			public void onRefresh() {
//				// TODO Auto-generated method stub
//				isRefresh = true;
//				pageIndex = 1;// 更新。请求第一页数据
//				HttpGet(pageIndex, pageSize);
//			}
//
//			@Override
//			public void onLoadMore() {
//				// TODO Auto-generated method stub
//				isLoadMore = true;
//				pageIndex++;// 加载更多，请求下一页数据
//				HttpGet(pageIndex, pageSize);
//			}
//		});
//	}
//
//	/**
//	 * 适配器
//	 */
//	public void updateAdapter(){
//		if(adapter != null){
//			adapter.notifyDataSetChanged();
//		}
//	}
//
///*	public void showMsg(String msg){
//		try {
//			Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}*/
//
//	@SuppressLint("NewApi")
//	private void HttpGet(int pageIndex, int pageSize) {
//		// TODO Auto-generated method stub
//		int status = 1; //状态
//		if(uid.isEmpty()){
//			uid = AppConfig.uid;
//		}
//		successTask = SiJiuSDK.get().startOrderList(getActivity(), appId,
//				appKey, uid, pageIndex, pageSize, status,
//				new ApiRequestListener() {
//
//					@Override
//					public void onSuccess(Object obj) {
//						// TODO Auto-generated method stub
//						if(obj != null){
//							Order order = (Order)obj;
//							if(order.getResult())
//								sendData(120, obj, handler);
//							else
//								sendData(AppConfig.FLAG_FAIL, order.getMessage(), handler);
//						}else{
//							sendData(AppConfig.FLAG_FAIL, "获取失败!", handler);
//						}
//					}
//
//					@Override
//					public void onError(int statusCode) {
//						// TODO Auto-generated method stub
//						sendData(AppConfig.FLAG_REQUEST_ERROR, "链接错误!", handler);
//					}
//				});
//	}
//
///*	public void sendData(int num, Object data, Handler callback){
//		Message msg = callback.obtainMessage();
//		msg.what = num;
//		msg.obj = data;
//		msg.sendToTarget();
//	}*/
//	/**
//	 * 处理订单返回数据
//	 */
//	public Handler handler = new Handler(){
//
//		@Override
//		public void handleMessage(Message msg) {
//
////			linearLayout.setVisibility(View.GONE);
//			switch (msg.what) {
//			case 120:
//				Order order = (Order)msg.obj;
//				isPull(order.orderList);
//				break;
//			case AppConfig.FLAG_REQUEST_ERROR:
//				showMsg((String)msg.obj);
//				break;
//			case AppConfig.FLAG_FAIL:
//				showMsg((String)msg.obj);
//				break;
//			}
//		}
//	};
//}
