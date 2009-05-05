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
package org.oscarehr.PMmodule.exception;

import org.oscarehr.PMmodule.model.ProgramClientRestriction;

/**
 */
public class ServiceRestrictionException extends Exception {

    private ProgramClientRestriction restriction;

    public ServiceRestrictionException(ProgramClientRestriction restriction) {
        this.restriction = restriction;
    }

    public ServiceRestrictionException(String s, ProgramClientRestriction restriction) {
        super(s);
        this.restriction = restriction;
    }

    public ServiceRestrictionException(String s, Throwable throwable, ProgramClientRestriction restriction) {
        super(s, throwable);
        this.restriction = restriction;
    }

    public ServiceRestrictionException(Throwable throwable, ProgramClientRestriction restriction) {
        super(throwable);
        this.restriction = restriction;
    }

    public ProgramClientRestriction getRestriction() {
        return restriction;
    }
}
