package kr.co.makeit.seoul.domain;

import java.util.List;

import kr.co.makeit.seoul.map.GeoPoint;
import kr.co.makeit.seoul.map.GeoTrans;

public class TMtoGoogle {
	public GeoPoint TMgoogle(List<Domain> domain, int n) {
		GeoPoint geoPoint = new GeoPoint(domain.get(n).getX(), domain
				.get(n).getY());
		GeoPoint changedPoint = GeoTrans.convert(GeoTrans.TM, GeoTrans.GEO,
				geoPoint);
		return changedPoint;
	}
	
	public GeoPoint TMgoogle(Domain domain) {
		GeoPoint geoPoint = new GeoPoint(domain.getX(), domain
				.getY());
		GeoPoint changedPoint = GeoTrans.convert(GeoTrans.TM, GeoTrans.GEO,
				geoPoint);
		return changedPoint;
	}
}
