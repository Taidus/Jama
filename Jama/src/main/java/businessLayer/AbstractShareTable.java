package businessLayer;

import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.sun.xml.internal.xsom.impl.scd.Iterators.Map;

@MappedSuperclass
public abstract class AbstractShareTable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private float AtheneumCapitalBalance;
	private float AtheneumCommonBalance;
	private float Structures;
	private float personnel;
	
	@ElementCollection
	private Map<ChiefScientist,Float> sharePerPersonnel;
	
	private float GoodsAndServices;
	private float businessTrip;
	private float consumerMaterials;
	private float InventoryMaterials;
	private float rentals;
	private float personnelOnConctract;
	private float otherCost;
	
	
	public float getAtheneumCapitalBalance() {
		return AtheneumCapitalBalance;
	}
	public void setAtheneumCapitalBalance(float atheneumCapitalBalance) {
		AtheneumCapitalBalance = atheneumCapitalBalance;
	}
	public float getAtheneumCommonBalance() {
		return AtheneumCommonBalance;
	}
	public void setAtheneumCommonBalance(float atheneumCommonBalance) {
		AtheneumCommonBalance = atheneumCommonBalance;
	}
	public float getStructures() {
		return Structures;
	}
	public void setStructures(float structures) {
		Structures = structures;
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
		return GoodsAndServices;
	}
	public void setGoodsAndServices(float goodsAndServices) {
		GoodsAndServices = goodsAndServices;
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
		return InventoryMaterials;
	}
	public void setInventoryMaterials(float inventoryMaterials) {
		InventoryMaterials = inventoryMaterials;
	}
	public float getRentals() {
		return rentals;
	}
	public void setRentals(float rentals) {
		this.rentals = rentals;
	}
	public float getPersonnelOnConctract() {
		return personnelOnConctract;
	}
	public void setPersonnelOnConctract(float personnelOnConctract) {
		this.personnelOnConctract = personnelOnConctract;
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
