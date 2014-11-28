package kr.actus.ckck.cartlist;

import java.util.ArrayList;

import kr.actus.ckck.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CartAdapter extends BaseAdapter {
	
private static final String TAG = "MainActivity";
	
	Context context;
	LayoutInflater inflater;
	
	TextView tvTitle,tvPrice,tvCount;
	
	Button btnDel;
	CartItem item;
	ArrayList<CartItem> data = new ArrayList<CartItem>();
	int layout;
	
public CartAdapter(Context context,int layout,ArrayList<CartItem> data){
		this.context = context;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		this.data = data;
		this.layout = layout;
		
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
	public View getView(int position, View v, ViewGroup parent) {
		if (v == null) {
			v = inflater.inflate(layout, parent, false);
		}
		
		tvTitle = (TextView)v.findViewById(R.id.cart_list_tv_title);
		tvPrice = (TextView)v.findViewById(R.id.cart_list_tv_price);
		tvCount = (TextView)v.findViewById(R.id.cart_list_tv_count);
		tvTitle = (TextView)v.findViewById(R.id.cart_list_tv_title);
		btnDel = (Button)v.findViewById(R.id.cart_list_btn_del);
		
		
		tvTitle.setText(data.get(position).getTitle());
		int price = data.get(position).getPrice();
		
		tvPrice.setText(price+"");
		 
		tvCount.setText(data.get(position).getCount()+"");
		
		
		return v;
	}

}
