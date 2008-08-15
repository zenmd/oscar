package org.oscarehr.PMmodule.model;

import java.util.Calendar;

public class ClientHistoryFilter {
   private Calendar actionStartDate;
   private Calendar actionEndDate;
   private String actionTxt;
   private Integer programId;
   
   public Calendar getActionEndDate() {
	 return actionEndDate;
   }
   
   public void setActionEndDate(Calendar actionEndDate) {
	 this.actionEndDate = actionEndDate;
   }
   
   public Calendar getActionStartDate() {
	 return actionStartDate;
   }
   
   public void setActionStartDate(Calendar actionStartDate) {
	 this.actionStartDate = actionStartDate;
   }
   
   public String getActionTxt() {
	 return actionTxt;
   }
   
   public void setActionTxt(String actionTxt) {
	 this.actionTxt = actionTxt;
   }
   
   public Integer getProgramId() {
	 return programId;
   }
   
   public void setProgramId(Integer programId) {
	 this.programId = programId;
   }
}
