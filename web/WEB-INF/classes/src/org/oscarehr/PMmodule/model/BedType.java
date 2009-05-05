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

package org.oscarehr.PMmodule.model;

import java.io.Serializable;
import java.util.Calendar;

import org.apache.commons.lang.builder.ToStringBuilder;

public class BedType implements Serializable {

	private static final long serialVersionUID = 1L;
/*	
    public static String REF = "BedType";
    public static String PROP_DEFAULT = "default";
    public static String PROP_NAME = "name";
    public static String PROP_ID = "id";
*/    
    private int hashCode = Integer.MIN_VALUE;
    private Integer id;
    private String name;
    private boolean active;
    private String lastUpdateUser;
    private Calendar lastUpdateDate;

    public Calendar getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Calendar lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

/*
   // constructors
	public BedType () {
		initialize();
	}

	public BedType (Integer id) {
		this.setId(id);
		initialize();
	}

	public BedType (
		Integer id,
		String name,
		boolean m_default) {

		this.setId(id);
		this.setName(name);
		this.setDefault(m_default);
		initialize();
	}
*/

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

    protected void initialize () {}

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
        this.hashCode = Integer.MIN_VALUE;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public boolean isActive () {
        return active;
    }

    public void setActive (boolean active) {
        this.active = active;
    }
    
    public boolean equals (Object obj) {
        if (null == obj) return false;
        if (!(obj instanceof BedType)) return false;
        else {
            BedType bedType = (BedType) obj;
            if (null == this.getId() || null == bedType.getId()) return false;
            else return (this.getId().equals(bedType.getId()));
        }
    }

    public int hashCode () {
        if (Integer.MIN_VALUE == this.hashCode) {
            if (null == this.getId()) return super.hashCode();
            else {
                String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
                this.hashCode = hashStr.hashCode();
            }
        }
        return this.hashCode;
    }
}