package com.quatro.service;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import com.quatro.dao.IntakeDao;
import com.quatro.web.intake.OptionList;
import com.quatro.model.QuatroIntakeOptionValue;
import com.quatro.web.intake.OptionListConstant;
import org.apache.struts.util.LabelValueBean;

public class IntakeManager {
    private IntakeDao intakeDao;

	public IntakeDao getIntakeDao() {
		return intakeDao;
	}

	public void setIntakeDao(IntakeDao intakeDao) {
		this.intakeDao = intakeDao;
	}

	public OptionList LoadOptionsList() {
        List lst=intakeDao.LoadOptionsList();
        OptionList lst2= new OptionList();
        HashMap<Integer, String> map= OptionListConstant.getPrefixDefined();

        ArrayList[] lst3 = new ArrayList[11];
        for(int i=0;i<11;i++) lst3[i]= new ArrayList();
        
        boolean getRec;
        for(int i=0;i<11;i++){
          getRec=false;
          for(int j=0;j<lst.size();j++){
        	QuatroIntakeOptionValue obj= (QuatroIntakeOptionValue)lst.get(j);
       	    if(obj.getPrefix().equals(map.get(new Integer(i)))){
       	      LabelValueBean obj2 = new LabelValueBean();
       	      obj2.setLabel(obj.getDescription());
       	      obj2.setValue(obj.getCode());
        	  lst3[i].add(obj2);
        	  getRec=true;
        	}else{
              if(getRec==true){
            	  getRec=false;
            	  break;	
              }
            }
          }
      	  lst2.setValue(i, lst3[i]);            	
        }
        
        return lst2;
	}

	
}
