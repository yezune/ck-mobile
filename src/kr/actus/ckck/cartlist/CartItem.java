package kr.actus.ckck.cartlist;

import android.os.Parcel;
import android.os.Parcelable;

public class CartItem implements Parcelable {

	private String title;
	private int count;
	private int price;
	private String shopId;
	private String shopName;
	public CartItem(String shopId,String shopName,String title, int count, int price) {
		// TODO Auto-generated constructor stub
		this.shopId=shopId;
		this.shopName=shopName;
		this.title = title;
		this.count = count;
		this.price = price;

	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(shopId);
		dest.writeString(shopName);
		dest.writeString(title);
		dest.writeInt(count);
		dest.writeInt(price);
		

	}

	public static final Parcelable.Creator<CartItem> CREATOR = new Parcelable.Creator<CartItem>() {

		@Override
		public CartItem createFromParcel(Parcel src) {
			// TODO Auto-generated method stub
			String shopId = src.readString();
			String shopName = src.readString();
			String title = src.readString();
			int count = src.readInt();
			int price = src.readInt();

			return new CartItem(shopId,shopName,title, count, price);

		}

		@Override
		public CartItem[] newArray(int size) {
			// TODO Auto-generated method stub
			return new CartItem[size];

		}

	};

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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

}
