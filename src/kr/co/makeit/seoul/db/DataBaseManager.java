package kr.co.makeit.seoul.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kr.co.makeit.seoul.domain.Domain;
import kr.co.makeit.seoul.map.GeoPoint;
import kr.co.makeit.seoul.map.GeoTrans;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;

/**
 * 데이터 베이스를 쉽게 쓰기위해 작성한 클래스
 * @since 2014. 5. 30.
 * @version 1.0
 * @author jinhyung
 */
public class DataBaseManager {
	private static DataBaseManager instance;
	private static final String DB_TABLE = "gym";
	private SQLiteDatabase db;
	private DataBaseHelper helper;
	private Context mContext;
	
	
	//싱글톤 패턴
	public static DataBaseManager getInstance(Context context){
		if(instance == null) instance = new DataBaseManager(context);
		return instance;
	}
	private DataBaseManager(Context context) {
		helper = new DataBaseHelper(context);
		mContext = context;
	}
	//싱글톤 패턴 끝
	
	/**
	 * 체육시설 정보 추가
	 * @param domain 체육시설 도메인
	 */
	public void addGym(Domain domain){
		db = helper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("GUBUN", domain.getGUBUN());
		cv.put("SVCID", domain.getSVCID());
		cv.put("MAXCLASSNM", domain.getMAXCLASSNM());
		cv.put("MINCLASSNM", domain.getMINCLASSNM());
		cv.put("SVCSTATNM", domain.getSVCSTATNM());
		cv.put("SVCNM", domain.getSVCNM());
		cv.put("PAYATNM", domain.getPAYATNM());
		cv.put("PLACENM", domain.getPLACENM());
		cv.put("USETGTINFO", domain.getUSETGTINFO());
		cv.put("SVCURL", domain.getSVCURL());

		GeoPoint geoPoint = new GeoPoint(domain.getX(), domain.getY());
		geoPoint = GeoTrans.convert(GeoTrans.TM, GeoTrans.GEO,	geoPoint);
		
		cv.put("X", geoPoint.getX());
		cv.put("Y", geoPoint.getY());
		
		Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
		List<Address> list = null;
		try {
			list = geocoder.getFromLocation(geoPoint.getY(), geoPoint.getX(), 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Address addr = list.get(0);
		
		
		cv.put("ADR1", "서울시 " + addr.getLocality() + " " + addr.getThoroughfare());
		cv.put("ADR2", addr.getLocality());
		
		
		db.insert(DB_TABLE, null, cv);
		db.close();
	}
	public void addGymForList(List<Domain> domainList) {
		db = helper.getWritableDatabase();
		db.beginTransaction();
		Domain domain = null;
//		Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
		for (int i = 0; i < domainList.size(); i++) {
			domain = domainList.get(i);
			
			ContentValues cv = new ContentValues();
			cv.put("GUBUN", domain.getGUBUN());
			cv.put("SVCID", domain.getSVCID());
			cv.put("MAXCLASSNM", domain.getMAXCLASSNM());
			cv.put("MINCLASSNM", domain.getMINCLASSNM());
			cv.put("SVCSTATNM", domain.getSVCSTATNM());
			cv.put("SVCNM", domain.getSVCNM());
			cv.put("PAYATNM", domain.getPAYATNM());
			cv.put("PLACENM", domain.getPLACENM());
			cv.put("USETGTINFO", domain.getUSETGTINFO());
			cv.put("SVCURL", domain.getSVCURL());

			
			
			if(domain.getX() == 0d || domain.getY() == 0d){
				continue;
			}
			
			GeoPoint geoPoint = new GeoPoint(domain.getX(), domain.getY());
			geoPoint = GeoTrans.convert(GeoTrans.TM, GeoTrans.GEO,	geoPoint);
			
			cv.put("X", geoPoint.getX());
			cv.put("Y", geoPoint.getY());
			
			
			/* 줫같은 위치받아오기
			List<Address> list = null;
			try {
				Log.v("geo", geoPoint.getY() +""+ geoPoint.getX());
				list = geocoder.getFromLocation(geoPoint.getY(), geoPoint.getX(), 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Address addr = list.get(0);
			
			
			cv.put("ADR1", "서울시 " + addr.getLocality() + " " + addr.getThoroughfare());
			cv.put("ADR2", addr.getLocality());
			*/
			db.insert(DB_TABLE, null, cv);
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
	}
	public Domain getGym(int gid) {
		Domain domain = new Domain();
		db = helper.getReadableDatabase();
		Cursor cursor = db.query(DB_TABLE, null, "gid = " + gid, null, null, null, "gid DESC");
		if(cursor.moveToFirst()) {
			domain.setGUBUN(cursor.getString(1));
			domain.setSVCID(cursor.getString(2));
			domain.setMAXCLASSNM(cursor.getString(3));
			domain.setMINCLASSNM(cursor.getString(4));
			domain.setSVCSTATNM(cursor.getString(5));
			domain.setSVCNM(cursor.getString(6));
			domain.setPAYATNM(cursor.getString(7));
			domain.setPLACENM(cursor.getString(8));
			domain.setUSETGTINFO(cursor.getString(9));
			domain.setSVCURL(cursor.getString(10));
			domain.setX(cursor.getString(11));
			domain.setY(cursor.getString(12));
			domain.setADR1(cursor.getString(13));
			domain.setADR2(cursor.getString(14));
		}
		cursor.close();
		db.close();
		return domain;
	}
	/**
	 * 체육관 시설을 웨어절로 찾아오기
	 * @author jinhyung
	 * @date 2014. 8. 22.
	 * @param whereString 웨어절 문구
	 * @return 체육관 목록 <pre> 예시 ["GUBUN = 어쩌고 AND SVCID = 어쩌고"]
	 */
	public List<Domain> getGymForWhereString(String whereString) {
		Domain domain;
		List<Domain> list = new ArrayList<Domain>();
		db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + DB_TABLE + " WHERE " + whereString, null);
		while(cursor.moveToNext()) {
			domain = new Domain();
			domain.setGUBUN(cursor.getString(1));
			domain.setSVCID(cursor.getString(2));
			domain.setMAXCLASSNM(cursor.getString(3));
			domain.setMINCLASSNM(cursor.getString(4));
			domain.setSVCSTATNM(cursor.getString(5));
			domain.setSVCNM(cursor.getString(6));
			domain.setPAYATNM(cursor.getString(7));
			domain.setPLACENM(cursor.getString(8));
			domain.setUSETGTINFO(cursor.getString(9));
			domain.setSVCURL(cursor.getString(10));
			domain.setX(cursor.getString(11));
			domain.setY(cursor.getString(12));
			domain.setADR1(cursor.getString(13));
			domain.setADR2(cursor.getString(14));
			list.add(domain);
		}
		cursor.close();
		db.close();
		return list;
	}
	/**
	 * 체육관 시설 정보를 지우는 메서드
	 * @author jinhyung
	 * @date 2014. 8. 22.
	 * @param gid 고유아이디
	 */
	public void delGym(int gid) {
		db = helper.getWritableDatabase();
		db.execSQL("DELETE FROM " + DB_TABLE + "WHERE gid = " + gid);
		db.close();
	}
}
