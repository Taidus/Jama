package presentationLayer;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import daoLayer.ChiefScientistDaoBean;
import daoLayer.CompanyDaoBean;
import businessLayer.AgreementType;
import businessLayer.ChiefScientist;
import businessLayer.Company;

@Named("utilPB")
@SessionScoped //FIXME
public class UtilPresentationBean implements Serializable{
	/**
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB private ChiefScientistDaoBean chiefDaoBean;
	@EJB private CompanyDaoBean companyDaoBean;
	
	public SelectItem[] getAgreementTypeItems(){
		AgreementType[] types = AgreementType.values();
		SelectItem[] result = new SelectItem[types.length];
		for(int i=0; i< types.length; i++){
			result[i] = new SelectItem(types[i], types[i].toString());
		}
		return result;
	}
	
	public SelectItem[] getChiefItems(){
		List<ChiefScientist> chiefs = chiefDaoBean.getAll();
		
		SelectItem[] result = new SelectItem[chiefs.size()];
		ChiefScientist current = null;
		for(int i=0; i< chiefs.size(); i++){
			current = chiefs.get(i);
			result[i] = new SelectItem(current, current.getCompleteName());
		}
		return result;
	}
	
	public SelectItem[] getCompanyItems(){
		
		List<Company> companies = companyDaoBean.getAll();
		
		SelectItem[] result = new SelectItem[companies.size()];
		Company current = null;
		for(int i=0; i< companies.size(); i++){
			current = companies.get(i);
			result[i] = new SelectItem(current, current.getName());
		}
		return result;
	}
	
	public SelectItem[] getFilterChiefItems(){
		List<ChiefScientist> chiefs = chiefDaoBean.getAll();
		
		SelectItem[] result = new SelectItem[chiefs.size()+1];
		result[0] = new SelectItem("", "Tutti");
		ChiefScientist current = null;
		for(int i=0; i< chiefs.size(); i++){
			current = chiefs.get(i);
			result[i+1] = new SelectItem(current.getId(), current.getCompleteName());
		}
		return result;
	}
	
	public SelectItem[] getFilterCompanyItems(){
		
		List<Company> companies = companyDaoBean.getAll();
		
		SelectItem[] result = new SelectItem[companies.size()+1];
		result[0] = new SelectItem("", "Tutte");
		Company current = null;
		for(int i=0; i< companies.size(); i++){
			current = companies.get(i);
			result[i+1] = new SelectItem(current.getId(), current.getName());
		}
		return result;
	}
	
	
	public UtilPresentationBean() {}

}
