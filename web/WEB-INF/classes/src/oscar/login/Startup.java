/**
 * Copyright (c) 2001-2002. Department of Family Medicine, McMaster University. All Rights Reserved. *
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
 * Jason Gallagher
 *
 * This software was written for the
 * Department of Family Medicine
 * McMaster University
 * Hamilton
 * Ontario, Canada   Creates a new instance of Startup
 *
 *
 * Startup.java
 *
 * Created on September 22, 2005, 3:13 PM
 */
package oscar.login;

import java.io.InputStream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This ContextListener is used to Initialize classes at startup - Initialize the DBConnection Pool.
 * 
 * @author Jay Gallagher
 */
public class Startup implements ServletContextListener {
        private static Logger log = LogManager.getLogger(Startup.class);
	public Startup() {}

	public void contextInitialized(ServletContextEvent sc) {
		System.out.println("contextInit");
		
		String contextPath = "";
		try {
//			String webInfDir = sc.getServletContext().getResource("/WEB-INF").getPath();
	        System.setProperty("log4j.configuration","log4j.properties");

	        String url = sc.getServletContext().getResource("/").getPath();
            int idx = url.lastIndexOf('/');
			url = url.substring(0,idx);

			idx = url.lastIndexOf('/');
			url = url.substring(idx+1);

			idx = url.lastIndexOf('.');
			if (idx > 0) url = url.substring(0,idx);

			contextPath = url;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String propName = contextPath + ".properties";
        oscar.OscarProperties p = oscar.OscarProperties.getInstance();
		try {
	        log.info("looking up  /WEB-INF" + propName);
			InputStream pf = sc.getServletContext().getResource("/WEB-INF/" + propName).openStream();
			p.loader(pf);
			p.setProperty("contextPath", contextPath);
	        log.info("loading properties from /WEB-INF/" + propName);
		}
		catch(java.io.FileNotFoundException e)
		{
			System.err.println("Configuration file: " + propName + " cannot be found, it should be put in WEB-INF folder");
			return;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
		System.out.println("LAST LINE IN contextInitialized");
                
	}
	public void contextDestroyed(ServletContextEvent arg0) {}

}
