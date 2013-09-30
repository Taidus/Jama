package presentationLayer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import businessLayer.Agreement;
import businessLayer.ChiefScientist;
import daoLayer.AgreementDaoBean;
import daoLayer.AgreementSearchService;

@Named
@Dependent
public class LazyAgreementDataModel extends LazyDataModel<Agreement> {
	private static final long serialVersionUID = 1L;

	@EJB
	private AgreementDaoBean agreementDao;
	@EJB
	private AgreementSearchService agreementSearch;

	private List<Agreement> fetchedAgreements;

	private Date filterMinDate;
	private Date filterMaxDate;
	private Integer currentChiefId, currentCompanyId;
	
	private boolean filterChanged;

	public LazyAgreementDataModel() {
		this.filterChanged = true;
		this.currentChiefId = null;
		this.currentCompanyId = null;
	}

	@PostConstruct
	public void init() {
		this.fetchedAgreements = agreementDao.getAll();
		System.out.println("Size: " + fetchedAgreements.size());
		for (Agreement agr : fetchedAgreements) {
			System.out.println("Agr #" + agr.getId() + ": " + agr.getChief() + "; " + agr.getCompany());
		}
		Field[] f = Agreement.class.getFields();
		for (int i = 0; i < f.length; i++) {
			System.out.println(f[i]);
		}
	}

	@Override
	public Agreement getRowData(String rowKey) {
		return null;
	}

	@Override
	public Object getRowKey(Agreement agr) {
		return null;
	}

	@Override
	public List<Agreement> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		// TODO plug Hibernate in
		System.out.println("-----------------");
		System.out.println("First: " + first + "; page size: " + pageSize);
		System.out.println("Min: " + filterMinDate + "; max: " + filterMaxDate);

		for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			String value = filters.get(key);
			System.out.println("[" + key + ", " + value + "]");
		}

		List<Agreement> data = new ArrayList<>();
//		data = fetchedAgreements;

		// filter
		// for (Agreement agr : fetchedAgreements) {
		// boolean match = true;
		//
		// for (Iterator<String> it = filters.keySet().iterator();
		// it.hasNext();) {
		// try {
		// String filterProperty = it.next();
		// String filterValue = filters.get(filterProperty);
		// String fieldValue =
		// String.valueOf(agr.getClass().getField(filterProperty).get(agr));
		// System.out.println("Agr #" + agr.getId() + ": " + filterProperty +
		// " = " + fieldValue);
		//
		// if (filterValue == null || fieldValue.startsWith(filterValue)) {
		// match = true;
		// } else {
		// match = false;
		// break;
		// }
		// } catch (Exception e) {
		// System.err.println(e);
		// match = false;
		// }
		// }
		//
		// if (match) {
		// data.add(agr);
		// }
		// }
		
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
		System.out.println("Chief ID: " + currentChiefId + "; company ID: " + currentCompanyId);
		System.out.println("Filter changed: " + filterChanged);		
		
		if(filterChanged){
			agreementSearch.init(filterMinDate, filterMaxDate, currentChiefId, currentCompanyId);
		}
		agreementSearch.setPageSize(pageSize);
		agreementSearch.setCurrentPage(first/pageSize);

		data = agreementSearch.getCurrentResults();

		// sort
		if (sortField != null) {
			// Collections.sort(data, new LazySorter(sortField, sortOrder));
		}

		// rowCount
		int dataSize = data.size();
		int rowCount = dataSize;
		
		// paginate
		if (dataSize > pageSize) {
			if (first + pageSize <= dataSize) {
				data = data.subList(first, first + pageSize);
			} else {
				data = data.subList(first, first + (dataSize % pageSize));
			}

			// try {
			// return data.subList(first, first + pageSize);
			// } catch (IndexOutOfBoundsException e) {
			// return data.subList(first, first + (dataSize % pageSize));
			// }
		}
		else if(dataSize == pageSize){
			rowCount++;
		}
		
		filterChanged = false;
		this.setRowCount(rowCount);
		return data;
	}

	public Date getFilterMinDate() {
		return filterMinDate;
	}

	public void setFilterMinDate(Date filterMinDate) {
		if(null == this.filterMinDate){
			if(filterMinDate != null){
				filterChanged = true;
			}
		}
		else if(!this.filterMinDate.equals(filterMinDate)){
			filterChanged = true;
		}
		this.filterMinDate = filterMinDate;
	}

	public Date getFilterMaxDate() {
		return filterMaxDate;
	}

	public void setFilterMaxDate(Date filterMaxDate) {
		if(null == this.filterMaxDate){
			if(filterMaxDate != null){
				filterChanged = true;
			}
		}
		else if(!this.filterMaxDate.equals(filterMaxDate)){
			filterChanged = true;
		}
		this.filterMaxDate = filterMaxDate;
	}
	
	private void updateChiefId(Integer newValue){
		if(currentChiefId != newValue){
			currentChiefId = newValue;
			filterChanged = true;
		}
	}
	
	private void updateCompanyId(Integer newValue){
		if(currentCompanyId != newValue){
			currentCompanyId = newValue;
			filterChanged = true;
		}
	}

}