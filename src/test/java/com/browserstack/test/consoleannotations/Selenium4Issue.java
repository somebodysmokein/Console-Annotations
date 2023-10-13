package com.browserstack.test.consoleannotations;


import java.net.MalformedURLException;
        import java.net.URL;
        import java.util.HashMap;
        import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
        import org.openqa.selenium.remote.DesiredCapabilities;
        import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
        import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

//import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class Selenium4Issue {

    public static String REMOTE_URL = "https://venkateshr_0PiUN7:ADZqq5cND8dihy2RJAZD@hub-cloud.browserstack.com/wd/hub";
//  public static String REMOTE_URL = "http://localhost:4444/wd/hub";

    public static void main(String[] args) throws MalformedURLException {

        Selenium4Issue mainObj=new Selenium4Issue();
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // Setting the browser capability
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("browserVersion", "100.0");

        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();

        // Setting the OS or device version capabilities
        browserstackOptions.put("os", "Windows");
        browserstackOptions.put("osVersion", "10");

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
            WebElement searchField = mainObj.expandRootElement(By.name("q"),driver);
            //WebElement searchField = driver.findElement(By.name("q1"));
            searchField.sendKeys("BrowserStack");
            WebElement searchButton = driver.findElement(By.name("btnK"));

            // Using relative locators of Selenium 4 to locate a button
            //WebElement feelingLucky = driver.findElement(with(By.name("btnI")).toRightOf(searchButton));
            WebElement feelingLucky = driver.findElement(By.name("btnI"));

            // Using Action class of Selenium 4
            Actions actions = new Actions(driver);
            actions.moveToElement(feelingLucky).click().build().perform();

            // Setting the status of test as 'passed' or 'failed' based on the condition; if URL of the web page contains 'browserstack'
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            //WebDriverWait wait = new WebDriverWait(driver, 5);
            try {
                wait.until(ExpectedConditions.urlContains("browserstack"));
                markTestStatus("passed","URL contains 'browserstack'!",driver);
            }
            catch(Exception e) {
                markTestStatus("failed","URL does not contain 'browserstack'",driver);
            }
            System.out.println(driver.getCurrentUrl());
            driver.quit();
        } catch (Exception e) {
            markTestStatus("failed", "Exception!", driver);
            e.printStackTrace();
            driver.quit();
        }
    }

    // marking the tests as passed or failed in BrowserStack
    public static void markTestStatus(String status, String reason, WebDriver driver) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \""+status+"\", \"reason\": \""+reason+"\"}}");
    }

    public WebElement expandRootElement(By locator, WebDriver driver) {
        WebElement returnObj = null;
        try {
            WebElement shadowHost=driver.findElement(locator);
            Object shadowRoot = ((JavascriptExecutor) driver).executeScript("return arguments[0].shadowRoot", shadowHost);
            if (shadowRoot instanceof WebElement) {
                // ChromeDriver 95
                returnObj = (WebElement) shadowRoot;
            } else if (shadowRoot instanceof Map) {
                // ChromeDriver 96+
                // Based on https://github.com/SeleniumHQ/selenium/issues/10050#issuecomment-974231601
                Map<String, Object> shadowRootMap = (Map<String, Object>) shadowRoot;
                String shadowRootKey = (String) shadowRootMap.keySet().toArray()[0];
                String id = (String) shadowRootMap.get(shadowRootKey);
                RemoteWebElement remoteWebElement = new RemoteWebElement();
                remoteWebElement.setParent((RemoteWebDriver) driver);
                remoteWebElement.setId(id);
                returnObj = remoteWebElement;
            } else if(shadowRoot == null)
            {
                return shadowHost;
            }
            else {
                Assert.fail("Unexpected return type for shadowRoot in expandRootElement()");
            }
            return returnObj; 
        }catch(ClassCastException e)
        {
            throw new NoSuchElementException("Not able to locate element");
        }
    }
}