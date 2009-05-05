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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class AuditStrategyFactory {
	
	private static class AuditStrategyKey {
		private Class clazz;
		private int event;

		AuditStrategyKey(Class clazz, int event) {
			this.clazz = clazz;
			this.event = event;
		}

		Class getClazz() {
			return clazz;
		}

		int getEvent() {
			return event;
		}
		
//		@Override
		public int hashCode() {
			return HashCodeBuilder.reflectionHashCode(this);
		}
		
//		@Override
		public boolean equals(Object rhs) {
		    return EqualsBuilder.reflectionEquals(this, rhs);
		}
	}

	private static final Map strategies = new HashMap();

	public static AuditStrategy create(Object entity, int event) {
		if (entity == null || event > 2) {
			throw new IllegalArgumentException("Parameters entity and event must be non-null");
		}

		return (AuditStrategy) strategies.get(new AuditStrategyKey(entity.getClass(), event));
	}

	public static void register(Class clazz, int event, AuditStrategy strategy) {
		if (clazz == null || event > 2 || strategy == null) {
			throw new IllegalArgumentException("Parameters class, event and strategy must be non-null");
		}

		strategies.put(new AuditStrategyKey(clazz, event), strategy);
	}

	private AuditStrategyFactory() {}

}