package usersManagement;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.Enumeration;
import java.util.Iterator;

import javax.enterprise.context.RequestScoped;
import javax.jms.IllegalStateException;

import util.Config;
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
public class LdapManager {

	private LDAPConnection lc;
	private LDAPSocketFactory ssf;
	

	private void connect() throws LDAPException, UnsupportedEncodingException {

		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		ssf = new LDAPJSSESecureSocketFactory();
		LDAPConnection.setSocketFactory(ssf);
		lc = new LDAPConnection();
		lc.connect(Config.ldapHost, Config.ldapPort);
		lc.bind(Config.ldapVersion, Config.loginDN,
				Config.password.getBytes("UTF8"));

	}

	private void close() throws LDAPException {
		lc.disconnect();

	}
	
	private String getValue(Enumeration<String> allValues){
		
		String result = null;
		
		if (allValues != null) {
			
			boolean found=false;

			while (allValues.hasMoreElements() && found==false) {

				String value = (String) allValues.nextElement();
				if(value!= null && !value.trim().equals("")){
					found=true;
				if (Base64.isLDIFSafe(value)) {
					result= value;
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

		Iterator<LDAPAttribute> allAttributes = (Iterator<LDAPAttribute>) attributeSet
				.iterator();
		
		while (allAttributes.hasNext()) {

			LDAPAttribute attribute = allAttributes.next();
			String attributeName = attribute.getName();

			Enumeration<String> allValues = (Enumeration<String>) attribute.getStringValues();
			String value = getValue(allValues);
			
			result.setRole(Role.CHIEF_SCIENTIST);;

			
			//TODO if else list
			if(attributeName.trim().equals("givenName")){
				result.setName(value);
			}else if(attributeName.trim().equals("sn")){
				result.setSurname(value);
			}if(attributeName.trim().equals("mail")){
				result.setEmail(value);;
			}if(attributeName.trim().equals("sss")){
				//FIXME
				Department d = new Department();
				d.setCode(value);
				result.addDepartment(d);
			}if(attributeName.trim().equals("uid")){
				result.setSerialNumber(value);;
			}if(attributeName.trim().equals("userPassword")){
				//FIXME
				try {
					result.setPassword(value);
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
				};
			}


		}

		return result;

	}

	public User getUser(String serialNumber) throws IllegalStateException {

		User result = null;

		try {
			connect();

			String[] attrs = { "sn" };
			LDAPSearchResults searchResults = lc.search(Config.searchBase,
					Config.searchScope, serialNumber, attrs, false);

			int count = searchResults.getCount();

			if (count != 1) {
				throw new IllegalStateException("Found " + count
						+ " matches for serial number: " + serialNumber);
			}

			while (searchResults.hasMore()) {

				LDAPEntry nextEntry = null;
				nextEntry = searchResults.next();
				System.out.println(nextEntry.getDN());
				System.out.println("  Attributes: ");
				LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
				result = buildUser(attributeSet);

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

}
