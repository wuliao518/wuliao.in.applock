package com.example.applock;

import java.util.List;

import com.example.applock.dao.BlackDao;
import com.example.applock.domain.AppInfo;
import com.example.applock.engine.InfoServer;
import com.example.applock.receiver.LockReceiver;
import com.example.applock.service.WatchDog;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class ListInfo extends Activity implements OnClickListener{
	private SharedPreferences  sp;
	private ListView lv_info;
	private PackageManager pm;
	private TextView tv_show,mTVHaha;
	private List<AppInfo> infos;
	private BlackDao dao;
	private List<String> temps;
	private SlidingMenu mSlidingMenu;
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {		
			super.handleMessage(msg);
			lv_info.setAdapter(new MyAdapter());		
		}		
	};
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.list_info);
		
		new Thread(){
			@Override
			public void run() {
				InfoServer infoServer=new InfoServer(getApplicationContext());
				infos=infoServer.getAllApps();
				handler.sendEmptyMessage(0);
			}
			
		}.start();
		Intent server=new Intent(getApplicationContext(), WatchDog.class);
		startService(server);
		init();	
		//showDialog();
		lv_info.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TranslateAnimation ta = new TranslateAnimation(
						Animation.RELATIVE_TO_SELF, 0.0f,
						Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.0f,
						Animation.RELATIVE_TO_SELF, 0.0f);
				ta.setDuration(500);
				final ImageView image=(ImageView) view.findViewById(R.id.imageView2);
				final String packNmae=infos.get(position).getPackName();			
				view.startAnimation(ta);
				ta.setAnimationListener(new AnimationListener() {					
					@Override
					public void onAnimationStart(Animation animation) {
						
					}				
					@Override
					public void onAnimationRepeat(Animation animation) {
						
					}					
					@Override
					public void onAnimationEnd(Animation animation) {
						if(dao.find(packNmae)){
							dao.delete(packNmae);
							temps.remove(packNmae);
							image.setBackgroundResource(R.drawable.unlock);
						}else{
							dao.add(packNmae);
							temps.add(packNmae);
							image.setBackgroundResource(R.drawable.lock);
						}
					}
				});
				
			}
		});	
		
	}

	private void init() {
		lv_info=(ListView) findViewById(R.id.lv_info);
		dao=new BlackDao(getApplicationContext());
		mSlidingMenu=new SlidingMenu(this);
		mSlidingMenu.setMode(SlidingMenu.LEFT);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		mSlidingMenu.setFadeDegree(0.35f);
		mSlidingMenu.attachToActivity(ListInfo.this, SlidingMenu.SLIDING_CONTENT);
		mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		mSlidingMenu.setMenu(R.layout.leftmenu);
		mTVHaha=(TextView) findViewById(R.id.tv_haha);
		temps=dao.getAll();
		tv_show=(TextView) findViewById(R.id.tv_show_menu);
		tv_show.setOnClickListener(this);
		mTVHaha.setOnClickListener(this);
	}
	private void showDialog() {
		
		final AlertDialog.Builder builder=new AlertDialog.Builder(this);
		LayoutInflater inflate=LayoutInflater.from(getApplicationContext());
		View view=inflate.inflate(R.layout.dialog, null);
		builder.setView(view);
		builder.setPositiveButton("确定",new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				
			}

		});
		builder.setNegativeButton("取消",new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
							
			}

		});
		
		builder.show();
	}
	class MyAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			return infos.size();
		}
		@Override
		public Object getItem(int position) {
			return infos.get(position);
		}
		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view=convertView;
			if(view==null){
				view=View.inflate(getApplicationContext(), R.layout.item_info, null);
			}
			TextView text=(TextView) view.findViewById(R.id.tv_item);
			ImageView image1=(ImageView) view.findViewById(R.id.imageView1);
			ImageView image2=(ImageView) view.findViewById(R.id.imageView2);
			if(temps.contains(infos.get(position).getPackName())){
				image2.setBackgroundResource(R.drawable.lock);
			}else{
				image2.setBackgroundResource(R.drawable.unlock);
			}
			text.setText(infos.get(position).getLable());
			image1.setBackgroundDrawable(infos.get(position).getIcon());
			return view;
		}
		
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.tv_show_menu:
				mSlidingMenu.toggle(true);
				break;
			case R.id.tv_haha:
				mSlidingMenu.toggle(true);
				break;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
