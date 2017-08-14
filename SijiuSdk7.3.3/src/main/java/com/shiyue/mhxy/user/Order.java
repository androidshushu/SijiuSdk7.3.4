package com.shiyue.mhxy.user;

import java.util.List;

/**
 * 订单
 * 
 */
public class Order {

	// "PageIndex": 1,
	// "PageSize": 10,
	// "TotalItemCount": 92,
	// "TotalPageCount": 10

	private boolean result;
	private String message;
	public List<OrderItem> orderList;
	private int pageIndex = 0;
	private int pageSize = 0;
	private int totalItemCount = 0;
	private int totalPageCount = 0;

	public Order() {

	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalItemCount() {
		return totalItemCount;
	}

	public void setTotalItemCount(int totalItemCount) {
		this.totalItemCount = totalItemCount;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	@Override
	public String toString() {
		return "Order [result=" + result + ", message=" + message
				+ ", orderList=" + orderList + ", pageIndex=" + pageIndex
				+ ", pageSize=" + pageSize + ", totalItemCount="
				+ totalItemCount + ", totalPageCount=" + totalPageCount + "]";
	}

	public class OrderItem {

		// "Billno": "20140102130146840",
		// "PayTypeName": "天下支付",
		// "OrderStatus": "待充值",
		// "CreateDate": "2014-01-02 13:01:46",
		// "Amount": 5
		private String billno;
		private String payTypeName;
		private String orderStatus;
		private String createDate;
		private String gameName;
		private int amount;

		public OrderItem() {

		}

		public String getBillno() {
			return billno;
		}

		public void setBillno(String billno) {
			this.billno = billno;
		}

		public String getPayTypeName() {
			return payTypeName;
		}

		public void setPayTypeName(String payYypeName) {
			this.payTypeName = payYypeName;
		}

		public String getOrderStatus() {
			return orderStatus;
		}

		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}

		public String getCreateDate() {
			return createDate;
		}

		public void setCreateDate(String createDate) {
			this.createDate = createDate;
		}

		public int getAmount() {
			return amount;
		}

		public void setAmount(int amount) {
			this.amount = amount;
		}

		@Override
		public String toString() {
			return "OrderItem [billno=" + billno + ", payTypeName="
					+ payTypeName + ", orderStatus=" + orderStatus
					+ ", createDate=" + createDate + ", amount=" + amount + "]";
		}

		public String getGameName() {
			return gameName;
		}

		public void setGameName(String gameName) {
			this.gameName = gameName;
		}

	}

}
