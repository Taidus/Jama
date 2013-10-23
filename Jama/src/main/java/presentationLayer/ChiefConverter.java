package presentationLayer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import util.Config;
import businessLayer.ChiefScientist;
import daoLayer.ChiefScientistDaoBean;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

@Named
@RequestScoped
public class ChiefConverter implements Converter {
	
	@EJB private ChiefScientistDaoBean chiefDaoBean;
	
	public ChiefConverter() {}

	@Override	
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		int id = Integer.parseInt(value);
		return chiefDaoBean.getById(id);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if(null == value){
			return "";
		}
		return String.valueOf(((ChiefScientist) value).getId());
	}
	
	public String testFM() throws IOException, TemplateException{
				
		Configuration cfg = new Configuration();

		// Specify the data source where the template files come from. Here I set a
		// plain directory for it, but non-file-system are possible too:
		cfg.setDirectoryForTemplateLoading(new File(Config.fileDir));

		// Specify how templates will see the data-model. This is an advanced topic...
		// for now just use this:
		cfg.setObjectWrapper(new DefaultObjectWrapper());

		// Set your preferred charset template files are stored in. UTF-8 is
		// a good choice in most applications:
		cfg.setDefaultEncoding("UTF-8");

		// Sets how errors will appear. Here we assume we are developing HTML pages.
		// For production systems TemplateExceptionHandler.RETHROW_HANDLER is better.
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

		// At least in new projects, specify that you want the fixes that aren't
		// 100% backward compatible too (these are very low-risk changes as far as the
		// 1st and 2nd version number remains):
		cfg.setIncompatibleImprovements(new Version(2, 3, 20));  // FreeMarker 2.3.20
				
		Map<String, Object> root = new HashMap<>();
		root.put("word", "Pastrullo");
		
		Template temp = cfg.getTemplate("testFM.ftl");
				
		Writer out = new OutputStreamWriter(System.out);
		temp.process(root, out);  
		
		System.out.println();
		
		return "";
	}

}
