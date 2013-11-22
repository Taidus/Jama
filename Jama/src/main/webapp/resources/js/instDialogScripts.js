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
	var height = $(window).height()*9/10;
	var width = $(window).width()*9/10;
	var jId = $("div[id*=" + dialogId + "]");
	jId.height(height).width(width).css("overflow", "auto").offset({ 'top': height/20, 'left': width/20});
}