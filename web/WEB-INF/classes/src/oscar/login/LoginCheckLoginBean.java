/*
 * Copyright (c) 2005. Department of Family Medicine, McMaster University. All Rights Reserved. *
 * This software is published under the GPL GNU General Public License. This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version. * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. * * You should have
 * received a copy of the GNU General Public License along with this program; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA. * <OSCAR
 * TEAM> This software was written for the Department of Family Medicine McMaster Unviersity
 * Hamilton Ontario, Canada
 */
package oscar.login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.ibm.ws.webcontainer.httpsession.DBPortability;

import oscar.log.LogAction;
import oscar.log.LogConst;
import oscar.oscarDB.DBPreparedHandler;
import oscar.util.UtilDateUtilities;

public class LoginCheckLoginBean {
	private static final Logger _logger = Logger
			.getLogger(LoginCheckLoginBean.class);

	private static final String LOG_PRE = "Login!@#$: ";

	private String username = "";

	private String password = "";

	private String pin = "";

	private String ip = "";

	// private String userid = null; //who is logining? provider_no
	private String userpassword = null; // your password in the table

	// private String userpin = null; //your password in the table

	private String firstname = null;

	private String lastname = null;

	private String profession = null;

	private String rolename = null;

	Properties oscarVariables = null;

	private MessageDigest md;

	com.quatro.model.security.Security secBean = null;

	public LoginCheckLoginBean() {
	}

	public void ini(String user_name, String password, String pin1, String ip1,
		Properties variables) {
		setUsername(user_name);
		setPassword(password);
		setPin(pin1);
		setIp(ip1);
        setVariables(variables);
	}

	public String[] authenticate(boolean isAuthenticated,
			ApplicationContext appContext) throws Exception, SQLException {
		secBean = getUser(appContext);

		// the user is not in security table
		if (secBean == null) {
			return cleanNullObj(LOG_PRE + "No Such User: " + username);
		}
		// check pin if needed
		String[] strAuth = new String[6];
		String expired_days = "";
		if (!isAuthenticated) {
			String sPin = pin;
			if (oscar.OscarProperties.getInstance().isPINEncripted())
				sPin = oscar.Misc.encryptPIN(sPin);

			// if (!sPin.equals(secBean.getPin())) {
			// return cleanNullObj(LOG_PRE + "Pin-remote needed: " + username);
			// }

			if (secBean.getBExpireset().intValue() == 1
					&& (secBean.getDateExpiredate() == null || secBean
							.getDateExpiredate()
							.before(UtilDateUtilities.now()))) {
				return cleanNullObjExpire(LOG_PRE + "Expired: " + username);
			}
			if (secBean.getBExpireset().intValue() == 1) {
				// Give warning if the password will be expired in 10 days.
				long date_expireDate = secBean.getDateExpiredate().getTime();
				long date_now = UtilDateUtilities.now().getTime();
				long date_diff = (date_expireDate - date_now)
						/ (24 * 3600 * 1000);

				if (secBean.getBExpireset().intValue() == 1 && date_diff < 11) {
					expired_days = String.valueOf(date_diff);
				}
			}

			StringBuffer sbTemp = new StringBuffer();
			byte[] btTypeInPasswd = md.digest(password.getBytes());
			for (int i = 0; i < btTypeInPasswd.length; i++)
				sbTemp = sbTemp.append(btTypeInPasswd[i]);
			password = sbTemp.toString();

			userpassword = secBean.getPassword();
			if (userpassword.length() < 20) {
				sbTemp = new StringBuffer();
				byte[] btDBPasswd = md.digest(userpassword.getBytes());
				for (int i = 0; i < btDBPasswd.length; i++)
					sbTemp = sbTemp.append(btDBPasswd[i]);
				userpassword = sbTemp.toString();
			}
			isAuthenticated = password.equals(userpassword);
		}
		if (isAuthenticated) {
			// login successfully
			strAuth[0] = secBean.getProviderNo();
			strAuth[1] = firstname;
			strAuth[2] = lastname;
			strAuth[3] = profession;
			strAuth[4] = rolename;
			strAuth[5] = expired_days;
			return strAuth;
		} else { // login failed
			return cleanNullObj(LOG_PRE + "password failed: " + username);
		}
	}

