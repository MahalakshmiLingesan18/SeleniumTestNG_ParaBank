package com.parasoft.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class BaseUtils {
	
	//To read a list of elements 
	public static List<Map<String, String>> readExcel(String filePath, String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            Workbook workbook = WorkbookFactory.create(fis);
            
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                System.out.println("DEBUG: sheet '" + sheetName + "' not found in " + filePath);
                return data;
            }
            
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                System.out.println("DEBUG: header row not found in sheet '" + sheetName + "'");
                return data;
            }
            
            DataFormatter formatter = new DataFormatter();

            // collect headers (use formatter to preserve any formatting)
            List<String> headers = new ArrayList<>();
            for (int c = 0; c < headerRow.getLastCellNum(); c++) {
                Cell headerCell = headerRow.getCell(c, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                String header = headerCell == null ? ("Column" + c) : formatter.formatCellValue(headerCell).trim();
                headers.add(header);
            }

            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row currentRow = sheet.getRow(r);
                if (currentRow == null) continue;

                Map<String, String> rowData = new LinkedHashMap<>(); // keep column order
                boolean anyNonEmpty = false;
                for (int c = 0; c < headers.size(); c++) {
                    Cell cell = currentRow.getCell(c, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    String value = formatter.formatCellValue(cell).trim(); // always returns a string
                    if (!value.isEmpty()) anyNonEmpty = true;
                    rowData.put(headers.get(c), value);
                }
                
             // skip completely empty rows
                if (anyNonEmpty) {
                    data.add(rowData);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
	
	//To read each element from a row and column
	public static String excelRead(String filePath, String sheetName, int rowIndex, int colIndex) {
	    try (FileInputStream is = new FileInputStream(new File(filePath));
	         Workbook workbook = new XSSFWorkbook(is)) {

	        Sheet sheet = workbook.getSheet(sheetName);
	        if (sheet == null) {
	            System.out.println("DEBUG: Sheet '" + sheetName + "' not found in " + filePath);
	            return "";
	        }

	        Row row = sheet.getRow(rowIndex);
	        if (row == null) {
	            System.out.println("DEBUG: Row " + rowIndex + " not found in sheet '" + sheetName + "'");
	            return "";
	        }

	        Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
	        if (cell == null) {
	            System.out.println("DEBUG: Cell at row " + rowIndex + ", col " + colIndex + " is empty/null");
	            return "";
	        }

	        // Normalize cell value to string
	        DataFormatter formatter = new DataFormatter();
	        String value = formatter.formatCellValue(cell);
	        return value == null ? "" : value.trim();

	    } catch (Exception e) {
	        System.out.println("ERROR reading Excel: " + e.getMessage());
	        return "";
	    }
	}
	
	public static String getProperty(String key) {
		
		Properties properties = new Properties();
		
		try {
            FileInputStream fis = new FileInputStream("src/main/resources/PropertiesFile/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage());
        }
        return properties.getProperty(key);
    }
	
	public static String captureScreenshot(WebDriver driver) {
        String screenshotDir = "target/screenshots/";
        new File(screenshotDir).mkdirs();
        
        Date d = new Date();
        String screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

        String filePath = screenshotDir + screenshotName;

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
        	
        	Files.copy(srcFile.toPath(), new File(filePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }
	
	public static void cleanScreenshotsFolder() {
	    String screenshotsDir = System.getProperty("user.dir") + File.separator + "target" + File.separator + "screenshots";
	    File dir = new File(screenshotsDir);
	    if (dir.exists()) {
	        File[] files = dir.listFiles();
	        if (files != null) {
	            for (File f : files) {
	                f.delete();
	            }
	        }
	    }
	}
}
