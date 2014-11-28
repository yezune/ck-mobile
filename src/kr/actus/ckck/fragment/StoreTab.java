package kr.actus.ckck.fragment;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import kr.actus.ckck.MainActivity;
import kr.actus.ckck.R;
import kr.actus.ckck.gridlist.GridAdapter;
import kr.actus.ckck.gridlist.GridItem;
import kr.actus.ckck.storelist.StoreListAdapter;
import kr.actus.ckck.storelist.StoreListItem;
import kr.actus.ckck.util.AsyncData;
import kr.actus.ckck.util.SetURL;
import kr.actus.ckck.util.SetUtil;
import android.app.ActionBar;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class StoreTab extends Fragment implements OnClickListener{
	MainActivity mainactivity;
	View v;
	LinearLayout linearMenu, linearInform;
	Button btnMenu, btnInform;
	ListView listView;
	ImageView img;
	StoreListItem sItem;
	StoreListAdapter adapter;
	ArrayList<StoreListItem> storeListItem = new ArrayList<StoreListItem>();
	Bundle savebundle;
	SetURL ur;
	SetUtil util;
	AsyncHttpClient client ;
	String path = ur.path;
	AsyncData asyncData;
	Dialog dg;
	
	String shopId,shopName, menuImg, delivery,telNumber, sTime,eTime;
	TextView tvTelNumber, tvDelivery,tvTime,tvMoney;
	public StoreTab(MainActivity mainActivity, Bundle bundle) {
		this.mainactivity = mainActivity;
		this.savebundle = bundle;
		Log.v(ur.TAG,"storeTab bundle :"+savebundle);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		v = inflater.inflate(R.layout.fragment_store, container, false);
		ActionBar actionbar = getActivity().getActionBar();
		client = new AsyncHttpClient();
		shopId = savebundle.getString("shopId");
		shopName = savebundle.getString("shopName");
		menuImg = savebundle.getString("menuImg");
		
		delivery=savebundle.getString("delivery");
		if(delivery.equals("0")){
			delivery="배달의뢰";
		}else{
			delivery="직접배달";
		}
		telNumber=savebundle.getString("telNumber");
		sTime=savebundle.getString("sTime");
		eTime=savebundle.getString("eTime");
		
		tvTelNumber=(TextView) v.findViewById(R.id.store_tv_phone);
		tvDelivery=(TextView) v.findViewById(R.id.store_tv_delivery);
		tvTime=(TextView) v.findViewById(R.id.store_tv_time);
		tvMoney=(TextView) v.findViewById(R.id.store_tv_money);
		
		tvTelNumber.setText(telNumber);
		tvDelivery.setText(delivery);
		tvTime.setText(sTime+" ~ "+eTime);
		
		
//		actionbar.setDisplayHomeAsUpEnabled(false);
		linearMenu = (LinearLayout) v.findViewById(R.id.store_lay_list);
		linearInform = (LinearLayout) v.findViewById(R.id.store_lay_inform);
		btnMenu = (Button) v.findViewById(R.id.store_btn_menu);
		btnInform = (Button) v.findViewById(R.id.store_btn_inform);
		img = (ImageView)v.findViewById(R.id.store_img);
		
		
		Bitmap bitmap = BitmapFactory.decodeFile(menuImg);
		img.setImageBitmap(bitmap);
		listView = (ListView)v.findViewById(R.id.store_listview);
		
		actionbar.setTitle(savebundle.getString("shopName"));
		
		
		init();
		
		
		btnMenu.setSelected(true);
		btnMenu.setOnClickListener(this);
		btnInform.setOnClickListener(this);
		
		
		return v;
	}

	private void init() {
		
		RequestParams param = new RequestParams();

//		param.put("localID", "1");
		param.put("shopID", shopId);
		Log.v(ur.TAG,"bundle.getString shopID :"+shopId);
//		dg = util.setProgress(mainActivity);
//		asyncData = new AsyncData(cView.getContext(), ur.SHOPLIST,param);
//		JSONArray response  = (JSONArray) asyncData.postJSONArray();
//		
		client.post(ur.MENULIST, param, new JsonHttpResponseHandler() {

			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
				Log.v(ur.TAG, "store response error :" + errorResponse);
//				dg.dismiss();
				super.onFailure(e, errorResponse);
			}

			@Override
			public void onSuccess(JSONArray response) {

				Log.v(ur.TAG, "store response array:" + response);
				try {
					for (int i = 0; i < response.length(); i++) {
						JSONObject con = response.getJSONObject(i);
						String shopId = con.getString("shopID");
						String menuId = con.getString("menuID");
						String menuName = con.getString("menuName");
						String img = con.getString("menuImage");
						Log.v(ur.TAG,"img : "+img);
						int price = con.getInt("price");
						String eventFunc = con.getString("eventFunc");
						String descript = con.getString("Descript");
						File file = new File(path + img);
//						Log.v(ur.TAG, "file path+img" + img);
						if (!file.exists()) {
							String savefile = util.filePath(path+img);
							asyncData = new AsyncData(getActivity());
							asyncData.binaryClient(img,savefile);

							// asyncBinary(img);
						}
						
						sItem = new StoreListItem(menuId,shopId,shopName,menuName,path+img,price,eventFunc,descript);
						storeListItem.add(sItem);
						
//						 sItem = new StoreListItem(shopName,type);
//						 storeListItem.add(sItem);
						 
					}
					adapter = new StoreListAdapter(mainactivity, R.layout.store_list_item, storeListItem);
					listView.setAdapter(adapter);
					 

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				super.onSuccess(response);
			}

			@Override
			public void onFinish() {
				dg.dismiss();
				super.onFinish();
			}

			@Override
			public void onStart() {

				dg = util.setProgress(getActivity());
				super.onStart();
			}

		});
		
		
		
		
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
