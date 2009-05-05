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
package oscar.oscarDB;

import java.util.Properties;

import oscar.OscarProperties;
;

public class OscarHibernateProperties extends Properties {
	public OscarHibernateProperties(){
		//get db config from oscar properties
		Properties opr=OscarProperties.getInstance();
		setProperty("hibernate.connection.url",opr.getProperty("db_uri")+OscarProperties.getInstance().getProperty("db_name"));
		setProperty("hibernate.connection.username",OscarProperties.getInstance().getProperty("db_username"));
		setProperty("hibernate.connection.password",OscarProperties.getInstance().getProperty("db_password"));
		setProperty("hibernate.connection.driver_class",OscarProperties.getInstance().getProperty("db_driver"));
		setProperty("hibernate.dialect",opr.getProperty("hibernate.dialect"));
	}
}
