/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kr.actus.ckck;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import kr.actus.ckck.drawer.CenterActivity;
import kr.actus.ckck.drawer.EventActivity;
import kr.actus.ckck.drawer.MyHistoryActivity;
import kr.actus.ckck.drawer.SettingActivity;
import kr.actus.ckck.drawerlist.DrawerAdapter;
import kr.actus.ckck.drawerlist.DrawerItem;
import kr.actus.ckck.fragment.MainTab;
import kr.actus.ckck.fragment.MenuTab;
import kr.actus.ckck.fragment.StoreTab;
import kr.actus.ckck.setaddr.SetAddrActivity;
import kr.actus.ckck.util.AsyncData;
import kr.actus.ckck.util.SetURL;
import kr.actus.ckck.util.SetUtil;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;

	DrawerAdapter drawerAdapter;
	DrawerItem drawerItem;
	ArrayList<DrawerItem> drawerItemList = new ArrayList<DrawerItem>();
	// private LinearLayout mDrawerLinear;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private Button addrOther;
	final static String TAG = "MainActivity";

	public final static int MAINTAB = 0;
	public final static int STORETAB = 1;
	public final static int MENUTAB = 2;

	Dialog dg;
	AsyncData sResponese;
	SetUtil util;
	SetURL ur;
	int mCurrentFragmentIndex;
	String menuIndex;
	SharedPreferences pref;
	SharedPreferences.Editor editor;
	String uniqueKey;
	TextView addrBasic, drawerAdd, drawerCenter, drawerEvent, drawerSetting;
	Bundle savebundle;
	JSONObject con;
	int len;
	boolean isWiFi, isMobile;
	Fragment newFragment;
	// ServerResponse sr = new ServerResponse();
	AsyncHttpClient client = new AsyncHttpClient();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		checkNet();
		savebundle = new Bundle();
		getActionBar().setTitle(getTitle());
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b67bc")) );
		pref = getSharedPreferences(ur.PREF, 0);
		editor = pref.edit();

		try {
			setDrawer();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addrOther = (Button) findViewById(R.id.main_btn_addr_reg);
		addrOther.setOnClickListener(this);
		addrBasic = (TextView) findViewById(R.id.main_tv_addr_basic);

	// 액션바 아이콘 활성화
		getActionBar().setDisplayHomeAsUpEnabled(false);

		getActionBar().setHomeButtonEnabled(true);

		// 액션바 아이콘과 drawerToggle 상태 설정
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				Log.v(TAG, getActionBar().getTitle() + "");
				invalidateOptionsMenu(); // onPrepareOptionsMenu()호출
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			setTitle(R.string.app_name);
		}

		mCurrentFragmentIndex = MAINTAB;
		fragmentReplace(mCurrentFragmentIndex);
		setStatus();
		// setActionBar();

	}

	
	private void checkNet() { //wifi & mobile 상태 체크 후 다이얼로그 출력
		ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		isMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
		isWiFi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		if (!isMobile && !isWiFi) {
			util.dialog(this,R.string.net_error);
		}
String gps = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
	   	
		if (!(gps.matches(".*gps.*") && gps.matches(".*network.*"))) {
			util.dialog(this,R.string.gps_check);
		} 
		
		
		
	}

	private void setDrawer() throws JSONException {
		mDrawerTitle = getText(R.string.menu);
//		mCategory = getResources().getStringArray(R.array.category_arr);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setOnClickListener(this);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		// mDrawerLinear = (LinearLayout) findViewById(R.id.drawer_linear);

		// drawer를 열때 drawer내용 오버레이
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		
		client.post(ur.SHOPCATE, new JsonHttpResponseHandler() {

			@Override
			public void onFailure(Throwable e, JSONArray errorResponse) {
				Log.v(TAG, "shopcate failure :" + e + "::" + errorResponse);
				super.onFailure(e, errorResponse);
			}

			@Override
			public void onSuccess(int statusCode, JSONArray response) {

				// con = response.getJSONObject(i);
				try {
					Log.v(TAG, "response.length :" + response.length());
					for (int i = 0; i < response.length(); i++) {
						con = response.getJSONObject(i);
						//
						drawerItem = new DrawerItem(con.getString("shopCate"), con
								.getString("cateName"));
						
						drawerItemList.add(drawerItem);

					}
					setAdapter();

					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				super.onSuccess(statusCode, response);
			}

			

			@Override
			public void onFinish() {
				dg.dismiss();
				super.onFinish();
			}

			@Override
			public void onStart() {
				dg = util.setProgress(MainActivity.this);
				super.onStart();
			}

		});

		

	}

	private void setAdapter() {	//drawer리스트뷰연결
		// TODO Auto-generated method stub
		View header = getLayoutInflater().inflate(
				R.layout.drawer_list_item_header, null, false);
		View footer = getLayoutInflater().inflate(
				R.layout.drawer_list_item_footer, null, false);

		header.findViewById(R.id.drawer_list_item_header_tv1)
				.setOnClickListener(this);
		header.findViewById(R.id.drawer_list_item_header_tv2)
				.setOnClickListener(this);
		header.findViewById(R.id.drawer_list_item_header_tv3)
				.setOnClickListener(this);
		footer.findViewById(R.id.drawer_list_item_footer_tv1)
				.setOnClickListener(this);
		footer.findViewById(R.id.drawer_list_item_footer_tv2)
				.setOnClickListener(this);
		footer.findViewById(R.id.drawer_list_item_footer_tv3)
				.setOnClickListener(this);

		mDrawerList.addHeaderView(header);
		mDrawerList.addFooterView(footer);

		drawerAdapter = new DrawerAdapter(this, this,
				R.layout.drawer_list_item, drawerItemList);
		mDrawerList.setAdapter(drawerAdapter);
		
	}

	// 액션바 아이콘 변경으로 인해서 회원정보 리턴값 처리해야함 14/11/23
	private void setStatus() {
//		dg = util.setProgress(this);
		uniqueKey = Secure.getString(this.getContentResolver(),
				Secure.ANDROID_ID);
		RequestParams param = new RequestParams();
		param.put("uniqueKey", uniqueKey);
		// JSONArray result = sr.postJSONArray(SetURL.MEMINFO, param);

		// editor.putString("regKey", result.getString(0));

		client.post(SetURL.MEMINFO, param, new JsonHttpResponseHandler() {

			@Override
			public void onFailure(Throwable e, JSONArray errorResponse) {
				Log.v(TAG, "array failure : " + errorResponse);
				super.onFailure(e, errorResponse);
			}

			@Override
			public void onSuccess(JSONArray response) {
				try {
					 Log.v(TAG,"array success : "+response.getJSONObject(0));
					con = response.getJSONObject(0);
					saveInfo();
					
//					Log.v(TAG, "array success : " + con.getString("address1"));
					// addrBasic.setText(con.getString("address1"));
					// pref.getString("uniqueKey", null);
					
					// Log.v(TAG,"array success : "+addrBasic.getText().toString());
//					Log.v(TAG, "pref : " + pref.getString("uniqueKey", null));

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				super.onSuccess(response);
			}

		

			

		});
		
		

	}
	
	public void saveInfo() {
		 editor.clear();
			 try {
			editor.putString("memPoint", con.getString("memPoint"));
//			 editor.putString("RegDate", con.getString("regDate"));
			 editor.putString("address1", con.getString("address1"));
			 editor.putString("address2", con.getString("address2"));
			 editor.putString("memName", con.getString("memName"));
			 editor.putString("uniqueKey",con.getString("uniqueKey"));
			 editor.putString("mobile", con.getString("mobile"));

			 editor.commit();
//			 dg.dismiss();
			 String deliAddr =pref.getString("deliAddr", "0"); 
			
			 addrBasic.setText("배송지 | "+con.getString("address1"));
			
			
			 Log.v(TAG,"check pref uniqueKey : "+pref.getString("uniqueKey", ""));
			 
			 } catch (JSONException e) {
					Log.v(TAG, "error :"+e);
					e.printStackTrace();
				}
		
	}
	
	
	

	
	private Fragment getFragment(int idx) {
		 newFragment = null;
		switch (idx) {
		case MAINTAB:
			newFragment = new MainTab(this);

			break;
		case STORETAB:
			newFragment = new StoreTab(this,savebundle);
			break;
		case MENUTAB:
			newFragment = new MenuTab(this,savebundle);
			break;
		default:

			break;
		}

		return newFragment;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		
		menu.findItem(R.id.action_add_user).setVisible(true);
		menu.findItem(R.id.action_search).setVisible(false);
		menu.findItem(R.id.action_cart).setVisible(false);
		return super.onCreateOptionsMenu(menu);
	}

	// invalidateOptionsMenu()호출
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		//서버에서 회원정보를 받아왔응ㄹ때 액션바 아이콘 변경
		
		menu.findItem(R.id.action_search).setVisible(true);
		menu.findItem(R.id.action_cart).setVisible(true);
		menu.findItem(R.id.action_add_user).setVisible(false);
		
		
		
		// drawer 메뉴가 열려있을때 액션바 아이콘 설정
		// boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerLinear);
//		 menu.findItem(R.id.action_cart).setVisible(!drawerOpen);

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// drawer메뉴가 닫혀있거나 메뉴에서 아이템 클릭했을때 액션바아이콘 설정
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
		case R.id.action_cart: // 장바구니 선택시 화면 전환

			Intent intent = new Intent(this, CartActivity.class);
			startActivity(intent);
			return true;
		case R.id.action_add_user: // 회원가입선택시 화면전환

			Intent intentUser = new Intent(this, AddUserActivity.class);

			startActivity(intentUser);

			return true;

		default:
			return super.onOptionsItemSelected(item);

		}
	}

	// drawer메뉴에서 리스트뷰의 아이템 클릭했을때
	// private class DrawerItemClickListener implements
	// ListView.OnItemClickListener {
	// @Override
	// public void onItemClick(AdapterView<?> parent, View view, int position,
	// long id) {
	// selectItem(position);
	// }
	// }

