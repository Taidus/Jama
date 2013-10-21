package controllerLayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Alternative;

import util.Config;
import businessLayer.SimpleAgreementShareTableFiller;

@Alternative
@SessionScoped
public class SimpleFillerFactoryBean extends FillerFactoryBean {
	private static final long serialVersionUID = 1L;

	public SimpleFillerFactoryBean() {}
	
	@Override
	protected SimpleAgreementShareTableFiller createFiller(String depDirectory) {
		String depPath = Config.depRatesPath + depDirectory.toLowerCase() + "/";

		String propertiesFilePath = depPath + "parameters.properties";
		Properties p = new Properties();
		InputStream in = null;

		try {
			in = new FileInputStream(propertiesFilePath);
			p.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new IllegalStateException("Could not find or open " + propertiesFilePath);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException("Could not read " + propertiesFilePath);
		}

		float structuresRate = Float.parseFloat(p.getProperty("structuresRate").trim());
		float atheneumCommonBalanceRate = Float.parseFloat(p.getProperty("atheneumCommonBalanceRate").trim());

		propertiesFilePath = depPath + "atheneumRateTable.properties";
		p = new Properties();
		in = null;

		try {
			in = new FileInputStream(propertiesFilePath);
			p.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new IllegalStateException("Could not find or open " + propertiesFilePath);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException("Could not read " + propertiesFilePath);
		}

		SortedMap<Float, Float> atheneumCapitalBalanceRateTable = new TreeMap<>();
		for (String property : p.stringPropertyNames()) {
			atheneumCapitalBalanceRateTable.put(Float.parseFloat(property.trim()), Float.parseFloat(p.getProperty(property).trim()));
		}

		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new SimpleAgreementShareTableFiller(atheneumCapitalBalanceRateTable, structuresRate, atheneumCommonBalanceRate);
	}

}