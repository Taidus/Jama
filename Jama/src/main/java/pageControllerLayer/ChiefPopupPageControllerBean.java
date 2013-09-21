package pageControllerLayer;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import businessLayer.ChiefScientist;
import daoLayer.ChiefScientistDaoBean;

@Named("chiefPopupPCB")
@RequestScoped
public class ChiefPopupPageControllerBean{
	
	@EJB private ChiefScientistDaoBean chiefDao;
	
	private ChiefScientist chief;

	public ChiefPopupPageControllerBean() {
		this.chief = new ChiefScientist();
	}
	
	public ChiefScientist getChief() {
		return chief;
	}

	public void save(){
		chiefDao.createChiefScientist(chief);
	}
	

}
