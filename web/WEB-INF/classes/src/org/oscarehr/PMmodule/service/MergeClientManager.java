package org.oscarehr.PMmodule.service;

import java.util.List;

import org.oscarehr.PMmodule.dao.MergeClientDao;
import org.oscarehr.PMmodule.model.ClientMerge;

public class MergeClientManager {
	private MergeClientDao mergeClientDao;

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
}
