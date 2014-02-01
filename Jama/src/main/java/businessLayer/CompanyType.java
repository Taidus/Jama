package businessLayer;

public enum CompanyType {

				
		PRIVATE("Privato"),
		PUBLIC("Pubblico"),
		OTHER("Altro");
		
		CompanyType(String description) {
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
