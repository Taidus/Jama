package test;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import serviceLayer.AgreementServiceBean;
import serviceLayer.ChiefScientistServiceBean;
import serviceLayer.UARServiceBean;
import businessLayer.Agreement;
import businessLayer.ChiefScientist;
import businessLayer.UAR;

@Named
@RequestScoped
public class TestBean{
	
	@EJB
	private ChiefScientistServiceBean chiefSB;
	@EJB
	private UARServiceBean UARSB;
	@EJB
	private AgreementServiceBean agrSB;
	

	public TestBean() {
	}

	public void doJob(){
		
		
		UAR disit = UARSB.createUAR("45/cvb55", "disit");
		ChiefScientist chief = chiefSB.createChiefScientist("paolo", "nesimerda");
		String title = "convenzione schifosa per eclap";
		Agreement agr = agrSB.createAgreement(title, disit, chief);
		
	}
	
	
}
