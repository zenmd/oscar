<%--

    Copyright (c) 2001-2002. Department of Family Medicine, McMaster University. All Rights Reserved.
    This software is published under the GPL GNU General Public License.
    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public License
    as published by the Free Software Foundation; either version 2
    of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.

    This software was written for the
    Department of Family Medicine
    McMaster University
    Hamilton
    Ontario, Canada

--%>

<%@ page
	import="oscar.form.graphic.*, oscar.util.*, oscar.form.*, oscar.form.data.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>


<%
    String formClass = "ONAREnhanced";
    String formLink = "formonarenhancedpg2.jsp";

    int demoNo = Integer.parseInt(request.getParameter("demographic_no"));
    int formId = Integer.parseInt(request.getParameter("formId"));
    int provNo = Integer.parseInt((String) session.getAttribute("user"));
    FrmRecord rec = (new FrmRecordFactory()).factory(formClass);
    java.util.Properties props = rec.getFormRecord(demoNo, formId);

    FrmData fd = new FrmData();
    String resource = fd.getResource();
    resource = resource + "ob/riskinfo/";
    props.setProperty("c_lastVisited", "pg2");

    //get project_home
    String project_home = request.getContextPath().substring(1);    
%>
<%
  boolean bView = false;
  if (request.getParameter("view") != null && request.getParameter("view").equals("1")) bView = true; 
  %>

<% 
    FrmAREnhancedBloodWorkTest ar1BloodWorkTest = new FrmAREnhancedBloodWorkTest(demoNo, formId); 
    java.util.Properties ar1Props = ar1BloodWorkTest.getAr1Props(); 
    int ar1BloodWorkTestListSize = ar1BloodWorkTest.getAr1BloodWorkTestListSize(); 
    String ar1CompleteSignal = "AR1 labs Complete"; 
  %>
<%
	String abo = "";
	String rh ="";
	if(UtilMisc.htmlEscape(props.getProperty("ar2_bloodGroup", "")).equals("") ){
		abo = UtilMisc.htmlEscape(ar1Props.getProperty("pg1_labABO", ""));
	}else{
		abo = UtilMisc.htmlEscape(props.getProperty("ar2_bloodGroup", ""));
	}

	if(UtilMisc.htmlEscape(props.getProperty("ar2_rh", "")).equals("")){
		rh = UtilMisc.htmlEscape(ar1Props.getProperty("pg1_labRh", ""));
	}else{
		rh = UtilMisc.htmlEscape(props.getProperty("ar2_rh", ""));
	}
%>
<html:html locale="true">
<head>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/global.js"></script>
<title>Antenatal Record 2</title>
<html:base />
<link rel="stylesheet" type="text/css"
	href="<%=bView?"arStyleView.css" : "arStyle.css"%>">
<!-- calendar stylesheet -->
<link rel="stylesheet" type="text/css" media="all" href="../share/calendar/calendar.css" title="win2k-cold-1" />

<!-- main calendar program -->
<script type="text/javascript" src="../share/calendar/calendar.js"></script>

<!-- language for the calendar -->
<script type="text/javascript" src="../share/calendar/lang/<bean:message key="global.javascript.calendar"/>"></script>

<!-- the following script defines the Calendar.setup helper function, which makes
       adding a calendar a matter of 1 or 2 lines of code. -->
<script type="text/javascript" src="../share/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>

<script>
	$(document).ready(function(){	
		window.moveTo(0, 0);
		window.resizeTo(screen.availWidth, screen.availHeight);
	});
</script>

<style type="text/css">

body{
margin: 0;
padding: 0;
border: 0;
overflow: hidden;
height: 100%; 
max-height: 100%; 
}

#framecontent{
position: absolute;
top: 0;
bottom: 0; 
left: 0;
width: 200px; /*Width of frame div*/
height: 100%;
overflow: hidden; /*Disable scrollbars. Set to "scroll" to enable*/
background: navy;
color: white;
}

#maincontent{
position: fixed;
top: 0; 
left: 200px; /*Set left value to WidthOfFrameDiv*/
right: 0;
bottom: 0;
overflow: auto; 
background: #fff;
}

.innertube{
margin: 15px; /*Margins for inner DIV inside each DIV (to provide padding)*/
}

* html body{ /*IE6 hack*/
padding: 0 0 0 200px; /*Set value to (0 0 0 WidthOfFrameDiv)*/
}

* html #maincontent{ /*IE6 hack*/
height: 100%; 
width: 100%; 
}

</style>

