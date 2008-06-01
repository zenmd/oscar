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

    private static Log log = LogFactory.getLog(ProgramClientRestrictionDAO.class);

    public Collection find(int programId, int demographicNo) {

        List pcrs = getHibernateTemplate().find("from ProgramClientRestriction pcr where pcr.enabled = true and pcr.programId = ? and pcr.demographicNo = ? order by pcr.startDate", new Object[]{new Integer(programId), new Integer(demographicNo)});
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
    
    public List findAllForClient(Integer demographicNo,String providerNo,Integer facilityId) {
    	String sql ="from ProgramClientRestriction pcr where pcr.demographicNo = ? and pcr.programId in " +Utility.getUserOrgQueryString(facilityId) +" order by pcr.endDate desc";
        Object[] params=null;
        if(facilityId.intValue()==0)params=new Object[]{demographicNo,providerNo};
        else params=new Object[]{demographicNo,facilityId,providerNo};
    	List results = getHibernateTemplate().find(sql,params);       
        return results;
    }
    public Collection findForClient(Integer demographicNo) {
        Collection pcrs = getHibernateTemplate().find("from ProgramClientRestriction pcr where pcr.enabled = true and pcr.demographicNo = ? order by pcr.programId", demographicNo);
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

    public Collection findForClient(Integer demographicNo, Integer facilityId) {
        ArrayList paramList = new ArrayList();
        String sSQL="from ProgramClientRestriction pcr where pcr.enabled = true and " +
  		 "pcr.demographicNo = ? and pcr.programId in (select s.id from Program s where s.facilityId = ? or s.facilityId is null) " +
         "order by pcr.programId";
          paramList.add(demographicNo);
          paramList.add(facilityId);
          Object params[] = paramList.toArray(new Object[paramList.size()]);
          Collection pcrs= getHibernateTemplate().find(sSQL, params);
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
        Collection pcrs = getHibernateTemplate().find("from ProgramClientRestriction pcr where pcr.enabled = false and pcr.demographicNo = ? order by pcr.programId", demographicNo);
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

}
