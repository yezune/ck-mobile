package kr.actus.ckck.setaddrlist;



import java.util.ArrayList;

import kr.actus.ckck.R;
import kr.actus.ckck.setaddr.SetAddrActivity;
import kr.actus.ckck.setaddr.SetAddrTab;
import kr.actus.ckck.util.SetURL;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class SetAddrAdapter extends BaseAdapter  {
	private static final String TAG = "MainActivity";
	
	Context context;
	LayoutInflater Inflater;
	SetURL ur;
	TextView tvAddr,tvPost;
	Button btnSelect;
	SetAddrTab activity;
	ArrayList<SetAddrListItem> data = new ArrayList<SetAddrListItem>();
	int layout;
	SharedPreferences pref;
	SharedPreferences.Editor editor;
	
	
	public SetAddrAdapter(SetAddrTab activity,Context context, int layout, ArrayList<SetAddrListItem> data){
		this.context = context;
		this.activity = activity;
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
	
		
		tvAddr = (TextView)cView.findViewById(R.id.setaddr_list_addr);
		tvAddr.setText(data.get(position).getAddr());
	
		
		tvPost = (TextView)cView.findViewById(R.id.setaddr_list_post);
		tvPost.setText(data.get(position).getPost());
		
		btnSelect = (Button)cView.findViewById(R.id.setaddr_list_btn);
		btnSelect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				String address = data.get(position).getPost()+ " " + data.get(position).getAddr();
				pref = activity.getActivity().getSharedPreferences(ur.PREF, 0);
				editor = pref.edit();
				editor.putString("deliAddr", address);
				editor.commit();
				Log.v(ur.TAG,"pref.gsetString deliAddr : "+pref.getString("deliAddr", "0"));
				
				activity.requestAdapter(address);
				
			}
		});
		
		
		
	
		return cView;
	
	}
	
	}

 