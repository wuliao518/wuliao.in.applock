package com.example.applock.domain;

import android.graphics.drawable.Drawable;

public class AppInfo {
	private Drawable icon;
	private String packName;
	private String lable;
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	public String getPackName() {
		return packName;
	}
	public void setPackName(String packName) {
		this.packName = packName;
	}
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	
	
	
}
