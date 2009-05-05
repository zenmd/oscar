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
package org.caisi.model;

import org.apache.commons.lang.StringUtils;

public class IssueGroup implements Comparable{

	private int id=0;
	private String name=null;

	private void setId(int id)
	{
		this.id=id;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		name=StringUtils.trimToNull(name);
		if (name==null) throw(new IllegalArgumentException("Can not be null."));
		this.name = name;
	}
	
	public int compareTo(Object o) {
	   return(name.compareTo(((IssueGroup)o).name));
    }
}
