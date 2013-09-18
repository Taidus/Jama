package serviceLayer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import businessLayer.UAR;

@Stateless
public class UARServiceBean {
	
	@PersistenceContext(unitName="primary")
	private EntityManager em;

	public UARServiceBean() {
	}
	
	public UAR createUAR(String code, String name) {
		
		UAR uar = new UAR();
		uar.setCode(code);
		uar.setName(name);
		em.persist(uar);
	
		return uar;
		}
	
	public void removeUAR(int id){
		
		UAR uar = em.find(UAR.class, id);
		em.remove(uar);
		
		
	}
		
	}


