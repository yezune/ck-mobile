package kr.actus.ckck.gridlist;



import java.util.ArrayList;

import kr.actus.ckck.R;




import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
	Context context;
	LayoutInflater Inflater;
	
	
	
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
			ImageView menuImg = (ImageView)cView.findViewById(R.id.grid_img);
		menuImg.setImageResource(data.get(position).getImg());
		
		TextView tvTitle = (TextView)cView.findViewById(R.id.grid_tv_title);
		tvTitle.setText(data.get(position).getTitle().toString());
	
		
		TextView tvType = (TextView)cView.findViewById(R.id.grid_tv_type);
		tvType.setText(data.get(position).getType().toString());
		
		TextView tvMin = (TextView)cView.findViewById(R.id.grid_tv_min);
		tvMin.setText(data.get(position).getMinMoney().toString());
		TextView tvDelivery = (TextView)cView.findViewById(R.id.grid_tv_deli);
		tvDelivery.setText(data.get(position).getDelivery().toString());
		
		menuImg.setOnClickListener(new OnClickListener() {				
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			
				
			}
		});
		
		
		
		return cView;
	
	}
	}

 