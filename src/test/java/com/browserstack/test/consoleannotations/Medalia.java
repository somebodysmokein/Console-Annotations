package com.browserstack.test.consoleannotations;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Medalia {

    public static final String AUTOMATE_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    public static final String AUTOMATE_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    public static final String WEB_URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
    private AndroidDriver driver;

    @Test
    public void testWebMdLogin() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("deviceName", "Samsung Galaxy S22");
        browserstackOptions.put("osVersion", "12.0");
        browserstackOptions.put("local", "false");
        browserstackOptions.put("debug", "true");
        browserstackOptions.put("networkLogs", "true");
        browserstackOptions.put("consoleLogs", "verbose");
        //browserstackOptions.put("seleniumVersion", "3.141.59");
        browserstackOptions.put("appiumVersion", "2.0.1");
        browserstackOptions.put("projectName", "Medalia");
        browserstackOptions.put("buildName", "Medalia-debugging");
        browserstackOptions.put("sessionName", "Medalia-test");

        capabilities.setCapability("bstack:options", browserstackOptions);

        driver = new AndroidDriver(new URL(WEB_URL), capabilities);

        try {
            // logging start of test session at BrowserStack
            //annotate("verify Apple Pay issue", "info", driver);

            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            driver.get("https://survey.medallia.com/?mec-ll-video-intg");
            driver.context("NATIVE_APP");
            Thread.sleep(30000);

        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.quit();
    }
}
