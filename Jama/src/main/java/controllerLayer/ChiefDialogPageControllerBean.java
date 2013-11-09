package controllerLayer;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import annotations.TransferObj;
import businessLayer.ChiefScientist;
import businessLayer.Contract;
import daoLayer.ChiefScientistDaoBean;

@Named("chiefDialogPCB")
@RequestScoped
public class ChiefDialogPageControllerBean{
	
	@Inject 
	@TransferObj 
	private Contract contract;
	
	@EJB private ChiefScientistDaoBean chiefDao;
	
	private ChiefScientist chief;

	public ChiefDialogPageControllerBean() {
		this.chief = new ChiefScientist();
	}
	
	public ChiefScientist getChief() {
		return chief;
	}
	
	public void clear(){
		this.chief = new ChiefScientist();
	}

	public void save(){
		chiefDao.createChiefScientist(chief);
		contract.setChief(chief);
	}

	

}