<script>

	function validate() {
		for(var x=1;x<=54;x++) {
			if($('#sv_'+ x).length>0) {
				var patt1=new RegExp("^(\\d{4}/\\d{2}/\\d{2})?$");
				if(!patt1.test($("input[name='pg2_date"+x+"']").val())) {
					alert("Subsequent Visit - Date is invalid");
					return false;
				}
				
				patt1=new RegExp("^((\\d)+w(\\+\\d)?)?$");
				if(!patt1.test($("input[name='pg2_gest"+x+"']").val())) {
					alert("Subsequent Visit - Gestational Age is invalid");
					return false;
				}
				patt1=new RegExp("^(\\d{1,4}(\\.\\d)?)?$");
				if(!patt1.test($("input[name='pg2_wt"+x+"']").val())) {
					alert("Subsequent Visit - Weight is invalid");
					return false;
				}
				patt1=new RegExp("^(\\d{1,3}/\\d{1,3})?$");
				if(!patt1.test($("input[name='pg2_BP"+x+"']").val())) {
					alert("Subsequent Visit - BP is invalid");
					return false;
				}
				
				
			}
		}
		
		for(var x=1;x<=12;x++) {
			if($('#us_'+ x).length>0) {
				var patt1=new RegExp("^(\\d{4}/\\d{2}/\\d{2})?$");
				if(!patt1.test($("input[name='ar2_uDate"+x+"']").val())) {
					alert("U/S - Date is invalid");
					return false;
				}
				
				patt1=new RegExp("^((\\d)+w(\\+\\d)?)?$");
				if(!patt1.test($("input[name='ar2_uGA"+x+"']").val())) {
					alert("U/S - Gestational Age is invalid");
					return false;
				}
			}
		}
		return true;
	}

	$(document).ready(function() {
		$("select[name='ar2_strep']").val('<%= UtilMisc.htmlEscape(props.getProperty("ar2_strep", "")) %>');
		$("select[name='ar2_bloodGroup']").val('<%= abo %>');
		$("select[name='ar2_rh']").val('<%= rh %>');
	});
	
	function adjustDynamicListTotals() {		
		$('#rf_num').val(adjustDynamicListTotalsRF('rf_',20));
		$('#sv_num').val(adjustDynamicListTotalsSV('sv_',54));
		$('#us_num').val(adjustDynamicListTotalsUS('us_',12));
	}
	
	function adjustDynamicListTotalsRF(name,max) {		
		var total = 0;
		for(var x=1;x<=max;x++) {
			if($('#'+ name +x).length>0) {
				total++;
				if(x != total) {
					$("#rf_"+x).attr('id','rf_'+total);				
					$("input[name='c_riskFactors"+x+"']").attr('name','c_riskFactors'+total);
					$("input[name='c_planManage"+x+"']").attr('name','c_planManage'+total);				
				}
			}			
		}	
		return total;
	}
	
	function adjustDynamicListTotalsSV(name,max) {		
		var total = 0;
		for(var x=1;x<=max;x++) {
			if($('#'+ name +x).length>0) {
				total++;
				if(x != total) {					
					$("#sv_"+x).attr('id','sv_'+total);				
					$("input[name='pg2_date"+x+"']").attr('name','pg2_date'+total);
					$("input[name='pg2_gest"+x+"']").attr('name','pg2_gest'+total);
					$("input[name='pg2_wt"+x+"']").attr('name','pg2_wt'+total);
					$("input[name='pg2_BP"+x+"']").attr('name','pg2_BP'+total);
					$("input[name='pg2_urinePr"+x+"']").attr('name','pg2_urinePr'+total);
					$("input[name='pg2_urineGl"+x+"']").attr('name','pg2_urineGl'+total);
					$("input[name='pg2_ht"+x+"']").attr('name','pg2_ht'+total);
					$("input[name='pg2_presn"+x+"']").attr('name','pg2_presn'+total);
					$("input[name='pg2_FHR"+x+"']").attr('name','pg2_FHR'+total);
					$("input[name='pg2_comments"+x+"']").attr('name','pg2_comments'+total);
				}
			}			
		}	
		return total;
	}
	
	function adjustDynamicListTotalsUS(name,max) {		
		var total = 0;
		for(var x=1;x<=max;x++) {
			if($('#'+ name +x).length>0) {
				total++;
				if(x != total) {
					$("#us_"+x).attr('id','us_'+total);				
					$("input[name='ar2_uDate"+x+"']").attr('name','ar2_uDate'+total);
					$("input[name='ar2_uGA"+x+"']").attr('name','ar2_uGA'+total);
					$("input[name='ar2_uResults"+x+"']").attr('name','ar2_uResults'+total);						  
				}
			}			
		}	
		return total;
	}
</script>


<script>
function addRiskFactor() {
	var total = jQuery("#rf_num").val();
	total++;
	jQuery("#rf_num").val(total);
	jQuery.ajax({url:'onarenhanced_rf.jsp?n='+total,async:false, success:function(data) {
		  jQuery("#rf_container").append(data);
	}});
}

function deleteRiskFactor(id) {
	var followUpId = jQuery("input[name='rf_"+id+"']").val();
	jQuery("form[name='FrmForm']").append("<input type=\"hidden\" name=\"rf.delete\" value=\""+followUpId+"\"/>");
	jQuery("#rf_"+id).remove();

}

function setInput(id,type,val) {
	//alert("input[name='"+type+id+"']" + "=" + val);
	jQuery("input[name='"+type+id+"']").each(function() {
		jQuery(this).val(val);
	});
}

function setCheckbox(id,type,val) {
	jQuery("input[name='"+type+id+"']").each(function() {
		if(val=='true')
			jQuery(this).attr("checked",true);
		else
			jQuery(this).attr("checked",false);
	});
}

function addSubsequentVisit() {
	var total = jQuery("#sv_num").val();
	total++;
	jQuery("#sv_num").val(total);
	jQuery.ajax({url:'onarenhanced_sv.jsp?n='+total,async:false, success:function(data) {
		  jQuery("#sv_container").append(data);
	}});
}

function deleteSubsequentVisit(id) {
	var followUpId = jQuery("input[name='sv_"+id+"']").val();
	jQuery("form[name='FrmForm']").append("<input type=\"hidden\" name=\"sv.delete\" value=\""+followUpId+"\"/>");
	jQuery("#sv_"+id).remove();

}

function addUltraSound() {
	var total = jQuery("#us_num").val();
	total++;
	jQuery("#us_num").val(total);
	jQuery.ajax({url:'onarenhanced_us.jsp?n='+total,async:false, success:function(data) {
		  jQuery("#us_container").append(data);
	}});
}

function deleteUltraSound(id) {
	var followUpId = jQuery("input[name='us_"+id+"']").val();
	jQuery("form[name='FrmForm']").append("<input type=\"hidden\" name=\"us.delete\" value=\""+followUpId+"\"/>");
	jQuery("#us_"+id).remove();

}

jQuery(document).ready(function() {
	<%
		String rf = props.getProperty("rf_num", "0");
		if(rf.length() == 0)
			rf = "0";		
		int rfNum = Integer.parseInt(rf);
		for(int x=0;x<rfNum;x++) {	
			int y=x+1;
		%>
		jQuery.ajax({url:'onarenhanced_rf.jsp?n='+<%=y%>,async:false, success:function(data) {
			  jQuery("#rf_container").append(data);
			  setInput(<%=y%>,"c_riskFactors",'<%= props.getProperty("c_riskFactors"+y, "") %>');
			  setInput(<%=y%>,"c_planManage",'<%= props.getProperty("c_planManage"+y, "") %>');			 
		}});
		<%
		}
	%>
	
	
	<%
	String sv = props.getProperty("sv_num", "0");
	if(sv.length() == 0)
		sv = "0";		
	int svNum = Integer.parseInt(sv);
	for(int x=0;x<svNum;x++) {
		int y=x+1;
	%>
		jQuery.ajax({url:'onarenhanced_sv.jsp?n='+<%=y%>,async:false, success:function(data) {
		  jQuery("#sv_container").append(data);
		  setInput(<%=y%>,"pg2_date",'<%= props.getProperty("pg2_date"+y, "") %>');
		  setInput(<%=y%>,"pg2_gest",'<%= props.getProperty("pg2_gest"+y, "") %>');
		  setInput(<%=y%>,"pg2_wt",'<%= props.getProperty("pg2_wt"+y, "") %>');
		  setInput(<%=y%>,"pg2_BP",'<%= props.getProperty("pg2_BP"+y, "") %>');
		  setInput(<%=y%>,"pg2_urinePr",'<%= props.getProperty("pg2_urinePr"+y, "") %>');
		  setInput(<%=y%>,"pg2_urineGl",'<%= props.getProperty("pg2_urineGl"+y, "") %>');
		  setInput(<%=y%>,"pg2_ht",'<%= props.getProperty("pg2_ht"+y, "") %>');
		  setInput(<%=y%>,"pg2_presn",'<%= props.getProperty("pg2_presn"+y, "") %>');
		  setInput(<%=y%>,"pg2_FHR",'<%= props.getProperty("pg2_FHR"+y, "") %>');
		  setInput(<%=y%>,"pg2_comments",'<%= props.getProperty("pg2_comments"+y, "") %>');		 
	}});
	<%
	}
%>


<%
String us = props.getProperty("us_num", "0");
if(us.length() == 0)
	us = "0";		
int usNum = Integer.parseInt(us);
for(int x=1;x<usNum+1;x++) {			
%>
	jQuery.ajax({url:'onarenhanced_us.jsp?n='+<%=x%>,async:false, success:function(data) {
	  jQuery("#us_container").append(data);
	  setInput(<%=x%>,"ar2_uDate",'<%= props.getProperty("ar2_uDate"+x, "") %>');
	  setInput(<%=x%>,"ar2_uGA",'<%= props.getProperty("ar2_uGA"+x, "") %>');
	  setInput(<%=x%>,"ar2_uResults",'<%= props.getProperty("ar2_uResults"+x, "") %>');	  
}});
<%
}
%>
});


