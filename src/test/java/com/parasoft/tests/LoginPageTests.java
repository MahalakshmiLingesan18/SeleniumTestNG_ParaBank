package com.parasoft.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.parasoft.utilities.BaseUtils;
import com.parasoft.pageobjects.LoginPage;
import com.parasoft.testdata.LoginData;

public class LoginPageTests extends BaseTest {
	
	private LoginPage loginPage;
	
	@BeforeMethod(alwaysRun = true)
    public void initPage() {
        loginPage = new LoginPage();
    }
	
	@DataProvider(name = "loginData")
	public Object[][] loginData() {
	    List<Map<String, String>> rows = BaseUtils.readExcel("src/test/resources/TestData/ParaBank.xlsx", "Login");
	    // convert to Object[][]
	    return rows.stream()
	               .map(map -> new Object[] { new LoginData(map) })
	               .toArray(Object[][]::new);
	}
	
	@Test(description = "User enters the valid credentials for login", dataProvider = "loginData")
	public void validLogin(LoginData data) {
        
		loginPage.enterUserName(data.getUsername());
		loginPage.enterPassword(data.getPassword());
		loginPage.clickLogin();
		
		Assert.assertTrue(loginPage.isHomePageDisplayed(), "User is not on Home page!");
		
		System.out.println(loginPage.getHomePageElement());
	}

}
