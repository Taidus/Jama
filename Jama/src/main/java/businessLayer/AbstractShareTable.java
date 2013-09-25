package businessLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.resource.spi.IllegalStateException;

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

	// XXX: un refactor per accorpare tutta sta roba in un qualche tipo di
	// collezione rende migliore la
	// validazione (attualmente c'è un'istruzione mostro pazzesca)
	protected float goodsAndServices;
	protected float businessTrip;
	protected float consumerMaterials;
	protected float inventoryMaterials;
	protected float rentals;
	protected float personnelOnContract;
	protected float otherCost;

	// da vedere il sistema per il passaggio del messaggio di errore
	public abstract boolean isValid() throws IllegalStateException;
	public void initFields(){
		
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
	
	
	
	//XXX: se per caso aggiungo un attributo alla classe e mi dimentico di metterlo qua, esplode la validazione
	
	//XXX: non sono sicuro che List sia giusto, forse un array è più adatto (visto come la uso di solito)
	public List<Float> getMainValues() {
		ArrayList<Float> mainValues = new ArrayList<Float>();
		mainValues.add(atheneumCapitalBalance);
		mainValues.add(atheneumCommonBalance);
		mainValues.add(structures);
		mainValues.add(personnel);
		mainValues.add(goodsAndServices);
		
		return mainValues;
	}

	protected boolean arePersonnelSharesConsistent() {
		Float total = Float.valueOf(0);
		for (Iterator<Entry<ChiefScientist, Float>> it = sharePerPersonnel
				.entrySet().iterator(); it.hasNext();) {
			total += it.next().getValue();
		}
		return total.equals(Float.valueOf(100));
	}

	protected boolean areGoodsSharesConsistent() {
		return Float.valueOf(100).equals(
				businessTrip + consumerMaterials + inventoryMaterials + rentals
						+ personnelOnContract + otherCost);
	}

	protected boolean areMainValuesConsistent() {
		Float total = atheneumCapitalBalance + atheneumCommonBalance
				+ structures + personnel + goodsAndServices;
		if (total.compareTo(Float.valueOf(100)) > 0) {
			return false;
		}
		adjustMainValues(total);
		return true;
	}

	protected void adjustMainValues(Float total) {
		goodsAndServices += Float.valueOf(100) - total;
	}
	
	
}
