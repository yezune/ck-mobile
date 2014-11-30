package kr.actus.ckck;


import java.util.ArrayList;

import kr.actus.ckck.cartlist.CartAdapter;
import kr.actus.ckck.cartlist.CartItem;
import kr.actus.ckck.util.SetURL;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
	Button btnOrder,btnInsert;
	ListView cartList;
	CartItem cItem;
	ArrayList<CartItem> cItemList = new ArrayList<CartItem>();
	TextView cartTitle, cartPrice;
	EditText cartCount;
	CartAdapter cartAdapter;
	SetURL ur;
	Intent intent;
	int price, count;
	int sum=0;;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	   setContentView(R.layout.activity_cart) ;
	   getActionBar().setDisplayHomeAsUpEnabled(true);
	   getActionBar().setTitle(R.string.title_cart);
	   
	    intent = getIntent();
//	   Log.v(ur.TAG,"cart intent :"+intent.getIntExtra("count",0));
	    btnOrder = (Button) findViewById(R.id.cart_btn_order);
	    btnOrder.setOnClickListener(this);
	    btnInsert = (Button) findViewById(R.id.cart_btn_insert);
	    btnInsert.setOnClickListener(this);
	    
			   cartTitle = (TextView)findViewById(R.id.cart_tv_store_name);
			   cartList = (ListView)findViewById(R.id.cart_listview);
			   cartTitle.setText(intent.getStringExtra("shopName"));
			   cartPrice = (TextView)findViewById(R.id.cart_tv_order_sum);
			   
			   
			   String menuName = intent.getStringExtra("menuName");
			   price = intent.getIntExtra("price",0);
			   count = intent.getIntExtra("count",0);
			   if(count!=0){
			   Log.v(ur.TAG,"cart count :"+count);
			   cItem = new CartItem(menuName,count,price);
			   cItemList.add(cItem);
			   cartAdapter = new CartAdapter(this,R.layout.cart_list_item,cItemList);
			   cartList.setAdapter(cartAdapter);
			   }else{
				 
				   Log.v(ur.TAG,"장바구니가비어있음");
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

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.cart_btn_order:
			
		
			Intent intent1 = new Intent(this,SendOrderActivity.class);
			
			intent1.putExtras(intent);
			intent1.putExtra("priceSum",sum);
			intent1.putExtra("cartList",cItemList);
			startActivity(intent1);
		
			break;
		case R.id.cart_btn_insert:
			
			
			
			break;
		}
		
	}

	public void itemStat(int price,int count) {
		sum = price*count;
		
		 cartPrice.setText("합계 : "+sum+"원");
		 if(sum<intent.getIntExtra("minPrice", 0)){
				
				
			
		 AlertDialog.Builder ab = new AlertDialog.Builder(this);
			ab.setMessage("장바구니가 비어 있어 주문화면으로 이동 합니다.");
			ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					finish();
					
				}
			});
			ab.show();
		
	}
	}
	
}
