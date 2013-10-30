package daoLayer;

import java.util.List;

import javax.ejb.Local;

//nn si può fare un interfaccia?
public abstract class  Pager {

	public abstract void next();
	
	public abstract void previous();

	public abstract int getCurrentPage();

	public abstract void setCurrentPage(int currentPage);

	public abstract List<?> getCurrentResults();

	public abstract int getPageSize();

	public abstract void setPageSize(int pageSize);

}
