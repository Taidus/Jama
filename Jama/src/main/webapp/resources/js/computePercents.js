/**
 * 
 */

function computePercents(element,targetId){
	
	var euroField = document.getElementById("agreementWiz:j_idt99:wholeAmount");
	var target = document.getElementById(targetId);
	var percent = element.value;
	
	
	target.value=euroField.value*percent/100;
	
}