	private String[] cleanNullObj(String errorMsg) {
		_logger.info(errorMsg);
		LogAction.addALog("", "failed", LogConst.CON_LOGIN, username, ip);
		userpassword = null;
		password = null;
		return null;
	}

	private String[] cleanNullObjExpire(String errorMsg) {
		_logger.info(errorMsg);
		LogAction.addALog("", "expired", LogConst.CON_LOGIN, username, ip);
		userpassword = null;
		password = null;
		return new String[] { "expired" };
	}

	private com.quatro.model.security.Security getUser(
			ApplicationContext appContext) throws SQLException {
		com.quatro.service.security.UsersManager um = (com.quatro.service.security.UsersManager) appContext
				.getBean("usersManager");
		secBean = um.getUser(username);
		if (secBean == null)
			return null;
		com.quatro.model.security.SecProvider prov = um
				.getProviderByProviderNo(secBean.getProviderNo());
		firstname = prov.getFirstName();
		lastname = prov.getLastName();
		profession = prov.getProviderType();
		rolename = "";

		return secBean;
	}

	public String[] getPreferences() {
		if (org.oscarehr.common.IsPropertiesOn.isCaisiEnable()) {
			String[] temp = new String[] { "8", "18", "15", "a", "disabled",
					"disabled" };
			ResultSet rs = null;
			DBPreparedHandler accessDB = new DBPreparedHandler();
			try {
				String strSQL = "select start_hour, end_hour, every_min, mygroup_no,new_tickler_warning_window,default_caisi_pmm from preference where provider_no = '"
						+ secBean.getProviderNo() + "'";
				rs = accessDB.queryResults(strSQL);
				while (rs.next()) {
					temp[0] = DBHelp.getString(rs, "start_hour");
					temp[1] = DBHelp.getString(rs, "end_hour");
					temp[2] = DBHelp.getString(rs, "every_min");
					temp[3] = DBHelp.getString(rs, "mygroup_no");
					temp[4] = DBHelp
							.getString(rs, "new_tickler_warning_window");
					temp[5] = DBHelp.getString(rs, "default_caisi_pmm");
				}
				rs.close();
				accessDB.closeConn();
			} catch (SQLException e) {
			} finally {
				if (temp[0] == null) { // no preference for the useid
					temp[0] = "8"; // default value
					temp[1] = "18";
					temp[2] = "15";
					temp[3] = "a";
					temp[4] = "disabled";
					temp[5] = "disabled";
				}
			}
			return temp;
		} else {
			String[] temp = new String[] { "8", "18", "15", "a" };
			DBPreparedHandler accessDB = new DBPreparedHandler();
			ResultSet rs = null;
			try {
				String strSQL = "select start_hour, end_hour, every_min, mygroup_no from preference where provider_no = '"
						+ secBean.getProviderNo() + "'";
				rs = accessDB.queryResults(strSQL);
				while (rs.next()) {
					temp[0] = accessDB.getString(rs, "start_hour");
					temp[1] = accessDB.getString(rs, "end_hour");
					temp[2] = accessDB.getString(rs, "every_min");
					temp[3] = accessDB.getString(rs, "mygroup_no");
				}
				rs.close();
				accessDB.closeConn();
			} catch (SQLException e) {
			} finally {
				if (temp[0] == null) { // no preference for the useid
					temp[0] = "8"; // default value
					temp[1] = "18";
					temp[2] = "15";
					temp[3] = "a";
				}
			}
			return temp;
		}
	}

	public void setUsername(String user_name) {
		this.username = user_name;
	}

	public void setPassword(String password) {
		this.password = password.replace(' ', '\b'); // no white space to be
														// allowed in the
														// password
	}

	public void setPin(String pin1) {
		this.pin = pin1.replace(' ', '\b');
	}

	public void setIp(String ip1) {
		this.ip = ip1;
	}

    public void setVariables(Properties variables) {
        this.oscarVariables = variables;
        try {
            md = MessageDigest.getInstance("SHA"); //may get from prop file, e.g. MD5
        } catch (NoSuchAlgorithmException foo) {
            _logger.error(LOG_PRE + "NoSuchAlgorithmException - SHA");
        }
    }
}
