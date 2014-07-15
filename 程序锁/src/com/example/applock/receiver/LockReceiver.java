package com.example.applock.receiver;

import com.example.applock.service.WatchDog;
import com.example.applock.service.WatchDog.MyBinder;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.sax.StartElementListener;

public class LockReceiver extends BroadcastReceiver {
	private MyBinder binder;
	private MyConn conn;
	@Override
	public void onReceive(Context context, Intent intent) {
		String action=intent.getAction();
		Intent server=new Intent(context,WatchDog.class);
		 if (Intent.ACTION_SCREEN_ON.equals(action)) { // 开屏
			 System.out.println("开平");
         } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { // 锁屏
        	System.out.println("锁屏");
            context.bindService(server, conn, Context.MODE_PRIVATE);
            binder.clear();
         } else if (Intent.ACTION_BOOT_COMPLETED.equals(action)) { // 开机
            context.startService(server);
         }
	
	}
	private class MyConn implements ServiceConnection{
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder=(MyBinder) service;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			
			
		}
		
	}
}


















