function toFirstTab(tabViewer) {
	if (tabViewer !== undefined && tabViewer != null) {
		if (tabViewer.cfg !== undefined && tabViewer.cfg.steps !== undefined) {
			tabViewer.loadStep(tabViewer.cfg.steps[0], true);
		} else if (tabViewer.select !== undefined) {
			tabViewer.select(0);
		}
	}
}