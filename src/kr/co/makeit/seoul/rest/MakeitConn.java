package kr.co.makeit.seoul.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import kr.co.makeit.seoul.db.DataBaseManager;
import kr.co.makeit.seoul.domain.Domain;
import kr.co.makeit.seoul.domain.WTdomain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

@EBean
public class MakeitConn {
	@RootContext
	Context context;
	DataBaseManager manager;

	@Background
	public void parse(ArrayList<Domain> domainList, String strUrl) {
		manager = DataBaseManager.getInstance(context);

		URL url;
		BufferedReader reader;
		String str = "", result = "";
		try {
			url = new URL(strUrl);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			while ((str = reader.readLine()) != null)
				result += str;
			JSONObject jsonObject = new JSONObject(result);
			JSONObject jsonObject2 = jsonObject
					.getJSONObject("ListPublicReservationSport");
			JSONArray jsonArray = jsonObject2.getJSONArray("row");
			

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				String GUBUN = obj.getString("GUBUN");
				String SVCID = obj.getString("SVCID");
				String MAXCLASSNM = obj.getString("MAXCLASSNM");
				String MINCLASSNM = obj.getString("MINCLASSNM");
				String SVCSTATNM = obj.getString("SVCSTATNM");
				String SVCNM = obj.getString("SVCNM");
				String PAYATNM = obj.getString("PAYATNM");
				String PLACENM = obj.getString("PLACENM");
				String USETGTINFO = obj.getString("USETGTINFO");
				String SVCURL = obj.getString("SVCURL");
				String X = obj.getString("X");
				String Y = obj.getString("Y");
				if (X == null && Y == null) {continue;}

				Domain d = new Domain(GUBUN, SVCID, MAXCLASSNM, MINCLASSNM,
						SVCSTATNM, SVCNM, PAYATNM, PLACENM, USETGTINFO, SVCURL,
						X, Y);
				domainList.add(d);
//				manager.addGym(d);

			}
			manager.addGymForList(domainList);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException er) {
			// TODO Auto-generated catch block
			er.printStackTrace();
		} catch (JSONException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

	}
	
	@Background
	public void ssibal(WTdomain wTdomain,String urlStr,String goo){
		URL url;
		BufferedReader reader;
		
		String str = "", result = "";
		
			try {
				url = new URL(urlStr+goo);
				reader = new BufferedReader(new InputStreamReader(url.openStream()));
				while ((str = reader.readLine()) != null)
					result += str;
				
				
				JSONObject jsonObject = new JSONObject(result);
				try {
					String code = jsonObject.getString("CODE");
				} catch (JSONException e) {
					goo = "용산";
					
					url = new URL(urlStr+goo);
					reader = new BufferedReader(new InputStreamReader(url.openStream()));
					while ((str = reader.readLine()) != null)
						result += str;
				}
				
				
				
					
					
				
				
				
				JSONObject jsonObject2 = jsonObject
						.getJSONObject("RealtimeWeatherStation");
				JSONArray jsonArray = jsonObject2.getJSONArray("row");
				for(int i=0; i<jsonArray.length(); i++){
					JSONObject j = jsonArray.getJSONObject(0);
					
					
					String time = j.getString("SAWS_OBS_TM");
					String place = j.getString("STN_NM");
					String temp = j.getString("SAWS_TA_AVG");
					String humi = j.getString("SAWS_HD");
					String windir = j.getString("NAME");
					String speed = j.getString("SAWS_WS_AVG");
					String rain = j.getString("SAWS_RN_SUM");
					String solar = j.getString("SAWS_SOLAR");
					String sunshine = j.getString("SAWS_SHINE");
					
					wTdomain = new WTdomain(time, place, temp, humi, windir, speed, rain, solar, sunshine);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
