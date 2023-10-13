package com.browserstack.test.consoleannotations;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class SafariLocal {

    public static final String AUTOMATE_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    public static final String AUTOMATE_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    public static final String URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
    public WebDriver driver=null;
    @Test
    public void testApplePay() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Edge");
        capabilities.setCapability("browserVersion", "latest");
        capabilities.setCapability("nativeWebTap","true");
        //capabilities.setCapability("autoAcceptAlerts","true");
        //capabilities.setCapability("browserstack.safari.enablePopups", "true");

        //browserstackOptions.put("safari.enablePopups", "true");

        /*SafariOptions options = new SafariOptions();
        options.setCapability("javascript.enabled", true);

        capabilities.setCapability(SafariOptions.CAPABILITY,options);*/

        //browserstackOptions.put("machine","207.254.11.100");
        //capabilities.setCapability("bstack:options", browserstackOptions);
        //driver = new RemoteWebDriver(new URL(URL), capabilities);
        WebDriverManager.safaridriver().setup();
        driver = new SafariDriver();




        try {
            // logging start of test session at BrowserStack
            //annotate("verify Apple Pay issue", "info", driver);
            driver.get("https://c.rt2.me/76Czhq0lb2");
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            //final WebDriverWait wait = new WebDriverWait(driver, 10);
            //wait.until(ExpectedConditions.titleIs("WS_automation_testing1"));
            // gClick pl


            WebElement payBtn=driver.findElement(By.xpath("//input[starts-with(@aria-label, 'Pay with Apple Pay')]"));
            payBtn=fluientWaitforElement(payBtn,60,5);
            payBtn.click();
            Thread.sleep(1000);

            WebElement payNowBtn=driver.findElement(By.xpath("//*[@id=\"payNowButton\"]"));
            payNowBtn=fluientWaitforElement(payNowBtn,60,5);
            //JavascriptExecutor executor = (JavascriptExecutor)driver;
            //executor.executeScript("arguments[0].click();", payNowBtn);
            Actions action = new Actions(driver);
            action.moveToElement(payNowBtn).click().perform();
            //payNowBtn.click();
            Thread.sleep(5000);


        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.quit();
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
