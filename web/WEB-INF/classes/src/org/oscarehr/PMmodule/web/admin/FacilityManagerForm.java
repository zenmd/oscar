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
package org.oscarehr.PMmodule.web.admin;

import org.apache.struts.action.ActionForm;
import org.oscarehr.PMmodule.model.Facility;

/**
 */
public class FacilityManagerForm extends ActionForm {
    private Facility facility;

    public Facility getFacility() {
    	if(facility == null) facility = new Facility();
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }
}
