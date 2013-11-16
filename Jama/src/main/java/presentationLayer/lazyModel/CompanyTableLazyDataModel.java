package presentationLayer.lazyModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import usersManagement.User;
import daoLayer.UserSearchService;
import businessLayer.Company;

@Dependent
public class CompanyTableLazyDataModel extends LazyDataModel<Company> implements Serializable {
	private static final long serialVersionUID = 1082863000755513400L;
	
	@Inject
	private UserSearchService searchService; //TODO modificare qui e sotto
	
	private List<Company> displayedUsers;
	
	@PostConstruct
	public void init(){
		searchService.init();
		setRowCount(searchService.getResultNumber().intValue());
	}
	
	@Override
	public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		searchService.setCurrentPage(first / pageSize);
		searchService.setPageSize(pageSize);
		
//		displayedUsers = searchService.getCurrentResults();
		
		return displayedUsers;
	}
	
	public void closePager(){
		searchService.finished();
	}

}
