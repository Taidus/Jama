package presentationLayer.lazyModel;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import businessLayer.Company;
import usersManagement.User;
import daoLayer.UserSearchService;

@Dependent
public class UserTableLazyDataModel extends LazyDataModel<User> {
	private static final long serialVersionUID = 1719783501989512734L;

	@Inject
	private UserSearchService searchService;

	private List<User> displayedUsers;
	
	private User selectedUser;
	
	public User getSelectedUser() {
		return selectedUser;
	}
	
	public void setSelectedUser(User selectedUser) {
		System.out.println("User table LDM: setting " + selectedUser + " as selected user");
		this.selectedUser = selectedUser;
	}
	
	public List<User> getDisplayedUsers() {
		return displayedUsers;
	}
	
	@Override
	public Object getRowKey(User object) {
		return object.getId();
	}
	
	@Override
	public User getRowData(String rowKey) {
		int key = Integer.parseInt(rowKey);
		
		User current = null;
		boolean found = false;
		Iterator<User> it = displayedUsers.iterator();
		
		while (false == found && it.hasNext()) {
			current = it.next();
			if (current.getId() == key) {
				found = true;
			}
		}
		
		if (false == found) {
			throw new IllegalStateException("Cannot retrieve user from a displayed row");
		}
		
		return current;
	}


	@Override
	public List<User> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {

		String filter = filters.get("surname");
		System.out.println("User filter: " + filter);

		searchService.init(filter);
		setRowCount(searchService.getResultNumber().intValue());

		searchService.setCurrentPage(first / pageSize);
		searchService.setPageSize(pageSize);

		displayedUsers = searchService.getCurrentResults();

		return displayedUsers;
	}


	public void closePager() {
		searchService.finished();
	}

}
