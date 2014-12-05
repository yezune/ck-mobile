package kr.actus.ckck;


import kr.actus.ckck.util.SetURL;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class SelectOrderActivity extends Activity implements OnClickListener {
	public static class ActivityReference{
		public static Activity activityReference;
	}

	private static final String TAG = "MainActivity";
	Button btnCart;
	
	TextView menuName,edPrice, eventFunc,descript, edPriceSum, tvPriceMin;
	SetURL ur;
	EditText count;
	ImageView img,btnMinus, btnPlus;
	int flagCount;
	int price,priceMin;
	Intent intent;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_select_order);
	
	intent = getIntent();
	
	menuName = (TextView)findViewById(R.id.order_tv_title);
	img = (ImageView)findViewById(R.id.order_img_main);
	edPrice=(TextView) findViewById(R.id.order_tv_price);
	eventFunc=(TextView) findViewById(R.id.order_tv_service);
	edPriceSum = (TextView) findViewById(R.id.order_tv_price_sum);
	descript = (TextView) findViewById(R.id.order_tv_descript);
	tvPriceMin = (TextView) findViewById(R.id.order_tv_price_min);
	menuName.setText(getIntent().getStringExtra("menuName"));
	descript.setText(getIntent().getStringExtra("descript"));
	price = getIntent().getIntExtra("price", 0);
	edPrice.setText(price+"");
	Bitmap bm = BitmapFactory.decodeFile(getIntent().getStringExtra("menuImg"));
	img.setImageBitmap(bm);
	eventFunc.setText(getIntent().getStringExtra("eventFunc"));
	
	priceMin = getIntent().getIntExtra("minPrice", 0);
	tvPriceMin.setText("최소 주문 금액 : "+priceMin+"원 이상");
	
	
	count=(EditText) findViewById(R.id.order_edit_count);
	btnMinus = (ImageView) findViewById(R.id.order_btn_minus);
	btnMinus.setOnClickListener(this);
	btnPlus = (ImageView)	findViewById(R.id.order_btn_plus);
	btnPlus.setOnClickListener(this);
	
	
	
	
	
	flagCount = Integer.parseInt(count.getText().toString());
	
	
	findViewById(R.id.order_btn_cart).setOnClickListener(this);
	findViewById(R.id.order_btn_cancel).setOnClickListener(this);
	findViewById(R.id.order_btn_minus).setOnClickListener(this);
	findViewById(R.id.order_btn_plus).setOnClickListener(this);
	  getActionBar().setDisplayHomeAsUpEnabled(true);
	getActionBar().setTitle(R.string.title_selectorder);
	total(flagCount);
	count.setOnEditorActionListener(new OnEditorActionListener() {
		
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if(actionId==EditorInfo.IME_ACTION_DONE){
				flagCount = Integer.parseInt(count.getText().toString());
				total(flagCount);
			}
			return false;
		}
			
	});
	    // TODO Auto-generated method stub
	}
	
	
	int sum;
private void total(int cnt) {
		 sum =  price*cnt;
		
		edPriceSum.setText(sum+"원");
		count.setText(flagCount+"");
	}



	@Override
	public void onClick(View v) {
		flagCount = Integer.parseInt(count.getText().toString());
		switch(v.getId()){
			case R.id.order_btn_cart:
				
				if(priceMin>sum){
					AlertDialog.Builder ab = new AlertDialog.Builder(this);
					ab.setMessage("최소주문금액 이상 주문 가능합니다.");
					ab.setPositiveButton("확인", null);
					ab.show();
					
				}else{
				Intent intent = new Intent(this,CartActivity.class);
				getIntent().putExtra("count", flagCount);
				
				intent.putExtras(getIntent());
				
//				intent.putExtra("edPrice", edPrice.getText().toString());
//				intent.putExtra("service", service.getText().toString());
//				intent.putExtra("count", count.getText().toString());
				ActivityReference.activityReference = SelectOrderActivity.this;
				startActivity(intent);
				}
				break;
			case R.id.order_btn_cancel:
				finish();
				break;
			case R.id.order_btn_minus:
				
				
				if(flagCount>1){
					flagCount -= 1;
				}
				total(flagCount);
				
				break;
			case R.id.order_btn_plus:
				flagCount += 1;
				total(flagCount);
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


	

	