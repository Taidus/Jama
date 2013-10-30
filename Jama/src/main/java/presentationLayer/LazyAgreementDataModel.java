package presentationLayer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import businessLayer.Agreement;

public abstract class LazyAgreementDataModel extends LazyDataModel<Agreement> {
	// NB: i filtri di PF lavorano con chiavi di tipo stringa. Queste stesse
	// chiavi vengono utilizzate anche nel passaggio dei parametri via URL. Per
	// un corretto funzionamento della tabella, le chiavi relative ad uno stesso
	// parametro devono coincidere (i.e., chiave filtro = chiave parametro URL).
	// Essendo String, questa uguaglianza va mantenuta "a mano".

	private static final long serialVersionUID = 1L;

	protected DataTable dataTable;

	protected List<Agreement> displayedAgreements;
	protected int pageFirst, pageRows;
	protected Agreement selectedValue;

	protected Integer filterChiefId, filterCompanyId;
	protected boolean ignoreUiTableFilters;

	public LazyAgreementDataModel() {
		this.ignoreUiTableFilters = false;
		this.filterChiefId = null;
		this.filterCompanyId = null;
	}

	@Override
	public final Agreement getRowData(String rowKey) {
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
	public final Object getRowKey(Agreement agr) {
		return agr.getId();
	}

	@Override
	public final List<Agreement> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		this.pageFirst = first;
		this.pageRows = pageSize;
		updateFields(sortField, sortOrder, filters);
		displayedAgreements = getData(filters);

		// rowCount
		int rowCount;
		rowCount = displayedAgreements.size();
		this.setRowCount(rowCount);
		System.out.println("getRowCount = " + this.getRowCount());
		System.out.println("First: " + first + "; pageFirst: " + pageFirst + "; page size: " + pageSize + "; pageRows: " + pageRows + "; row count: "
				+ rowCount);

		ignoreUiTableFilters = false;
		System.out.println("--------------------------------------------");
		return displayedAgreements;
	}

	protected void updateFields(String sortField, SortOrder sortOrder, Map<String, String> filters) {
		/* TODO Blocco stampe. Eliminare */
		System.out.println("-----------------");

		System.out.print("Filters: { ");
		for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			String value = filters.get(key);
			System.out.print("[" + key + ", " + value + "]   ");
		}
		System.out.println("}");
		/* Fine blocco stampe. Ce ne sono altre, però! */

		updateChiefAndCompany(filters);
		System.out.println("Chief ID: " + filterChiefId + "; company ID: " + filterCompanyId);
	}

	protected abstract List<Agreement> getData(Map<String, String> filters);

	protected void updateChiefAndCompany(Map<String, String> filters) {
		if (!ignoreUiTableFilters && filters != null) {
			Integer newChiefId = null;
			Integer newCompanyId = null;
			String tmp = filters.get("chief.id");
			if (tmp != null) {
				newChiefId = Integer.parseInt(tmp);
			}
			tmp = filters.get("company.id");
			if (tmp != null) {
				newCompanyId = Integer.parseInt(tmp);
			}
			setFilterChiefId(newChiefId);
			setFilterCompanyId(newCompanyId);
		}
	}

	public void filterOnReload() {
		System.out.println("Filtering on reload");
		ignoreUiTableFilters = true;
	}

	public void loadTableFilters() {
		if (ignoreUiTableFilters) {
			Map<String, String> filters = buildFilterList().filters;
			dataTable.setFilters(filters);
			// in teoria, in questo modo vengono passati più filtri di quelli
			// realmente esistenti. Questo non è un problema, in quanto quelli
			// effettivamente usati sono gli id del responsabile e della ditta

			if (filters.containsKey("sortorder")) {
				// TODO forse servirebbe una condizione migliore
				dataTable.setSortOrder(filters.get("sortorder"));
			}

			if (filters.containsKey("first")) {
				try {
					dataTable.setFirst(Integer.parseInt(filters.get("first")));
				} catch (NumberFormatException e) {
				}
			}

			if (filters.containsKey("rows")) {
				try {
					int value = Integer.parseInt(filters.get("rows"));
					dataTable.setRows((value > 0) ? value : 10);
					// XXX controllare se setRows è il metodo giusto
				} catch (NumberFormatException e) {
				}
			}
		}
	}

	public void setFilterChiefId(Integer filterChiefId) {
		System.out.println("################################################### Setting filter chief id to " + filterChiefId);
		this.filterChiefId = filterChiefId;
	}

	public void setFilterCompanyId(Integer filterCompanyId) {
		System.out.println("################################################### Setting filter comp id to " + filterCompanyId);
		this.filterCompanyId = filterCompanyId;
	}

	public Integer getFilterChiefId() {
		System.out.println("Getting chief value: " + filterChiefId);
		// return (filterChiefId != null) ? filterChiefId : 0;
		return filterChiefId;
	}

	public Integer getFilterCompanyId() {
		System.out.println("Getting company value: " + filterCompanyId);
		// return (filterCompanyId != null) ? filterCompanyId : 0;
		return filterCompanyId;
	}

	public Agreement getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(Agreement selectedValue) {
		this.selectedValue = selectedValue;
	}

	public int getPageFirst() {
		return pageFirst;
	}

	public void setPageFirst(int pageFirst) {
		System.out.println("IO NON DOVREI ESSERE CHIAMATO SENZA MOTIVO!");
		this.pageFirst = pageFirst;
	}

	public int getPageRows() {
		return pageRows;
	}

	public void setPageRows(int pageRows) {
		this.pageRows = pageRows;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	public final String getFiltersAsParameterList() {
		return buildFilterList().asParameterList();
	}

	private FilterList buildFilterList() {
		FilterList l = initFilterList();

		l.put("first", String.valueOf(pageFirst));
		l.put("rows", String.valueOf(pageRows));
		if (filterChiefId != null) {
			l.put("chief.id", filterChiefId.toString());
		}
		if (filterCompanyId != null) {
			l.put("company.id", filterCompanyId.toString());
		}

		return l;
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
