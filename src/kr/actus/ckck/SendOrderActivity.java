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
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import kr.actus.ckck.cartlist.CartAdapter;
import kr.actus.ckck.cartlist.CartItem;
import kr.actus.ckck.setaddr.SetAddrTab;
import kr.actus.ckck.setaddrlist.SetAddrAdapter;
import kr.actus.ckck.setaddrlist.SetAddrListItem;
import kr.actus.ckck.util.SetURL;
import kr.actus.ckck.util.SetUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SendOrderActivity extends Activity implements OnClickListener,
		RadioGroup.OnCheckedChangeListener, OnCheckedChangeListener {
	
	final int CBCASH=0;
	final int CBCARD=1;
	final int CBLOCASH=2;
	
	Button btnFinish,btnPost;
	CheckBox cbAddr, cbSms, cbAgree1, cbAgree2;
	EditText edAddr1, edAddr2, edRequest, edMobile;
	TextView tvPriceSum;
	ListView listView,postList;
	RadioGroup rg;
	RadioButton rb;
	Intent intent;
	SharedPreferences pref;
	SharedPreferences.Editor editor;
	SetURL ur;
	AsyncHttpClient client;
	AlertDialog.Builder ab;
	String shopId,memberKey,address,descript;
	StringBuffer orderMenu = new StringBuffer();
	int payType,orderPrice;
	SetUtil util;
	CartAdapter cartAdapter;
	CartItem cItem;
	Activity activity;
	Document doc;
	SAXBuilder builder;
	SetAddrListItem item;
	SetAddrAdapter adapter;
	ArrayList<SetAddrListItem> itemList = new ArrayList<SetAddrListItem>();
	ArrayList<CartItem> cItemList = new ArrayList<CartItem>();
	Dialog dg;
	String oAddrPost, oAddr2;
	LinearLayout layBasic, layPost;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 ab = new AlertDialog.Builder(this);
		setContentView(R.layout.activity_send_order);
		 getActionBar().setDisplayHomeAsUpEnabled(true);
		   getActionBar().setTitle(R.string.title_send_order);
		intent = getIntent();
		pref = getSharedPreferences(ur.PREF, 0);
		editor = pref.edit();
		layBasic = (LinearLayout)findViewById(R.id.send_lay_basic);
		layPost = (LinearLayout)findViewById(R.id.send_lay_post);
		edAddr1 = (EditText) findViewById(R.id.send_edit_post_add);
		edAddr2 = (EditText) findViewById(R.id.send_edit_detail_add);
		edRequest = (EditText) findViewById(R.id.send_edit_request);
		edMobile = (EditText) findViewById(R.id.send_edit_mobile);
		listView = (ListView) findViewById(R.id.send_listView);
		btnPost =(Button)findViewById(R.id.send_btn_add1);
		btnPost.setOnClickListener(this);
		
		postList = (ListView) findViewById(R.id.send_post_listview);
		tvPriceSum = (TextView) findViewById(R.id.send_tv_sum);
		tvPriceSum.setText(intent.getIntExtra("orderPrice",0)+"원");
		cbAddr = (CheckBox) findViewById(R.id.send_cb_addrg);
		
		cbAddr.setOnCheckedChangeListener(this);
		cbAddr.setChecked(true);
		setOnText(cbAddr.isChecked());
		cbSms = (CheckBox) findViewById(R.id.send_cb_sms);
		cbSms.setOnCheckedChangeListener(this);
		cbAgree1 = (CheckBox) findViewById(R.id.send_cb_agree1);
		cbAgree1.setOnCheckedChangeListener(this);
		cbAgree2 = (CheckBox) findViewById(R.id.send_cb_agree2);
		cbAgree2.setOnCheckedChangeListener(this);
		
		rg = (RadioGroup)findViewById(R.id.send_rg);
		
		rg.setOnCheckedChangeListener(this);
		
		btnFinish = (Button) findViewById(R.id.send_btn_finish);
		btnFinish.setOnClickListener(this);
	
		setItem();
		
	}
	
	private void setItem() {
		int j;
for (j = 0; j < ur.CARTSET.length; j++) {
			
			String saveName = ur.CARTSET[j];
			String tempItem = pref.getString(saveName, null);
			
		if(tempItem!=null){
			
				String tem[] = tempItem.split(":");

				cItem = new CartItem(tem[0], tem[1], tem[2],
						Integer.parseInt(tem[3]), Integer.parseInt(tem[4]));
				cItemList.add(cItem);
				if(j==0){
					orderMenu.append(tem[2]+":"+tem[3]+":"+tem[4]);
				}else{
					orderMenu.append(","+tem[2]+":"+tem[3]+":"+tem[4]);
				}
			}
		
		}
cartAdapter = new CartAdapter(this, R.layout.cart_list_item,
		cItemList);
listView.setAdapter(cartAdapter);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.send_btn_finish:
			if(edAddr1.getText().toString().equalsIgnoreCase("")&&edAddr2.getText().toString().equalsIgnoreCase("") 
					&& edMobile.getText().toString().equalsIgnoreCase("") ){
				ab.setMessage("배송지 및 연락처를 꼭 기입하셔야 합니다.");
				ab.setPositiveButton("확인", null);
				ab.show();
			}else{
			sendOrder();
			}
			break;
		case R.id.send_btn_add1:
			if(cbAddr.isChecked()){
				
				ab.setMessage("등록된 배송지 체크를 해제 하셔야 검색 가능 합니다.");
				ab.setPositiveButton("확인", null);
				ab.show();
			}else{
				if (edAddr1.getText().toString().equalsIgnoreCase("")) {
					Toast.makeText(v.getContext(), "검색할 주소를 입력하세요",
							Toast.LENGTH_SHORT).show();
				} else {
					

					requestPost();

				}
				
			}
			
			break;

		}

	}

	private void sendOrder() {
		client = new AsyncHttpClient();
		
		shopId = intent.getStringExtra("shopId");
		memberKey=pref.getString("uniqueKey", null);
		address=edAddr1.getText()+" "+edAddr2.getText();
		
		orderPrice = intent.getIntExtra("orderPrice",0);
		descript = edRequest.getText().toString();
//		orderMenu = intent.getStringExtra("menuName")
//			+":"+intent.getIntExtra("count",0)
//		    +":"+intent.getIntExtra("price",0);
		Log.v(ur.TAG,"orderMenu :"+orderMenu);
		RequestParams param = new RequestParams();
		param.put("shopID", shopId);
		param.put("memberKey",memberKey);
		param.put("payType",payType+"");
		param.put("address",address);
		param.put("orderPrice",orderPrice+"");
		param.put("descript",descript);
		param.put("orderMenu",orderMenu.toString());
		Log.v(ur.TAG,"orderMenu :"+orderMenu.toString());
		client.post(ur.ORDER, param, new JsonHttpResponseHandler(){

			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
				Log.v(ur.TAG,"order errorResponse :"+errorResponse);
				super.onFailure(e, errorResponse);
			}

			@Override
			public void onSuccess(JSONObject response) {
				try {
					String result = response.getString("ResultCode");
					if(result.equals("ok")){
						ab.setMessage("주문이 완료 되었습니다.");
						ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								for(int i=0;i<ur.CARTSET.length;i++){
									editor.putString(ur.CARTSET[i], null);
									}
								if(oAddrPost!=null){
								editor.putString("addressPost", oAddrPost);
								editor.putString("address1", oAddr2);
								}
									editor.commit();
									setResult(0);
									finish();
								
							}
						});
						ab.show();
						
					}else{
						
						ab.setMessage("다시 시도해 주세요.");
						ab.setPositiveButton("확인", null);
						ab.show();
					}
					Log.v(ur.TAG,"order response :"+response);
					
				
				
				
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				super.onSuccess(response);
			}

			
			
		});
		
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

