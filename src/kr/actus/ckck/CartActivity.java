package kr.actus.ckck;


import java.util.ArrayList;

import kr.actus.ckck.cartlist.CartAdapter;
import kr.actus.ckck.cartlist.CartItem;
import kr.actus.ckck.util.SetURL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
	SetURL ur;
	Intent intent;
	int price, count;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	   setContentView(R.layout.activity_cart) ;
	   getActionBar().setDisplayHomeAsUpEnabled(true);
	   getActionBar().setTitle(R.string.title_cart);
	   
	    intent = getIntent();
//	   Log.v(ur.TAG,"cart intent :"+intent.getIntExtra("count",0));
	   order = (Button) findViewById(R.id.cart_btn_order);
			   order.setOnClickListener(this);
			  
			   cartTitle = (TextView)findViewById(R.id.cart_tv_store_name);
			   cartList = (ListView)findViewById(R.id.cart_listview);
			   cartTitle.setText(intent.getStringExtra("shopName"));
			   
			   
			   String menuName = intent.getStringExtra("menuName");
			   price = intent.getIntExtra("price",0);
			   count = intent.getIntExtra("count",0);
			   Log.v(ur.TAG,"cart count :"+count);
			   cItem = new CartItem(menuName,count,price);
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
