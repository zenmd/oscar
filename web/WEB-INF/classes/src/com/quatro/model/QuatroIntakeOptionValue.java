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
package com.quatro.model;

import java.io.Serializable;

public class QuatroIntakeOptionValue implements Serializable {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

  private String prefix;
  private String code;
  private String description;
  private boolean active;
  private int displayOrder;

  public boolean isActive() {
	return active;
  }
  
  public void setActive(boolean active) {
	this.active = active;
  }
  
  public String getCode() {
	return code;
  }

  public void setCode(String code) {
	this.code = code;
  }

  public String getDescription() {
	return description;
  }

  public void setDescription(String description) {
	this.description = description;
  }

  public int getDisplayOrder() {
	return displayOrder;
  }

  public void setDisplayOrder(int displayOrder) {
	this.displayOrder = displayOrder;
  }

  public String getPrefix() {
	return prefix;
  }

  public void setPrefix(String prefix) {
	this.prefix = prefix;
  }
  
  public boolean equals(Object obj) {
      if (null == obj)
          return false;
      if (!(obj instanceof QuatroIntakeOptionValue))
          return false;
      else {
    	  QuatroIntakeOptionValue qv = (QuatroIntakeOptionValue) obj;
          if (null == this.getPrefix() || null == qv.getPrefix() ||
       		  null == this.getCode() || null == qv.getCode())
              return false;
          else
              return (this.getPrefix().equals(qv.getPrefix()) && 
            		  this.getCode().equals(qv.getCode()));
      }
  }

  public int hashCode() {
      if (Integer.MIN_VALUE == this.hashCode) {
          if (null == this.getPrefix() || null == this.getCode())
              return super.hashCode();
          else {
              String hashStr = this.getClass().getName() + ":" + this.getPrefix() + ":" + this.getCode();
              this.hashCode = hashStr.hashCode();
          }
      }
      return this.hashCode;
  }	

}
