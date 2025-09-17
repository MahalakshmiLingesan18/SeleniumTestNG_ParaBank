package com.parasoft.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.parasoft.pageobjects.LoginPage;
import com.parasoft.pageobjects.TransferFunds;
import com.parasoft.testdata.TransferFundsData;
import com.parasoft.utilities.BaseUtils;

public class TransferFundsTests  extends BaseTest {
	
	private LoginPage loginPage;
	private TransferFunds transferFunds;
	
	@BeforeMethod(alwaysRun = true)
    public void initPage() {
		loginPage = new LoginPage();
		transferFunds = new TransferFunds();
    }
	
	private void loginWithFirstRow() {
		List<Map<String, String>> rows = BaseUtils.readExcel("src/test/resources/TestData/ParaBank.xlsx", "Login");
		Map<String, String> data = rows.get(0);
		
		loginPage.enterUserName(data.get("username"));
        loginPage.enterPassword(data.get("password"));
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isHomePageDisplayed(), "Login failed in helper");
	}
    
    @DataProvider(name = "transferFundsData")
    public Object[][] getTransferFundsData() {
        List<Map<String, String>> testData = BaseUtils.readExcel("src/test/resources/TestData/ParaBank.xlsx", "TransferFunds");
        Assert.assertFalse(testData.isEmpty(), "TransferFunds sheet is empty or missing");
        return testData.stream()
                       .map(map -> new Object[] { new TransferFundsData(map) })
                       .toArray(Object[][]::new);
    }
	
	@Test(description = "Transferring Funds from one account to another", dataProvider = "transferFundsData")
	public void transferFund(TransferFundsData data) {
		
		loginWithFirstRow();
		
		transferFunds.clickTransferFundsLink();
		
		transferFunds.enterAmount(data.getAccount());
		transferFunds.selectFromAccount(1);
		transferFunds.selectToAccount(0);
		transferFunds.clickTransferButton();
		
		Assert.assertTrue(transferFunds.isTransactionMessageDisplayed(), "Transaction failed");
		
		System.out.println(transferFunds.getTransactionMessage());
		
	}

}
