package kr.actus.ckck.setaddr;



import com.google.android.gms.tagmanager.Container;

import kr.actus.ckck.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SetMapTab extends Fragment {
	View v;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if(v == null){
		 v = inflater.inflate(R.layout.tab_setmap, container, false);
		}
		
		
		return v;
	}
	@Override
	public void onDestroyView() {
		if(v!=null){
			ViewGroup parent = (ViewGroup)v.getParent();
			if(parent!=null){
				parent.removeView(v);
			}
		}
		super.onDestroyView();
	}

}
