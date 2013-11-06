package presentationLayer.lazyModel;

import java.util.Map;

import org.primefaces.model.SortOrder;

import businessLayer.Contract;

public abstract class SkeletalContractTableLDM extends ContractTableLazyDataModel {
	// classe di comodo. I filtri comuni a tutte le tabelle vengono gestiti qui

	private static final long serialVersionUID = -7601359158639574407L;

	protected String filterContractClass;
	protected Boolean filterClosedContract;
	protected Integer filterCompanyId;


	public SkeletalContractTableLDM() {
		super();
		this.filterContractClass = Contract.class.getName();
		this.filterClosedContract = null;
		this.filterCompanyId = null;
	}


	public Boolean getFilterClosedContract() {
		return filterClosedContract;
	}


	public void setFilterClosedContract(Boolean filterClosedContract) {
		System.out.println("Vuoi convenzioni chiuse? " + filterClosedContract);
		this.filterClosedContract = filterClosedContract;
	}


	public String getFilterContractClass() {
		return filterContractClass;
	}


	public Integer getFilterCompanyId() {
		return filterCompanyId;
	}


	public void setFilterCompanyId(Integer filterCompanyId) {
		this.filterCompanyId = filterCompanyId;
	}


	public void setFilterContractClass(String filterContractClass) {
		if (null == filterContractClass) {
			this.filterContractClass = Contract.class.getName();
		} else {
			this.filterContractClass = filterContractClass;
		}
	}


	protected Class<? extends Contract> getClassFromFilter() {
		try {
			return (Class<? extends Contract>) Class.forName(filterContractClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new IllegalStateException("Illegal Contract class: " + filterContractClass);
			// TODO rivedere la gestione dell'eccezione, se serve
		}
	}


	@Override
	protected void updateFields(String sortField, SortOrder sortOrder, Map<String, String> filters) {
		if (!ignoreUiTableFilters && filters != null) {

			String tmp = filters.get("closedString");
			Boolean newFilterClosed = null;
			tmp = filters.get("closedString");
			if (tmp != null) {
				newFilterClosed = Boolean.valueOf(tmp);
			}
			setFilterClosedContract(newFilterClosed);

			Integer newCompanyId = null;
			tmp = filters.get("company.id");
			if (tmp != null) {
				newCompanyId = Integer.parseInt(tmp);
			}
			setFilterCompanyId(newCompanyId);

			setFilterContractClass(filters.get("class"));
		}
		// System.out.println("Company ID: " + filterCompanyId + "; closed: " +
		// filterClosedContract);
		System.out.println("Closed: " + filterClosedContract + "; type: " + filterContractClass + "; company ID: " + filterCompanyId);
	}


	@Override
	protected FilterList initFilterList() {
		FilterList l = new FilterList();
		l.put("class", filterContractClass.toString());

		if (filterClosedContract != null) {
			l.put("closedString", filterClosedContract.toString());
		}

		if (filterCompanyId != null) {
			l.put("company.id", filterCompanyId.toString());
		}

		return l;
	}

}
