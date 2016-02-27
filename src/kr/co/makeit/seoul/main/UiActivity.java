package kr.co.makeit.seoul.main;

import java.util.List;

import kr.co.makeit.seoul.list.ListActivity_;
import kr.co.makeit.seoul.map.MapActivity_;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RawRes;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_ui)
public class UiActivity extends Activity implements LocationListener {

	LocationManager locationManager = null;
	Location mylocation;
	String provider = null;
	String str;
	@ViewById
	Button btn_map, btn_list;
	@ViewById
	RadioGroup radioGroup;

	@AfterViews
	void ui() {
		setUi();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		locationManager.requestLocationUpdates(provider, 500, 1, this);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		locationManager.removeUpdates(this);
	}
	

	private void setUi() {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		// chkGpsService();

		locationManager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria c = new Criteria();
		provider = locationManager.getBestProvider(c, true);
		
		if(provider == null || locationManager.isProviderEnabled(provider)){
			List<String> list = locationManager.getAllProviders();
			for(int i=0; i<list.size(); i++){
				String temp = list.get(i);
				
				if(locationManager.isProviderEnabled(temp)){
					provider = temp;
					break;
				}
			}
		}
		
		Location location = locationManager.getLastKnownLocation(provider);
		
		if(location == null) {
			Toast.makeText(this, "사용가능한 위치 정보 제공자가 음슴", Toast.LENGTH_LONG).show();
			
		} else {
			onLocationChanged(location);
		}
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton rb = (RadioButton) findViewById(checkedId);
				str = rb.getText().toString();
			}
		});
	}
	
	

	@Click({ R.id.btn_map, R.id.btn_list })
	void click(View v) {

		switch (v.getId()) {
		case R.id.btn_map:
			if (chkGpsService() == false) {
				chkGpsService();
			} else {

				Intent intent = new Intent(getApplicationContext(),
						MapActivity_.class);
				intent.putExtra("sel", str);
				intent.putExtra("lat", String.valueOf(mylocation.getLatitude()));
				intent.putExtra("lon", String.valueOf(mylocation.getLongitude()));
				startActivity(intent);
			}

			break;
		case R.id.btn_list:

			Intent intent = new Intent(getApplicationContext(),ListActivity_.class);
			intent.putExtra("sel", str);
			intent.putExtra("lat", String.valueOf(mylocation.getLatitude()));
			intent.putExtra("lon", String.valueOf(mylocation.getLongitude()));
			startActivity(intent);
			break;
		}
	}

	@SuppressWarnings("deprecation")
	private boolean chkGpsService() {

		String gps = android.provider.Settings.Secure.getString(
				getContentResolver(),
				android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);


		if (!(gps.matches(".*gps.*") && gps.matches(".*network.*"))) {

			// GPS OFF 일때 Dialog 표시
			AlertDialog.Builder gsDialog = new AlertDialog.Builder(this);
			gsDialog.setTitle("위치 서비스 설정");
			gsDialog.setMessage("무선 네트워크 사용, GPS 위성 사용을 모두 체크하셔야 정확한 위치 서비스가 가능합니다.\n위치 서비스 기능을 설정하시겠습니까?");
			gsDialog.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// GPS설정 화면으로 이동
							Intent intent = new Intent(
									android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							intent.addCategory(Intent.CATEGORY_DEFAULT);
							startActivity(intent);
						}
					})
					.setNegativeButton("NO",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									return;
								}
							}).create().show();
			return false;

		} else {
			return true;

		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		mylocation = location;
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	


}
