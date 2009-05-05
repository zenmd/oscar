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