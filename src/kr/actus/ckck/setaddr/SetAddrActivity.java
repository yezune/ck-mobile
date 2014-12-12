package kr.actus.ckck.setaddr;

import kr.actus.ckck.R;
import kr.actus.ckck.R.id;
import kr.actus.ckck.R.layout;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SetAddrActivity extends FragmentActivity implements OnClickListener{ 
	//OnArticleSelectedListener{


		final String TAG = "MainActivity";

		int mCurrentFragmentIndex;
		public final static int SETADDR = 0;
		public final static int SETMAP = 1;
		public final static int SETDISTANCE = 2;
		Button btSetAddr,btSetMap,btSetDistance;
		
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_setaddr);
			 getActionBar().setDisplayHomeAsUpEnabled(true);
			
			
		
			
			btSetAddr = (Button) findViewById(R.id.btn_setaddr_addr);
			btSetAddr.setOnClickListener(this);
			btSetMap = (Button) findViewById(R.id.btn_setaddr_map);
			btSetMap.setOnClickListener(this);
//			btSetDistance = (Button) findViewById(R.id.btn_setaddr_dis);
//			btSetDistance.setOnClickListener(this);
			
			
			mCurrentFragmentIndex = SETADDR;

			fragmentReplace(mCurrentFragmentIndex);
			
			
		}

	
		public void fragmentReplace(int reqNewFragmentIndex) {

			Fragment newFragment = null;

			Log.d(TAG, "fragmentReplace " + reqNewFragmentIndex);

			newFragment = getFragment(reqNewFragmentIndex);
			Log.v(TAG,"newFragment : "+newFragment);
			// replace fragment
			final FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			Bundle savebundle = new Bundle();
		

		
			transaction.replace(R.id.ll_fragment, newFragment);
			//백키로 현재플래그먼트없애기
//			transaction.addToBackStack(null);
			// Commit the transaction
			transaction.commit();

		}

		private Fragment getFragment(int idx) {
			Fragment newFragment = null;
			
			switch (idx) {
			case SETADDR:
				newFragment = new SetAddrTab();
				layoutSet();
				btSetAddr.setTextColor(getResources().getColor(R.color.black));
				btSetAddr.setSelected(true);
				break;
			case SETMAP:
				newFragment = new SetMapTab();
				layoutSet();
				btSetMap.setTextColor(getResources().getColor(R.color.black));
				btSetMap.setSelected(true);
				break;
//			case SETDISTANCE:
//				newFragment = new SetDistanceTab();
//				layoutSet();
//				btSetDistance.setTextColor(getResources().getColor(R.color.black));
//				btSetDistance.setSelected(true);
//				break;
			
			default:
				Log.d(TAG, "Unhandle case");
				break;
			}

			return newFragment;
		}

		private void layoutSet() {
			btSetAddr.setSelected(false);
			btSetMap.setSelected(false);
//			btSetDistance.setSelected(false);
//			btSetDistance.setTextColor(getResources().getColor(R.color.white));
			btSetMap.setTextColor(getResources().getColor(R.color.white));
			btSetAddr.setTextColor(getResources().getColor(R.color.white));
		}
		
		

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.btn_setaddr_addr:
				mCurrentFragmentIndex = SETADDR;
				fragmentReplace(mCurrentFragmentIndex);
				break;
			case R.id.btn_setaddr_map:
				mCurrentFragmentIndex = SETMAP;
				fragmentReplace(mCurrentFragmentIndex);
				break;
//			case R.id.btn_setaddr_dis:
//				mCurrentFragmentIndex = SETDISTANCE;
//				fragmentReplace(mCurrentFragmentIndex);
//				break;
			
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

	

