package presentationLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import businessLayer.Agreement;
import daoLayer.AgreementDaoBean;

@Named("agreementListPB")
@ConversationScoped
public class AgreementListPresentationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private AgreementDaoBean agreementDao;
	
	@Inject private Conversation conversation;

	private List<Agreement> filteredValues;
	private List<Agreement> agreements;
	private Date filterMinDate;
	private Date filterMaxDate;

	public AgreementListPresentationBean() {
		System.out.println("Costruito");
	}
	
	@PostConstruct
	public void init() {
		this.agreements = agreementDao.getAll();
		this.filteredValues = agreements;
		conversation.begin();
	}
	
	public Conversation getConversation() {
		return conversation;
	}

	public List<Agreement> getAllAgreements() {
		return agreements;
	}

	public List<Agreement> getFilteredValues() {
		return filteredValues;
	}

	public void setFilteredValues(List<Agreement> filteredValues) {
		this.filteredValues = filteredValues;
	}

	public Date getFilterMinDate() {
		return filterMinDate;
	}

	public void setFilterMinDate(Date filterMinDate) {
		this.filterMinDate = filterMinDate;
	}

	public Date getFilterMaxDate() {
		return filterMaxDate;
	}

	public void setFilterMaxDate(Date filterMaxDate) {
		this.filterMaxDate = filterMaxDate;
	}

	public void filterByDate() {
		//FIXME
		Date current;
		List<Agreement> newFilteredValues = new ArrayList<Agreement>();
		boolean afterMax, beforeMin;
		for (Agreement agr : agreements) {
			current = agr.getApprovalDate();
			afterMax = false;
			beforeMin = false;

			if (filterMinDate != null && current.before(filterMinDate)) {
				beforeMin = true;
			}

			if (!beforeMin && filterMaxDate != null && current.after(filterMaxDate)) {
				afterMax = true;
			}

			if (!beforeMin && !afterMax) {
				newFilteredValues.add(agr);
			}
		}
		filteredValues = newFilteredValues;
	}
	
	private void close(){
		conversation.end();
		System.out.println("Conversazione chiusa");
	}
	
	public String backToHome(){
		close();
		return "home";
	}

}
