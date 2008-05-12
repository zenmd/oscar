package org.oscarehr.PMmodule.dao;

import org.oscarehr.PMmodule.model.QuatroAdmission;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class QuatroAdmissionDao extends HibernateDaoSupport {
    public void saveAdmission(QuatroAdmission admission) {
        if (admission == null) {
            throw new IllegalArgumentException();
        }

        getHibernateTemplate().saveOrUpdate(admission);
    }

}
