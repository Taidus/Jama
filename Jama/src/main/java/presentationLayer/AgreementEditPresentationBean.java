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

	public AgreementEditPresentationBean() {
	}

	public int getCurrentTabIndex() {
		return currentTabIndex;
	}

	public void setCurrentTabIndex(int currentTabIndex) {
		this.currentTabIndex = currentTabIndex;

	}

	public void handleFlow(TabChangeEvent event) {

		TabView tv = (TabView) event.getComponent();
		currentTabIndex = tv.getActiveIndex();

	}

	public String cancel() {
		currentTabIndex = defaultTab;
		manager.cancel();
		return getListLink();

	}

	public String save() {
		currentTabIndex = defaultTab;
		manager.save();
		return getListLink();

	}
	
	private String getListLink(){
		return "agreementList?faces-redirect=true&" + manager.getFiltersParamList();
	}

}
