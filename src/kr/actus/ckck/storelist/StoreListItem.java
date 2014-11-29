package kr.actus.ckck.storelist;

import android.os.Parcel;
import android.os.Parcelable;

public class StoreListItem implements Parcelable {

	private String menuId;
	private String shopId;
	private String shopName;
	private String menuName;
	private String menuImg;
	private int price;
	private String eventFunc;
	private String descript;
	private int minPrice;

	public StoreListItem(String menuId, String shopId, String shopName,String menuName,
			String menuImg, int price, String eventFunc, String descript, int minPrice) {

		this.menuId = menuId;
		this.shopId = shopId;
		this.shopName = shopName;
		this.menuName = menuName;
		this.menuImg = menuImg;
		this.price = price;
		this.eventFunc = eventFunc;
		this.descript = descript;
		this.minPrice = minPrice;

	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeString(menuId);
		dest.writeString(shopId);
		dest.writeString(shopName);
		dest.writeString(menuName);
		dest.writeString(menuImg);
		dest.writeInt(price);
		dest.writeString(eventFunc);
		dest.writeString(descript);
		dest.writeInt(minPrice);

	}

	public static final Parcelable.Creator<StoreListItem> CREATOR = new Parcelable.Creator<StoreListItem>() {

		@Override
		public StoreListItem createFromParcel(Parcel src) {
			// TODO Auto-generated method stub
			String menuId = src.readString();
			String shopId = src.readString();
			String shopName = src.readString();
			String menuName = src.readString();
			String menuImg = src.readString();
			int price = src.readInt();
			String eventFunc = src.readString();
			String descript = src.readString();
			int minPrice = src.readInt();
			return new StoreListItem(menuId, shopId,shopName, menuName, menuImg, price,
					eventFunc, descript, minPrice);

		}

		@Override
		public StoreListItem[] newArray(int size) {
			// TODO Auto-generated method stub
			return new StoreListItem[size];

		}

	};

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuImg() {
		return menuImg;
	}

	public void setMenuImg(String menuImg) {
		this.menuImg = menuImg;
	}

	public String getEventFunc() {
		return eventFunc;
	}

	public void setEventFunc(String eventFunc) {
		this.eventFunc = eventFunc;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}


	public String getShopName() {
		return shopName;
	}


	public void setShopName(String shopName) {
		this.shopName = shopName;
	}


	public int getMinPrice() {
		return minPrice;
	}


	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

}
