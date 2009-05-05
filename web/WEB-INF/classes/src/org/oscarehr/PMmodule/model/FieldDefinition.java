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

import java.io.Serializable;

public class FieldDefinition implements Serializable {
	 private static final long serialVersionUID = 1L;
	 private String fieldName;
	 private Integer fieldLength;
	 private String fieldType;
	 private Integer fieldStartIndex;
	 private String dateFormatStr;
	public Integer getFieldLength() {
		return fieldLength;
	}
	public void setFieldLength(Integer fieldLength) {
		this.fieldLength = fieldLength;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public Integer getFieldStartIndex() {
		return fieldStartIndex;
	}
	public void setFieldStartIndex(Integer fieldStartIndex) {
		this.fieldStartIndex = fieldStartIndex;
	}
	public String getDateFormatStr() {
		return dateFormatStr;
	}
	public void setDateFormatStr(String dateFormatStr) {
		this.dateFormatStr = dateFormatStr;
	}
}
