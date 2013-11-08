function toFirstTab(tabViewer) {
//	alert(Object.keys(tabViewer));
	if (tabViewer !== undefined && tabViewer != null) {
		if (tabViewer.cfg !== undefined && tabViewer.cfg.steps !== undefined) {
//			alert(Object.keys(tabViewer.cfg) + "\n\n" + Object.keys(tabViewer.cfg.steps));
			tabViewer.loadStep(tabViewer.cfg.steps[0], true);
		} else if (tabViewer.select !== undefined) {
//			alert("Select");
			tabViewer.select(0);
		}
	}
}