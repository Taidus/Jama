package presentationLayer.lazyModel;

import java.util.Date;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;

import businessLayer.Contract;
import daoLayer.ContractSearchService;
import daoLayer.ResultPagerBean;

@Dependent
public class ChiefContractListLDM extends SkeletalContractTableLDM {
	private static final long serialVersionUID = 6458813690566381587L;

	@EJB
	private ContractSearchService contractSearch;

	protected Date filterContractMinDate, filterContractMaxDate, filterInstMinDate, filterInstMaxDate;


	public ChiefContractListLDM() {
		super();
		this.filterContractMinDate = null;
		this.filterContractMaxDate = null;
		this.filterInstMinDate = null;
		this.filterInstMaxDate = null;
	}


	public Date getFilterContractMinDate() {
		return filterContractMinDate;
	}


	public void setFilterContractMinDate(Date filterContractMinDate) {
		System.out.println("TTTTTTTTTTTTTTTTTTTT filterContractMinDate" + filterContractMinDate);
		this.filterContractMinDate = filterContractMinDate;
	}


	public Date getFilterContractMaxDate() {
		return filterContractMaxDate;
	}


	public void setFilterContractMaxDate(Date filterContractMaxDate) {
		this.filterContractMaxDate = filterContractMaxDate;
	}


	public Date getFilterInstMinDate() {
		return filterInstMinDate;
	}


	public void setFilterInstMinDate(Date filterInstMinDate) {
		System.out.println("TTTTTTTTTTTTTTTTTTTT filterInstMinDate" + filterInstMinDate);
		this.filterInstMinDate = filterInstMinDate;
	}


	public Date getFilterInstMaxDate() {
		return filterInstMaxDate;
	}


	public void setFilterInstMaxDate(Date filterInstMaxDate) {
		this.filterInstMaxDate = filterInstMaxDate;
	}


	@Override
	protected void initPager(Map<String, String> filters) {
		System.out.println("Contract: min date = " + filterContractMinDate + "; max date = " + filterContractMaxDate);
		System.out.println("Installment: min date = " + filterInstMinDate + "; max date = " + filterInstMaxDate);

		System.out.println("Querying");
		contractSearch.initWithLoggedUserCode(filterContractMinDate, filterContractMaxDate, filterCompanyId, null, getClassFromFilter(),
				filterClosedContract, filterInstMinDate, filterInstMaxDate);

	}


	@Override
	protected FilterList initFilterList() {
		FilterList l = super.initFilterList();

		if (filterContractMinDate != null) {
			l.put("fcontrmindate", String.valueOf(filterContractMinDate.getTime()));
		}
		if (filterContractMaxDate != null) {
			l.put("fcontrmaxdate", String.valueOf(filterContractMaxDate.getTime()));
		}
		if (filterInstMinDate != null) {
			l.put("finstmindate", String.valueOf(filterInstMinDate.getTime()));
		}
		if (filterInstMaxDate != null) {
			l.put("finstmaxdate", String.valueOf(filterInstMaxDate.getTime()));
		}

		return l;
	}


	@Override
	protected ResultPagerBean<Contract> getPager() {
		return contractSearch;
	}

}
