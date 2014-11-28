package kr.actus.ckck;


import kr.actus.ckck.util.SetURL;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectOrderActivity extends Activity implements OnClickListener {

	private static final String TAG = "MainActivity";
	Button btnCart, btnMinus, btnPlus;
	TextView menuName,edPrice, eventFunc,descript, edPriceSum;
	SetURL ur;
	EditText count;
	ImageView img;
	int flagCount=1;
	int price;
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
	
	menuName.setText(getIntent().getStringExtra("menuName"));
	descript.setText(getIntent().getStringExtra("descript"));
	price = getIntent().getIntExtra("price", 0);
	edPrice.setText(price+"");
	Bitmap bm = BitmapFactory.decodeFile(getIntent().getStringExtra("menuImg"));
	img.setImageBitmap(bm);
	eventFunc.setText(getIntent().getStringExtra("eventFunc"));
	
	
	
	count=(EditText) findViewById(R.id.order_edit_count);
	btnMinus = (Button) findViewById(R.id.order_btn_minus);
	btnPlus = (Button)	findViewById(R.id.order_btn_plus);
	
	
	
	
	
	
	flagCount = Integer.parseInt(count.getText().toString());
	
	
	findViewById(R.id.order_btn_cart).setOnClickListener(this);
	findViewById(R.id.order_btn_cancel).setOnClickListener(this);
	findViewById(R.id.order_btn_minus).setOnClickListener(this);
	findViewById(R.id.order_btn_plus).setOnClickListener(this);
	  getActionBar().setDisplayHomeAsUpEnabled(true);
	getActionBar().setTitle(R.string.title_selectorder);
	total(flagCount);
	    // TODO Auto-generated method stub
	}
	
	
	
private void total(int cnt) {
		int sum =  price*cnt;
		
		edPriceSum.setText(sum+"¿ø");
		count.setText(flagCount+"");
	}



	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.order_btn_cart:
				Intent intent = new Intent(this,CartActivity.class);
				getIntent().putExtra("count", flagCount);
				
				intent.putExtras(getIntent());
				
//				intent.putExtra("edPrice", edPrice.getText().toString());
//				intent.putExtra("service", service.getText().toString());
//				intent.putExtra("count", count.getText().toString());
				startActivity(intent);
				break;
			case R.id.order_btn_cancel:
				finish();
				break;
			case R.id.order_btn_minus:
				
				
				if(flagCount>0){
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


	

	