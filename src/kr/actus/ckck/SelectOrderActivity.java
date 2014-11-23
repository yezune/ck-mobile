package kr.actus.ckck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SelectOrderActivity extends Activity implements OnClickListener {

	
	Button btnCart;
	TextView price, service;
	EditText count;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_select_order);
	
	
	price=(TextView) findViewById(R.id.order_tv_price);
	service=(TextView) findViewById(R.id.order_tv_service);
	count=(EditText) findViewById(R.id.order_edit_count);
	
	findViewById(R.id.order_btn_cart).setOnClickListener(this);
	findViewById(R.id.order_btn_cancel).setOnClickListener(this);
	  getActionBar().setDisplayHomeAsUpEnabled(true);
	getActionBar().setTitle(R.string.action_selectorder);
	
	    // TODO Auto-generated method stub
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.order_btn_cart:
				Intent intent = new Intent(this,CartActivity.class);
				intent.putExtra("price", price.getText().toString());
				intent.putExtra("service", service.getText().toString());
				intent.putExtra("count", count.getText().toString());
				startActivity(intent);
				break;
			case R.id.order_btn_cancel:
				finish();
				break;
				
		}
		
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

}
