package kr.actus.ckck.drawerlist;

import android.os.Parcel;
import android.os.Parcelable;

public class DrawerItem implements Parcelable {
	private int num;
	private String title;
	
	

	
	public DrawerItem(int num,String title) {
		// TODO Auto-generated constructor stub
		this.num = num;
		this.title = title;
		
		
	}

	

	



	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(num);
		dest.writeString(title);
		
	
		
			
	}
	
	public static final Parcelable.Creator<DrawerItem> CREATOR = new Parcelable.Creator<DrawerItem>(){

		@Override
		public DrawerItem createFromParcel(Parcel src) {
			// TODO Auto-generated method stub
			int num = src.readInt();
			String title = src.readString();
	
			
			
			
			return new DrawerItem(num,title);
			
		}

		@Override
		public DrawerItem[] newArray(int size) {
			// TODO Auto-generated method stub
			return new DrawerItem[size];
			
		}
		
		
	};




	public int getNum() {
		return num;
	}





	public void setNum(int num) {
		this.num = num;
	}





	public String getTitle() {
		return title;
	}





	public void setTitle(String title) {
		this.title = title;
	}


}

