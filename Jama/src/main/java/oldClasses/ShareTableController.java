package oldClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import util.Messages;
import businessLayer.AbstractShareTable;
import businessLayer.ChiefScientist;

public class ShareTableController {

	private List<PersonnelShare> shares;
	private PersonnelShare selectedShare;
	private ShareTableHolder holder;

	public ShareTableController(ShareTableHolder shareTableHolder) {
		this.holder = shareTableHolder;
		shares = new ArrayList<PersonnelShare>();
		initShares(holder.getShareTable().getSharePerPersonnel());
	}

	public PersonnelShare getSelectedShare() {
		return selectedShare;
	}

	public void setSelectedShare(PersonnelShare selectedShare) {
		this.selectedShare = selectedShare;
	}

	public List<PersonnelShare> getShares() {

		return shares;
	}

	public AbstractShareTable getShareTable() {
		return holder.getShareTable();
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

		// float[] mainValues = {
		// holder.getShareTable().getAtheneumCapitalBalance(),
		// holder.getShareTable().getAtheneumCommonBalance(),
		// holder.getShareTable().getStructures(),
		// holder.getShareTable().getPersonnel(),
		// holder.getShareTable().getGoodsAndServices() };
		//
		// float[] goodsAndServicesValues = {
		// holder.getShareTable().getBusinessTrip(),
		// holder.getShareTable().getConsumerMaterials(),
		// holder.getShareTable().getInventoryMaterials(),
		// holder.getShareTable().getRentals(),
		// holder.getShareTable().getPersonnelOnContract(),
		// holder.getShareTable().getOtherCost() };
		//
		// float[] personnelValues = createPersonnelValues(shares);

		debug();
		fillAgreementPersonnelShares();
		sharesDoubleEntryCheck();
		holder.getShareTable().validate();

	}

	private void initShares(Map<ChiefScientist, Float> sharePerPersonnel) {
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

	// private float[] createPersonnelValues(List<PersonnelShare> shares) {
	// float[] f = new float[shares.size()];
	// int i = 0;
	// for (PersonnelShare p : shares) {
	// if (p.getChiefScientist() != null) {
	// f[i] = p.getShare();
	// i++;
	// }
	// }
	// return f;
	// }

	private void fillAgreementPersonnelShares() {
		Map<ChiefScientist, Float> m = new HashMap<>();
		for (PersonnelShare p : shares) {
			if (p.getChiefScientist() != null) {
				m.put(p.getChiefScientist(), p.getShare());
			}
		}
		holder.getShareTable().setSharePerPersonnel(m);
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

	private static class PersonnelShare {
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
		return holder.getWholeAmount() * percent / 100;
	}

	public float getPercentAtheneumCapitalBalance() {
		return computePercent(holder.getShareTable()
				.getAtheneumCapitalBalance());
	}

	public float getPercentAtheneumCommonBalance() {
		return computePercent(holder.getShareTable().getAtheneumCommonBalance());
	}

	public float getPercentStructures() {
		return computePercent(holder.getShareTable().getStructures());
	}

	public float getPercentPersonnel() {
		return computePercent(holder.getShareTable().getPersonnel());
	}

	// public Map<ChiefScientist, Float> getSharePerPersonnel() {
	// return sharePerPersonnel;
	// }

	public float getPercentGoodsAndServices() {
		return computePercent(holder.getShareTable().getGoodsAndServices());
	}

	public float getPercentBusinessTrip() {
		return computePercent(holder.getShareTable().getBusinessTrip());
	}

	public float getPercentConsumerMaterials() {
		return computePercent(holder.getShareTable().getConsumerMaterials());
	}

	public float getPercentInventoryMaterials() {
		return computePercent(holder.getShareTable().getInventoryMaterials());
	}

	public float getPercentRentals() {
		return computePercent(holder.getShareTable().getRentals());
	}

	public float getPercentPersonnelOnContract() {
		return computePercent(holder.getShareTable().getPersonnelOnContract());
	}

	public float getPercentOtherCost() {
		return computePercent(holder.getShareTable().getOtherCost());
	}

}
