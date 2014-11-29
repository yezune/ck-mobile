package kr.actus.ckck.storelist;



import java.util.ArrayList;

import kr.actus.ckck.MainActivity;
import kr.actus.ckck.SelectOrderActivity;
import kr.actus.ckck.R;
import kr.actus.ckck.fragment.MainTab;
import kr.actus.ckck.fragment.StoreTab;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StoreListAdapter extends BaseAdapter  {
	private static final String TAG = "MainActivity";
	
	Context context;
	LayoutInflater Inflater;
	
	TextView tvTitle,tvPrice;
	LinearLayout linearLay;
	
	ArrayList<StoreListItem> data = new ArrayList<StoreListItem>();
	StoreListItem item;
	int layout;
	
	
	Fragment fragment = null;
	
	public StoreListAdapter(Context context, int layout, ArrayList<StoreListItem> data){
		this.context = context;
		this.Inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.data = data;
		this.layout = layout;
		
	}

	
	


	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	
	
	public View getView(final int position , View cView, ViewGroup parent) {

		
		
		if (cView == null) {
			cView = Inflater.inflate(layout, parent, false);
		}
	
		
		tvTitle = (TextView)cView.findViewById(R.id.store_list_title);
		tvTitle.setText(data.get(position).getMenuName().toString());
	
		
		tvPrice = (TextView)cView.findViewById(R.id.store_list_price);
		tvPrice.setText(data.get(position).getPrice()+"¿ø");
		
		linearLay = (LinearLayout)cView.findViewById(R.id.sotre_list_layout);
		linearLay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.v(TAG,"linearlay_store click");
				Intent intent = new Intent(v.getContext(),SelectOrderActivity.class);
				
				
				
				intent.putExtra("menuId", data.get(position).getMenuId());
				intent.putExtra("shopId", data.get(position).getShopId());
				intent.putExtra("menuName", data.get(position).getMenuName());
				intent.putExtra("menuImg", data.get(position).getMenuImg());
				intent.putExtra("price", data.get(position).getPrice());
				intent.putExtra("eventFunc", data.get(position).getEventFunc());
				intent.putExtra("descript", data.get(position).getDescript());
				intent.putExtra("shopName", data.get(position).getShopName());
				intent.putExtra("minPrice", data.get(position).getMinPrice());
				
				context.startActivity(intent);
				
			}
		});
		
		
	
		return cView;
	
	}
	
	}

 