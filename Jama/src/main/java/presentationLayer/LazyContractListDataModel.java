package presentationLayer;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;

import org.primefaces.model.SortOrder;

import businessLayer.Agreement;
import businessLayer.Contract;
import daoLayer.ContractSearchService;

@Dependent
public class LazyContractListDataModel extends LazyContractDataModel {
	private static final long serialVersionUID = 1L;

	@EJB
	private ContractSearchService contractSearch;

	private Date filterMinDate, filterMaxDate;
	protected SortOrder sortOrder;

	public LazyContractListDataModel() {
		super();
		this.sortOrder = SortOrder.DESCENDING;
	}

	public Date getFilterMinDate() {
		return filterMinDate;
	}

	public void setFilterMinDate(Date filterMinDate) {
		System.out.println("################################################### Setting min date to " + filterMaxDate);
		this.filterMinDate = filterMinDate;
	}

	public Date getFilterMaxDate() {
		return filterMaxDate;
	}

	public void setFilterMaxDate(Date filterMaxDate) {
		System.out.println("################################################### Setting max date to " + filterMaxDate);
		this.filterMaxDate = filterMaxDate;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(SortOrder sortOrder) {
		System.out.println("################################################### Setting sort order to " + sortOrder);
		this.sortOrder = sortOrder;
	}
	
	public void closeService(){
		contractSearch.finished();
	}
	
	@Override
	protected List<Contract> getData(Map<String, String> filters) {
		System.out.println("Min date: " + filterMinDate + "; max date: " + filterMaxDate);

		System.out.println("Querying");
		contractSearch.init(filterMinDate, filterMaxDate, filterChiefId, filterCompanyId, sortOrder, Agreement.class);

		contractSearch.setPageSize(pageRows);
//		int currentPage = (pageSize != 0) ? first / pageSize : 0;
		int currentPage = pageFirst / pageRows;
		contractSearch.setCurrentPage(currentPage);

		List<Contract> result = contractSearch.getCurrentResults();
		contractSearch.next();
		result.addAll(contractSearch.getCurrentResults());

		return result;
	}

	@Override
	protected void updateFields(String sortField, SortOrder sortOrder, Map<String, String> filters) {
		super.updateFields(sortField, sortOrder, filters);
		setSortOrder(sortOrder);
		System.out.println("Order: " + sortOrder);
	}

	@Override
	protected FilterList initFilterList() {
		FilterList l = new FilterList();
		if (filterMinDate != null) {
			l.put("fmindate", String.valueOf(filterMinDate.getTime()));
		}
		if (filterMaxDate != null) {
			l.put("fmaxdate", String.valueOf(filterMaxDate.getTime()));

		}
		if (sortOrder != null) {
			l.put("sortorder", sortOrder.toString());
		}
		return l;
	}
}
