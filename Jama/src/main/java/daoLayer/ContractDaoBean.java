package daoLayer;

import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import businessLayer.Agreement;
import businessLayer.Contract;

@Stateful
@ConversationScoped
public class ContractDaoBean {
	@PersistenceContext(unitName = "primary",type=PersistenceContextType.EXTENDED)
	private EntityManager em;

	public ContractDaoBean() {
	}

	public Contract create(Contract contract) {
		
//		for(Installment i : agreement.getInstallments()){
//			i.setAgreement(agreement);
//		}
		em.persist(contract);
		return contract;
	}

	public void delete(int id) {

		Contract c = em.find(Contract.class, id);
		if (c != null) {

			em.remove(c);
		}
	}

	public Contract getById(int id) {

		return em.find(Contract.class, id);

	}
	
	public List<Agreement> getAll(){
		
		return em.createNamedQuery("Agreement.findAll",Agreement.class).getResultList();
		
	}
	
	public TypedQuery<Agreement> getFindAllQuery(){
		return em.createNamedQuery("Agreement.findAll",Agreement.class);
	}
	
	@Remove
	public void close(){
		
	}

}
