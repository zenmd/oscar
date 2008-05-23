<%@ include file="/taglibs.jsp"%>
<div id="projecttools" class="toolgroup">
<div class="label"><strong>Navigator</strong></div>
<div class="body">
<div><span><c:out value="${client.formattedName}" />(<c:out
	value="${client.demographicNo}" />)</span> <c:choose>
	<c:when test="${'C' eq summary}">
		<div><b>Summary</b>&nbsp;&nbsp;|&nbsp;&nbsp;</div>
	</c:when>
	<c:when test="${'V' eq summary}">
		<div><html:link action="/PMmodule/QuatroClientSummary.do"
			name="actionParam" style="color:Navy;text-decoration:none;">Summary</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		</div>
	</c:when>
	<c:otherwise>
				&nbsp;
			</c:otherwise>
</c:choose> <c:choose>
	<c:when test="${'C' eq history}">
		<div><b>History</b></div>
	</c:when>
	<c:when test="${'V' eq history}">
		<div><html:link action="/PMmodule/ClientHistory.do"
			name="actionParam" style="color:Navy;text-decoration:none;">History</html:link>
		</div>
	</c:when>
	<c:otherwise>
				&nbsp;
			</c:otherwise>
</c:choose> <c:choose>
	<c:when test="${'C' eq intake}">
		<div><b>Intake</b></div>
	</c:when>
	<c:when test="${'V' eq intake}">
		<div><html:link action="/PMmodule/QuatroIntake.do"
			name="actionParam" style="color:Navy;text-decoration:none;">Intake</html:link>
		</div>
	</c:when>
	<c:otherwise>
				&nbsp;
			</c:otherwise>
</c:choose> <c:choose>
	<c:when test="${'C' eq admission}">
		<div><b>Admission</b></div>
	</c:when>
	<c:when test="${'V' eq admission}">
		<div><html:link action="/PMmodule/QuatroAdmission.do"
			name="actionParam" style="color:Navy;text-decoration:none;">Admission</html:link>
		</div>
	</c:when>
	<c:otherwise>
				&nbsp;
			</c:otherwise>
</c:choose> <c:choose>
	<c:when test="${'C' eq refer}">
		<div><b>Referral</b></div>
	</c:when>
	<c:when test="${'V' eq refer}">
		<div><html:link action="PMmodule/QuatroRefer.do"
			name="actionParam" style="color:Navy;text-decoration:none;"> Referral</html:link>
		</div>
	</c:when>
	<c:otherwise>
				&nbsp;
			</c:otherwise>
</c:choose> <c:choose>
	<c:when test="${'C' eq discharge}">
		<div><b>Discharge</b></div>
	</c:when>
	<c:when test="${'V' eq discharge}">
		<div><html:link action="/PMmodule/QuatroDischarge.do"
			name="actionParam" style="color:Navy;text-decoration:none;">Discharge</html:link>
		</div>
	</c:when>
	<c:otherwise>
				&nbsp;
			</c:otherwise>
</c:choose> <c:choose>
	<c:when test="${'C' eq restriction}">
		<div><b>Service Restriction</b></div>
	</c:when>
	<c:when test="${'V' eq restriction}">
		<div><html:link action="/PMmodule/QuatroServiceRestriction.do"
			name="actionParam" style="color:Navy;text-decoration:none;">Service Restriction</html:link>
		</div>
	</c:when>
	<c:otherwise>
				&nbsp;
			</c:otherwise>
</c:choose> <c:choose>
	<c:when test="${'C' eq complaint}">
		<div><b>Complaint</b></div>
	</c:when>
	<c:when test="${'V' eq complaint}">
		<div><html:link action="/PMmodule/QuatroComplaint.do"
			name="actionParam" style="color:Navy;text-decoration:none;">Complaint</html:link>
		</div>
	</c:when>
	<c:otherwise>
				&nbsp;
			</c:otherwise>
</c:choose> <c:choose>
	<c:when test="${'C' eq consent}">
		<div><b>Consent</b></div>
	</c:when>
	<c:when test="${'V' eq consent}">
		<div><html:link action="/PMmodule/QuatroConsent.do"
			name="actionParam" style="color:Navy;text-decoration:none;">Consent</html:link>
		</div>
	</c:when>
	<c:otherwise>
				&nbsp;
			</c:otherwise>
</c:choose> <c:choose>
	<c:when test="${'C' eq case}">
		<div><b>Case Management</b></div>
	</c:when>
	<c:when test="${'V' eq case}">
		<div><html:link action="/CaseManagementView2.do"
			name="actionParam" style="color:Navy;text-decoration:none;">Case Management</html:link>
		</div>
	</c:when>
	<c:otherwise>
				&nbsp;
			</c:otherwise>
</c:choose> <c:choose>
	<c:when test="${'C' eq attachment}">
		<div><b>Attachment</b></div>
	</c:when>
	<c:when test="${'V' eq attachment}">
		<div><html:link action="/PMmodule/UploadFile.do"
			name="actionParam" style="color:Navy;text-decoration:none;">Attachment</html:link>
		</div>
	</c:when>
	<c:otherwise>
				&nbsp;
			</c:otherwise>
</c:choose>
</div>
</div></div>