package com.example.applock;

import com.example.applock.utils.MD5Utils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	private SharedPreferences sp;
	private EditText et_pwd,et_pwd2;
	private Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		init();	
	}
	private void init(){
		sp=getSharedPreferences("pwd", Activity.MODE_PRIVATE);
		String pwd=sp.getString("pwd",null);
		if(pwd==null){
			setContentView(R.layout.main_info);
			initReg();
		}else{
			initLog();
		}
	}
	private void initLog() {
		Intent intent=new Intent(getApplicationContext(), PwdActivity.class);
		intent.putExtra("packName", getPackageName());
		startActivity(intent);
		finish();		
	}
	private void initReg() {
		et_pwd=(EditText) findViewById(R.id.et_pwd);
		et_pwd2=(EditText) findViewById(R.id.et_pwd2);
		button=(Button) findViewById(R.id.bt_comit);
		button.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_comit:
			String pwd1=et_pwd.getText().toString();
			String pwd2=et_pwd2.getText().toString();			
			System.out.println(pwd1);
			System.out.println(pwd2+"....");
			if(pwd1.equals(pwd2)&&pwd1!=null){	
				try {
					Editor edit=sp.edit();
					edit.putString("pwd",MD5Utils.encode(pwd1));
					edit.commit();
					Toast.makeText(this, "创建成功",0).show();
					overridePendingTransition(R.anim.activity_in,R.anim.activity_out);
					Intent intent=new Intent(this,ListInfo.class);
					startActivity(intent);
					finish();
				} catch (Exception e) {
					Toast.makeText(this, "创建错误",0).show();
					clearEdit();
				}
				
			}else{
				Toast.makeText(this, "密码不一致",0).show();
				
			}
			break;

		default:
			break;
		}
		
	}
	private void clearEdit() {
		et_pwd.setText("");
		et_pwd2.setText("");
	}

}
