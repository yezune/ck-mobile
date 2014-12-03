package kr.actus.ckck.myhistorylist;



import java.util.ArrayList;

import kr.actus.ckck.MainActivity;
import kr.actus.ckck.R;
import kr.actus.ckck.SelectOrderActivity;
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

public class MyHistoryAdapter extends BaseAdapter  {
	private static final String TAG = "MainActivity";
	
	Context context;
	LayoutInflater Inflater;
	
	TextView tvShopName,tvPrice,tvMenu;
	LinearLayout linearLay;
	
	ArrayList<MyHistoryListItem> data = new ArrayList<MyHistoryListItem>();
	int layout;
	
	
	public MyHistoryAdapter(Context context, int layout, ArrayList<MyHistoryListItem> data){
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

	
	
	public View getView(int position , View cView, ViewGroup parent) {

		
		
		if (cView == null) {
			cView = Inflater.inflate(layout, parent, false);
		}

		
	
		tvShopName = (TextView)cView.findViewById(R.id.myhistory_item_tv_shopName);
		tvShopName.setText(data.get(position).getShopName());

		tvMenu = (TextView)cView.findViewById(R.id.myhistory_item_tv_orderMenu);
		tvMenu.setText(data.get(position).getOrderMenu());
	
		tvPrice = (TextView)cView.findViewById(R.id.myhistory_item_tv_price);
		tvPrice.setText(data.get(position).getPrice()+"¿ø");
		
		
		linearLay = (LinearLayout)cView.findViewById(R.id.myhistory_item_layout);
		
		
		linearLay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});

		
		
		
	
		return cView;
	
	}
	
	}

 