public void setOnText(boolean addr){
	if(addr){
		edAddr1.setText(pref.getString("address1", pref.getString("useraddress1",null)));
		edAddr2.setText(pref.getString("address2", pref.getString("useraddress2",null)));
		edMobile.setText(pref.getString("mobile", null));
//		btnPost.setClickable(false);
		
//		cbSms.setChecked(pref.getBoolean("sms", false));
//		cbAgree1.setChecked(pref.getBoolean("agree1", false));
//		cbAgree2.setChecked(pref.getBoolean("agree2", false));
	}else{
		edAddr1.setText("");
		edAddr2.setText("");
		edMobile.setText("");
		
		cbSms.setChecked(false);
		cbAgree1.setChecked(false);
		cbAgree2.setChecked(false);
//		btnPost.setClickable(true);
	}
	
	
	
}
	
	@Override
	public void onCheckedChanged(CompoundButton btnView, boolean isChecked) {
		switch (btnView.getId()) {
		case R.id.send_cb_addrg:
			setOnText(isChecked); 
			
			break;
		case R.id.send_cb_agree1:
			if (isChecked) {

			} else {

			}
			break;

		case R.id.send_cb_agree2:
			if (isChecked) {

			} else {

			}
			break;

		case R.id.send_cb_sms:
			if (isChecked) {

			} else {

			}
			break;

		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
	
		switch(checkedId){
		case R.id.send_rb_cash:
			payType=CBCASH;
			break;
		case R.id.send_rb_card:
			payType=CBCARD;
			break;
		
		case R.id.send_rb_local_cash:
			payType=CBLOCASH;
			break;
		
		}
		
	}
	public void removePref(int pos) {
		editor.putString(ur.CARTSET[pos], null);
		editor.commit();
		Log.v(ur.TAG, "removePref :" + pref.getString(ur.CARTSET[pos], null));

	}
	private void requestPost() {
		client = new AsyncHttpClient();
		try {
			String url = ur.POSTIP + "?regkey=" + ur.REGKEY + "&target="
					+ ur.TARGET + "&query="
					+ URLEncoder.encode(edAddr1.getText().toString(), "EUC-KR");

			client.addHeader("accept-language", "ko");
			client.get(url, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int stat, Header[] header, byte[] binary) {
					try {

						// if(binary.length>204){
						InputStream is = null;
						// Log.v(ur.TAG,"binary size :"+binary.length);
						is = new ByteArrayInputStream(binary);

						// XML 파싱
						builder = new SAXBuilder();
						doc = builder.build(is);
						// Log.v(ur.TAG,"doc :"+doc);
						// itemlist하위에 우편번호와 주소값을 가짐

						Element itemlist;

						if (doc.getRootElement().getName().equals("post")) {
							itemlist = doc.getRootElement()
									.getChild("itemlist");
							List list = itemlist.getChildren();

							itemList.clear();
							// 검색결과가 여러개인 경우 반복하며 우편번호와 주소값을 뽑아낸다
							for (int i = 0; i < list.size(); i++) {
								Element eitem = (Element) list.get(i);
								String address = eitem.getChildText("address");
								String postcd = eitem.getChildText("postcd");
								
								item = new SetAddrListItem(address, postcd);
								itemList.add(item);

							}

						} else if (doc.getRootElement().getName()
								.equals("error")) {
							itemlist = doc.getRootElement();

							String msg = itemlist.getChildText("message");
							Toast.makeText(getApplicationContext(), msg,
									Toast.LENGTH_SHORT).show();

						}

						// }else{
						// Toast.makeText(v.getContext(), "검색하실 주소를 다시 입력하세요.",
						// Toast.LENGTH_SHORT).show();
						// }
						adapter = new SetAddrAdapter(SendOrderActivity.this, getBaseContext(), R.layout.setaddr_list_item, itemList);
						
						postList.setAdapter(adapter);
						layBasic.setVisibility(View.INVISIBLE);
						layPost.setVisibility(View.VISIBLE);

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
					dg = util.setProgress(SendOrderActivity.this);
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
		
		oAddrPost = addr1;
		oAddr2 = addr2;
		
		
		
		edAddr1.setText(addr2);
		layBasic.setVisibility(View.VISIBLE);
		layPost.setVisibility(View.INVISIBLE);
		itemList.clear();
		adapter.notifyDataSetChanged();
		
		
	}
}
