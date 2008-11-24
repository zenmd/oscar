package org.oscarehr.casemgmt.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.oscarehr.PMmodule.model.Provider;
import org.oscarehr.casemgmt.model.CaseManagementIssue;
import org.oscarehr.casemgmt.model.CaseManagementNote;

import org.oscarehr.casemgmt.web.formbeans.CaseManagementEntryFormBean;
import org.oscarehr.common.model.UserProperty;
import org.springframework.web.context.WebApplicationContext;

import oscar.util.UtilDateUtilities;

import com.quatro.common.KeyConstants;
import com.quatro.util.*;

public class CaseManagementNoteAction extends BaseCaseManagementEntryAction {

	private static Logger log = LogManager.getLogger(CaseManagementNoteAction.class);

	ActionMessages messages = new ActionMessages();

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return edit(mapping, form, request, response);
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("edit");

		CaseManagementEntryFormBean cform = (CaseManagementEntryFormBean) form;
		Object reqForm = request.getParameter("form");
		request.setAttribute("change_flag", "false");
		request.setAttribute("from", "casemgmt");
		HashMap actionParam = (HashMap) request.getAttribute("actionParam");
		if (actionParam == null) {
			actionParam = new HashMap();
			String cId = request.getParameter("clientId");
			if (Utility.IsEmpty(cId))
				cId = (String) request.getSession(true).getAttribute(
						"casemgmt_DemoNo");
			actionParam.put("clientId", cId);
		}
		request.setAttribute("actionParam", actionParam);

		String demono = (String) actionParam.get("clientId");
		request.setAttribute("clientId", demono);
		request.setAttribute("client", clientManager
				.getClientByDemographicNo(demono));
		String providerNo = getProviderNo(request);
		Integer shelterId = (Integer) request.getSession(true).getAttribute(
				KeyConstants.SESSION_KEY_SHELTERID);
		Boolean restore = (Boolean) request.getAttribute("restore");
		//Integer programId = (Integer) request.getSession(true).getAttribute("case_program_id");
		Integer currentFacilityId = (Integer) request.getSession(true)
				.getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
		/*
		if (null==programId || programId.intValue()==0) {
			Integer demoInt = Integer.valueOf(demono);
			programId = clientManager.getRecentProgramId(
					Integer.valueOf(demono), providerNo, currentFacilityId);
		}
		*/
		super.setScreenMode(request, KeyConstants.TAB_CLIENT_CASE);
		boolean isReadOnly =super.isReadOnly(request, "", KeyConstants.FUN_CLIENTCASE, new Integer(0));
		if(isReadOnly) request.setAttribute("isReadOnly", Boolean.TRUE);
		request.setAttribute("demoName", getDemoName(demono));
		request.setAttribute("demoAge", getDemoAge(demono));
		request.setAttribute("demoDOB", getDemoDOB(demono));

		/* process the request from other module */
		if (!"casemgmt".equalsIgnoreCase(request.getParameter("from"))) {

			// no demographic number, no page
			if (request.getParameter("demographicNo") == null
					|| "".equals(request.getParameter("demographicNo"))) {
				return mapping.findForward("NoDemoERR");
			}
			request.setAttribute("from", "");
		}

		/* prepare url for billing */
		if (request.getParameter("from") != null) {
			request.setAttribute("from", request.getParameter("from"));
		}

		String url = "";
		if ("casemgmt".equals(request.getAttribute("from"))) {
			String ss = (String) request.getSession(true).getAttribute(
					"casemgmt_VlCountry");
			Properties oscarVariables = (Properties) request.getSession(true)
					.getAttribute("oscarVariables");
			String province = "";
			if (oscarVariables != null) {
				province = ((String) oscarVariables.getProperty("billregion",
						"")).trim().toUpperCase();
			}

			Date today = new Date();
			Calendar todayCal = Calendar.getInstance();
			todayCal.setTime(today);

			String Hour = Integer.toString(todayCal.get(Calendar.HOUR));
			String Min = Integer.toString(todayCal.get(Calendar.MINUTE));
		}

		/* remove the remembered echart string */
		request.getSession(true).setAttribute("lastSavedNoteString", null);

