package com.parasoft.pageobjects;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.parasoft.webdriverutility.DriverManager;

public class TransferFunds {
	
	public TransferFunds() {
		PageFactory.initElements(DriverManager.getDriver(), this);
	}
	
	@FindBy(xpath="//a[text()='Transfer Funds']")
	private WebElement transferFundsLink;
	
	@FindBy(id="amount")
	private WebElement amount;
	
	@FindBy(id="fromAccountId")
	private WebElement fromAccount;
	
	@FindBy(id="toAccountId")
	private WebElement toAccount;
	
	@FindBy(xpath="//input[@value='Transfer']")
	private WebElement transferButton;
	
	@FindBy(xpath="//div[@id='showResult']/p[1]")
	private WebElement verifyTransaction;
	
	private WebDriverWait getWait() {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
    }
	
	public void clickTransferFundsLink() {
		getWait().until(ExpectedConditions.elementToBeClickable(transferFundsLink)).click();
	}
	
	public void enterAmount(String money) {
		getWait().until(ExpectedConditions.visibilityOf(amount)).sendKeys(money);
	}
	
	public void selectFromAccount(int accountNumber) {
		Select select = new Select(fromAccount);
		select.selectByIndex(accountNumber);
	}
	
	public void selectToAccount(int accountNumber) {
		Select select = new Select(toAccount);
		select.selectByIndex(accountNumber);
	}
	
	public void clickTransferButton() {
		getWait().until(ExpectedConditions.elementToBeClickable(transferButton)).click();
	}
	
	public boolean isTransactionMessageDisplayed() {
		return getWait().until(ExpectedConditions.visibilityOf(verifyTransaction)).isDisplayed();
	}
	
	public String getTransactionMessage() {
		return getWait().until(ExpectedConditions.visibilityOf(verifyTransaction)).getText();
	}

}
