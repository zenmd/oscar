package org.oscarehr.survey.service;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OscarFormManager {

	public List getForms();
	public void generateCSV(Integer formId, OutputStream out);
	public void convertFormXMLToDb(Integer formId);
	public Map<String[],String> getFormReport(Integer formId, Date startDate, Date endDate);
}
