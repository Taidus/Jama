package presentationLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import businessLayer.Company;
import util.Messages;
import controllerLayer.CompanyManagerBean;
import daoLayer.CompanyDaoBean;

@Named("companyDialogPB")
@ConversationScoped
public class CompanyDialogPresentationBean implements Serializable {
	private static final long serialVersionUID = -4695232863581347323L;

	@Inject
	private CompanyManagerBean manager;

	@Inject
	private CompanyDaoBean companyDao;


	public CompanyDialogPresentationBean() {}


	public void validateSocialNumber(FacesContext context, UIComponent component, Object value) {
		System.out.println("°°° Company dialog PB: validazione CF.");
		String socialNumber = (String) value;

		if (socialNumber != null && !socialNumber.isEmpty()) {
			System.out.println("CF non vuoto: " + socialNumber);
			Company company = manager.getCompany();

			if (!socialNumber.equalsIgnoreCase(company.getSocialNumber()) && companyDao.getBySocialNumber(socialNumber) != null) {
				throw new ValidatorException(Messages.getErrorMessage("err_duplicateSocialNumber"));
			}
		}
	}


	public void validateRequiredParams(FacesContext context, UIComponent component, Object value) {
		System.out.println("°°° Company dialog PB: validazione parametri.");
		Company c = manager.getCompany();
		System.out.println("\tCF: " + c.getSocialNumber() + "\n\tPartita IVA: " + c.getVatNumber());
		if ((null == c.getSocialNumber() || c.getSocialNumber().isEmpty()) && (null == c.getVatNumber() || c.getVatNumber().isEmpty())) {
			throw new ValidatorException(Messages.getErrorMessage("err_socialOrVatRequired"));
		}
		System.out.println("°°° Validazione superata");
	}
}
