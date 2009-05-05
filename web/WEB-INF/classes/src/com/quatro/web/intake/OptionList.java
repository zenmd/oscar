/*******************************************************************************
 * Copyright (c) 2008, 2009 Quatro Group Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU General Public License
 * which accompanies this distribution, and is available at
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * Contributors:
 *     <Quatro Group Software Systems inc.>  <OSCAR Team>
 *******************************************************************************/
package com.quatro.web.intake;

import java.util.List;
import java.util.ArrayList;

public class OptionList {
  private List optionValues;

  public OptionList(){
	 optionValues= new ArrayList();
	 ArrayList obj= new ArrayList();
	 for(int i=0;i<12;i++) optionValues.add(obj);
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

  public List getReasonForService(){
	  return (List)optionValues.get(IntakeConstant.REASONFORSERVICELIST);
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
