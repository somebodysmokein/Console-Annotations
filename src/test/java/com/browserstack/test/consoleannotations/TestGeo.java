package com.browserstack.test.consoleannotations;

import org.openqa.selenium.By;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
//import io.appium.java_client.ios.IOSElement;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;
public class TestGeo {

    public static final String AUTOMATE_USERNAME = "venkateshr_0PiUN7";
    public static final String AUTOMATE_KEY = "ADZqq5cND8dihy2RJAZD";
    public static final String URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

    public static void main(String[] args) throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("deviceName", "iPhone 14");
        browserstackOptions.put("osVersion", "16");
        browserstackOptions.put("local", "false");
        browserstackOptions.put("debug", "true");
        browserstackOptions.put("networkLogs", "true");
        //browserstackOptions.put("seleniumVersion", "3.141.59");
        browserstackOptions.put("appiumVersion", "1.22.0");
        browserstackOptions.put("projectName", "WebMd");
        browserstackOptions.put("buildName", "WebMd-debugging");
        browserstackOptions.put("sessionName", "WebMd-test");
        caps.setCapability("bstack:options", browserstackOptions);


        IOSDriver driver = new IOSDriver(new URL("https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub"), caps);
        driver.setLocation(new Location(40.748441, -73.985664, 10));
        driver.get("https://the-internet.herokuapp.com/geolocation");
        driver.findElement(By.xpath("//*[@id='content']/div/button")).click();
        Thread.sleep(5000);
        // To accept/block the popup, you need to switch the context to “NATIVE_APP“ and click on the Allow/Block button.
        driver.context("NATIVE_APP");
        driver.findElement(By.name("Allow")).click();
        Thread.sleep(5000);
        driver.quit();
    }

}
