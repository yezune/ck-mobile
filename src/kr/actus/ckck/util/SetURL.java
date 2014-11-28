package kr.actus.ckck.util;

import android.os.Build;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.text.AndroidCharacter;

public class SetURL {
	public static final String URL = "http://54.65.19.127:3000";
	public static final String  JOIN = URL+"/api/joinMem";
	public static final String  MEMINFO =URL+"/api/memInfo";
	public static final String  LOCALLIST =URL+"/api/localList";
	public static final String  SHOPCATE =URL+"/api/shopCate";
	public static final String  SHOPLIST = URL+"/api/shopList";
	public static final String  MENULIST =URL+"/api/menuList";
	public static final String  ORDER =URL+"/api/order";
	public static final String  MYORDER =URL+"/api/myOrder";
	public static final String TAG = "MainActivity";
	//http://biz.epost.go.kr/openapi/openapi_request.jsp?subGubun=sub_3&subGubun_1=cum_38&gubun=m07 
	// 성명 강선규 이메일 darksamo@gmail.com 으로 신청한 인증키값
	public static final String ZIPCODE = "a314996f3c4a87fed1416726619504";
	//Sharedpreferences
	public static final String pref = "CKCK";
	
	public static final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
	
	
	}
