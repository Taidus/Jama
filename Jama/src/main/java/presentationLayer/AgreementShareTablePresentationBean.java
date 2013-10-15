package presentationLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import util.InvalidValueException;
import util.Messages;
import annotations.TransferObj;
import businessLayer.Agreement;
import businessLayer.ChiefScientist;

@Named("agrShareTablePB")
@ConversationScoped
public class AgreementShareTablePresentationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	@TransferObj
	private Agreement agreement;
	@Inject
	private AgreementShareTableBuilder builder;

	private float atheneumCapitalBalance;
	private float atheneumCommonBalance;
	private float structures;
	private float personnel;

	private Map<ChiefScientist, Float> sharePerPersonnel;

	private float goodsAndServices;
	private float businessTrip;
	private float consumerMaterials;
	private float inventoryMaterials;
	private float rentals;
	private float personnelOnContract;
	private float otherCost;

	private ChiefScientist nextChiefToInsert;
	private float nextShareToInsert;

	private List<PersonnelShare> shares;
	private PersonnelShare selectedShare;

	public void addRow() {
		PersonnelShare toInsert = new PersonnelShare(nextChiefToInsert,
				nextShareToInsert);
		shares.add(toInsert);
		agreement.getShareTable().getSharePerPersonnel()
				.put(toInsert.chiefScientist, toInsert.share);
	}

	public void removeRow() {
		agreement.getShareTable().getSharePerPersonnel()
				.remove(selectedShare.getChiefScientist());
		shares.remove(selectedShare);
	}

	public List<PersonnelShare> getShares() {
		shares.clear();
		for (Entry<ChiefScientist, Float> e : agreement.getShareTable()
				.getSharePerPersonnel().entrySet()) {
			shares.add(new PersonnelShare(e.getKey(), e.getValue()));
		}
		return shares;
	}

	public ChiefScientist getNextChiefToInsert() {
		return nextChiefToInsert;
	}

	public void setNextChiefToInsert(ChiefScientist nextChiefToInsert) {
		this.nextChiefToInsert = nextChiefToInsert;
	}

	public float getNextShareToInsert() {
		return nextShareToInsert;
	}

	public void setNextShareToInsert(float nextShareToInsert) {
		this.nextShareToInsert = nextShareToInsert;
	}

	public AgreementShareTablePresentationBean() {
		this.sharePerPersonnel = new HashMap<>();
		this.shares = new ArrayList<>();
		this.otherCost = 100F;
		this.goodsAndServices = 100F;
	}

	@PostConstruct
	public void init() {
		update();
	}

	public float getAtheneumCapitalBalance() {
		return atheneumCapitalBalance;
	}

	public float getAtheneumCommonBalance() {
		return atheneumCommonBalance;
	}

	public float getStructures() {
		return structures;
	}

	public float getPersonnel() {
		return personnel;
	}

	public Map<ChiefScientist, Float> getSharePerPersonnel() {
		return sharePerPersonnel;
	}

	public float getGoodsAndServices() {
		return goodsAndServices;
	}

	public float getBusinessTrip() {
		return businessTrip;
	}

	public float getConsumerMaterials() {
		return consumerMaterials;
	}

	public float getInventoryMaterials() {
		return inventoryMaterials;
	}

	public float getRentals() {
		return rentals;
	}

	public float getPersonnelOnContract() {
		return personnelOnContract;
	}

	public float getOtherCost() {
		return otherCost;
	}

	void setAtheneumCapitalBalance(float atheneumCapitalBalance) {
		this.atheneumCapitalBalance = atheneumCapitalBalance;
	}

	void setAtheneumCommonBalance(float atheneumCommonBalance) {
		this.atheneumCommonBalance = atheneumCommonBalance;
	}

	void setStructures(float structures) {
		this.structures = structures;
	}

	public void setPersonnel(float personnel) {
		this.personnel = personnel;
		System.out.println("Quota personale aggiornata: " + personnel);
	}

	public void setBusinessTrip(float businessTrip) {
		this.businessTrip = businessTrip;
		updateOtherCosts();
	}

	public void setConsumerMaterials(float consumerMaterials) {
		this.consumerMaterials = consumerMaterials;
		updateOtherCosts();
	}

	public void setInventoryMaterials(float inventoryMaterials) {
		this.inventoryMaterials = inventoryMaterials;
		updateOtherCosts();
	}

	public void setRentals(float rentals) {
		this.rentals = rentals;
		updateOtherCosts();
	}

	private void update() {
		try {
			// il try-catch-throw è necessario anche qui perché il metodo non
			// viene chiamato solo in seguito alla validazione: potrebbero
			// esserci valori scorretti che non derivano dall'input dell'utente
			// (e.g., il file di configurazione è errato)
			builder.build();
		} catch (InvalidValueException e) {
			throw new IllegalStateException(
					"Incorrect agreement share table values");
		}

		System.out.println("Campi aggiornati: athCapBal = "
				+ atheneumCapitalBalance + ", athCommonBal = "
				+ atheneumCommonBalance + ", structures = " + structures);
		float sum = atheneumCapitalBalance + atheneumCommonBalance + structures
				+ personnel;
		this.goodsAndServices = 100F - sum;
		System.out.println("G&S aggiornato: " + goodsAndServices);
	}

	private void updateOtherCosts() {
		float sum = rentals + inventoryMaterials + consumerMaterials
				+ businessTrip;
		this.otherCost = 100F - sum;
		System.out.println("Other cost aggiornato: " + otherCost);
	}

	public void validatePersonnel(FacesContext context, UIComponent component,
			Object value) {
		float newPersonnel;
		if (value instanceof Double) {
			newPersonnel = ((Double) value).floatValue();
		} else if (value instanceof Long) {
			newPersonnel = ((Long) value).floatValue();
		} else {
			throw new IllegalStateException("Inserted input is not a number");
		}

		float oldPersonnel = this.personnel;
		this.personnel = newPersonnel;

		try {
			update();
		} catch (IllegalStateException e) {
			this.personnel = oldPersonnel;
			throw new ValidatorException(
					Messages.getErrorMessage("err_shareTableInvalidInput"));
		}

	}

	public void validateBusinessTrip(FacesContext context,
			UIComponent component, Object value) {
		float businessTrip;
		if (value instanceof Double) {
			businessTrip = ((Double) value).floatValue();
		} else if (value instanceof Long) {
			businessTrip = ((Long) value).floatValue();
		} else {
			throw new IllegalStateException("Inserted input is not a number");
		}

		float sum = rentals + inventoryMaterials + consumerMaterials
				+ businessTrip;
		System.out.println("Subfields sum: " + sum);
		if (sum > 100F) {
			throw new ValidatorException(
					Messages.getErrorMessage("err_shareTableInvalidInput"));
		}

	}

	public void validateRentals(FacesContext context, UIComponent component,
			Object value) {
		float rentals;
		if (value instanceof Double) {
			rentals = ((Double) value).floatValue();
		} else if (value instanceof Long) {
			rentals = ((Long) value).floatValue();
		} else {
			throw new IllegalStateException("Inserted input is not a number");
		}

		float sum = rentals + inventoryMaterials + consumerMaterials
				+ businessTrip;
		System.out.println("Subfields sum: " + sum);
		if (sum > 100F) {
			throw new ValidatorException(
					Messages.getErrorMessage("err_shareTableInvalidInput"));
		}

	}

	public void validateInventoryMat(FacesContext context,
			UIComponent component, Object value) {
		float inventoryMaterials;
		if (value instanceof Double) {
			inventoryMaterials = ((Double) value).floatValue();
		} else if (value instanceof Long) {
			inventoryMaterials = ((Long) value).floatValue();
		} else {
			throw new IllegalStateException("Inserted input is not a number");
		}

		float sum = rentals + inventoryMaterials + consumerMaterials
				+ businessTrip;
		System.out.println("Subfields sum: " + sum);
		if (sum > 100F) {
			throw new ValidatorException(
					Messages.getErrorMessage("err_shareTableInvalidInput"));
		}

	}

	public void validateConsumerMat(FacesContext context,
			UIComponent component, Object value) {
		float consumerMaterials;
		if (value instanceof Double) {
			consumerMaterials = ((Double) value).floatValue();
		} else if (value instanceof Long) {
			consumerMaterials = ((Long) value).floatValue();
		} else {
			throw new IllegalStateException("Inserted input is not a number");
		}

		float sum = rentals + inventoryMaterials + consumerMaterials
				+ businessTrip;
		System.out.println("Subfields sum: " + sum);
		if (sum > 100F) {
			throw new ValidatorException(
					Messages.getErrorMessage("err_shareTableInvalidInput"));
		}

	}

	public float getPercentOfMainField(float field) {
		return agreement.getWholeAmount() * field / 100;
	}

	public float getPercentOfGoodsField(float field) {
		return getPercentOfMainField(goodsAndServices) * field / 100;
	}

	public PersonnelShare getSelectedShare() {
		return selectedShare;
	}

	public void setSelectedShare(PersonnelShare selectedShare) {
		this.selectedShare = selectedShare;
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
}
