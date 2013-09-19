package pageControllerLayer;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import businessLayer.Agreement;
import daoLayer.AgreementDaoBean;

@Named("agreementWizardPCB")
@SessionScoped
public class agreementWizardPageControllerBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@EJB private AgreementDaoBean agreementDao;
	private Agreement agreement;

	public agreementWizardPageControllerBean() {
		this.agreement = new Agreement();
	}
	
	public Agreement getAgreement() {
		return agreement;
	}
	
	public void save(){
		agreementDao.create(agreement);
	}

}
