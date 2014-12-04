package kr.actus.ckck;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import kr.actus.ckck.cartlist.CartAdapter;
import kr.actus.ckck.cartlist.CartItem;
import kr.actus.ckck.util.SetURL;
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
	TextView cartTitle, cartPrice;
	EditText cartCount;
	CartAdapter cartAdapter;
	SetURL ur;
	Intent intent;
	int price, count;
	int sum = 0;
	SharedPreferences pref;
	SharedPreferences.Editor editor;
	String shopId, shopName, menuName;

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

		cartTitle = (TextView) findViewById(R.id.cart_tv_store_name);
		cartList = (ListView) findViewById(R.id.cart_listview);

		cartPrice = (TextView) findViewById(R.id.cart_tv_order_sum);

		cartTitle.setText(shopName);

		if (intent.getIntExtra("onAir", 0) == 0) {
			price = intent.getIntExtra("price", 0);
			count = intent.getIntExtra("count", 0);
			shopId = intent.getStringExtra("shopId");
			shopName = intent.getStringExtra("shopName");
			menuName = intent.getStringExtra("menuName");

			checkItem();

		}
		setItem();

	}

	String saveName = null;
	String tempItem;
	String temp = null;
	String flagId = null;

	// sharedpreference안에 값이 없으면 저장 공간으로 할당하여 cItemList저장
	private void checkItem() {
		int i;
		String temp = new String();

		String tm = null;

		int len = ur.CARTSET.length;
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
				Toast.makeText(this, "다른 상호의 장바구니 목록을 삭제하고 다시 선택하세요.",
						Toast.LENGTH_SHORT).show();
				break;
			}

		}
		}
	}

	// for(i=0;i<ur.CARTSET.length;i++){
	//
	// saveName = ur.CARTSET[i];//키값
	// tempItem = pref.getString(saveName, null);
	// if(tempItem==null && i==0){
	// temp = shopId+":"+shopName+":"+menuName+":"+count+":"+price;
	// editor.putString(saveName, temp);
	// editor.commit();
	// break;
	//
	// }else if(tempItem!=null){
	// String tem[] =tempItem.split(":");
	//
	// if(tem[0].equals(shopId)&&i<ur.CARTSET.length-1){
	//
	//
	// temp = shopId+":"+shopName+":"+menuName+":"+count+":"+price;
	// editor.putString(saveName, temp);
	// editor.commit();
	// for(int k=i+1;k<ur.CARTSET.length;k++){
	// tm=pref.getString(ur.CARTSET[k], null);
	// if(tm==null){
	// ur.CARTSET[k]=saveName;
	// break;
	// }
	// }
	// break;
	//
	//
	// //preSaveName = tempItem;
	// }

	/*
	 * if(tempItem!=null){ String tem[] =preSaveName.split(":");
	 * if(tem[0]==shopId){ preSaveName = tempItem;
	 * 
	 * Toast.makeText(this, "장바구니에는 같은상호의 메뉴만 저장가능합니다.",
	 * Toast.LENGTH_SHORT).show(); Log.v(ur.TAG,i+"번째 장바구니목록있음");
	 * 
	 * }else{
	 * 
	 * 
	 * Log.v(ur.TAG,i+"번째 장바구니목록없음"); temp =
	 * shopId+":"+shopName+":"+menuName+":"+count+":"+price;
	 * editor.putString(saveName, temp); editor.commit(); i=ur.CARTSET.length;
	 * }else{
	 * 
	 * 
	 * }
	 */
	// }
	//
	//
	// }
	//
	// }

	private void setItem() {
		int j;
		for (j = 0; j < ur.CARTSET.length; j++) {
			saveName = ur.CARTSET[j];
			tempItem = pref.getString(saveName, null);
			if (tempItem != null) {

				String tem[] = tempItem.split(":");

				cItem = new CartItem(tem[0], tem[1], tem[2],
						Integer.parseInt(tem[3]), Integer.parseInt(tem[4]));
				cItemList.add(cItem);
				// for(int k=0;k<tem.length;k++){
				// Log.v(ur.TAG,"temp["+k+"]"+tem[k]); }
			}
			cartAdapter = new CartAdapter(this, R.layout.cart_list_item,
					cItemList);
			cartList.setAdapter(cartAdapter);
		}
	}

	// cItem = new CartItem(pref.getStringSet(saveName, null));
	// cItemList.add(cItem);
	// cartAdapter = new CartAdapter(this,R.layout.cart_list_item,cItemList);
	// cartList.setAdapter(cartAdapter);
	// }else{
	//
	// Log.v(ur.TAG,"장바구니가비어있음");
	// }

	// }else{
	// Log.v(ur.TAG,"else");
	// //sharedpreference 에 저장되어있는 아이템 불러와서 목록출력
	//
	// }

	// }

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

			Intent intent1 = new Intent(this, SendOrderActivity.class);

			// intent1.putExtras(intent);

			intent1.putExtra("shopId", intent.getStringExtra("shopId"));

			intent1.putExtra("orderPrice", sum);
			intent1.putExtra("cartList", cItemList);
			startActivity(intent1);

			break;
		case R.id.cart_btn_insert:

			break;
		}

	}

	public void removePref(int pos) {
		editor.putString(ur.CARTSET[pos], null);
		editor.commit();
		Log.v(ur.TAG, "removePref :" + pref.getString(ur.CARTSET[pos], null));

	}
	// public void itemStat(int price,int count) {
	// sum = price*count;
	//
	// cartPrice.setText("합계 : "+sum+"원");
	// if(sum<intent.getIntExtra("minPrice", 0)){
	//
	//
	//
	// AlertDialog.Builder ab = new AlertDialog.Builder(this);
	// ab.setMessage("장바구니가 비어 있어 주문화면으로 이동 합니다.");
	// ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface arg0, int arg1) {
	// finish();
	//
	// }
	// });
	// ab.show();
	//
	// }
	// }

}
