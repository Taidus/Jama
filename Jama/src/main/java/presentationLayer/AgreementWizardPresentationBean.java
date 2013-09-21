package presentationLayer;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import businessLayer.AgreementType;
import businessLayer.ChiefScientist;
import businessLayer.Company;

@Named("agreementWizPB")
@SessionScoped //FIXME
public class AgreementWizardPresentationBean implements Serializable{
	/**
	 */
	private static final long serialVersionUID = 1L;
	
	public SelectItem[] getAgreementTypeItems(){
		AgreementType[] types = AgreementType.values();
		SelectItem[] result = new SelectItem[types.length];
		for(int i=0; i< types.length; i++){
			result[i] = new SelectItem(types[i], types[i].toString());
		}
		return result;
	}
	
	public SelectItem[] getChiefItems(){
		//FIXME initialize properly
		ChiefScientist[] chiefs = new ChiefScientist[2];
		chiefs[0] = new ChiefScientist();
		chiefs[0].setName("Beppe");
		chiefs[0].setSurname("Modica");
		chiefs[1] = new ChiefScientist();
		chiefs[1].setName("Enrico");
		chiefs[1].setSurname("Vicario");
		
		SelectItem[] result = new SelectItem[chiefs.length];
		for(int i=0; i< chiefs.length; i++){
			result[i] = new SelectItem(chiefs[i].getId(), chiefs[i].toString());
		}
		return result;
	}
	
	public SelectItem[] getCompanyItems(){
		//FIXME initialize properly
		Company[] companies = new Company[2];
		companies[0] = new Company();
		companies[0].setName("Apple");
		companies[1] = new Company();
		companies[1].setName("Microsoft");
		
		SelectItem[] result = new SelectItem[companies.length];
		for(int i=0; i< companies.length; i++){
			result[i] = new SelectItem(companies[i].getId(), companies[i].toString());
		}
		return result;
	}
	
	public AgreementWizardPresentationBean() {}

}
