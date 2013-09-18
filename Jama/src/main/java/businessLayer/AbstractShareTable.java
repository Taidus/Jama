package businessLayer;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractShareTable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

}
