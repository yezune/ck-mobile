package kr.actus.ckck.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import kr.actus.ckck.MainActivity;
import kr.actus.ckck.R;
import kr.actus.ckck.gridlist.GridAdapter;
import kr.actus.ckck.gridlist.GridItem;
import kr.actus.ckck.util.AsyncData;
import kr.actus.ckck.util.SetURL;
import kr.actus.ckck.util.SetUtil;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class MenuTab extends Fragment {
	MainActivity mainactivity;
	String cateName;
	String shopCate;
	int localID = 1;
	AsyncHttpClient client = new AsyncHttpClient();
	SetURL ur;
	SetUtil util;
	Dialog dg;
	JSONObject con;
	GridItem item;
	GridAdapter adapter;
	GridView gridList;
	ArrayList<GridItem> itemList = new ArrayList<GridItem>();
	String path = ur.path;
	AQuery aq;
	AsyncData binary;
	View v;
	Bundle bundle;

	public MenuTab(MainActivity mainActivity,Bundle bundle) {
		this.mainactivity = mainActivity;
		this.bundle = bundle;
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
       
		 v = inflater.inflate(R.layout.fragment_menu, container, false);
       
		gridList = (GridView) v.findViewById(R.id.menu_grid_list);
		itemList.clear();
		if (bundle!=null){
			cateName = bundle.getString("cateName");
			shopCate = bundle.getString("shopCate");	
			
		}
		mainactivity.setTitle(cateName);
		Log.v(ur.TAG,"menu bundle : "+ bundle.getString("cateName"));
		init();

		return v;
	}

	private void init() {
		RequestParams param = new RequestParams();

		param.put("localID", "1");
		param.put("shopCate", shopCate);

		client.post(ur.SHOPLIST, param, new JsonHttpResponseHandler() {

			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
				Log.v(ur.TAG, "menu response error :" + errorResponse);
				dg.dismiss();
				super.onFailure(e, errorResponse);
			}

			@Override
			public void onSuccess(JSONArray response) {

				Log.v(ur.TAG, "menu response array:" + response);
				try {
					for (int i = 0; i < response.length(); i++) {
						con = response.getJSONObject(i);
						String shopName = con.getString("shopName");
						String shopId = con.getString("shopID");
						String type = con.getString("primeMenu");
						int minMoney = con.getInt("minPrice");
						String delivery = con.getString("delivery");
						String img = con.getString("shopImage");
						
						String telNumber = con.getString("telNumber");
						String sTime = con.getString("startTime");
						String eTime = con.getString("endTime");
						File file = new File(path + img);
						Log.v(ur.TAG, "file path+img" + path+img);
						String savefile = path+img;
						if (!file.exists()) {
							savefile = util.filePath(path+img);
							binary = new AsyncData(v.getContext());
							binary.binaryClient(img,savefile);

							// asyncBinary(img);
						}
						

						 item = new GridItem(savefile, shopId, shopName, type, minMoney, delivery,telNumber,sTime,eTime);
						 itemList.add(item);
						
						
					}
					
					 adapter = new GridAdapter(mainactivity, getActivity(), R.layout.gridview_item, itemList);
					 gridList.setAdapter(adapter);
					
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

	// public void asyncBinary(final String imgUrl){
	// aq = new AQuery(mainactivity);
	//
	// aq.ajax(ur.URL+imgUrl, byte[].class, new AjaxCallback<byte[]>(){
	//
	// @Override
	// public void callback(String url, byte[] object, AjaxStatus status) {
	// Log.v(ur.TAG,"bytes array:"+object.length);
	//
	// FileOutputStream out = null;
	// fullImg = filePath(imgUrl);
	// Log.v(ur.TAG,"save path :"+fullImg);
	// try {
	// File file = new File(fullImg);
	//
	// out = new FileOutputStream(fullImg);
	//
	// out.write(object);
	// out.close();
	//
	//
	//
	// }catch (FileNotFoundException e1) {
	// Log.v(ur.TAG,"FileNotFoundException :"+e1);
	// e1.printStackTrace();
	// }catch (IOException e) {
	// Log.v(ur.TAG,"IOException :"+e);
	// e.printStackTrace();
	// }
	// super.callback(url, object, status);
	// }
	// });
	// }
	// 파일저장 경로 및 파일명 적용.


}