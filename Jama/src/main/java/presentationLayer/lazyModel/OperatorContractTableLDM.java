package presentationLayer.lazyModel;

import java.util.Date;
import java.util.Map;

import org.primefaces.model.SortOrder;

public abstract class OperatorContractTableLDM extends SkeletalContractTableLDM {
	// classe usata puramente per il riutilizzo di codice

	private static final long serialVersionUID = 1L;

	protected Date filterMinDate, filterMaxDate;
	protected Integer filterChiefId;


	public OperatorContractTableLDM() {
		super();
		this.filterChiefId = null;
		this.filterMaxDate = null;
		this.filterMinDate = null;
	}


	public void setFilterChiefId(Integer filterChiefId) {
		System.out.println("################################################### Setting filter chief id to " + filterChiefId);
		this.filterChiefId = filterChiefId;
	}


	public Integer getFilterChiefId() {
		// return (filterChiefId != null) ? filterChiefId : 0;
		return filterChiefId;
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


	@Override
	protected void updateFields(String sortField, SortOrder sortOrder, Map<String, String> filters) {
		super.updateFields(sortField, sortOrder, filters);
		if (!ignoreUiTableFilters && filters != null) {
			Integer newChiefId = null;
			String tmp = filters.get("chief.id");
			if (tmp != null) {
				newChiefId = Integer.parseInt(tmp);
			}

			setFilterChiefId(newChiefId);
		}
		System.out.println("Chief ID: " + filterChiefId);
	}


	@Override
	protected FilterList initFilterList() {
		FilterList l = super.initFilterList();

		if (filterChiefId != null) {
			l.put("chief.id", filterChiefId.toString());
		}
		if (filterMinDate != null) {
			l.put("fmindate", String.valueOf(filterMinDate.getTime()));
		}
		if (filterMaxDate != null) {
			l.put("fmaxdate", String.valueOf(filterMaxDate.getTime()));

		}

		return l;
	}

}
