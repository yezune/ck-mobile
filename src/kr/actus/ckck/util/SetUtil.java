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
	// 로딩중 표시 프로그래스바
	public static Dialog setProgress(Context context) {
		Dialog dg = new Dialog(context, android.R.style.Theme_Dialog);
		dg.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dg.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dg.setContentView(new ProgressBar(context, null,
				android.R.attr.progressBarStyle));
		dg.setCanceledOnTouchOutside(false);
		dg.setCancelable(false);
		dg.show();

		return dg;

	}

	static boolean flag;

	public static void dialog(Context context, String msg) {
		AlertDialog.Builder adb = new AlertDialog.Builder(context);
		adb.setMessage(msg);
		adb.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		adb.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		adb.show();
		
	}

	public static String filePath(String temp) {

		String dir = "";
		// String [] saveItem = new String[3];
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
		fullPath = fullPath + "/" + fileName;
		// SD카드 저장경로및 파일명 리턴.
		return fullPath;

	}

	public static class ActivityReference {

	}

}
