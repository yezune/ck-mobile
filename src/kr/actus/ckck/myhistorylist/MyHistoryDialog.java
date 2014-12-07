package kr.actus.ckck.myhistorylist;

import kr.actus.ckck.R;
import kr.actus.ckck.util.SetURL;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MyHistoryDialog extends Activity {

	TextView shopName,orderMenu,orderTime,price,payType,address,descript,status,deliverName;
	SetURL ur;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.myhistory_dialog);
	
	
	

		Display display=((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		
		int width = (int)(display.getWidth()*0.9);
		
		int height = (int)(display.getWidth()*0.9);
		
		getWindow().getAttributes().width = width;
		getWindow().getAttributes().height = height;
		
		init();
	    // TODO Auto-generated method stub
	}

	private void init() {
				shopName=(TextView) findViewById(R.id.myhistory_dialog_tv_shopname);
				orderMenu=(TextView) findViewById(R.id.myhistory_dialog_tv_ordermenu);
				orderTime=(TextView) findViewById(R.id.myhistory_dialog_tv_ordertime);
				price=(TextView) findViewById(R.id.myhistory_dialog_tv_price);
				payType=(TextView) findViewById(R.id.myhistory_dialog_tv_paytype);
				address=(TextView) findViewById(R.id.myhistory_dialog_tv_address);
				descript=(TextView) findViewById(R.id.myhistory_dialog_tv_descript);
				status=(TextView) findViewById(R.id.myhistory_dialog_tv_status);
				deliverName=(TextView) findViewById(R.id.myhistory_dialog_tv_delivername);
				
		shopName.setText(getIntent().getStringExtra("shopName"));
		orderMenu.setText(getIntent().getStringExtra("orderMenu"));
		orderTime.setText(getIntent().getStringExtra("orderTime"));
		price.setText(getIntent().getIntExtra("price",0)+"원");
		String pay = null;
		int payT = getIntent().getIntExtra("payType", 4);
			if(payT==0) pay="현금";
			if(payT==1) pay="카드";
			if(payT==2) pay="지역화폐";
			if(payT==4) pay="정보가 확인되지 않습니다.";
		
		payType.setText(pay);
		address.setText(getIntent().getStringExtra("address"));
		String descriptT = getIntent().getStringExtra("descript");
		if(descriptT.equals("undefined")){
			descriptT = "없음";
		}
		descript.setText(descriptT);
		
		String stat = null;
		int tempstat = getIntent().getIntExtra("status", 4);
			if(tempstat==0) stat="배달 준비중";
			if(tempstat==1) stat="배달중";
			if(tempstat==2) stat="배달완료";
			if(tempstat==4) stat="정보가 확인되지 않습니다.";
		
		status.setText(stat);
		String name = getIntent().getStringExtra("deliverName");
		Log.v(ur.TAG,"name :"+name);
		if(name!=null||name.length()==0)name = "아직 지정되지 않았습니다.";
		
		deliverName.setText(name);
		
	}

}
