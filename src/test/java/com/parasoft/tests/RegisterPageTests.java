package com.parasoft.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.parasoft.pageobjects.RegisterPage;
import com.parasoft.testdata.RegisterData;
import com.parasoft.utilities.BaseUtils;

public class RegisterPageTests extends BaseTest {
	
    private RegisterPage registerPage;
    
    @BeforeMethod(alwaysRun = true)
    public void initPage() {
    	registerPage = new RegisterPage();
    }
    
    @DataProvider(name = "registrationData")
    public Object[][] getRegistrationData() {
        List<Map<String, String>> testData = BaseUtils.readExcel("src/test/resources/TestData/ParaBank.xlsx", "Register");
        return testData.stream()
                       .map(map -> new Object[] { new RegisterData(map) })
                       .toArray(Object[][]::new);
    }
	
	@Test(description = "New user registers for online banking", dataProvider = "registrationData")
	public void accountCreation(RegisterData data) {
		
		registerPage.clickRegisterLink();
		
	    registerPage.enterFirstName(data.getFirstName());
	    registerPage.enterLastName(data.getLastName());
	    registerPage.enterAddress(data.getAddress());
	    registerPage.enterCity(data.getCity());
	    registerPage.enterState(data.getState());
	    registerPage.enterZipCode(data.getZipCode());
	    registerPage.enterPhone(data.getPhone());
	    registerPage.enterSSN(data.getSsn());
	    registerPage.enterUsername(data.getUsername());
	    registerPage.enterPassword(data.getPassword());
	    registerPage.reEnterPassword(data.getConfirmPassword());
	    registerPage.clickRegisterButton();
	    
        Assert.assertTrue(registerPage.isRegistrationMessageDisplayed(), "New user is not registered");
        
        System.out.println(registerPage.getRegistrationMessage());
	}

}