$(document).ready(function(){
	updatePageLock(false);
});

var lockData;
var mylock=false;

function requestLock() {
	updatePageLock(true);
}

function releaseLock() {
	updatePageLock(false);
}

function updatePageLock(lock) {
	   haveLock=false;
		$.ajax({
		   type: "POST",
		   url: "<%=request.getContextPath()%>/PageMonitoringService.do",
		   data: { method: "update", page: "formonarenhanced<%=demoNo%>", lock: lock },
		   dataType: 'json',
		   success: function(data,textStatus) {
			   lockData=data;
				var locked=false;
				var lockedProviderName='';
				var providerNames='';
				haveLock=false;
			   $.each(data, function(key, val) {				
			     if(val.locked) {
			    	 locked=true;
			    	 lockedProviderName=val.providerName;
			     }
			     if(val.locked==true && val.self==true) {
					   haveLock=true;
				   }
			     if(providerNames.length > 0)
			    	 providerNames += ",";
			     providerNames += val.providerName;
			     
			   });

			   var lockedMsg = locked?'<span style="color:red" title="'+lockedProviderName+'">&nbsp(locked)</span>':'';
			   $("#lock_notification").html(
				'<span title="'+providerNames+'">Viewers:'+data.length+lockedMsg+'</span>'	   
			   );
			   
			  
			   if(haveLock==true) { //i have the lock
					$("#lock_req_btn").hide();
				   	$("#lock_rel_btn").show();
			   } else if(locked && !haveLock) { //someone else has lock.
				   $("#lock_req_btn").hide();
			   		$("#lock_rel_btn").hide();
			   } else { //no lock
				   $("#lock_req_btn").show();
			   		$("#lock_rel_btn").hide();
			   }
		   }
		 }
		);
		setTimeout(function(){updatePageLock(haveLock)},30000);
		   
}
</script>

</head>

