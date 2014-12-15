package kr.actus.ckck;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import kr.actus.ckck.setaddrlist.SetAddrAdapter;
import kr.actus.ckck.setaddrlist.SetAddrListItem;
import kr.actus.ckck.util.AsyncData;
import kr.actus.ckck.util.SetURL;
import kr.actus.ckck.util.SetUtil;
import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class AddUserActivity extends Activity implements OnClickListener{
	private final String TAG = "MainActivity";
	String uniqueKey;
	EditText regKey,cName, name, edAddr1, edAddr2, mobile;
	CheckBox cbSms,cbAgree1,cbAgree2;
	boolean sms,email,agree1,agree2;
	Button inReg, btnPost;
	ListView listView;
	SetURL ur;
	SetUtil util;
	Spinner mobileSpin;
	String fMobileNum;
	ArrayAdapter<CharSequence> spinAdapter;
	SharedPreferences pref;
	SharedPreferences.Editor editor;
	AsyncHttpClient client;
	AsyncData sr; 
	SAXBuilder builder;
	Document doc;
	Dialog dg;
	SetAddrListItem item;
	SetAddrAdapter adapter;
	LinearLayout layBasic,layPost;
	
	ArrayList<SetAddrListItem> itemList = new ArrayList<SetAddrListItem>();
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_adduser);
	    getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.title_add_user);
	    pref = getSharedPreferences(ur.PREF, 0);
		editor = pref.edit();
		
	    init();
	    setSpinner();
	    
	    uniqueKey = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);
	    client = new AsyncHttpClient();
	}
	   
	private void setSpinner() {
		spinAdapter = ArrayAdapter.createFromResource(this, R.array.user_mobile, android.R.layout.simple_spinner_item);
		spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mobileSpin.setAdapter(spinAdapter);
		mobileSpin.setSelection(0);
		fMobileNum = (String) mobileSpin.getItemAtPosition(0);
		mobileSpin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				
				fMobileNum = (String) mobileSpin.getItemAtPosition(pos);
				
				Log.v(TAG,"moNum :"+fMobileNum);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
				
			}
		
		
		
		});
		
	}

	private void init() {
		regKey = (EditText) findViewById(R.id.user_regKey);
		cName= (EditText) findViewById(R.id.user_cName);
		name= (EditText) findViewById(R.id.user_name);
		edAddr1= (EditText) findViewById(R.id.user_add);
		edAddr2= (EditText) findViewById(R.id.user_dadd);
		mobile= (EditText) findViewById(R.id.user_mobile);
		
		cbSms = (CheckBox) findViewById(R.id.user_check_sms);
//		cbEmail = (CheckBox) findViewById(R.id.user_check_email);
		cbAgree1 = (CheckBox) findViewById(R.id.user_check_agree1);
		cbAgree2 = (CheckBox) findViewById(R.id.user_check_agree2);
		mobileSpin = (Spinner) findViewById(R.id.user_mobile_spinner);
		inReg = (Button)findViewById(R.id.user_btn_reg);
		inReg.setOnClickListener(this);
		btnPost = (Button)findViewById(R.id.user_btn_post);
		btnPost.setOnClickListener(this);
		
		layBasic = (LinearLayout)findViewById(R.id.user_lay1);
		layPost = (LinearLayout)findViewById(R.id.user_lay2);
		
		listView = (ListView)findViewById(R.id.user_listView);
		
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

	@Override
	public void onClick(final View v) {
		switch(v.getId()){
		case R.id.user_btn_reg:
			if(!cbSms.isChecked() && !cbAgree1.isChecked() && !cbAgree2.isChecked()){
				
			Toast.makeText(this, "서비스 제공을 위하여 동의 해주세요.", Toast.LENGTH_SHORT).show();	
			}else{
//				Dialog pro = util.setProgress(this);
				
				RequestParams param = new RequestParams();
				param.put("uniqueKey",uniqueKey);
				param.put("memName", name.getText().toString());
				param.put("regKey", regKey.getText().toString());
				param.put("mobile", fMobileNum+mobile.getText().toString());
				param.put("address1",edAddr1.getText().toString());
				param.put("address2", edAddr2.getText().toString());
				
//				sr = new AsyncData(this,SetURL.JOIN, param);
				client.post(SetURL.JOIN, param, new JsonHttpResponseHandler(){

					@Override
					public void onFailure(int statusCode, Throwable e,
							JSONObject errorResponse) {
						Toast.makeText(v.getContext(), "오류가 발생했습니다.다시시도해주세요.", Toast.LENGTH_SHORT).show();	
						super.onFailure(statusCode, e, errorResponse);
					}

					@Override
					public void onSuccess(JSONObject response) {
						Log.v(TAG,"success :"+response);
						Toast.makeText(v.getContext(), "가입성공", Toast.LENGTH_SHORT).show();	
						editor.putString("uniqueKey", uniqueKey);
						editor.putString("memName", name.getText().toString());
						editor.putString("regKey", regKey.getText().toString());
						editor.putString("mobile", mobile.getText().toString());
						editor.putString("address1", edAddr1.getText().toString());
						editor.putString("address2", edAddr2.getText().toString());
						
						editor.putBoolean("sms", cbSms.isChecked());
						editor.putBoolean("agree1", cbAgree1.isChecked());
						editor.putBoolean("agree2", cbAgree2.isChecked());
//						
						editor.commit();
						
						
						setResult(0);
						finish();
						super.onSuccess(response);
					}

					@Override
					protected Object parseResponse(String responseBody)
							throws JSONException {
						Log.v(TAG,"parseResponse :"+ responseBody);
						return super.parseResponse(responseBody);
					}

					@Override
					public void onFinish() {
						dg.dismiss();
						super.onFinish();
					}

					@Override
					public void onStart() {
						dg = util.setProgress(v.getContext());
						super.onStart();
					}
					
					
					
					
					
					
					
					
				});
//				JSONArray result = sr.postJSONArray();
//				Log.v(TAG,"result :"+result);
//				try {
//					
//				if(result!=null){
//					
//						if(result.getJSONArray(0).equals("ok")){
////							pro.dismiss();
//							Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
//							editor.putString("uniqueKey", uniqueKey);
//							editor.putString("memName", name.getText().toString());
//							editor.putString("regKey", regKey.getText().toString());
//							editor.putString("mobile", mobile.getText().toString());
////							editor.putString("address1", edAddr1.getText().toString());
//							editor.putString("address2", edAddr2.getText().toString());
//							
//							editor.putBoolean("sms", cbSms.isChecked());
//							editor.putBoolean("agree1", cbAgree1.isChecked());
//							editor.putBoolean("agree2", cbAgree2.isChecked());
//							
//							editor.commit();
//							
//						}else if(result.getJSONArray(0).equals("fail")){
////							pro.dismiss();
//							Toast.makeText(this, "오류 : "+result.getJSONArray(1), Toast.LENGTH_SHORT).show();
//							
//						}
//						
//						
//					
//					
//				}else
//				{
////					pro.dismiss();
//					Toast.makeText(this, "다시 시도 하세요.", Toast.LENGTH_SHORT).show();
//					
//				}
//				
//				
//				}catch (JSONException e) {
////					pro.dismiss();
//					Log.v(TAG,"jsonException :"+e);
//					e.printStackTrace();
//				}
			}
			
			
			break;
		case R.id.user_btn_post:
			
			if(edAddr1.getText().toString().equalsIgnoreCase("")){
				Toast.makeText(v.getContext(), "검색할 주소를 입력하세요", Toast.LENGTH_SHORT).show();
			}else{
				
				requestPost();
				
			}
			break;
		}
		
	}

	private void requestPost() {
		try {
			layPost.setVisibility(View.VISIBLE);
			layBasic.setVisibility(View.INVISIBLE);
			
			String url = ur.POSTIP + "?regkey=" + ur.REGKEY + "&target="
					+ ur.TARGET + "&query="
					+ URLEncoder.encode(edAddr1.getText().toString(), "EUC-KR");

			client.addHeader("accept-language", "ko");
			client.get(url, new AsyncHttpResponseHandler() {

				

				@Override
				public void onSuccess(int stat, Header[] header, byte[] binary) {
					try {
						
//						if(binary.length>204){
						InputStream is = null;
//						Log.v(ur.TAG,"binary size :"+binary.length);
						is = new ByteArrayInputStream(binary);

						// XML 파싱
						builder = new SAXBuilder();
						doc = builder.build(is);
//						Log.v(ur.TAG,"doc :"+doc);
						//itemlist하위에 우편번호와 주소값을 가짐
						
						Element itemlist;
							
						if(doc.getRootElement().getName().equals("post")){
						itemlist = doc.getRootElement().getChild("itemlist");
						List list = itemlist.getChildren();
						
						itemList.clear();
						//검색결과가 여러개인 경우 반복하며 우편번호와 주소값을 뽑아낸다
						for(int i=0; i<list.size();i++){
							Element eitem = (Element)list.get(i);
							String address = eitem.getChildText("address");
							String postcd = eitem.getChildText("postcd");
							//address와 postcd 변수를 이용하여 자신에게 알맞는 형태로 사용하기
//							this.cbAddr.addItem(postcd+" | "+address);
							
							item = new SetAddrListItem(address, postcd);
							itemList.add(item);
							
						}
							
							
						}else if(doc.getRootElement().getName().equals("error")){
							itemlist = doc.getRootElement();
							
							String msg = itemlist.getChildText("message");
							Toast.makeText(AddUserActivity.this, msg+"다시 검색 하세요", Toast.LENGTH_SHORT).show();
							edAddr1.setText("");
							
							
						}
						
					
//						}else{
//							Toast.makeText(v.getContext(), "검색하실 주소를 다시 입력하세요.", Toast.LENGTH_SHORT).show();
//						}
						
						
						adapter = new SetAddrAdapter(AddUserActivity.this,getBaseContext(), R.layout.setaddr_list_item, itemList);
						listView.setAdapter(adapter);
						

					} catch (JDOMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
					super.onSuccess(stat, header, binary);
				}

				@Override
				public void onFinish() {
					dg.dismiss();
					super.onFinish();
				}

				@Override
				public void onStart() {
					dg = util.setProgress(AddUserActivity.this);
					super.onStart();
				}

				

			});

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void requestAdapter(String addr1,String addr2){
		String address = addr1+ " " + addr2;
		
		
		editor.putString("addressPost", addr1);
		editor.putString("address1", addr2);
		editor.commit();
		
		Log.v(ur.TAG,"pref.gsetString deliAddr : "+pref.getString("address2", null));
		edAddr1.setText(addr2);
		
		itemList.clear();
		adapter.notifyDataSetChanged();
		layBasic.setVisibility(View.VISIBLE);
		layPost.setVisibility(View.INVISIBLE);
		
	}
}
