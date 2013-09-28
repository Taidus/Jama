package presentationLayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import businessLayer.Agreement;
import daoLayer.AgreementDaoBean;

@Named("agreementListPB")
@RequestScoped
public class AgreementListPresentationBean {
	
	@EJB private AgreementDaoBean agreementDao;
	
	private List<Agreement> filteredValues;
	private List<Agreement> agreements;
	private Date filterMinDate;
	private Date filterMaxDate;

	public AgreementListPresentationBean() {
		this.agreements = agreementDao.getAll();
		this.filteredValues = agreements;
	}
	
	public List<Agreement> getAllAgreements(){
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
	
	public void filterByDate(){
		Date current;
		List<Agreement> newFilteredValues = new ArrayList<Agreement>();
		for(Agreement agr : filteredValues){
			current = agr.getApprovalDate();
			if(!current.before(filterMinDate) && !current.after(filterMaxDate)){
				newFilteredValues.add(agr);
			}
		}
		filteredValues = newFilteredValues;
	}

}
