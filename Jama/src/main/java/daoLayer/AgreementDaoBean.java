package daoLayer;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import businessLayer.Agreement;
import businessLayer.ChiefScientist;
import businessLayer.Department;



@Stateless
public class AgreementDaoBean {
	@PersistenceContext(unitName="primary")
	private EntityManager em;
	
	public AgreementDaoBean() {
	}
	
	
	//pochi campi per prova
	public Agreement createAgreement(String title,Department department,ChiefScientist chief){
		
		Agreement agr = new Agreement();
		agr.setTitle(title);
		agr.setDepartment(department);
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
