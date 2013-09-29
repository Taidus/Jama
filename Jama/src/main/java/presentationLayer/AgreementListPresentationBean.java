package presentationLayer;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import businessLayer.Agreement;
import daoLayer.ChiefScientistDaoBean;

@Named("agreementListPB")
@ConversationScoped
public class AgreementListPresentationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB private ChiefScientistDaoBean chiefDao;
	@Inject private LazyAgreementDataModel lazyModel;
	
	@Inject private Conversation conversation;
	
	private Agreement selectedValue;

	public AgreementListPresentationBean() {}
	
	@PostConstruct
	public void init() {		
		conversation.begin();
	}
	
	public Conversation getConversation() {
		return conversation;
	}
	
	public LazyAgreementDataModel getLazyModel() {  
        return lazyModel;
    }

	public Agreement getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(Agreement selectedValue) {
		this.selectedValue = selectedValue;
	}


//	public void filterByDate() {
//		//FIXME
//		Date current;
//		List<Agreement> newFilteredValues = new ArrayList<Agreement>();
//		boolean afterMax, beforeMin;
//		for (Agreement agr : agreements) {
//			current = agr.getApprovalDate();
//			afterMax = false;
//			beforeMin = false;
//
//			if (filterMinDate != null && current.before(filterMinDate)) {
//				beforeMin = true;
//			}
//
//			if (!beforeMin && filterMaxDate != null && current.after(filterMaxDate)) {
//				afterMax = true;
//			}
//
//			if (!beforeMin && !afterMax) {
//				newFilteredValues.add(agr);
//			}
//		}
//		filteredValues = newFilteredValues;
//	}
	
	private void close(){
		conversation.end();
	}
	
	public String backToHome(){
		close();
		return "home";
	}

}