<script type="text/javascript" language="Javascript">
    function reset() {        
        document.forms[0].target = "";
        document.forms[0].action = "/<%=project_home%>/form/formname.do" ;
	}
    function onPrint() {
        document.forms[0].submit.value="print"; 
        var ret = checkAllDates();
        if(ret==true)
        {
            if( document.forms[0].c_finalEDB.value == "" && !confirm("<bean:message key="oscarEncounter.formOnar.msgNoEDB"/>")) {
                ret = false;
            }
            else {
                document.forms[0].action = "../form/createpdf?__title=Antenatal+Record+Part+2&__cfgfile=onar2PrintCfgPg1&__cfgGraphicFile=onar2PrintGraphCfgPg1&__template=onar2";
                document.forms[0].target="_blank";       
            }
                
        }   
        return ret;
    }

    function getFormEntity(name) {
		if (name.value.length>0) {
			return true;
		} else {
			return false;
		}
		/*
		for (var j=0; j<document.forms[0].length; j++) { 
				if (document.forms[0].elements[j] != null && document.forms[0].elements[j].name ==  name ) {
					 return document.forms[0].elements[j] ;
				}
		}*/
    }
    function onWtSVG() {
        var ret = checkAllDates();
		var param="";
		var obj = null;
		if (document.forms[0].c_finalEDB != null && (document.forms[0].c_finalEDB.value).length==10) {
			param += "?c_finalEDB=" + document.forms[0].c_finalEDB.value;
		} else {
			ret = false;
		}
		if (document.forms[0].c_ppWt != null && (document.forms[0].c_ppWt.value).length>0) {
			param += "&c_ppWt=" + document.forms[0].c_ppWt.value;
		} else {
			ret = false;
		}

		obj = document.forms[0].pg2_date1;
		if (getFormEntity(obj))  param += "&pg2_date1=" + obj.value; 
		obj = document.forms[0].pg2_date2;
		if (getFormEntity(obj))  param += "&pg2_date2=" + obj.value; 
		obj = document.forms[0].pg2_date3;
		if (getFormEntity(obj))  param += "&pg2_date3=" + obj.value; 
		obj = document.forms[0].pg2_date4;
		if (getFormEntity(obj))  param += "&pg2_date4=" + obj.value; 
		obj = document.forms[0].pg2_date5;
		if (getFormEntity(obj))  param += "&pg2_date5=" + obj.value; 
		obj = document.forms[0].pg2_date6;
		if (getFormEntity(obj))  param += "&pg2_date6=" + obj.value; 
		obj = document.forms[0].pg2_date7;
		if (getFormEntity(obj))  param += "&pg2_date7=" + obj.value; 
		obj = document.forms[0].pg2_date8;
		if (getFormEntity(obj))  param += "&pg2_date8=" + obj.value; 
		obj = document.forms[0].pg2_date9;
		if (getFormEntity(obj))  param += "&pg2_date9=" + obj.value; 
		obj = document.forms[0].pg2_date10;
		if (getFormEntity(obj))  param += "&pg2_date10=" + obj.value; 
		obj = document.forms[0].pg2_date11;
		if (getFormEntity(obj))  param += "&pg2_date11=" + obj.value; 
		obj = document.forms[0].pg2_date12;
		if (getFormEntity(obj))  param += "&pg2_date12=" + obj.value; 
		obj = document.forms[0].pg2_date13;
		if (getFormEntity(obj))  param += "&pg2_date13=" + obj.value; 
		obj = document.forms[0].pg2_date14;
		if (getFormEntity(obj))  param += "&pg2_date14=" + obj.value; 
		obj = document.forms[0].pg2_date15;
		if (getFormEntity(obj))  param += "&pg2_date15=" + obj.value; 
		obj = document.forms[0].pg2_date16;
		if (getFormEntity(obj))  param += "&pg2_date16=" + obj.value; 
		obj = document.forms[0].pg2_date17;
		if (getFormEntity(obj))  param += "&pg2_date17=" + obj.value; 
		obj = document.forms[0].pg2_wt1;
		if (getFormEntity(obj))  param += "&pg2_wt1=" + obj.value; 
		obj = document.forms[0].pg2_wt2;
		if (getFormEntity(obj))  param += "&pg2_wt2=" + obj.value; 
		obj = document.forms[0].pg2_wt3;
		if (getFormEntity(obj))  param += "&pg2_wt3=" + obj.value; 
		obj = document.forms[0].pg2_wt4;
		if (getFormEntity(obj))  param += "&pg2_wt4=" + obj.value; 
		obj = document.forms[0].pg2_wt5;
		if (getFormEntity(obj))  param += "&pg2_wt5=" + obj.value; 
		obj = document.forms[0].pg2_wt6;
		if (getFormEntity(obj))  param += "&pg2_wt6=" + obj.value; 
		obj = document.forms[0].pg2_wt7;
		if (getFormEntity(obj))  param += "&pg2_wt7=" + obj.value; 
		obj = document.forms[0].pg2_wt8;
		if (getFormEntity(obj))  param += "&pg2_wt8=" + obj.value; 
		obj = document.forms[0].pg2_wt9;
		if (getFormEntity(obj))  param += "&pg2_wt9=" + obj.value; 
		obj = document.forms[0].pg2_wt10;
		if (getFormEntity(obj))  param += "&pg2_wt10=" + obj.value; 
		obj = document.forms[0].pg2_wt11;
		if (getFormEntity(obj))  param += "&pg2_wt11=" + obj.value; 
		obj = document.forms[0].pg2_wt12;
		if (getFormEntity(obj))  param += "&pg2_wt12=" + obj.value; 
		obj = document.forms[0].pg2_wt13;
		if (getFormEntity(obj))  param += "&pg2_wt13=" + obj.value; 
		obj = document.forms[0].pg2_wt14;
		if (getFormEntity(obj))  param += "&pg2_wt14=" + obj.value; 
		obj = document.forms[0].pg2_wt15;
		if (getFormEntity(obj))  param += "&pg2_wt15=" + obj.value; 
		obj = document.forms[0].pg2_wt16;
		if (getFormEntity(obj))  param += "&pg2_wt16=" + obj.value; 
		obj = document.forms[0].pg2_wt17;
		if (getFormEntity(obj))  param += "&pg2_wt17=" + obj.value; 
/*
		for (var i = 1; i < 18; i++) {
				getFormEntity(("pg2_date"+i)) ;
			if (obj != null && (obj.value).length>0) {
				param += "&pg2_date" + i + "=" + obj.value;
			}
			obj = getFormEntity(("pg2_wt"+i)) ;
			if (obj != null && (obj.value).length>0) {
				param += "&pg2_wt" + i + "=" + obj.value;
			}
		}
		for (var i = 18; i < 35; i++) {
			var obj = getFormEntity(("pg3_date"+i)) ;
			if (obj != null && (obj.value).length>0) {
				param += "&pg3_date" + i + "=" + obj.value;
			}
			obj = getFormEntity(("pg3_wt"+i)) ;
			if (obj != null && (obj.value).length>0) {
				param += "&pg3_wt" + i + "=" + obj.value;
			}
		}
*/

        if(ret==true)  {
            popupFixedPage(650,850,'formonar2wt.jsp'+param);
        }

    }
    function onSave() {    	
        document.forms[0].submit.value="save";
        var ret = checkAllDates();
        var ret1 = validate();
        if(ret==true && ret1 == true) {
            reset();
            ret = confirm("Are you sure you want to save this form?");
        }
        adjustDynamicListTotals();
        return ret && ret1;
    }
    
    function onSaveExit() {
        document.forms[0].submit.value="exit";
        var ret = checkAllDates();
        var ret1 = validate();
        if(ret==true && ret1 == true) {
            reset();
            ret = confirm("Are you sure you wish to save and close this window?");
        }
        adjustDynamicListTotals();
        return ret && ret1;
    }
    function popupPage(varpage) {
        windowprops = "height=700,width=960"+
            ",location=no,scrollbars=yes,menubars=no,toolbars=no,resizable=no,screenX=50,screenY=50,top=20,left=20";
        var popup = window.open(varpage, "ar1", windowprops);
        if (popup.opener == null) {
            popup.opener = self;
        }
    }
    function popPage(varpage,pageName) {
        windowprops = "height=700,width=960"+
            ",location=no,scrollbars=yes,menubars=no,toolbars=no,resizable=no,screenX=50,screenY=50,top=20,left=20";
        var popup = window.open(varpage,pageName, windowprops);
        //if (popup.opener == null) {
        //    popup.opener = self;
        //}
        popup.focus();
    }

	function isNumber(ss){
		var s = ss.value;
        var i;
        for (i = 0; i < s.length; i++){
            // Check that current character is number.
            var c = s.charAt(i);
			if (c == '.') {
				continue;
			} else if (((c < "0") || (c > "9"))) {
                alert('Invalid '+s+' in field ' + ss.name);
                ss.focus();
                return false;
			}
        }
        // All characters are numbers.
        return true;
    }
function popupFixedPage(vheight,vwidth,varpage) { 
  var page = "" + varpage;
  windowprop = "height="+vheight+",width="+vwidth+",location=no,scrollbars=yes,menubars=no,toolbars=no,resizable=yes,screenX=10,screenY=0,top=0,left=0";
  var popup=window.open(page, "planner", windowprop);
}
function wtEnglish2Metric(obj) {
	//if(isNumber(document.forms[0].c_ppWt) ) {
	//	weight = document.forms[0].c_ppWt.value;
	if(isNumber(obj) ) {
		weight = obj.value;
		weightM = Math.round(weight * 10 * 0.4536) / 10 ;
		if(confirm("Are you sure you want to change " + weight + " pounds to " + weightM +"kg?") ) {
			//document.forms[0].c_ppWt.value = weightM;
			obj.value = weightM;
		}
	}
}
function htEnglish2Metric(obj) {
	height = obj.value;
	if(height.length > 1 && height.indexOf("'") > 0 ) {
		feet = height.substring(0, height.indexOf("'"));
		inch = height.substring(height.indexOf("'"));
		if(inch.length == 1) {
			inch = 0;
		} else {
			inch = inch.charAt(inch.length-1)=='"' ? inch.substring(0, inch.length-1) : inch;
			inch = inch.substring(1);
		}
		
		//if(isNumber(feet) && isNumber(inch) )
			height = Math.round((feet * 30.48 + inch * 2.54) * 10) / 10 ;
			if(confirm("Are you sure you want to change " + feet + " feet " + inch + " inch(es) to " + height +"cm?") ) {
				obj.value = height;
			}
		//}
	}
}

