package com.example.applock.engine;

import java.util.ArrayList;
import java.util.List;

import com.example.applock.domain.AppInfo;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

public class InfoServer {
	private Context context;
	private PackageManager pm;
	private List<AppInfo> appInfos;
	private AppInfo myApp;
	public InfoServer(Context context) {
		this.context = context;
		pm=context.getPackageManager();
	}

	public List<AppInfo> getAllApps(){
		
		appInfos=new ArrayList<AppInfo>();
		Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<Drawable > mList = new ArrayList<Drawable>() ;
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        for(ResolveInfo info : infos){
        	myApp = new AppInfo();
			String packName=info.activityInfo.packageName;
			System.out.println(packName);
			Drawable icon=info.loadIcon(pm);
			String lable=(String) info.loadLabel(pm);
			myApp.setIcon(icon);
			myApp.setLable(lable);
			myApp.setPackName(packName);
			appInfos.add(myApp);
			myApp=null;	           
        }
		return appInfos;
	}
}
