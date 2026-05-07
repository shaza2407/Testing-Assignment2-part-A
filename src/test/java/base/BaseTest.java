package base;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import configuration.ConfigReader;

import java.io.ByteArrayInputStream;

public class BaseTest {
    protected WebDriver driver;
    ConfigReader config = new ConfigReader();


    @BeforeMethod
    public void setup() {

         System.setProperty("chromeBrowser","chromeexe");     //chrome macOS driver
         driver = new ChromeDriver();

//        System.setProperty("edgeBrowser","edgeexe");     //edge window driver
//        driver = new EdgeDriver();

        driver.manage().window().maximize();    //maximize window
        driver.get(config.get("url"));      //open site using the URL
    }
    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE || result.getStatus() == ITestResult.SKIP) {
            Allure.addAttachment(result.getName() + " - Screenshot", "image/png", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)), "png");
        }
        if (driver != null) {
            driver.quit();
        }
    }
}