/**
 * DHTML date validation script. Courtesy of SmartWebby.com (http://www.smartwebby.com/dhtml/)
 */
// Declaring valid date character, minimum year and maximum year
var dtCh= "/";
var minYear=1900;
var maxYear=9900;

    function isInteger(s){
        var i;
        for (i = 0; i < s.length; i++){
            // Check that current character is number.
            var c = s.charAt(i);
            if (((c < "0") || (c > "9"))) return false;
        }
        // All characters are numbers.
        return true;
    }

    function stripCharsInBag(s, bag){
        var i;
        var returnString = "";
        // Search through string's characters one by one.
        // If character is not in bag, append to returnString.
        for (i = 0; i < s.length; i++){
            var c = s.charAt(i);
            if (bag.indexOf(c) == -1) returnString += c;
        }
        return returnString;
    }

    function daysInFebruary (year){
        // February has 29 days in any year evenly divisible by four,
        // EXCEPT for centurial years which are not also divisible by 400.
        return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
    }
    function DaysArray(n) {
        for (var i = 1; i <= n; i++) {
            this[i] = 31
            if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
            if (i==2) {this[i] = 29}
       }
       return this
    }

    function isDate(dtStr){
        var daysInMonth = DaysArray(12)
        var pos1=dtStr.indexOf(dtCh)
        var pos2=dtStr.indexOf(dtCh,pos1+1)
        var strMonth=dtStr.substring(0,pos1)
        var strDay=dtStr.substring(pos1+1,pos2)
        var strYear=dtStr.substring(pos2+1)
        strYr=strYear
        if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
        if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
        for (var i = 1; i <= 3; i++) {
            if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
        }
        month=parseInt(strMonth)
        day=parseInt(strDay)
        year=parseInt(strYr)
        if (pos1==-1 || pos2==-1){
            return "format"
        }
        if (month<1 || month>12){
            return "month"
        }
        if (day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
            return "day"
        }
        if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
            return "year"
        }
        if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
            return "date"
        }
    return true
    }


    function checkTypeIn(obj) {
      if(!checkTypeNum(obj.value) ) {
          alert ("You must type in a number in the field.");
        }
    }

    function valDate(dateBox)
    {
        try
        {
            var dateString = dateBox.value;
            if(dateString == "")
            {
                return true;
            }
            var dt = dateString.split('/');
            var y = dt[0];
            var m = dt[1];
            var d = dt[2];
            var orderString = m + '/' + d + '/' + y;
            var pass = isDate(orderString);

            if(pass!=true)
            {
                alert('Invalid '+pass+' in field ' + dateBox.name);
                dateBox.focus();
                return false;
            }
        }
        catch (ex)
        {
            alert('Catch Invalid Date in field ' + dateBox.name);
            dateBox.focus();
            return false;
        }
        return true;
    }

    function checkAllDates()
    {
        var b = true;
        if(valDate(document.forms[0].c_finalEDB)==false){
            b = false;
        }else
        if(valDate(document.forms[0].pg2_formDate)==false){
            b = false;
        }
        return b;
    }

	function calcWeek(source) {
<%
String fedb = props.getProperty("c_finalEDB", "");

String sDate = "";
if (!fedb.equals("") && fedb.length()==10 ) {
	FrmGraphicAR arG = new FrmGraphicAR();
	java.util.Date edbDate = arG.getStartDate(fedb);
    sDate = UtilDateUtilities.DateToString(edbDate, "MMMMM dd, yyyy"); //"yy,MM,dd");
%>
	    var delta = 0;
        var str_date = getDateField(source.name);
        if (str_date.length < 10) return;
        var yyyy = str_date.substring(0, str_date.indexOf("/"));
        var mm = eval(str_date.substring(eval(str_date.indexOf("/")+1), str_date.lastIndexOf("/")) - 1);
        var dd = str_date.substring(eval(str_date.lastIndexOf("/")+1));
        var check_date=new Date(yyyy,mm,dd);
        var start=new Date("<%=sDate%>");

		if (check_date.getUTCHours() != start.getUTCHours()) {
			if (check_date.getUTCHours() > start.getUTCHours()) {
			    delta = -1 * 60 * 60 * 1000;
			} else {
			    delta = 1 * 60 * 60 * 1000;
			}
		} 

		var day = eval((check_date.getTime() - start.getTime() + delta) / (24*60*60*1000));
        var week = Math.floor(day/7);
		var weekday = day%7;
        source.value = week + "w+" + weekday;
<% } %>
}

	function getDateField(name) {
		var temp = ""; //pg2_gest1 - pg2_date1
		var n1 = name.substring(eval(name.indexOf("t")+1));

		if(name.indexOf("ar2_")>=0) {
			n1 = name.substring(eval(name.indexOf("A")+1));
			name = "ar2_uDate" + n1;
		} else if (n1>36) {
			name = "pg4_date" + n1;
		} else if (n1<=36 && n1>18) {
			name = "pg3_date" + n1;
		} else {
			name = "pg2_date" + n1;
		}
        
        for (var i =0; i <document.forms[0].elements.length; i++) {
            if (document.forms[0].elements[i].name == name) {
               return document.forms[0].elements[i].value;
    	    }
	    }
        return temp;
    }
function calToday(field) {
	var calDate=new Date();
	varMonth = calDate.getMonth()+1;
	varMonth = varMonth>9? varMonth : ("0"+varMonth);
	varDate = calDate.getDate()>9? calDate.getDate(): ("0"+calDate.getDate());
	field.value = calDate.getFullYear() + '/' + (varMonth) + '/' + varDate;
}
</script>


<body bgproperties="fixed" topmargin="0" leftmargin="0" rightmargin="0">
<div id="framecontent">
<div class="innertube">
	Reminders
	<br/>
	<br/>
	<div id="lock_notification">
		<span title="">Viewers:</span>
	</div>
	<div id="lock_req">
		<input id="lock_req_btn" type="button" value="Request Lock" onclick="requestLock();"/>
		<input style="display:none" id="lock_rel_btn" type="button" value="Release Lock" onclick="releaseLock();"/>
	</div>
