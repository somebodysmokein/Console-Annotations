package com.browserstack.test.tests;

import com.browserstack.test.consoleannotations.Selenium4Issue;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Locale;

//import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class GoogleSrch{

        public static String REMOTE_URL = "https://venkateshr_0PiUN7:ADZqq5cND8dihy2RJAZD@hub-cloud.browserstack.com/wd/hub";
//  public static String REMOTE_URL = "http://localhost:4444/wd/hub";

        // marking the tests as passed or failed in BrowserStack
        public static void markTestStatus(String status, String reason, WebDriver driver) {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \""+status+"\", \"reason\": \""+reason+"\"}}");
        }

        @Test(dataProvider="testdata")
        public void testGoogleSrch(String srchStr) throws MalformedURLException {

            Selenium4Issue mainObj=new Selenium4Issue();

            //FirefoxOptions optionsFF=new FirefoxOptions();
            //optionsFF.addArguments("--force-dark-mode");

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--force-dark-mode");

            DesiredCapabilities capabilities = new DesiredCapabilities();

            // Setting the browser capability
            capabilities.setCapability("browser", "Chrome");
            capabilities.setCapability("browser_version", "latest");
            capabilities.setCapability(ChromeOptions.CAPABILITY,options);
            HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();

            // Setting the OS or device version capabilities
            browserstackOptions.put("os", "Windows");
            //browserstackOptions.put("osVersion", "10");

            // Setting a build name for the test
            browserstackOptions.put("buildName", "BStack-[Java] Selenium 4 Sample Test");

            // Setting a session name for the test
            browserstackOptions.put("sessionName", "Selenium 4 test");

            // Setting the Selenium version
            //browserstackOptions.put("seleniumVersion", "4.1.1");
            capabilities.setCapability("bstack:options", browserstackOptions);

            WebDriver driver = new RemoteWebDriver(new URL(REMOTE_URL), capabilities);
            //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(31));

            try {
                driver.get("https://www.google.com");
                WebElement searchField = driver.findElement(By.name("q"));
                //WebElement searchField = driver.findElement(By.name("q1"));
                searchField.sendKeys(srchStr);
                WebElement searchButton = driver.findElement(By.name("btnK"));

                // Using relative locators of Selenium 4 to locate a button
                //WebElement feelingLucky = driver.findElement(with(By.name("btnI")).toRightOf(searchButton));
                WebElement feelingLucky = driver.findElement(By.name("btnI"));

                // Using Action class of Selenium 4
                Actions actions = new Actions(driver);
                actions.moveToElement(feelingLucky).click().build().perform();

                // Setting the status of test as 'passed' or 'failed' based on the condition; if URL of the web page contains 'browserstack'
                //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                try {
                    wait.until(ExpectedConditions.urlContains(srchStr.toLowerCase(Locale.ROOT)));
                    markTestStatus("passed","URL contains "+srchStr+"!",driver);
                }
                catch(Exception e) {
                    markTestStatus("failed","URL does not contain "+srchStr,driver);
                }
                System.out.println(driver.getCurrentUrl());
                driver.quit();
            } catch (Exception e) {
                markTestStatus("failed", "Exception!", driver);
                e.printStackTrace();
                driver.quit();
            }
        }

    @DataProvider(name="testdata")
    public Object[][] TestDataFeed(){

// Create object array with 2 rows and 2 column- first parameter is row and second is //column
        Object [][] srchdata=new Object[2][1];

// Enter data to row 0 column 0
        srchdata[0][0]="BrowserStack";
// Enter data to row 1 column 0
        srchdata[1][0]="Google";
        return srchdata;
    }
}
