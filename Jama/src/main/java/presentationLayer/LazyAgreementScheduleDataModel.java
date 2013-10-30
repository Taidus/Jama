package presentationLayer;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;

import businessLayer.Agreement;
import businessLayer.Installment;
import daoLayer.DeadlineSearchService;

@Dependent
public class LazyAgreementScheduleDataModel extends LazyAgreementDataModel {
	private static final long serialVersionUID = 1L;

	@EJB
	private DeadlineSearchService searchService;

	private Date filterMinDate, filterMaxDate;

	public LazyAgreementScheduleDataModel() {
		super();
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
	
	public void closeService(){
		searchService.finished();
	}

	public Date findClosestDeadline(Agreement agr) {
		List<Installment> insts = agr.getInstallments();
		Date closestDeadline = null;
		
		boolean found = false;
		Iterator<Installment> it = insts.iterator();
		while(it.hasNext() && !found){
			closestDeadline = it.next().getDate();
			if(null == filterMinDate || !closestDeadline.before(filterMinDate)){
				found = true;
			}
		}
		
		return closestDeadline;
	}

	@Override
	protected List<Agreement> getData(Map<String, String> filters) {
		System.out.println("Min date: " + filterMinDate + "; max date: " + filterMaxDate);

		System.out.println("Querying");
		searchService.init(filterMinDate, filterMaxDate, filterChiefId, filterCompanyId, null);

		searchService.setPageSize(pageRows);
//		int currentPage = (pageSize != 0) ? first / pageSize : 0;
		int currentPage = pageFirst / pageRows;
		searchService.setCurrentPage(currentPage);

		List<Agreement> result = searchService.getCurrentResults();
		searchService.next();
		result.addAll(searchService.getCurrentResults());

		return result;
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
		return l;
	}
}