</div>
</div>


<div id="maincontent">
<div id="content_bar" class="innertube" style="background-color: #c4e9f6">

<html:form action="/form/formname">

	<input type="hidden" name="commonField" value="ar2_" />
	<input type="hidden" name="c_lastVisited"
		value=<%=props.getProperty("c_lastVisited", "pg2")%> />
	<input type="hidden" name="demographic_no"
		value="<%= props.getProperty("demographic_no", "0") %>" />
	<input type="hidden" name="formCreated"
		value="<%= props.getProperty("formCreated", "") %>" />
	<input type="hidden" name="form_class" value="<%=formClass%>" />
	<input type="hidden" name="form_link" value="<%=formLink%>" />
	<input type="hidden" name="formId" value="<%=formId%>" />
	<input type="hidden" name="ID"
		value="<%= props.getProperty("ID", "0") %>" />
	<input type="hidden" name="provider_no"
		value=<%=request.getParameter("provNo")%> />
	<input type="hidden" name="provNo"
		value="<%= request.getParameter("provNo") %>" />
	<input type="hidden" name="submit" value="exit" />
	<%
	String historyet = "";
	if (request.getParameter("historyet") != null) {
		out.println("<input type=\"hidden\" name=\"historyet\" value=\"" + request.getParameter("historyet") + "\">" );
		historyet = "&historyet=" + request.getParameter("historyet");
	}
%>

	<table class="Head" class="hidePrint">
		<tr>
			<td align="left">
			<%
  if (!bView) {
%> <input type="submit" value="Save"
				onclick="javascript:return onSave();" /> <input type="submit"
				value="Save and Exit" onclick="javascript:return onSaveExit();" /> <%
  }
%> <input type="submit" value="Exit"
				onclick="javascript:return onExit();" /> <input type="submit"
				value="Print" onclick="javascript:return onPrint();" /></td>

			<%
  if (!bView) {
%>
			<td><a
				href="javascript: popPage('formlabreq07.jsp?demographic_no=<%=demoNo%>&formId=0&provNo=<%=provNo%>&labType=AnteNatal','LabReq');">LAB</a>
			</td>

			<td align="right"><b>View:</b> <a
				href="javascript: popupPage('formonarenhancedpg1.jsp?demographic_no=<%=demoNo%>&formId=<%=formId%>&provNo=<%=provNo+historyet%>&view=1');">
			AR1</a> </td>
			<td align="right"><b>Edit:</b> <a
				href="formonarenhancedpg1.jsp?demographic_no=<%=demoNo%>&formId=<%=formId%>&provNo=<%=provNo%>" onclick="return onSave();">AR1</a>
			
			<%if(((FrmONAREnhancedRecord)rec).isSendToPing(""+demoNo)) {	%> <a
				href="study/ar2ping.jsp?demographic_no=<%=demoNo%>">Send to PING</a>
			<% }	%>
			</td>
			<%
  }
