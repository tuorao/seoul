package kr.co.makeit.seoul.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLite 헬퍼 클래스
 * SQLite의 버전관리를 위한 클래스
 * @since 2014. 5. 29.
 * @version 1.0
 * @author jinhyung
 */
public class DataBaseHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "gymposition.db";		//사용할 디비명
	private static final int DB_VERSION = 1;					//현재 버전
	private static final String DB_TABLE = "gym";
	private static final String ONCREATE_DB = "CREATE TABLE " + DB_TABLE + "(gid INTEGER PRIMARY KEY, GUBUN TEXT, SVCID TEXT, MAXCLASSNM TEXT, MINCLASSNM TEXT, SVCSTATNM TEXT, SVCNM TEXT, PAYATNM TEXT, PLACENM TEXT, USETGTINFO TEXT, SVCURL TEXT, X TEXT, Y TEXT, ADR1 TEXT, ADR2 TEXT)";

	/**
	 * 생성자에서 Context만 넘겨주면 디비명과 버전은 알아서 생성해줌
	 * @param context 컨텍스트
	 */
	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(ONCREATE_DB);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
		onCreate(db);

	}
	

}
