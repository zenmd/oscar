package com.quatro.web.intake;

import java.util.HashMap;

public class IntakeConstant {
	  //constant int below should start from 0 to the max value without duplication/gap between.
	  public static final int GENDER = 0;
	  public static final int REFERREDBY = 1;
	  public static final int ABORIGINAL = 2;
	  public static final int CURSLEEPARRANGEMENT = 3;
	  public static final int LENGTHOFHOMELESS = 4;
	  public static final int REASONFORHOMELESS = 5;
	  public static final int SOURCEINCOME = 6;
	  public static final int LIVEDBEFORE = 7;
	  public static final int STATUSINCANADA = 8;
	  public static final int REFERREDTO = 9;
	  public static final int REASONNOADMIT = 10;

	  //other non-dropdown field constants
	  public static final int CONTACTNAME = 11;
	  public static final int CONTACTNUMBER = 12;
	  public static final int CONTACTEMAIL = 13;
	  public static final int YOUTH = 14;
	  public static final int ABORIGINALOTHER = 15;
	  public static final int PREGNANT = 16;
	  public static final int DISCLOSEDABUSE = 17;
	  
	  public static final int DISABILITY = 18;
	  public static final int OBSERVEDABUSE = 19;
	  public static final int DISCLOSEDMENTALISSUE = 20;
	  public static final int POORHYGIENE = 21;
	  public static final int OBSERVEDMENTALISSUE = 22;
	  public static final int DISCLOSEDALCOHOLABUSE = 23;
	  public static final int OBSERVEDALCOHOLABUSE = 24;
	  public static final int BIRTHCERTIFICATE = 25;
	  public static final int BIRTHCERTIFICATEYN = 26;
	  public static final int SIN = 27;
	  public static final int SINYN = 28;
	  public static final int HEALTHCARDNO = 29;
	  public static final int HEALTHCARDNOYN = 30;
	  public static final int DRIVERLICENSENO = 31;
	  public static final int DRIVERLICENSENOYN = 32;
	  public static final int CITIZENCARDNO = 33;
	  public static final int CITIZENCARDNOYN = 34;
	  public static final int NATIVERESERVENO = 35;
	  public static final int NATIVERESERVENOYN = 36;
	  public static final int VETERANNO = 37;
	  public static final int VETERANNOYN = 38;
	  public static final int RECORDLANDING = 39;
	  public static final int RECORDLANDINGYN = 40;
	  public static final int LIBRARYCARD = 41;
	  public static final int LIBRARYCARDYN = 42;
	  public static final int IDOTHER = 43;
	  public static final int INCOME = 44;
	  public static final int INCOMEWORKERNAME1 = 45;
	  public static final int INCOMEWORKERPHONE1 = 46;
	  public static final int INCOMEWORKEREMAIL1 = 47;
	  public static final int INCOMEWORKERNAME2 = 48;
	  public static final int INCOMEWORKERPHONE2 = 49;
	  public static final int INCOMEWORKEREMAIL2 = 50;
	  public static final int INCOMEWORKERNAME3 = 51;
	  public static final int INCOMEWORKERPHONE3 = 52;
	  public static final int INCOMEWORKEREMAIL3 = 53;
	  public static final int LIVEDBEFOREOTHER = 54;
	  public static final int ORIGINALCOUNTRY = 55;
	  public static final int PROGRAM = 56;
	  public static final int COMMENTS = 57;
	  
	  public static final int LANGUAGE = 58;
	  public static final int COUNTRYOFORIGINAL = 59;
	  public static final int VAW = 60;
	  public static final int INSHELTERBEFORE = 61;
	  
	  private static HashMap<Integer, String> PREFIX;
	  
	  public static HashMap<Integer, String> getPrefixDefined() {
			 if(PREFIX==null){
				PREFIX = new HashMap<Integer, String>();
				PREFIX.put(new Integer(GENDER), "GEN");
				PREFIX.put(new Integer(REFERREDBY), "RFB");
				PREFIX.put(new Integer(ABORIGINAL), "AOI");
				PREFIX.put(new Integer(CURSLEEPARRANGEMENT), "CSA");
				PREFIX.put(new Integer(LENGTHOFHOMELESS), "LHL");
				PREFIX.put(new Integer(REASONFORHOMELESS), "RHL");
				PREFIX.put(new Integer(SOURCEINCOME), "SIM");
				PREFIX.put(new Integer(LIVEDBEFORE), "LBF");
				PREFIX.put(new Integer(STATUSINCANADA), "SCA");
				PREFIX.put(new Integer(REFERREDTO), "RFT");
				PREFIX.put(new Integer(REASONNOADMIT), "RNA");
			 }
			 return PREFIX;
		  }
}
