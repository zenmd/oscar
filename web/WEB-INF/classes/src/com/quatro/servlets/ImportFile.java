package com.quatro.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;


import org.oscarehr.casemgmt.model.ClientImage;
import org.oscarehr.util.SpringUtils;
import java.util.List;
import com.quatro.model.LookupCodeValue;
import oscar.OscarProperties;

import com.quatro.service.LookupManager;

public class ImportFile extends HttpServlet {

	public void init(ServletConfig servletConfig) throws ServletException {
	        super.init(servletConfig);
	        OscarProperties p = OscarProperties.getInstance();
	       
	        LookupManager lookupManager = (LookupManager) SpringUtils.getBean("lookupManager");
	        List prps = lookupManager.LoadCodeList("PRP",true, null, null);
	        for(int i=0; i<prps.size();i++)
	        {
	        	LookupCodeValue pv = (LookupCodeValue) prps.get(i);
	        	p.setProperty(pv.getDescription(), pv.getBuf1());
	        }
	    }
	protected String getImageFilename(String demoNo, HttpServletRequest request) {
		ClientImage img = null; //clientImageMgr.getClientImage(demoNo);
		
		if(img != null) {
			String path=request.getSession(true).getServletContext().getRealPath("/");
			int encodedValue = (int)(Math.random()*Integer.MAX_VALUE);
			String filename = "client" +encodedValue+"."+ img.getImage_type();
			try {
				java.io.FileOutputStream os= new java.io.FileOutputStream(path+"/images/"+filename);
				os.write(img.getImage_data());
				os.flush();
				os.close();
				return filename;
			}catch(Exception e) {
				//log.warn(e);
			}
		}
		return null;
	}

}
