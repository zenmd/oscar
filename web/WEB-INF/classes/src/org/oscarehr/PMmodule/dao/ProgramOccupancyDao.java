package org.oscarehr.PMmodule.dao;

import java.util.Calendar;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.oscarehr.PMmodule.model.ProgramQueue;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;



import com.quatro.model.ProgramOccupancy;

public class ProgramOccupancyDao extends HibernateDaoSupport {
	private Log log = LogFactory.getLog(ProgramOccupancyDao.class);


    public void insertProgramOccupancy(String providerNo,Calendar occDate) {
    	
     String sql=" insert into program_occupancy(recordid,occdate,program_id,occupancy," +
                "capacity_actual,capacity_funding,queue,lastupdateuser,lastupdatedate)" +
                "select seq_program_occupancy.nextval,?,p.program_id,ad.v_occupancy,"+
                "pr.v_actualCapacity,p.Capacity_funding,pq.v_queue,?,sysdate" +
                "from program p,"+
                "(select program_id, count(*) v_occupancy from admission where admission_status='admitted' group by program_id) ad,"+
                "(select r.program_id, sum(r.beds) v_actualCapacity from v_room_beds r group by r.program_id) pr,"+
                "(select program_id, count(*) v_queue from program_queue pq group by program_id) pq" +
                " where p.program_id = ad.program_id and p.program_id=pr.program_id and p.program_id = pq.program_id(+)" ;
        SQLQuery query=getSession().createSQLQuery(sql);
        query.setCalendar(0, occDate);
        query.setString(1, providerNo);
        query.executeUpdate();
    }
    public void deleteProgramOccupancy(Calendar occDate) {
    	
        String sql=" delete from program_occupancy" +
                   "where trunc(occdate)=?";
           SQLQuery query=getSession().createSQLQuery(sql);
           query.setCalendar(0, DateUtils.truncate(occDate, Calendar.DATE));        
           query.executeUpdate();
    }
}
