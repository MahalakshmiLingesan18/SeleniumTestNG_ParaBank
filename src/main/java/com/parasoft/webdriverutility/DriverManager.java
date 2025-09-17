package com.parasoft.webdriverutility;

import java.util.Objects;
import org.openqa.selenium.WebDriver;

public final class DriverManager {
	
	// Private constructor to prevent instantiation
	private DriverManager() {
	}
	
	// ThreadLocal variable to store driver instance per thread
	private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
	
	// Setter - assigns driver instance for current thread
	public static void setDriver(WebDriver driverInstance) {
	    if (Objects.nonNull(driverInstance)) { //prevents assigning null
	    	driverThreadLocal.set(driverInstance);
	    }
	}
	
	// Getter - retrieves driver instance for current thread
	public static WebDriver getDriver() {
	    return driverThreadLocal.get();
	}
	
	// Unload - removes driver reference from ThreadLocal
	public static void unload() {
		driverThreadLocal.remove();
	}

}
