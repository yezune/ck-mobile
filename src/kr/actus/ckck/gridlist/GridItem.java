package kr.actus.ckck.gridlist;



import android.os.Parcel;
import android.os.Parcelable;

public class GridItem implements Parcelable {
	private String imgPath;
	private String title;
	private String type;
	private String minMoney;
	private String delivery;
	
	
	


	
	public GridItem(String imgPath, String title,String type,String minMoney,String delivery) {
		// TODO Auto-generated constructor stub
		this.imgPath = imgPath;
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
		dest.writeString(imgPath);
		dest.writeString(title);
		dest.writeString(type);
		dest.writeString(minMoney);
		dest.writeString(delivery);
	
		
			
	}
	
	public static final Parcelable.Creator<GridItem> CREATOR = new Parcelable.Creator<GridItem>(){

		@Override
		public GridItem createFromParcel(Parcel src) {
			// TODO Auto-generated method stub
			String imgPath = src.readString();
			String title = src.readString();
			String type = src.readString();
			String minMoney = src.readString();
			String delivery = src.readString();
			
			return new GridItem(imgPath,title,type,minMoney,delivery);
			
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


}

