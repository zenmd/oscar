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
package org.oscarehr.PMmodule.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.oscarehr.PMmodule.dao.ClientDao;
import org.oscarehr.PMmodule.dao.MergeClientDao;
import org.oscarehr.PMmodule.model.ClientMerge;
import org.oscarehr.PMmodule.model.Demographic;
import org.oscarehr.PMmodule.web.formbean.ClientSearchFormBean;

public class MergeClientManager {
	private MergeClientDao mergeClientDao;
	private ClientDao	clientDao;

	public void setMergeClientDao(MergeClientDao mergeClientDao) {
		this.mergeClientDao = mergeClientDao;
	}

	public void merge(ClientMerge cmObj) {
		mergeClientDao.merge(cmObj);
	}

	public void unMerge(ClientMerge cmObj) {
		mergeClientDao.unMerge(cmObj);
	}

	public Integer getHead(Integer demographic_no) {
		return mergeClientDao.getHead(demographic_no);
	}

	public List getTail(Integer demographic_no) {
		return mergeClientDao.getTail(demographic_no);
	}
	public ClientMerge getClientMerge(Integer demographic_no) {
		return mergeClientDao.getClientMerge(demographic_no);
	}
	public List  searchMerged(ClientSearchFormBean criteria){
		criteria.setMerged(true);
		List lst=this.clientDao.search(criteria, false,false);
		List result = new ArrayList();
		Iterator items =lst.iterator();
		while(items.hasNext()){
			Demographic client=(Demographic)items.next();
			List subRecord = clientDao.getClientSubRecords(client.getDemographicNo());
			if(subRecord.size()>0) {
				Iterator subs = subRecord.iterator();
				while(subs.hasNext()){
					Integer cId=Integer.valueOf(subs.next().toString());
					Demographic mergedClient= clientDao.getClientByDemographicNo(cId); 
					result.add(mergedClient);
					result.add(client);
				}				
			}
		}
		return result;
	}
	
	public void setClientDao(ClientDao clientDao) {
		this.clientDao = clientDao;
	}
}
