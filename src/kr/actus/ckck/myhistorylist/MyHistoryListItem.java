package kr.actus.ckck.myhistorylist;

import android.os.Parcel;
import android.os.Parcelable;

public class MyHistoryListItem implements Parcelable {
	private String orderId;
	private String shopId;
	private String shopName;
	private String orderMenu;
	private String orderTime;
	private int price;
	private int payType;
	private String address;
	private String descript;
	private int status;
	private String deliverName;
	
	


	public MyHistoryListItem(String orderId, String shopId, String shopName,String orderMenu,
			String orderTime, int price, int payType, String address, String descript, int status,String deliverName) {

		this.orderId = orderId;
		this.shopId = shopId;
		this.shopName = shopName;
		this.orderMenu = orderMenu;
		this.orderTime = orderTime;
		this.price = price;
		this.payType = payType;
		this.address = address;
		this.descript = descript;
		this.status = status;
		this.deliverName = deliverName;

	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeString(orderId);
		dest.writeString(shopId);
		dest.writeString(shopName);
		dest.writeString(orderMenu);
		dest.writeString(orderTime);
		dest.writeInt(price);
		dest.writeInt(payType);
		dest.writeString(address);
		dest.writeString(descript);
		dest.writeInt(status);
		dest.writeString(deliverName);

	}

	public static final Parcelable.Creator<MyHistoryListItem> CREATOR = new Parcelable.Creator<MyHistoryListItem>() {

		@Override
		public MyHistoryListItem createFromParcel(Parcel src) {
			// TODO Auto-generated method stub
			String orderId = src.readString();
			String shopId = src.readString();
			String shopName = src.readString();
			String orderMenu = src.readString();
			String orderTime = src.readString();
			int price= src.readInt();
			int payType = src.readInt();
			String address = src.readString();
			String descript = src.readString();
			int status= src.readInt();
			String deliverName = src.readString();
			
			
			
			
			
			return new MyHistoryListItem(orderId,shopId, shopName,orderMenu,
					orderTime, price, payType, address, descript, status, deliverName);

		}

		@Override
		public MyHistoryListItem[] newArray(int size) {
			// TODO Auto-generated method stub
			return new MyHistoryListItem[size];

		}

	};




	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getShopId() {
		return shopId;
	}


	public void setShopId(String shopId) {
		this.shopId = shopId;
	}


	public String getShopName() {
		return shopName;
	}


	public void setShopName(String shopName) {
		this.shopName = shopName;
	}


	public String getOrderMenu() {
		return orderMenu;
	}


	public void setOrderMenu(String orderMenu) {
		this.orderMenu = orderMenu;
	}


	public String getOrderTime() {
		return orderTime;
	}


	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public int getPayType() {
		return payType;
	}


	public void setPayType(int payType) {
		this.payType = payType;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getDescript() {
		return descript;
	}


	public void setDescript(String descript) {
		this.descript = descript;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getDeliverName() {
		return deliverName;
	}


	public void setDeliverName(String deliverName) {
		this.deliverName = deliverName;
	}

	

}
