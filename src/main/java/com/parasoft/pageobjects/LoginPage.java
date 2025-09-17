package com.parasoft.pageobjects;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.parasoft.webdriverutility.DriverManager;

public class LoginPage {
	
	public LoginPage() {
		PageFactory.initElements(DriverManager.getDriver(), this);;
	}
	
	@FindBy(name="username")
	private WebElement userNameBox;
	
	@FindBy(name="password")
	private WebElement passwordBox;
	
	@FindBy(xpath="//input[@value='Log In']")
	private WebElement loginButton;
	
	@FindBy(xpath="//p[@class='smallText']")
	private WebElement homePageDisplayed;
	
	@FindBy(xpath="//a[text()='Forgot login info?']")
	private WebElement forgotLogin;
	
	private WebDriverWait getWait() {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
    }
	
	public void enterUserName(String user) {
		getWait().until(ExpectedConditions.visibilityOf(userNameBox)).sendKeys(user);
	}
	
	public void enterPassword(String pass) {
		getWait().until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(pass);
	}
	
	public void clickLogin() {
		getWait().until(ExpectedConditions.elementToBeClickable(loginButton)).click();
	}
	
	public boolean isHomePageDisplayed() {
		return getWait().until(ExpectedConditions.visibilityOf(homePageDisplayed)).isDisplayed();
	}
	
	public String getHomePageElement() {
		return getWait().until(ExpectedConditions.visibilityOf(homePageDisplayed)).getText();
	}
	
	public void clickForgotLogin() {
		getWait().until(ExpectedConditions.elementToBeClickable(forgotLogin)).click();
	}
	

}
