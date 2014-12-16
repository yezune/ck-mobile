package kr.actus.ckck;

import java.util.ArrayList;

import kr.actus.ckck.SelectOrderActivity.ActivityReference;
import kr.actus.ckck.cartlist.CartAdapter;
import kr.actus.ckck.cartlist.CartItem;
import kr.actus.ckck.util.SetURL;
import kr.actus.ckck.util.SetUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CartActivity extends Activity implements OnClickListener {

	/** Called when the activity is first created. */
	Button btnOrder, btnInsert;
	ListView cartList;
	CartItem cItem;
	ArrayList<CartItem> cItemList = new ArrayList<CartItem>();
	TextView cartTitle, cartPrice,minOrder;
	EditText cartCount;
	CartAdapter cartAdapter;
	SetURL ur;
	Intent intent;
	int price, count, minPrice;
	
	SharedPreferences pref;
	SharedPreferences.Editor editor;
	String shopId, shopName, menuName;
	SetUtil util;
	Activity activity;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.title_cart);

		intent = getIntent();
		 
		pref = getSharedPreferences(ur.PREF, 0);
		editor = pref.edit();

		btnOrder = (Button) findViewById(R.id.cart_btn_order);
		btnOrder.setOnClickListener(this);
		btnInsert = (Button) findViewById(R.id.cart_btn_insert);
		btnInsert.setOnClickListener(this);
		minOrder = (TextView)findViewById(R.id.cart_tv_order_min);
		cartTitle = (TextView) findViewById(R.id.cart_tv_store_name);
		cartList = (ListView) findViewById(R.id.cart_listview);

		cartPrice = (TextView) findViewById(R.id.cart_tv_order_sum);
		
		minPrice = getIntent().getIntExtra("minPrice", 0);
		minOrder.setText("최소 주문 금액 : "+minPrice+"원");
//		Log.v(ur.TAG,"minPrice :"+minPrice);
		if (intent.getIntExtra("onAir", 0) == 1) {
			btnInsert.setVisibility(View.INVISIBLE);
			
			
		}
			activity = ActivityReference.activityReference;
			price = intent.getIntExtra("price", 0);
			count = intent.getIntExtra("count", 0);
			shopId = intent.getStringExtra("shopId");
			shopName = intent.getStringExtra("shopName");
			menuName = intent.getStringExtra("menuName");
			Log.v(ur.TAG,"menuName cart :"+menuName);
			checkItem();

			
			
		
		cartTitle.setText(shopName);
		setItem();

	}

	String saveName = null;
	String tempItem;
	String temp = null;
	String flagId = null;
	int len = ur.CARTSET.length;
	
	// sharedpreference안에 값이 없으면 저장 공간으로 할당하여 cItemList저장
	private void checkItem() {
		int i;
		String temp = new String();

		String tm = null;

		
		String arr[] = new String[len];
		for (int j = 0; j < len; j++) {
			arr[j] = pref.getString(ur.CARTSET[j], null);
		}

		
		

		for (i = 0; i < len; i++) {
			
			if (arr[i] == null&& i==0) {
				
				temp = shopId + ":" + shopName + ":" + menuName + ":" + count + ":"
						+ price;
				editor.putString(ur.CARTSET[0], temp);
				editor.commit();
				break;
			}else if(arr[i] == null){
				String tem[] = arr[0].split(":");
				flagId = tem[0];
				if(flagId.equals(shopId)){
				temp = shopId + ":" + shopName + ":" + menuName + ":" + count
						+ ":" + price;
				editor.putString(ur.CARTSET[i], temp);
				editor.commit();
				break;
			} else {
				Toast.makeText(this, "다른 상호의 장바구니 목록을 확인하세요.",
						Toast.LENGTH_SHORT).show();
				break;
			}

		}
		}
	}
	int tPriceSum;
	private void setItem() {
	int j;
	tPriceSum = 0;
	
	
		for (j = 0; j < ur.CARTSET.length; j++) {
			saveName = ur.CARTSET[j];
			tempItem = pref.getString(saveName, null);
			if (tempItem != null) {

				String tem[] = tempItem.split(":");
				int tem3 = Integer.parseInt(tem[3]);
				int tem4 = Integer.parseInt(tem[4]);
				cItem = new CartItem(tem[0], tem[1], tem[2],tem3, tem4);
				tPriceSum += tem3*tem4;
				
				cItemList.add(cItem);
			}
			
		}
		cartAdapter = new CartAdapter(this, R.layout.cart_list_item,
				cItemList);
		cartList.setAdapter(cartAdapter);
		cartPrice(tPriceSum);
	}

	private void cartPrice(int sum) {
		cartPrice.setText("합계 :"+sum+"원");
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cart_btn_order:
			if(tPriceSum>=minPrice){
			Intent intent1 = new Intent(this, SendOrderActivity.class);
			
			// intent1.putExtras(intent);

			intent1.putExtra("shopId", intent.getStringExtra("shopId"));

			intent1.putExtra("orderPrice", tPriceSum);
			intent1.putExtra("cartList", cItemList);
			
			startActivityForResult(intent1,0);
			}else{
				AlertDialog.Builder ab = new AlertDialog.Builder(this);
				ab.setMessage("최소주문금액 이상 주문 가능합니다.");
				ab.setPositiveButton("확인", null);
				ab.show();
			}
			break;
		case R.id.cart_btn_insert:
			
			finishAct();
			
			break;
		}

	}

	public void removePref(int pos,int removePrice) {
		editor.putString(ur.CARTSET[pos], null);
		editor.commit();
		tPriceSum = tPriceSum-removePrice;
		cartPrice(tPriceSum);
		Log.v(ur.TAG, "removePref :" + pref.getString(ur.CARTSET[pos], null));
		if(pos==0){
			AlertDialog.Builder adb = new AlertDialog.Builder(this);
			adb.setMessage("장바구니 목록이 없어 장바구니를 종료합니다.");
			adb.setPositiveButton("확인", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					finishAct();
					
					
				}
			});
			adb.show();
		}

	}
	public void finishAct(){
		if(activity!=null){
			activity.finish();	
		}
		
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode ==0){
			finishAct();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
