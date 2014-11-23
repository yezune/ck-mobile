package kr.actus.ckck;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class CartActivity extends Activity implements OnClickListener{

	/** Called when the activity is first created. */
	Button order;
	ListView cartList;
	TextView cartTitle, cartPrice;
	EditText cartCount;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	   setContentView(R.layout.activity_cart) ;
	   getActionBar().setDisplayHomeAsUpEnabled(true);
	   getActionBar().setTitle(R.string.action_cart);
	   
	   
	   order = (Button) findViewById(R.id.cart_btn_order);
			   order.setOnClickListener(this);
			   cartList.setAdapter(adapter)
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
