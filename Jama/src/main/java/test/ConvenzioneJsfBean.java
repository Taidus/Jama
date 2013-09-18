package test;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ConvenzioneJsfBean{
	
	@EJB private ConvenzioneServiceBean csb;
	private String title; 
	
	public ConvenzioneJsfBean() {}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void create(){
		csb.createConvenzione(this.title);
	}
	
	
}
