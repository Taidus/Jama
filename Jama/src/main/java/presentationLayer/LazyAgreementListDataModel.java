package presentationLayer;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;

import org.primefaces.model.SortOrder;

import businessLayer.Agreement;
import daoLayer.AgreementSearchService;

@Dependent
public class LazyAgreementListDataModel extends LazyAgreementDataModel {
	private static final long serialVersionUID = 1L;

	@EJB
	private AgreementSearchService agreementSearch;

	private Date filterMinDate, filterMaxDate;
	protected SortOrder sortOrder;

	public LazyAgreementListDataModel() {
		super();
		this.sortOrder = SortOrder.DESCENDING;
	}

	public Date getFilterMinDate() {
		return filterMinDate;
	}

	public void setFilterMinDate(Date filterMinDate) {
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
		this.sortOrder = sortOrder;
	}
	
	

	@Override
	protected List<Agreement> getData(int first, int pageSize, Map<String, String> filters) {
		System.out.println("Min date: " + filterMinDate + "; max date: " + filterMaxDate);

		System.out.println("Querying");
		agreementSearch.init(filterMinDate, filterMaxDate, filterChiefId, filterCompanyId, sortOrder);

		agreementSearch.setPageSize(pageSize);
		agreementSearch.setCurrentPage(first / pageSize);

		List<Agreement> result = agreementSearch.getCurrentResults();
		agreementSearch.next();
		result.addAll(agreementSearch.getCurrentResults());

		return result;
	}

	@Override
	protected void updateFields(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		super.updateFields(first, pageSize, sortField, sortOrder, filters);
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
