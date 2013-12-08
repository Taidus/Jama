package util;

import javax.ejb.Remove;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import usersManagement.LdapManager;
import usersManagement.RolePermission;
import usersManagement.User;
import businessLayer.Department;

@Stateless
public class UserInit {

	
	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@Inject
	private LdapManager ldap;

	private void createUser(User u){
		
		if (em.createNamedQuery("User.findBySerialNumber")
				.setParameter("number", u.getSerialNumber()).getResultList()
				.isEmpty()) {

			em.persist(u);

		}

		try {
			Department d = em.createNamedQuery("Department.findByCode", Department.class)
					.setParameter("code", u.getDepartment().getCode())
					.getSingleResult();
			u.setDepartment(d);
		} catch (NoResultException e) {
			em.persist(u.getDepartment());
		}
	}

	
	public void execute() {
		
		
		System.out.println("Inizializing...");

		// TODO eliminare
		User u;

		String serial = "Z000000";
		u = ldap.getUserBySerial(serial);
		u.addRolePermission(RolePermission.ADMIN);
		createUser(u);
		System.out.println(u);
		System.out.println(u.getDepartment().getRateDirectory());

	

		serial = "Z000001";
		u = ldap.getUserBySerial(serial);
		u.addRolePermission(RolePermission.OPERATOR);
		createUser(u);
		System.out.println(u);
		System.out.println(u.getDepartment().getRateDirectory());

		

		serial = "Z000002";
		u = ldap.getUserBySerial(serial);
		u.addRolePermission(RolePermission.PROFESSOR);
		createUser(u);
		System.out.println(u);
		System.out.println(u.getDepartment().getRateDirectory());
		

	}
	
	@Remove
	public void finished(){
		
	}
}
