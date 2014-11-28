package kr.actus.ckck.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import kr.actus.ckck.MainActivity;
import kr.actus.ckck.R;
import kr.actus.ckck.gridlist.GridAdapter;
import kr.actus.ckck.gridlist.GridItem;
import kr.actus.ckck.util.AsyncData;
import kr.actus.ckck.util.SetURL;
import kr.actus.ckck.util.SetUtil;
import kr.actus.ckck.viewpager.ViewPagerAdapter;
import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainTab extends Fragment {

	public static final String MENU_NUM = "menu_num";
	private static final String TAG = "MainActivity";
	private GridView gridList;
	private GridAdapter gAdapter;
	private GridItem gItem;
	private LinearLayout mPageMark;
	private ArrayList<GridItem> gListItem = new ArrayList<GridItem>();
	
	private ViewPager mPager;
	Dialog dg;
	SetURL ur;
	SetUtil util;
	String path = ur.path;
	AsyncData asyncData;
	AsyncHttpClient client;
	public int[] mRes = new int[] { R.drawable.menu_sample,
			R.drawable.menu_sample, R.drawable.menu_sample,
			R.drawable.menu_sample, R.drawable.menu_sample };
	private int prePosition;
	View cView;
	MainActivity mainActivity;

	public MainTab(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		cView = inflater.inflate(R.layout.fragment_main, container, false);
		
		int i = getArguments().getInt(MENU_NUM);
		// imgAd = (ImageView) cView.findViewById(R.id.imgAd);
		// addrOther = (Button) cView.findViewById(R.id.addrOther);
		client = new AsyncHttpClient();
		mPager = (ViewPager) cView.findViewById(R.id.content_pager);
		mPageMark = (LinearLayout) cView.findViewById(R.id.page_mark);
		gridList = (GridView) cView.findViewById(R.id.main_grid_list);
		setViewPager();
		setGrid();

		// imgAd.setOnClickListener(this);
		// addrOther.setOnClickListener(this);

		// drawer메뉴 리스트뷰 테스트용
//		String category = getResources().getStringArray(R.array.category_arr)[i];
//
//		int imageId = getResources().getIdentifier(
//				category.toLowerCase(Locale.getDefault()), "drawable",
//				getActivity().getPackageName());
//
//		getActivity().setTitle(category);

		return cView;
	}

	private void setViewPager() { // 광고창 뷰페이져
		ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getActivity(), mRes);
		mPager.setAdapter(vpAdapter);

		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) { // 광고포지션위치 확인용 서클 설정
				mPageMark.getChildAt(prePosition).setBackgroundResource(
						R.drawable.page_not);
				mPageMark.getChildAt(position).setBackgroundResource(
						R.drawable.board_circle);
				prePosition = position;

			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		initPageMark();

	}

	private void initPageMark() { // 광고 포지션 위치 확인용 서클
		for (int i = 0; i < mRes.length; i++) {
			ImageView iv = new ImageView(getActivity().getApplicationContext());
			iv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));


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
	
			RequestParams param = new RequestParams();

			param.put("localID", "1");
//			dg = util.setProgress(mainActivity);
//			asyncData = new AsyncData(cView.getContext(), ur.SHOPLIST,param);
//			JSONArray response  = (JSONArray) asyncData.postJSONArray();
//			
			client.post(ur.SHOPLIST, param, new JsonHttpResponseHandler() {

				@Override
				public void onFailure(Throwable e, JSONObject errorResponse) {
//					Log.v(ur.TAG, "menu response error :" + errorResponse);
//					dg.dismiss();
					super.onFailure(e, errorResponse);
				}

				@Override
				public void onSuccess(JSONArray response) {

					Log.v(ur.TAG, "menu response array:" + response);
					try {
						for (int i = 0; i < response.length(); i++) {
							JSONObject con = response.getJSONObject(i);
							String shopId = con.getString("shopID");
							String title = con.getString("shopName");
							String primeMenu = con.getString("primeMenu");
							String minMoney = con.getString("minPrice");
							String delivery = con.getString("delivery");
							String img = con.getString("shopImage");
							
							String telNumber = con.getString("telNumber");
							String sTime = con.getString("startTime");
							String eTime = con.getString("endTime");
							Log.v(ur.TAG,"telNumber : "+telNumber);
							Log.v(ur.TAG,"sTime : "+sTime);
							Log.v(ur.TAG,"eTime : "+eTime);
							
							File file = new File(path + img);
							
							if (!file.exists()) {
								String savefile = util.filePath(path+img);
								
								asyncData.binaryClient(img,savefile);

								// asyncBinary(img);
							}
							

							 gItem = new GridItem(path+img, shopId, title, primeMenu, minMoney, delivery, telNumber,sTime,eTime);
							 gListItem.add(gItem);
							 
						}
						gAdapter = new GridAdapter(mainActivity, getActivity(), R.layout.gridview_item, gListItem);
						 gridList.setAdapter(gAdapter);
						 

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					super.onSuccess(response);
				}

				@Override
				public void onFinish() {
					dg.dismiss();
					super.onFinish();
				}

				@Override
				public void onStart() {

					dg = util.setProgress(getActivity());
					super.onStart();
				}

			});

		
//		for (int k = 0; k < 10; k++) {
//			gItem = new GridItem(R.drawable.menu_sample, "상호명", "타입", "최소주문금액",
//					"배달여부");
//			gListItem.add(gItem);
//		}
		
		gAdapter = new GridAdapter(mainActivity, getActivity(),
				R.layout.gridview_item, gListItem);
		gridList.setAdapter(gAdapter);

	}

}
