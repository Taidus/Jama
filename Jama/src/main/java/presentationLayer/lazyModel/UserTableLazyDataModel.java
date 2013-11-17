package presentationLayer.lazyModel;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import usersManagement.User;
import daoLayer.UserSearchService;

@Dependent
public class UserTableLazyDataModel extends LazyDataModel<User> {
	private static final long serialVersionUID = 1719783501989512734L;

	@Inject
	private UserSearchService searchService;

	private List<User> displayedUsers;


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
