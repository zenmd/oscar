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
package com.quatro.service;

import com.quatro.dao.TopazDao;
import com.quatro.model.signaturePad.TopazValue;

public class TopazManager {
    private TopazDao topazDao;

    public void setTopazDao(TopazDao topazDao) {
        this.topazDao = topazDao;
    }

    public TopazValue getTopazValue(Integer recordId,String moduleCd){
        return topazDao.getTopazValue(recordId,moduleCd);
    }

    public boolean isSignatureExist(Integer recordId){
        return topazDao.isSignatureExist(recordId);
    }
    
    public void saveTopazValue(TopazValue tv){
        topazDao.saveTopazValue(tv);
    }
    
    public void deleteSignature(Integer recordId){
        topazDao.deleteSignature(recordId);
    }

}
