package daoLayer;

import java.util.List;

import javax.ejb.Remove;

//nn si può fare un interfaccia?
public abstract class  Pager<T> {

	public abstract void next();
	
	public abstract void previous();

	public abstract int getCurrentPage();

	public abstract void setCurrentPage(int currentPage);

	public abstract List<T> getCurrentResults();

	public abstract int getPageSize();

	public abstract void setPageSize(int pageSize);
	
	public abstract Long getResultNumber();
	
	@Remove
	public void finished(){
		
	}

}
