package businessLayer;

import java.util.HashMap;
import java.util.Map;
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

	public abstract void validate(float[] mainValues,
			float[] goodsAndServicesValues, float[] personnelValues,
			float goodsAndServices, float personnel);

	protected void initFields() {

		sharePerPersonnel = new HashMap<ChiefScientist, Float>();

	}

	public float getAtheneumCapitalBalance() {
		return atheneumCapitalBalance;
	}

	public void setAtheneumCapitalBalance(float atheneumCapitalBalance) {
		this.atheneumCapitalBalance = atheneumCapitalBalance;
	}

	public float getAtheneumCommonBalance() {
		return atheneumCommonBalance;
	}

	public void setAtheneumCommonBalance(float atheneumCommonBalance) {
		this.atheneumCommonBalance = atheneumCommonBalance;
	}

	public float getStructures() {
		return structures;
	}

	public void setStructures(float structures) {
		this.structures = structures;
	}

	public float getPersonnel() {
		return personnel;
	}

	public void setPersonnel(float personnel) {
		this.personnel = personnel;
	}

	public Map<ChiefScientist, Float> getSharePerPersonnel() {
		return sharePerPersonnel;
	}

	public void setSharePerPersonnel(
			Map<ChiefScientist, Float> sharePerPersonnel) {
		this.sharePerPersonnel = sharePerPersonnel;
	}

	public float getGoodsAndServices() {
		return goodsAndServices;
	}

	public void setGoodsAndServices(float goodsAndServices) {
		this.goodsAndServices = goodsAndServices;
	}

	public float getBusinessTrip() {
		return businessTrip;
	}

	public void setBusinessTrip(float businessTrip) {
		this.businessTrip = businessTrip;
	}

	public float getConsumerMaterials() {
		return consumerMaterials;
	}

	public void setConsumerMaterials(float consumerMaterials) {
		this.consumerMaterials = consumerMaterials;
	}

	public float getInventoryMaterials() {
		return inventoryMaterials;
	}

	public void setInventoryMaterials(float inventoryMaterials) {
		this.inventoryMaterials = inventoryMaterials;
	}

	public float getRentals() {
		return rentals;
	}

	public void setRentals(float rentals) {
		this.rentals = rentals;
	}

	public float getPersonnelOnContract() {
		return personnelOnContract;
	}

	public void setPersonnelOnContract(float personnelOnContract) {
		this.personnelOnContract = personnelOnContract;
	}

	public float getOtherCost() {
		return otherCost;
	}

	public void setOtherCost(float otherCost) {
		this.otherCost = otherCost;
	}

	public int getId() {
		return id;
	}

	protected boolean arePersonnelValuesConsistent(float[] personnelValues,
			float personnel) {
		float sum = 0;
		for (float f : personnelValues) {
			sum += f;
		}
		sum *= personnel / 100;
		return MathUtil.doubleEquals(personnel, sum);
	}

	protected boolean areGoodsAndServicesValuesConsistent(
			float[] goodsAndServicesValues, float goodsAndServices) {
		float sum = 0;
		for (float f : goodsAndServicesValues) {
			sum += f;
		}
		sum *= goodsAndServices / 100;
		return MathUtil.doubleEquals(goodsAndServices, sum);
	}

	protected boolean areMainValuesConsistent(float[] mainValues) {
		float sum = 0;
		for (float f : mainValues) {
			sum += f;
		}
		return (!(sum < 100));
	}

	protected void adjustMainValues(float total) {
		goodsAndServices += 100 - total;
	}

}
