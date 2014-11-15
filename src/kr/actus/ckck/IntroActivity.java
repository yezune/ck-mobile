package kr.actus.ckck;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class IntroActivity extends Activity {

	public static final int DOWN_PROGRESS = 0;
	private static final String TAG = "MainActivity";
	ProgressBar inBar;
	TextView inVer, inTxt;
	public static String dbPath;
	File dbFile;
	JSONObject res;
	JSONArray con;
	boolean isWiFi, isMobile;

	public String dbUrl = "";
	AsyncHttpClient client = new AsyncHttpClient();

	String tableName;
	String[] att;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		inBar = (ProgressBar) findViewById(R.id.inPb);
		inTxt = (TextView) findViewById(R.id.inTxt);

		client = new AsyncHttpClient();

		checkSet();

	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:

				new DownFileAsync().execute(dbUrl);

				break;
			case 1:
				inTxt.setText("DB 저장 중 입니다...");

				break;

			}

			super.handleMessage(msg);
		}

	};

	private void startDown(String geturl) {
		Log.v(TAG, "startDown");
		client.get(geturl, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject response) {

				inBar.setProgress(10);
				super.onSuccess(response);
			}

		});

	}

	public void secondDown(String ur, final String[] at) {
		att = at;
		client.get(ur, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject response) {
				try {

					inTxt.setText("수신된 좌표를 저장 중 입니다.");
					con = response.getJSONArray("data");
					tableName = (String) response.getString("TableName");

				} catch (JSONException e) {
					Log.v(TAG, "secondDown error :" + e);
				} catch (SQLException e) {

					Log.v(TAG, "secondDownd SQLexception :" + e);
				}

				super.onSuccess(response);
			}

		});

	}

	private void checkSet() {
		dbFile = new File(dbPath);
		try {
			// manifest 에서 버전정보 가져와서 text에 셋
			PackageInfo i = getPackageManager().getPackageInfo(
					getPackageName(), 0);
			inVer.setText(i.versionName);
		} catch (NameNotFoundException e) {
			Log.v(TAG, "Not Found Exception :" + e);
		}

	}

	class DownFileAsync extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... aurl) {
			int count;

			try {
				URL url = new URL(aurl[0]);
				URLConnection conexion = url.openConnection();
				conexion.connect();

				int lenghtOfFile = conexion.getContentLength();
				Log.v(TAG, "Lenght of file: " + lenghtOfFile);

				InputStream input = new BufferedInputStream(url.openStream());
				OutputStream output = new FileOutputStream(dbPath);

				byte data[] = new byte[1024];

				long total = 0;

				while ((count = input.read(data)) != -1) {
					total += count;
					publishProgress("" + (int) ((total * 100) / lenghtOfFile));
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				input.close();
			} catch (Exception e) {
				Log.v(TAG, "DownLoad error : " + e);
			}
			return null;

		}

		protected void onProgressUpdate(String... progress) {
			Log.d("ANDRO_ASYNC", progress[0]);
			inBar.setProgress(Integer.parseInt(progress[0]));
		}

		@Override
		protected void onPostExecute(String unused) {

			Toast.makeText(IntroActivity.this, "작업완료", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 0:
			Intent intent = new Intent(IntroActivity.this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| intent.FLAG_ACTIVITY_SINGLE_TOP);

			startActivity(intent);
			IntroActivity.this.finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
