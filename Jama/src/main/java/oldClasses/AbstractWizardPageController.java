package oldClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import util.Messages;
import businessLayer.AbstractShareTable;
import businessLayer.ChiefScientist;

@ConversationScoped
public abstract class AbstractWizardPageController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AbstractWizardPageController() {
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

	// XXX: ho messo una toppa al bug della modifica delle quote ma sta roba fa
	// un pò schifo
	public List<PersonnelShare> getShares() {
		initShares(getShareTable().getSharePerPersonnel());
		return shares;
	}

	public abstract AbstractShareTable getShareTable();

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

		// float[] mainValues = { getShareTable().getAtheneumCapitalBalance(),
		// getShareTable().getAtheneumCommonBalance(),
		// getShareTable().getStructures(),
		// getShareTable().getPersonnel(),
		// getShareTable().getGoodsAndServices() };
		//
		// float[] goodsAndServicesValues = { getShareTable().getBusinessTrip(),
		// getShareTable().getConsumerMaterials(),
		// getShareTable().getInventoryMaterials(),
		// getShareTable().getRentals(),
		// getShareTable().getPersonnelOnContract(),
		// getShareTable().getOtherCost() };
		//
		// float[] personnelValues = createPersonnelValues(shares);

		debug();

		sharesDoubleEntryCheck();
		fillAgreementPersonnelShares();
		getShareTable().validate();

		
	}

	protected void initShares(Map<ChiefScientist, Float> sharePerPersonnel) {
		shares = new ArrayList<PersonnelShare>();
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

}
