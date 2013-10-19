/**
 * 
 */
function setRowEvent() {
	
	/*$("div[class*=hideShow]").ready(function(){
		alert("ready");
		setHideShow();
	});
	
	$("div[class*=hideShow]").change(function(){
		alert("change");
		setHideShow();
	});
	
	$("div[class*=hideShow]").submit(function(){
		alert("submit");
		setHideShow();
	});
	*/
	



	
	
}

function setHideShow(){
	
	$("div[class*=hideShow] tr").find(".list-row-button").hide();

	$("div[class*=hideShow] tr").mouseover(function() {
			jQuery(this).find(".list-row-button").show();
		}).mouseout(function() {
			jQuery(this).find(".list-row-button").hide();
		}).click(function(e) {
			jQuery("#attchsList tr").removeClass("ui-state-highlight");
			e.stopPropagation();
		}).removeClass("ui-state-highlight");
	
	
	
}


/*

$(document).ready(function(){
	setRowEvent();
	
});

$(document).change(function(){
	alert("documentChange");
	setRowEvent();
	
});



*/






