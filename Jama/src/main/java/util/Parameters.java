package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class Parameters {

	public static final Map<Float, Float> atheneumCapitalBalanceRateTable;
	public static final float structuresRate;
	public static final float atheneumCommonBalanceRate;

	//FIXME
	private static final String filePath = "/home/giulio/Progetto Tesi/Applications/jboss-as-7.1.1.Final/standalone/deployments/Jama.war/WEB-INF/classes/";

	static {

		String propertiesFileName = "parameters.properties";
		String propertiesFilePath = filePath + propertiesFileName;
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

		structuresRate = Float.parseFloat(p.getProperty("structuresRate").trim());
		atheneumCommonBalanceRate =Float.parseFloat(p.getProperty("atheneumCommonBalanceRate").trim());

		propertiesFilePath = filePath + "atheneumRateTable.properties";
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

		atheneumCapitalBalanceRateTable = new TreeMap<>();
		for (String property : p.stringPropertyNames()) {
			atheneumCapitalBalanceRateTable.put(Float.parseFloat(property.trim()), Float.parseFloat(p.getProperty(property).trim()));
		}
	}

}
