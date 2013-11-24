package presentationLayer;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityExistsException;

import usersManagement.LdapManager;
import util.Messages;
import util.Percent;
import annotations.TransferObj;
import businessLayer.ChiefScientist;
import businessLayer.Contract;
import daoLayer.ChiefScientistDaoBean;

@Named("chiefDialogPB")
@ConversationScoped
public class ChiefDialogPresentationBean implements Serializable {
	private static final long serialVersionUID = -1986970883691414914L;

	@Inject
	@TransferObj
	private Contract contract;

	@EJB
	private ChiefScientistDaoBean chiefDao;

	@Inject
	private LdapManager ldapManager;

	private ChiefScientist chief;
	private ChiefScientist tempLdapChief;


	public ChiefDialogPresentationBean() {
		this.chief = new ChiefScientist();
	}


	public ChiefScientist getChief() {
		return chief;
	}


	public void clear() {
		this.chief = new ChiefScientist();
	}


	public void validateSerialNumber(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			String serial = value.toString();
			
			if (null != chiefDao.getBySerial(serial)) {
				System.out.println("ChiefDialogPB: matricola duplicata");
				throw new ValidatorException(Messages.getErrorMessage("err_duplicateSerial"));
			}

			tempLdapChief = ldapManager.getChiefScientistBySerial(serial);
			if (null == tempLdapChief) {
				System.out.println("ChiefDialogPB: matricola non trovata in ldap");
				throw new ValidatorException(Messages.getErrorMessage("err_badImport"));
			}
		}
	}


	public void save() {
		try {
			// il controllo sulla matricola duplicata viene eseguito nel
			// validator
			chiefDao.createChiefScientist(chief);
			contract.setChief(chief);
		} catch (Exception e) {
			System.out.println("ChiefDialogPB: errore db");
			FacesContext.getCurrentInstance().addMessage(null, Messages.getErrorMessage("err_db"));
			throw e;
		}
	}


	public void importUser() {
		if (null == tempLdapChief) {
			FacesContext.getCurrentInstance().addMessage(null, Messages.getErrorMessage("err_badImport"));
		}
		else {
			chief = tempLdapChief;
		}
	}

}
