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
package org.caisi.dao;

import java.util.List;

import org.caisi.model.RedirectLink;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class RedirectLinkDao extends AbstractDao{

	public RedirectLink find(int id) {

		Session session = sessionFactory.openSession();
		try {
			return((RedirectLink)session.get(RedirectLink.class, String.valueOf(id)));
		}
		finally {
			session.close();
		}
	}

	public void save(RedirectLink redirectLink) {

		Session session = sessionFactory.openSession();
		try {
			Transaction tx=session.beginTransaction();
			session.save(redirectLink);
			tx.commit();
		}
		finally {
			session.close();
		}
	}


    public List findAll() {

		Session session = sessionFactory.openSession();
		try {
			Query query=session.createQuery("from RedirectLink");
			
//			@SuppressWarnings("unchecked")
			List results=query.list();
			
			return(results);
		}
		finally {
			session.close();
		}
	}

}
