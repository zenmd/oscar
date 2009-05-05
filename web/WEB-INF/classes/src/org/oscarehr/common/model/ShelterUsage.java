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
package org.oscarehr.common.model;

public class ShelterUsage {

	private int low;
	private int medium;
	private int high;

	public ShelterUsage(int low, int medium, int high) {
		this.low = low;
		this.medium = medium;
		this.high = high;
	}

	public int getLow() {
    	return low;
    }

	public int getMedium() {
    	return medium;
    }

	public int getHigh() {
    	return high;
    }
	
}