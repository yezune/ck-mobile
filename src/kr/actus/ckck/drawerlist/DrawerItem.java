package kr.actus.ckck.drawerlist;

import android.os.Parcel;
import android.os.Parcelable;

public class DrawerItem implements Parcelable {
	private String index;
	private String title;

	public DrawerItem(String index, String title) {
		// TODO Auto-generated constructor stub
		this.index = index;
		this.title = title;

	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(index);
		dest.writeString(title);

	}

	public static final Parcelable.Creator<DrawerItem> CREATOR = new Parcelable.Creator<DrawerItem>() {

		@Override
		public DrawerItem createFromParcel(Parcel src) {
			// TODO Auto-generated method stub
			String index = src.readString();
			String title = src.readString();

			return new DrawerItem(index, title);

		}

		@Override
		public DrawerItem[] newArray(int size) {
			// TODO Auto-generated method stub
			return new DrawerItem[size];

		}

	};

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
