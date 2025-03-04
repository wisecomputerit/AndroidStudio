package com.ch12;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
	}

	public void onClick(View view) {
		// 取得 Fragment 管理物件
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		switch (view.getId()) {
		case R.id.lotto:
			ft.add(R.id.fragment_addin_linearlayout, new LottoFragment(),
					"f_lotto");
			ft.addToBackStack(null);
			break;
		case R.id.get:
			Fragment fragment = fm.findFragmentByTag("f_lotto");
			if (fragment != null) {
				TextView lotto_num = (TextView) fragment.getView()
						.findViewById(R.id.lotto_num);
				Toast.makeText(context, lotto_num.getText().toString(),
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.pop:
			fm.popBackStack();
			break;
		case R.id.clear:
			fm.popBackStack(null, 
					FragmentManager.POP_BACK_STACK_INCLUSIVE);
			break;
		}
		// 提交
		ft.commit();
	}
}
