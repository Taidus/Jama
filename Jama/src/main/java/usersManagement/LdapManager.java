package usersManagement;

import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import util.Config;
import util.Encryptor;
import businessLayer.ChiefScientist;
import businessLayer.Department;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPJSSESecureSocketFactory;
import com.novell.ldap.LDAPSearchResults;
import com.novell.ldap.LDAPSocketFactory;
import com.novell.ldap.util.Base64;

@RequestScoped
public class LdapManager implements LdapQueryInterface {

	private LDAPConnection lc;
	private LDAPSocketFactory ssf;

	@SuppressWarnings("restriction")
	private void connect() throws LDAPException, UnsupportedEncodingException {

		// System.out.println("====LDAP CALLED====");

		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		ssf = new LDAPJSSESecureSocketFactory();
		LDAPConnection.setSocketFactory(ssf);
		lc = new LDAPConnection();
		lc.connect(Config.ldapHost, Config.ldapPort);
		lc.bind(Config.ldapVersion, Config.loginDN,
				Config.password.getBytes("UTF8"));

	}

	private void close() throws LDAPException {
		if (lc != null && lc.isConnected()) {
			lc.disconnect();
		}
	}

	private String getValue(Enumeration<String> allValues) {

		String result = null;

		if (allValues != null) {

			boolean found = false;

			while (allValues.hasMoreElements() && found == false) {

				String value = (String) allValues.nextElement();
				if (value != null && !value.trim().equals("")) {
					found = true;
					if (Base64.isLDIFSafe(value)) {
						result = value;
					}

					else {
						result = Base64.encode(value.getBytes());
					}
				}
			}
		}
		return result;

	}

	@SuppressWarnings("unchecked")
	private User buildUser(LDAPAttributeSet attributeSet) {
		User result = new User();
		Department d = new Department();

		Iterator<LDAPAttribute> allAttributes = (Iterator<LDAPAttribute>) attributeSet
				.iterator();

		while (allAttributes.hasNext()) {

			LDAPAttribute attribute = allAttributes.next();
			String attributeName = attribute.getName().trim();

			Enumeration<String> allValues = (Enumeration<String>) attribute
					.getStringValues();
			String value = getValue(allValues).trim();

			// TODO mettere i nomi dei parametri in un file di configurazione
			if (attributeName.equalsIgnoreCase("givenName")) {
				result.setName(value);
			} else if (attributeName.equalsIgnoreCase("sn")) {
				result.setSurname(value);
			} else if (attributeName.equalsIgnoreCase("mail")) {
				result.setEmail(value);
			} else if (attributeName.equalsIgnoreCase("uid")) {
				result.setSerialNumber(value);
			} else if (attributeName.equalsIgnoreCase("userPassword")) {
				// TODO controllare che funzioni
				result.setEncryptor(Encryptor.getFromPasswordWithPrefix(value));
				result.setPassword(value);
			} else if (attributeName.equalsIgnoreCase("departmentNumber")) {
				d.setCode(value);
			} else if (attributeName.equalsIgnoreCase("ou")) {
				d.setName(value);
			}
		}

		result.setRole(Role.PROFESSOR);
		result.addDepartment(d);

		return result;

	}

	@SuppressWarnings("unchecked")
	private ChiefScientist buildChiefScientist(LDAPAttributeSet attributeSet) {
		ChiefScientist result = new ChiefScientist();
		Department d = new Department();

		Iterator<LDAPAttribute> allAttributes = (Iterator<LDAPAttribute>) attributeSet
				.iterator();

		while (allAttributes.hasNext()) {

			LDAPAttribute attribute = allAttributes.next();
			String attributeName = attribute.getName().trim();

			Enumeration<String> allValues = (Enumeration<String>) attribute
					.getStringValues();
			String value = getValue(allValues).trim();

			// TODO mettere i nomi dei parametri in un file di configurazione
			if (attributeName.equalsIgnoreCase("givenName")) {
				result.setName(value);
			} else if (attributeName.equalsIgnoreCase("sn")) {
				result.setSurname(value);
			} else if (attributeName.equalsIgnoreCase("uid")) {
				result.setSerialNumber(value);
			} else if (attributeName.equalsIgnoreCase("departmentNumber")) {
				d.setCode(value);
			} else if (attributeName.equalsIgnoreCase("ou")) {
				d.setName(value);
			}
		}

		// result.addDepartment(d);

		return result;

	}

	@SuppressWarnings("unchecked")
	private Department buildDept(LDAPAttributeSet attributeSet) {
		Department d = new Department();

		Iterator<LDAPAttribute> allAttributes = (Iterator<LDAPAttribute>) attributeSet
				.iterator();

		while (allAttributes.hasNext()) {

			LDAPAttribute attribute = allAttributes.next();
			String attributeName = attribute.getName().trim();

			Enumeration<String> allValues = (Enumeration<String>) attribute
					.getStringValues();
			String value = getValue(allValues).trim();

			// TODO mettere i nomi dei parametri in un file di configurazione
			if (attributeName.equalsIgnoreCase("uniqueIdentifier")) {
				d.setCode(value);
			} else if (attributeName.equalsIgnoreCase("description")) {
				d.setName(value);
			}
			// TODO set actual directory
			d.setRateDirectory(Config.depRatesPath + "dsi");

		}

		return d;
	}

