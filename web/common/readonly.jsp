<!--  isReadOnly ?? --->
<script type="text/javascript">
	var hashVal0= "";
	var needToConfirm = false;
    function getHash()
    {
       var hashVal = "";
       var frm = document.forms[0];
       if (!frm) return "";
       var k = frm.elements.length;
       for(var i=0; i < k; i++) 
       {
          var elem = frm.elements[i];
          if (elem) {
              if (elem.type == 'hidden' ) continue;
              if (elem.type == 'checkbox' || elem.type == 'radio') {
                 hashVal += elem.checked;
              }  
              else
              {
                 hashVal += elem.value;
              }
          }
       }
       return hashVal;
   	}
	function initHash()
	{
		initPage();
		hashVal0 = getHash();
	}
	function confirmClose() {
		if(!needToConfirm) return;
		setNoConfirm();
        var hashVal1 = getHash();
        if( hashVal1 != hashVal0) {
         		return "You have made changes. To save these changes, click Cancel, then Save."; 
     	}
	}
	function setNoConfirm()
	{
        needToConfirm = false; 
        setTimeout('resetFlag()', 750);
	}	
    function resetFlag() { needToConfirm = true; } 
</script>
	<c:out value="${isReadOnly}" />
	<logic:notPresent name="isReadOnly">
		<script type="text/javascript">
			readOnly=false;
			needToConfirm = true;
			window.onload = initHash;
		    window.onbeforeunload = confirmClose; 
		</script>
	</logic:notPresent>
	<logic:present name="isReadOnly">
		<logic:equal name="isReadOnly" value="true">
		<script type="text/javascript">
			readOnly = true;
			needToConfirm = false;
			setReadOnly();
		</script>
	</logic:equal>
	<logic:equal name="isReadOnly" value="false">
		<script type="text/javascript">
			readOnly=false;
			needToConfirm = true;
			window.onload = initHash;
		    window.onbeforeunload = confirmClose; 
		</script>
	</logic:equal>
</logic:present>