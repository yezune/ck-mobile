package kr.actus.ckck.cartlist;



import android.os.Parcel;
import android.os.Parcelable;

public class CartItem implements Parcelable {

	private String title;
	private int count;
	private String price;
	

	
	public CartItem(String title,int count,String price) {
		// TODO Auto-generated constructor stub
		this.count = count;
		this.title = title;
		this.price = price;
		
		
	}

	

	

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(count);
		dest.writeString(title);
		dest.writeString(price);
		
	
		
			
	}
	
	public static final Parcelable.Creator<CartItem> CREATOR = new Parcelable.Creator<CartItem>(){

		@Override
		public CartItem createFromParcel(Parcel src) {
			// TODO Auto-generated method stub
			
			String title = src.readString();
			int count = src.readInt();
			String price = src.readString();
			
			
			return new CartItem(title,count,price);
			
		}

		@Override
		public CartItem[] newArray(int size) {
			// TODO Auto-generated method stub
			return new CartItem[size];
			
		}
		
		
	};


}

