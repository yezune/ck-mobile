package kr.actus.ckck.drawer;

import org.json.JSONArray;
import org.json.JSONException;

import kr.actus.ckck.R;
import kr.actus.ckck.R.layout;
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

public class MyHistoryActivity extends Activity {

	AsyncHttpClient client;
	SharedPreferences pref;
	SetURL ur;
	SetUtil util;
	Dialog dg;
	Context context;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_myhistory);
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	    pref = getSharedPreferences(ur.PREF, 0);
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
					Log.v(ur.TAG," myhistory response :"+response);
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

				@Override
				protected Object parseResponse(String responseBody)
						throws JSONException {
					Log.v(ur.TAG," myhistory responseBody :"+responseBody);
					return super.parseResponse(responseBody);
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
