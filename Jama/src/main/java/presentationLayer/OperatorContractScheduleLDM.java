package presentationLayer;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;

import businessLayer.Agreement;
import businessLayer.Contract;
import businessLayer.Installment;
import daoLayer.DeadlineSearchService;
import daoLayer.ResultPagerBean;

@Dependent
public class OperatorContractScheduleLDM extends OperatorContractTableLDM {
	private static final long serialVersionUID = 1L;

	@EJB
	private DeadlineSearchService searchService;


	public OperatorContractScheduleLDM() {
		super();
	}


	public Date findClosestDeadline(Agreement agr) {
		List<Installment> insts = agr.getInstallments();
		Date closestDeadline = null;

		boolean found = false;
		Iterator<Installment> it = insts.iterator();
		while (it.hasNext() && !found) {
			closestDeadline = it.next().getDate();
			if (null == filterMinDate || !closestDeadline.before(filterMinDate)) {
				found = true;
			}
		}

		return closestDeadline;
	}


	@Override
	protected ResultPagerBean<Contract> getPager() {
		return searchService;
	}


	@Override
	protected void initPager(Map<String, String> filters) {
		System.out.println("Min date: " + filterMinDate + "; max date: " + filterMaxDate);

		System.out.println("Querying");
		searchService.init(filterMinDate, filterMaxDate, filterChiefId, filterCompanyId, null, Agreement.class);
	}
}
