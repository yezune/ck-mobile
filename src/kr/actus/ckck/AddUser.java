package kr.actus.ckck;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddUser extends Activity implements OnClickListener{
	private final String TAG = "MainActivity";
	String androidId;
	EditText cNum,cName, name, add, dAdd, mobile;
	CheckBox sms,email,agree1,agree2;
	Button inReg;
	SetUtil util;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_adduser);
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	    init();
	    androidId = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);
		
	    
	    
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
				param.put("uniqueKey",androidId);
				param.put("memeName", name);
				param.put("regKey", cNum);
				param.put("mobile", mobile);
				param.put("address1",add);
				param.put("address2", dAdd);
				
				
				JSONObject result = ServerResponse.postRequest(SetURL.basic+SetURL.join, param);
				Log.v(TAG,"result :"+SetURL.basic+SetURL.join);
				if(result!=null){
					pro.dismiss();
				}else
				{
					pro.dismiss();
					Toast.makeText(this, "다시 시도 하세요.", Toast.LENGTH_SHORT).show();
					
				}
			}
			
			
			break;
		}
		
	}

}
