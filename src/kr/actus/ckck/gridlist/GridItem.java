package kr.actus.ckck.gridlist;

import android.os.Parcel;
import android.os.Parcelable;

public class GridItem implements Parcelable {
	private String imgPath;
	private String shopId;
	private String title;
	private String type;
	private String minMoney;
	private String delivery;
	private String telNumber;
	private String sTime;
	private String eTime;

	public GridItem(String imgPath, String shopId, String title, String type,
			String minMoney, String delivery, String telNumber, String sTime,
			String eTime) {
		// TODO Auto-generated constructor stub
		this.imgPath = imgPath;
		this.shopId = shopId;
		this.title = title;
		this.type = type;
		this.minMoney = minMoney;
		this.delivery = delivery;
		this.telNumber = telNumber;
		this.sTime = sTime;
		this.eTime = eTime;

	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(imgPath);
		dest.writeString(shopId);
		dest.writeString(title);
		dest.writeString(type);
		dest.writeString(minMoney);
		dest.writeString(delivery);
		dest.writeString(telNumber);
		dest.writeString(sTime);
		dest.writeString(eTime);

	}

	public static final Parcelable.Creator<GridItem> CREATOR = new Parcelable.Creator<GridItem>() {

		@Override
		public GridItem createFromParcel(Parcel src) {
			// TODO Auto-generated method stub
			String imgPath = src.readString();
			String shopId = src.readString();
			String title = src.readString();
			String type = src.readString();
			String minMoney = src.readString();
			String delivery = src.readString();
			String telNumber = src.readString();
			String sTime = src.readString();
			String eTime = src.readString();

			return new GridItem(imgPath, shopId, title, type, minMoney,
					delivery, telNumber, sTime, eTime);

		}

		@Override
		public GridItem[] newArray(int size) {
			// TODO Auto-generated method stub
			return new GridItem[size];

		}

	};

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMinMoney() {
		return minMoney;
	}

	public void setMinMoney(String minMoney) {
		this.minMoney = minMoney;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getsTime() {
		return sTime;
	}

	public void setsTime(String sTime) {
		this.sTime = sTime;
	}

	public String geteTime() {
		return eTime;
	}

	public void seteTime(String eTime) {
		this.eTime = eTime;
	}

}
