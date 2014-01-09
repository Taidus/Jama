package daoLayer;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import businessLayer.Department;

@Stateful
@ConversationScoped
public class DepartmentDaoBean {

	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
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

	public Department getById(int id) {

		return em.find(Department.class, id);
	}

	public Department getByCode(String code) {
		System.out.println("GET BY CODE: " + code + "   ////");
		try {
			return em
					.createNamedQuery("Department.findByCode", Department.class)
					.setParameter("code", code).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Department> getAll() {

		return em.createNamedQuery("Department.findAll", Department.class)
				.getResultList();

	}

}
