package com.parasoft.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.parasoft.utilities.BaseUtils;
import com.parasoft.pageobjects.BillPay;
import com.parasoft.pageobjects.LoginPage;
import com.parasoft.testdata.BillPayData;

public class BillPayPageTests extends BaseTest {
	
	private LoginPage loginPage;
    private BillPay billPay;
    
    @BeforeMethod(alwaysRun = true)
    public void initPage() {
    	loginPage = new LoginPage();
    	billPay = new BillPay();
    }
    
    private void loginWithFirstRow() {
		List<Map<String, String>> rows = BaseUtils.readExcel("src/test/resources/TestData/ParaBank.xlsx", "Login");
		Map<String, String> data = rows.get(0);
		
		loginPage.enterUserName(data.get("username"));
        loginPage.enterPassword(data.get("password"));
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isHomePageDisplayed(), "Login failed in helper");
	}

    @DataProvider(name = "paymentDetails")
    public Object[][] getPaymentDetails() {
        List<Map<String, String>> testData = BaseUtils.readExcel("src/test/resources/TestData/ParaBank.xlsx", "BillPay");
        Assert.assertFalse(testData.isEmpty(), "BillPay sheet is empty or missing");
        return testData.stream()
                       .map(map -> new Object[] { new BillPayData(map) })
                       .toArray(Object[][]::new);
    }
	
	@Test(description = "User pays a bill", dataProvider = "paymentDetails")
	public void billPayment(BillPayData data) {
		
		loginWithFirstRow();

		billPay.clickBillPay();
		
		billPay.enterPayeeName(data.getPayeeName());
		billPay.enterAddress(data.getAddress());
		billPay.enterCity(data.getCity());
		billPay.enterState(data.getState());
		billPay.enterZipCode(data.getZipCode());
		billPay.enterPhone(data.getPhone());
		billPay.enterAccountNumber(data.getAccountNumber());
		billPay.repeatAccountNumber(data.getVerifyAccountNumber());
		billPay.enterAmount(data.getAmount());
		billPay.chooseAccount(0);
		billPay.clickSendPaymentButton();
		
		Assert.assertTrue(billPay.isPaymentDetailsDisplayed(), "Payment was not Successfull");
		
		System.out.println(billPay.getPaymentDetails());
		
	}

}
