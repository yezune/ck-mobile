package kr.actus.ckck.storelist;



import android.os.Parcel;
import android.os.Parcelable;

public class StoreListItem implements Parcelable {
	
	private String title;
	private String type;

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	
	public StoreListItem(String title,String type) {
		// TODO Auto-generated constructor stub
		
		this.title = title;
		this.type = type;
		
	}

	

	

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(title);
		dest.writeString(type);
	
	
		
			
	}
	
	public static final Parcelable.Creator<StoreListItem> CREATOR = new Parcelable.Creator<StoreListItem>(){

		@Override
		public StoreListItem createFromParcel(Parcel src) {
			// TODO Auto-generated method stub
			
			String title = src.readString();
			String type = src.readString();
			
			return new StoreListItem(title,type);
			
		}

		@Override
		public StoreListItem[] newArray(int size) {
			// TODO Auto-generated method stub
			return new StoreListItem[size];
			
		}
		
		
	};


}

