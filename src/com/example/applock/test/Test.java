package com.example.applock.test;

import java.util.List;

import com.example.applock.dao.BlackDao;

import android.test.AndroidTestCase;

public class Test extends AndroidTestCase {
	public void testAdd(){
		BlackDao dao=new BlackDao(getContext());
		dao.add("com.app.wuliao");
	}
	public void testFind(){
		BlackDao dao=new BlackDao(getContext());
		if(dao.find("com.app.wuliao")){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
	}
	public void testAll(){
		BlackDao dao=new BlackDao(getContext());
		List<String> ss=dao.getAll();
		for(String s : ss){
			System.out.println(s);
		}
	}

}