package usersManagement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import businessLayer.Department;
import util.Config;

@ApplicationScoped
@Singleton
@SuppressWarnings("unchecked")
@LocalBean
public class LdapCachedManager implements LdapQueryInterface {

	private Map<LdapQuery, ObjectWithDate<?>> cache;
	@Inject
	private LdapManager ldapManager;

	public LdapCachedManager() {
		super();
		cache = new HashMap<LdapQuery, ObjectWithDate<?>>();
	}

	@Lock(LockType.WRITE)
	private void writeToCache(LdapQuery key, ObjectWithDate<?> value) {
		cache.put(key, value);
		
	}

	@Lock(LockType.READ)
	public List<Department> getAllDepts() {

		List<Department> result = null;
		LdapQuery query = new LdapQuery(Calendar.getInstance(), null,
				QueryType.ALL_DEPTS);

		ObjectWithDate<List<Department>> obj = (ObjectWithDate<List<Department>>) cache
				.get(query);

		if (obj != null && (!toUpdate(obj.getDate(), Calendar.getInstance()))) {
			result = obj.getObj();
		}

		else {
			result = ldapManager.getAllDepts();
			writeToCache(query, new ObjectWithDate<List<Department>>(result,
					Calendar.getInstance()));
		}

		return result;
	}

	@Lock(LockType.READ)
	public User getUserBySerial(String serialNumber)
			throws IllegalStateException {

		List<User> result = null;
		LdapQuery query = new LdapQuery(Calendar.getInstance(), serialNumber,
				QueryType.USER_BY_SERIAL);

		ObjectWithDate<List<User>> obj = (ObjectWithDate<List<User>>) cache
				.get(query);

		if (obj != null && (!toUpdate(obj.getDate(), Calendar.getInstance()))) {
				result = obj.getObj();
			}

		else {
			result = new ArrayList<>();
			result.add(ldapManager.getUserBySerial(serialNumber));
			writeToCache(
					query,
					new ObjectWithDate<List<User>>(result, Calendar
							.getInstance()));
		}

		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}

	}

	@Lock(LockType.READ)
	public List<User> getUsersByDept(String deptCode) {

		List<User> result = null;
		LdapQuery query = new LdapQuery(Calendar.getInstance(), deptCode,
				QueryType.USER_BY_DEPTH);

		ObjectWithDate<List<User>> obj = (ObjectWithDate<List<User>>) cache
				.get(query);

		if (obj != null && (!toUpdate(obj.getDate(), Calendar.getInstance()))) {
			result = obj.getObj();
		}

		else {
			result = ldapManager.getUsersByDept(deptCode);
			writeToCache(
					query,
					new ObjectWithDate<List<User>>(result, Calendar
							.getInstance()));
		}

		return result;
	}

	@Lock(LockType.READ)
	public List<User> getAllUsers() {
		List<User> result = null;
		LdapQuery query = new LdapQuery(Calendar.getInstance(), null,
				QueryType.ALL_USERS);

		ObjectWithDate<List<User>> obj = (ObjectWithDate<List<User>>) cache
				.get(query);

		if (obj != null && (!toUpdate(obj.getDate(), Calendar.getInstance()))) {
			result = obj.getObj();
		}

		else {
			result = ldapManager.getAllUsers();
			writeToCache(
					query,
					new ObjectWithDate<List<User>>(result, Calendar
							.getInstance()));
		}

		return result;
	}

	private boolean toUpdate(Calendar oldDate, Calendar newDate) {
		// fic differenze , per test è scalato ai minuti invece che alle ore
		// (aggiungere /60)
		long difference = newDate.getTimeInMillis() - oldDate.getTimeInMillis();
		if ((difference / 1000 / 60) < Config.hoursBeforeLdapCacheUpdate) {
//			System.out.println("====NOT TO UPDATE====");
			return false;
		} else {
//			System.out.println("===== TO UPDATE =====");
			return true;
		}
	}

	private static enum QueryType {
		USER_BY_SERIAL, USER_BY_DEPTH, ALL_USERS, ALL_DEPTS
	}

	private static class ObjectWithDate<T> {
		private T obj;
		private Calendar date;

		public ObjectWithDate(T obj, Calendar date) {
			super();
			this.obj = obj;
			this.date = date;
		}

		public T getObj() {
			return obj;
		}

		public Calendar getDate() {
			return date;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((obj == null) ? 0 : obj.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ObjectWithDate<?> other = (ObjectWithDate<?>) obj;
			if (this.obj == null) {
				if (other.obj != null)
					return false;
			} else if (!this.obj.equals(other.obj))
				return false;
			return true;
		}
		

	}

	private static class LdapQuery {
		private String filer;
		private QueryType type;

		public LdapQuery(Calendar date, String filter, QueryType type) {
			super();
			this.filer = filter;
			this.type = type;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((filer == null) ? 0 : filer.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			LdapQuery other = (LdapQuery) obj;
			if (filer == null) {
				if (other.filer != null)
					return false;
			} else if (!filer.equals(other.filer))
				return false;
			if (type != other.type)
				return false;
			return true;
		}

	}

}
