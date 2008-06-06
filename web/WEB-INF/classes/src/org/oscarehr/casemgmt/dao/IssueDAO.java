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

package org.oscarehr.casemgmt.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.oscarehr.casemgmt.model.Issue;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class IssueDAO extends HibernateDaoSupport {

    public Issue getIssue(Integer id) {
        return (Issue)this.getHibernateTemplate().get(Issue.class, id);
    }

    public List getIssues() {
        return this.getHibernateTemplate().find("from Issue");
    }

    public List findIssueByCode(String[] codes) {
        String code = StringUtils.join(codes,",");
        return this.getHibernateTemplate().find("from Issue i where i.code in (?)", new Object[] {code});
    }
    
    public Issue findIssueByCode(String code) {        
        List list = this.getHibernateTemplate().find("from Issue i where i.code = ?", new Object[] {code});
        if( list != null )
            return (Issue)list.get(0);
        
        return null;
    }

    public void saveIssue(Issue issue) {
        this.getHibernateTemplate().saveOrUpdate(issue);
    }

    public List findIssueBySearch(String search) {
        search = "%" + search + "%";
        search = search.toLowerCase();
        String sql = "from Issue i where lower(i.code) like ? or lower(i.description) like ?";
        return this.getHibernateTemplate().find(sql, new Object[] {search, search});
    }

    public List search(String search,String providerNo, String programId) {

        StringBuffer buf = new StringBuffer();

        search = "%" + search + "%";
        search = search.toLowerCase();
        String sql = "from Issue i where (lower(i.code) like ? or lower(i.description) like ?)";
        return this.getHibernateTemplate().find(sql, new Object[] {search, search});
    }

    public List searchNoRolesConcerned(String search) {
        search = "%" + search + "%";
        search = search.toLowerCase();
        String sql = "from Issue i where (lower(i.code) like ? or lower(i.description) like ?)";
        System.out.println(sql);
        return this.getHibernateTemplate().find(sql, new Object[] {search, search});
    }
}
