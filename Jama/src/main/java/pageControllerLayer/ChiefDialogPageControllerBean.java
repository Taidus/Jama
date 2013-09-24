package pageControllerLayer;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import businessLayer.ChiefScientist;
import daoLayer.ChiefScientistDaoBean;

@Named("chiefDialogPCB")
@RequestScoped
public class ChiefDialogPageControllerBean{
	
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
	}
	

}
