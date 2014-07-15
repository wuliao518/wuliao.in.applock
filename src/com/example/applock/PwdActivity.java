package com.example.applock;

import com.example.applock.service.WatchDog;
import com.example.applock.service.WatchDog.MyBinder;
import com.example.applock.utils.MD5Utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PwdActivity extends Activity implements OnClickListener{
	private MyBinder builder;
	private String packName;
	private MyConn conn;
	private EditText edit;
	private Button button;
	private SharedPreferences sp;
	private int i=0;
	private long start,end;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		packName=getIntent().getExtras().getString("packName");
		System.out.println(packName);
		sp=getSharedPreferences("pwd", Context.MODE_PRIVATE);
		setContentView(R.layout.confirm);
		conn=new MyConn();
		edit=(EditText) findViewById(R.id.et_confirm);
		button=(Button) findViewById(R.id.bt_confirm);
		button.setOnClickListener(this);
		Intent server=new Intent(this,WatchDog.class);
		bindService(server, conn , Context.BIND_AUTO_CREATE);
	}

	@Override
	public void onClick(View v) {
		try {
			String pwd=sp.getString("pwd", null);
			String pwd2=MD5Utils.encode(edit.getText().toString());
			if(pwd.equals(pwd2)){
				if(getPackageName().equals(packName)){
					Intent intent=new Intent(getApplicationContext(), ListInfo.class);
					overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
					startActivity(intent);
					finish();
				}else{
					overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
					finish();
					builder.stop(packName);
				}
			}else{
				edit.setText("");
				Toast.makeText(getApplicationContext(), "密码不正确", 0).show();
			}
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "发生错误", 0).show();
		}
	
	}
	private class MyConn implements ServiceConnection{
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			builder=(MyBinder) service;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			
			
		}
		
	}
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		unbindService(conn);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//start=System.currentTimeMillis();
		if(keyCode==KeyEvent.KEYCODE_BACK){
			i++;
			 Intent i= new Intent(Intent.ACTION_MAIN); 
			 i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			 i.addCategory(Intent.CATEGORY_HOME); 
			 startActivity(i);  
			return true;

		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	
	
	
	
	
}
