function toFirstTab(tabViewer) {
	if (tabViewer !== undefined && tabViewer != null) {
		if (tabViewer.cfg !== undefined && tabViewer.cfg.steps !== undefined) {
			tabViewer.loadStep(tabViewer.cfg.steps[0], true);
		} else if (tabViewer.select !== undefined) {
			tabViewer.select(0);
		}
	}
}

function setSize(dialogId){
	var wScaling = 9/10;
	var hScaling = 9/10;
	var height = $(window).height()*hScaling;
	var width = $(window).width()*wScaling;
	var jId = $("div[id*=" + dialogId + "]");
	jId.height(height).width(width).css("overflow", "auto").offset({ 'top': height*(1 - hScaling)/2, 'left': width*(1- wScaling)/2 });
}