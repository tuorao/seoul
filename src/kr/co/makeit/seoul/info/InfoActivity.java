package kr.co.makeit.seoul.info;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import kr.co.makeit.seoul.domain.Domain;
import kr.co.makeit.seoul.main.R;
import kr.co.makeit.seoul.weather.WeatherActivity_;
import android.R.drawable;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_info)
public class InfoActivity extends Activity {

	Domain domain;
	@ViewById
	TextView textView1, textView2, textView3, textView4;
	@ViewById
	ImageView imageView1;
	@ViewById
	Button button1;
	private HashMap<String, String> map;

	@AfterViews
	void view() {
		setImg();

		Intent intent = getIntent();
		domain = (Domain) intent.getExtras().getSerializable("domain");
		textView1.setText("대분류명 : " + domain.getMAXCLASSNM() + "\n소분류명 : "
				+ domain.getMINCLASSNM() + "\n서비스상 : " + domain.getSVCNM()
				+ "\n결제방법 : " + domain.getPAYATNM() + "\n장소명 : "
				+ domain.getUSETGTINFO());
		textView4.setText(Html.fromHtml("<a href = \"" + domain.getSVCURL()
				+ "\"> 모바일 웹 사이트 바로가기"));
		textView4.setMovementMethod(LinkMovementMethod.getInstance());
		textView2.setText(domain.getPLACENM());
		textView3.setText(domain.getSVCNM());
		imageView1.setImageBitmap(GetImageFromURL(domain));
		

		Log.i("ssibaL", map.get(domain.getSVCID()));
	}

