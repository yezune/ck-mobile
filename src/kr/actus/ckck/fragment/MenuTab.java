package kr.actus.ckck.fragment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import kr.actus.ckck.MainActivity;
import kr.actus.ckck.R;
import kr.actus.ckck.gridlist.GridAdapter;
import kr.actus.ckck.gridlist.GridItem;
import kr.actus.ckck.util.SetURL;
import kr.actus.ckck.util.SetUtil;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MenuTab extends Fragment {
	MainActivity mainactivity;
	String title;
	CharSequence menuIndex;
	int localID = 1;
	AsyncHttpClient client = new AsyncHttpClient();
	SetURL url;
	SetUtil util;
	Dialog dg ;
	JSONObject con;
	GridItem item;
	GridAdapter adapter;
	
	ArrayList<GridItem> itemList = new ArrayList<GridItem>();
	String path;
	
	public MenuTab(MainActivity mainActivity, CharSequence menuIndex, CharSequence mTitle) {
		this.mainactivity = mainActivity;
		this.menuIndex = menuIndex;
		title = (String) mTitle;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_menu, container, false);
		 path = mainactivity.getExternalFilesDir(path).toString();
		Log.v(url.TAG,"path :"+path);
		init();
		
		
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	private void init(){
		RequestParams param = new RequestParams();
		
		param.put("localID", "1");
		param.put("shopCate", menuIndex);
		
		
		client.post(url.SHOPLIST, param, new JsonHttpResponseHandler(){

			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
				Log.v(url.TAG,"menu response error :"+errorResponse);
				dg.dismiss();
				super.onFailure(e, errorResponse);
			}

			@Override
			public void onSuccess(JSONArray response) {
				
				Log.v(url.TAG,"menu response array:"+response);
						try {				
							for(int i = 0 ;i<response.length();i++){
							con = response.getJSONObject(i);
							String title = con.getString("shopName");
							String type = con.getString("primeMenu");
							String minMoney = con.getString("minPrice");
							String delivery = con.getString("delivery");
							String img = con.getString("shopName");
							path = img;
							binaryClient(url.URL+img, path);
							
							
							
//							item = new GridItem(img, title, type, minMoney, delivery);
							
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						dg.dismiss();
					
				
				
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

	public void binaryClient(String hUrl, final String path) {
		Log.v(url.TAG,"binaryClient :"+path);
		String[] allow = new String[] { "image/png", "image/jpeg" };
		
		client.get(hUrl, new BinaryHttpResponseHandler(allow) {

		

			
			// 바이너리값다운성공시에 바이너리값을 기본 제공함.
			@Override
			public void onSuccess(byte[] fileData) {
				// TODO Auto-generated method stub
				Log.v(url.TAG, "JSON Download SUCCESS");

				try {
					FileOutputStream out = null;
					out = new FileOutputStream(path);

					out.write(fileData);
					
					out.close();
					Bitmap bitmap = BitmapFactory.decodeFile(path);
//					imgTitle.setImageBitmap(bitmap);
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] binaryData, Throwable error) {
				Log.v(url.TAG,"binary failure :"+error);

				super.onFailure(statusCode, headers, binaryData, error);
			}

		});
}
}
