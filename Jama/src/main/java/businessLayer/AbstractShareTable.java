package businessLayer;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractShareTable {
	//TODO cancellare stampe varie
	
	

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

	protected AbstractShareTable() {
		super();
	}
	
	protected final void initFields() {
		sharePerPersonnel = new HashMap<ChiefScientist, Float>();
		otherCost = 100F;
		goodsAndServices = 100F;
	}
	
	protected void copy(AbstractShareTable copy){
		this.atheneumCapitalBalance = copy.atheneumCapitalBalance;
		this.atheneumCommonBalance = copy.atheneumCommonBalance;
		this.structures = copy.structures;
		this.personnel = copy.personnel;
		this.sharePerPersonnel = new HashMap<>(copy.sharePerPersonnel);
		this.goodsAndServices = copy.goodsAndServices;
		this.businessTrip = copy.businessTrip;
		this.consumerMaterials = copy.consumerMaterials;
		this.inventoryMaterials = copy.inventoryMaterials;
		this.rentals = copy.rentals;
		this.personnelOnContract = copy.personnelOnContract;
		this.otherCost = copy.otherCost;
	}

	public int getId() {
		return id;
	}

	public float getAtheneumCapitalBalance() {
		return atheneumCapitalBalance;
	}

	public void setAtheneumCapitalBalance(float atheneumCapitalBalance) {
		this.atheneumCapitalBalance = atheneumCapitalBalance;
		updateGoodsAndServices();
	}

	public float getAtheneumCommonBalance() {
		return atheneumCommonBalance;
	}

	public void setAtheneumCommonBalance(float atheneumCommonBalance) {
		this.atheneumCommonBalance = atheneumCommonBalance;
		updateGoodsAndServices();
	}

	public float getStructures() {
		return structures;
	}

	public void setStructures(float structures) {
		this.structures = structures;
		updateGoodsAndServices();
	}

	public float getPersonnel() {
		return personnel;
	}

	public void setPersonnel(float personnel) {
		this.personnel = personnel;
		updateGoodsAndServices();
	}

	public float getGoodsAndServices() {
		return goodsAndServices;
	}

	public float getBusinessTrip() {
		return businessTrip;
	}

	public void setBusinessTrip(float businessTrip) {
		this.businessTrip = businessTrip;
		updateOtherCosts();
	}

	public float getConsumerMaterials() {
		return consumerMaterials;
	}

	public void setConsumerMaterials(float consumerMaterials) {
		this.consumerMaterials = consumerMaterials;
		updateOtherCosts();
	}

	public float getInventoryMaterials() {
		return inventoryMaterials;
	}

	public void setInventoryMaterials(float inventoryMaterials) {
		this.inventoryMaterials = inventoryMaterials;
		updateOtherCosts();
	}

	public float getRentals() {
		return rentals;
	}

	public void setRentals(float rentals) {
		this.rentals = rentals;
		updateOtherCosts();
	}

	public float getPersonnelOnContract() {
		return personnelOnContract;
	}

	public void setPersonnelOnContract(float personnelOnContract) {
		this.personnelOnContract = personnelOnContract;
		updateOtherCosts();
	}

	public float getOtherCost() {
		return otherCost;
	}
	
	public Map<ChiefScientist, Float> getSharePerPersonnel() {
		return sharePerPersonnel;
	}


	protected void updateGoodsAndServices() {
		float sum = atheneumCapitalBalance + atheneumCommonBalance + structures + personnel;
		this.goodsAndServices = 100F - sum;
		System.out.println("G&S aggiornato: " + goodsAndServices);
	}
	
	protected void updateOtherCosts() {
		float sum = rentals + inventoryMaterials + consumerMaterials + businessTrip + personnelOnContract;
		this.otherCost = 100F - sum;
		System.out.println("Other cost aggiornato: " + otherCost);
	}

}
