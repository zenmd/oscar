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

package oscar.oscarTags;

import java.util.ArrayList;


public class TagObject {
    private ArrayList assignedTags;
    private String objectId;
    private String objectClass;
    
    public void assignTag(String tagName) {
        getAssignedTags().add(tagName);
    }

    public ArrayList getAssignedTags() {
        return assignedTags;
    }

    public void setAssignedTags(ArrayList assignedTags) {
        this.assignedTags = assignedTags;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }
    
    
    
}
