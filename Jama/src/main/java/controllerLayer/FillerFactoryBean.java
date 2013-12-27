package controllerLayer;

import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import util.Config;
import businessLayer.ContractShareTableFiller;
import businessLayer.Department;
import daoLayer.FillerDaoBean;

@SessionScoped
public abstract class FillerFactoryBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject protected FillerDaoBean fillerDao;

	public ContractShareTableFiller getFiller(Department dep) {
		String depDir = Config.depRatesDefaultDir;
		if(null != dep && new File(dep.getRateDirectory()).exists()){
			depDir = dep.getRateDirectory() ;
		}
		System.out.println("°°°°°°°°°°°°°°Filler factory: depDir = " + depDir);
		ContractShareTableFiller currentFiller = createFiller(depDir);
		System.out.println("°°°°°°°°°°°°°°Filler factory: current =\n\t " + currentFiller);

		
		boolean found = false;
		List<ContractShareTableFiller> fillers = fillerDao.getAll();
		System.out.println("°°°°°°°°°°°°°°Filler factory: fillers =\n\t " + fillers);
		Iterator<ContractShareTableFiller> it = fillers.iterator();
		while(!found && it.hasNext()){
			ContractShareTableFiller f = it.next();
			if(currentFiller.equals(f)){
				System.out.println("°°°°°°°°°°°°°°Filler factory: equals ");
				found = true;
				currentFiller = f;
			}
		}
		if(!found){
			fillerDao.create(currentFiller);
		}
		
		return currentFiller;
	}
	
	protected abstract ContractShareTableFiller createFiller(String depDirectory);
}
