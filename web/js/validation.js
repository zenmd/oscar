/* This method will return true if valid, false otherwise (and present an alert box). */ 
String.prototype.trim = function() { return this.replace(/^\s+|\s+$/, ''); };

function validateRequiredField(fieldId, fieldName, maxLength)
{
	var field=document.getElementById(fieldId);

	if (field.value==null || field.value=='')
	{
		alert('The field '+fieldName+' is required.');
		return(false);
	}
	
	if (field.value.length > maxLength)
	{
		alert('The value you entered for '+fieldName+' is too long, maximum length allowed is '+maxLength+' characters.');
		return(false);
	}
	
	return(true);
}

//for html tag on pages, added by Dawson
function validateRequiredFieldByName(fieldName, fieldNameDisplayed, maxLength)
{
	var field=document.getElementsByName(fieldName)[0];

	if (field.value==null || field.value=='')
	{
		alert('The field '+fieldName+' is required.');
		return(false);
	}
	
	if (field.value.length > maxLength)
	{
		alert('The value you entered for '+fieldNameDisplayed+' is too long, maximum length allowed is '+maxLength+' characters.');
		return(false);
	}
	
	return(true);
}

function isInteger(s){
    var i;

    if (isEmpty(s))
      if (isInteger.arguments.length == 1) return 0;
    else
      return (isInteger.arguments[1] == true);

    for(i = 0; i < s.length; i++){
       var c = s.charAt(i);

       if (!isDigit(c)) return false;
    }

    return true;
}

function isEmpty(s){
   return ((s == null) || (s.length == 0))
}

function isDigit(c){
   return ((c >= "0") && (c <= "9"))
}
function trimInputBox()
{
    var k = document.forms[0].elements.length;
    for(var i=0; i < k; i++) 
    {
       var elem = document.forms[0].elements[i];
       if (elem) {
           if (elem.type == 'textarea'|| elem.type=='text') {
              elem.value = elem.value.trim().trim();
           }  
       }
    }
}
