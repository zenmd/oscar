package com.quatro.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.oscarehr.util.SpringUtils;
import java.util.List;
import com.quatro.model.LookupCodeValue;
import oscar.OscarProperties;

import com.quatro.service.LookupManager;
public class InitPropertyServlet extends HttpServlet {
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
}
