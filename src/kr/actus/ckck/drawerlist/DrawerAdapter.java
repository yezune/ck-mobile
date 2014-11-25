package kr.actus.ckck.drawerlist;

import java.util.ArrayList;

import kr.actus.ckck.MainActivity;
import kr.actus.ckck.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DrawerAdapter extends BaseAdapter {
	
private static final String TAG = "MainActivity";
	MainActivity activity;
	Context context;
	LayoutInflater inflater;
	
	TextView tvTitle,tvPrice;
	
	DrawerItem item;
	ArrayList<DrawerItem> data = new ArrayList<DrawerItem>();
	int layout;
	
public DrawerAdapter(MainActivity activity,Context context,int layout,ArrayList<DrawerItem> data){
		this.context = context;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		this.data = data;
		this.layout = layout;
		this.activity = activity;
		
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
		
		tvTitle = (TextView)v.findViewById(R.id.drawer_list_item_body);
		tvTitle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				activity.receive(data.get(position).getTitle().toString());
				
				
				
				
			}
		});
		
		return v;
	}

}
