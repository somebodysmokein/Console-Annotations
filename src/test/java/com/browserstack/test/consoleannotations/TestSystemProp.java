package com.browserstack.test.consoleannotations;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestSystemProp {

        public static void main(String args[]) {

            System.setProperty("", "/Users/venkatesh/Downloads/chromedriver_mac_arm64/chromedriver");

            // Initializing the browser driver

            WebDriver driver = new ChromeDriver();

            // Navigating to BrowserStack website

            driver.get("https://www.browserstack.com");

            System.out.println("BrowserStack is launched successfully on Chrome!");

            driver.close();

        }

}
