package presentationLayer.lazyModel;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import presentationLayer.UtilPresentationBean;
import businessLayer.Contract;
import daoLayer.DeadlineSearchService;
import daoLayer.Pager;
import daoLayer.ResultPager;

@Dependent
public class OperatorContractScheduleLDM extends OperatorContractTableLDM {
	private static final long serialVersionUID = 1L;

	@EJB
	private DeadlineSearchService searchService;
	
	@Inject
	private UtilPresentationBean util;


	public OperatorContractScheduleLDM() {
		super();
	}


	@Override
	protected Pager<Contract> getPager() {
		return searchService;
	}


	@Override
	protected void initPager(Map<String, String> filters) {
		searchService.init(filterMinDate, filterMaxDate, filterChiefId, filterCompanyId, null, getClassFromFilter(), filterClosedContract);
	}
	
	@Override
	protected List<Contract> getData(Map<String, String> filters) {
		List<Contract> data = super.getData(filters);
		final Map<Contract, Date> contractMinDate = new HashMap<>();
		
		for(Contract c : data){
			contractMinDate.put(c, util.findClosestDeadline(c, filterMinDate));
		}
		
		Contract[] sortedData = data.toArray(new Contract[0]);
		Arrays.sort(sortedData, new Comparator<Contract>() {
			@Override
			public int compare(Contract o1, Contract o2) {
				return contractMinDate.get(o1).compareTo(contractMinDate.get(o2));
			}
		});
		
		
		return Arrays.asList(sortedData);
	}
}
