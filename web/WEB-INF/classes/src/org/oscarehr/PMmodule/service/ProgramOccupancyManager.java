package org.oscarehr.PMmodule.service;

import java.util.Calendar;
import java.util.List;

import org.oscarehr.PMmodule.dao.ClientDao;
import org.oscarehr.PMmodule.dao.ProgramOccupancyDao;
import org.oscarehr.PMmodule.model.Demographic;
import org.oscarehr.PMmodule.model.SdmtIn;

public class ProgramOccupancyManager {
	
	private ProgramOccupancyDao  programOccupancyDao;	
	private ClientDao clientDao;
	
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
	public List getSdmtOutList(Calendar today,boolean includeSendout){
		return programOccupancyDao.getSdmtOutList(today, includeSendout);
	}
	public void updateSdmtOut(int batchNo){
		programOccupancyDao.updateSdmtOut(batchNo);
	}
	 public void insertSdmtIn(SdmtIn sdVal){
		 programOccupancyDao.insertSdmtIn(sdVal);
		 Demographic client=new Demographic();
		 client.setDemographicNo(sdVal.getRecordId());
		 client.setBenefitUnitStatus(sdVal.getBenefitUnitStatus());
		 client.setPin(sdVal.getSdmtBenUnitId().toString()); 
		 client.setHin(sdVal.getClientId().toString());  //sdmt client id
		 clientDao.updateClientBenUnitStatus(client);
	 }
	public void setClientDao(ClientDao clientDao) {
		this.clientDao = clientDao;
	}
}
