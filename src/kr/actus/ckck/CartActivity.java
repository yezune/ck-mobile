package kr.actus.ckck;


import java.util.ArrayList;

import kr.actus.ckck.cartlist.CartAdapter;
import kr.actus.ckck.cartlist.CartItem;
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
	CartItem cItem;
	ArrayList<CartItem> cItemList = new ArrayList<CartItem>();
	TextView cartTitle, cartPrice;
	EditText cartCount;
	CartAdapter cartAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	   setContentView(R.layout.activity_cart) ;
	   getActionBar().setDisplayHomeAsUpEnabled(true);
	   getActionBar().setTitle(R.string.title_cart);
	   
	   
	   order = (Button) findViewById(R.id.cart_btn_order);
			   order.setOnClickListener(this);
			  
			   
			   cartList = (ListView)findViewById(R.id.cart_listview);
			   cItem = new CartItem("유황오리 진흙구이",1,"50,000원");
			   cItemList.add(cItem);
			   cartAdapter = new CartAdapter(this,R.layout.cart_list_item,cItemList);
			   cartList.setAdapter(cartAdapter);
			   
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