	public List<Department> getAllDepts() {

		List<Department> result = new ArrayList<>();
		String filter = "businessCategory" + "=" + Config.deptsBusinessCategory;

		try {
			connect();
			LDAPSearchResults searchResults = lc.search(Config.searchBase,
					Config.searchScope, filter, null, false);

			while (searchResults.hasMore()) {

				LDAPEntry nextEntry = null;
				nextEntry = searchResults.next();
				LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
				Department d = buildDept(attributeSet);
				if (d != null) {
					result.add(d);
				}

			}

		} catch (UnsupportedEncodingException | LDAPException e) {
			e.printStackTrace();
		}

		finally {

			try {
				close();
			} catch (LDAPException e) {
				e.printStackTrace();
			}
		}

		return result;

	}

	private List<User> getUsers(String filter) {
		List<User> result = new ArrayList<>();

		try {
			connect();
			LDAPSearchResults searchResults = lc.search(Config.searchBase,
					Config.searchScope, filter, null, false);

			while (searchResults.hasMore()) {

				LDAPEntry nextEntry = null;
				nextEntry = searchResults.next();
				LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
				User u = buildUser(attributeSet);
				if (u != null) {
					result.add(u);
				}

			}

		} catch (UnsupportedEncodingException | LDAPException e) {
			e.printStackTrace();
		}

		finally {

			try {
				close();
			} catch (LDAPException e) {
				e.printStackTrace();
			}
		}

		return result;

	}

	private List<ChiefScientist> getChiefScientis(String filter) {

		List<ChiefScientist> result = new ArrayList<>();

		try {
			connect();
			LDAPSearchResults searchResults = lc.search(Config.searchBase,
					Config.searchScope, filter, null, false);

			while (searchResults.hasMore()) {

				LDAPEntry nextEntry = null;
				nextEntry = searchResults.next();
				LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
				ChiefScientist c = buildChiefScientist(attributeSet);
				if (c != null) {
					result.add(c);
				}

			}

		} catch (UnsupportedEncodingException | LDAPException e) {
			e.printStackTrace();
		}

		finally {

			try {
				close();
			} catch (LDAPException e) {
				e.printStackTrace();
			}
		}

		return result;

	}

	public User getUserBySerial(String serialNumber)
			throws IllegalStateException {

		User result = null;
		String filter = "uid=" + serialNumber;

		List<User> list = getUsers(filter);
		if (list.size() > 1) {
			throw new java.lang.IllegalStateException("Found " + list.size()
					+ " matches for serial number: " + serialNumber);

		} else if (list.size() != 0) {
			result = list.get(0);
		}

		return result;

	}

	public List<User> getUsersByDept(String deptCode) {

		String filter = "departmentNumber=" + deptCode;

		return getUsers(filter);

	}

	public List<User> getAllUsers() {

		return getUsers(null);

	}

	public List<ChiefScientist> getAllChiefScientists() {
		return getChiefScientis(null);
	}

	public List<ChiefScientist> getChiefScientistsByDepth(String deptCode) {
		String filter = "departmentNumber=" + deptCode;

		return getChiefScientis(filter);
	}

	public ChiefScientist getChiefScientistBySerial(int serialNumber) {
		ChiefScientist result = null;
		String filter = "uid=" + serialNumber;

		List<ChiefScientist> list = getChiefScientis(filter);
		if (list.size() > 1) {
			throw new java.lang.IllegalStateException("Found " + list.size()
					+ " matches for serial number: " + serialNumber);

		} else if (list.size() != 0) {
			result = list.get(0);
		}

		return result;
	}

	private String getDnFromUserSerial(String serialNumber) {
		String dn = null;
		String filter = "uid=" + serialNumber;
		LDAPSearchResults searchResults;
		try {
			connect();
			searchResults = lc.search(Config.searchBase, Config.searchScope,
					filter, null, false);

			int count = 0;
			while (searchResults.hasMore()) {

				LDAPEntry nextEntry = null;
				nextEntry = searchResults.next();
				dn = nextEntry.getDN();
				count++;
			}

			close();
			if (count != 1) {
				throw new IllegalStateException("Found " + count
						+ "mathes for user with uid=" + serialNumber);
			}
		}

		catch (LDAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dn;
	}

	@SuppressWarnings("restriction")
	public boolean login(String password, String serialNumber) {

		boolean successful = false;

		try {
			close();
			String objectDN = getDnFromUserSerial(serialNumber);
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			ssf = new LDAPJSSESecureSocketFactory();
			LDAPConnection.setSocketFactory(ssf);
			lc = new LDAPConnection();
			lc.connect(Config.ldapHost, Config.ldapPort);

			lc.bind(Config.ldapVersion, objectDN, password.getBytes("UTF8"));
			if (lc.isBound()) {
				successful = true;
			} 
			close();
		} catch (LDAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return successful;

	}
}
