package daoLayer;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import businessLayer.Department;

@Stateful
@ConversationScoped
public class DepartmentDaoBean {

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	public DepartmentDaoBean() {
	}

	public Department createDepartment(Department department) {

		em.persist(department);

		return department;
	}

	public void removeDepartment(int id) {

		Department department = em.find(Department.class, id);
		if (department != null) {
			em.remove(department);
		}

	}
	
	
	public Department getById(int id){
		
		return em.find(Department.class,id);
	}
	
	
	public List<Department> getAll(){
		
		return em.createNamedQuery("Department.findAll",Department.class).getResultList();
		
	}

}
