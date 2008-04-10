package com.quatro.web.lookup;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class LookupListForm extends ActionForm{
	List lookups;
    String openerForm;
    String codeName;
    String descName;
    String keywordName;
    String tableId;
    
	public List getLookups() {
		return lookups;
	}

	public void setLookups(List lookups) {
		this.lookups = lookups;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getDescName() {
		return descName;
	}

	public void setDescName(String descName) {
		this.descName = descName;
	}

	public String getOpenerForm() {
		return openerForm;
	}

	public void setOpenerForm(String openerForm) {
		this.openerForm = openerForm;
	}

	public String getKeywordName() {
		return keywordName;
	}

	public void setKeywordName(String keywordName) {
		this.keywordName = keywordName;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
}
