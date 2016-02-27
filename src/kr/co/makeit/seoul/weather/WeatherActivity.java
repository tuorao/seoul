package kr.co.makeit.seoul.weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import kr.co.makeit.seoul.domain.WTdomain;
import kr.co.makeit.seoul.main.R;
import kr.co.makeit.seoul.rest.MakeitConn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_weather2)
public class WeatherActivity extends Activity {
	@ViewById
	TextView placename,speed_text,temp_text,humi_text,rain_text,windir_text,solar_text,sunshine_text;
	@ViewById
	Button sisulchange_btn;
	@ViewById
	ImageView image1;
	@Bean MakeitConn con;
	WTdomain wTdomain = new WTdomain();
	String urlStr = "http://openapi.seoul.go.kr:8088/436f50416368616e31313459444c5567/json/RealtimeWeatherStation/1/18/";
	String goo_change;
	private String goo;

	
	@AfterViews
	void ui(){
		Intent get = getIntent();
		goo = get.getExtras().getString("goo");
		goo_change = goo.substring(0,goo.length()-1);
		Log.i("qsssssibal", goo_change);
		
		ssibal(wTdomain, urlStr, goo_change);
//		uiCon(wTdomain);
		
		
	}
	@Click
	void sisulchange_btn(){
		finish();
	}
	

	
	@UiThread
	void uiCon(WTdomain wTdomain){
		
		speed_text.setText(wTdomain.getSAWS_WS_AVG()+" m/s");
		temp_text.setText(wTdomain.getSAWS_TA_AVG()+" 도");
		humi_text.setText(wTdomain.getSAWS_HD()+" %");
		windir_text.setText(wTdomain.getNAME());
		rain_text.setText(wTdomain.getSAWS_RN_SUM()+" mm");
		solar_text.setText(wTdomain.getSAWS_SOLAR());
		sunshine_text.setText(wTdomain.getSAWS_SHINE());
		placename.setText(goo);
		
		if (wTdomain.getSAWS_RN_SUM().equals("0.0") ) {
			BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.sunny);
			image1.setImageBitmap(drawable.getBitmap());
		} else {
			BitmapDrawable drawable1 = (BitmapDrawable) getResources().getDrawable(R.drawable.rain);
			image1.setImageBitmap(drawable1.getBitmap());
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
					String windir = j.getString("NAME" );
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
			Log.i("ssiba", wTdomain.toString());
			uiCon(wTdomain);
		
	}

}
