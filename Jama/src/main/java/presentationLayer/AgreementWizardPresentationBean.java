package presentationLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FlowEvent;

import businessLayer.ContractHelper;
import util.Messages;
import annotations.Current;
import controllerLayer.ContractManagerBean;
import daoLayer.ChiefScientistDaoBean;

@Named("agreementWizardPB")
@ConversationScoped
public class AgreementWizardPresentationBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String defaultTab = "tabDati";
	private String currentTabId;
	@Inject
	private ContractManagerBean manager;
	@Inject
	@Current
	private ContractHelper helper;
	@Inject
	private ChiefScientistDaoBean chiefDao;


	public AgreementWizardPresentationBean() {
		currentTabId = defaultTab;
	}


	public String getCurrentTabId() {
		return currentTabId;
	}


	public boolean isLastStep() {
		if (currentTabId.equals("tabEnd")) {
			return true;
		} else {
			return false;
		}
	}


	public String handleFlow(FlowEvent event) {
		currentTabId = event.getNewStep();
		return event.getNewStep();
	}


	public String cancel() {
		currentTabId = defaultTab;
		manager.cancel();
		return "home";
	}


	public String save() {
		currentTabId = defaultTab;
		manager.save();
		return "home";
	}


	public boolean renderIvaComponents() {
		return helper.renderIvaComponents();
	}
	
	public boolean renderType(){
		return helper.renderType();
	}
	
	public boolean renderShareTable(){
		return helper.renderShareTable();
	}
	
	public boolean renderPersonnelQuotes(){
		return helper.renderPersonnelQuotes();
	}
	
	public String getContractTypeName(){
		return helper.getName().toLowerCase();
	}
	
	
	public void validateSerial(FacesContext context, UIComponent component, Object value) {
		String serial = (String) value;
		
		if(chiefDao.getBySerial(serial)!= null){

			throw new ValidatorException(Messages.getErrorMessage("err_duplicateSerial"));

		}
		
		
	}
}
