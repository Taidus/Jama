package businessLayer;

public enum AgreementType {
	
	
	INDUSTRIAL_RESEARCH("Ricerca industriale"),
	SPERIMENTAL_DEVELOPMENT("Sviluppo sperimentale"),
	STUDIES_AND_INVESTIGATIONS("Studi ed indagini"),
	FREE_TECHNOLOGICAL_SERVICES("Servizi tecnologici e organizzativi non a tariffa");
	
	AgreementType(String description) {
		this.description = description;
	}

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
