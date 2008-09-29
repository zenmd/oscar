
<%@ include file="/taglibs.jsp"%>
<div id="projecttools" class="toolgroup">
<div class="label"><strong>Navigator</strong></div>
<div class="body">
<div><span><c:out value="${facility.name}" />(<c:out
	value="${facility.id}" />)</span> 
	
<c:choose>
	<c:when test="${'C' eq tabEdit}">
		<div><b>General</b></div>
	</c:when>

	<c:when test="${'V' eq tabEdit}">
		<div><html:link
			action="/PMmodule/FacilityManager.do?method=edit" name="actionParam"
			style="color:Navy;text-decoration:none;">General</html:link></div>
	</c:when>
	<c:otherwise>
				&nbsp;
			</c:otherwise>
</c:choose> 
<c:choose>
	<c:when test="${'C' eq tabBed}">
		<div><b>Room/Bed</b></div>
	</c:when>
	<c:when test="${'V' eq tabBed}">
		<div><html:link action="/PMmodule/BedManager.do?method=manageroom"
			name="actionParam" style="color:Navy;text-decoration:none;">Room/Bed</html:link>
		</div>
	</c:when>
	<c:when test="${'VN' eq tabBed}">
		<div>Room/Bed (N/A)
		</div>
	</c:when>
	<c:otherwise>
				&nbsp;
			</c:otherwise>
</c:choose></div>
</div>
</div>
