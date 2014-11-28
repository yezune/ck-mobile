package kr.actus.ckck.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AsyncData {
	private static final String TAG = "MainActivity";
	AsyncHttpClient client;
	JSONObject object = null;
//	JSONArray resultArray=null;
	JSONArray array=null;
	Dialog dg;
	SetUtil util;
	Context context;
	String getUrl;
	RequestParams param;
	public AsyncData(Context context, final String getUrl, RequestParams param){
		this.context = context;
		this.getUrl = getUrl;
		this.param = param;
	}
	public AsyncData(Context context){
		this.context = context;
		
	}
	

	public JSONArray postJSONArray() {
		Log.v(TAG,"postJSONArray");
		client = new AsyncHttpClient();
		
		client.post(getUrl, param, new JsonHttpResponseHandler() {
			
		
		

			@Override
			public void onSuccess(JSONArray response) {
				Log.v(TAG,"JSONArray response : "+response);
//				dg.dismiss();
				array = response;
				super.onSuccess(response);
			}

			@Override
			public void onFailure(Throwable e, JSONArray errorResponse) {
				Log.v(TAG,"JSONArray errorResponse : "+errorResponse);
				array = errorResponse;
				super.onFailure(e, errorResponse);
			}

		

			@Override
			protected Object parseResponse(String responseBody)
					throws JSONException {
				Log.v(TAG,"JSONArray response : "+responseBody);
				return super.parseResponse(responseBody);
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
		
		return object;

	}

	public void binaryClient(final String imgUrl, final String saveFile) {
		client = new AsyncHttpClient();
		String[] allow = new String[] { "image/png","image/jpeg" };
		client.get(SetURL.URL+imgUrl,new BinaryHttpResponseHandler() {
					// 바이너리값다운성공시에 바이너리값을 기본 제공함.
					@Override
					public void onSuccess(byte[] fileData) {
						// TODO Auto-generated method stub
						
						FileOutputStream out = null;
						
					
						try {

							out = new FileOutputStream(saveFile);

							out.write(fileData);
							out.close();

						} catch (FileNotFoundException e1) {
							Log.v(TAG, "FileNotFoundException :" + e1);
							e1.printStackTrace();
						} catch (IOException e) {
							Log.v(TAG, "IOException :" + e);
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] binaryData, Throwable error) {
						Log.v(TAG, "BinaryDate error :" + error);
						super.onFailure(statusCode, headers, binaryData, error);
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



}
