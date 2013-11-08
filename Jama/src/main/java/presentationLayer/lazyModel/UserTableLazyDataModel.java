package presentationLayer.lazyModel;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import daoLayer.UserSearchService;
import usersManagement.User;

@Dependent
public class UserTableLazyDataModel extends LazyDataModel<User> {
	private static final long serialVersionUID = 1719783501989512734L;
	
	@Inject
	private UserSearchService searchService;
	
	private List<User> displayedUsers;
	
	@PostConstruct
	public void init(){
		searchService.init();
		setRowCount(searchService.getResultNumber().intValue());
	}
	
	@Override
	public List<User> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		searchService.setCurrentPage(first / pageSize);
		searchService.setPageSize(pageSize);
		
		displayedUsers = searchService.getCurrentResults();
		
		return displayedUsers;
	}
	
	public void closePager(){
		searchService.finished();
	}

}
