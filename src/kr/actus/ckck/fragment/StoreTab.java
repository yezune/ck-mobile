package kr.actus.ckck.fragment;

import java.util.ArrayList;

import kr.actus.ckck.MainActivity;
import kr.actus.ckck.R;
import kr.actus.ckck.storelist.StoreListAdapter;
import kr.actus.ckck.storelist.StoreListItem;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class StoreTab extends Fragment implements OnClickListener{
	MainActivity mainactivity;
	View v;
	LinearLayout linearMenu, linearInform;
	Button btnMenu, btnInform;
	ListView listView;
	StoreListItem sItem;
	StoreListAdapter adapter;
	ArrayList<StoreListItem> storeListItem = new ArrayList<StoreListItem>();
	public StoreTab(MainActivity mainActivity) {
		this.mainactivity = mainActivity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		v = inflater.inflate(R.layout.fragment_store, container, false);
//		ActionBar actionbar = getActivity().getActionBar();
//		actionbar.setDisplayHomeAsUpEnabled(false);
		linearMenu = (LinearLayout) v.findViewById(R.id.store_lay_list);
		linearInform = (LinearLayout) v.findViewById(R.id.store_lay_inform);
		btnMenu = (Button) v.findViewById(R.id.store_btn_menu);
		btnInform = (Button) v.findViewById(R.id.store_btn_inform);
		listView = (ListView)v.findViewById(R.id.store_listview);
		
		sItem = new StoreListItem("유황오리 진흙구이","50,000원");
		storeListItem.add(sItem);
		
		adapter = new StoreListAdapter(v.getContext(), R.layout.store_list_item, storeListItem);
		listView.setAdapter(adapter);
		
		
		btnMenu.setSelected(true);
		btnMenu.setOnClickListener(this);
		btnInform.setOnClickListener(this);
		
		setList();
		return v;
	}

	private void setList() {
		// TODO Auto-generated method stub
		
	}

	private void layoutSet() {
		linearMenu.setVisibility(LinearLayout.INVISIBLE);
		linearInform.setVisibility(LinearLayout.INVISIBLE);
		
		btnMenu.setSelected(false);
		btnInform.setSelected(false);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case android.R.id.home:
			Bundle bundle = new Bundle();
			mainactivity.receive(bundle, mainactivity.MAINTAB);
			return true;

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.store_btn_menu:
			
			layoutSet();
			btnMenu.setSelected(true);
			linearMenu.setVisibility(LinearLayout.VISIBLE);
			break;
		case R.id.store_btn_inform:
			layoutSet();
			btnInform.setSelected(true);
			linearInform.setVisibility(LinearLayout.VISIBLE);
			break;
		}
		
	}

}
