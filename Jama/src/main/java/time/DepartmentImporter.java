package time;

import java.util.List;
import java.util.TimerTask;

import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import usersManagement.LdapManager;
import businessLayer.Department;

@Stateful
public class DepartmentImporter extends TimerTask {

	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@Inject
	private LdapManager ldap;

	public DepartmentImporter() {
		super();
	}
	
	public void execute(){
		
		List<Department> depts = ldap.getAllDepts();
		for (Department d : depts) {
			try {
				em.createNamedQuery("Department.findByCode", Department.class)
						.setParameter("code", d.getCode()).getSingleResult();
			} catch (NoResultException e) {
				if(d.getCode()!= null && d.getCode()!=""){
				em.persist(d);
			}}
		}
	}

	@Override
	public void run() {
		
		execute();

	}

}
