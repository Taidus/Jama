package presentationLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

import pageControllerLayer.AgreementManagerBean;

@Named("agreementEditPB")
@ConversationScoped
public class AgreementEditPresentationBean implements Serializable {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private int currentTabIndex = defaultTab;
	private static final int defaultTab = 0;

	@Inject
	AgreementManagerBean manager;

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
	        System.out.println("================"+currentTabIndex);

	}

	public String cancel() {
		currentTabIndex = defaultTab;
		return manager.cancel();
	}

	public String save() {
		currentTabIndex = defaultTab;
		return manager.save();
	}

}
