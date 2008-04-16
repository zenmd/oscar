package com.quatro.dao;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.quatro.model.QuatroIntakeOptionValue;
import org.oscarehr.PMmodule.model.QuatroIntake;


public class IntakeDao extends HibernateDaoSupport {

	public List LoadOptionsList() {
		String sSQL="from QuatroIntakeOptionValue s order by s.prefix, s.displayOrder";		
        return getHibernateTemplate().find(sSQL);
	}

	public QuatroIntake getQuatroIntake(Integer intakeId) {
		List result = getHibernateTemplate().find("from QuatroIntake i where i.id = ?",
		  new Object[] {intakeId});

		if(result.size()==0)
		  return null;
		else
		  return (QuatroIntake)result.get(0);
		
	}

	public List getQuatroIntakeHeaderListByFacility(Integer clientId, Integer facilityId, String providerNo) {
//		select * from Intake i where i.client_id = 200492 and i.program_id in 
//		(select p.program_id from program p, program_provider q where p.facility_id =200058 
//and p.program_id=q.program_id and q.provider_no=999998) order by i.creation_date desc
		List results = getHibernateTemplate().find("from QuatroIntakeHeader i where i.clientId = ? and i.programId in " +
			"(select p.id from Program p, ProgramProvider q where p.facilityId =?" + 
			" and p.id= q.ProgramId and q.ProviderNo=?) order by i.createdOn desc",
			new Object[] {clientId, new Long(facilityId.longValue()), providerNo });

		return results;
	}

	public void saveQuatroIntake(QuatroIntake intake){
	     getHibernateTemplate().saveOrUpdate(intake);
	}
	
}
