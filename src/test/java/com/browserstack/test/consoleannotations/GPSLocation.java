package com.browserstack.test.consoleannotations;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class GPSLocation {

    public static final String AUTOMATE_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    public static final String AUTOMATE_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    public static final String WEB_URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
    public IOSDriver driver=null;


    @Test
    public void testWebMdLogin() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("deviceName", "iPhone 14");
        browserstackOptions.put("osVersion", "16");
        browserstackOptions.put("local", "false");
        browserstackOptions.put("debug", "true");
        browserstackOptions.put("networkLogs", "true");
        browserstackOptions.put("appiumVersion", "1.22.0");
        browserstackOptions.put("projectName", "WebMd");
        browserstackOptions.put("buildName", "WebMd-debugging");
        browserstackOptions.put("sessionName", "WebMd-test");
        capabilities.setCapability("autoAcceptAlerts","true");
        capabilities.setCapability("browserstack.safari.enablePopups", "true");
        capabilities.setCapability("bstack:options", browserstackOptions);
        driver = new IOSDriver(new URL(WEB_URL), capabilities);
        driver.setLocation(new Location(40.748441, -73.985664, 10));
        try {
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

            //To confirm if the GPS location is set
            driver.get("https://www.google.com/?q=nearby%20restaurants");
            WebElement q=driver.findElement(By.name("q"));
            q.sendKeys("find nearby restaurants");
            Thread.sleep(10000);
            q.sendKeys(Keys.ENTER);
            Thread.sleep(2000);

            WebElement listElt=driver.findElement(By.xpath("//*[@role=\"listbox\"]/li[1]"));
            listElt.click();
            Thread.sleep(5000);


            //WebMD website
            driver.get("https://www.webmd.com//persantine-side-effects-drug-center.htm");

            Thread.sleep(20000);

        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.quit();

}

    public WebElement fluientWaitforElement(WebElement element, int timoutSec, int pollingSec) {
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
