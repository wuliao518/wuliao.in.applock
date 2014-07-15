package com.example.applock.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.applock.PwdActivity;
import com.example.applock.dao.BlackDao;
import com.example.applock.receiver.LockReceiver;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;

public class WatchDog extends Service {
	private BlackDao dao;
	private List<String> apps;
	private Timer timer;
	private TimerTask task;
	private MyBinder binder;
	private List<String> temps=new ArrayList<String>();
	private boolean flag;
	private BroadcastReceiver mLockReceiver;
	private IntentFilter filter;
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
	public class MyBinder extends Binder{
		public void stop(String str){
				temps.add(str);
		}
		public void start(String str){
			if(temps.contains(str)){
				temps.remove(str);
			}
	}
		public void  clear(){
			temps.clear();
		}
	}
	@Override
	public void onCreate() {
		flag=true;
		binder=new MyBinder();
		mLockReceiver=new LockReceiver();
		filter=new IntentFilter(Intent.ACTION_SCREEN_OFF);
		dao=new BlackDao(getApplicationContext());		
		new Thread(){
			@Override
			public void run() {
				while(flag){
					registerReceiver(mLockReceiver, filter);
					apps=dao.getAll();
					ActivityManager am=(ActivityManager) getSystemService(ACTIVITY_SERVICE);
					List<RunningTaskInfo> tasks=am.getRunningTasks(1);
					String packName =tasks.get(0).topActivity.getPackageName();	
					if(apps.contains(packName)){
						if(temps.contains(packName)){
							continue;
						}
						Intent intent=new Intent(getApplicationContext(), PwdActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.putExtra("packName", packName);
						startActivity(intent);	
					}
					try {
						sleep(500);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
				}				
			}		
		}.start();
		scheduleClear();
		super.onCreate();
			
	}

	private void scheduleClear() {
		task=new TimerTask() {		
			@Override
			public void run() {
				binder.clear();
			}
		};
		timer=new Timer();
		timer.scheduleAtFixedRate(task, 0, 500*60);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		flag=false;
		unregisterReceiver(mLockReceiver);
	}
	 
	
	
	

}