	private Bitmap GetImageFromURL(Domain domain) {
		Bitmap imgBitmap = null;
		BufferedInputStream bis = null;
		
		try {
			
			URL url = new URL(map.get(domain.getSVCID()));
			URLConnection conn = url.openConnection();
			conn.connect();
			
			int nSize = conn.getContentLength();
			bis = new BufferedInputStream(conn.getInputStream(),nSize);
			imgBitmap = BitmapFactory.decodeStream(bis);

			
			bis.close();

		} catch(Exception e){
			BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.main_icon);
			imgBitmap = drawable.getBitmap();
			return imgBitmap;
		}
		return imgBitmap;
		
	}

	@Click(R.id.button1)
	void click(View v) {
		switch (v.getId()) {
		case R.id.button1:
			Intent putIntent = new Intent(getApplicationContext(),
					WeatherActivity_.class);
			putIntent.putExtra("goo", seePalJHWAddress());
			startActivity(putIntent);
			finish();
		}
	}

	/**
	 * 구 주소를 받아오는 메서드 시팔전현우어드레스
	 * 
	 * @return
	 */
	private String seePalJHWAddress() {
		Geocoder geocoder = new Geocoder(getApplication(), Locale.getDefault());
		List<Address> list = null;
		try {
			list = geocoder.getFromLocation(domain.getY(), domain.getX(), 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Address addr = list.get(0);
		return addr.getLocality();
	}

	void setImg() {
		map = new HashMap<String, String>();
		map.put("S131111143653687355",
				"http://nbamania.com/g2/data/cheditor4/1308/59f96ba6f9c45aa03869fae43fe7e455_1376568108.1612.jpg");
		map.put("S131111143653323322",
				"http://m.hangang.seoul.go.kr/img/03_sports/01/04_01.jpg");
		map.put("S131111143652959388",
				"http://nbamania.com/g2/data/cheditor4/1308/59f96ba6f9c45aa03869fae43fe7e455_1376568108.1612.jpg");
		map.put("S131111143652576191",
				"http://m.hangang.seoul.go.kr/img/03_sports/01/04_01.jpg");
		map.put("S131111143652552944",
				"http://m.hangang.seoul.go.kr/img/03_sports/01/04_01.jpg");
		map.put("S131111143652430638",
				"http://m.hangang.seoul.go.kr/img/03_sports/01/04_01.jpg");
		map.put("S131111143651523125",
				"http://hangang.seoul.go.kr/files/2013/12/intro02_1-1.jpg");
		map.put("S131111143651088572",
				"http://nbamania.com/g2/data/cheditor4/1308/59f96ba6f9c45aa03869fae43fe7e455_1376568108.1612.jpg");
		map.put("S131111143650909388",
				"http://m.hangang.seoul.go.kr/img/03_sports/01/04_01.jpg");
		map.put("S131111143650821061",
				"http://hangang.seoul.go.kr/files/2013/12/intro02_1-1.jpg");
		map.put("S131111143654031237",
				"http://postfiles7.naver.net/20131002_54/zippy088_1380687637819PBmKm_JPEG/%BE%C6%C0%CC%C6%F913.9.23._160.jpg?type=w2");
		map.put("S131111143653727594",
				"http://m.hangang.seoul.go.kr/img/01_park/09/basketball.jpg");
		map.put("S131111143653360603",
				"http://m.hangang.seoul.go.kr/img/01_park/09/basketball.jpg");
		map.put("S131111143653119594",
				"http://m.hangang.seoul.go.kr/img/01_park/09/basketball.jpg");
		map.put("S131111143652802024",
				"http://m.hangang.seoul.go.kr/img/01_park/09/basketball.jpg");
		map.put("S131111143651935294",
				"http://m.hangang.seoul.go.kr/img/01_park/09/basketball.jpg");
		map.put("S131111143651914832",
				"http://m.hangang.seoul.go.kr/img/01_park/09/basketball.jpg");
		map.put("S131111143651734076",
				"http://m.hangang.seoul.go.kr/img/01_park/09/basketball.jpg");
		map.put("S131111143651306957",
				"http://m.hangang.seoul.go.kr/img/01_park/09/basketball.jpg");
		map.put("S131111143650999847",
				"http://m.hangang.seoul.go.kr/img/01_park/09/basketball.jpg");
		map.put("S140203093637747819",
				"http://parks.seoul.go.kr/cheditor/attach/20090514/LhftOycf.gif");
		map.put("S140125142346333973",
				"http://www.sportsnsay.com/shop/uploadimg/shop/325/201305070134427553.JPG");
		map.put("S140125141714188670",
				"http://www.sportsnsay.com/shop/uploadimg/shop/325/201305070134427553.JPG");
		map.put("S140125140924738603",
				"http://www.sportsnsay.com/shop/uploadimg/shop/325/201305070134427553.JPG");
		map.put("S140125140023317380",
				"http://www.sportsnsay.com/shop/uploadimg/shop/325/201305070134427553.JPG");
		map.put("S110411170413798134",
				"http://www.sportsnsay.com/shop/uploadimg/shop/243/201305071254448040.JPG");
		map.put("S110411165858998761",
				"http://www.sportsnsay.com/shop/uploadimg/shop/243/201305071254448040.JPG");
		map.put("S110411165337959570",
				"http://www.sportsnsay.com/shop/uploadimg/shop/243/201305071254448040.JPG");
		map.put("S110411164825077700",
				"http://www.sportsnsay.com/shop/uploadimg/shop/243/201305071254448040.JPG");
		map.put("S110411163737077958",
				"http://www.sportsnsay.com/shop/uploadimg/shop/243/201305071254448040.JPG");
		map.put("S110411162744502397",
				"http://www.sportsnsay.com/shop/uploadimg/shop/243/201305071254448040.JPG");
		map.put("S110411161938270362",
				"http://www.sportsnsay.com/shop/uploadimg/shop/243/201305071254448040.JPG");
		map.put("S110411161255317193",
				"http://www.sportsnsay.com/shop/uploadimg/shop/243/201305071254448040.JPG");
		map.put("S110411160721515363",
				"http://www.sportsnsay.com/shop/uploadimg/shop/243/201305071254448040.JPG");
		map.put("S110411154953185591",
				"http://www.sportsnsay.com/shop/uploadimg/shop/243/201305071254448040.JPG");
		map.put("S110411154429409327",
				"http://www.sportsnsay.com/shop/uploadimg/shop/243/201305071254448040.JPG");
		map.put("S110411153645556331",
				"http://www.sportsnsay.com/shop/uploadimg/shop/243/201305071254448040.JPG");
		map.put("S110411152822373719",
				"http://www.sportsnsay.com/shop/uploadimg/shop/243/201305071254448040.JPG");
		map.put("S110411151100619462",
				"http://www.sportsnsay.com/shop/uploadimg/shop/243/201305071254448040.JPG");
		map.put("S110411144219610503",
				"http://www.sportsnsay.com/shop/uploadimg/shop/243/201305071254448040.JPG");
		map.put("S110408184055229164",
				"http://www.sportsnsay.com/shop/uploadimg/shop/243/201305071254448040.JPG");
		map.put("S110408183714318122",
				"http://www.sportsnsay.com/shop/uploadimg/shop/243/201305071254448040.JPG");
		map.put("S110408183337367258",
				"http://www.sportsnsay.com/shop/uploadimg/shop/243/201305071254448040.JPG");
		map.put("S110408182900747937",
				"http://www.sportsnsay.com/shop/uploadimg/shop/243/201305071254448040.JPG");
		map.put("S110408182216074465",
				"http://www.sportsnsay.com/shop/uploadimg/shop/243/201305071254448040.JPG");
		map.put("S110408180729756017",
				"http://www.sportsnsay.com/shop/uploadimg/shop/243/201305071254448040.JPG");
		map.put("S131111143654123609",
				"http://www.sportsnsay.com/shop/uploadimg/shop/113/201305071104515327.jpg");
		map.put("S131111143654107759",
				"http://www.sportsnsay.com/shop/uploadimg/shop/113/201305071104515327.jpg");
		map.put("S131111143653546429",
				"http://file.newswire.co.kr/data/datafile2/thumb_big/2010/07/2039103817_20100716112653_1239484557.jpg");
		map.put("S131111143653381535",
				"http://file.newswire.co.kr/data/datafile2/thumb_big/2010/07/2039103817_20100716112653_1239484557.jpg");
		map.put("S131111143653299083",
				"http://m.hangang.seoul.go.kr/img/03_sports/01/04_01.jpg");
		map.put("S131111143653226655",
				"http://hangang.seoul.go.kr/files/2013/11/basketball.jpg");
		map.put("S131111143653202916",
				"http://hangang.seoul.go.kr/files/2013/11/basketball.jpg");
		map.put("S131111143653169544",
				"http://hangang.seoul.go.kr/files/2013/11/basketball.jpg");
		map.put("S131111143652485690",
				"http://m.hangang.seoul.go.kr/img/03_sports/01/04_01.jpg");
		map.put("S131111143652457325",
				"http://m.hangang.seoul.go.kr/img/03_sports/01/04_01.jpg");
		map.put("S131111143652389999",
				"http://m.hangang.seoul.go.kr/img/03_sports/01/04_01.jpg");
		map.put("S131111143652158778",
				"http://hangang.seoul.go.kr/files/2013/12/intro02_41.jpg");
		map.put("S131111143651770486",
				"http://file.newswire.co.kr/data/datafile2/thumb_big/2010/07/2039103817_20100716112653_1239484557.jpg");
		map.put("S131111143651611471",
				"http://m.hangang.seoul.go.kr/img/03_sports/01/04_01.jpg");
		map.put("S131111143651565107",
				"http://m.hangang.seoul.go.kr/img/03_sports/01/04_01.jpg");
		map.put("S131111143651541729",
				"http://m.hangang.seoul.go.kr/img/03_sports/01/04_01.jpg");
		map.put("S131111143651020362",
				"http://file.newswire.co.kr/data/datafile2/thumb_big/2010/07/2039103817_20100716112653_1239484557.jpg");
		map.put("S131111143650276607",
				"http://www.sportsnsay.com/shop/uploadimg/shop/113/201305071104515327.jpg");
		map.put("S131111154155886681",
				"http://www.betanews.net/imagedb/thumb/2010/0416/3689fd92.jpg");
		map.put("S131111154043031263",
				"http://www.betanews.net/imagedb/thumb/2010/0416/3689fd92.jpg");
		map.put("S131111143653016946",
				"http://www.betanews.net/imagedb/thumb/2010/0416/3689fd92.jpg");
		map.put("S131111143650588326",
				"http://www.betanews.net/imagedb/thumb/2010/0416/3689fd92.jpg");
		map.put("S140125131354724641",
				"http://www.sportsnsay.com/shop/uploadimg/shop/306/201305070122424265.JPG");
		map.put("S140125130804005021",
				"http://www.sportsnsay.com/shop/uploadimg/shop/306/201305070122424265.JPG");
		map.put("S140125115554278125",
				"http://www.sportsnsay.com/shop/uploadimg/shop/306/201305070122424265.JPG");
		map.put("S140125114544393857",
				"http://www.sportsnsay.com/shop/uploadimg/shop/306/201305070122424265.JPG");
		map.put("S140125112559213069",
				"http://www.sportsnsay.com/shop/uploadimg/shop/306/201305070122424265.JPG");
		map.put("S140125111514825253",
				"http://www.sportsnsay.com/shop/uploadimg/shop/306/201305070122424265.JPG");
		map.put("S140125110338217036",
				"http://www.sportsnsay.com/shop/uploadimg/shop/306/201305070122424265.JPG");
		map.put("S140125105610125689",
				"http://www.sportsnsay.com/shop/uploadimg/shop/306/201305070122424265.JPG");
		map.put("S140125102239163649",
				"http://www.sportsnsay.com/shop/uploadimg/shop/306/201305070122424265.JPG");
		map.put("S140125100737444402",
				"http://www.sportsnsay.com/shop/uploadimg/shop/306/201305070122424265.JPG");
		map.put("S130219112558648458",
				"http://parks.seoul.go.kr/cheditor/attach/20090514/LhftOycf.gif");
		map.put("S120913104333561578",
				"http://parks.seoul.go.kr/cheditor/attach/20090514/LhftOycf.gif");
		map.put("S140820193752583235",
				"http://www.filmkorea.or.kr/data/thumb/300_LMHF_0009967.jpg");
		map.put("S131212180936706768",
				"http://footballclub.co.kr/data/file/001/_thumb/720x0_90/3695593965_crp6Uixh_DSC02816.jpg");
		map.put("S131212174006963738",
				"http://footballclub.co.kr/data/file/001/_thumb/720x0_90/3695593965_crp6Uixh_DSC02816.jpg");
		map.put("S131212170740341849",
				"http://footballclub.co.kr/data/file/001/_thumb/720x0_90/3695593965_crp6Uixh_DSC02816.jpg");
		map.put("S140408085915487290",
				"https://www.sisul.or.kr/home_childrenpark/images/img_old/sub5/img_03.gif");
		map.put("S131205165625002740",
				"http://i.ytimg.com/vi/p6xj9l7M38o/0.jpg");
		map.put("S131205154030792809",
				"http://i.ytimg.com/vi/p6xj9l7M38o/0.jpg");
		map.put("S131202140357647537",
				"http://i.ytimg.com/vi/p6xj9l7M38o/0.jpg");
		map.put("S131202134058211135",
				"http://i.ytimg.com/vi/p6xj9l7M38o/0.jpg");
		map.put("S120110164252052955",
				"http://cfile236.uf.daum.net/image/124FFB3E4F7118252E0540");
		map.put("S120110163808151010",
				"http://cfile236.uf.daum.net/image/124FFB3E4F7118252E0540");
		map.put("S120110163150815486",
				"http://cfile236.uf.daum.net/image/124FFB3E4F7118252E0540");
		map.put("S140403180447455600",
				"http://www.ijongno.co.kr/resources/include/commonfile/image/content/jculture/wind.jpg");
		map.put("S140514144849704476",
				"http://www.sportsnsay.com/shop/uploadimg/shop/354/201305080217369420.JPG");
		map.put("S131112150228518551",
				"http://www.sportsnsay.com/shop/uploadimg/shop/263/201304260553501025.JPG");
		map.put("S131112143035112731",
				"http://www.sportsnsay.com/shop/uploadimg/shop/263/201304260553501025.JPG");
		map.put("S131112141618243171",
				"http://www.sportsnsay.com/shop/uploadimg/shop/263/201304260553501025.JPG");
		map.put("S131112140107805350",
				"http://www.sportsnsay.com/shop/uploadimg/shop/263/201304260553501025.JPG");
		map.put("S131112134735582750",
				"http://www.sportsnsay.com/shop/uploadimg/shop/263/201304260553501025.JPG");
		map.put("S131112110438767252",
				"http://www.sportsnsay.com/shop/uploadimg/shop/263/201304260553501025.JPG");
		map.put("S110408152414987497",
				"http://tour.ydp.go.kr/Upload/fhsq3ix3xg20101227-1.jpg");
		map.put("S110408161005278849",
				"http://www.sportsnsay.com/shop/uploadimg/shop/412/201305080303433925.JPG");
		map.put("S131217164958744189",
				"http://env.seoul.go.kr/files/2012/05/514a9bc8104dc4.56117654.jpg");
		map.put("S140411162813389915",
				"http://file.newswire.co.kr/data/datafile2/thumb_big/2007/05/2007051411791094420.06833300.jpg");
		map.put("S131103171040611111",
				"http://4.bp.blogspot.com/-9v4gsSKuNCQ/Ula70SkBT7I/AAAAAAAAVIQ/g7GbjI1ZRfc/s640/DSC_7446.JPG");
		map.put("S131103170138089861",
				"http://4.bp.blogspot.com/-9v4gsSKuNCQ/Ula70SkBT7I/AAAAAAAAVIQ/g7GbjI1ZRfc/s640/DSC_7446.JPG");
		map.put("S131102143941728984",
				"http://4.bp.blogspot.com/-9v4gsSKuNCQ/Ula70SkBT7I/AAAAAAAAVIQ/g7GbjI1ZRfc/s640/DSC_7446.JPG");
		map.put("S131027164931125495",
				"http://4.bp.blogspot.com/-9v4gsSKuNCQ/Ula70SkBT7I/AAAAAAAAVIQ/g7GbjI1ZRfc/s640/DSC_7446.JPG");
		map.put("S130218155726147330",
				"http://file.newswire.co.kr/data/datafile2/thumb_big/2005/06/2005062111193222930.30025100.jpg");
		map.put("S120620105938082979",
				"http://file.newswire.co.kr/data/datafile2/thumb_big/2005/06/2005062111193222930.30025100.jpg");
		map.put("S120620103913949627",
				"http://file.newswire.co.kr/data/datafile2/thumb_big/2005/06/2005062111193222930.30025100.jpg");
		map.put("S120605140843257226",
				"http://file.newswire.co.kr/data/datafile2/thumb_big/2005/06/2005062111193222930.30025100.jpg");
		map.put("S120423174310156240",
				"http://file.newswire.co.kr/data/datafile2/thumb_big/2005/06/2005062111193222930.30025100.jpg");
		map.put("S120314094001099854",
				"http://file.newswire.co.kr/data/datafile2/thumb_big/2005/06/2005062111193222930.30025100.jpg");
		map.put("S130204152759881177",
				"http://cphoto.asiae.co.kr/listimglink/6/2008060314345318918_1.jpg");
		map.put("S130129133904717417",
				"http://cphoto.asiae.co.kr/listimglink/6/2008060314345318918_1.jpg");
		map.put("S140121143044161093",
				"http://blog.joins.com/usr/j/d/jdy1025/7/S5030300.JPG");
		map.put("S140121142631690533",
				"http://filmkorea.or.kr/data/location/culfcam_0000152_003.jpg");
		map.put("S140121130852908339",
				"http://filmkorea.or.kr/data/location/culfcam_0000152_003.jpg");
		map.put("S131111143653142460",
				"http://hangang.seoul.go.kr/files/2013/11/intro02_12-1.jpg");
		map.put("S131111143652516972",
				"http://m.hangang.seoul.go.kr/img/03_sports/01/04_01.jpg");
		map.put("S131111143652202928",
				"http://m.hangang.seoul.go.kr/img/03_sports/01/04_01.jpg");
		map.put("S131111143653668868",
				"http://m.hangang.seoul.go.kr/img/01_park/07/intro03_4.jpg");
		map.put("S131111143653645665",
				"http://m.hangang.seoul.go.kr/img/01_park/07/intro03_4.jpg");
		map.put("S131111143653605733",
				"http://m.hangang.seoul.go.kr/img/01_park/07/intro03_4.jpg");
		map.put("S131111143652923090",
				"http://m.hangang.seoul.go.kr/img/01_park/07/intro03_4.jpg");
		map.put("S131111143652356053",
				"http://tong.visitkorea.or.kr/upload/traveli/fileup/1314719718961.jpg");
		map.put("S131111143652332319",
				"http://tong.visitkorea.or.kr/upload/traveli/fileup/1314719718961.jpg");
		map.put("S131111143652301406",
				"http://tong.visitkorea.or.kr/upload/traveli/fileup/1314719718961.jpg");
		map.put("S131111143652265634",
				"http://tong.visitkorea.or.kr/upload/traveli/fileup/1314719718961.jpg");
		map.put("S131111143651481765",
				"http://pds24.egloos.com/pds/201204/12/05/e0106805_4f869bde83c57.png");
		map.put("S131111143651417904",
				"http://tong.visitkorea.or.kr/upload/traveli/fileup/1314719718961.jpg");
		map.put("S131111143651390112",
				"http://tong.visitkorea.or.kr/upload/traveli/fileup/1314719718961.jpg");
		map.put("S131111143651345434",
				"http://tong.visitkorea.or.kr/upload/traveli/fileup/1314719718961.jpg");
		map.put("S131111143651055152",
				"http://m.hangang.seoul.go.kr/img/01_park/07/intro03_4.jpg");
		map.put("S131111143650394490",
				"http://tong.visitkorea.or.kr/upload/traveli/fileup/1314719718961.jpg");
		map.put("S131111143653263537",
				"http://m.hangang.seoul.go.kr/img/01_park/01/intro02_p02.gif");
		map.put("S131111143651975927",
				"http://m.hangang.seoul.go.kr/img/01_park/01/intro02_p02.gif");
		map.put("S131217165836539132",
				"http://spp.seoul.go.kr/cms/upload/board/B0125//20090427172115531.JPG");
		map.put("S131217165421123319",
				"http://www.sportsnsay.com/shop/uploadimg/shop/327/201305070135509607.jpg");
		map.put("S130318163922133054",
				"https://irs3.4sqi.net/img/general/width960/6630184_wbw-qME-Ao2ROYx7zeyhaOqUizBOwFYHlfB5U6Leknc.jpg");
		map.put("S140714103708340242",
				"http://stadium.seoul.go.kr/images/reserve/pic05.jpg");
		map.put("S140714103209662036",
				"http://stadium.seoul.go.kr/images/reserve/pic05.jpg");
		map.put("S140714102750189648",
				"http://stadium.seoul.go.kr/images/reserve/pic05.jpg");
		map.put("S140714101055498825",
				"http://stadium.seoul.go.kr/images/reserve/pic05.jpg");
		map.put("S140714095223436002",
				"http://stadium.seoul.go.kr/images/reserve/pic05.jpg");
		map.put("S140714094447178534",
				"http://stadium.seoul.go.kr/images/reserve/pic05.jpg");
		map.put("S140714093209070904",
				"http://stadium.seoul.go.kr/images/reserve/pic05.jpg");
		map.put("S140714091649053869",
				"http://stadium.seoul.go.kr/images/reserve/pic05.jpg");
		map.put("S110408161957590584",
				"http://www.sportsnsay.com/shop/uploadimg/shop/412/201305080303272535.jpg");
		map.put("S131111143654147683",
				"http://www.sportsnsay.com/shop/uploadimg/shop/169/201305071200457773.jpg");
		map.put("S131111143654057609",
				"http://www.sportsnsay.com/shop/uploadimg/shop/169/201305071200457773.jpg");
		map.put("S131111143653753763",
				"http://hangang.seoul.go.kr/files/2013/12/intro03_4.jpg");
		map.put("S131111143653564439",
				"http://hangang.seoul.go.kr/files/2013/12/intro03_4.jpg");
		map.put("S131111143653244354",
				"http://pds24.egloos.com/pds/201204/12/05/e0106805_4f869bde83c57.png");
		map.put("S131111143652874965",
				"http://hangang.seoul.go.kr/files/2013/12/intro03_4.jpg");
		map.put("S131111143652831581",
				"http://hangang.seoul.go.kr/files/2013/12/intro03_4.jpg");
		map.put("S131111143652412511",
				"http://m.hangang.seoul.go.kr/img/03_sports/01/04_01.jpg");
		map.put("S131111143652244571",
				"http://hangang.seoul.go.kr/files/2013/12/intro03_4.jpg");
		map.put("S131111143651956429",
				"http://m.hangang.seoul.go.kr/img/03_sports/01/04_01.jpg");
		map.put("S131111143651798332",
				"http://hangang.seoul.go.kr/files/2013/12/intro03_4.jpg");
		map.put("S131111143651453055",
				"http://pds24.egloos.com/pds/201204/12/05/e0106805_4f869bde83c57.png");
		map.put("S131111143651326889",
				"http://hangang.seoul.go.kr/files/2013/12/intro03_4.jpg");
		map.put("S131111143653586331",
				"http://file.newswire.co.kr/data/datafile2/thumb_big/2010/07/2039103817_20100716112653_1239484557.jpg");
		map.put("S131111143653525612",
				"http://m.hangang.seoul.go.kr/img/01_park/08/intro02_4.jpg");
		map.put("S131111143652899165",
				"http://file.newswire.co.kr/data/datafile2/thumb_big/2010/07/2039103817_20100716112653_1239484557.jpg");
		map.put("S131111143651868836",
				"http://file.newswire.co.kr/data/datafile2/thumb_big/2010/07/2039103817_20100716112653_1239484557.jpg");
		map.put("S131111143651827747",
				"http://file.newswire.co.kr/data/datafile2/thumb_big/2010/07/2039103817_20100716112653_1239484557.jpg");
		map.put("S131111143650951545",
				"http://m.hangang.seoul.go.kr/img/01_park/08/intro02_4.jpg");
		map.put("S131111143650750342",
				"http://www.sportsnsay.com/shop/uploadimg/shop/301/201305070117062560.JPG");
		map.put("S131111143653709199",
				"http://tong.visitkorea.or.kr/cms/resource/96/1567896_image2_1.jpg");
		map.put("S131111143653340770",
				"http://hangang.seoul.go.kr/files/2013/12/intro02_41.jpg");
		map.put("S131111143651893558",
				"http://tong.visitkorea.or.kr/cms/resource/96/1567896_image2_1.jpg");
		map.put("S131111143651645900",
				"http://hangang.seoul.go.kr/files/2013/12/intro02_41.jpg");
		map.put("S131111143650856533",
				"http://m.hangang.seoul.go.kr/img/01_park/03/intro02_1-1.jpg");
		map.put("S131219121605815859",
				"http://env.seoul.go.kr/files/2012/05/514a9b32221473.34139554.jpg");
		map.put("S131217163558583621",
				"http://env.seoul.go.kr/files/2012/05/514a9b32221473.34139554.jpg");
		map.put("S131217163111382702",
				"http://env.seoul.go.kr/files/2012/05/514a9b32221473.34139554.jpg");
		map.put("S140811095630807401",
				"http://www.filmkorea.or.kr/data/thumb/300_LMHF_0009967.jpg");
		map.put("S110524113045400921",
				"http://stadium.seoul.go.kr/images/reserve/pic10.jpg");
		map.put("S110408151649559092",
				"http://tour.ydp.go.kr/Upload/fhsq3ix3xg20101227-1.jpg");
		map.put("S110408161414813267",
				"http://www.sportsnsay.com/shop/uploadimg/shop/412/201305080303272535.jpg");
		map.put("S140812172931990032",
				"http://kukjungtv.com/data/file/kukjungNews/2009082873_v5tA4cbd_EAB5ADECA095EC9DBCEBB3B4.jpg");
		map.put("S140422155836726701",
				"http://kukjungtv.com/data/file/kukjungNews/2009082873_v5tA4cbd_EAB5ADECA095EC9DBCEBB3B4.jpg");
		map.put("S130424165737447594",
				"http://inews.seoul.go.kr/hsn/files/upload/article/basic_img_000031754.jpg");
		map.put("S110408145609728241",
				"http://pds21.egloos.com/pds/201204/13/05/e0106805_4f8824a18a361.png");
		map.put("S110408141630576007",
				"http://pds21.egloos.com/pds/201204/13/05/e0106805_4f8824a18a361.png");
		map.put("S131128161917281001",
				"http://cfile233.uf.daum.net/image/210851455293F03F1A6878");
		map.put("S131118162754554884",
				"http://cfile233.uf.daum.net/image/210851455293F03F1A6878");
		map.put("S110414161146514556",
				"http://cfile233.uf.daum.net/image/210851455293F03F1A6878");
		map.put("S130705214036646904",
				"http://env.seoul.go.kr/files/2012/03/4f73eedae64187.70741663.jpg");
		map.put("S130705213508797217",
				"http://env.seoul.go.kr/files/2012/03/4f73eedae64187.70741663.jpg");
		map.put("S130626162250827079",
				"http://env.seoul.go.kr/files/2012/03/4f73eedae64187.70741663.jpg");
		map.put("S130531153115089093",
				"http://env.seoul.go.kr/files/2012/03/4f73eedae64187.70741663.jpg");
		map.put("S110519093618035925",
				"http://env.seoul.go.kr/files/2012/06/nanji_01.jpg");
		map.put("S110502160710652313",
				"http://env.seoul.go.kr/files/2012/03/4f73eedae64187.70741663.jpg");
		map.put("S110502134152637416",
				"http://env.seoul.go.kr/files/2012/06/nanji_01.jpg");
		map.put("S131217164338450588",
				"http://env.seoul.go.kr/files/2012/05/514a9bc8104dc4.56117654.jpg");
		map.put("SEEDFIX-409",
				"http://www.dongjaknews.com/imgdata/dongjaknews_com/201012/2010121634033109.jpg");
		map.put("SEEDFIX-217",
				"http://sports.seocho.go.kr/resources/solution/epms/user/images/common/seocho_facility.gif");
		map.put("SEEDFIX-232", "http://gfmc.kr/images/picture/pc1-001.jpg");
		map.put("SEEDFIX-408",
				"http://www.dongjaknews.com/imgdata/dongjaknews_com/201012/2010121634033109.jpg");
		map.put("SEEDFIX-171", "http://gfmc.kr/images/picture/pb1-001.jpg");
		map.put("SEEDFIX-51",
				"http://cfile23.uf.tistory.com/image/206E024F4E7A17161E40D2");
		map.put("SEEDFIX-318",
				"http://www.gangbukcmc.seoul.kr/sports/images/group03/wpool_01.jpg");
		map.put("SEEDFIX-405",
				"http://www.sportsnsay.com/shop/uploadimg/shop/189/201305071216373903.jpg");
		map.put("SEEDFIX-330",
				"http://www.gangbukcmc.seoul.kr/sports/images/layout/sports_m1.gif");
		map.put("SEEDFIX-310",
				"http://www.dobongsiseol.or.kr/sports_culture/img/sub04/01/akua.jpg");
		map.put("SEEDFIX-172", "http://gfmc.kr/images/picture/pb1-001.jpg");
	}

}