		/*
		 * if(request.getSession(true).getAttribute("archiveView")!="true") issues =
		 * caseManagementMgr.filterIssues(caseManagementMgr.getIssues(providerNo,
		 * demono),providerNo,programId); else issues =
		 * caseManagementMgr.getIssues(providerNo, demono);
		 */

		cform.setDemoNo(demono);
		CaseManagementNote note = null;		
		String nId = request.getParameter("noteId");
		if (Utility.IsEmpty(nId))
			nId = (String) request.getAttribute("noteId");
		String forceNote = request.getParameter("forceNote");
		if (forceNote == null)
			forceNote = "false";

		log.debug("NoteId " + nId);

		// set date 2 weeks in past so we retrieve more recent saved notes
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -14);
		Date twoWeeksAgo = cal.getTime();
		// CaseManagementTmpSave tmpsavenote =
		// this.caseManagementMgr.restoreTmpSave(providerNo, demono, programId,
		// twoWeeksAgo);
		
		
		 List programIds =clientManager.getRecentProgramIds(Integer.valueOf(demono),providerNo,shelterId);
	     List programs = null;
	     if (programIds.size() > 0) {
		     String progs = ((Integer)programIds.get(0)).toString();
		     for (int i=1; i<programIds.size(); i++)
		     {
		   	   progs += "," + ((Integer)programIds.get(i)).toString();
		     }
		     programs =  lookupManager.LoadCodeList("PRO", true, progs, null);
	      }
	      else
	      {
	    	 programs = new ArrayList();
	      }
		request.setAttribute("lstProgram", programs);
		
		if (request.getParameter("note_edit") != null
				&& request.getParameter("note_edit").equals("new")) {
			log.info("NEW NOTE GENERATED");
			request.getSession(true).setAttribute("newNote", "true");
			request.getSession(true).setAttribute("issueStatusChanged", "false");
			request.setAttribute("newNoteIdx", request
					.getParameter("newNoteIdx"));

			note = new CaseManagementNote();
			note.setProvider_no(providerNo);
			Provider prov = new Provider();
			prov.setProviderNo(providerNo);
			note.setProvider(prov);
			note.setDemographic_no(new Integer(demono));

			this.insertReason(request, note);

		//	resetTemp(providerNo, new Integer(demono), programId);

		}
		/*
		 * else if (tmpsavenote != null && !forceNote.equals("true")) {
		 * log.debug("tempsavenote is NOT NULL"); if (tmpsavenote.getNote_id() >
		 * 0) { request.getSession(true).setAttribute("newNote", "false");
		 * request.setAttribute("noteId",
		 * String.valueOf(tmpsavenote.getNote_id())); note =
		 * caseManagementMgr.getNote(String.valueOf(tmpsavenote.getNote_id()));
		 * log.debug("Restoring " + String.valueOf(note.getId())); } else {
		 * request.getSession(true).setAttribute("newNote", "true");
		 * request.getSession(true).setAttribute("issueStatusChanged", "false");
		 * note = new CaseManagementNote(); note.setProvider_no(providerNo);
		 * Provider prov = new Provider(); prov.setProviderNo(providerNo);
		 * note.setProvider(prov); note.setDemographic_no(demono); }
		 * 
		 * note.setNote(tmpsavenote.getNote());
		 *  }
		 */
		else if (nId != null && Integer.parseInt(nId) > 0) {
			log.info("Using nId " + nId + " to fetch note");
			request.getSession(true).setAttribute("newNote", "false");
			note = caseManagementMgr.getNote(nId);

			if (note.getHistory() == null || note.getHistory().equals("")) {
				// old note - we need to save the original in here
				note.setHistory(note.getNote());
				caseManagementMgr.saveNoteSimple(note);
			}

		} else {
			// A hack to load last unsigned note when not specifying a
			// particular note to edit
			// if there is no unsigned note load a new one
			if ((note = getLastSaved(request, demono, providerNo)) == null) {
				request.getSession(true).setAttribute("newNote", "true");
				request.getSession(true)
						.setAttribute("issueStatusChanged", "false");
				if(cform.getCaseNote()==null){
				note = new CaseManagementNote();
				note.setProvider_no(providerNo);
				Provider prov = new Provider();
				prov.setProviderNo(providerNo);
				note.setProvider(prov);
				note.setDemographic_no(new Integer(demono));
				}
				else{ 
					note=cform.getCaseNote();			
				}

				this.insertReason(request, note);
			}
		}
		List issues = caseManagementMgr.getIssues(providerNo, demono);

		Iterator itr1 = note.getIssues().iterator();
		 
		ArrayList lstIssueSelection = new ArrayList();
		while (itr1.hasNext()) {
			CaseManagementIssue iss = (CaseManagementIssue) itr1.next();
			lstIssueSelection.add(iss.getIssue());
		}
		if(note.getIssues().isEmpty() && !Utility.IsEmpty(cform.getLstIssue()))
			lstIssueSelection.add(cform.getLstIssue());
		request.setAttribute("lstIssueSelection", lstIssueSelection);

		request.setAttribute("lstCaseStatus", super.lookupManager.LoadCodeList(
				"CST", true, null, null));
		request.setAttribute("lstEncounterType", super.lookupManager.LoadCodeList(
				"CET", true, null, null));
		/*
		 * do the restore if(restore != null && restore.booleanValue() == true) {
		 * String tmpsavenote =
		 * this.caseManagementMgr.restoreTmpSave(providerNo,demono,programId);
		 * if(tmpsavenote != null) { note.setNote(tmpsavenote); } }
		 */
		log.debug("Set Encounter Type: " + note.getEncounter_type());
		log.debug("Fetched Note " + String.valueOf(note.getId()));
		this.caseManagementMgr.getEditors(note);
		cform.setCaseNote(note);
		/* set issue checked list */

		CheckBoxBean[] checkedList = new CheckBoxBean[issues.size()];
		for (int i = 0; i < issues.size(); i++) {
			checkedList[i] = new CheckBoxBean();
			CaseManagementIssue iss = (CaseManagementIssue) issues.get(i);
			checkedList[i].setIssue(iss);
			checkedList[i].setUsed(caseManagementMgr.haveIssue(iss.getId(),
					demono, providerNo, shelterId));

		}

		Iterator itr = note.getIssues().iterator();
		while (itr.hasNext()) {
			int id = ((CaseManagementIssue) itr.next()).getId().intValue();
			SetChecked(checkedList, id);
		}

		if(cform.getIssueCheckList()==null || cform.getIssueCheckList().length==0)cform.setIssueCheckList(checkedList);

		// Why are we caching over 31000 issues?
		// System.out.println("Fetching all issues");
		// /* set new issue list */
		// List aInfo = caseManagementMgr.getAllIssueInfo();
		// System.out.println("Got Issues and going to check for new ones");
		// List issueInfo = new ArrayList();
		// itr = aInfo.iterator();
		// while (itr.hasNext())
		// {
		// Issue iss = (Issue) itr.next();
		// if (!inCaseIssue(iss, issues))
		// {
		// LabelValueBean ll = new LabelValueBean();
		// ll.setValue(iss.getId().toString());
		// ll.setLabel(iss.getDescription());
		// issueInfo.add(ll);
		// }
		// }
		//                
		// System.out.println("Caching issues " + issueInfo.size());
		// cform.setNewIssueList(issueInfo);
		// if (!note.isSigned())
		// cform.setSign("off");
		// else
		if (note == null || note.getId() == null
				|| note.getId().intValue() == 0) {
			cform.setSign("off");
		} else {
			if (note.isSigned())
				cform.setSign("on");
			else
				cform.setSign("off");
		}
		if (!note.isIncludeissue())
			cform.setIncludeIssue("off");
		else
			cform.setIncludeIssue("on");
		/*
		 * boolean passwd = caseManagementMgr.getEnabled(); String chain =
		 * request.getParameter("chain");
		 * 
		 * if (chain != null && chain.length() > 0) {
		 * request.getSession(true).setAttribute("passwordEnabled", new
		 * Boolean(passwd)); return mapping.findForward(chain); }
		 * 
		 * request.setAttribute("passwordEnabled", new Boolean(passwd));
		 * 
		 * String ajax = request.getParameter("ajax"); if (ajax != null &&
		 * ajax.equalsIgnoreCase("true")) {
		 * request.getSession(true).setAttribute("caseManagementEntryForm", cform);
		 * return mapping.findForward("issueList_ajax"); }
		 */
		return mapping.findForward("view");
	}
	public ActionForward history(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("history");

		CaseManagementEntryFormBean cform = (CaseManagementEntryFormBean) form;
		HashMap actionParam = (HashMap) request.getAttribute("actionParam");
		if (actionParam == null) {
			actionParam = new HashMap();
			String cId = request.getParameter("clientId");
			if (Utility.IsEmpty(cId))
				cId = (String) request.getSession(true).getAttribute(
						"casemgmt_DemoNo");
			actionParam.put("clientId", cId);
		}
		request.setAttribute("actionParam", actionParam);

		String demono = (String) actionParam.get("clientId");
		request.setAttribute("clientId", demono);
		request.setAttribute("client", clientManager
				.getClientByDemographicNo(demono));

		request.setAttribute("demoName", getDemoName(demono));
		request.setAttribute("demoAge", getDemoAge(demono));
		request.setAttribute("demoDOB", getDemoDOB(demono));

		String noteid = (String) request.getParameter("noteId");
		if(Utility.IsEmpty(noteid) && request.getAttribute("noteId")!=null) noteid =request.getAttribute("noteId").toString();
		CaseManagementNote note = caseManagementMgr.getNote(noteid);
		super.setScreenMode(request, KeyConstants.FUN_CLIENTCASE);
		request.setAttribute("history", note.getHistory());
		request.setAttribute("client", clientManager
				.getClientByDemographicNo(demono));
		cform.setCaseNote_history(note.getHistory());
		return mapping.findForward("historyview");
	}
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(true);
		super.setScreenMode(request, KeyConstants.TAB_CLIENT_CASE);
		String providerNo = getProviderNo(request);
		CaseManagementEntryFormBean cform = (CaseManagementEntryFormBean) form;
		request.setAttribute("change_flag", "false");

		String demono = getDemographicNo(request);
		request.setAttribute("demoName", getDemoName(demono));
		request.setAttribute("demoAge", getDemoAge(demono));
		request.setAttribute("demoDOB", getDemoDOB(demono));

		request.setAttribute("from", request.getParameter("from"));
		boolean hasError =false; 
		if(cform.getCaseNote().getProgram_no()==null ||cform.getCaseNote().getProgram_no().intValue()==0)
		{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.message.save.case.program", request.getContextPath()));
			saveMessages(request, messages);
			hasError = true;
		}
		if(Utility.IsEmpty(cform.getCaseNote().getNote()))
		{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.message.save.case.note", request.getContextPath()));
			saveMessages(request, messages);
			hasError = true;
		}
		if(hasError)return edit(mapping, form, request, response);
		Integer noteId = noteSave(cform, request);
		request.setAttribute("noteId", noteId.toString());
		/* prepare the message */
		if (messages.get().hasNext()) {
			return edit(mapping, form, request, response);
		}
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"message.save.success", request.getContextPath()));
		saveMessages(request, messages);

		// are we in the new encounter and chaining actions?
		String chain = request.getParameter("chain");
		if (chain != null) {
			request.getSession(true).setAttribute("newNote", new Boolean(false));
			request.getSession(true).setAttribute("saveNote", new Boolean(true)); // tell
																				// CaseManagementView
																				// we
																				// have
																				// just
																				// saved
																				// note
			return mapping.findForward(chain);
		}

		// this.caseManagementMgr.saveNote();
		if ("on".equals(cform.getSign()) && cform.getCaseNote().getCaseStatusId().intValue() == 1){
//			request.setAttribute("noteId", noteId);
//			return history(mapping, form, request, response);
			request.setAttribute("isReadOnly", Boolean.TRUE);
		}
		return edit(mapping, form, request, response);
		
		// return mapping.findForward("view");
	}
	private Integer noteSave(CaseManagementEntryFormBean cform,
			HttpServletRequest request) throws Exception {

		// we don't want to save empty notes!
		boolean hasError = false;
		CaseManagementNote note = (CaseManagementNote) cform.getCaseNote();
		//note.setProgram_no(cform.getProgramId());
		String noteTxt = note.getNote();
		if (noteTxt == null || noteTxt.equals(""))
			return new Integer(-1);

		String providerNo = (String) request.getSession(true).getAttribute(
				KeyConstants.SESSION_KEY_PROVIDERNO);
		Provider provider = getProvider(request);
		// String userName = provider != null ? provider.getFullName() : "";

		String demo = getDemographicNo(request);
		/*
		 * CaseManagementCPP cpp = this.caseManagementMgr.getCPP(demo); if (cpp ==
		 * null) { cpp = new CaseManagementCPP(); cpp.setDemographic_no(demo); }
		 * 
		 * String caisiLoaded = (String)
		 * request.getSession(true).getAttribute("caisiLoaded"); boolean inCaisi =
		 * false; if (caisiLoaded != null &&
		 * caisiLoaded.equalsIgnoreCase("true")) inCaisi = true;
		 */
		String lastSavedNoteString = (String) request.getSession(true)
				.getAttribute("lastSavedNoteString");
		Date now = new Date();
		/* get the checked issue save into note */
		List issuelist = new ArrayList();
		CheckBoxBean[] checkedlist = (CheckBoxBean[]) cform.getIssueCheckList();

		String sign = (String) request.getParameter("sign");
		// String includeIssue = (String) request.getParameter("includeIssue");
		if (sign == null || !sign.equals("on")) {
			note.setSigning_provider_no("");
			note.setSigned(false);
			cform.setSign("off");
		} else {
			note.setSigning_provider_no(providerNo);
			note.setSigned(true);
		}

		note.setProvider_no(providerNo);
		if (provider != null)
			note.setProvider(provider);

		WebApplicationContext ctx = this.getSpringContext();
		String role = null;
		Integer currentFacilityId = (Integer) request.getSession(true)
				.getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
		// if this is an update, don't overwrite the program id
		/*
		if (note.getProgram_no()==null ||note.getProgram_no().intValue()==0) {
			try {
				Integer programId = this.clientManager.getRecentProgramId(
						Integer.valueOf(demo), providerNo, currentFacilityId);
						
				request.getSession(true).setAttribute("case_program_id", programId);
				note.setProgram_no(programId);
			} catch (Exception e) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"error.case.note.program", request.getContextPath()));
				saveMessages(request, messages);
				throw e;
			}
		}		
		 * try { role =
		 * String.valueOf((programManager.getProgramProvider(note.getProvider_no(),
		 * note.getProgram_no())).getRole().getId()); } catch (Throwable e) {
		 * log.error(e); role = "0"; }
		 * if(request.getSession(true).getAttribute("archiveView")!="true")
		 * note.setReporter_caisi_role(role); else
		 * note.setReporter_caisi_role("1");
		 * 
		 * note.setReporter_caisi_role(role);
		 * 
		 * try { team =
		 * String.valueOf((admissionManager.getAdmission(note.getProgram_no(),
		 * Integer.valueOf(note.getDemographic_no()))).getTeamId()); } catch
		 * (Throwable e) { log.error(e); team = "0"; }
		 * note.setReporter_program_team(team);
		 */
		Set issueset = new HashSet();
		Set noteSet = new HashSet();
		String ongoing = "";
		String[] iss = cform.getTxtIssueKey().split(":");
		for (int i = 0; i < iss.length; i++) {
			CaseManagementIssue cmIss = new CaseManagementIssue();
			Integer issId = Integer.valueOf(iss[i]);
			cmIss.setIssue_id(issId);
			cmIss.setDemographic_no(new Integer(demo));
			cmIss.setLastUpdateUser(providerNo); 
			cmIss.setUpdate_date(now);
			// cmIss.setIssue(caseManagementMgr.getIssue(issId.toString()));
			issueset.add(cmIss);
			issuelist.add(cmIss);
		}

		note.setIssues(issueset);

		/* remove signature and the related issues from note */
		String noteString = note.getNote();
		// noteString = removeSignature(noteString);
		noteString = removeCurrentIssue(noteString);
		note.setNote(noteString);
		note.setIncludeissue(true);
		/* add the related issues to note */

		String issueString = "";
		issueString = createIssueString(issueset, note.getCaseStatusId());
		// insert the string before signiture

		int index = noteString.indexOf("\n[[");
		if (index >= 0) {
			String begString = noteString.substring(0, index);
			String endString = noteString.substring(index + 1);
			note.setNote(begString + issueString + endString);
		} else {
			note.setNote(noteString + issueString);
		}

		/* save all issue changes for demographic */
		// caseManagementMgr.saveAndUpdateCaseIssues(issuelist);
		// Quatro Issue Logic
		// issuelist = new ArrayList<CaseManagementIssue>();
		caseManagementMgr.saveAndUpdateCaseIssues(issuelist);
		if (note.isSigned()) {
		//	prop = ResourceBundle.getBundle("oscarResources", request.getLocale());
			String message = "[ Signed on "
					/*
				    + prop.getString("oscarEncounter.class.EctSaveEncounterAction.msgSigned")
					+ " "
					*/
					+ UtilDateUtilities.DateToString(now, "dd-MMM-yyyy H:mm",
							request.getLocale())
					+ " by "
					/*
					+ prop.getString("oscarEncounter.class.EctSaveEncounterAction.msgSigBy")
					+ " "
					*/ 
					+ provider.getFormattedName() + " ]";
			String n = note.getNote() + "\n" + message;
			note.setNote(n);

			// only update appt if there is one
			// Lillian comments appointment logic,because sessionBean is null
			// if (sessionBean.appointmentNo != null &&
			// !sessionBean.appointmentNo.equals(""))
			// caseManagementMgr.updateAppointment(sessionBean.appointmentNo,
			// sessionBean.status, "sign");
		}

		if (note.getPassword() != null && note.getPassword().length() > 0) {
			note.setLocked(true);
		} else {
			note.setLocked(false);
		}

		int revision;

		if (note.getRevision() != null) {
			revision = Integer.parseInt(note.getRevision());
			++revision;
		} else
			revision = 1;

		note.setRevision(String.valueOf(revision));

		String observationDate = cform.getObservation_date();

		if (observationDate != null && !observationDate.equals("")) {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"dd/MMM/yyyy HH:mm");
			Date dateObserve = formatter.parse(observationDate);
			note.setObservation_date(dateObserve);
		} else if (note.getObservation_date() == null) {
			note.setObservation_date(now);
		}

		note.setUpdate_date(now);
		if (note.getCreate_date() == null)
			note.setCreate_date(now);

		/* save note including add signature */
		String savedStr = caseManagementMgr.saveNote(note, providerNo,
				lastSavedNoteString);
		/* remember the str written into echart */
		request.getSession(true).setAttribute("lastSavedNoteString", savedStr);
		caseManagementMgr.getEditors(note);
		/*
		try {
			this.caseManagementMgr.deleteTmpSave(providerNo, note
					.getDemographic_no(), note.getProgram_no());
		} catch (Throwable e) {
			log.warn(e);
		}
	*/
		return note.getId();
	}
	/*
	 * Insert encounter reason for new note
	 */
	private void insertReason(HttpServletRequest request,
			CaseManagementNote note) {
		String reqStr = request.getParameter("note_edit");
		if (reqStr != null && "new".equals(reqStr)) {
			note.setNote("\n["
					+ UtilDateUtilities.DateToString(UtilDateUtilities.now(),
							"dd-MMM-yyyy HH:mm:ss", request.getLocale())
					+ " .: " + "] \n");
		}
	}

	private CaseManagementNote getLastSaved(HttpServletRequest request,
			String demono, String providerNo) {
		CaseManagementNote note = null;
		List notes = null;
		Integer shelterId = (Integer) request.getSession(true).getAttribute(
				KeyConstants.SESSION_KEY_SHELTERID);
		UserProperty prop = caseManagementMgr.getUserProperty(providerNo,
				UserProperty.STALE_NOTEDATE);
		notes = caseManagementMgr.getNotes(demono, prop, shelterId, providerNo);
		/*
		Integer programId = (Integer) request.getSession(true).getAttribute("case_program_id");

		if (programId == null) {
			programId = new Integer("0");
		}
		*/

		for (int idx = notes.size() - 1; idx >= 0; --idx) {
			CaseManagementNote n = (CaseManagementNote) notes.get(idx);
			if (!n.isSigned() && n.getProvider_no().equals(providerNo)) {
				note = n;
				request.getSession(true).setAttribute("newNote", "false");
				break;
			}
		}

		return note;
	}


}
