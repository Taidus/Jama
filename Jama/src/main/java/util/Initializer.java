package util;

import java.security.GeneralSecurityException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

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
	public void init(){
		User admin = new User();
		byte[] adminPassword;
		try {
			adminPassword = Encryptor.encrypt("pastrullo");
			admin.setPassword(adminPassword);
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		admin.setSerialNumber("d612f");
		admin.setEmail("ciccio.capopasticcio@pasticcino.com");
		admin.setName("CiccioCapo");
		admin.setSurname("Pasticcio");
		admin.setRole(Role.ADMIN);		
		em.persist(admin);
		
		
		User teacher = new User();
		byte[] teacherPassword;
		try {
			teacherPassword = Encryptor.encrypt("pastrullo");
			teacher.setPassword(teacherPassword);
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		teacher.setSerialNumber("d612m");
		teacher.setEmail("ciccio.pasticcio@pasticcino.com");
		teacher.setName("Ciccio");
		teacher.setSurname("Pasticcio");
		teacher.setRole(Role.CHIEF_SCIENTIST);		
		em.persist(teacher);
		
		
		
		
	}

}
