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

public class ShelterPopulation {

	private int pastYear;
	private int current;

	public ShelterPopulation(int pastYear, int current) {
		this.pastYear = pastYear;
		this.current = current;
	}
	
	public int getPastYear() {
		return pastYear;
	}

	public int getCurrent() {
		return current;
	}

}
