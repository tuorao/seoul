package kr.co.makeit.seoul.domain;

public class WTdomain {

	private String SAWS_OBS_TM;
	private String STN_NM;
	private String SAWS_TA_AVG;
	private String SAWS_HD;
	private String NAME;
	private String SAWS_WS_AVG;
	private String SAWS_RN_SUM;
	private String SAWS_SOLAR;
	private String SAWS_SHINE;
	
	public WTdomain(){}
	
	public WTdomain(String SAWS_OBS_TM, String STN_NM, String SAWS_TA_AVG, String SAWS_HD,String NAME,
			String SAWS_WS_AVG, String SAWS_RN_SUM, String SAWS_SOLAR, String SAWS_SHINE){
		this.SAWS_OBS_TM = SAWS_OBS_TM;
		this.STN_NM = STN_NM;
		this.SAWS_TA_AVG = SAWS_TA_AVG;
		this.SAWS_HD = SAWS_TA_AVG;
		this.NAME = NAME;
		this.SAWS_SHINE = SAWS_SHINE;
		this.SAWS_SOLAR = SAWS_SOLAR;
		this.SAWS_RN_SUM = SAWS_RN_SUM;
		this.SAWS_WS_AVG = SAWS_WS_AVG;
	}
	public String getSAWS_OBS_TM() {
		return SAWS_OBS_TM;
	}
	public void setSAWS_OBS_TM(String sAWS_OBS_TM) {
		SAWS_OBS_TM = sAWS_OBS_TM;
	}
	public String getSTN_NM() {
		return STN_NM;
	}
	public void setSTN_NM(String sTN_NM) {
		STN_NM = sTN_NM;
	}
	public String getSAWS_TA_AVG() {
		return SAWS_TA_AVG;
	}
	public void setSAWS_TA_AVG(String sAWS_TA_AVG) {
		SAWS_TA_AVG = sAWS_TA_AVG;
	}
	public String getSAWS_HD() {
		return SAWS_HD;
	}
	public void setSAWS_HD(String sAWS_HD) {
		SAWS_HD = sAWS_HD;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getSAWS_WS_AVG() {
		return SAWS_WS_AVG;
	}
	public void setSAWS_WS_AVG(String sAWS_WS_AVG) {
		SAWS_WS_AVG = sAWS_WS_AVG;
	}
	public String getSAWS_RN_SUM() {
		return SAWS_RN_SUM;
	}
	public void setSAWS_RN_SUM(String sAWS_RN_SUM) {
		SAWS_RN_SUM = sAWS_RN_SUM;
	}
	public String getSAWS_SOLAR() {
		return SAWS_SOLAR;
	}
	public void setSAWS_SOLAR(String sAWS_SOLAR) {
		SAWS_SOLAR = sAWS_SOLAR;
	}
	public String getSAWS_SHINE() {
		return SAWS_SHINE;
	}
	public void setSAWS_SHINE(String sAWS_SHINE) {
		SAWS_SHINE = sAWS_SHINE;
	}

	@Override
	public String toString() {
		return "WTdomain [SAWS_OBS_TM=" + SAWS_OBS_TM + ", STN_NM=" + STN_NM
				+ ", SAWS_TA_AVG=" + SAWS_TA_AVG + ", SAWS_HD=" + SAWS_HD
				+ ", NAME=" + NAME + ", SAWS_WS_AVG=" + SAWS_WS_AVG
				+ ", SAWS_RN_SUM=" + SAWS_RN_SUM + ", SAWS_SOLAR=" + SAWS_SOLAR
				+ ", SAWS_SHINE=" + SAWS_SHINE + "]";
	}
	
	
}
