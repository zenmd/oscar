/*
* 
* Copyright (c) 2001-2002. Centre for Research on Inner City Health, St. Michael's Hospital, Toronto. All Rights Reserved. *
* This software is published under the GPL GNU General Public License. 
* This program is free software; you can redistribute it and/or 
* modify it under the terms of the GNU General Public License 
* as published by the Free Software Foundation; either version 2 
* of the License, or (at your option) any later version. * 
* This program is distributed in the hope that it will be useful, 
* but WITHOUT ANY WARRANTY; without even the implied warranty of 
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
* GNU General Public License for more details. * * You should have received a copy of the GNU General Public License 
* along with this program; if not, write to the Free Software 
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA. * 
* 
* <OSCAR TEAM>
* 
* This software was written for 
* Centre for Research on Inner City Health, St. Michael's Hospital, 
* Toronto, Ontario, Canada 
*/

package org.oscarehr.PMmodule.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.ClientReferral;
import org.oscarehr.PMmodule.model.ProgramQueue;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.quatro.common.KeyConstants;

public class ProgramQueueDao extends HibernateDaoSupport {

    private Log log = LogFactory.getLog(ProgramQueueDao.class);


    public ProgramQueue getProgramQueue(Integer queueId) {
        if (queueId == null || queueId.intValue() <= 0) {
            throw new IllegalArgumentException();
        }

        ProgramQueue result = (ProgramQueue) getHibernateTemplate().get(ProgramQueue.class, queueId);

        if (log.isDebugEnabled()) {
            log.debug("getProgramQueue: queueId=" + queueId + ",found=" + (result != null));
        }

        return result;
    }

    public ProgramQueue getProgramQueueByIntakeId(Integer intakeId) {
        String queryStr = " FROM ProgramQueue q WHERE q.fromIntakeId=?";
        List results = getHibernateTemplate().find(queryStr, intakeId);
        if(results.size()==0) return null;
        return (ProgramQueue)results.get(0);
    }
    
    public ProgramQueue getProgramQueueByReferralId(Integer referralId) {
        if (referralId == null) {
            throw new IllegalArgumentException();
        }

        String queryStr = " FROM ProgramQueue q WHERE q.ReferralId=?";
        List results = getHibernateTemplate().find(queryStr, referralId);

        if(results.size()==0) return null;
        return (ProgramQueue)results.get(0);
    }
    
    public List getProgramQueuesByProgramId(Integer programId) {
        if (programId == null) {
            throw new IllegalArgumentException();
        }

        String queryStr = " FROM ProgramQueue q WHERE q.ProgramId=? ORDER BY  q.Id  ";
        List results = getHibernateTemplate().find(queryStr, programId);

        if (log.isDebugEnabled()) {
            log.debug("getProgramQueue: programId=" + programId + ",# of results=" + results.size());
        }

        return results;
    }

    public List getProgramQueuesByClientId(Integer clientId) {
        if (clientId == null) {
            throw new IllegalArgumentException();
        }

        String queryStr = " FROM ProgramQueue q WHERE q.ClientId=? ORDER BY  q.Id  ";
        List results = getHibernateTemplate().find(queryStr, clientId);

        return results;
    }

    public List getProgramQueuesByClientIdProgramId(Integer clientId, Integer programId) {
        if (clientId == null || programId==null) {
            throw new IllegalArgumentException();
        }

        String queryStr = " FROM ProgramQueue q WHERE q.ClientId=? and q.ProgramId=? ORDER BY  q.Id  ";
        List results = getHibernateTemplate().find(queryStr, new Object[] {clientId, programId});

        return results;
    }

    public void saveProgramQueue(ProgramQueue programQueue) {
        if (programQueue == null) {
            return;
        }

        getHibernateTemplate().saveOrUpdate(programQueue);

        if (log.isDebugEnabled()) {
            log.debug("saveProgramQueue: id=" + programQueue.getId());
        }

    }
    
    public void delete(ProgramQueue pqObj){
    	getHibernateTemplate().delete(pqObj);
    }

    public ProgramQueue getQueue(Integer programId, Integer clientId) {
        if (programId == null) {
            throw new IllegalArgumentException();
        }
        if (clientId == null) {
            throw new IllegalArgumentException();
        }

        ProgramQueue result = null;
        List results = this.getHibernateTemplate().find("from ProgramQueue pq where pq.ProgramId = ? and pq.ClientId = ?",
                new Object[]{programId, clientId});

        if (!results.isEmpty()) {
            result = (ProgramQueue) results.get(0);
        }

        if (log.isDebugEnabled()) {
            log.debug("getQueue: programId=" + programId + ",clientId=" + clientId + ",found=" + (result != null));
        }

        return result;
    }
    
	public void setIntakeRejectStatus(String intakeIds) {
       if(intakeIds==null || intakeIds.length()==0) return;
	   String[] split = intakeIds.split(",");
       for(int i=0;i<split.length;i++){
    	 Integer intakeId= Integer.valueOf(split[i]);  
	     getHibernateTemplate().bulkUpdate("update QuatroIntakeDB q set q.intakeStatus='" +
            KeyConstants.INTAKE_STATUS_REJECTED + "' where q.id=?", intakeId);
       }  
	}
	
}
