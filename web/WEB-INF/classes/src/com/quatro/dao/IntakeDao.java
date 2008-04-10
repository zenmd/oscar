package com.quatro.dao;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.quatro.model.QuatroIntakeOptionValue;


public class IntakeDao extends HibernateDaoSupport {

	public List LoadOptionsList() {
		String sSQL="from QuatroIntakeOptionValue s order by s.prefix, s.displayOrder";		
        return getHibernateTemplate().find(sSQL);
	}

}
