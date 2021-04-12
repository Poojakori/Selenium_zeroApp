package com.company;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReusableMethods {
    protected static WebDriver driver;
    protected static ExtentReports reports;
    protected static ExtentTest logger;
    static  String reportFolder ="C:\\Users\\ekpoo\\Desktop\\TekArch\\Selenium\\Reports\\";

    @BeforeMethod
    @Parameters("driverName")
    public static void InitializeDriver(String driverName) {
        if(driverName=="chrome") {
            System.setProperty("webdriver.chrome.driver", "C:\\\\Users\\\\ekpoo\\\\Desktop\\\\TekArch\\\\Selenium\\\\DriverLinks\\\\chromedriver_win32\\\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
        else if(driverName=="firefox"){
            System.setProperty("webdriver.gecko.driver", "C:\\\\Users\\\\ekpoo\\\\Desktop\\\\TekArch\\\\Selenium\\\\DriverLinks\\\\geckodriver-v0.26.0-win64\\\\geckodriver.exe");
            WebDriver driver = new FirefoxDriver();
            driver.manage().window().maximize();
        }
        else{
            System.setProperty("webdriver.ie.driver", "C:\\\\Users\\\\ekpoo\\\\Desktop\\\\TekArch\\\\Selenium\\\\DriverLinks\\\\IEDriverServer_x64_2.41.0\\\\IEDriverServer.exe");
            WebDriver driver = new InternetExplorerDriver();
            driver.manage().window().maximize();
        }
    }

    public static void enterText(WebElement ele, String txtValue, String objectName) throws IOException {

        if (ele.isDisplayed()) {
            ele.clear();
            ele.sendKeys(txtValue);
            System.out.println(txtValue + "has been successfully Entered  into " + objectName);
            logger.log(LogStatus.INFO, objectName+" is entered successfully");
        } else {
            System.out.println(objectName + " is not displayed on the web page.");
            logger.log(LogStatus.ERROR,objectName + " is not entered in the web page." + logger.addScreenCapture(takeScreenshot()));
        }
    }


    public static void switchFrame(WebElement ele, String objectName){
        if (ele.isDisplayed()) {
            driver.switchTo().frame(ele);
            System.out.println(objectName + "has been successfully switched. ");

        } else {
            System.out.println(objectName + " is not displayed on the web page.");
        }

    }


    public static void clickElement(WebElement ele, String ObjectName) throws IOException {
        if(ele.isDisplayed())
        {
            ele.click();;
            System.out.println(ObjectName + " has been successfully clicked");
            logger.log(LogStatus.INFO, ObjectName+" has been successfully clicked.");

        }
        else
        {
            System.out.println(ObjectName + " is not displayed in the web page.");
            logger.log(LogStatus.ERROR,ObjectName + " is not clicked." +
                    logger.addScreenCapture(takeScreenshot()));
        }
    }
    public static void InitializeReport(){
        reports = new ExtentReports(reportFolder + new SimpleDateFormat("'xeroReport_'YYYYMMddHHmm'.html'").
                format(new Date()));

    }
    public static String takeScreenshot() throws IOException {
        TakesScreenshot srcShot = ((TakesScreenshot) driver);
        File srcFile = srcShot.getScreenshotAs(OutputType.FILE);
        String imagePath = reportFolder + "ScreenShots\\" + new SimpleDateFormat("'Image_'YYYYMMddHHmm'.png'").format(new Date());
        File destFile = new File(imagePath);
        FileUtils.copyFile(srcFile,destFile);
        return imagePath;
    }
    public static String[][] readXlData(String path, String sheetName) throws IOException {
        FileInputStream fs=new FileInputStream(new File(path));
        HSSFWorkbook wb=new HSSFWorkbook(fs);
        HSSFSheet sheet=wb.getSheet(sheetName);
        int rowCount=sheet.getLastRowNum()+1;
        int colCount=sheet.getRow(0).getLastCellNum();
        String[][] data=new String[rowCount][colCount];
        for(int i=0;i<rowCount;i++){
            for(int j=0;j<colCount;j++){
                int cellType=sheet.getRow(i).getCell(j).getCellType();
                if(cellType== HSSFCell.CELL_TYPE_NUMERIC){
                    int val=(int)sheet.getRow(i).getCell(j).getNumericCellValue();
                    data[i][j]=String.valueOf(val);
                }
                else
                    data[i][j]=sheet.getRow(i).getCell(j).getStringCellValue();
            }
        }
        return (data);
    }


    public static void checkPresence(Boolean ele,String str) throws IOException {
        if(ele==true)
            logger.log(LogStatus.PASS, str+" is Present, Test case successfully completed.");
        else {
            logger.log(LogStatus.FAIL, str + " is not present, Test is failed.");
            logger.log(LogStatus.ERROR, str + " is not displayed in the web page." +
                    logger.addScreenCapture(takeScreenshot()));
        }
    }

    public static void checkBox(WebElement ele, String txtValue) throws IOException {

        if (ele.isSelected()) {
            System.out.println(txtValue + "Is already selected.");
            logger.log(LogStatus.INFO, txtValue+" is selected successfully");
        } else {
            ele.click();
            System.out.println(txtValue + " is selected.");
            logger.log(LogStatus.INFO, txtValue+" is selected successfully");
        }
    }

    public static void switchWindaow(){
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
    }



}
