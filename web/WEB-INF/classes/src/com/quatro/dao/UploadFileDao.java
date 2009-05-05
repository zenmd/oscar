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
package com.quatro.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.oscarehr.PMmodule.dao.MergeClientDao;
import org.oscarehr.casemgmt.dao.CaseManagementNoteDAO;
import org.oscarehr.casemgmt.model.CaseManagementNote;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.quatro.model.AttachmentText;
import com.quatro.model.Attachment;
import com.quatro.util.Utility;

public class UploadFileDao extends HibernateDaoSupport {

	private static Logger log = LogManager.getLogger(CaseManagementNoteDAO.class);
	private MergeClientDao mergeClientDao;
	public void saveAttachementText(AttachmentText rtv) {
		getHibernateTemplate().saveOrUpdate(rtv);
	}

	public AttachmentText getAttachmentText(Integer docId) {
		return (AttachmentText) getHibernateTemplate().get(
				AttachmentText.class, docId);
	}

	public void saveAttachement(Attachment atv) {
		try{
		getHibernateTemplate().saveOrUpdate(atv);
		atv.getAttText().setDocId(atv.getId());
		atv.getAttText().setRevDate(atv.getRevDate());
		if(null!=atv.getAttText().getAttData());
		getHibernateTemplate().saveOrUpdate(atv.getAttText());
		}catch(Exception e)
		{
			;
		}
	}
   public void deleteAttachment(Integer docId){
	   getHibernateTemplate().delete(getAttachment(docId));
   }
	public Attachment getAttachment(Integer docId) {
		return (Attachment) getHibernateTemplate().get(Attachment.class, docId);
	}
	public int getAttachment(String refNo, String fileName, int fileSize){
		String clientIds=mergeClientDao.getMergedClientIds(Integer.valueOf(refNo));		
		String hql = " from Attachment t where t.refNo in "+ clientIds+ " and t.fileName=? and t.fileSize=? " ;
		List lst =getHibernateTemplate().find(hql,	new Object[]{fileName,new Integer(fileSize)});
	 int count=0;
	 if(lst.size()>0) count=lst.size();
		return count;
		
	}
	public List getAttach(Integer moduleId, String refNo, String providerNo, Integer shelterId) {			
		String clientIds=mergeClientDao.getMergedClientIds(Integer.valueOf(refNo));		
		String hql = " from Attachment t where t.moduleId = ? and t.refNo in "+ clientIds+ " and t.refProgramId in " +
		Utility.getUserOrgQueryString(providerNo,shelterId) + " order by t.revDate desc";
		List lst =getHibernateTemplate().find(hql,	moduleId);
		return lst;
	}

	public void setMergeClientDao(MergeClientDao mergeClientDao) {
		this.mergeClientDao = mergeClientDao;
	}
}
