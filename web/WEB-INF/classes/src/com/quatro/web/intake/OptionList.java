package com.quatro.web.intake;

import java.util.List;
import java.util.ArrayList;

public class OptionList {
  private List optionValues;

  public OptionList(){
	 optionValues= new ArrayList();
	 ArrayList obj= new ArrayList();
	 for(int i=0;i<11;i++) optionValues.add(obj);
  }
  
  public List getOptionValues() {
	return optionValues;
  }

  public void setOptionValues(List optionValues) {
	this.optionValues = optionValues;
  }

  public void setValue(int index, List lst){
	  optionValues.set(index, lst);
  }
  
  public List getGender(){
	  return (List)optionValues.get(IntakeConstant.GENDER);
  }
  
  public List getReferredBy(){
	  return (List)optionValues.get(IntakeConstant.REFERREDBY);
  }

  public List getAboriginal(){
	  return (List)optionValues.get(IntakeConstant.ABORIGINAL);
  }

  public List getCurSleepArrangement(){
	  return (List)optionValues.get(IntakeConstant.CURSLEEPARRANGEMENT);
  }

  public List getLengthOfHomeless(){
	  return (List)optionValues.get(IntakeConstant.LENGTHOFHOMELESS);
  }

  public List getReasonForHomeless(){
	  return (List)optionValues.get(IntakeConstant.REASONFORHOMELESS);
  }

  public List getSourceIncome(){
	  return (List)optionValues.get(IntakeConstant.SOURCEINCOME);
  }
  
  public List getLivedBefore(){
	  return (List)optionValues.get(IntakeConstant.LIVEDBEFORE);
  }

  public List getStatusInCanada(){
	  return (List)optionValues.get(IntakeConstant.STATUSINCANADA);
  }

  public List getReferredTo(){
	  return (List)optionValues.get(IntakeConstant.REFERREDTO);
  }

  public List getReasonNoAdmit(){
	  return (List)optionValues.get(IntakeConstant.REASONNOADMIT);
  }
  
}
