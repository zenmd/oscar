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
