package kr.actus.ckck.setaddr;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import kr.actus.ckck.R;
import kr.actus.ckck.setaddrlist.SetAddrAdapter;
import kr.actus.ckck.setaddrlist.SetAddrListItem;
import kr.actus.ckck.util.SetURL;
import kr.actus.ckck.util.SetUtil;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SetAddrTab extends Fragment implements OnClickListener {

	EditText edAddr1, edAddr2;
	TextView loc;
	Button btnAddr1, btnAddr2;
	AsyncHttpClient client;
	SetURL ur;
	SAXBuilder builder;
	Document doc;
	View v;
	ListView listView;
	SetAddrListItem item;
	SetAddrAdapter adapter;
	SetUtil util;
	Dialog dg;
	SharedPreferences pref;
	SharedPreferences.Editor editor;
	LinearLayout layPost, layAddr;

	ArrayList<SetAddrListItem> itemList = new ArrayList<SetAddrListItem>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		pref = getActivity().getSharedPreferences(ur.PREF, 0);
		editor = pref.edit();

		v = inflater.inflate(R.layout.tab_setaddr, container, false);
		edAddr1 = (EditText) v.findViewById(R.id.addrtab_ed_add1);
		edAddr2 = (EditText) v.findViewById(R.id.addrtab_ed_add2);
		loc = (TextView) v.findViewById(R.id.addrtab_tv_loc);
		loc.setText(pref.getString("address1", "설정된 배송지가 없습니다.")
				+ pref.getString("address2", ""));
		btnAddr1 = (Button) v.findViewById(R.id.addrtab_btn_add1);
		btnAddr2 = (Button) v.findViewById(R.id.addrtab_btn_add2);
		listView = (ListView) v.findViewById(R.id.addrtab_listview);
		layPost = (LinearLayout) v.findViewById(R.id.addrtab_lay_postsearch);
		layAddr = (LinearLayout) v.findViewById(R.id.addrtab_lay_detailaddr);

		btnAddr1.setOnClickListener(this);
		btnAddr2.setOnClickListener(this);
		client = new AsyncHttpClient();

		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addrtab_btn_add1:

			Log.v(ur.TAG, "edaddr :" + edAddr1.getText());

			if (edAddr1.getText().toString().equalsIgnoreCase("")) {
				Toast.makeText(v.getContext(), "검색할 주소를 입력하세요",
						Toast.LENGTH_SHORT).show();
			} else {
				layAddr.setVisibility(View.INVISIBLE);
				layPost.setVisibility(View.VISIBLE);

				requestPost();

			}
			break;
		case R.id.addrtab_btn_add2:
			String addr1 = pref.getString("address1", null);
			if (addr1 != null) {
				if (edAddr2.getText().toString().equalsIgnoreCase("")) {
					Toast.makeText(v.getContext(), "세부 주소를 입력하세요",
							Toast.LENGTH_SHORT).show();

				} else {
					editor.putString("address2", edAddr2.getText().toString());
					editor.commit();

					loc.setText(addr1 + edAddr2.getText());
					edAddr2.setText("");
				}

			} else {
				Toast.makeText(v.getContext(), "주소검색을 완료 하세요",
						Toast.LENGTH_SHORT).show();
			}
			
			break;
		}

	}

	private void requestPost() {

		try {
			String url = ur.POSTIP + "?regkey=" + ur.REGKEY + "&target="
					+ ur.TARGET + "&query="
					+ URLEncoder.encode(edAddr1.getText().toString(), "EUC-KR");

			client.addHeader("accept-language", "ko");
			client.get(url, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int stat, Header[] header, byte[] binary) {
					try {

						// if(binary.length>204){
						InputStream is = null;
						// Log.v(ur.TAG,"binary size :"+binary.length);
						is = new ByteArrayInputStream(binary);

						// XML 파싱
						builder = new SAXBuilder();
						doc = builder.build(is);
						// Log.v(ur.TAG,"doc :"+doc);
						// itemlist하위에 우편번호와 주소값을 가짐

						Element itemlist;

						if (doc.getRootElement().getName().equals("post")) {
							itemlist = doc.getRootElement()
									.getChild("itemlist");
							List list = itemlist.getChildren();

							itemList.clear();
							// 검색결과가 여러개인 경우 반복하며 우편번호와 주소값을 뽑아낸다
							for (int i = 0; i < list.size(); i++) {
								Element eitem = (Element) list.get(i);
								String address = eitem.getChildText("address");
								String postcd = eitem.getChildText("postcd");
								
								item = new SetAddrListItem(address, postcd);
								itemList.add(item);

							}

						} else if (doc.getRootElement().getName()
								.equals("error")) {
							itemlist = doc.getRootElement();

							String msg = itemlist.getChildText("message");
							Toast.makeText(v.getContext(), msg,
									Toast.LENGTH_SHORT).show();

						}

						// }else{
						// Toast.makeText(v.getContext(), "검색하실 주소를 다시 입력하세요.",
						// Toast.LENGTH_SHORT).show();
						// }

						adapter = new SetAddrAdapter(SetAddrTab.this, v
								.getContext(), R.layout.setaddr_list_item,
								itemList);
						listView.setAdapter(adapter);

					} catch (JDOMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					super.onSuccess(stat, header, binary);
				}

				@Override
				public void onFinish() {
					dg.dismiss();
					super.onFinish();
				}

				@Override
				public void onStart() {
					dg = util.setProgress(v.getContext());
					super.onStart();
				}

			});

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void requestAdapter(String addr1, String addr2) {
		String address = addr1 + " " + addr2;

		editor.putString("addressPost", addr1);
		editor.putString("address1", addr2);
		editor.commit();

		edAddr1.setText(addr2);

		layAddr.setVisibility(View.VISIBLE);
		layPost.setVisibility(View.INVISIBLE);
		Log.v(ur.TAG,
				"pref.gsetString deliAddr : " + pref.getString("address1", "0"));
		loc.setText(address);
		itemList.clear();
		adapter.notifyDataSetChanged();

	}
}
