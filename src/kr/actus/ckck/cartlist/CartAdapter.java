package kr.actus.ckck.cartlist;

import java.util.ArrayList;

import kr.actus.ckck.CartActivity;
import kr.actus.ckck.util.SetURL;
import kr.actus.ckck.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.TextView;

public class CartAdapter extends BaseAdapter {
	
private static final String TAG = "MainActivity";
	
	Context context;
	LayoutInflater inflater;
	CartActivity cartActivity;
	TextView tvTitle,tvPrice,tvCount;
	
	ImageView btnDel;
	CartItem item;
	ArrayList<CartItem> data = new ArrayList<CartItem>();
	int layout;
	SetURL ur;
	
public CartAdapter(CartActivity cartActivity,int layout,ArrayList<CartItem> data){
		this.context = cartActivity;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		this.data = data;
		this.layout = layout;
		this.cartActivity = cartActivity;
		
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View v, ViewGroup parent) {
		if (v == null) {
			v = inflater.inflate(layout, parent, false);
		}
		
		tvTitle = (TextView)v.findViewById(R.id.cart_list_tv_title);
		tvPrice = (TextView)v.findViewById(R.id.cart_list_tv_price);
		tvCount = (TextView)v.findViewById(R.id.cart_list_tv_count);
		tvTitle = (TextView)v.findViewById(R.id.cart_list_tv_title);
		btnDel = (ImageView)v.findViewById(R.id.cart_list_btn_del);
		
		
		tvTitle.setText(data.get(position).getTitle());
		int price = data.get(position).getPrice();
		int count = data.get(position).getCount();
		tvPrice.setText(price+"��");
		tvCount.setText(count+"��");
//		cartActivity.itemStat(price,count);
		btnDel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				data.remove(position);
				Log.v(ur.TAG,"cartAdapter pos :"+position);
				cartActivity.removePref(position);
//				cartActivity.itemStat(0,0);
				notifyDataSetChanged();
			}
		});
		
		return v;
	}

}
