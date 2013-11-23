package controllerLayer;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import usersManagement.LdapManager;
import annotations.TransferObj;
import businessLayer.ChiefScientist;
import businessLayer.Contract;
import daoLayer.ChiefScientistDaoBean;

@Named("chiefDialogPCB")
@ConversationScoped
public class ChiefDialogPageControllerBean implements Serializable {
	private static final long serialVersionUID = -1986970883691414914L;

	@Inject
	@TransferObj
	private Contract contract;

	@EJB
	private ChiefScientistDaoBean chiefDao;

	@Inject
	private LdapManager ldapManager;

	private ChiefScientist chief;


	public ChiefDialogPageControllerBean() {
		this.chief = new ChiefScientist();
	}


	public ChiefScientist getChief() {
		return chief;
	}


	public void clear() {
		this.chief = new ChiefScientist();
	}


	public void save() {
		chiefDao.createChiefScientist(chief);
		contract.setChief(chief);
	}


	public void importUser() {
		chief = ldapManager.getChiefScientistBySerial(chief.getSerialNumber());
	}

}
