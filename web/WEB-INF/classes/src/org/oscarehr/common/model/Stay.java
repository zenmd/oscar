package org.oscarehr.common.model;

import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Interval;

public class Stay {
	
	private static final Logger LOG = LogManager.getLogger(Stay.class);

	private Interval interval;

	public Stay(Date admission, Date discharge, Date start, Date end) {
		DateTime admissionDateTime = admission.after(start) ? new DateTime(admission) : new DateTime(start);
		DateTime dischargeDateTime = (discharge != null) ? new DateTime(discharge) : new DateTime(end);
		
		try {
			interval = new Interval(admissionDateTime, dischargeDateTime);
		} catch (IllegalArgumentException e) {
			LOG.error(e);
			LOG.error("admission: " + admission + " discharge: " + discharge);
			LOG.error("admission datetime: " + admissionDateTime + " discharge datetime: " + dischargeDateTime);
			
			throw e;
		}
	}
	
	public Interval getInterval() {
		return interval;
	}

}