 package businessLayer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;


@NamedQuery(name = "AgreementShareTableFiller.findAll", query = "SELECT f FROM AgreementShareTableFiller f ") 
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)

public abstract class ContractShareTableFiller {
	public abstract void fill(ContractShareTable table);
	
	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(Object obj);
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO) private int id;
	
}
