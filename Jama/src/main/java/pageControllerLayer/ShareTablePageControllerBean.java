package pageControllerLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import util.Messages;
import businessLayer.AbstractShareTable;
import businessLayer.ChiefScientist;

@RequestScoped
// FIXME: RequestScoped rompe l'aggiunta e la rimozione delle quote del personale
public abstract class ShareTablePageControllerBean {

	public ShareTablePageControllerBean() {
		shares = new ArrayList<PersonnelShare>();
	}

	private List<PersonnelShare> shares;
	private PersonnelShare selectedShare;

	public PersonnelShare getSelectedShare() {
		return selectedShare;
	}

	public void setSelectedShare(PersonnelShare selectedShare) {
		this.selectedShare = selectedShare;
	}

	public List<PersonnelShare> getShares() {

		return shares;
	}

	public void validate(FacesContext context, UIComponent component,
			Object value) {

		// float atheneumCapitalBalance = (Float) ((UIInput) component
		// .findComponent("atheneumCapitalBalance")).getValue();
		// float atheneumCommonBalance = (Float) ((UIInput) component
		// .findComponent("atheneumCommonBalance")).getValue();
		// float structures = (Float) ((UIInput) component
		// .findComponent("structures")).getValue();
		// float personnel = (Float) ((UIInput) component
		// .findComponent("personnel")).getValue();
		//
		// float goodsAndServices = (Float) ((UIInput) component
		// .findComponent("goodsAndServices")).getValue();
		// float businessTrip = (Float) ((UIInput) component
		// .findComponent("businessTrip")).getValue();
		// float consumerMaterials = (Float) ((UIInput) component
		// .findComponent("consumerMaterials")).getValue();
		// float inventoryMaterials = (Float) ((UIInput) component
		// .findComponent("inventoryMaterials")).getValue();
		// float rentals = (Float) ((UIInput)
		// component.findComponent("rentals"))
		// .getValue();
		// float personnelOnContract = (Float) ((UIInput) component
		// .findComponent("personnelOnContract")).getValue();
		// float otherCost = (Float) ((UIInput) component
		// .findComponent("otherCost")).getValue();
		//
		// float[] mainValues = { atheneumCapitalBalance, atheneumCommonBalance,
		// structures, personnel, goodsAndServices };
		// float[] goodsAndServicesValues = { businessTrip, consumerMaterials,
		// inventoryMaterials, rentals, personnelOnContract, otherCost };
		// float[] personnelValues = createPersonnelValues(shares);

		float[] mainValues = {
				getShareTable().getAtheneumCapitalBalance(),
				getShareTable().getAtheneumCommonBalance(),
				getShareTable().getStructures(),
				getShareTable().getPersonnel(),
				getShareTable().getGoodsAndServices() };

		float[] goodsAndServicesValues = {
				getShareTable().getBusinessTrip(),
				getShareTable().getConsumerMaterials(),
				getShareTable().getInventoryMaterials(),
				getShareTable().getRentals(),
				getShareTable().getPersonnelOnContract(),
				getShareTable().getOtherCost() };

		float[] personnelValues = createPersonnelValues(shares);

		debug();

		sharesDoubleEntryCheck();

		getShareTable().validate(mainValues, goodsAndServicesValues,
				personnelValues, getShareTable().getGoodsAndServices(),
				getShareTable().getPersonnel());

		fillAgreementPersonnelShares();
	}
	
	//find a solution
	protected void initShares(Map<ChiefScientist, Float> sharePerPersonnel) {
		Set<Entry<ChiefScientist, Float>> s = sharePerPersonnel.entrySet();
		for (Iterator<Entry<ChiefScientist, Float>> it = s.iterator(); it
				.hasNext();) {
			Entry<ChiefScientist, Float> e = it.next();
			shares.add(new PersonnelShare(e.getKey(), e.getValue()));
		}
		if (shares.isEmpty()) {
			shares.add(new PersonnelShare());
		}
	}

