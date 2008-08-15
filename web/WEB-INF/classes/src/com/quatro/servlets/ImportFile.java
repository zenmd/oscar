package com.quatro.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import org.oscarehr.PMmodule.model.FieldDefinition;
import org.oscarehr.PMmodule.model.SdmtIn;

import org.oscarehr.casemgmt.model.ClientImage;
import org.oscarehr.util.SpringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.quatro.model.LookupCodeValue;
import oscar.OscarProperties;

import com.quatro.service.LookupManager;

public class ImportFile extends HttpServlet {

	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		OscarProperties p = OscarProperties.getInstance();

		LookupManager lookupManager = (LookupManager) SpringUtils
				.getBean("lookupManager");
		List prps = lookupManager.LoadCodeList("PRP", true, null, null);
		for (int i = 0; i < prps.size(); i++) {
			LookupCodeValue pv = (LookupCodeValue) prps.get(i);
			p.setProperty(pv.getDescription(), pv.getBuf1());
		}
	}
	protected ArrayList getTemplate(HttpServletRequest request,String dir,String filename) {
		FieldDefinition fDev = null; // clientImageMgr.getClientImage(demoNo);
		ArrayList list = new ArrayList();

		String path = request.getSession(true).getServletContext().getRealPath("/");
		//String filename = "sdmt_out_template.txt";
		try {

			BufferedReader in=null;

			try {			
				
			        in= new BufferedReader(new FileReader(path + "/"+dir+"/"+ filename));
			        String str;
			        while ((str = in.readLine()) != null) {
			           fDev = new FieldDefinition();
			           fDev.setFieldName(str.substring(0,30));
			           fDev.setFieldLength(new Integer(str.substring(30,35)));
			           fDev.setFieldType(str.substring(35,36));
			           list.add(fDev);
			        }
			        in.close();


			} catch (IOException e) {
				// catch io errors from FileInputStream or readLine()
				System.out.println("Uh oh, got an IOException error!"
						+ e.getMessage());

			} finally {
				if(in!=null) in.close();				
			}

		} catch (Exception e) {
			// log.warn(e);
		}

		return list;
	}

	protected void outputSDMT(HttpServletRequest request, List clientInfo) {

		String path = request.getSession(true).getServletContext().getRealPath("/");
		String year = (new Integer(Calendar.getInstance().YEAR)).toString();
		String month = (new Integer(Calendar.getInstance().MONTH)).toString();
		String day = (new Integer(Calendar.getInstance().DATE)).toString();
		String hour = (new Integer(Calendar.getInstance().HOUR)).toString();
		String min = (new Integer(Calendar.getInstance().MINUTE)).toString();
		String filename = year + month + day + hour + min + ".out";
		try {
			//java.io.FileOutputStream os = new java.io.FileOutputStream(path	+ "/out/" + filename);
			FileWriter fstream = new FileWriter(path	+ "/out/" + filename);
		    BufferedWriter out = new BufferedWriter(fstream);
		    for(int i=0;i<clientInfo.size();i++){  
		    	SdmtIn sdVal = (SdmtIn)clientInfo.get(i);
		    	ArrayList tempLst = getTemplate(request, "/out/template/", "sdmt_out_template.txt");
		    	//out.write();
		    }		      
		      out.close();			
		} catch (Exception e) {
			// log.warn(e);
		}

	}

}
