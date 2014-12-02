package kr.actus.ckck.setaddrlist;

import android.os.Parcel;
import android.os.Parcelable;

public class SetAddrListItem implements Parcelable {

	private String addr;
	private String post;
	

	public SetAddrListItem(String addr, String post) {

		this.addr = addr;
		this.post = post;
	

	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeString(addr);
		dest.writeString(post);
		
	}

	public static final Parcelable.Creator<SetAddrListItem> CREATOR = new Parcelable.Creator<SetAddrListItem>() {

		@Override
		public SetAddrListItem createFromParcel(Parcel src) {
			// TODO Auto-generated method stub
			String add = src.readString();
			String post = src.readString();
		
			return new SetAddrListItem(add, post);

		}

		@Override
		public SetAddrListItem[] newArray(int size) {
			// TODO Auto-generated method stub
			return new SetAddrListItem[size];

		}

	};


	public String getAddr() {
		return addr;
	}


	public void setAddr(String addr) {
		this.addr = addr;
	}


	public String getPost() {
		return post;
	}


	public void setPost(String post) {
		this.post = post;
	}

	

	
}
