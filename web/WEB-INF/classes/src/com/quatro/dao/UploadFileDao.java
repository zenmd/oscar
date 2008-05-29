package com.quatro.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.oscarehr.casemgmt.dao.CaseManagementNoteDAO;
import org.oscarehr.casemgmt.model.CaseManagementNote;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.quatro.model.AttachmentText;
import com.quatro.model.Attachment;

public class UploadFileDao extends HibernateDaoSupport {

	private static Log log = LogFactory.getLog(CaseManagementNoteDAO.class);

	public void saveAttachementText(AttachmentText rtv) {
		getHibernateTemplate().saveOrUpdate(rtv);
	}

	public AttachmentText getAttachmentText(Integer docId) {
		return (AttachmentText) getHibernateTemplate().get(
				AttachmentText.class, docId);
	}

	public void saveAttachement(Attachment atv) {
		getHibernateTemplate().saveOrUpdate(atv);
		atv.getAttText().setDocId(atv.getId());
		atv.getAttText().setRevDate(atv.getRevDate());
		if(null!=atv.getAttText().getAttData());
		getHibernateTemplate().saveOrUpdate(atv.getAttText());
		
	}
   public void deleteAttachment(Integer docId){
	   getHibernateTemplate().delete(getAttachment(docId));
   }
	public Attachment getAttachment(Integer docId) {
		return (Attachment) getHibernateTemplate().get(Attachment.class, docId);
	}

	public List<Attachment> getAttach(Integer moduleId, String refNo, String providerNo, Integer facilityId) {
		String progSQL = "";
		String hql = "";
		Object [] params = null;
		if (facilityId == 0) {
			progSQL = "(select p.id from Program p where  'P' || p.id in (select a.code from LstOrgcd a, Secuserrole b " +
			" where a.fullcode like '%' || b.orgcd || '%' and b.providerNo=?))";
			params = new Object[] { moduleId, refNo,providerNo };
		}	else {
			progSQL = "(select p.id from Program p where p.facilityId =? and 'P' || p.id in (select a.code from LstOrgcd a, Secuserrole b " +
		       " where a.fullcode like '%' || b.orgcd || '%' and b.providerNo=?))";
			params = new Object[] { moduleId, refNo,facilityId, providerNo };		
		}

		hql = " from Attachment t where t.moduleId = ? and t.refNo=? and t.refProgramId in " +
				progSQL + " order by t.revDate desc";
		List<Attachment> lst =getHibernateTemplate().find(hql,	params);
		return lst;
	}
}
