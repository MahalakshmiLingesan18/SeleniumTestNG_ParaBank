package com.parasoft.pageobjects;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.parasoft.webdriverutility.DriverManager;

public class RegisterPage {
	
	public RegisterPage() {
		PageFactory.initElements(DriverManager.getDriver(), this);
	}
	
	@FindBy(xpath="//a[text()='Register']")
	private WebElement register;
	
	@FindBy(id="customer.firstName")
	private WebElement firstNameBox;
	
	@FindBy(id="customer.lastName")
	private WebElement lastNameBox;
	
	@FindBy(id="customer.address.street")
	private WebElement addressBox;
	
	@FindBy(id="customer.address.city")
	private WebElement cityBox;
	
	@FindBy(id="customer.address.state")
	private WebElement stateBox;
	
	@FindBy(id="customer.address.zipCode")
	private WebElement zipCodeBox;
	
	@FindBy(id="customer.phoneNumber")
	private WebElement phoneBox;
	
	@FindBy(id="customer.ssn")
	private WebElement ssnBox;
	
	@FindBy(id="customer.username")
	private WebElement usernameBox;
	
	@FindBy(id="customer.password")
	private WebElement passwordBox;
	
	@FindBy(id="repeatedPassword")
	private WebElement confirmPasswordBox;
	
	@FindBy(xpath="//input[@value='Register']")
	private WebElement registerButton;
	
	@FindBy(xpath="//div[@id='rightPanel']/p")
	private WebElement verifyRegistration;
	
	private WebDriverWait getWait() {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
    }
	
	public void clickRegisterLink() {
		getWait().until(ExpectedConditions.elementToBeClickable(register)).click();
	}
	
	public void enterFirstName(String fName) {
		getWait().until(ExpectedConditions.visibilityOf(firstNameBox)).sendKeys(fName);
	}
	
	public void enterLastName(String lName) {
		getWait().until(ExpectedConditions.visibilityOf(lastNameBox)).sendKeys(lName);
	}
	
	public void enterAddress(String address) {
		getWait().until(ExpectedConditions.visibilityOf(addressBox)).sendKeys(address);
	}
	
	public void enterCity(String city) {
		getWait().until(ExpectedConditions.visibilityOf(cityBox)).sendKeys(city);
	}
	
	public void enterState(String state) {
		getWait().until(ExpectedConditions.visibilityOf(stateBox)).sendKeys(state);
	}
	
	public void enterZipCode(String zipCode) {
		getWait().until(ExpectedConditions.visibilityOf(zipCodeBox)).sendKeys(zipCode);
	}
	
	public void enterPhone(String phoneNumber) {
		getWait().until(ExpectedConditions.visibilityOf(phoneBox)).sendKeys(phoneNumber);
	}
	
	public void enterSSN(String ssn) {
		getWait().until(ExpectedConditions.visibilityOf(ssnBox)).sendKeys(ssn);
	}
	
	public void enterUsername(String user) {
		getWait().until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(user);
	}
	
	public void enterPassword(String pass) {
		getWait().until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(pass);
	}
	
	public void reEnterPassword(String confirmPass) {
		getWait().until(ExpectedConditions.visibilityOf(confirmPasswordBox)).sendKeys(confirmPass);
	}
	
	public void clickRegisterButton() {
		getWait().until(ExpectedConditions.elementToBeClickable(registerButton)).click();
	}
	
	public boolean isRegistrationMessageDisplayed() {
		return getWait().until(ExpectedConditions.visibilityOf(verifyRegistration)).isDisplayed();
	}
	
	public String getRegistrationMessage() {
		return getWait().until(ExpectedConditions.visibilityOf(verifyRegistration)).getText();
	}

}
