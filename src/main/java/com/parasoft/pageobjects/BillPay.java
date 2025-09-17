package com.parasoft.pageobjects;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.parasoft.webdriverutility.DriverManager;

public class BillPay {
	
	public BillPay() {
		PageFactory.initElements(DriverManager.getDriver(), this);
	}
	
	@FindBy(xpath="//a[text()='Bill Pay']")
	private WebElement billPayLink;
	
	@FindBy(name="payee.name")
	private WebElement payeeNameBox;
	
	@FindBy(name="payee.address.street")
	private WebElement addressBox;
	
	@FindBy(name="payee.address.city")
	private WebElement cityBox;
	
	@FindBy(name="payee.address.state")
	private WebElement stateBox;
	
	@FindBy(name="payee.address.zipCode")
	private WebElement zipCodeBox;
	
	@FindBy(name="payee.phoneNumber")
	private WebElement phoneBox;
	
	@FindBy(name="payee.accountNumber")
	private WebElement accountBox;
	
	@FindBy(name="verifyAccount")
	private WebElement verifyAccountBox;
	
	@FindBy(name="amount")
	private WebElement amountBox;
	
	@FindBy(name="fromAccountId")
	private WebElement fromAccountBox;
	
	@FindBy(xpath="//input[@value='Send Payment']")
	private WebElement sendPayment;
	
	@FindBy(xpath="//div[@id='billpayResult']/p")
	private WebElement verifyPayment;
	
	private WebDriverWait getWait() {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
    }
    
    public void clickBillPay() {
    	getWait().until(ExpectedConditions.elementToBeClickable(billPayLink)).click();
	}
	
	public void enterPayeeName(String payee) {
		getWait().until(ExpectedConditions.visibilityOf(payeeNameBox)).sendKeys(payee);
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
	
	public void enterAccountNumber(String accountNumber) {
		getWait().until(ExpectedConditions.visibilityOf(accountBox)).sendKeys(accountNumber);
	}
	
	public void repeatAccountNumber(String repeatAccountNumber) {
		getWait().until(ExpectedConditions.visibilityOf(verifyAccountBox)).sendKeys(repeatAccountNumber);
	}
	
	public void enterAmount(String amount) {
		getWait().until(ExpectedConditions.visibilityOf(amountBox)).sendKeys(amount);
	}
	
	public void chooseAccount(int fromAccount) {
		Select select = new Select(fromAccountBox);
		select.selectByIndex(fromAccount);
	}
	
	public void clickSendPaymentButton() {
		getWait().until(ExpectedConditions.elementToBeClickable(sendPayment)).click();
	}
	
	public boolean isPaymentDetailsDisplayed() {
		return getWait().until(ExpectedConditions.visibilityOf(verifyPayment)).isDisplayed();
	}
	
	public String getPaymentDetails() {
		return getWait().until(ExpectedConditions.visibilityOf(verifyPayment)).getText();
	}

}
