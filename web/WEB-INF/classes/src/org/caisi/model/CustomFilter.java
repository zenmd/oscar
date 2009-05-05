/*
* 
* Copyright (c) 2001-2002. Centre for Research on Inner City Health, St. Michael's Hospital, Toronto. All Rights Reserved. *
* This software is published under the GPL GNU General Public License. 
* This program is free software; you can redistribute it and/or 
* modify it under the terms of the GNU General Public License 
* as published by the Free Software Foundation; either version 2 
* of the License, or (at your option) any later version. * 
* This program is distributed in the hope that it will be useful, 
* but WITHOUT ANY WARRANTY; without even the implied warranty of 
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
* GNU General Public License for more details. * * You should have received a copy of the GNU General Public License 
* along with this program; if not, write to the Free Software 
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA. * 
* 
* <OSCAR TEAM>
* 
* This software was written for 
* Centre for Research on Inner City Health, St. Michael's Hospital, 
* Toronto, Ontario, Canada  - UPDATED: Quatro Group 2008/2009
*/

package org.caisi.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.Provider;

public class CustomFilter extends BaseObject {
	private Date start_date;
	private Date end_date;
	private String status;
	
	private String provider_no;        
	private String programId;
	
	public CustomFilter() {
		setStatus("");
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setStartDate(String data) {
		if(data == null || data.length()==0) {
			data = "1900/01/01";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		try {
			setStart_date(formatter.parse(data));
		}catch(Exception e) {
			throw new IllegalArgumentException("Invalid service date, use yyyy/MM/dd");
		}
	}
	
	public String getStartDate() {
		if(getStart_date() != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			String ret= formatter.format(getStart_date());
			if(ret.equals("1900/01/01")) ret =  "";
			return ret;
		}
		return "";
	}
	
	public void setEndDate(String data) {
		if(data == null || data.length()==0) {
			data = "8888/12/31";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		try {
			setEnd_date(formatter.parse(data));
		}catch(Exception e) {
			throw new IllegalArgumentException("Invalid service date, use yyyy/MM/dd");
		}
	}
	
	public String getEndDate() {
		if(getEnd_date() != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			String ret = formatter.format(getEnd_date());
			if(ret.equals("8888/12/31")) ret= "";
			return ret;
		}
		return "";
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	
	public String getProvider_no() {
		return provider_no;
	}

	public void setProvider_no(String provider_no) {
		this.provider_no = provider_no;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}
    
}
