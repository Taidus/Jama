package serviceLayer;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import businessLayer.Agreement;
import businessLayer.ChiefScientist;
import businessLayer.UAR;



@Stateless
public class AgreementServiceBean {
	@PersistenceContext(unitName="primary")
	private EntityManager em;
	
	public AgreementServiceBean() {
	}
	
	
	//pochi campi per prova
	public Agreement createAgreement(String title,UAR uar,ChiefScientist chief){
		
		Agreement agr = new Agreement();
		agr.setTitle(title);
		agr.setUar(uar);
		agr.setChief(chief);
		em.persist(agr);
		return agr;
	}
	
	public void deleteAgreement(int id){
		
		Agreement agr = em.find(Agreement.class,id);
		if(agr!=null){
			
			em.remove(agr);
		}
	}

}
