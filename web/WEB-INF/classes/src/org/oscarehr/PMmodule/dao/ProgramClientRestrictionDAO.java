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
package org.oscarehr.PMmodule.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.caisi.dao.DemographicDAO;
import org.oscarehr.PMmodule.model.ProgramClientRestriction;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.quatro.util.Utility;

import java.util.Iterator;

/**
 */
public class ProgramClientRestrictionDAO extends HibernateDaoSupport {
    private DemographicDAO demographicDAOT;
    private ProgramDao programDao;
    private ProviderDao providerDao;
    private MergeClientDao mergeClientDao;

    private static Log log = LogFactory.getLog(ProgramClientRestrictionDAO.class);

    public Collection find(Integer programId, Integer demographicNo) {
    	String clientIds =mergeClientDao.getMergedClientIds(demographicNo);
        List pcrs = getHibernateTemplate().find("from ProgramClientRestriction pcr where pcr.enabled = true and pcr.programId = ? and pcr.demographicNo in "+clientIds+" order by pcr.startDate",programId);
//        for (ProgramClientRestriction pcr : pcrs) {
        for (int i=0;i<pcrs.size();i++) {
        	ProgramClientRestriction pcr =(ProgramClientRestriction)pcrs.get(i);
            setRelationships(pcr);
        }
        return pcrs;
    }

    public void save(ProgramClientRestriction restriction) {
        getHibernateTemplate().saveOrUpdate(restriction);
    }

    public ProgramClientRestriction find(Integer restrictionId) {
        return setRelationships((ProgramClientRestriction) getHibernateTemplate().get(ProgramClientRestriction.class, restrictionId));
    }

    public Collection findForProgram(Integer programId) {
    	Collection pcrs = getHibernateTemplate().find("from ProgramClientRestriction pcr where pcr.enabled = true and pcr.programId = ? order by pcr.demographicNo", programId);
//        for (ProgramClientRestriction pcr : pcrs) {
//            setRelationships(pcr);
//        }
        Iterator iterator = pcrs.iterator (); 
        while (iterator.hasNext ()) {
       	   ProgramClientRestriction pcr = (ProgramClientRestriction) iterator.next(); 
           setRelationships(pcr);
        }
        return pcrs;
    }

    public Collection findDisabledForProgram(Integer programId) {
        Collection pcrs = getHibernateTemplate().find("from ProgramClientRestriction pcr where pcr.enabled = false and pcr.programId = ? order by pcr.demographicNo", programId);
//        for (ProgramClientRestriction pcr : pcrs) {
//            setRelationships(pcr);
//        }
        Iterator iterator = pcrs.iterator (); 
        while (iterator.hasNext ()) {
        	ProgramClientRestriction pcr = (ProgramClientRestriction) iterator.next(); 
           setRelationships(pcr);
        }
        return pcrs;
    }
    
    public List findAllForClient(Integer demographicNo,String providerNo,Integer shelterId) {
    	String clientIds =mergeClientDao.getMergedClientIds(demographicNo);
    	String sql ="from ProgramClientRestriction pcr where pcr.demographicNo in "+clientIds+" and pcr.programId in " +Utility.getUserOrgQueryString(providerNo,shelterId) +" order by pcr.endDate desc";
    	List results = getHibernateTemplate().find(sql);       
        return results;
    }
    public Collection findForClient(Integer demographicNo) {
    	String clientIds =mergeClientDao.getMergedClientIds(demographicNo);
        Collection pcrs = getHibernateTemplate().find("from ProgramClientRestriction pcr where pcr.enabled = true and pcr.demographicNo in " +clientIds+" order by pcr.programId");
//        for (ProgramClientRestriction pcr : pcrs) {
//            setRelationships(pcr);
//        }
        Iterator iterator = pcrs.iterator (); 
        while (iterator.hasNext ()) {
        	ProgramClientRestriction pcr = (ProgramClientRestriction) iterator.next(); 
            setRelationships(pcr);
        }
        return pcrs;
    }

    public Collection findForClient(Integer demographicNo,String providerNo, Integer shelterId) {        
        String clientIds =mergeClientDao.getMergedClientIds(demographicNo);
        String sSQL="from ProgramClientRestriction pcr where pcr.enabled = true and " +
  		 "pcr.demographicNo in "+clientIds+" and pcr.programId in " + Utility.getUserOrgQueryString(providerNo, shelterId)+
         "order by pcr.programId";        
          Collection pcrs= getHibernateTemplate().find(sSQL, demographicNo);
//          for (ProgramClientRestriction pcr : pcrs) {
//              setRelationships(pcr);
//          }
          Iterator iterator = pcrs.iterator (); 
          while (iterator.hasNext ()) {
          	 ProgramClientRestriction pcr = (ProgramClientRestriction) iterator.next(); 
             setRelationships(pcr);
          }
          return pcrs;
    }

    public Collection findDisabledForClient(Integer demographicNo) {
    	String clientIds =mergeClientDao.getMergedClientIds(demographicNo);
        Collection pcrs = getHibernateTemplate().find("from ProgramClientRestriction pcr where pcr.enabled = false and pcr.demographicNo in "+clientIds+" order by pcr.programId");
//        for (ProgramClientRestriction pcr : pcrs) {
//            setRelationships(pcr);
//        }
        Iterator iterator = pcrs.iterator (); 
        while (iterator.hasNext ()) {
        	 ProgramClientRestriction pcr = (ProgramClientRestriction) iterator.next(); 
             setRelationships(pcr);
        }
        return pcrs;
    }

    private ProgramClientRestriction setRelationships(ProgramClientRestriction pcr) {
        pcr.setClient(demographicDAOT.getDemographic("" + pcr.getDemographicNo()));
        pcr.setProgram(programDao.getProgram(pcr.getProgramId()));
        pcr.setProvider(providerDao.getProvider(pcr.getProviderNo()));
        
        return pcr;
    }

    //@Required
    public void setDemographicDAOT(DemographicDAO demographicDAOT) {
        this.demographicDAOT = demographicDAOT;
    }

    //@Required
    public void setProgramDao(ProgramDao programDao) {
        this.programDao = programDao;
    }

    //@Required
    public void setProviderDao(ProviderDao providerDao) {
        this.providerDao = providerDao;
    }

	public void setMergeClientDao(MergeClientDao mergeClientDao) {
		this.mergeClientDao = mergeClientDao;
	}

}
