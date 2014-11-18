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
import java.util.Locale;

import kr.actus.ckck.gridlist.GridAdapter;
import kr.actus.ckck.gridlist.GridItem;
import kr.actus.ckck.setaddr.SetAddr;
import kr.actus.ckck.viewpager.ViewPagerAdapter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener{
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private LinearLayout mDrawerLinear;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mCategory;
	private Button addrOther;
	private final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getActionBar().setTitle(getTitle());

		mDrawerTitle = getText(R.string.menu);

		mCategory = getResources().getStringArray(R.array.category_arr);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerLinear = (LinearLayout) findViewById(R.id.drawer_linear);
		addrOther = (Button) findViewById(R.id.addrOther);
		addrOther.setOnClickListener(this);
		
		// drawer를 열때 drawer내용 오버레이
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// drawer 리스트뷰 어댑터 연결
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mCategory));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// 액션바 아이콘 활성화
		getActionBar().setDisplayHomeAsUpEnabled(false);
		
		getActionBar().setHomeButtonEnabled(true);

		
		// 액션바 아이콘과 drawerToggle 상태 설정
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 
		R.drawable.ic_drawer, 
		R.string.drawer_open, 
		R.string.drawer_close 
		) {
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
			selectItem(0);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	//invalidateOptionsMenu()호출
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		// drawer 메뉴가 열려있을때 액션바 아이콘 설정
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerLinear);
		menu.findItem(R.id.action_cart).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // drawer메뉴가 닫혀있거나 메뉴에서 아이템 클릭했을때 액션바아이콘 설정
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        
        switch(item.getItemId()) {
        case R.id.action_cart: //장바구니 선택시 화면 전환
           
          Intent intent = new Intent(this,Cart.class);
           startActivity(intent);
            return true;
        case R.id.action_addUser: //회원가입선택시 화면전환
        	
        	Intent intentUser = new Intent(this,AddUser.class);
        	
        	startActivity(intentUser);
        
        	return true;
        
        default:
            return super.onOptionsItemSelected(item);
            
    
    }
	}
	// drawer메뉴에서 리스트뷰의 아이템 클릭했을때
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		// drawer메뉴에서 아이템을 클릭했을때 fragment 화면 변경
		Fragment fragment = new MenuFragment();
		Bundle args = new Bundle();
		args.putInt(MenuFragment.MENU_NUM, position);
		fragment.setArguments(args);

		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(mCategory[position]);
		mDrawerLayout.closeDrawer(mDrawerLinear);
	}

	@Override
	public void setTitle(CharSequence title) {
		 mTitle = title;
		 getActionBar().setTitle(mTitle);
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

	
	public static class MenuFragment extends Fragment implements
			OnClickListener {
		public static final String MENU_NUM = "menu_num";
		private ImageView imgAd;
//		private Button addrOther;
		private GridView gridList;
		private GridAdapter gAdapter;
		private GridItem gItem;
		private LinearLayout mPageMark;
		private ArrayList<GridItem> gListItem = new ArrayList<GridItem>();
		private ViewPager mPager;
		public int[] mRes = new int[]{R.drawable.menu_sample,R.drawable.menu_sample,R.drawable.menu_sample,R.drawable.menu_sample,R.drawable.menu_sample};
		private int prePosition;
		View cView;
		
		public MenuFragment() {
			//서브 클래스용 생성자
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			cView = inflater.inflate(R.layout.fragment_main, container,
					false);
			int i = getArguments().getInt(MENU_NUM);
//			imgAd = (ImageView) cView.findViewById(R.id.imgAd);
//			addrOther = (Button) cView.findViewById(R.id.addrOther);
			
			mPager = (ViewPager) cView.findViewById(R.id.content_pager);
			mPageMark=(LinearLayout) cView.findViewById(R.id.page_mark);
			setViewPager();
			setGrid();
			
//			imgAd.setOnClickListener(this);
//			addrOther.setOnClickListener(this);
			
			
			//drawer메뉴 리스트뷰 테스트용
			String category = getResources().getStringArray(
					R.array.category_arr)[i];

			int imageId = getResources().getIdentifier(
					category.toLowerCase(Locale.getDefault()), "drawable",
					getActivity().getPackageName());
			
			getActivity().setTitle(category);
			return cView;
		}

	
		
		
		private void setViewPager() {	//광고창 뷰페이져
			ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getActivity(),mRes);
			mPager.setAdapter(vpAdapter);
			
			mPager.setOnPageChangeListener(new OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int position) {	//광고포지션위치 확인용 서클 설정
					mPageMark.getChildAt(prePosition).setBackgroundResource(
							R.drawable.page_not);
					mPageMark.getChildAt(position).setBackgroundResource(
							R.drawable.board_circle);
					prePosition = position;
					
				}
				
				@Override
				public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			initPageMark();
			
		}

		private void initPageMark() {	//광고 포지션 위치 확인용 서클
			for (int i = 0; i < mRes.length; i++) {
				ImageView iv = new ImageView(getActivity().getApplicationContext());
				
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);
				params.gravity = Gravity.CENTER;
			
				iv.setLayoutParams(params);
				iv.setPadding(10, 10, 10, 10);
				
				if (i == 0)
					iv.setBackgroundResource(R.drawable.board_circle);
				else
					iv.setBackgroundResource(R.drawable.page_not);

				mPageMark.addView(iv);
			}
			prePosition = 0;
			
		}

		private void setGrid() {// 그리드뷰 아이템 테스트
			for(int k=0;k<10;k++){
				gItem = new GridItem(R.drawable.menu_sample, "상호명", "타입", "최소주문금액", "배달여부");
				gListItem.add(gItem);
				}
				gridList = (GridView)cView.findViewById(R.id.gridView1);
				gAdapter = new GridAdapter(getActivity(), R.layout.gridview_item, gListItem);
				gridList.setAdapter(gAdapter);
				
			
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
//			case R.id.imgAd:	//광고항목 클릭시
//				Toast.makeText(getActivity(), "imgAd click!",
//						Toast.LENGTH_SHORT).show();
//				break;
			

			}

		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
		case R.id.addrOther:
			
			Intent intent = new Intent(this, SetAddr.class);
			startActivity(intent);
			break;
		}
		
	}
	
}