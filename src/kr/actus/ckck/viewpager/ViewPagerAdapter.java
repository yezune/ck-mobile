package kr.actus.ckck.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ViewPagerAdapter extends PagerAdapter {
	private LayoutInflater inflater;
	private Context context;
	private int[]mRes;
	
	public ViewPagerAdapter(Context context, int[] mRes){
		super();
		this.context = context;
		this.mRes = mRes;
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mRes.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return view == ((ImageView) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView iView = new ImageView(context);
		iView.setImageResource(mRes[position]);
		((ViewPager) container).addView(iView,0);
		
		return iView;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView((ImageView) object);
		
		
	}
	
}
