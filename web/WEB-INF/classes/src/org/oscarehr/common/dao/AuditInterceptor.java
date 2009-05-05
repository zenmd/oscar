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
package org.oscarehr.common.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

/**
 * Hibernate Audit Interceptor
 */
public class AuditInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = 1L;
	
	private List strategies;
	
	public AuditInterceptor() {
		strategies = new ArrayList();
	}
	
	/**
	 * @see org.hibernate.EmptyInterceptor#onSave(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
	 */
	
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		if (entity instanceof Auditable) {
			AuditStrategy strategy = AuditStrategyFactory.create(entity, AuditableEvent.CREATE);
			
			if (strategy != null) {
				strategies.add(strategy);
				strategy.auditCreate(entity, id, state, propertyNames);
			}
		}
		
	    return false;
	}
	
	/**
	 * @see org.hibernate.Interceptor#onFlushDirty(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
	 */
	
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
		if (entity instanceof Auditable) {
			AuditStrategy strategy = AuditStrategyFactory.create(entity, AuditableEvent.UPDATE);
			
			if (strategy != null) {
				strategies.add(strategy);
				strategy.auditUpdate(entity, id, currentState, previousState, propertyNames);
			}
        }
		
		return false;
	}

	/**
	 * @see org.hibernate.Interceptor#onDelete(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
	 */
	
	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		if (entity instanceof Auditable) {
			AuditStrategy strategy = AuditStrategyFactory.create(entity, AuditableEvent.DELETE);
			
			if (strategy != null) {
				strategies.add(strategy);
				strategy.auditDelete(entity, id, state, propertyNames);
			}
		}
	}
	
	/**
	 * @see org.hibernate.EmptyInterceptor#postFlush(java.util.Iterator)
	 */
	
	public void postFlush(Iterator entities) {
//		for (AuditStrategy strategy : strategies) {
		for (int i=0; i<strategies.size(); i++) {
	        AuditStrategy strategy = (AuditStrategy) strategies.get(i);
			strategy.saveAudits();
        }
	}

}