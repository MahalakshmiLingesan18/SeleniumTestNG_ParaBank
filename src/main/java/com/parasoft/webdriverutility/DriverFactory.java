package com.parasoft.webdriverutility;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public final class DriverFactory {
	
	// prevent instantiation
	private DriverFactory() {
	}
	
	public static WebDriver getDriver(String browser) {
		
		if (browser == null || browser.isBlank()) {
            throw new IllegalArgumentException("Browser parameter is required (chrome/firefox/edge).");
        }
		
        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "chrome":
            	WebDriverManager.chromedriver().setup();
            	ChromeOptions chromeOptions = new ChromeOptions();
            	
            	// strong prefs - disable password manager and related UI
            	Map<String, Object> prefs = new HashMap<>();
            	prefs.put("credentials_enable_service", false);
            	prefs.put("profile.password_manager_enabled", false);
            	// mute notification UI
            	prefs.put("profile.default_content_setting_values.notifications", 2);
            	chromeOptions.setExperimentalOption("prefs", prefs);

            	// recommended switches
            	chromeOptions.addArguments("--disable-popup-blocking");
            	chromeOptions.addArguments("--disable-notifications");
            	chromeOptions.addArguments("--disable-save-password-bubble");
            	chromeOptions.addArguments("--no-first-run");
            	chromeOptions.addArguments("--no-default-browser-check");
            	chromeOptions.addArguments("--disable-extensions");
            	chromeOptions.addArguments("--disable-dev-shm-usage");
            	chromeOptions.addArguments("--disable-gpu");
         
            	// disable some features that may trigger leak detection
            	chromeOptions.addArguments("--disable-features=PasswordLeakDetection,AutofillServerCommunication,AutofillKeyMeasures");
            	// reduce automation detection banner noise
            	chromeOptions.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
            	chromeOptions.setExperimentalOption("useAutomationExtension", false);
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
            	WebDriverManager.firefoxdriver().setup();
            	FirefoxOptions firefoxOptions = new FirefoxOptions();

                // Disable Firefox password manager prompts
                // signon.rememberSignons = false -> don't offer to remember new logins
                firefoxOptions.addPreference("signon.rememberSignons", false);
                firefoxOptions.addPreference("signon.autofillForms", false);
                
                // Prevent "saved logins" UI from showing
                firefoxOptions.addPreference("security.warn_entering_secure", false);
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
            case "microsoftedge":
            	WebDriverManager.edgedriver().setup();
            	EdgeOptions edgeOptions = new EdgeOptions();

                // Edge is chromium-based; use same prefs as Chrome
                edgeOptions.setExperimentalOption("prefs", Map.of(
                        "credentials_enable_service", false,
                        "profile.password_manager_enabled", false
                ));
                edgeOptions.addArguments("--disable-popup-blocking");
                edgeOptions.addArguments("--disable-notifications");
                edgeOptions.addArguments("--disable-save-password-bubble");
                driver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        return driver;
    }

}
