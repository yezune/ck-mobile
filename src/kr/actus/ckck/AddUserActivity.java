package kr.actus.ckck;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.RequestParams;

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
import android.widget.Spinner;
import android.widget.Toast;

public class AddUserActivity extends Activity implements OnClickListener{
	private final String TAG = "MainActivity";
	String uniqueKey;
	EditText regKey,cName, name, address1, address2, mobile;
	CheckBox sms,email,agree1,agree2;
	Button inReg;
	SetURL url;
	SetUtil util;
	Spinner mobileSpin;
	String fMobileNum;
	ArrayAdapter<CharSequence> spinAdapter;
	SharedPreferences pref;
	SharedPreferences.Editor editor;
	
	AsyncData sr; 
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_adduser);
	    getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.title_add_user);
	    pref = getSharedPreferences(url.PREF, 0);
		editor = pref.edit();
		
	    init();
	    setSpinner();
	    
//	    uniqueKey = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);
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
		address1= (EditText) findViewById(R.id.user_add);
		address2= (EditText) findViewById(R.id.user_dadd);
		mobile= (EditText) findViewById(R.id.user_mobile);
		
		sms = (CheckBox) findViewById(R.id.user_check_sms);
		email = (CheckBox) findViewById(R.id.user_check_email);
		agree1 = (CheckBox) findViewById(R.id.user_check_agree1);
		agree2 = (CheckBox) findViewById(R.id.user_check_agree2);
		mobileSpin = (Spinner) findViewById(R.id.user_mobile_spinner);
		inReg = (Button)findViewById(R.id.user_btn_reg);
		
		inReg.setOnClickListener(this);
		
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
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.user_btn_reg:
			if(!sms.isChecked() && !email.isChecked() && !agree1.isChecked() && !agree2.isChecked()){
				
			Toast.makeText(this, "서비스 제공을 위하여 동의 해주세요.", Toast.LENGTH_SHORT).show();	
			}else{
//				Dialog pro = util.setProgress(this);
				
				RequestParams param = new RequestParams();
				param.put("uniqueKey",uniqueKey);
				param.put("memName", name.getText().toString());
				param.put("regKey", regKey.getText().toString());
				param.put("mobile", fMobileNum+mobile.getText().toString());
				param.put("address1",address1.getText().toString());
				param.put("address2", address2.getText().toString());
				
				sr = new AsyncData(this,SetURL.JOIN, param);
				JSONArray result = sr.postJSONArray();
				Log.v(TAG,"result :"+result);
				try {
				if(result!=null){
					
						if(result.getJSONArray(0).equals("ok")){
//							pro.dismiss();
							Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
							editor.putString("uniqueKey", uniqueKey);
							editor.putString("memName", name.getText().toString());
							editor.putString("regKey", regKey.getText().toString());
							editor.putString("mobile", mobile.getText().toString());
							editor.putString("address1", address1.getText().toString());
							editor.putString("address2", address2.getText().toString());
							editor.commit();
							
						}else if(result.getJSONArray(0).equals("fail")){
//							pro.dismiss();
							Toast.makeText(this, "오류 : "+result.getJSONArray(1), Toast.LENGTH_SHORT).show();
							
						}
						
						
					
					
				}else
				{
//					pro.dismiss();
					Toast.makeText(this, "다시 시도 하세요.", Toast.LENGTH_SHORT).show();
					
				}
				
				
				}catch (JSONException e) {
//					pro.dismiss();
					Log.v(TAG,"jsonException :"+e);
					e.printStackTrace();
				}
			}
			
			
			break;
		}
		
	}

}
