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

@Named("agreementWizPB")
@SessionScoped //FIXME
public class AgreementWizardPresentationBean implements Serializable{
	/**
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB private ChiefScientistDaoBean chiefDaoBean;
	@EJB private CompanyDaoBean companyDaoBean;
	
	private String[] wizardPageNames = {"NewFile1", "NewFile2"};
	private int currentPageIndex;
	
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
		for(int i=0; i< chiefs.size(); i++){
			result[i] = new SelectItem(chiefs.get(i).getId(), chiefs.get(i).toString());
		}
		return result;
	}
	
	public SelectItem[] getCompanyItems(){
		
		List<Company> companies = companyDaoBean.getAll();
		
		SelectItem[] result = new SelectItem[companies.size()];
		for(int i=0; i< companies.size(); i++){
			result[i] = new SelectItem(companies.get(i).getId(), companies.get(i).toString());
		}
		return result;
	}
	
	public AgreementWizardPresentationBean() {
		this.currentPageIndex = 0;
	}
	
	public String next(){
		if(currentPageIndex +1 < wizardPageNames.length){
			currentPageIndex++;
			return wizardPageNames[currentPageIndex];
		}
		else{
			return null;
		}
	}
	
	public String previous(){
		if(currentPageIndex > 0){
			currentPageIndex--;
			return wizardPageNames[currentPageIndex];
		}
		else{
			return null;
		}
	}

}
