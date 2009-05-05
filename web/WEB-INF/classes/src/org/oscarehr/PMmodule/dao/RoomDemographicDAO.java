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
* Toronto, Ontario, Canada  - UPDATED: Quatro Group 2008/2009
*/

package org.oscarehr.PMmodule.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.oscarehr.PMmodule.model.RoomDemographic;
import org.oscarehr.PMmodule.model.RoomDemographicPK;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Hibernate implementation of RoomDemographicDAO
 */
public class RoomDemographicDAO extends HibernateDaoSupport {

    private static final Log log = LogFactory.getLog(RoomDemographicDAO.class);
    private MergeClientDao mergeClientDao;
    public void setMergeClientDao(MergeClientDao mergeClientDao) {
		this.mergeClientDao = mergeClientDao;
	}

	/**
     * @see org.oscarehr.PMmodule.dao.RoomDemographicDAO#roomDemographicExists(java.lang.Integer)
     */
    public boolean roomDemographicExists(Integer demographicNo) {
        boolean exists = (((Long)getHibernateTemplate().iterate("select count(*) from RoomDemographic rd where rd.id.demographicNo = " + demographicNo).next()).intValue() == 1);
        log.debug("roomExists: " + exists);

        return exists;
    }

    boolean roomDemographicExists(RoomDemographicPK id) {
        boolean exists = (((Long)getHibernateTemplate().iterate("select count(*) from RoomDemographic rd where rd.id.roomId = " + id.getRoomId() + " and rd.id.demographicNo = " + id.getDemographicNo()).next()).intValue() == 1);
        log.debug("roomDemographicExists: " + exists);

        return exists;
    }


    public int getRoomOccupanyByRoom(Integer roomId) {
    	Long count = (Long)getHibernateTemplate().find("select count(*) from RoomDemographic rd where rd.id.roomId = ?", roomId).get(0);
        return count.intValue();
    }

    /**
     * @see org.oscarehr.PMmodule.dao.RoomDemographicDAO#getRoomDemographicByRoom(java.lang.Integer)
     */
    public List getRoomDemographicByRoom(Integer roomId) {
        List roomDemographics = getHibernateTemplate().find("from RoomDemographic rd where rd.id.roomId = ?", roomId);

        return roomDemographics;
    }
    public List getRoomDemographicByBed(Integer bedId){
        List roomDemographics = getHibernateTemplate().find("from RoomDemographic rd where rd.bedId = ?", bedId);

        return roomDemographics;
    }
    /**
     * @see org.oscarehr.PMmodule.dao.RoomDemographicDAO#getRoomDemographicByDemographic(java.lang.Integer)
     */
    public RoomDemographic getRoomDemographicByDemographic(Integer demographicNo) {
   	    String clientIds =mergeClientDao.getMergedClientIds(demographicNo);
        List roomDemographics = getHibernateTemplate().find("from RoomDemographic rd where rd.id.demographicNo in "+clientIds);
        if(roomDemographics == null){
        	return null;
        }
        if (roomDemographics.size() > 1) {
            throw new IllegalStateException("Client is assigned to more than one room");
        }

        RoomDemographic roomDemographic = (RoomDemographic)((roomDemographics.size() == 1)? roomDemographics.get(0): null);

        log.debug("getRoomDemographicByDemographic: " + demographicNo);
        return roomDemographic;
    }

    public RoomDemographic getRoomDemographicByAdmissionId(Integer admissionId) {
        List roomDemographics = getHibernateTemplate().find("from RoomDemographic rd where rd.admissionId=?", admissionId);
        if(roomDemographics == null){
        	return null;
        }
        if (roomDemographics.size() > 1) {
            throw new IllegalStateException("Client is assigned to more than one room");
        }

        RoomDemographic roomDemographic = (RoomDemographic)((roomDemographics.size() == 1)? roomDemographics.get(0): null);

        return roomDemographic;
    }
    
    /**
     * @see org.oscarehr.PMmodule.dao.RoomDemographicDAO#saveRoomDemographic(org.oscarehr.PMmodule.model.RoomDemographic)
     */
    public void saveRoomDemographic(RoomDemographic roomDemographic) {
        //updateHistory(roomDemographic);
    	try{
        getHibernateTemplate().saveOrUpdate(roomDemographic);
        getHibernateTemplate().flush();
        
        log.debug("saveRoomDemographic: " + roomDemographic);
    	}catch(Exception ex){
    		String a = ex.getMessage();
    	}
    }

    public void deleteRoomDemographic(RoomDemographic roomDemographic) {
         // delete
        getHibernateTemplate().delete(roomDemographic);
        getHibernateTemplate().flush();
    }
    public void deleteRoomDemographic(String clients,Integer roomId){
    	
    	String sql ="delete from RoomDemographic where rd.id.demographicNo in ("+clients+") and roomId=?";
    	getHibernateTemplate().update(sql,roomId); 
    	getHibernateTemplate().flush();
    }

}
