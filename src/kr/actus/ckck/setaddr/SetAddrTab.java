package kr.actus.ckck.setaddr;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.Header;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import kr.actus.ckck.R;
import kr.actus.ckck.util.SetURL;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SetAddrTab extends Fragment implements OnClickListener {

	EditText edAddr1;
	TextView loc;
	Button btnAddr1;
	AsyncHttpClient client;
	SetURL ur;
	SAXBuilder builder;
	Document doc;
	View v;
	ListView listView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 v = inflater.inflate(R.layout.tab_setaddr, container, false);
		edAddr1 = (EditText) v.findViewById(R.id.addrtab_ed_add1);
		loc = (TextView) v.findViewById(R.id.addrtab_tv_loc);
		btnAddr1 = (Button) v.findViewById(R.id.addrtab_btn_add1);
		listView = (ListView) v.findViewById(R.id.addrtab_listview);
		btnAddr1.setOnClickListener(this);

		client = new AsyncHttpClient();

		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addrtab_btn_add1:
			
			Log.v(ur.TAG, "edaddr :"+edAddr1.getText()); 
			
			
			if(edAddr1.getText().toString().equalsIgnoreCase("")){
				Toast.makeText(v.getContext(), "검색할 주소를 입력하세요", Toast.LENGTH_SHORT).show();
			}else{
				
				requestPost();
				
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
				@Deprecated
				public void onSuccess(String content) {
					Log.v(ur.TAG, "post success :" + content);

					super.onSuccess(content);
				}

				@Override
				public void onSuccess(int stat, Header[] header, byte[] binary) {
					try {
//						if(binary.length>204){
						InputStream is = null;
//						Log.v(ur.TAG,"binary size :"+binary.length);
						is = new ByteArrayInputStream(binary);

						// XML 파싱
						builder = new SAXBuilder();
						doc = builder.build(is);
//						Log.v(ur.TAG,"doc :"+doc);
						//itemlist하위에 우편번호와 주소값을 가짐
						
						Element itemlist;
							
						if(doc.getRootElement().getName().equals("post")){
						itemlist = doc.getRootElement().getChild("itemlist");
						List list = itemlist.getChildren();
						
						//검색결과가 여러개인 경우 반복하며 우편번호와 주소값을 뽑아낸다
						for(int i=0; i<list.size();i++){
							Element item = (Element)list.get(i);
							String address = item.getChildText("address");
							String postcd = item.getChildText("postcd");
							//address와 postcd 변수를 이용하여 자신에게 알맞는 형태로 사용하기
//							this.cbAddr.addItem(postcd+" | "+address);
							
							
						}
						}else if(doc.getRootElement().getName().equals("error")){
							itemlist = doc.getRootElement();
							
							String msg = itemlist.getChildText("message");
							Toast.makeText(v.getContext(), msg, Toast.LENGTH_SHORT).show();
							
							
						}
						
						
						
//						}else{
//							Toast.makeText(v.getContext(), "검색하실 주소를 다시 입력하세요.", Toast.LENGTH_SHORT).show();
//						}
						
						
						
						

					} catch (JDOMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					super.onSuccess(stat, header, binary);
				}

			});

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
