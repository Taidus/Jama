package presentationLayer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import businessLayer.Agreement;

public abstract class LazyAgreementDataModel extends LazyDataModel<Agreement> {
	private static final long serialVersionUID = 1L;

	protected List<Agreement> displayedAgreements;
	protected Agreement selectedValue;

	protected Integer filterChiefId, filterCompanyId;
	protected boolean ignoreTableFilters;

	public LazyAgreementDataModel() {
		this.ignoreTableFilters = false;
		this.filterChiefId = null;
		this.filterCompanyId = null;
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
		updateFields(first, pageSize, sortField, sortOrder, filters);
		displayedAgreements = getData(first, pageSize, filters);

		// rowCount
		int rowCount;
		// if (displayedAgreements.size() == pageSize) {
		// displayedAgreements.add(null);
		//
		// }
		rowCount = displayedAgreements.size();
		this.setRowCount(rowCount);
		System.out.println("getRowCount = " + this.getRowCount());
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

		ignoreTableFilters = false;
		return displayedAgreements;
	}

	protected void updateFields(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		// TODO splittami
		/* TODO Blocco stampe. Eliminare */
		System.out.println("-----------------");

		System.out.println("Filters: ");
		for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			String value = filters.get(key);
			System.out.print("[" + key + ", " + value + "]   ");
		}
		/* Fine blocco stampe. Ce ne sono altre, però! */

		updateChiefAndCompany(filters);
		System.out.println("Chief ID: " + filterChiefId + "; company ID: " + filterCompanyId);
	}

	protected abstract List<Agreement> getData(int first, int pageSize, Map<String, String> filters);

	protected void updateChiefAndCompany(Map<String, String> filters) {
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
			setFilterChiefId(newChiefId);
			setFilterCompanyId(newCompanyId);
		}
	}

	public void filterOnReload() {
		ignoreTableFilters = true;
	}

	public void setFilterChiefId(Integer filterChiefId) {
		System.out.println("################################################### Setting filter chief id to " + filterChiefId);
		this.filterChiefId = filterChiefId;
	}

	public void setFilterCompanyId(Integer filterCompanyId) {
		this.filterCompanyId = filterCompanyId;
	}

	public Integer getFilterChiefId() {
		System.out.println("Getting value: " + filterChiefId);
		// return (filterChiefId != null) ? filterChiefId : 0;
		return filterChiefId;
	}

	public Integer getFilterCompanyId() {
		System.out.println("Getting value: " + filterCompanyId);
//		return (filterCompanyId != null) ? filterCompanyId : 0;
		return filterCompanyId;
	}

	public Agreement getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(Agreement selectedValue) {
		this.selectedValue = selectedValue;
	}

	public final String getFiltersAsParameterList() {
		FilterList l = initFilterList();
		if (filterChiefId != null) {
			l.put("fchiefid",  filterChiefId.toString());
		}
		if(filterCompanyId != null){
			l.put("fcompid", filterCompanyId.toString());
		}
		return l.asParameterList();
	}
	
	protected abstract FilterList initFilterList();

	public static final class FilterList {
		private Map<String, String> filters;

		protected FilterList() {
			super();
			this.filters = new HashMap<>();
		}

		protected void put(String key, String value) {
			filters.put(key, value);
		}

		public String get(String key) {
			return filters.get(key);
		}

		public String asParameterList() {
			String result = "";

			for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
				String s = it.next();
				result += s + "=" + filters.get(s);
				if (it.hasNext()) {
					result += "&";
				}
			}

			return result;
		}
	}
}
