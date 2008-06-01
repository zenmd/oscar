/*
* Copyright (c) 2007-2008. CAISI, Toronto. All Rights Reserved.
* This software is published under the GPL GNU General Public License. 
* This program is free software; you can redistribute it and/or 
* modify it under the terms of the GNU General Public License 
* as published by the Free Software Foundation; either version 2 
* of the License, or (at your option) any later version. 
* 
* This program is distributed in the hope that it will be useful, 
* but WITHOUT ANY WARRANTY; without even the implied warranty of 
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
* GNU General Public License for more details. 
* 
* You should have received a copy of the GNU General Public License 
* along with this program; if not, write to the Free Software 
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.  
* 
* This software was written for 
* CAISI, 
* Toronto, Ontario, Canada 
*/
package org.oscarehr.PMmodule.web;

import javax.servlet.http.HttpServletRequest;

import org.oscarehr.PMmodule.dao.FacilityDAO;
import org.oscarehr.PMmodule.dao.ProgramDao;
import org.oscarehr.PMmodule.model.Facility;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.util.SpringUtils;
import java.util.List;

public class ProgramUtils
{
    private static ProgramDao programDao=(ProgramDao)SpringUtils.getBean("programDao");
    private static FacilityDAO facilityDao=(FacilityDAO)SpringUtils.getBean("facilityDAO");    

    /**
     * This method stores the program ids as javascript arrays of numbers in the session space grouped
     * by their restrictions.
     * The intent is to allow javascript on a given page to determine if a restriction is being breached
     * or not and to alert the user.
     * @param request
     */
    public static void addProgramRestrictions(HttpServletRequest request) {
        addProgramGenderRestrictions(request);
        addProgramAgeRestrictions(request);
        intakeAdmitToNewFacilityValidation(request);
    }
    
    private static void addProgramAgeRestrictions(HttpServletRequest request) {
        request.getSession().setAttribute("programAgeValidationMethod", addProgramAgeRestrictionsMethod());
    }

    private static void intakeAdmitToNewFacilityValidation(HttpServletRequest request) {
    	request.getSession().setAttribute("admitToNewFacilityValidationMethod", admitToNewFacilityValidationMethod(request));
    }
    
    public static String admitToNewFacilityValidationMethod(HttpServletRequest request) {
        StringBuilder sb=new StringBuilder();
        
        sb.append("function isNewFacility(newProgramId,oldProgramId)\n");
        sb.append("{\n");
        
        sb.append("var oldIn=false; var newIn=false; \n");
        sb.append("if(oldProgramId=='' || oldProgramId==null) {return false;}\n");
//        for(Facility facility : facilityDao.getFacilities()) {
        List facilities = facilityDao.getFacilities();
        for(int i=0;i<facilities.size();i++) {        	
        	Facility facility = (Facility)facilities.get(i); 
//        	for(Program program : programDao.getProgramsByFacilityId(facility.getId())) {
        	List programs = programDao.getProgramsByFacilityId(facility.getId());
        	for(int j=0;j<programs.size();j++) {
        		Program program = (Program)programs.get(i);
        		sb.append("if(oldProgramId==" + program.getId()+") {oldIn=true;}\n ");
        		sb.append("if(newProgramId==" + program.getId()+") {newIn=true;} \n");
        	}
        	sb.append("if(oldIn==true && newIn==true) {return(false);}\n");
        	
        	sb.append("else { oldIn=false; newIn=false; } \n");
        }
//        for(Program program : programDao.getCommunityPrograms()){
        Program[] programs2=programDao.getCommunityPrograms();
        for(int i=0;i<programs2.length;i++){
        	Program program = programs2[i];
        	if(program.isCommunity()) {
        		sb.append("if(oldProgramId=="+program.getId()+") {return(false);} \n");
        		sb.append("if(newProgramId=="+program.getId()+") {return(false);} \n");
        	}
        }
        sb.append(" return(true);\n");
        sb.append("}\n");
        
        return(sb.toString());
    }
    
    public static String addProgramAgeRestrictionsMethod() {
        StringBuilder sb=new StringBuilder();
        
        sb.append("function validAgeRangeForProgram(programId, age)\n");
        sb.append("{\n");
        
        List programs = programDao.getAllActiveBedPrograms();
//        for (Program program : programDao.getAllActiveBedPrograms())
        for (int i=0;i<programs.size();i++)
        {
            Program program= (Program)programs.get(i);
        	sb.append("if (programId == "+program.getId()+" && ( age<"+program.getAgeMin()+" || age>"+program.getAgeMax()+" )) return(false);\n");
        }
        
        sb.append("return(true);\n");
        sb.append("}\n");
        
        return(sb.toString());
    }

    private static void addProgramGenderRestrictions(HttpServletRequest request)
    {
        StringBuilder programMaleOnly=new StringBuilder("[");
        StringBuilder programFemaleOnly=new StringBuilder("[");
        StringBuilder programTransgenderOnly=new StringBuilder("[");
        
//        for (Program program : programDao.getProgramByGenderType("Man"))
        List programs1=programDao.getProgramByGenderType("Man");
        for (int i=0;i<programs1.size();i++)
        {
        	Program program = (Program)programs1.get(i);
        	if (programMaleOnly.length()>1) programMaleOnly.append(',');
            programMaleOnly.append(program.getId());
        }
//        for (Program program : programDao.getProgramByGenderType("Woman"))
        List programs2=programDao.getProgramByGenderType("Man");
        for (int i=0;i<programs2.size();i++)
        {
        	Program program = (Program)programs2.get(i);
            if (programFemaleOnly.length()>1) programFemaleOnly.append(',');
            programFemaleOnly.append(program.getId());
        }
//        for (Program program : programDao.getProgramByGenderType("Transgender"))
        List programs3=programDao.getProgramByGenderType("Man");
        for (int i=0;i<programs3.size();i++)
        {
        	Program program = (Program)programs3.get(i);
            if (programTransgenderOnly.length()>1) programTransgenderOnly.append(',');
            programTransgenderOnly.append(program.getId());
        }
        
        programMaleOnly.append(']');
        programFemaleOnly.append(']');
        programTransgenderOnly.append(']');
        
        // yeah I know we shouldn't set it in the session but I can't set it in the request because struts isn't being used properly and this method isn't actually called before render, it's called in a prior request method. 
        // considering no one cares about the quality of this code anymore it's simpler for me to continue on as is and use the session space knowing it'll cause the session / shared variable issues. Sorry but management says quality is not a priority and speed is instead. So, here's proliferating a bad practice in the name of speed.
        request.getSession().setAttribute("programMaleOnly", programMaleOnly.toString());
        request.getSession().setAttribute("programFemaleOnly", programFemaleOnly.toString());
        request.getSession().setAttribute("programTransgenderOnly", programTransgenderOnly.toString());
    }
}