package kr.co.makeit.seoul.main;

import java.util.ArrayList;

import kr.co.makeit.seoul.db.DataBaseHelper;
import kr.co.makeit.seoul.domain.Domain;
import kr.co.makeit.seoul.rest.MakeitConn;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.NoTitle;

@NoTitle
@EActivity(R.layout.activity_main)
public class MainActivity extends Activity{
	private SQLiteDatabase db;
	private DataBaseHelper helper;

	String strUrl = "http://openAPI.seoul.go.kr:8088/436f50416368616e31313459444c5567/json/ListPublicReservationSport/1/210/";
	
	@Bean MakeitConn con;
	ArrayList<Domain> domain = new ArrayList<Domain>();

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		//디비 업그레이드
		helper = new DataBaseHelper(getApplicationContext());
		db = helper.getWritableDatabase();
		helper.onUpgrade(db, 1, 1);
		//디비 업그레이드 종료
		
		con.parse(domain, strUrl);

	}
	
	@AfterViews
	void ui(){
		gotoMain();
		
	}
	@Background
	void gotoMain(){
		try {
			Thread.sleep(2000);
			startActivity(new Intent(this, UiActivity_.class));
			finish();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	
}
