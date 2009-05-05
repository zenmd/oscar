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
package oscar.oscarDB;

import java.util.Calendar;


public class DBPreparedHandlerParam {
   private Calendar dateValue;
   private String stringValue;
   private int intValue;
   private String paramType;

   public static String PARAM_STRING = "String";
   public static String PARAM_DATE = "Date";
   public static String PARAM_INT = "Int";

   public DBPreparedHandlerParam(String stringValue){
	   this.intValue = 0;
	   this.stringValue= stringValue;
	   this.dateValue=null;
	   this.paramType=PARAM_STRING;
   }
   
   public DBPreparedHandlerParam(Calendar dateValue){
	   this.intValue = 0;
	   this.stringValue=null;
	   this.dateValue= dateValue;
	   this.paramType=PARAM_DATE;
   }

   public DBPreparedHandlerParam(int intValue){
	   this.intValue= intValue;
	   this.stringValue = "";
	   this.dateValue=null;
	   this.paramType=PARAM_INT;
   }

   public Calendar getDateValue() {
	  return dateValue;
   }

   public int getIntValue() {
		  return intValue;
   }

//   public void setDateValue(Date dateValue) {
//	  this.dateValue = dateValue;
//   }

   public String getParamType() {
	  return paramType;
   }

//   public void setParamType(String paramType) {
//	  this.paramType = paramType;
//   }

   public String getStringValue() {
	  return stringValue;
   }

//   public void setStringValue(String stringValue) {
//	  this.stringValue = stringValue;
//   }
   
}
