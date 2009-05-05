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
package org.oscarehr.common.dao;

import org.hibernate.SessionFactory;

public abstract class BaseAuditStrategy implements AuditStrategy {
	
	private SessionFactory sessionFactory;
	
	public BaseAuditStrategy() {
		registerEvents();
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public abstract void registerEvents();
	
	public void saveAudits() {
	    // write audits
		// clear pending audits
	}

}