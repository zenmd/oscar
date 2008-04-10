package com.quatro.web.intake;

import java.util.HashMap;

public class OptionListConstant {
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
