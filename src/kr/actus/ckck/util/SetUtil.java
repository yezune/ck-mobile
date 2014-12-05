package kr.actus.ckck.util;

import java.io.File;

import kr.actus.ckck.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ProgressBar;

public class SetUtil {
	//�ε��� ǥ�� ���α׷�����
	public static Dialog setProgress(Context context){
		Dialog dg = new Dialog(context, android.R.style.Theme_Dialog);
		dg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dg.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dg.setContentView(new ProgressBar(context,null,android.R.attr.progressBarStyle));
		dg.setCanceledOnTouchOutside(false);
		dg.setCancelable(false);
		dg.show();
		
		return dg;
		
	}
	public static void dialog(Context context,int msg){
		AlertDialog.Builder adb = new AlertDialog.Builder(context);
		adb.setMessage(msg);
		switch(msg){
		case R.string.net_error:
		
		adb.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				android.os.Process.killProcess(android.os.Process.myPid());//�� ����
				
			}
		});
		case R.string.gps_check:
			adb.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// GPS����â ����
					Intent intent = new Intent(
							android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					intent.addCategory(Intent.CATEGORY_DEFAULT);
					
					
					
				}

			});
			adb.setNegativeButton("���", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
			
			break;
		}
		adb.show();
		
	}
	
	public static String filePath(String temp) {

		String dir = "";
//		String [] saveItem = new String[3];
		String fullPath = null;
		String fileName = null;
		
		int n = temp.lastIndexOf("/");

		if (n >= 0) {

			dir = temp.substring(0, n);
			fullPath = dir;
			
			File file = new File(fullPath);
			if (!file.exists())
				file.mkdirs();
			fileName = temp.substring(n + 1);
			
			if (n == 0)
				dir = "/";
		}
		fullPath = fullPath+"/"+fileName; 
		// SDī�� �����ι� ���ϸ� ����.
		return fullPath;

	}
	public static class ActivityReference{
		
	}
	
	
}
