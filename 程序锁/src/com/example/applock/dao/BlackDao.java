package com.example.applock.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.applock.db.BlackDB;

public class BlackDao {
	private Context context;
	private SQLiteDatabase database;
	private BlackDB black;
	public BlackDao(Context context) {
		this.context = context;
		black=new BlackDB(context);
	}

	public void add(String str){
		database=black.getWritableDatabase();
		if(database.isOpen()){
			ContentValues values=new ContentValues();
			values.put("packName", str);
			database.insert("info", null, values);
			close();
		}
	}
	public boolean find(String str){
		database=black.getWritableDatabase();
		if(database.isOpen()){
			Cursor cursor=database.query("info", new String[]{"packName"},"packName=?",new String[]{str},null,null,null);
			if(cursor.moveToNext()){
				cursor.close();
				close();
				return true;
			}else{
				cursor.close();
				close();
				return false;
			}		
			
		}
		return false;
	}
	public boolean delete(String str){
		database=black.getWritableDatabase();
		if(database.isOpen()){
			if(database.delete("info", "packName=?", new String[]{str})==1){
				close();
				return true;
			}else{
				close();
				return false;
			}		
		}
		return false;
	}
	public List<String> getAll(){
		List<String> packs=new ArrayList<String>();
		database=black.getWritableDatabase();
		if(database.isOpen()){
			Cursor cursor=database.query("info", new String[]{"packName"}, null, null, null, null, null);
			while(cursor.moveToNext()){
				packs.add(cursor.getString(0));
			}
			cursor.close();
			close();
			return packs;		
		}

		return null;
		
		
	}
	private void close(){
		database.close();
		black.close();
	}
}
