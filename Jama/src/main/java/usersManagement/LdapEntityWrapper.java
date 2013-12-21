package usersManagement;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import daoLayer.DepartmentDaoBean;
import businessLayer.ChiefScientist;
import businessLayer.Department;

@ConversationScoped
@Stateful
public class LdapEntityWrapper {
	
	
	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@Inject 
	private LdapManager ldap;
	
	@Inject 
	private DepartmentDaoBean deptDao;

	public List<Department> getAllDepts() {
		return ldap.getAllDepts();
	}

	public Department getDeptDeptByCode(String serial) {
		return ldap.getDeptDeptByCode(serial);
	}

	public User getUserBySerial(String serialNumber)
			throws IllegalStateException {
		User u = ldap.getUserBySerial(serialNumber);
		Department d = deptDao.getByCode(u.getDepartment().getCode());
		if(d != null){
			u.setDepartment(d);
		}else{
			em.persist(u.getDepartment());
		}
		return u;
	}

	public List<User> getUsersByDept(String deptCode) {
		return ldap.getUsersByDept(deptCode);
	}

	public List<User> getAllUsers() {
		return ldap.getAllUsers();
	}

	public List<ChiefScientist> getAllChiefScientists() {
		return ldap.getAllChiefScientists();
	}

	public List<ChiefScientist> getChiefScientistsByDepth(String deptCode) {
		return ldap.getChiefScientistsByDepth(deptCode);
	}

	public ChiefScientist getChiefScientistBySerial(String serialNumber) {
		return ldap.getChiefScientistBySerial(serialNumber);
	}

	public boolean authenticate(String password, String serialNumber) {
		return ldap.authenticate(password, serialNumber);
	}
	
	
	
}