	private void sharesDoubleEntryCheck() {
		PersonnelShare[] sharesArray = new PersonnelShare[shares.size()];
		sharesArray = shares.toArray(sharesArray);
		for (int firstIndex = 0; firstIndex < sharesArray.length; firstIndex++) {
			for (int secondIndex = firstIndex + 1; secondIndex < sharesArray.length; secondIndex++) {
				ChiefScientist firstChief = sharesArray[firstIndex]
						.getChiefScientist();
				ChiefScientist secondChief = sharesArray[secondIndex]
						.getChiefScientist();
				if (firstChief.equals(secondChief)) {
					throw new ValidatorException(
							Messages.getErrorMessage("err_doubleChiefInShares"));
				}
			}
		}
	}

	private float[] createPersonnelValues(List<PersonnelShare> shares) {
		float[] f = new float[shares.size()];
		int i = 0;
		for (PersonnelShare p : shares) {
			if (p.getChiefScientist() != null) {
				f[i] = p.getShare();
				i++;
			}
		}
		return f;
	}

	private void fillAgreementPersonnelShares() {
		Map<ChiefScientist, Float> m = new HashMap<>();
		for (PersonnelShare p : shares) {
			if (p.getChiefScientist() != null) {
				m.put(p.getChiefScientist(), p.getShare());
			}
		}
		getShareTable().setSharePerPersonnel(m);
	}

	private void debug() {
		System.out.println("Quote attuali: ");
		for (PersonnelShare p : shares) {
			if (p.getChiefScientist() != null) {
				System.out.println(p.getChiefScientist().getCompleteName()
						+ ": " + p.getShare());
			}
		}
	}

	public static class PersonnelShare {
		private ChiefScientist chiefScientist;
		private float share;

		public PersonnelShare(ChiefScientist chiefScientist, float share) {
			this.chiefScientist = chiefScientist;
			this.share = share;
		}

		public PersonnelShare() {
			chiefScientist = null;
			share = 0;
		}

		public ChiefScientist getChiefScientist() {
			return chiefScientist;
		}

		public void setChiefScientist(ChiefScientist chiefScientist) {
			this.chiefScientist = chiefScientist;
		}

		public float getShare() {
			return share;
		}

		public void setShare(float share) {
			this.share = share;
		}

	}

	public void addRow() {
		shares.add(new PersonnelShare());
	}

	public void removeRow() {
		shares.remove(selectedShare);
	}

	// metodi per le percentuali
	private float computePercent(float percent) {
		return getWholeAmount() * percent / 100;
	}
	
	private float computeRelativePercent(float percent, float relativePercent) {
		return computePercent(relativePercent) * percent / 100;
	}

	public float getPercentAtheneumCapitalBalance() {
		return computePercent(getShareTable()
				.getAtheneumCapitalBalance());
	}

	public float getPercentAtheneumCommonBalance() {
		return computePercent(getShareTable().getAtheneumCommonBalance());
	}

	public float getPercentStructures() {
		return computePercent(getShareTable().getStructures());
	}

	public float getPercentPersonnel() {
		return computePercent(getShareTable().getPersonnel());
	}

	// public Map<ChiefScientist, Float> getSharePerPersonnel() {
	// return sharePerPersonnel;
	// }

	public float getPercentGoodsAndServices() {
		return computePercent(getShareTable().getGoodsAndServices());
	}

	public float getPercentBusinessTrip() {
		return computeRelativePercent(getShareTable().getBusinessTrip(), getShareTable().getGoodsAndServices());
	}

	public float getPercentConsumerMaterials() {
		return computeRelativePercent(getShareTable().getConsumerMaterials(), getShareTable().getGoodsAndServices());
	}

	public float getPercentInventoryMaterials() {
		return computeRelativePercent(getShareTable().getInventoryMaterials(), getShareTable().getGoodsAndServices());
	}

	public float getPercentRentals() {
		return computeRelativePercent(getShareTable().getRentals(), getShareTable().getGoodsAndServices());
	}

	public float getPercentPersonnelOnContract() {
		return computeRelativePercent(getShareTable().getPersonnelOnContract(), getShareTable().getGoodsAndServices());
	}

	public float getPercentOtherCost() {
		return computeRelativePercent(getShareTable().getOtherCost(), getShareTable().getGoodsAndServices());
	}
	
	public abstract AbstractShareTable getShareTable();
	public abstract float getWholeAmount();

}
