package com.parasoft.reports;

import java.util.Objects;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public final class ExtentReport {
	
	/**
	 * Private constructor to avoid external instantiation
	 */
	private ExtentReport() {
	}
	
	private static ExtentReports extent;
	
	public static void initReports(String fileName) {
		if(Objects.isNull(extent)) {
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter (fileName);
			sparkReporter.config().setDocumentTitle(fileName);
			sparkReporter.config().setReportName(fileName);
			
			extent = new ExtentReports();
	        extent.attachReporter(sparkReporter);
	        /*extent.setSystemInfo("Release No", "22");
	        extent.setSystemInfo("Environment", "QA");
	        extent.setSystemInfo("Build no", "B-12673");*/
		}
	}
	
	public static ExtentTest createTest(String testcasename) {
		return extent.createTest(testcasename);
	}
	
	public static void flushReports(){
		if(Objects.nonNull(extent)) {
			extent.flush();
		}
	}

}
