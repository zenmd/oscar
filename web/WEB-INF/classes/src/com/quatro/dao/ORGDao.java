package com.quatro.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import oscar.oscarDB.DBPreparedHandler;
import oscar.oscarDB.DBPreparedHandlerParam;

import com.quatro.model.FieldDefValue;
import com.quatro.model.LookupCodeValue;
import com.quatro.model.LookupTableDefValue;
import com.quatro.util.Utility;

public class ORGDao extends HibernateDaoSupport {

	/* Column property mappings defined by the generic idx 
	 *  1 - Code 2 - Description 3 Active 
	 *  4 - Display Order, 5 - ParentCode 6 - Buf1 7 - CodeTree
	 */
	
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
	
	public List LoadCodeList(String tableId, boolean activeOnly, String code, String codeDesc)
	{
	   return LoadCodeList(tableId,activeOnly,"",code,codeDesc);
	}

	public List LoadFieldDefList(String tableId) 
	{
		String sSql = "from FieldDefValue s where s.tableId=? order by s.fieldIndex ";
		ArrayList paramList = new ArrayList();
	    paramList.add(tableId);
	    Object params[] = paramList.toArray(new Object[paramList.size()]);
		
	    return getHibernateTemplate().find(sSql,params);
	}

	public List LoadCodeList(String tableId,boolean activeOnly,  String parentCode,String code, String codeDesc)
	{
		LookupTableDefValue tableDef = GetLookupTableDef(tableId);
		List fields = LoadFieldDefList(tableId);
		DBPreparedHandlerParam [] params = new DBPreparedHandlerParam[4];
		String fieldNames [] = new String[7];
		String sSQL1 = "";
		String sSQL="select distinct ";
		for (int i = 1; i <= 7; i++)
		{
			boolean ok = false;
			for (int j = 0; j<fields.size(); j++)
			{
				FieldDefValue fdef = (FieldDefValue)fields.get(j);
				if (fdef.getGenericIdx()== i)
				{
					sSQL += "s." + fdef.getFieldSQL() + ",";
					fieldNames[i-1]=fdef.getFieldSQL();
					ok = true;
					break;
				}
			}
			if (!ok) {
				sSQL += " null field" + i + ",";
				fieldNames[i-1] = "field" + i;
			}
		}
		sSQL = sSQL.substring(0,sSQL.length()-1);
	    sSQL +=" from " + tableDef.getTableName() ;
		sSQL1 = Utility.replace(sSQL,"s.", "a.") + " a,";	    
		sSQL += " s where 1=1";
	    int i= 0;
        if (activeOnly) {
	    	sSQL += " and " + fieldNames[2] + "=?"; 
	    	params[i++] = new DBPreparedHandlerParam(1);
        }
	   if (!Utility.IsEmpty(parentCode)) {
	    	sSQL += " and " + fieldNames[4] + "=?"; 
	    	params[i++]= new DBPreparedHandlerParam(parentCode);
	   }
	   if (!Utility.IsEmpty(code)) {
	    	sSQL += " and " + fieldNames[0] + "=?"; 
	    	params[i++] = new DBPreparedHandlerParam(code);
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
			   lv.setActive(1 == Integer.valueOf("0" + db.getString(rs, 3)).intValue());
			   lv.setOrderByIndex(Integer.valueOf("0" + db.getString(rs,4)).intValue());
			   lv.setParentCode(db.getString(rs, 5));
			   lv.setBuf1(db.getString(rs,6));
			   lv.setCodeTree(db.getString(rs, 7));
			   list.add(lv);
			}
			rs.close();
	   }
	   catch(SQLException e)
	   {
		   System.out.println(e.getStackTrace().toString());
	   }
	   return list;
	}
	
	public void delete(String orgcd) {
		
		try {
			//getSession().delete(persistentInstance);
			
			getHibernateTemplate().bulkUpdate("delete LstOrgcd q where q.code=?", orgcd);
		
		} catch (RuntimeException re) {
			throw re;
		}
	}

	
}
