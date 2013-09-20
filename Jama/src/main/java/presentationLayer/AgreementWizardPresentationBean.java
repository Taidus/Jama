package presentationLayer;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import businessLayer.AgreementType;
import businessLayer.ChiefScientist;
import businessLayer.Company;

@Named("agreementWizPB")
@SessionScoped
public class AgreementWizardPresentationBean implements Serializable{
	/**
	 */
	private static final long serialVersionUID = 1L;
	
	public SelectItem[] getAgreementTypeItems(){
		AgreementType[] types = AgreementType.values();
		return arrayToSelectItems(types);
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
		
		return arrayToSelectItems(chiefs);
	}
	
	public SelectItem[] getCompanyItems(){
		//FIXME initialize properly
		Company[] companies = new Company[2];
		companies[0] = new Company();
		companies[0].setName("Apple");
		companies[1] = new Company();
		companies[1].setName("Microsoft");
		
		return arrayToSelectItems(companies);
	}
	
	private <T> SelectItem[] arrayToSelectItems(T[] array){
		SelectItem[] result = new SelectItem[array.length];
		for(int i=0; i< array.length; i++){
			result[i] = new SelectItem(array[i], array[i].toString());
		}
		return result;
	}
	
	public AgreementWizardPresentationBean() {}

}
