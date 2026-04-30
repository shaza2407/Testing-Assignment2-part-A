package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import configuration.ConfigReader;

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
    public void teardown() {
        driver.quit();
    }
}