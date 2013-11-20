package util;

import java.util.List;

import usersManagement.User;
import businessLayer.Department;

public interface LdapQueryInterface {
	

	public List<Department> getAllDepts();

	public  User getUserBySerial(String serialNumber)
			throws IllegalStateException;
	public List<User> getUsersByDept(String deptCode);
	
	public List<User> getAllUsers();

}



