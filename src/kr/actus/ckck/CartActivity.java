package kr.actus.ckck;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CartActivity extends Activity implements OnClickListener{

	/** Called when the activity is first created. */
	Button order;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	   setContentView(R.layout.activity_cart) ;
	   getActionBar().setDisplayHomeAsUpEnabled(true);
	   
	    // TODO Auto-generated method stub
	   order = (Button) findViewById(R.id.cart_btn_order);
			   order.setOnClickListener(this);
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
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.cart_btn_order:
			Intent intent = new Intent(this,SendOrderActivity.class);
			
			startActivity(intent);
			break;
		}
		
	}

	
}
