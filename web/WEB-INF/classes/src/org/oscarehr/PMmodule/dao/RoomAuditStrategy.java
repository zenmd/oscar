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
package org.oscarehr.PMmodule.dao;

import java.io.Serializable;

import org.oscarehr.PMmodule.model.Room;
import org.oscarehr.common.dao.AuditStrategyFactory;
import org.oscarehr.common.dao.AuditableEvent;
import org.oscarehr.common.dao.BaseAuditStrategy;

public class RoomAuditStrategy extends BaseAuditStrategy {
	
	//@Override
	public void registerEvents() {
		AuditStrategyFactory.register(Room.class, AuditableEvent.CREATE, this);
		AuditStrategyFactory.register(Room.class, AuditableEvent.UPDATE, this);
		AuditStrategyFactory.register(Room.class, AuditableEvent.DELETE, this);
	}

	public void auditCreate(Object entity, Serializable id, Object[] currentState, String[] propertyNames) {
	    System.out.println("RoomDemographicAuditStrategy.auditCreate()");
    }

	public void auditUpdate(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames) {
	    System.out.println("RoomDemographicAuditStrategy.auditUpdate()");
    }
	
	public void auditDelete(Object entity, Serializable id, Object[] state, String[] propertyNames) {
		System.out.println("RoomDemographicAuditStrategy.auditDelete()");
	}

}