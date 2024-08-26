package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import demo.utils.ExcelDataProvider;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;
import org.testng.Assert;
import java.time.Duration;
import java.util.logging.Level;
import demo.wrappers.Wrappers;




public class TestCases extends ExcelDataProvider{ // Lets us read the data
        ChromeDriver driver;

        Wrappers wrappers;
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");
                

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        @BeforeMethod
        public void setUp() throws InterruptedException {
            wrappers = new Wrappers(driver);
            wrappers.navigateToYoutube();
            Thread.sleep(2000);
        }

        @Test(description = "Verify the functionality of testCase01" , enabled = true)
        public void testCase01() throws InterruptedException{
                wrappers.clickOnAbout();
                wrappers.printMessage();
        }

        @Test(description = "verify functionality of testCase02", enabled = true)
        public void testCase02() throws InterruptedException{
                wrappers.selectTab("Movies");
                wrappers.clickOnNextBtn();
                wrappers.verifyRatings();
                wrappers.verifyCategoryExists();
                Thread.sleep(3000);
                wrappers.s.assertAll(); 
        }

        @Test(description = "verify the functionality of testCase03", enabled = true)
        public void testCase03() throws InterruptedException{
                wrappers.selectTab("Music");
                wrappers.clickNextBtnInMusic();
                wrappers.printPlaylistName();
                Thread.sleep(3000);
                wrappers.s.assertAll();
        }

        @Test(description = "verify the functionality of testCase04", enabled = true)
        public void testCase04() throws InterruptedException{
                wrappers.selectTab("News");
                Thread.sleep(2000);
                wrappers.printNews();
                Thread.sleep(2000);        
        }

        @Test(description = "Verify functionality of tescase05", dataProvider = "excelData", dataProviderClass = ExcelDataProvider.class, enabled = true)
        public void testCase05( String item) throws InterruptedException{
                wrappers.searchItem(item);
                Thread.sleep(2000);
                wrappers.verifyTotalViews();
                Thread.sleep(2000);
        }

        @AfterTest
        public void endTest() {
               // driver.close();
                driver.quit();
        }
}