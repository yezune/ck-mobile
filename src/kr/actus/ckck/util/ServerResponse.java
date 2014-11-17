package kr.actus.ckck.util;

import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ServerResponse {
	static AsyncHttpClient client = new AsyncHttpClient();
	static JSONObject result;
	
	
	public static JSONObject postRequest(String getUrl,RequestParams param){
		
		client.post(getUrl, param, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(JSONObject response) {
				result = response;
				super.onSuccess(response);
			}

			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
				result = null;
				super.onFailure(e, errorResponse);
			}
			
			
			
			
		});
		return result;
		
		
	}



	
	
	
	
	
}