//	private void selectItem(String menuIndex,String title) {	
		// drawer메뉴에서 아이템을 클릭했을때 fragment 화면 변경
//		newFragment = null;
//		newFragment = getFragment(MENUTAB);
//		if (savebundle != null) {
//			savebundle = null;
//		}			
//		fragmentReplace(MENUTAB);
//		newFragment.setArguments(savebundle);
//		
//		FragmentManager fragmentManager = getSupportFragmentManager();
//		fragmentManager.beginTransaction()
//				.replace(R.id.content_frame, newFragment).commit();
//
		// update selected item and title, then close the drawer
		// mDrawerList.setItemChecked(title, true);
//		setTitle(title);
//		mDrawerLayout.closeDrawer(mDrawerList);
//	}
	public void fragmentReplace(int fragmentIndex) {
		
		newFragment = null;
		newFragment = getFragment(fragmentIndex);
		newFragment.setArguments(savebundle);
	
		// 플래그먼트 교체
		final FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		// Bundle savebundle = new Bundle();
		// savebundle.putString("title", "bundle value");

		
		
		transaction.replace(R.id.content_frame, newFragment);
		transaction.addToBackStack(null);
//		Log.v(TAG, "newFragment :" + newFragment);
		transaction.commit();

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
//		Log.v(TAG, "title : " + mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// 토글상태 동기화
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// 변경 값 토글에 설정
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.main_btn_addr_reg:

			Intent intent = new Intent(this, SetAddrActivity.class);
			startActivity(intent);
			break;
	
		case R.id.drawer_list_item_header_tv1:
			Intent intent1 = new Intent(this, MyHistoryActivity.class);
			startActivity(intent1);
			break;
		case R.id.drawer_list_item_header_tv2:
			Intent intent2 = new Intent(this, AddUserActivity.class);
			startActivity(intent2);
			break;
		case R.id.drawer_list_item_header_tv3:
			Log.v(TAG, "카테고리 click");
			break;
		case R.id.drawer_list_item_footer_tv1:
			Intent intent3 = new Intent(this, CenterActivity.class);
			startActivity(intent3);
			break;
		case R.id.drawer_list_item_footer_tv2:
			Intent intent4 = new Intent(this, EventActivity.class);
			startActivity(intent4);
			break;
		case R.id.drawer_list_item_footer_tv3:
			Intent intent5 = new Intent(this, SettingActivity.class);
			startActivity(intent5);

			break;

		}

	}
	public void receive(Bundle bundle, int index) {
		if (savebundle != null) {
			savebundle = null;
		}
		savebundle = bundle;
		mDrawerLayout.closeDrawer(mDrawerList);
		fragmentReplace(index);
		
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		
		Log.v(TAG,"back newfragment :"+ newFragment.getId());
		super.onBackPressed();
	}

}