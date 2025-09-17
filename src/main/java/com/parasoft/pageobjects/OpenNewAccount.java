package com.parasoft.pageobjects;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.parasoft.webdriverutility.DriverManager;

public class OpenNewAccount {
	
	public OpenNewAccount() {
		PageFactory.initElements(DriverManager.getDriver(), this);
	}
	
	@FindBy(xpath="//a[text()='Open New Account']")
	private WebElement openNewAccountLink;
	
	@FindBy(xpath="//select[@id='type']")
	private WebElement accountType;
	
	@FindBy(xpath="//select[@id='fromAccountId']")
	private WebElement chooseExistingAccount;
	
	@FindBy(xpath="//input[@value='Open New Account']")
	private WebElement openNewAccountButton;
	
	@FindBy(xpath="//a[@id='newAccountId']")
	private WebElement newAccountNumber;
    
    private WebDriverWait getWait() {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
    }
	
    public void clickopenNewAccountLink() {
    	getWait().until(ExpectedConditions.elementToBeClickable(openNewAccountLink)).click();
	}
    
	public void selectAccountType(String string) {
		Select select = new Select(accountType);
		select.selectByVisibleText(string);
	}
	
	public void selectExistingAccount(int index) {
		Select select = new Select(chooseExistingAccount);
		select.selectByIndex(index);
	}
	
	public void clickopenNewAccountButton() {
		getWait().until(ExpectedConditions.elementToBeClickable(openNewAccountButton)).click();
	}
	
	public String accountNumber() {
		return getWait().until(ExpectedConditions.visibilityOf(newAccountNumber)).getText();
	}
	
	public boolean accountNumberDisplayed() {
		return getWait().until(ExpectedConditions.visibilityOf(newAccountNumber)).isDisplayed();
	}

}
