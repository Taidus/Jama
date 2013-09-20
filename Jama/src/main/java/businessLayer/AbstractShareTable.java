package businessLayer;

import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractShareTable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private float atheneumCapitalBalance;
	private float atheneumCommonBalance;
	private float structures;
	private float personnel;
	
	
	@ElementCollection
	private Map<ChiefScientist,Float> sharePerPersonnel;
	private float goodsAndServices;
	private float businessTrip;
	private float consumerMaterials;
	private float inventoryMaterials;
	private float rentals;
	private float personnelOnContract;
	private float otherCost;
	
	
	//da vedere il sistema per il passaggio del messaggio di errore
	public abstract boolean validate();
	
	
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
	public void setSharePerPersonnel(Map<ChiefScientist, Float> sharePerPersonnel) {
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
	
	
	
	
	
	
	
	
	

}
