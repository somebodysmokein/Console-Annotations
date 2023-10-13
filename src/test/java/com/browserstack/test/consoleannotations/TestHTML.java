package com.browserstack.test.consoleannotations;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.percy.selenium.Percy;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.Test;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class TestHTML {

    public static final String AUTOMATE_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    public static final String AUTOMATE_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    public static final String URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
    public WebDriver driver=null;
    public Percy percy;
    @Test
    public void testCosnoleAnnotations() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("os", "Windows");
        //browserstackOptions.put("deviceName", "iPhone 13");
        browserstackOptions.put("osVersion", "10");
        browserstackOptions.put("local", "false");
        browserstackOptions.put("debug", "true");
        browserstackOptions.put("networkLogs", "true");
        browserstackOptions.put("seleniumVersion", "3.141.59");
        browserstackOptions.put("projectName", "Test HTML");
        browserstackOptions.put("buildName", "HTML-debugging");
        browserstackOptions.put("sessionName", "HTML-test");
        //browserstackOptions.put("safari.enablePopups", "true");

        //browserstackOptions.put("machine","207.254.11.100");
        capabilities.setCapability("bstack:options", browserstackOptions);
        WebDriverManager.chromedriver().setup();
        ChromeOptions options=new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        percy = new Percy(driver);
        //driver = new RemoteWebDriver(new URL(URL), capabilities);

        try {

            Path sampleFile = Paths.get("Expected.html");
            System.out.println(sampleFile.toUri().toString());
            //driver.get("https://www.google.com");
            driver.get("Users/venkatesh/Projects/Console-Annotations/src/main/resources/Expected.html");
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

            System.out.println(driver.getTitle());
            percy.snapshot("HTML Test", Arrays.asList(new Integer[]{375, 480, 720, 1280, 1440, 1920}));
            Thread.sleep(3000);


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (driver !=null)
                driver.quit();
        }
    }

    public WebElement fluientWaitforElement(WebElement element, int timoutSec, int pollingSec) {

        //System.out.println(driver.getTitle());
        FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timoutSec))
                .pollingEvery(Duration.ofSeconds(pollingSec))
                .ignoring(NoSuchElementException.class, TimeoutException.class)
                .ignoring(StaleElementReferenceException.class);

        for (int i = 0; i < 2; i++) {
            try {

                fWait.until(ExpectedConditions.visibilityOf(element));
                fWait.until(ExpectedConditions.elementToBeClickable(element));
            } catch (Exception e) {

                System.out.println("Element Not found trying again - " + element.toString().substring(70));
                e.printStackTrace();

            }
        }

        return element;

    }

}
