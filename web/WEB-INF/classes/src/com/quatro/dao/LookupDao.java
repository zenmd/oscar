package com.quatro.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import oscar.MyDateFormat;
import oscar.oscarDB.DBPreparedHandler;
import oscar.oscarDB.DBPreparedHandlerParam;

import com.quatro.model.FieldDefValue;
import com.quatro.model.LookupCodeValue;
import com.quatro.model.LookupTableDefValue;
import com.quatro.util.Utility;

import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.Facility;

public class LookupDao extends HibernateDaoSupport {

	/* Column property mappings defined by the generic idx 
	 *  1 - Code 2 - Description 3 Active 
	 *  4 - Display Order, 5 - ParentCode 6 - Buf1 7 - CodeTree
	 */
	
	public List LoadCodeList(String tableId, boolean activeOnly, String code, String codeDesc)
	{
	   return LoadCodeList(tableId,activeOnly,"",code,codeDesc);
	}
	
	public LookupCodeValue GetCode(String tableId,String code)
	{
		List lst = LoadCodeList(tableId, false, code, "");
		LookupCodeValue lkv = null;
		if (lst.size()>0) 
		{
			lkv = (LookupCodeValue) lst.get(0);
		}
		return lkv;
	}

	public List LoadCodeList(String tableId,boolean activeOnly,  String parentCode,String code, String codeDesc)
	{
		LookupTableDefValue tableDef = GetLookupTableDef(tableId);
		List fields = LoadFieldDefList(tableId);
		DBPreparedHandlerParam [] params = new DBPreparedHandlerParam[100];
		String fieldNames [] = new String[7];
		String sSQL1 = "";
		String sSQL="select distinct ";
		boolean activeFieldExists = true;
		for (int i = 1; i <= 7; i++)
		{
			boolean ok = false;
			for (int j = 0; j<fields.size(); j++)
			{
				FieldDefValue fdef = (FieldDefValue)fields.get(j);
				if (fdef.getGenericIdx()== i)
				{
					if (fdef.getFieldSQL().indexOf('(') >= 0) {
						sSQL += fdef.getFieldSQL() + " " + fdef.getFieldName()+ ",";
						fieldNames[i-1]=fdef.getFieldName();
					}
					else
					{
						sSQL += "s." + fdef.getFieldSQL() + ",";
						fieldNames[i-1]=fdef.getFieldSQL();
					}
					ok = true;
					break;
				}
			}
			if (!ok) {
				sSQL += " null field" + i + ",";
				fieldNames[i-1] = "field" + i;
				if (i==3) activeFieldExists = false;
			}
		}
		sSQL = sSQL.substring(0,sSQL.length()-1);
	    sSQL +=" from " + tableDef.getTableName() ;
		sSQL1 = sSQL.replace("s.", "a.") + " a,";	    
		sSQL += " s where 1=1";
	    int i= 0;
        if (activeFieldExists && activeOnly) {
	    	sSQL += " and " + fieldNames[2] + "=?"; 
	    	params[i++] = new DBPreparedHandlerParam(1);
        }
	   if (!Utility.IsEmpty(parentCode)) {
	    	sSQL += " and " + fieldNames[4] + "=?"; 
	    	params[i++]= new DBPreparedHandlerParam(parentCode);
	   }
	   if (!Utility.IsEmpty(code)) {
	    	sSQL += " and " + fieldNames[0] + " in (";
	    	String [] codes = code.split(",");
    		sSQL += "?";
	    	params[i++] = new DBPreparedHandlerParam(codes[0]);
	    	for(int k = 1; k<codes.length; k++)
	    	{
	    		sSQL += ",?";
		    	params[i++] = new DBPreparedHandlerParam(codes[k]);
	    	}
	    	sSQL += ")";
	   }
	   if (!Utility.IsEmpty(codeDesc)) {
	    	sSQL += " and " + fieldNames[1] + " like ?"; 
	    	params[i++]= new DBPreparedHandlerParam("%" + codeDesc + "%");
	   }	
	   
	   if (tableDef.isTree()) {
		 sSQL = sSQL1 + "(" + sSQL + ") b";
		 sSQL += " where b." + fieldNames[6] + " like a." + fieldNames[6] + "||'%'";
	   }
	   if (tableDef.isTree())
	   {
		   sSQL += " order by 7,1";
	   } else {
		   sSQL += " order by 4,5,2";
	   }
	   DBPreparedHandlerParam [] pars = new DBPreparedHandlerParam[i];
	   for(int j=0; j<i;j++)
	   {
		   pars[j] = params[j];
	   }
	   
	   DBPreparedHandler db = new DBPreparedHandler();
	   ArrayList list = new ArrayList();
	   try {
		   ResultSet rs = db.queryResults(sSQL,pars);
		   while (rs.next()) {
			   LookupCodeValue lv = new LookupCodeValue();
			   lv.setPrefix(tableId);
			   lv.setCode(rs.getString(1));
			   lv.setDescription(db.getString(rs, 2));
			   lv.setActive(1 == Integer.valueOf("0" + db.getString(rs, 3)));
			   lv.setOrderByIndex(Integer.valueOf("0" + db.getString(rs,4)));
			   lv.setParentCode(db.getString(rs, 5));
			   lv.setBuf1(db.getString(rs,6));
			   lv.setCodeTree(db.getString(rs, 7));
			   list.add(lv);
			}
			rs.close();
	   }
	   catch(SQLException e)
	   {
		  e.printStackTrace();
	   }
	   return list;
	}

