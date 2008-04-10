package com.quatro.web.intake;

import java.util.List;
import java.util.ArrayList;

public class OptionList {
  private List<List> optionValues;

  public OptionList(){
	 optionValues= new ArrayList<List>();
	 ArrayList obj= new ArrayList();
	 for(int i=0;i<11;i++) optionValues.add(obj);
  }
  
  public List getOptionValues() {
	return optionValues;
  }

  public void setOptionValues(List<List> optionValues) {
	this.optionValues = optionValues;
  }

  public void setValue(int index, List lst){
	  optionValues.set(index, lst);
  }
  
  public List getGender(){
	  return (List)optionValues.get(OptionListConstant.GENDER);
  }
  
  public List getReferredBy(){
	  return (List)optionValues.get(OptionListConstant.REFERREDBY);
  }

  public List getAboriginal(){
	  return (List)optionValues.get(OptionListConstant.ABORIGINAL);
  }

  public List getCurSleepArrangement(){
	  return (List)optionValues.get(OptionListConstant.CURSLEEPARRANGEMENT);
  }

  public List getLengthOfHomeless(){
	  return (List)optionValues.get(OptionListConstant.LENGTHOFHOMELESS);
  }

  public List getReasonForHomeless(){
	  return (List)optionValues.get(OptionListConstant.REASONFORHOMELESS);
  }

  public List getSourceIncome(){
	  return (List)optionValues.get(OptionListConstant.SOURCEINCOME);
  }
  
  public List getLivedBefore(){
	  return (List)optionValues.get(OptionListConstant.LIVEDBEFORE);
  }

  public List getStatusInCanada(){
	  return (List)optionValues.get(OptionListConstant.STATUSINCANADA);
  }

  public List getReferredTo(){
	  return (List)optionValues.get(OptionListConstant.REFERREDTO);
  }

  public List getReasonNoAdmit(){
	  return (List)optionValues.get(OptionListConstant.REASONNOADMIT);
  }
  
}
