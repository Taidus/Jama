package test;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Convenzione {
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String title;

	public Convenzione() {}

	public Convenzione(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
}
