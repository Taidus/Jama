package businessLayer;

import java.util.HashMap;
import java.util.Map;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import util.MathUtil;

@MappedSuperclass
public abstract class AbstractShareTable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	protected float atheneumCapitalBalance;
	protected float atheneumCommonBalance;
	protected float structures;
	protected float personnel;

	@ElementCollection
	protected Map<ChiefScientist, Float> sharePerPersonnel;

	protected float goodsAndServices;
	protected float businessTrip;
	protected float consumerMaterials;
	protected float inventoryMaterials;
	protected float rentals;
	protected float personnelOnContract;
	protected float otherCost;

	public abstract void validate();

	protected final void initFields() {

		sharePerPersonnel = new HashMap<ChiefScientist, Float>();
		otherCost = 100F;
		goodsAndServices = 100F;
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

	public int getId() {
		return id;
	}
	

	public void setSharePerPersonnel(Map<ChiefScientist, Float> sharePerPersonnel) {
		//FIXME sta qui perché serve da altre parti. Questa cosa la dobbiamo decidere per bene
		this.sharePerPersonnel = sharePerPersonnel;
	}

	protected boolean arePersonnelValuesConsistent() {
		float sum = 0;
		for (float f : sharePerPersonnel.values()) {
			sum += f;
		}
		sum *= personnel / 100;
		return MathUtil.doubleEquals(personnel, sum);
	}

	protected boolean areGoodsAndServicesValuesConsistent() {
		float[] goodsAndServicesValues = { businessTrip, consumerMaterials, inventoryMaterials, rentals, personnelOnContract, otherCost };
		float sum = 0;
		for (float f : goodsAndServicesValues) {
			sum += f;
		}
		sum *= goodsAndServices / 100;
		return MathUtil.doubleEquals(goodsAndServices, sum);
	}

	protected boolean areMainValuesConsistent() {
		float[] mainValues = { atheneumCapitalBalance, atheneumCommonBalance, structures, personnel, goodsAndServices };
		float sum = 0;
		for (float f : mainValues) {
			sum += f;
		}
		return MathUtil.doubleEquals(sum, 100);
	}

	protected void adjustMainValues(float total) {
		goodsAndServices += 100 - total;
	}

	public void updateGoodsAndServices(AjaxBehaviorEvent e) throws AbortProcessingException {
		float sum = atheneumCapitalBalance + atheneumCommonBalance + structures + personnel;

		goodsAndServices = 100F - sum;
		System.out.println("G&S aggiornato: " + goodsAndServices);

	}

}
