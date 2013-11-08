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
		

		String adminSerialNumber = "1111";

		if (em.createNamedQuery("User.findBySerialNumber")
				.setParameter("number", adminSerialNumber).getResultList()
				.isEmpty()) {

			User admin = new User();
			try {
				admin.setPassword("jama");
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			admin.setSerialNumber(adminSerialNumber);
			admin.setEmail("ciccio@pasticcio.com");
			admin.setName("Admin");
			admin.setSurname("Admin");
			admin.setRole(Role.ADMIN);
			
			admin.addDepartment(d);
			em.persist(admin);
		}

		String vicSerial = "0001";

		if (em.createNamedQuery("User.findBySerialNumber")
				.setParameter("number", vicSerial).getResultList()
				.isEmpty()) {

			User vicario = new User();
			try {
				vicario.setPassword("jama");
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			vicario.setSerialNumber(vicSerial);
			vicario.setEmail("enrico.vicario@unifi.it");
			vicario.setName("Enrico");
			vicario.setSurname("Vicario");
			vicario.setRole(Role.CHIEF_SCIENTIST);
			vicario.addDepartment(d);
			em.persist(vicario);
		}
		
		String arnSerial = "0002";
		if (em.createNamedQuery("User.findBySerialNumber")
				.setParameter("number", arnSerial).getResultList()
				.isEmpty()) {

			User arnone = new User();
			try {
				arnone.setPassword("jama");
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			arnone.setSerialNumber(arnSerial);
			arnone.setEmail("andrea.arnone@unifi.it");
			arnone.setName("Andrea");
			arnone.setSurname("Arnone");
			arnone.setRole(Role.CHIEF_SCIENTIST);
			arnone.addDepartment(d);
			em.persist(arnone);
		}
		
		
		String cecchiSerial = "0000";

		
		
		if (em.createNamedQuery("User.findBySerialNumber")
				.setParameter("number", cecchiSerial).getResultList()
				.isEmpty()) {

			User operator = new User();
			try {
				operator.setPassword("jama");
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			operator.setSerialNumber(cecchiSerial);
			operator.setEmail("patrizia.cecchi@unifi.it");
			operator.setName("Patrizia");
			operator.setSurname("Cecchi");
			operator.setRole(Role.OPERATOR);
			operator.addDepartment(d);;
			em.persist(operator);
		}
		
		
		String giulioSerial = "1112";

		
		
		if (em.createNamedQuery("User.findBySerialNumber")
				.setParameter("number", giulioSerial).getResultList()
				.isEmpty()) {

			User giulio = new User();
			try {
				giulio.setPassword("jama");
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			giulio.setSerialNumber(giulioSerial);
			giulio.setEmail("giulio.galvan@gmail.com");
			giulio.setName("Giulio");
			giulio.setSurname("Galvan");
			giulio.setRole(Role.CHIEF_SCIENTIST);
			giulio.addDepartment(d);;
			em.persist(giulio);
		}
		

	}

}
