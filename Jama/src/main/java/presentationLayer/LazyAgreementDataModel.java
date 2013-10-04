package presentationLayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import businessLayer.Agreement;
import daoLayer.AgreementSearchService;

@Dependent
public class LazyAgreementDataModel extends LazyDataModel<Agreement> {
	private static final long serialVersionUID = 1L;

	@EJB
	private AgreementSearchService agreementSearch;

	private List<Agreement> displayedAgreements;

	private Date filterMinDate;
	private Date filterMaxDate;
	private Integer currentChiefId, currentCompanyId;
	private boolean inputChanged;
	private SortOrder sortOrder;

	public LazyAgreementDataModel() {
		this.inputChanged = true;
		this.currentChiefId = null;
		this.currentCompanyId = null;
		this.sortOrder = SortOrder.DESCENDING;
	}

	@PostConstruct
	public void init() {
	}

	@Override
	public Agreement getRowData(String rowKey) {
		int key = Integer.parseInt(rowKey);

		Agreement current = null;
		boolean found = false;
		Iterator<Agreement> it = displayedAgreements.iterator();

		while (false == found && it.hasNext()) {
			current = it.next();
			if (current.getId() == key) {
				found = true;
			}
		}

		if (false == found) {
			throw new IllegalStateException("Cannot retrieve agreement from a displayed row");
		}

		return current;
	}

	@Override
	public Object getRowKey(Agreement agr) {
		return agr.getId();
	}

	@Override
	public List<Agreement> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		System.out.println("-----------------");
		System.out.println("First: " + first + "; page size: " + pageSize);
		System.out.println("Min: " + filterMinDate + "; max: " + filterMaxDate);

		for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			String value = filters.get(key);
			System.out.println("[" + key + ", " + value + "]");
		}

		displayedAgreements = new ArrayList<>();

		Integer newChiefId = null;
		Integer newCompanyId = null;
		if (filters != null) {
			String tmp = filters.get("chief.id");
			if (tmp != null) {
				newChiefId = Integer.parseInt(tmp);
			}
			tmp = filters.get("company.id");
			if (tmp != null) {
				newCompanyId = Integer.parseInt(tmp);
			}
		}
		updateChiefId(newChiefId);
		updateCompanyId(newCompanyId);
		setSortOrder(sortOrder);
		System.out.println("Chief ID: " + currentChiefId + "; company ID: " + currentCompanyId);
		System.out.println("Order: " + sortOrder);
		System.out.println("Filter changed: " + inputChanged);

		if (inputChanged) {
			agreementSearch.init(filterMinDate, filterMaxDate, currentChiefId, currentCompanyId, sortOrder);
		}
		agreementSearch.setPageSize(pageSize);
		agreementSearch.setCurrentPage(first / pageSize);

		displayedAgreements = agreementSearch.getCurrentResults();

		// sort
		if (sortField != null) {
			System.out.println(sortField);
			// Collections.sort(data, new LazySorter(sortField, sortOrder));
		}

		// rowCount
		int dataSize = displayedAgreements.size();
		int rowCount = dataSize;

		// paginate
		if (dataSize > pageSize) {
			if (first + pageSize <= dataSize) {
				displayedAgreements = displayedAgreements.subList(first, first + pageSize);
			} else {
				displayedAgreements = displayedAgreements.subList(first, first + (dataSize % pageSize));
			}

			// try {
			// return data.subList(first, first + pageSize);
			// } catch (IndexOutOfBoundsException e) {
			// return data.subList(first, first + (dataSize % pageSize));
			// }
		} else if (dataSize == pageSize) {
			rowCount++;
		}

		inputChanged = false;
		this.setRowCount(rowCount);
		return displayedAgreements;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(SortOrder sortOrder) {
		System.out.println("Setting order");
		if (sortOrder != null && !this.sortOrder.equals(sortOrder)) {
			this.sortOrder = sortOrder;
			inputChanged = true;
		}
	}

	public Date getFilterMinDate() {
		return filterMinDate;
	}

	public void setFilterMinDate(Date filterMinDate) {
		if (null == this.filterMinDate) {
			if (filterMinDate != null) {
				inputChanged = true;
			}
		} else if (!this.filterMinDate.equals(filterMinDate)) {
			inputChanged = true;
		}
		this.filterMinDate = filterMinDate;
	}

	public Date getFilterMaxDate() {
		return filterMaxDate;
	}

	public void setFilterMaxDate(Date filterMaxDate) {
		if (null == this.filterMaxDate) {
			if (filterMaxDate != null) {
				inputChanged = true;
			}
		} else if (!this.filterMaxDate.equals(filterMaxDate)) {
			inputChanged = true;
		}
		this.filterMaxDate = filterMaxDate;
	}

	private void updateChiefId(Integer newValue) {
		if (currentChiefId != newValue) {
			currentChiefId = newValue;
			inputChanged = true;
		}
	}

	private void updateCompanyId(Integer newValue) {
		if (currentCompanyId != newValue) {
			currentCompanyId = newValue;
			inputChanged = true;
		}
	}

}
