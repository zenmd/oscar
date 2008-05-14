package org.oscarehr.survey.service.impl;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.oscarehr.survey.dao.oscar.OscarFormDAO;
import org.oscarehr.survey.service.OscarFormManager;

public class OscarFormManagerImpl implements OscarFormManager {

	private OscarFormDAO dao;
	
	public void setOscarFormDAO(OscarFormDAO dao) {
		this.dao = dao;
	}
	
	public List getForms() {
		return dao.getOscarForms();
	}
	
	public void generateCSV(Integer formId, OutputStream out) {
		dao.generateCSV(formId, out);
	}

	public void convertFormXMLToDb(Integer formId) {
		dao.convertFormXMLToDb(formId);
	}
	
	public Map<String[],String> getFormReport(Integer formId, Date startDate, Date endDate) {
		return dao.getFormReport(formId, startDate, endDate);
	}
}
