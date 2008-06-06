
<%@ include file="/taglibs.jsp"%>
<div id="projecttools" class="toolgroup">
<div class="label"><strong>Navigator</strong></div>
<div class="body">
<div><span><c:out value="${facility.name}" />(<c:out
	value="${facility.id}" />)</span> <c:choose>
	<c:when test="${'C' eq tabGeneral}">
		<div><b>General</b></div>
	</c:when>
	
	<c:when test="${'V' eq tabGeneral}">
		<div><html:link action="/PMmodule/FacilityManager.do?method=view"
			name="actionParam" style="color:Navy;text-decoration:none;">General</html:link>
		</div>
	</c:when>
	<c:otherwise>
				&nbsp;
			</c:otherwise>
</c:choose> <c:choose>
	<c:when test="${'C' eq tabProgram}">
		<div><b>Program</b></div>
	</c:when>
	<c:when test="${'V' eq tabProgram}">
		<div><html:link action="/PMmodule/FacilityManager.do?method=listPrograms"
			name="actionParam" style="color:Navy;text-decoration:none;">Program</html:link>
		</div>
	</c:when>
	<c:otherwise>
				&nbsp;
			</c:otherwise>
</c:choose> <c:choose>
	<c:when test="${'C' eq tabMessage}">
		<div><b>Message</b></div>
	</c:when>
	<c:when test="${'V' eq tabMessage}">
		<div><html:link action="/PMmodule/FacilityManager.do?method=listMessages"
			name="actionParam" style="color:Navy;text-decoration:none;">Message</html:link>
		</div>
	</c:when>
	<c:otherwise>
				&nbsp;
			</c:otherwise>
</c:choose> 

</div>
</div></div>