package kr.actus.ckck.gridlist;



import java.util.ArrayList;

import kr.actus.ckck.MainActivity;
import kr.actus.ckck.R;
import kr.actus.ckck.fragment.MainTab;
import kr.actus.ckck.fragment.StoreTab;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import android.widget.TextView;

public class GridAdapter extends BaseAdapter  {
	private static final String TAG = "MainActivity";
	
	Context context;
	LayoutInflater Inflater;
	
	TextView tvTitle,tvType,tvMin,tvDelivery;
	ImageView menuImg;
	ArrayList<GridItem> data = new ArrayList<GridItem>();
//	GridItem item;
	int layout;
	MainActivity activity;
	Bitmap bitmap;
	
	Fragment fragment = null;
	
	public GridAdapter(MainActivity activity,Context context, int layout, ArrayList<GridItem> data){
		this.context = context;
		this.Inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.data = data;
		this.layout = layout;
		this.activity = activity;
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
		
		Log.v(TAG,"data.getposiotion :"+data.get(position).getTitle());
		
		menuImg = (ImageView)cView.findViewById(R.id.grid_img);
		bitmap = BitmapFactory.decodeFile(data.get(position).getImgPath());
		menuImg.setImageBitmap(bitmap);
		
		tvTitle = (TextView)cView.findViewById(R.id.grid_tv_title);
		tvTitle.setText(data.get(position).getTitle());
	
		
		tvType = (TextView)cView.findViewById(R.id.grid_tv_type);
		tvType.setText(data.get(position).getType());
		
		tvMin = (TextView)cView.findViewById(R.id.grid_tv_min);
		tvMin.setText("최소주문:"+data.get(position).getMinMoney()+"원 이상");
		tvDelivery = (TextView)cView.findViewById(R.id.grid_tv_deli);
		String deli;
		if(data.get(position).getDelivery().equals("0")) deli = "배달의뢰";
		else deli = "직접배달";
		tvDelivery.setText(deli);
		
		
		menuImg.setOnClickListener(new OnClickListener() {				
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(context, StoreActivity.class);
//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				intent.putExtra("title", tvTitle.getText().toString());
//				context.startActivity(intent);
				
				
				Bundle savebundle = new Bundle();
				savebundle.putString("shopName", data.get(position).getTitle().toString());
				savebundle.putString("type", data.get(position).getType().toString());
				savebundle.putInt("minPrice", data.get(position).getMinMoney());
				savebundle.putString("delivery", data.get(position).getDelivery().toString());
				savebundle.putString("menuImg", data.get(position).getImgPath());
				savebundle.putString("shopId", data.get(position).getShopId());
				savebundle.putString("telNumber", data.get(position).getTelNumber());
				savebundle.putString("sTime", data.get(position).getsTime());
				savebundle.putString("eTime", data.get(position).geteTime());
				
				activity.receive(savebundle,activity.STORETAB);
				
				
				
				//				nextActivity();
				
				//Log.v(TAG,"getItematpostion :"+gridList.getItemAtPosition(pos));
				}
		});
		return cView;
	
	}
	
	}

 