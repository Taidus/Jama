package businessLayer;

import java.io.Serializable;

import javax.faces.validator.ValidatorException;
import javax.persistence.Entity;

import util.Messages;

@Entity
public class AgreementShareTable extends AbstractShareTable implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public AgreementShareTable() {
		initFields();
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
	}


	public void setBusinessTrip(float businessTrip) {
		this.businessTrip = businessTrip;
	}

	public void setConsumerMaterials(float consumerMaterials) {
		this.consumerMaterials = consumerMaterials;
	}

	public void setInventoryMaterials(float inventoryMaterials) {
		this.inventoryMaterials = inventoryMaterials;
	}

	public void setRentals(float rentals) {
		this.rentals = rentals;
	}

	public void setPersonnelOnContract(float personnelOnContract) {
		this.personnelOnContract = personnelOnContract;
	}

	@Override
	public void validate() {
		if (!areMainValuesConsistent()) {
			throw new ValidatorException(Messages.getErrorMessage("err_shareTableValues"));
		}
		if (!areGoodsAndServicesValuesConsistent()) {
			throw new ValidatorException(Messages.getErrorMessage("err_shareTableGoods"));
		}
		if (!arePersonnelValuesConsistent()) {
			throw new ValidatorException(Messages.getErrorMessage("err_shareTablePersonnel"));
		}
	}

}
