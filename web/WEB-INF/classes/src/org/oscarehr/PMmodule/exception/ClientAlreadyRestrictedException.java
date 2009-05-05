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

/**
 */
public class ClientAlreadyRestrictedException extends Exception {
    public ClientAlreadyRestrictedException() {
    }

    public ClientAlreadyRestrictedException(String s) {
        super(s);
    }

    public ClientAlreadyRestrictedException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ClientAlreadyRestrictedException(Throwable throwable) {
        super(throwable);
    }
}
