package presentationLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

import controllerLayer.ContractManagerBean;

@Named("agreementEditPB")
@ConversationScoped
public class AgreementEditPresentationBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int currentTabIndex = defaultTab;
	private static final int defaultTab = 0;

	@Inject
	private ContractManagerBean manager;
	
	private boolean professorModeOn;

	public AgreementEditPresentationBean() {
		setDefault();
	}
	
	private void setDefault(){
		this.professorModeOn = false;
		this.currentTabIndex = defaultTab;

	}

	public int getCurrentTabIndex() {
		return currentTabIndex;
	}

	public void setCurrentTabIndex(int currentTabIndex) {
		this.currentTabIndex = currentTabIndex;
	}
	
	public boolean isProfessorModeOn() {
		return professorModeOn;
	}
	
	public void setProfessorModeOn(boolean professorModeOn) {
		this.professorModeOn = professorModeOn;
	}

	public void handleFlow(TabChangeEvent event) {

		TabView tv = (TabView) event.getComponent();
		currentTabIndex = tv.getActiveIndex();

	}

	public String cancel() {
		setDefault();
		manager.cancel();
		return getListLink();

	}

	public String save() {
		setDefault();
		manager.save();
		return getListLink();

	}
	
	private String getListLink(){
		return manager.getProvenancePage()+"&" + manager.getFiltersParamList();
	}

}
