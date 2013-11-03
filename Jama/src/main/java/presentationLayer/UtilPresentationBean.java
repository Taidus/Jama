package presentationLayer;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import util.Messages;
import businessLayer.Agreement;
import businessLayer.AgreementType;
import businessLayer.ChiefScientist;
import businessLayer.Company;
import businessLayer.Contract;
import businessLayer.Funding;
import businessLayer.Installment;
import daoLayer.ChiefScientistDaoBean;
import daoLayer.CompanyDaoBean;

@Named("utilPB")
@Dependent
public class UtilPresentationBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private ChiefScientistDaoBean chiefDaoBean;
	@EJB
	private CompanyDaoBean companyDaoBean;
	
	private static final Map<Class<? extends Contract>, String> contractTypeName;
	
	static{
		contractTypeName = new HashMap<>();
		contractTypeName.put(Contract.class, Messages.getString("contract"));
		contractTypeName.put(Agreement.class, Messages.getString("agreement"));
		contractTypeName.put(Funding.class, Messages.getString("funding"));
	}
	
	public String getNameFromClass(Class<? extends Contract> c){
		return contractTypeName.get(c);
	}


	public SelectItem[] getAgreementTypeItems() {
		AgreementType[] types = AgreementType.values();
		SelectItem[] result = new SelectItem[types.length];
		for (int i = 0; i < types.length; i++) {
			result[i] = new SelectItem(types[i], types[i].toString());
		}
		return result;
	}


	public SelectItem[] getChiefItems() {
		List<ChiefScientist> chiefs = chiefDaoBean.getAll();
		return getChiefsFromList(chiefs);
	}


	public SelectItem[] getCompanyItems() {
		List<Company> companies = companyDaoBean.getAll();
		SelectItem[] result = new SelectItem[companies.size()];
		Company current = null;
		for (int i = 0; i < companies.size(); i++) {
			current = companies.get(i);
			result[i] = new SelectItem(current, current.getName());
		}
		return result;
	}


	public SelectItem[] getFilterChiefItems() {
		List<ChiefScientist> chiefs = chiefDaoBean.getAll();
		SelectItem[] result = new SelectItem[chiefs.size() + 1];
		result[0] = new SelectItem("", Messages.getString("allM"));
		ChiefScientist current = null;
		for (int i = 0; i < chiefs.size(); i++) {
			current = chiefs.get(i);
			result[i + 1] = new SelectItem(current.getId(), current.getCompleteName());
		}
		return result;
	}


	public SelectItem[] getFilterCompanyItems() {
		List<Company> companies = companyDaoBean.getAll();
		SelectItem[] result = new SelectItem[companies.size() + 1];
		result[0] = new SelectItem("", Messages.getString("allF"));
		Company current = null;
		for (int i = 0; i < companies.size(); i++) {
			current = companies.get(i);
			result[i + 1] = new SelectItem(current.getId(), current.getName());
		}
		return result;
	}


	public SelectItem[] getChiefsItemsNotIn(Collection<ChiefScientist> list) {
		List<ChiefScientist> chiefs = chiefDaoBean.getAll();
		chiefs.removeAll(list);
		return getChiefsFromList(chiefs);
	}


	private SelectItem[] getChiefsFromList(List<ChiefScientist> list) {
		SelectItem[] result = new SelectItem[list.size()];
		ChiefScientist current = null;
		for (int i = 0; i < list.size(); i++) {
			current = list.get(i);
			result[i] = new SelectItem(current, current.getCompleteName());
		}
		return result;
	}


	public SelectItem[] getBooleanFilter() {
		return getBooleanFilter(Boolean.TRUE.toString(), Boolean.FALSE.toString());
	}


	public SelectItem[] getBooleanFilter(String trueLabel, String falseLabel) {
		SelectItem[] result = new SelectItem[3];
		result[0] = new SelectItem("", Messages.getString("allM"));
		result[1] = new SelectItem(Boolean.TRUE.toString(), trueLabel);
		result[2] = new SelectItem(Boolean.FALSE.toString(), falseLabel);
		return result;
	}


	/*
	 * public SelectItem[] getContractFilter() { ContractType[] types =
	 * ContractType.values(); SelectItem[] result = new SelectItem[types.length
	 * + 1]; result[0] = new SelectItem("", Messages.getString("allM")); for
	 * (int i = 0; i < types.length; i++) { result[i + 1] = new
	 * SelectItem(types[i].getRelatedClass(), types[i].getRelatedClassName()); }
	 * return result; }
	 */

	public SelectItem[] getContractFilter() {
		SelectItem[] result = new SelectItem[3];
		result[0] = new SelectItem(Contract.class.getName(), Messages.getString("allM"));
		result[1] = new SelectItem(Agreement.class.getName(), Messages.getString("agreement"));
		result[2] = new SelectItem(Funding.class.getName(), Messages.getString("funding"));
		return result;
	}


	public Date findClosestDeadline(Contract contract, Date minDate) {
		List<Installment> insts = contract.getInstallments();
		Date closestDeadline = null;
		boolean found = false;
		Iterator<Installment> it = insts.iterator();
		while (it.hasNext() && !found) {
			closestDeadline = it.next().getDate();
			if (null == minDate || !closestDeadline.before(minDate)) {
				found = true;
			}
		}
		return closestDeadline;
	}


	public UtilPresentationBean() {}
}
