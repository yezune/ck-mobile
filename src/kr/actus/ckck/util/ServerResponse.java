package kr.actus.ckck.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.JsonReader;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ServerResponse {
	private static final String TAG = "MainActivity";
	AsyncHttpClient client = new AsyncHttpClient();
	JSONObject object = null;
//	JSONArray resultArray=null;
	JSONArray array=null;
	

	public JSONArray postJSONArray(String getUrl, RequestParams param) {
		
		client.post(getUrl, param, new JsonHttpResponseHandler() {
			
		
		

			@Override
			public void onSuccess(int statusCode, JSONArray response) {
				Log.v(TAG,"JSONArray response : "+response);
				array = response;
				super.onSuccess(statusCode, response);
			}

			@Override
			public void onFailure(Throwable e, JSONArray errorResponse) {
				Log.v(TAG,"JSONArray errorResponse : "+errorResponse);
				array = errorResponse;
				super.onFailure(e, errorResponse);
			}
			

		});
		
		return array;

	}
public JSONObject postJSONObject(String getUrl, RequestParams param) {
		
		client.post(getUrl, param, new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONObject response) {
				object = response;
				Log.v(TAG,"success Response : "+response);
				super.onSuccess(response);
			}

			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
				object = errorResponse;
				Log.v(TAG,"postRequest failure : "+e+"\nerrorResponse : "+errorResponse);
				super.onFailure(e, errorResponse);
			}

		

			

		});
		
		return object;

	}
}
