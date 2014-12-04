package kr.actus.ckck;


import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import kr.actus.ckck.util.SetURL;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SendOrderActivity extends Activity implements OnClickListener,
		RadioGroup.OnCheckedChangeListener, OnCheckedChangeListener {
	final int CBCASH=0;
	final int CBCARD=1;
	final int CBLOCASH=2;
	
	Button btnFinish;
	CheckBox cbAddr, cbSms, cbAgree1, cbAgree2;
	EditText edAddr1, edAddr2, edRequest, edMobile;
	TextView tvPriceSum;
	ListView listView;
	RadioGroup rg;
	RadioButton rb;
	Intent intent;
	SharedPreferences pref;
	SharedPreferences.Editor editor;
	SetURL ur;
	AsyncHttpClient client;
	AlertDialog.Builder ab;
	String shopId,memberKey,address,descript,orderMenu;
	int payType,orderPrice;
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

		edAddr1 = (EditText) findViewById(R.id.send_edit_post_add);
		edAddr2 = (EditText) findViewById(R.id.send_edit_detail_add);
		edRequest = (EditText) findViewById(R.id.send_edit_request);
		edMobile = (EditText) findViewById(R.id.send_edit_mobile);
		listView = (ListView) findViewById(R.id.send_listView);
		
		tvPriceSum = (TextView) findViewById(R.id.send_tv_sum);
		tvPriceSum.setText(intent.getIntExtra("priceSum",0)+"원");
		cbAddr = (CheckBox) findViewById(R.id.send_cb_addrg);
		cbAddr.setOnCheckedChangeListener(this);
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
		Log.v(ur.TAG,"payType :"+payType);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.send_btn_finish:
			
			sendOrder();
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
		orderMenu = intent.getStringExtra("menuName")
			+":"+intent.getIntExtra("count",0)
		    +":"+intent.getIntExtra("price",0);
		RequestParams param = new RequestParams();
		param.put("shopID", shopId);
		param.put("memberKey",memberKey);
		param.put("payType",payType);
		param.put("address",address);
		param.put("orderPrice",orderPrice+"");
		param.put("descript",descript);
		param.put("orderMenu",orderMenu);
		
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
						ab.setPositiveButton("확인", null);
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

	@Override
	public void onCheckedChanged(CompoundButton btnView, boolean isChecked) {
		switch (btnView.getId()) {
		case R.id.send_cb_addrg:
			if (isChecked) {
				edAddr1.setText(pref.getString("address1", null));
				edAddr2.setText(pref.getString("address2", null));
				edMobile.setText(pref.getString("mobile", null));
				
				cbSms.setChecked(pref.getBoolean("sms", false));
				cbAgree1.setChecked(pref.getBoolean("agree1", false));
				cbAgree2.setChecked(pref.getBoolean("agree2", false));
			} else {
				edAddr1.setText("");
				edAddr2.setText("");
				edMobile.setText("");
				boolean temp = false;
				cbSms.setChecked(temp);
				cbAgree1.setChecked(temp);
				cbAgree2.setChecked(temp);
			}
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

}
