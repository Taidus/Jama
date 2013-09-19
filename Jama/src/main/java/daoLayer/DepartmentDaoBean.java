package daoLayer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import businessLayer.Department;

@Stateless
public class DepartmentDaoBean {
	
	@PersistenceContext(unitName="primary")
	private EntityManager em;

	public DepartmentDaoBean() {
	}
	
	public Department createDepartment(String code, String name) {
		
		Department department = new Department();
		department.setCode(code);
		department.setName(name);
		em.persist(department);
	
		return department;
		}
	
	public void removeDepartment(int id){
		
		Department department = em.find(Department.class, id);
		em.remove(department);
		
		
	}
		
	}


