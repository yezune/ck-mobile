package kr.actus.ckck.drawer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kr.actus.ckck.R;
import kr.actus.ckck.R.layout;
import kr.actus.ckck.myhistorylist.MyHistoryAdapter;
import kr.actus.ckck.myhistorylist.MyHistoryListItem;
import kr.actus.ckck.util.SetURL;
import kr.actus.ckck.util.SetUtil;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

public class MyHistoryActivity extends Activity {

	AsyncHttpClient client;
	SharedPreferences pref;
	SetURL ur;
	SetUtil util;
	Dialog dg;
	Context context;
	ListView listView;
	MyHistoryAdapter adapter;
	MyHistoryListItem item;
	ArrayList<MyHistoryListItem> itemList = new ArrayList<MyHistoryListItem>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_myhistory);
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	    pref = getSharedPreferences(ur.PREF, 0);
	    listView = (ListView) findViewById(R.id.myhistory_listView);
	    
	   context = this;
	    client = new AsyncHttpClient();
	    init();
	    
	   	}
	
	
	
	
	   	private void init() {
	   		String uniqueKey = pref.getString("uniqueKey", null);
	   		
	   		RequestParams param = new RequestParams();
	   		param.put("memberKey",uniqueKey);
	   		client.post(ur.MYORDER, param, new JsonHttpResponseHandler(){

				@Override
				public void onFailure(Throwable e, JSONArray errorResponse) {
					Log.v(ur.TAG," myhistory response :"+errorResponse);
					super.onFailure(e, errorResponse);
				}

				@Override
				public void onSuccess(JSONArray response) {
					
					Log.v(ur.TAG,"myhistory :"+response);
					try {
						for (int i = 0; i < response.length(); i++) {
							JSONObject con = response.getJSONObject(i);
							
							String orderId= con.getString("orderID");
							String shopId= con.getString("shopID");
							String shopName= con.getString("shopName");
							String orderMenu= con.getString("orderMenu");
							String orderTime= con.getString("orderTime");
							int price= con.getInt("orderPrice");
							int payType = con.getInt("payType");
							String address= con.getString("address");
							String descript= con.getString("Descript");
							int status= con.getInt("Status");
							String deliverName= con.getString("deliverName");
							item = new MyHistoryListItem(orderId, shopId, shopName, orderMenu, orderTime, 
									price, payType, address, descript, status, deliverName);
							itemList.add(item);
					
						}
						adapter = new MyHistoryAdapter(context, R.layout.myhistory_list_item, itemList);
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
					dg = util.setProgress(context);
					super.onStart();
				}

				
	   			
	   			
	   		});
		
	}
		@Override
	   	public boolean onOptionsItemSelected(MenuItem item) {
	   		// TODO Auto-generated method stub
	   		switch(item.getItemId()){
	   		case android.R.id.home:
	   			finish();
	   			return true;
	   		}
	   		return super.onOptionsItemSelected(item);
	   	}

}