	public LookupTableDefValue GetLookupTableDef(String tableId)
	{
		ArrayList paramList = new ArrayList();

		String sSQL="from LookupTableDefValue s where s.tableId= ?";		
	    paramList.add(tableId);
	    Object params[] = paramList.toArray(new Object[paramList.size()]);
	    try{
	      return (LookupTableDefValue)getHibernateTemplate().find(sSQL ,params).get(0);
	    }catch(Exception ex){
	    	return null;
	    }
	}
	public List LoadFieldDefList(String tableId) 
	{
		String sSql = "from FieldDefValue s where s.tableId=? order by s.fieldIndex ";
		ArrayList paramList = new ArrayList();
	    paramList.add(tableId);
	    Object params[] = paramList.toArray(new Object[paramList.size()]);
		
	    return getHibernateTemplate().find(sSql,params);
	}
	public List GetCodeFieldValues(LookupTableDefValue tableDef, String code)
	{
		String tableName = tableDef.getTableName();
		List fs = LoadFieldDefList(tableDef.getTableId());
		String idFieldName = "";
		
		String sql = "select ";
		for(int i=0; i<fs.size(); i++) {
			FieldDefValue fdv = (FieldDefValue) fs.get(i);
			if(fdv.getGenericIdx()==1) idFieldName = fdv.getFieldSQL();
			if (i==0) {
				sql += fdv.getFieldSQL();
			}
			else
			{
				sql += "," + fdv.getFieldSQL();
			}
		}
		sql += " from " + tableName + " s";
		sql += " where " + idFieldName + "='" + code + "'"; 
		try {
			DBPreparedHandler db = new DBPreparedHandler();
			ResultSet rs = db.queryResults(sql);
			if (rs.next()) {
				for(int i=0; i< fs.size(); i++) 
				{
					FieldDefValue fdv = (FieldDefValue) fs.get(i);
					String val = db.getString(rs, i+1);
					fdv.setVal(val);
					if (!Utility.IsEmpty(fdv.getLookupTable()))
					{
						LookupCodeValue lkv = GetCode(fdv.getLookupTable(),val);
						if (lkv != null) fdv.setValDesc(lkv.getDescription());
					}
				}
			}
			rs.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return fs;
	}
	public List GetCodeFieldValues(LookupTableDefValue tableDef)
	{
		String tableName = tableDef.getTableName();
		List fs = LoadFieldDefList(tableDef.getTableId());
		ArrayList codes = new ArrayList();
		String sql = "select ";
		for(int i=0; i<fs.size(); i++) {
			FieldDefValue fdv = (FieldDefValue) fs.get(i);
			if (i==0) {
				sql += fdv.getFieldSQL();
			}
			else
			{
				sql += "," + fdv.getFieldSQL();
			}
		}
		sql += " from " + tableName;
		try {
			DBPreparedHandler db = new DBPreparedHandler();
			ResultSet rs = db.queryResults(sql);
			while (rs.next()) {
				for(int i=0; i< fs.size(); i++) 
				{
					FieldDefValue fdv = (FieldDefValue) fs.get(i);
					String val = db.getString(rs, i+1);
					fdv.setVal(val);
					if (!Utility.IsEmpty(fdv.getLookupTable()))
					{
						LookupCodeValue lkv = GetCode(fdv.getLookupTable(),val);
						if (lkv != null) fdv.setValDesc(lkv.getDescription());
					}
				}
				codes.add(fs);
			}
			rs.close();
		}
		catch(SQLException e)
		{
			System.out.println(e.getStackTrace());
		}
		return codes;
	}
	private int GetNextId(String idFieldName, String tableName) throws SQLException

	{
		String sql = "select max(" + idFieldName + ")";
		sql += " from " + tableName;
		DBPreparedHandler db = new DBPreparedHandler();
		ResultSet rs = db.queryResults(sql);
		int id = 0;
		if (rs.next()) 
			 id = rs.getInt(1);
		return id + 1;
	}
	
	public String SaveCodeValue(boolean isNew, LookupTableDefValue tableDef, List fieldDefList) throws SQLException
	{
		String id = "";
		if (isNew)
		{
			id = InsertCodeValue(tableDef, fieldDefList);
		}
		else
		{
			id = UpdateCodeValue(tableDef,fieldDefList);
		}
		if ("OGN".equals(tableDef.getTableId()))
		{
			SaveAsOrgCode(GetCode("OGN", id)); 
		}
		return id;
	}
	public String SaveCodeValue(boolean isNew, LookupCodeValue codeValue) throws SQLException
	{
		String tableId = codeValue.getPrefix();
		LookupTableDefValue  tableDef = GetLookupTableDef(tableId);
		List fieldDefList = this.LoadFieldDefList(tableId);
		for(int i=0; i<fieldDefList.size(); i++)
		{
			FieldDefValue fdv = (FieldDefValue) fieldDefList.get(i);
			
			switch(fdv.getGenericIdx())
			{
			case 1:
				fdv.setVal(codeValue.getCode());
				break;
			case 2:
				fdv.setVal(codeValue.getDescription());
				break;
			case 3:
				fdv.setVal(codeValue.isActive()?"1":"0");
				break;
			case 4:
				fdv.setVal(String.valueOf(codeValue.getOrderByIndex()));
				break;
			case 5:
				fdv.setVal(codeValue.getParentCode());
				break;
			case 6:
				fdv.setVal(codeValue.getBuf1());
				break;
			case 7:
				fdv.setVal(codeValue.getCodeTree());
			}
		}
		if (isNew) 
		{
			return InsertCodeValue(tableDef, fieldDefList);
		}
		else
		{
			return UpdateCodeValue(tableDef,fieldDefList);
		}
	}
	private String InsertCodeValue(LookupTableDefValue tableDef, List fieldDefList) throws SQLException
	{
		String tableName = tableDef.getTableName();
		String idFieldVal = "";

		DBPreparedHandlerParam[] params = new DBPreparedHandlerParam[fieldDefList.size()];
		String phs = "";
		String sql = "insert into  " + tableName + "("; 
		for(int i=0; i< fieldDefList.size(); i++) {
			FieldDefValue fdv = (FieldDefValue) fieldDefList.get(i);
			sql += fdv.getFieldName() + ",";
			phs +="?,"; 
			if (fdv.getGenericIdx() == 1) {
				if (fdv.isAuto())
				{
					idFieldVal = String.valueOf(GetNextId(fdv.getFieldSQL(), tableName));
					fdv.setVal(idFieldVal);
				}
				else
				{
					idFieldVal = fdv.getVal();
				}
			}
			if ("S".equals(fdv.getFieldType()))
			{
				params[i] = new DBPreparedHandlerParam(fdv.getVal());
			}
			else if ("D".equals(fdv.getFieldType()))
			{
				params[i] = new DBPreparedHandlerParam(MyDateFormat.getSysDate(fdv.getVal()));
			}
			else
			{
				params[i] = new DBPreparedHandlerParam(Integer.valueOf(fdv.getVal()).intValue());
			}
		}
		sql = sql.substring(0,sql.length()-1);
		phs = phs.substring(0,phs.length()-1);
		sql += ") values (" + phs + ")";

		//check the existence of the code 
		LookupCodeValue lkv= GetCode(tableDef.getTableId(), idFieldVal);
		if(lkv != null) 
		{
			throw new SQLException("The Code Already Exist");
		}
		DBPreparedHandler db = new DBPreparedHandler();
		db.queryExecuteUpdate(sql, params);
		return idFieldVal;
	}
	private String UpdateCodeValue(LookupTableDefValue tableDef, List fieldDefList) throws SQLException
	{
		String tableName = tableDef.getTableName();
		String idFieldName = "";
		String idFieldVal = "";

		DBPreparedHandlerParam[] params = new DBPreparedHandlerParam[fieldDefList.size()+1];
		String sql = "update " + tableName + " set ";
		for(int i=0; i< fieldDefList.size(); i++) {
			FieldDefValue fdv = (FieldDefValue) fieldDefList.get(i);
			if (fdv.getGenericIdx()==1) {
				idFieldName = fdv.getFieldSQL();
				idFieldVal = fdv.getVal();
			}
			
			sql += fdv.getFieldName() + "=?,";
			if ("S".equals(fdv.getFieldType()))
			{
				params[i] = new DBPreparedHandlerParam(fdv.getVal());
			}
			else if ("D".equals(fdv.getFieldType()))
			{
				params[i] = new DBPreparedHandlerParam(MyDateFormat.getSysDate(fdv.getVal()));
			}
			else
			{
				params[i] = new DBPreparedHandlerParam(Integer.valueOf(fdv.getVal()).intValue());
			}
		}
		sql = sql.substring(0,sql.length()-1);
		sql += " where " + idFieldName + "=?";
		params[fieldDefList.size()] = params[0];
		DBPreparedHandler db = new DBPreparedHandler();
		db.queryExecuteUpdate(sql, params);
		return idFieldVal;
	}
	public void SaveAsOrgCode(Program program) throws SQLException
	{
		LookupTableDefValue tableDef = this.GetLookupTableDef("ORG");
		List codeValues = new ArrayList();
		String  programId = "0000000" + program.getId().toString();
		programId = "P" + programId.substring(programId.length()-7);
		String fullCode = "P" + program.getId();
		
		String facilityId = "0000000" + String.valueOf(program.getFacilityId());
		facilityId = "F" + facilityId.substring(facilityId.length()-7); 
		
		LookupCodeValue fcd = GetCode("ORG", "F" + program.getFacilityId());
		fullCode = fcd.getBuf1() + fullCode;
		
		boolean isNew = false;
		LookupCodeValue pcd = GetCode("ORG", "P" + program.getId());
		if (pcd == null) {
			isNew = true;
			pcd = new LookupCodeValue();
		}
		pcd.setPrefix("ORG");
		pcd.setCode("P" + program.getId());
		pcd.setCodeTree(fcd.getCodeTree() + programId);
		pcd.setDescription(program.getName());
		pcd.setBuf1(fullCode);
		pcd.setActive(program.getProgramStatus().equalsIgnoreCase("active"));
		pcd.setOrderByIndex(0);
		
		this.SaveCodeValue(isNew,pcd);
	}
	public void SaveAsOrgCode(Facility facility) throws SQLException
	{
		LookupTableDefValue tableDef = this.GetLookupTableDef("ORG");
		List codeValues = new ArrayList();
		String  facilityId = "0000000" + facility.getId().toString();
		facilityId = "F" + facilityId.substring(facilityId.length()-7);
		String fullCode = "F" + facility.getId();
		
		String orgId = "0000000" + String.valueOf(facility.getOrgId());
		orgId = "O" + orgId.substring(orgId.length()-7); 
		
		LookupCodeValue ocd = GetCode("ORG", "O" + facility.getOrgId());
		fullCode = ocd.getBuf1() + fullCode;
		
		boolean isNew = false;
		LookupCodeValue fcd = GetCode("ORG", "F" + facility.getId());
		if (fcd == null) {
			isNew = true;
			fcd = new LookupCodeValue();
		}
		fcd.setPrefix("ORG");
		fcd.setCode("F" + facility.getId());
		fcd.setCodeTree(ocd.getCodeTree() + facilityId);
		fcd.setDescription(facility.getName());
		fcd.setBuf1(fullCode);
		fcd.setActive(!facility.isDisabled());
		fcd.setOrderByIndex(0);
		
		this.SaveCodeValue(isNew,fcd);
	}
	public void SaveAsOrgCode(LookupCodeValue organization) throws SQLException
	{
		LookupTableDefValue tableDef = this.GetLookupTableDef("ORG");
		List codeValues = new ArrayList();
		String  orgId = "0000000" + organization.getCode();
		orgId = "O" + orgId.substring(orgId.length()-7);
		
		String sId = "R0000001";
		String fullCode = "R1" + "O" + organization.getCode() ;
		
		boolean isNew = false;
		LookupCodeValue ocd = GetCode("ORG", "O" + organization.getCode());
		if (ocd == null) {
			isNew = true;
			ocd = new LookupCodeValue();
		}
		ocd.setPrefix("ORG");
		ocd.setCode("O" + organization.getCode());
		ocd.setCodeTree(sId + orgId);
		ocd.setDescription(organization.getDescription());
		ocd.setBuf1(fullCode);
		ocd.setActive(organization.isActive());
		ocd.setOrderByIndex(0);
		
		this.SaveCodeValue(isNew,ocd);
	}

}
