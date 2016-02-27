package kr.co.makeit.seoul.list;

import java.io.IOException;
import java.text.Collator;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import kr.co.makeit.seoul.db.DataBaseManager;
import kr.co.makeit.seoul.domain.Distance;
import kr.co.makeit.seoul.domain.Domain;
import kr.co.makeit.seoul.domain.TMtoGoogle;
import kr.co.makeit.seoul.info.InfoActivity_;
import kr.co.makeit.seoul.main.R;
import kr.co.makeit.seoul.map.MapActivity_;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_list)
public class ListActivity extends Activity implements OnItemClickListener{
	List<Domain> domainList = new ArrayList<Domain>();
	List<Domain> save_domain = new ArrayList<Domain>();
	List<Domain> toggle_domain;
	DataBaseManager manager;
	DomainAdapter adapter;
	TMtoGoogle toG = new TMtoGoogle();
	Distance dist = new Distance();
	double mylon;
	double mylat;
	int itemsPerPage = 10;
	boolean loadingMore = false;
	String str_list;
	

	
	TextView name_view, dong_view, distance_view;
	@ViewById
	ListView listView1;
	@ViewById
	ToggleButton toggle;

	@AfterViews
	void ui() {
		Intent getIntent = getIntent();
		str = getIntent.getExtras().getString("sel");
		if (str == null)
			str = "축구장";
		mylon = Double.parseDouble(getIntent.getExtras().getString("lon"));
		mylat = Double.parseDouble(getIntent.getExtras().getString("lat"));
		manager = DataBaseManager.getInstance(getApplicationContext());
		domainList = manager.getGymForWhereString("MINCLASSNM= '" + str + "' ORDER BY PLACENM ASC");
		
		
		for(int i=0; i<domainList.size(); i++){
			save_domain.add(domainList.get(i));
		}
		
		Collections.sort(save_domain,myComparator);
		
		change();
		
		
	}
	
	/**
	 * 토글에 따라 리스트 변화
	 * @author jinhyung
	 * @date 2014. 8. 24.
	 */
	@UiThread
	void change(){
		
		if (toggle.isChecked()) {
			
			toggle_domain = domainList;
			
		} else {
			
			toggle_domain = save_domain;
			
		}
		
		adapter = new DomainAdapter(getApplicationContext(), R.layout.row,
				toggle_domain);
		listView1.setAdapter(adapter);
		listView1.setOnItemClickListener(this);
		
	}
	
	@Click
	void toggle(View v) {
		change();
	}
	
	public Comparator<Domain> myComparator = new Comparator<Domain>() {

		public Collator collator = Collator.getInstance();
		@Override
		public int compare(Domain lhs, Domain rhs) {
			// TODO Auto-generated method stub
			
			int go = (int) dist.cal(mylat, mylon, lhs.getY(), lhs.getX());
			int away = (int) dist.cal(mylat, mylon, rhs.getY(), rhs.getX());
			String str_go = String.valueOf(go);
			String str_away = String.valueOf(away);
			
			int dis_go = str_go.getBytes().length;
			int dis_away = str_away.getBytes().length;
			
			switch(dis_go){
			case 1:
				str_go = "00000"+str_go;
				break;
			case 2:
				str_go = "0000"+str_go;
				break;
			case 3:
				str_go = "000"+str_go;
				break;
			case 4:
				str_go = "00"+str_go;
				break;
			case 5:
				str_go = "0"+str_go;
				break;
			case 6:
				break;
			}
			
			switch(dis_away){
			case 1:
				str_away = "00000"+str_away;
				break;
			case 2:
				str_away = "0000"+str_away;
				break;
			case 3:
				str_away = "000"+str_away;
				break;
			case 4:
				str_away = "00"+str_away;
				break;
			case 5:
				str_away = "0"+str_away;
				break;
			case 6:
				break;
			}
			
			
			
			return collator.compare(str_go, str_away);
		}
	};
	private String str;
	

	private class DomainAdapter extends ArrayAdapter<Domain> {
		private Domain dd;

		private List<Domain> dl;

		public DomainAdapter(Context context, int resource, List<Domain> objects) {
			super(context, resource, objects);
			this.dl = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			dd = dl.get(position);
			
			
			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.row, null);
			}

			if (dd != null) {
				
				name_view = (TextView) convertView.findViewById(R.id.name_view);
				dong_view = (TextView) convertView.findViewById(R.id.dong_view);
				distance_view = (TextView) convertView.findViewById(R.id.distance_view);

				name_view.setText(dd.getSVCNM());
				dong_view.setText(dd.getPLACENM());
				int row = (int)dist.cal(mylat, mylon,dd.getY(), dd.getX());
				double changed_row = (double)row/1000;
				DecimalFormat df = new DecimalFormat("##.#");
				
				distance_view.setText(df.format(changed_row) + "km");

			}
			return convertView;

		}

	}

	// 위도 경도 주소로 변환
	public String getAddress(double lat, double lng) {
		String address = null;
		Geocoder geocoder = new Geocoder(this, Locale.getDefault());
		List<Address> list = null;

		try {
			list = geocoder.getFromLocation(lat, lng, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (list == null) {
			return null;
		}

		if (list.size() > 0) {
			Address addr = list.get(0);
			address = "서울시" + " "
					+ addr.getLocality() + " " + addr.getThoroughfare();
		}

		return address;

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
		Intent intent = new Intent(getApplicationContext(),InfoActivity_.class);
		intent.putExtra("domain", toggle_domain.get(position));
		startActivity(intent);
		
	}
	
	
	@Click
	void bottomBtnLeft() {
		Intent i = new Intent(getApplicationContext(), MapActivity_.class);
		i.putExtra("sel", str);
		i.putExtra("lon", String.valueOf(mylon));
		i.putExtra("lat", String.valueOf(mylat));
		startActivity(i);
		finish();
	}
	
	
}
