package util;

import java.security.GeneralSecurityException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;

import usersManagement.Role;
import usersManagement.User;

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

		String adminSerialNumber = "d612f";

		if (em.createNamedQuery("User.findBySerialNumber")
				.setParameter("number", adminSerialNumber).getResultList()
				.isEmpty()) {

			User admin = new User();
			byte[] adminPassword;
			try {
				adminPassword = Encryptor.encrypt("pastrullo");
				admin.setPassword(adminPassword);
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			admin.setSerialNumber(adminSerialNumber);
			admin.setEmail("ciccio.capopasticcio@pasticcino.com");
			admin.setName("CiccioCapo");
			admin.setSurname("Pasticcio");
			admin.setRole(Role.OPERATOR);
			em.persist(admin);
		}

		String teacherSerialNumber = "d612m";

		if (em.createNamedQuery("User.findBySerialNumber")
				.setParameter("number", teacherSerialNumber).getResultList()
				.isEmpty()) {

			User teacher = new User();
			byte[] teacherPassword;
			try {
				teacherPassword = Encryptor.encrypt("pastrullo");
				teacher.setPassword(teacherPassword);
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			teacher.setSerialNumber(teacherSerialNumber);
			teacher.setEmail("ciccio.pasticcio@pasticcino.com");
			teacher.setName("Ciccio");
			teacher.setSurname("Pasticcio");
			teacher.setRole(Role.CHIEF_SCIENTIST);
			em.persist(teacher);
		}
		
		String operatorSerialNumber = "d612o";

		
		
		if (em.createNamedQuery("User.findBySerialNumber")
				.setParameter("number", operatorSerialNumber).getResultList()
				.isEmpty()) {

			User operator = new User();
			byte[] operatorPassword;
			try {
				operatorPassword = Encryptor.encrypt("pastrullo");
				operator.setPassword(operatorPassword);
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
