package com.example.applock;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.widget.AlphabetIndexer;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AppLockSplash extends Activity {
	private TextView version;
	private RelativeLayout rl_main;
	private RelativeLayout loading;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			startIntent();
			super.handleMessage(msg);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		AlphaAnimation aa=new AlphaAnimation(0.5f, 1.0f);
		aa.setDuration(1000);
		rl_main.startAnimation(aa);
		handler.sendEmptyMessageDelayed(0, 1000);

	}
	private void init() {
		version = (TextView) findViewById(R.id.tv_version);
		loading = (RelativeLayout) findViewById(R.id.rl_loading);
		rl_main=(RelativeLayout) findViewById(R.id.rl_main);
		version.setText(getVersion());
	}

	private String getVersion(){
		try {
			PackageInfo info=getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_UNINSTALLED_PACKAGES);
			return "°æ±¾ºÅ"+info.versionName;
		} catch (NameNotFoundException e) {
			return "°æ±¾ºÅ´íÎó";
		}	
	}
	private void startIntent(){
		Intent intent=new Intent(this,MainActivity.class);
		startActivity(intent);
		finish();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
