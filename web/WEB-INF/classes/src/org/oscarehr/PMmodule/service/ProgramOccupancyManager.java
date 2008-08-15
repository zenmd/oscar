package org.oscarehr.PMmodule.service;

import java.util.Calendar;

import org.oscarehr.PMmodule.dao.ProgramOccupancyDao;

public class ProgramOccupancyManager {
	
	private ProgramOccupancyDao  programOccupancyDao;	
	
	public void insertProgramOccupancy(String providerNo,Calendar occDate) {
		programOccupancyDao.insertProgramOccupancy(providerNo, occDate);
	}
	public void deleteProgramOccupancy(Calendar occDate) {
		programOccupancyDao.deleteProgramOccupancy(occDate);
	}
	public void insertSdmtOut(){
		 programOccupancyDao.insertSdmtOut();
	 }
	
	public void setProgramOccupancyDao(ProgramOccupancyDao programOccupancyDao) {
		this.programOccupancyDao = programOccupancyDao;
	}
	 
}
