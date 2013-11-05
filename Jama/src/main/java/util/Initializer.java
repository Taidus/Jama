package util;

import java.security.GeneralSecurityException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import usersManagement.Role;
import usersManagement.User;
import businessLayer.Department;

@ApplicationScoped
@Singleton
@Startup
public class Initializer {

	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	public Initializer() {
	}

	@PostConstruct
	public void init() {
		
		String depCode = "DSI/DINFO";
		Department d;
		
		List<Department> depList = em.createNamedQuery("Department.findByCode", Department.class).setParameter("code", depCode).getResultList();
		
		if(depList.isEmpty()){
		
		d = new Department();
		d.setCode(depCode);
		d.setName("ex Dipartimento di Sistemi e Informatica");
		d.setRateDirectory("dsi");
		em.persist(d);
		
		}
		else{
			d = depList.get(0);
		}
		

		String adminSerialNumber = "d612f";

		if (em.createNamedQuery("User.findBySerialNumber")
				.setParameter("number", adminSerialNumber).getResultList()
				.isEmpty()) {

			User admin = new User();
			try {
				admin.setPassword("pastrullo");
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			admin.setSerialNumber(adminSerialNumber);
			admin.setEmail("ciccio.capopasticcio@pasticcino.com");
			admin.setName("CiccioCapo");
			admin.setSurname("Pasticcio");
			admin.setRole(Role.OPERATOR);
			
			admin.addDepartment(d);
			em.persist(admin);
		}

		String teacherSerialNumber = "d612m";

		if (em.createNamedQuery("User.findBySerialNumber")
				.setParameter("number", teacherSerialNumber).getResultList()
				.isEmpty()) {

			User teacher = new User();
			try {
				teacher.setPassword("pastrullo");
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			teacher.setSerialNumber(teacherSerialNumber);
			teacher.setEmail("ciccio.pasticcio@pasticcino.com");
			teacher.setName("Ciccio");
			teacher.setSurname("Pasticcio");
			teacher.setRole(Role.CHIEF_SCIENTIST);
			teacher.addDepartment(d);
			em.persist(teacher);
		}
		
		String operatorSerialNumber = "d612o";

		
		
		if (em.createNamedQuery("User.findBySerialNumber")
				.setParameter("number", operatorSerialNumber).getResultList()
				.isEmpty()) {

			User operator = new User();
			try {
				operator.setPassword("pastrullo");
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			operator.setSerialNumber(operatorSerialNumber);
			operator.setEmail("ciccio.pasticcere@pasticcino.com");
			operator.setName("Ciccio");
			operator.setSurname("Pasticcere");
			operator.setRole(Role.ADMIN);
			em.persist(operator);
		}

	}

}
