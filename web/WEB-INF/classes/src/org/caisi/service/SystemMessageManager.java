/*
* 
* Copyright (c) 2001-2002. Centre for Research on Inner City Health, St. Michael's Hospital, Toronto. All Rights Reserved. *
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
* <OSCAR TEAM>
* 
* This software was written for 
* Centre for Research on Inner City Health, St. Michael's Hospital, 
* Toronto, Ontario, Canada  - UPDATED: Quatro Group 2008/2009
*/


package org.caisi.service;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.caisi.dao.SystemMessageDAO;
import org.caisi.model.SystemMessage;

public class SystemMessageManager {

	private static Logger log = LogManager.getLogger(SystemMessageManager.class);
	
	
	private SystemMessageDAO dao = null;
	
	public SystemMessage getMessage(String messageId) {
		return dao.getMessage(Integer.valueOf(messageId));
	}
	public void setSystemMessageDAO(SystemMessageDAO dao) {
		this.dao = dao;
	}
	public void saveSystemMessage(SystemMessage msg) {
		dao.saveMessage(msg);
	}
	
	public List getMessages() {
		return dao.getMessages();
	}
	public List getActiveMessages() {
		return dao.getActiveMessages();
	}
	public int getActiveMessageCount()
	{
		return dao.getActiveMessageCount();
	}
}
