package kr.actus.ckck;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SendOrderActivity extends Activity implements OnClickListener{
Button btnFinish;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_send_order);
	    btnFinish = (Button)findViewById(R.id.send_btn_finish);
	   btnFinish.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.send_btn_finish:
			Toast.makeText(this, "�ֹ��Ϸ��ưŬ��", Toast.LENGTH_SHORT).show();
			
			break;
		}
		
	}

}