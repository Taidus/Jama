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
import daoLayer.AgreementDaoBean;

@Named
@Dependent
public class LazyAgreementDataModel extends LazyDataModel<Agreement> {
	private static final long serialVersionUID = 1L;
	
	@EJB private AgreementDaoBean agreementDao;

	private List<Agreement> fetchedAgreements;
	
	private Date filterMinDate, filterMaxDate;

	public LazyAgreementDataModel() {
	}
	
	@PostConstruct public void init(){
		this.fetchedAgreements = agreementDao.getAll();
		System.out.println("Size: " + fetchedAgreements.size());
		for(Agreement agr : fetchedAgreements){
			System.out.println("Agr #" + agr.getId() + ": " + agr.getChief() + "; " + agr.getCompany() );
		}
		Field[] f = Agreement.class.getFields();
		for(int i=0; i< f.length; i++){
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
		//TODO plug Hibernate in
		System.out.println("-----------------");
		System.out.println("Min: " + filterMinDate + "; max: " + filterMaxDate);
		
		for(Iterator<String> it= filters.keySet().iterator(); it.hasNext();){
			String key = it.next();
			String value = filters.get(key);
			System.out.println("[" + key + ", " + value + "]");
		}
		
		List<Agreement> data = new ArrayList<Agreement>();

		// filter
//		for (Agreement agr : fetchedAgreements) {
//			boolean match = true;
//
//			for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
//				try {
//					String filterProperty = it.next();
//					String filterValue = filters.get(filterProperty);
//					String fieldValue = String.valueOf(agr.getClass().getField(filterProperty).get(agr));
//					System.out.println("Agr #" + agr.getId() + ": " + filterProperty + " = " + fieldValue);
//
//					if (filterValue == null || fieldValue.startsWith(filterValue)) {
//						match = true;
//					} else {
//						match = false;
//						break;
//					}
//				} catch (Exception e) {
//					System.err.println(e);
//					match = false;
//				}
//			}
//
//			if (match) {
//				data.add(agr);
//			}
//		}
		

		// sort
		if (sortField != null) {
			//Collections.sort(data, new LazySorter(sortField, sortOrder));
		}

		// rowCount
		int dataSize = data.size();
		this.setRowCount(dataSize);

		// paginate
		if (dataSize > pageSize) {
			if(first + pageSize <= dataSize){
				return data.subList(first, first + pageSize);
			}
			else{
				return data.subList(first, first + (dataSize % pageSize));
			}
			
//			try {
//				return data.subList(first, first + pageSize);
//			} catch (IndexOutOfBoundsException e) {
//				return data.subList(first, first + (dataSize % pageSize));
//			}
		} else {
			return data;
		}
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
		this.filterMaxDate = filterMaxDate;
	}
	
	
}
