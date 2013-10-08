package presentationLayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	private Agreement selectedValue;

	private Date filterMinDate;
	private Date filterMaxDate;
	private Integer filterChiefId, filterCompanyId;
	private boolean inputChanged, ignoreTableFilters;
	private SortOrder sortOrder;

	public LazyAgreementDataModel() {
		this.inputChanged = true;
		this.ignoreTableFilters = false;
		this.filterChiefId = null;
		this.filterCompanyId = null;
		this.sortOrder = SortOrder.DESCENDING;
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
		//TODO splittami
		System.out.println("-----------------");
		System.out.println("Min date: " + filterMinDate + "; max date: " + filterMaxDate);

		System.out.println("Filters: ");
		for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			String value = filters.get(key);
			System.out.print("[" + key + ", " + value + "]   ");
		}

		displayedAgreements = new ArrayList<>();

		if (!ignoreTableFilters) {
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
		}
		setSortOrder(sortOrder);
		System.out.println("Chief ID: " + filterChiefId + "; company ID: " + filterCompanyId);
		System.out.println("Order: " + sortOrder);
		System.out.println("Input changed: " + inputChanged);

		if (inputChanged) {
			System.out.println("Querying");
			agreementSearch.init(filterMinDate, filterMaxDate, filterChiefId, filterCompanyId, sortOrder);
		}
		agreementSearch.setPageSize(pageSize);
		
		agreementSearch.setCurrentPage(first / pageSize);
		displayedAgreements = agreementSearch.getCurrentResults();
		agreementSearch.next();
		displayedAgreements.addAll(agreementSearch.getCurrentResults());

		// sort
		if (sortField != null) {
			System.out.println(sortField);
			// Collections.sort(data, new LazySorter(sortField, sortOrder));
		}

		// rowCount
		int rowCount;
		if (displayedAgreements.size() == pageSize) {
			displayedAgreements.add(null);

		}
		rowCount = displayedAgreements.size();
		System.out.println("First: " + first + "; page size: " + pageSize + "; row count: " + rowCount);

		// paginate
		// if (dataSize > pageSize) {
		// if (first + pageSize <= dataSize) {
		// displayedAgreements = displayedAgreements.subList(first, first +
		// pageSize);
		// } else {
		// displayedAgreements = displayedAgreements.subList(first, first +
		// (dataSize % pageSize));
		// }
		//
		// // try {
		// // return data.subList(first, first + pageSize);
		// // } catch (IndexOutOfBoundsException e) {
		// // return data.subList(first, first + (dataSize % pageSize));
		// // }
		// } else if (dataSize == pageSize) {
		// rowCount++;
		// }

		inputChanged = false;
		ignoreTableFilters = false;
		this.setRowCount(rowCount);
		System.out.println("getRowCount = " + this.getRowCount());
		return displayedAgreements;
	}

	public void filterOnReload() {
		inputChanged = true;
		ignoreTableFilters = true;
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
		if (filterChiefId != newValue) {
			filterChiefId = newValue;
			inputChanged = true;
		}
	}

	private void updateCompanyId(Integer newValue) {
		if (filterCompanyId != newValue) {
			filterCompanyId = newValue;
			inputChanged = true;
		}
	}

	public Integer getFilterChiefId() {
		System.out.println("Getting value: " + filterChiefId);
		return (filterChiefId != null) ? filterChiefId : 0;
	}

	public Integer getFilterCompanyId() {
		System.out.println("Getting value: " + filterCompanyId);
		return (filterCompanyId != null) ? filterCompanyId : 0;
	}

	public Agreement getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(Agreement selectedValue) {
		this.selectedValue = selectedValue;
	}
}
