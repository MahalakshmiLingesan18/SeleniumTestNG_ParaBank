package com.parasoft.tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.parasoft.utilities.BaseUtils;
import com.parasoft.webdriverutility.Driver;

public class BaseTest {
	
	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		
		String browser = BaseUtils.getProperty("browser");
		
		if (browser == null || browser.isBlank()) {
            throw new IllegalArgumentException("Browser not set in config.properties or system properties");
        }
		
		Driver.initDriver(browser);
	}
	
	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		Driver.quitDriver();
	}

}
