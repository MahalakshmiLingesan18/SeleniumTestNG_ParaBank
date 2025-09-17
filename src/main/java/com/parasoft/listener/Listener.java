package com.parasoft.listener;

import java.io.File;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.parasoft.reports.ExtentReport;
import com.parasoft.utilities.BaseUtils;
import com.parasoft.webdriverutility.DriverManager;

public class Listener implements ITestListener, ISuiteListener {
	
    private static final Logger logger = LogManager.getLogger(Listener.class);
    
    private static ThreadLocal<ExtentTest> testReport = new ThreadLocal<>();
    
    public static ExtentTest test;
    
    @Override
	public void onStart(ISuite suite) { 	
    	String fileName = System.getProperty("user.dir") + File.separator + "target" + File.separator + "ExtentReport.html";
    	File reportFile = new File(fileName);
        if (reportFile.exists()) {
            boolean deleted = reportFile.delete();
            if (!deleted) {
                logger.warn("Could not delete existing report file: " + fileName);
            } else {
                logger.info("Deleted previous report file: " + fileName);
            }
        }
		ExtentReport.initReports(fileName);
	}
    
    @Override
	public void onFinish(ISuite suite) {
		ExtentReport.flushReports();	
	}
    
    @Override
    public void onTestStart(ITestResult result) {
        test = ExtentReport.createTest(result.getTestClass().getName() + " @TestCase: " + result.getMethod().getDescription());
        testReport.set(test);  
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {  	
    	String methodName = result.getMethod().getMethodName();
        String logText = "<b>" + "TEST CASE: " + methodName.toUpperCase() + " - PASSED" + "</b>";      
        Markup markup = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
        testReport.get().log(Status.PASS, markup);
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
    	String methodName = result.getMethod().getMethodName();
        String logText = "<b>" + "TEST CASE: " + methodName.toUpperCase() + " - FAILED" + "</b>";  
        Markup markup = MarkupHelper.createLabel(logText, ExtentColor.RED);
        testReport.get().log(Status.FAIL, markup);
        
        String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
        testReport.get().fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured: Click to see"
                + "</font>" + "</b >" + "</summary>" +exceptionMessage.replaceAll(",", "<br>")+"</details>"+" \n");
         
        try {
        	String screenshotPath = BaseUtils.captureScreenshot(DriverManager.getDriver());
            testReport.get().fail("<b>" + "<font color=" + "red>" + "Screenshot" + "</font>" + "</b>", 
            		MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (Exception e) {
        	logger.error("Failed to capture screenshot on test failure", e);
            testReport.get().fail("Screenshot capture failed: " + e.getMessage());
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String logText = "<b>" + "TEST CASE: " + methodName.toUpperCase() + " - SKIPPED" + "</b>";     
        Markup markup = MarkupHelper.createLabel(logText, ExtentColor.ORANGE);
        testReport.get().log(Status.SKIP, markup);
    }
    
    @Override
    public void onStart(ITestContext context) {
    	logger.info("Starting Test Suite: " + context.getName());  //Optional: As we just have one test in our suite
    	BaseUtils.cleanScreenshotsFolder();
    }
    
    @Override
	public void onFinish(ITestContext context) {
    	logger.info("Finished Test Suite: " + context.getName());
    	
    	int passed = context.getPassedTests().getAllResults().size();
        int failed = context.getFailedTests().getAllResults().size();
        int skipped = context.getSkippedTests().getAllResults().size();
        int total = passed + failed + skipped;
        
        logger.info("Test Summary — Total: " + total + ", Passed: " + passed + ", Failed: " + failed + ", Skipped: " + skipped);
        
        try {
            testReport.remove();
        } catch (Exception e) {
            logger.warn("Could not remove ThreadLocal testReport", e);
        }
        
	}
    
}
