<%@include file="../taglibs.jsp"%>
<div id="projecttools" class="toolgroup">
<div class="label">
    <strong>System Messages</strong>
</div>
<div class="body">
			<c:import url="/SystemMessage.do?method=view" />
</div>
<div class="label">
    <strong>Facility Messages</strong>
</div>
<div class="body">
			<c:import url="/FacilityMessage.do?method=view" />
</div>
</div>