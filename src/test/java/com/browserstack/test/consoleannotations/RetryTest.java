package com.browserstack.test.consoleannotations;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class RetryTest {
    public WebDriver driver=null;
    public static final String AUTOMATE_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    public static final String AUTOMATE_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    public static final String WEB_URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";

    @BeforeMethod
    public void startDriver() throws MalformedURLException {
        //WebDriverManager.chromedriver().setup();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("os", "OS X");
        browserstackOptions.put("osVersion", "Monterey");
        browserstackOptions.put("browserName", "chrome");
        browserstackOptions.put("browserVersion", "latest");
        //browserstackOptions.put("deviceName", "iPhone 14");
        //browserstackOptions.put("osVersion", "16");
        browserstackOptions.put("local", "false");
        browserstackOptions.put("debug", "true");
        browserstackOptions.put("networkLogs", "true");
        //browserstackOptions.put("appiumVersion", "2.0.0");
        browserstackOptions.put("projectName", "WebMd");
        browserstackOptions.put("buildName", "WebMd-debugging");
        browserstackOptions.put("sessionName", "WebMd-test");
        //capabilities.setCapability("autoAcceptAlerts","true");
        capabilities.setCapability("allowAllCookies", "true");
        capabilities.setCapability("acceptSslCerts", "true");
        capabilities.setCapability("bstack:options", browserstackOptions);

        System.out.println("Caps => "+ capabilities.toString());



        //Create driver object using IOSDriver
        //driver = new IOSDriver(new URL(WEB_URL), capabilities);
        System.out.println("Web URL => "+ WEB_URL);
        driver = new RemoteWebDriver(new URL(WEB_URL), capabilities);
        //driver = new ChromeDriver();
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void Test1()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://browserstack.com/");
        long start = System.currentTimeMillis();
        long end = start + 30 * 10;
        while (System.currentTimeMillis() < end) {
            // Some expensive operation on the item.
            driver.getPageSource();
        }
        Assert.assertEquals(false, true);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void Test2()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://browserstack.com/");
        long start = System.currentTimeMillis();
        long end = start + 30 * 10;
        while (System.currentTimeMillis() < end) {
            // Some expensive operation on the item.
            driver.getPageSource();
        }
        Assert.assertEquals(false, true);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void Test3()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://browserstack.com/");
        long start = System.currentTimeMillis();
        long end = start + 30 * 10;
        while (System.currentTimeMillis() < end) {
            // Some expensive operation on the item.
            driver.getPageSource();
        }
        Assert.assertEquals(false, true);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void Test4()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://browserstack.com/");
        long start = System.currentTimeMillis();
        long end = start + 30 * 10;
        while (System.currentTimeMillis() < end) {
            // Some expensive operation on the item.
            driver.getPageSource();
        }
        Assert.assertEquals(false, true);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void Test5()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://browserstack.com/");
        long start = System.currentTimeMillis();
        long end = start + 30 * 10;
        while (System.currentTimeMillis() < end) {
            // Some expensive operation on the item.
            driver.getPageSource();
        }
        Assert.assertEquals(false, true);
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        driver.quit();
    }
}
