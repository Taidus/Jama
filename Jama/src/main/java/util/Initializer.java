package util;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import usersManagement.LdapManager;
import usersManagement.RolePermission;
import usersManagement.SystemRole;
import usersManagement.User;
import businessLayer.Department;

@ApplicationScoped
@Startup
@Singleton
public class Initializer {

	@PersistenceContext(unitName = "primary", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;

	@Inject
	private LdapManager ldap;

	public Initializer() {

	}
	
	public void initAllDepts(){
		
		List<Department> depts = ldap.getAllDepts();
		for(Department d : depts){
			try {
			em.createNamedQuery("Department.findByCode", Department.class)
					.setParameter("code", d.getCode())
					.getSingleResult();
		} catch (NoResultException e) {
			em.persist(d);
		}
		}
	}
	
	public void initAdmin(String serial){
		
		User u = ldap.getUserBySerial(serial);
		
		try {
			Department d= em.createNamedQuery("Department.findByCode", Department.class)
					.setParameter("code", u.getDepartment().getCode())
					.getSingleResult();
			u.setDepartment(d);
		} catch (NoResultException e) {
			em.persist(u.getDepartment());
		}
		
		if (em.createNamedQuery("User.findBySerialNumber")
				.setParameter("number", u.getSerialNumber()).getResultList()
				.isEmpty()) {
			
			u.addRole(new SystemRole(RolePermission.ADMIN));
			em.persist(u);

		}
		
	
	}
	
	
	@PostConstruct
	public void init(){
		
		
		initAllDepts();
		initAdmin("Z000002");
		
	}
	
	
//	public void createUser(User u){
//		
//		if (em.createNamedQuery("User.findBySerialNumber")
//				.setParameter("number", u.getSerialNumber()).getResultList()
//				.isEmpty()) {
//
//			em.persist(u);
//
//		}
//
//		try {
//			Department d = em.createNamedQuery("Department.findByCode", Department.class)
//					.setParameter("code", u.getDepartment().getCode())
//					.getSingleResult();
//			u.addDepartment(d);
//		} catch (NoResultException e) {
//			em.persist(u.getDepartment());
//		}
//	}
//
//	@PostConstruct
//	public void init() {
//		System.out.println("Inizializing...");
//
//
//
//		// TODO eliminare
//		User u;
//
//		String serial = "Z000000";
//		u = ldap.getUserBySerial(serial);
//		u.setRole(Role.ADMIN);
//		createUser(u);
//		System.out.println(u);
//		System.out.println(u.getDepartment().getRateDirectory());
//
//	
//
//		serial = "Z000001";
//		u = ldap.getUserBySerial(serial);
//		u.setRole(Role.OPERATOR);
//		createUser(u);
//		System.out.println(u);
//		System.out.println(u.getDepartment().getRateDirectory());
//
//		
//
//		serial = "Z000002";
//		u = ldap.getUserBySerial(serial);
//		u.setRole(Role.PROFESSOR);
//		createUser(u);
//		System.out.println(u);
//		System.out.println(u.getDepartment().getRateDirectory());
//
//
//	}
//
//	@Deprecated
//	public void oldInit() throws NoSuchAlgorithmException {
//
//		String depCode = "DSI/DINFO";
//		Department d;
//
//		List<Department> depList = em
//				.createNamedQuery("Department.findByCode", Department.class)
//				.setParameter("code", depCode).getResultList();
//
//		if (depList.isEmpty()) {
//
//			d = new Department();
//			d.setCode(depCode);
//			d.setName("ex Dipartimento di Sistemi e Informatica");
//			d.setRateDirectory("dsi");
//			em.persist(d);
//
//		} else {
//			d = depList.get(0);
//		}
//
//		String adminSerialNumber = "1111";
//
//		if (em.createNamedQuery("User.findBySerialNumber")
//				.setParameter("number", adminSerialNumber).getResultList()
//				.isEmpty()) {
//
//			User admin = new User();
//			admin.setSerialNumber(adminSerialNumber);
//			admin.setEmail("ciccio@pasticcio.com");
//			admin.setName("Admin");
//			admin.setSurname("Admin");
//			admin.setRole(Role.ADMIN);
//
//			admin.addDepartment(d);
//			em.persist(admin);
//		}
//
//		String vicSerial = "0001";
//
//		if (em.createNamedQuery("User.findBySerialNumber")
//				.setParameter("number", vicSerial).getResultList().isEmpty()) {
//
//			User vicario = new User();
//			vicario.setSerialNumber(vicSerial);
//			vicario.setEmail("enrico.vicario@unifi.it");
//			vicario.setName("Enrico");
//			vicario.setSurname("Vicario");
//			vicario.setRole(Role.PROFESSOR);
//			vicario.addDepartment(d);
//			em.persist(vicario);
//		}
//
//		String arnSerial = "0002";
//		if (em.createNamedQuery("User.findBySerialNumber")
//				.setParameter("number", arnSerial).getResultList().isEmpty()) {
//
//			User arnone = new User();
//			arnone.setSerialNumber(arnSerial);
//			arnone.setEmail("andrea.arnone@unifi.it");
//			arnone.setName("Andrea");
//			arnone.setSurname("Arnone");
//			arnone.setRole(Role.PROFESSOR);
//			arnone.addDepartment(d);
//			em.persist(arnone);
//		}
//
//		String cecchiSerial = "0000";
//
//		if (em.createNamedQuery("User.findBySerialNumber")
//				.setParameter("number", cecchiSerial).getResultList().isEmpty()) {
//
//			User operator = new User();
//			operator.setSerialNumber(cecchiSerial);
//			operator.setEmail("patrizia.cecchi@unifi.it");
//			operator.setName("Patrizia");
//			operator.setSurname("Cecchi");
//			operator.setRole(Role.OPERATOR);
//			operator.addDepartment(d);
//			;
//			em.persist(operator);
//		}
//
//	}

}
