package com.browserstack.test.consoleannotations;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class AppiumTest {

    public static final String AUTOMATE_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    public static final String AUTOMATE_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    public static final String URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
    public WebDriver driver=null;

    @Test
    public void testApplePay() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserstack.safari.enablePopups", "true");
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("deviceName", "iPhone 14 Pro");
        browserstackOptions.put("osVersion", "16");
        browserstackOptions.put("local", "false");
        browserstackOptions.put("debug", "true");
        browserstackOptions.put("networkLogs", "true");
        browserstackOptions.put("projectName", "Apple Pay");
        browserstackOptions.put("buildName", "Apple-Pay-debugging");
        browserstackOptions.put("sessionName", "Apple-Pay-test");
        //browserstackOptions.put("enableApplePay", "true");


        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 14");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"iOS");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"XCUITest");
        capabilities.setCapability(MobileCapabilityType.UDID,"7E2F5AC5-4DA7-4357-B5C6-17603DD04724");


        /*SafariOptions options=new SafariOptions();
        options.setPlatformName("iOS");
        options.setAndroidDeviceSerialNumber("N000TA1183962301141");
        options.setBrowserVersion("106");*/

        capabilities.setCapability("bstack:options", browserstackOptions);
        try {
            driver.get("https://c.rt2.me/76Czhq0lb2");
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);


            WebElement payBtn = driver.findElement(By.xpath("//input[starts-with(@aria-label, 'Pay with Apple Pay')]"));
            payBtn = fluientWaitforElement(payBtn, 60, 5);
            payBtn.click();
            Thread.sleep(5000);


            //WebElement payNowBtn=driver.findElement(By.xpath("//*[@id=\"payNowButton\"]"));
            WebElement payNowBtn=driver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"PAY NOW\"]"));
            payNowBtn=fluientWaitforElement(payNowBtn,60,5);
            payNowBtn.click();
            Thread.sleep(20000);



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
