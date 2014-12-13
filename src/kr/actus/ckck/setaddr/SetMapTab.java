package kr.actus.ckck.setaddr;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import kr.actus.ckck.R;
import kr.actus.ckck.util.SetURL;
import kr.actus.ckck.util.SetUtil;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


public class SetMapTab extends Fragment {
	static View v;
	GoogleMap map;
	SetURL ur;
	SetUtil util;
	Geocoder geoCoder;
	StringBuffer mAddress;
	Button setLoc;
	Dialog dg;
	SharedPreferences pref;
	SharedPreferences.Editor editor;
	String  address1, address2;
	AlertDialog.Builder adb;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try{
//		if (v == null) {
			v = inflater.inflate(R.layout.tab_setmap, container, false);
//		}
		}catch(InflateException e){
			
		}
		map = ((SupportMapFragment) getActivity().getSupportFragmentManager()
				.findFragmentById(R.id.setaddr_mapview)).getMap();
		geoCoder = new Geocoder(v.getContext(), Locale.KOREA);
		
		
		
		
		pref = getActivity().getSharedPreferences(ur.PREF,0);
		editor = pref.edit();
		
		setLoc = (Button) v.findViewById(R.id.setaddr_btn_map);
		init();
		adb = new AlertDialog.Builder(v.getContext());
		setLoc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mAddress != null) {
					
					adb.setMessage("배송지를 "+mAddress+"로 설정하시겠습니까?");
					adb.setPositiveButton("확인", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							editor.putString("address1", address1);
							editor.putString("address2", address2);
							editor.commit();
							getActivity().finish();
						}
					});
					adb.setNegativeButton("취소", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
						}
					});
					adb.show();
				}else{
					adb.setMessage("위치를 설정하여 주세요.");
					adb.setPositiveButton("확인", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
						}
					});
					adb.show();
				}

			}
		});
		
		
		map.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public void onMapClick(LatLng latLng) {
				mAddress = new StringBuffer();
				MarkerOptions markerOptions = new MarkerOptions();
				markerOptions.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.location));
				markerOptions.position(latLng);
				map.clear();
				map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
				map.addMarker(markerOptions);

				
				List<Address> addresses;
				try {
					addresses = geoCoder.getFromLocation(latLng.latitude,
							latLng.longitude, 1);

					if (null != addresses && addresses.size() > 0) {
						Address addr = addresses.get(0);

						mAddress.append(addr.getAdminArea()).append(" ");
						mAddress.append(addr.getLocality()).append(" ");
						
						mAddress.append(addr.getThoroughfare()).append(" ");
						address1 = mAddress.toString();
						
						mAddress.append(addr.getFeatureName());
						address2 = addr.getFeatureName();
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		return v;
		
	}

	private void init() {
		//서울대입구역 좌표37.481229, 126.952764
	
		double lat = Double.parseDouble(pref.getString("myLat", "37.481229"));
		
				double lng =Double.parseDouble(pref.getString("myLng","126.952764")); 
		LatLng address = new LatLng(lat, lng);
		CameraPosition cp = new CameraPosition.Builder().target((address)).zoom(15).build();
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
		
		
		
	}

	@Override
	public void onDestroyView() {
		
		if (v != null) {
			ViewGroup parent = (ViewGroup) v.getParent();
			if (parent != null) {
				parent.removeView(v);
			}
		}
		super.onDestroyView();
	}

}