%>
		</tr>
	</table>

	<table class="title" border="0" cellspacing="0" cellpadding="0"
		width="100%">
		<tr>
			<th><%=bView?"<font color='yellow'>VIEW PAGE: </font>" : ""%>ANTENATAL
			RECORD 2 </th>
		</tr>
	</table>
	<table width="50%" border="1" cellspacing="0" cellpadding="0">
		<tr>
			<td valign="top" width="50%">Patient's Last Name<br>
			<input type="text" name="c_lastName" style="width: 100%" size="30"
				maxlength="60" value="<%= props.getProperty("c_lastName", "") %>" />
			</td>
			<td valign="top">Patient's First Name<br>
			<input type="text" name="c_firstName" style="width: 100%" size="30"
				maxlength="60" value="<%= props.getProperty("c_firstName", "") %>" />
			</td>
		</tr>
		
		<tr>
			<td valign="top" width="50%">Birth attendants<br>
			<input type="text" name="c_ba" size="15" style="width: 100%"
				maxlength="25"
				value="<%= UtilMisc.htmlEscape(props.getProperty("c_ba", "")) %>">
			</td>
			<td>Newborn care<br>
			<input type="text" name="c_nc" size="15" style="width: 100%"
				maxlength="25"
				value="<%= UtilMisc.htmlEscape(props.getProperty("c_nc", "")) %>">
			</td>
		</tr>
	</table>
	<table width="100%" border="1" cellspacing="0" cellpadding="0">
		<tr>
			<td width="25%" colspan="5">Family physician<br>
			<input type="text" name="c_famPhys" size="30" maxlength="80"
				style="width: 100%"
				value="<%= props.getProperty("c_famPhys", "") %>" /></td>
			<td valign="top" rowspan="4" width="25%"><b>Final EDB</b>
			(yyyy/mm/dd)<br>
			<input type="text" name="c_finalEDB" style="width: 100%" size="10"
				maxlength="10" value="<%= props.getProperty("c_finalEDB", "") %>">
			</td>
			<td valign="top" rowspan="4" width="25%">Allergies or
			Sensitivities<br/>
			<textarea name="c_allergies" style="width: 100%" cols="30" rows="3"><%= props.getProperty("c_allergies", "") %></textarea></td>
			<td valign="top" rowspan="4">Medications / Herbals<br/>
			<textarea name="c_meds" style="width: 100%" cols="30" rows="3"><%= props.getProperty("c_meds", "") %></textarea></td>
		</tr>
		<tr>
			<td bgcolor="#CCCCCC" width="5%">G<br/>
			<input type="text" name="c_gravida" size="2" style="width: 100%"
				maxlength="3"
				value="<%= UtilMisc.htmlEscape(props.getProperty("c_gravida", "")) %>"></td>
			<td bgcolor="#CCCCCC" width="5%">T<br/>
			<input type="text" name="c_term" size="2" style="width: 100%"
				maxlength="3"
				value="<%= UtilMisc.htmlEscape(props.getProperty("c_term", "")) %>"></td>
			<td bgcolor="#CCCCCC" width="5%">P<br/>
			<input type="text" name="c_prem" size="2" style="width: 100%"
				maxlength="3"
				value="<%= UtilMisc.htmlEscape(props.getProperty("c_prem", "")) %>"></td>
			<td bgcolor="#CCCCCC" width="5%">A<br/>
			<input type="text" name="c_abort" size="2" style="width: 100%"
				maxlength="3"
				value="<%= UtilMisc.htmlEscape(props.getProperty("c_abort", "")) %>"></td>
			<td bgcolor="#CCCCCC" width="5%">L<br/>
			<input type="text" name="c_living" size="2" style="width: 100%"
				maxlength="3"
				value="<%= UtilMisc.htmlEscape(props.getProperty("c_living", "")) %>"></td>
		</tr>
	</table>

	<input type="hidden" id="rf_num" name="rf_num" value="<%= props.getProperty("rf_num", "0") %>"/>
			

	<table width="100%" border="0" cellspacing="0" cellpadding="0" id="rf_container">
		<tr>
			<th bgcolor="#CCCCCC" width="5%"></th>
			<th bgcolor="#CCCCCC" width="30%">Identified Risk Factors</th>
			<th bgcolor="#CCCCCC">Plan of Management</th>
		</tr>		
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td colspan="3"><input type="button" value="Add New" onclick="addRiskFactor();"/></td>
		</tr>
	</table>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	
		
		<tr>
			<td colspan="2"
				style="background-color: green; color: #FFFFFF; font-weight: bold;">
			<% 
        	if(ar1BloodWorkTestListSize == 9){ 
 	 %> <%=ar1CompleteSignal%> <% 
 	 } 
 	 %>
			</td>
		</tr>

	</table>
	<table width="100%" border="1" cellspacing="0" cellpadding="0">
		<tr>
			<th bgcolor="#CCCCCC" colspan="3">Recommended Immunoprophylaxis
			</th>
		</tr>
		<tr>
			<td width="30%"><b>Rh neg.</b> <input type="checkbox"
				name="ar2_rhNeg" <%= props.getProperty("ar2_rhNeg", "") %> />
			&nbsp;&nbsp;&nbsp;<b>Rh IG Given:</b> <input type="text"
				name="ar2_rhIG" id="ar2_rhIG" size="7" maxlength="10"
				value="<%= UtilMisc.htmlEscape(props.getProperty("ar2_rhIG", "")) %>">
				<img src="../images/cal.gif" id="ar2_rhIG_cal">
			</td>
			<td width="30%" nowrap><b>Rubella booster postpartum</b> <input
				type="checkbox" name="ar2_rubella"
				<%= props.getProperty("ar2_rubella", "") %> /></td>
			<td><b>Newborn needs: Hep B IG</b> <input type="checkbox"
				name="ar2_hepBIG" <%= props.getProperty("ar2_hepBIG", "") %> />
			&nbsp;&nbsp;&nbsp;<b>Hep B vaccine</b> <input type="checkbox"
				name="ar2_hepBVac" <%= props.getProperty("ar2_hepBVac", "") %> /></td>
		</tr>
	</table>
	<table width="100%" border="0">
		<tr>
			<td valign="top" bgcolor="#CCCCCC" align="center"><b>Subsequent
			Visits</b></td>
		</tr>
	</table>
	
	<input type="hidden" id="sv_num" name="sv_num" value="<%= props.getProperty("sv_num", "0") %>"/>
	
	<table width="100%" border="1" cellspacing="0" cellpadding="0" id="sv_container">
		<tr align="center">
			<td width="3%"></td>
			<td width="7%">Date<br>
			(yyyy/mm/dd)</td>
			<td width="7%">GA<br>
			(weeks)</td>
			<td width="7%"><!--  a href=# onclick="javascript:onWtSVG(); return false;"-->Weight<br>
			(Kg)</a></td>
			<td width="7%">B.P.</td>
			<td width="6%" colspan="2">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="2" align="center">Urine</td>
				</tr>
				<tr align="center">
					<td>Pr</td>
					<td>Gl</td>
				</tr>
			</table>
			</td>
			<td width="7%">SFH</td>
			<td width="7%">Pres.<br>
			Posn.</td>
			<td width="7%">FHR/<br/>
			Fm</td>
			<td nowrap>Comments</td>
			<!--  td nowrap width="4%">Cig./<br>day</td>-->
		</tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td colspan="10"><input type="button" value="Add New" onclick="addSubsequentVisit();"/></td>
		</tr>
	</table>
	
	<input type="hidden" id="us_num" name="us_num" value="<%= props.getProperty("us_num", "0") %>"/>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="20%">&nbsp;</td>
			<td width="80%" valign="top">
			<table width="100%" border="1" cellspacing="0" cellpadding="0">
				<tr>
					<th colspan="3" align="center" bgcolor="#CCCCCC">Ultrasound</th>
					<th colspan="2" align="center" bgcolor="#CCCCCC">Additional
					Lab Investigations</th>
				</tr>
				
				
				<tr>
					<td colspan="3" align="center">
					<div style="height:10em;overflow-y:scroll;width:100%">
						<table width="100%" id="us_container">
							<tr>
								<td width="5"></td>
								<td align="center" width="12%">Date</td>
								<td align="center" width="8%">GA</td>
								<td width="50%" align="center">Result</td>
							</tr>							
						</table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td colspan="4"><input type="button" value="Add New" onclick="addUltraSound();"/></td>
							</tr>
						</table>
						</div>
					</td>
					<td colspan="2" align="center" valign="top" >
						<table border="1" cellspacing="0" cellpadding="0">
							<tr>
								<td align="center" width="15%">Test</td>
								<td align="center">Result</td>
							</tr>
							<tr>
								<td>Hb</td>
								<td><input type="text" name="ar2_hb" size="10" maxlength="10"
								value="<%= UtilMisc.htmlEscape(props.getProperty("ar2_hb", "")) %>"></td>
							</tr>
				<tr>

					<td>ABO/Rh</td>
					
					<td>
						<select name="ar2_bloodGroup">
							<option value="NDONE">Not Done</option>
							<option value="A">A</option>
							<option value="B">B</option>
							<option value="AB">AB</option>
							<option value="O">O</option>
							<option value="UNK">Unknown</option>
						</select>		
						/
						<select name="ar2_rh">
							<option value="NDONE">Not Done</option>
							<option value="POS">Positive</option>
							<option value="NEG">Negative</option>
							<option value="UNK">Unknown</option>
						</select>					
					
					</td>

				</tr>
				<tr>

					<td>Repeat ABS</td>
					<td><input type="text" name="ar2_labABS" size="10"
						maxlength="10"
						value="<%= UtilMisc.htmlEscape(props.getProperty("ar2_labABS", "")) %>"></td>
				</tr>
				<tr>

					<td>1 hr. GCT</td>
					<td><input type="text" name="ar2_lab1GCT" size="10"
						maxlength="10"
						value="<%= UtilMisc.htmlEscape(props.getProperty("ar2_lab1GCT", "")) %>"></td>
				</tr>							
						</table>
					</td>
				</tr>
			

				<tr>
					<th colspan="3">Discussion Topics</th>
					<td>2 hr. GTT</td>
					<td><input type="text" name="ar2_lab2GTT" size="10"
						maxlength="10"
						value="<%= UtilMisc.htmlEscape(props.getProperty("ar2_lab2GTT", "")) %>"></td>
				</tr>
				<tr>
					<td colspan="3" rowspan="5">

					<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td width="33%"><input type="checkbox" name="ar2_exercise"
								<%= props.getProperty("ar2_exercise", "") %>>Exercise<br>
							<input type="checkbox" name="ar2_workPlan"
								<%= props.getProperty("ar2_workPlan", "") %>>Work plan<br>
							<input type="checkbox" name="ar2_intercourse"
								<%= props.getProperty("ar2_intercourse", "") %>>Intercourse<br>
							<input type="checkbox" name="ar2_travel"
								<%= props.getProperty("ar2_travel", "") %>>Travel<br>
							<input type="checkbox" name="ar2_prenatal"
								<%= props.getProperty("ar2_prenatal", "") %>>Prenatal
							classes<br>
							<input type="checkbox" name="ar2_birth"
								<%= props.getProperty("ar2_birth", "") %>>Birth plan<br>
							<input type="checkbox" name="ar2_onCall"
								<%= props.getProperty("ar2_onCall", "") %>>On call
							providers</td>
							<td width="33%"><input type="checkbox" name="ar2_preterm"
								<%= props.getProperty("ar2_preterm", "") %>>Preterm
							labour<br>
							<input type="checkbox" name="ar2_prom"
								<%= props.getProperty("ar2_prom", "") %>>PROM<br>
							<input type="checkbox" name="ar2_aph"
								<%= props.getProperty("ar2_aph", "") %>>APH<br>
							<input type="checkbox" name="ar2_fetal"
								<%= props.getProperty("ar2_fetal", "") %>>Fetal movement<br>
							<input type="checkbox" name="ar2_admission"
								<%= props.getProperty("ar2_admission", "") %>>Admission
							timing<br>
							<input type="checkbox" name="ar2_pain"
								<%= props.getProperty("ar2_pain", "") %>>Pain management<br>
							<input type="checkbox" name="ar2_labour"
								<%= props.getProperty("ar2_labour", "") %>>Labour
							support<br>
							</td>
							<td width="33%"><input type="checkbox" name="ar2_breast"
								<%= props.getProperty("ar2_breast", "") %>>Breast
							feeding<br>
							<input type="checkbox" name="ar2_circumcision"
								<%= props.getProperty("ar2_circumcision", "") %>>Circumcision<br>
							<input type="checkbox" name="ar2_dischargePlan"
								<%= props.getProperty("ar2_dischargePlan", "") %>>Discharge
							planning<br>
							<input type="checkbox" name="ar2_car"
								<%= props.getProperty("ar2_car", "") %>>Car seat safety<br>
							<input type="checkbox" name="ar2_depression"
								<%= props.getProperty("ar2_depression", "") %>>Depression<br>
							<input type="checkbox" name="ar2_contraception"
								<%= props.getProperty("ar2_contraception", "") %>>Contraception<br>
							<input type="checkbox" name="ar2_postpartumCare"
								<%= props.getProperty("ar2_postpartumCare", "") %>>Postpartum
							care</td>
						</tr>
					</table>

					</td>
					<td>GBS</td>
					<td>
						<select name="ar2_strep">
							<option value="NDONE">Not Done</option>
							<option value="POSSWAB">Positive swab result</option>
							<option value="POSSWAB">Positive swab result</option>
							<option value="POSURINE">Urine Positive for GBS</option>
							<option value="POSURINE">Group B Streptococcus </option>
							<option value="NEGSWAB">Negative swab result</option>
							<option value="DONEUNK">Done-result unknown</option>
							<option value="UNK">Unknown if screened</option>						
						</select>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table>

			</td>
		</tr>
	</table>
	<table width="100%" border="0">
		<tr>
			<td width="30%">Signature<br>
			<input type="text" name="pg2_signature" size="30" maxlength="50"
				style="width: 80%"
				value="<%= UtilMisc.htmlEscape(props.getProperty("pg2_signature", "")) %>">
			</td>
			<td width="20%">Date (yyyy/mm/dd)<br>
			<input type="text" name="pg2_formDate" class="spe"
				onDblClick="calToday(this)" size="10" maxlength="10"
				style="width: 80%"
				value="<%= props.getProperty("pg2_formDate", "") %>"></td>
			<td width="30%">Signature<br>
			<input type="text" name="pg2_signature2" size="30" maxlength="50"
				style="width: 80%"
				value="<%= UtilMisc.htmlEscape(props.getProperty("pg2_signature2", "")) %>">
			</td>
			<td width="20%">Date (yyyy/mm/dd)<br>
			<input type="text" name="pg2_formDate2" class="spe"
				onDblClick="calToday(this)" size="10" maxlength="10"
				style="width: 80%"
				value="<%= props.getProperty("pg2_formDate2", "") %>"></td>
		</tr>

	</table>

	<table class="Head" class="hidePrint">
		<tr>
			<td align="left">
			<%
  if (!bView) {
%> <input type="submit" value="Save"
				onclick="javascript:return onSave();" /> <input type="submit"
				value="Save and Exit" onclick="javascript:return onSaveExit();" /> <%
  }
