package presentationLayer.lazyModel;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import businessLayer.Company;
import businessLayer.Contract;
import daoLayer.CompanySearchService;

@Dependent
public class CompanyTableLazyDataModel extends LazyDataModel<Company> implements Serializable {
	private static final long serialVersionUID = 1082863000755513400L;

	@Inject
	private CompanySearchService searchService;

	private Company selectedValue;

	private List<Company> displayedCompanies;


	public Company getSelectedValue() {
		return selectedValue;
	}


	public void setSelectedValue(Company selectedValue) {
		System.out.println("Setting selected company: " + selectedValue);
		this.selectedValue = selectedValue;
	}


	@Override
	public Company getRowData(String rowKey) {
		int key = Integer.parseInt(rowKey);
		Company current = null;
		boolean found = false;
		Iterator<Company> it = displayedCompanies.iterator();
		while (false == found && it.hasNext()) {
			current = it.next();
			if (current.getId() == key) {
				found = true;
			}
		}
		if (false == found) {
			throw new IllegalStateException("Cannot retrieve company from a displayed row");
		}
		return current;
	}


	@Override
	public Object getRowKey(Company object) {
		return object.getId();
	}


	@Override
	public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		String nameFilter = filters.get("name");

		searchService.init(nameFilter);
		setRowCount(searchService.getResultNumber().intValue());

		searchService.setCurrentPage(first / pageSize);
		searchService.setPageSize(pageSize);

		displayedCompanies = searchService.getCurrentResults();

		return displayedCompanies;
	}


	public void closePager() {
		searchService.finished();
	}

}
