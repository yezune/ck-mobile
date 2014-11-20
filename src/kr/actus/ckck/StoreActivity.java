package kr.actus.ckck;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class StoreActivity extends Activity {

	/** Called when the activity is first created. */
	Bundle bundle = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    bundle = getIntent().getExtras();
	setContentView(R.layout.activity_menustore);
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	    getActionBar().setTitle(bundle.getString("title"));
	    // TODO Auto-generated method stub
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
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	
}
