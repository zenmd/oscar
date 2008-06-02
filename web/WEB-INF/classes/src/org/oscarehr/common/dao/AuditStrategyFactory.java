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