%> <input type="submit" value="Exit"
				onclick="javascript:return onExit();" /> <input type="submit"
				value="Print" onclick="javascript:return onPrint();" /></td>
			<%
  if (!bView) {
%>
			<td><a
				href="javascript: popPage('formlabreq07.jsp?demographic_no=<%=demoNo%>&formId=0&provNo=<%=provNo%>&labType=AnteNatal','LabReq');">LAB</a>
			</td>

			<td align="right"><font size="-1"><b>View:</b> <a
				href="javascript: popupPage('formonarenhancedpg1.jsp?demographic_no=<%=demoNo%>&formId=<%=formId%>&provNo=<%=provNo%>&view=1');">
			AR1</a> </font></td>
			<td align="right"><b>Edit:</b> <a
				href="formonarenhancedpg1.jsp?demographic_no=<%=demoNo%>&formId=<%=formId%>&provNo=<%=provNo%>" onclick="return onSave();">AR1</a>
			</td>
			<%
  }
%>
		</tr>
	</table>

</html:form>
</div>
</div>
</body>
<script type="text/javascript">
Calendar.setup({ inputField : "ar2_rhIG", ifFormat : "%Y/%m/%d", showsTime :false, button : "ar2_rhIG_cal", singleClick : true, step : 1 });
</script>
</html:html>