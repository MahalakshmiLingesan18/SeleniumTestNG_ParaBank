package com.parasoft.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.parasoft.pageobjects.LoginPage;
import com.parasoft.pageobjects.OpenNewAccount;
import com.parasoft.utilities.BaseUtils;

public class OpenNewAccountPageTests extends BaseTest {
	
    private LoginPage loginPage;
    private OpenNewAccount openNewAccount;
	
	@BeforeMethod(alwaysRun = true)
    public void initPage() {
        loginPage = new LoginPage();
        openNewAccount = new OpenNewAccount();
    }
	
	private void loginWithFirstRow() {
		List<Map<String, String>> rows = BaseUtils.readExcel("src/test/resources/TestData/ParaBank.xlsx", "Login");
		Map<String, String> data = rows.get(0);
		
		loginPage.enterUserName(data.get("username"));
        loginPage.enterPassword(data.get("password"));
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isHomePageDisplayed(), "Login failed in helper");
	}
	
	@Test(description = "User creates a new account")
	public void newAccountCreation() {
		
		loginWithFirstRow();
		
		openNewAccount.clickopenNewAccountLink();
		openNewAccount.selectAccountType("SAVINGS");
		openNewAccount.selectExistingAccount(1);
		openNewAccount.clickopenNewAccountButton();
		
		Assert.assertTrue(openNewAccount.accountNumberDisplayed(), "New Account is not created");
		
		if (openNewAccount.accountNumberDisplayed()==true) {
			String accountNumber = openNewAccount.accountNumber();
			Assert.assertNotNull(accountNumber, "Account number is null!");
			Assert.assertFalse(accountNumber.isBlank(), "Account number is empty!");
			System.out.println(accountNumber);
		}
	}

}
