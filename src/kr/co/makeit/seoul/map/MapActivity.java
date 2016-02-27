package kr.co.makeit.seoul.map;

import java.util.List;

import kr.co.makeit.seoul.db.DataBaseManager;
import kr.co.makeit.seoul.domain.Domain;
import kr.co.makeit.seoul.info.InfoActivity_;
import kr.co.makeit.seoul.list.ListActivity_;
import kr.co.makeit.seoul.main.R;
import kr.co.makeit.seoul.rest.MakeitConn;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.NoTitle;
import com.googlecode.androidannotations.annotations.UiThread;

@NoTitle
@EActivity(R.layout.activity_map)
public class MapActivity extends FragmentActivity implements
		OnMarkerClickListener, OnInfoWindowClickListener,
		OnMyLocationButtonClickListener, OnLocationChangedListener, OnMapLongClickListener {

	DataBaseManager manager;
	GoogleMap map;
	MarkerOptions marker = new MarkerOptions();
	String locationProvider;
	LocationManager locationManager;
	Geocoder geocoder;
	Location mylocation;
	String str;
	List<Domain> domain;
	String lat;
	String lon;
	

	@Bean MakeitConn con;

	@AfterViews
	void ui() {
		mapView();


	}
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		uiControl(domain);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	private void mapView() {
		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.mapexam)).getMap();
		map.setMyLocationEnabled(true);
		map.moveCamera(CameraUpdateFactory.zoomTo(15));
		map.setOnMyLocationButtonClickListener(this);
		map.setOnMarkerClickListener(this);
		map.setOnInfoWindowClickListener(this);
		map.setOnMapLongClickListener(this);
		
		Intent intent = getIntent();
		str = intent.getExtras().getString("sel");
		lon = intent.getExtras().getString("lon");
		lat = intent.getExtras().getString("lat");
		
		if (str == null)
			str = "축구장";
		Log.i("check", str);
		
		manager = DataBaseManager.getInstance(getApplicationContext());
		domain = manager.getGymForWhereString("MINCLASSNM= '"+str+"'");
		

//		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, (LocationListener) this);
//		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
//		geocoder = new Geocoder(this,Locale.KOREAN);
//		
		
		
	}
	
	@UiThread
	void uiControl(List<Domain> domain) {
		for (int n = 0; n < domain.size(); n++) {
//			GeoPoint geoPoint = new GeoPoint(domain.get(n).getX(), domain
//					.get(n).getY());
//			GeoPoint changedPoint = GeoTrans.convert(GeoTrans.TM, GeoTrans.GEO,
//					geoPoint);
			LatLng latlng = new LatLng(domain.get(n).getY(), domain.get(n).getX());
			marker.position(latlng);
			marker.title(domain.get(n).getSVCNM());
			marker.icon(BitmapDescriptorFactory
					.fromResource(R.drawable.map_point2));
			marker.snippet("대화창 클릭");
			map.addMarker(marker);
			
			

		}
		//내위치로 시작 설정
		LatLng myLatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
		CameraPosition cp = CameraPosition.builder().target(myLatLng).zoom(15).build();
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
	}
	
	@Override
	public boolean onMyLocationButtonClick() {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub
		int i = 0;
		for (int n=0; n<domain.size(); n++){
			if(marker.getTitle().equals(domain.get(n).getSVCNM())){
				i=n;
				break;
			}
		}
		final int a = i;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(domain.get(a).getSVCID())
		.setMessage("대분류명 : "+ domain.get(a).getMAXCLASSNM()+
				"\n소분류명 : " + domain.get(a).getMINCLASSNM()+
				"\n서비스상 : " + domain.get(a).getSVCNM()+
				"\n결제방법 : " + domain.get(a).getPAYATNM()+
				"\n장소명 : " + domain.get(a).getUSETGTINFO()+
				"\n바로가기 : " + domain.get(a).getSVCURL()).
				setCancelable(false)
				.setPositiveButton("상세 정보 보기", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getApplicationContext(),InfoActivity_.class);
						intent.putExtra("domain", domain.get(a));
						startActivity(intent);
					}
				})
				.setNegativeButton("돌아가기", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
		AlertDialog arlert = builder.create();
		arlert.show();
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		mylocation = location;
	}

	@Override
	public void onMapLongClick(LatLng latlng) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), latlng.latitude+ "    " + latlng.longitude, 1000).show();
	}
	@Click
	void bottomBtnRight() {
		Intent i = new Intent(getApplicationContext(), ListActivity_.class);
		i.putExtra("sel", str);
		i.putExtra("lon", lon);
		i.putExtra("lat", lat);
		startActivity(i);
		finish();
	}

}
