package kr.actus.ckck;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

public class AddUser extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_adduser);
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	    // TODO Auto-generated method stub
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
