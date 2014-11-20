package kr.actus.ckck.gridlist;



import java.util.ArrayList;

import kr.actus.ckck.R;
import kr.actus.ckck.StoreActivity;




import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
	private static final String TAG = "MainActivity";
	Context context;
	LayoutInflater Inflater;
	
	TextView tvTitle,tvType,tvMin,tvDelivery;
	ImageView menuImg;
	ArrayList<GridItem> data = new ArrayList<GridItem>();
	GridItem item;
	int layout;
	BaseAdapter hndle = this;
	
	
	
	public GridAdapter(Context context, int layout, ArrayList<GridItem> data){
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
		menuImg = (ImageView)cView.findViewById(R.id.grid_img);
		menuImg.setImageResource(data.get(position).getImg());
		
		tvTitle = (TextView)cView.findViewById(R.id.grid_tv_title);
		tvTitle.setText(data.get(position).getTitle().toString());
	
		
		tvType = (TextView)cView.findViewById(R.id.grid_tv_type);
		tvType.setText(data.get(position).getType().toString());
		
		tvMin = (TextView)cView.findViewById(R.id.grid_tv_min);
		tvMin.setText(data.get(position).getMinMoney().toString());
		tvDelivery = (TextView)cView.findViewById(R.id.grid_tv_deli);
		tvDelivery.setText(data.get(position).getDelivery().toString());
		
		menuImg.setOnClickListener(new OnClickListener() {				
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, StoreActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("title", tvTitle.getText().toString());
				context.startActivity(intent);
				//Log.v(TAG,"getItematpostion :"+gridList.getItemAtPosition(pos));
				}
		});
		return cView;
	
	}
	}

 