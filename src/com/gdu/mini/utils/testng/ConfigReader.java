package com.gdu.mini.utils.testng;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.testng.log4testng.Logger;

public class ConfigReader {
	private static Logger logger=Logger.getLogger(ConfigReader.class);
	private static ConfigReader cr;
	private int retryCount=0;
	private String sourceCondeDir="src";
	private String sourceCondeEncoding ="UTF-8";
	
	private static final String RETRYCOUNT="retrycount";
	private static final String SOURCEDIR="sourcecodedir";
	private static final String SOURCEENCODING="sourcecondeenconding";
	private static final String CONFIGFILE="src/config.properties";
	
	private ConfigReader() {
		readConfig(CONFIGFILE);
	}

	public static ConfigReader getInstance() {
		if(cr == null) {
			cr = new ConfigReader();
		}
		return cr;
	}
	
	private void readConfig(String fileName)
	{
		Properties properties=getConfig(fileName);
		if (properties!=null) {
			String sRetryCount=null;
			Enumeration<?> en=properties.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				if(key.toLowerCase().equals(RETRYCOUNT))
				{
					sRetryCount=properties.getProperty(key);
				}
				if(key.toLowerCase().equals(SOURCEDIR))
				{
					sourceCondeDir=properties.getProperty(key);
				}
				if(key.toLowerCase().equals(SOURCEENCODING))
				{
					sourceCondeEncoding=properties.getProperty(key);
				}
			}
			if(sRetryCount!=null)
			{
				sRetryCount=sRetryCount.trim();
				try {
					retryCount=Integer.parseInt(sRetryCount);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					throw new NumberFormatException("Parse " + RETRYCOUNT + " [" + sRetryCount + "] from String to Int Exception");
				}
			}
		}
	}
	
	public int getRetryCount()
	{
		return retryCount;
	}
	
	public String getSourceCodeDir()
	{
		return this.sourceCondeDir;
	}
	
	public String getSrouceCodeEncoding()
	{
		return this.sourceCondeEncoding;
	}
	
	private Properties getConfig(String propertyFileName)
	{
		Properties properties=new Properties();
		try {
			properties.load(new FileInputStream(propertyFileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			properties=null;
			e.printStackTrace();
			logger.warn("FileNotFoundException:" + propertyFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			properties=null;
			e.printStackTrace();
			logger.warn("IOException:" + propertyFileName);
		}
		return properties;
	}
	
}
