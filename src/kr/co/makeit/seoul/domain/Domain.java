package kr.co.makeit.seoul.domain;

import java.io.Serializable;



public class Domain implements Serializable{
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String GUBUN;	//서비스 구분 
	private String SVCID;	//서비스 ID
	private String MAXCLASSNM; // 대분류명
	private String MINCLASSNM; // 소분류명
	private String SVCSTATNM;	// 서비스상
	private String SVCNM;	// 서비스명
	private String PAYATNM;	// 결제방법
	private String PLACENM;	// 장소명
	private String USETGTINFO;	// 서비스대상
	private String SVCURL;	// 바로가기 URL
	private String X;	// X좌표
	private String Y;	// Y좌표
	private String ADR1;
	private String ADR2;
	
	public Domain() {
	}
	
	public Domain(String GUBUN, String SVCID, String MAXCLASSNM, String MINCLASSNM, String SVCSTATNM,
			String SVCNM, String PAYATNM, String PLACENM, String USETGTINFO, String SVCURL, String X, String Y )
			{
		this.GUBUN = GUBUN;
		this.SVCID = SVCID;
		this.MAXCLASSNM = MAXCLASSNM;
		this.MINCLASSNM = MINCLASSNM;
		this.SVCSTATNM = SVCSTATNM;
		this.SVCNM = SVCNM;
		this.PAYATNM = PAYATNM;
		this.PLACENM = PLACENM;
		this.USETGTINFO = USETGTINFO;
		this.SVCURL = SVCURL;
		this.X = X;
		this.Y = Y;
			}
	
	
	public String getADR1() {
		return ADR1;
	}

	public void setADR1(String aDR1) {
		ADR1 = aDR1;
	}

	public String getADR2() {
		return ADR2;
	}

	public void setADR2(String aDR2) {
		ADR2 = aDR2;
	}

	public String getGUBUN() {
		return GUBUN;
	}

	public String getSVCID() {
		return SVCID;
	}

	public String getMAXCLASSNM() {
		return MAXCLASSNM;
	}

	public String getMINCLASSNM() {
		return MINCLASSNM;
	}

	public String getSVCSTATNM() {
		return SVCSTATNM;
	}

	public String getSVCNM() {
		return SVCNM;
	}

	public String getPAYATNM() {
		return PAYATNM;
	}

	public String getPLACENM() {
		return PLACENM;
	}

	public String getUSETGTINFO() {
		return USETGTINFO;
	}

	public String getSVCURL() {
		return SVCURL;
	}

	public Double getX() {
		return seePalJHW(X);
	}

	public Double getY() {
		return seePalJHW(Y);
	}

	public void setGUBUN(String gUBUN) {
		GUBUN = gUBUN;
	}

	public void setSVCID(String sVCID) {
		SVCID = sVCID;
	}

	public void setMAXCLASSNM(String mAXCLASSNM) {
		MAXCLASSNM = mAXCLASSNM;
	}

	public void setMINCLASSNM(String mINCLASSNM) {
		MINCLASSNM = mINCLASSNM;
	}

	public void setSVCSTATNM(String sVCSTATNM) {
		SVCSTATNM = sVCSTATNM;
	}

	public void setSVCNM(String sVCNM) {
		SVCNM = sVCNM;
	}

	public void setPAYATNM(String pAYATNM) {
		PAYATNM = pAYATNM;
	}

	public void setPLACENM(String pLACENM) {
		PLACENM = pLACENM;
	}

	public void setUSETGTINFO(String uSETGTINFO) {
		USETGTINFO = uSETGTINFO;
	}

	public void setSVCURL(String sVCURL) {
		SVCURL = sVCURL;
	}

	public void setX(String x) {
		X = x;
	}

	public void setY(String y) {
		Y = y;
	}

	@Override
	public String toString() {
		return "Domain [GUBUN=" + GUBUN + ", SVCID=" + SVCID + ", MAXCLASSNM="
				+ MAXCLASSNM + ", MINCLASSNM=" + MINCLASSNM + ", SVCSTATNM="
				+ SVCSTATNM + ", SVCNM=" + SVCNM + ", PAYATNM=" + PAYATNM
				+ ", PLACENM=" + PLACENM + ", USETGTINFO=" + USETGTINFO
				+ ", SVCURL=" + SVCURL + ", X=" + X + ", Y=" + Y + "]";
	}
	/**
	 * 스트링을 더블로 바꾸어주는 메서드 시팔전현우
	 * @author jinhyung
	 * @date 2014. 8. 23.
	 * @param value 스트링
	 * @return 더블
	 */
	private Double seePalJHW(String value) {
		double result = 0d;
		if( !value.equals(""))
			result = Double.parseDouble(value);
		return result;
	}
	

}
