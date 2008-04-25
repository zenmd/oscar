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
* Toronto, Ontario, Canada 
*/

package com.quatro.service.security;

import java.util.List;

import com.quatro.dao.SecroleDao;
import com.quatro.dao.security.SecobjprivilegeDao;
import com.quatro.model.security.Secobjprivilege;
import com.quatro.model.security.Secrole;




public class RolesManager {

	private SecroleDao secroleDao;
	private SecobjprivilegeDao secobjprivilegeDao;
	
	public void setSecroleDao(SecroleDao dao) {
		this.secroleDao = dao;
	}
	public List getRoles() {
		return secroleDao.getRoles();
	}
	public Secrole getRole(String id) {
		return secroleDao.getRole(Long.valueOf(id));
	}
	public Secrole getRoleByRolename(String roleName) {
		return secroleDao.getRoleByName(roleName);
	}
	public void save(Secrole secrole) {
		secroleDao.save(secrole);
	}
	public void saveFunction(Secobjprivilege secobjprivilege) {
		secobjprivilegeDao.save(secobjprivilege);
	}
	public void setSecobjprivilegeDao(SecobjprivilegeDao secobjprivilegeDao) {
		this.secobjprivilegeDao = secobjprivilegeDao;
	}
	
}
