package com.browserstack.test.consoleannotations;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class TestAuth {

    public static final String AUTOMATE_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    public static final String AUTOMATE_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    public static final String WEB_URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
    public IOSDriver driver=null;


    @Test
    public void testWebMdLogin() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("browserVersion", "latest");
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("deviceName", "iPhone 14");
        browserstackOptions.put("osVersion", "16");
        browserstackOptions.put("local", "false");
        browserstackOptions.put("debug", "true");
        browserstackOptions.put("networkLogs", "true");
        browserstackOptions.put("appiumVersion", "2.0.0");
        browserstackOptions.put("projectName", "WebMd");
        browserstackOptions.put("buildName", "WebMd-debugging");
        browserstackOptions.put("sessionName", "WebMd-test");
        //browserstackOptions.put("headerParams", "{\"Authorization\":\"Basic d2VibWQ6c3RhZ2luZw==\"}");
        capabilities.setCapability("autoAcceptAlerts", "true");
        capabilities.setCapability("browserstack.safari.enablePopups", "true");
        capabilities.setCapability("bstack:options", browserstackOptions);


        //Create driver object using IOSDriver
        driver = new IOSDriver(new URL(WEB_URL), capabilities);

        try {

            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            driver.get("https://webmd:staging@www.staging.webmd.com/rheumatoid-arthritis/knee-ra-rheumatoid-arthritis-of-the-knee");

            final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.withTimeout(Duration.ofSeconds(15));
            Thread.sleep(3000);

        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.quit();
    }
}
