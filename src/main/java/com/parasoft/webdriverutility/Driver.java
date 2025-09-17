package com.parasoft.webdriverutility;

import java.time.Duration;
import java.util.Objects;

import org.openqa.selenium.WebDriver;

import com.parasoft.utilities.BaseUtils;

public final class Driver {
	
	// Private constructor to prevent instantiation
	private Driver() {
	}
	
	//Initializes the WebDriver instance if it doesn’t already exist
	public static void initDriver(String browser) throws Exception {
		
		if (browser == null || browser.isBlank()) {
            throw new IllegalArgumentException("Browser parameter is missing");
        }
		
		WebDriver driver = DriverFactory.getDriver(browser);
		
		DriverManager.setDriver(driver);
			
		String url = BaseUtils.getProperty("url");
		
		if (url == null || url.isBlank()) {
            throw new IllegalStateException("Base URL is not configured. Set 'url' in config.properties or provide via system property.");
        }
		
		driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // rely on explicit waits in POM

	}
	
	//Cleanly close and remove the driver when a test finishes
	 public static void quitDriver() throws Exception {
		 
		 try {
			 if (Objects.nonNull(DriverManager.getDriver())) {
			      DriverManager.getDriver().quit(); //shuts down the browser session
			      DriverManager.unload(); //removes the driver reference from the ThreadLocal so memory is freed, and no stale sessions remain
			 }
		 } catch (Exception e) {
			 throw new Exception("ERROR in quitDriver(): " + e.getMessage());
	     }
     }

}
