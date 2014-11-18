package kr.actus.ckck;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.RequestParams;

import kr.actus.ckck.util.ServerResponse;
import kr.actus.ckck.util.SetURL;
import kr.actus.ckck.util.SetUtil;
import android.app.Activity;
import android.app.Dialog;
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

public class AddUser extends Activity implements OnClickListener{
	private final String TAG = "MainActivity";
	String androidId;
	EditText cNum,cName, name, add, dAdd, mobile;
	CheckBox sms,email,agree1,agree2;
	Button inReg;
	SetUtil util;
	Spinner mobileSpin;
	String fMobileNum;
	ArrayAdapter<CharSequence> spinAdapter;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_adduser);
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	    init();
	    setSpinner();
	    Log.v(TAG,"moNum :"+fMobileNum);
	    androidId = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);
		
	    
	    
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
		cNum = (EditText) findViewById(R.id.user_cNum);
		cName= (EditText) findViewById(R.id.user_cName);
		name= (EditText) findViewById(R.id.user_name);
		add= (EditText) findViewById(R.id.user_add);
		dAdd= (EditText) findViewById(R.id.user_dadd);
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
				Dialog pro = util.setProgress(this);
				
				RequestParams param = new RequestParams();
				param.put("uniqueKey","2");
				param.put("memeName", name);
				param.put("regKey", cNum);
				param.put("mobile", mobile);
				param.put("address1",add);
				param.put("address2", dAdd);
				
				
				JSONObject result = ServerResponse.postRequest(SetURL.basic+SetURL.join, param);
				Log.v(TAG,"result :"+result);
				try {
				if(result!=null){
					
						if(result.getJSONObject("ResultCode").equals("ok")){
							pro.dismiss();
							Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
						}else if(result.getJSONObject("ResultCode").equals("fail")){
							pro.dismiss();
							Toast.makeText(this, "오류 : "+result.getString("error"), Toast.LENGTH_SHORT).show();
							
						}
						
						
					
					
				}else
				{
					pro.dismiss();
					Toast.makeText(this, "다시 시도 하세요.", Toast.LENGTH_SHORT).show();
					
				}
				
				
				}catch (JSONException e) {
					pro.dismiss();
					Log.v(TAG,"jsonException :"+e);
					e.printStackTrace();
				}
			}
			
			
			break;
		}
		
	}

}
