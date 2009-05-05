/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
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

public class RedirectLink {

	private int id=0;
	private String url=null;

	private void setId(int id)
	{
		this.id=id;
	}
	
	public int getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		url=StringUtils.trimToNull(url);
		if (url==null) throw(new IllegalArgumentException("Url can not be null."));
		this.url = url;
	}
}
