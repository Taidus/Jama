package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Parameters {

	public static final Map<Double, Double> atheneumCapitalBalanceRateTable;
	public static final double structuresRate;
	public static final double atheneumCommonBalanceRate;

	private static final String filePath = "src/main/resources/";

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

		structuresRate = Double.parseDouble(p.getProperty("structuresRate").trim());
		atheneumCommonBalanceRate = Double.parseDouble(p.getProperty("atheneumCommonBalanceRate").trim());

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

		atheneumCapitalBalanceRateTable = new HashMap<>();
		for (String property : p.stringPropertyNames()) {
			atheneumCapitalBalanceRateTable.put(Double.parseDouble(property.trim()), Double.parseDouble(p.getProperty(property).trim()));
		}
	}

}
