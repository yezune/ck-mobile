package kr.actus.ckck.gridlist;



import android.os.Parcel;
import android.os.Parcelable;

public class GridItem implements Parcelable {
	private int img;
	private String title;
	private String type;
	private String minMoney;
	private String delivery;
	
	
	public int getImg() {
		return img;
	}

	public void setImg(int img) {
		this.img = img;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	
	public GridItem(int img, String title,String type,String minMoney,String delivery) {
		// TODO Auto-generated constructor stub
		this.img = img;
		this.title = title;
		this.type = type;
		this.minMoney = minMoney;
		this.delivery = delivery;
		
	}

	

	

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(img);
		dest.writeString(title);
		dest.writeString(type);
		dest.writeString(minMoney);
		dest.writeString(delivery);
	
		
			
	}
	
	public static final Parcelable.Creator<GridItem> CREATOR = new Parcelable.Creator<GridItem>(){

		@Override
		public GridItem createFromParcel(Parcel src) {
			// TODO Auto-generated method stub
			int img = src.readInt();
			String title = src.readString();
			String type = src.readString();
			String minMoney = src.readString();
			String delivery = src.readString();
			
			return new GridItem(img,title,type,minMoney,delivery);
			
		}

		@Override
		public GridItem[] newArray(int size) {
			// TODO Auto-generated method stub
			return new GridItem[size];
			
		}
		
		
	};


}

