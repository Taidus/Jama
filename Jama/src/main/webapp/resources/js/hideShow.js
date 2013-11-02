function setHideShow() {
	$("div[class*=hideShow] tr").find(".list-row-button").hide();

	$("div[class*=hideShow] tr").mouseover(function() {
		jQuery(this).find(".list-row-button").show();
	}).mouseout(function() {
		jQuery(this).find(".list-row-button").hide();
	}).click(function(e) {
		jQuery("div[class*=contextMenu]").hide();
		jQuery("div[class*=hideShow] tr").removeClass("ui-state-highlight");
		e.stopPropagation();
	}).removeClass("ui-state-highlight");
}

$(document).ready(function() {
	setHideShow();
});