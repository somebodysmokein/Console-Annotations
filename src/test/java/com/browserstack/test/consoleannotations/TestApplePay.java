package com.browserstack.test.consoleannotations;




import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.*;
//import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class TestApplePay {
    public static final String AUTOMATE_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    public static final String AUTOMATE_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    public static final String URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
    public WebDriver driver=null;
    @Test
    public void testApplePay() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        //capabilities.setCapability("browserName", "Edge");
        //capabilities.setCapability("browserVersion", "latest");
        //capabilities.setCapability("nativeWebTap","true");
        //capabilities.setCapability("autoAcceptAlerts","true");
        capabilities.setCapability("browserstack.safari.enablePopups", "true");
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        //browserstackOptions.put("os", "Windows");
        browserstackOptions.put("deviceName", "iPhone 14 Pro");
        browserstackOptions.put("osVersion", "16");
        browserstackOptions.put("local", "false");
        browserstackOptions.put("debug", "true");
        browserstackOptions.put("networkLogs", "true");
        //browserstackOptions.put("seleniumVersion", "3.141.59");
        //browserstackOptions.put("appiumVersion", "2.0.0");
        browserstackOptions.put("projectName", "Apple Pay");
        browserstackOptions.put("buildName", "Apple-Pay-debugging");
        browserstackOptions.put("sessionName", "Apple-Pay-test");
        //browserstackOptions.put("enableApplePay", "true");

        //browserstackOptions.put("safari.enablePopups", "true");

        //SafariOptions options = new SafariOptions();
        //options.setCapability("javascript.enabled", true);

        //capabilities.setCapability(SafariOptions.CAPABILITY,options);

        //browserstackOptions.put("machine","207.254.11.100");
        capabilities.setCapability("bstack:options", browserstackOptions);
        //HttpClient.Factory clientFactory = new HttpClient.Factory(new HttpClientFactory(connectionTimeout, socketTimeout));


        //ClientConfig config=ClientConfig.defaultConfig().readTimeout(Duration.ofMinutes(10L));

        /*driver=RemoteWebDriver.builder()
                .address(new URL(URL))
                .setCapability("bstack:options", browserstackOptions)
                .config(config)
                .build();
*/


        //IOSDriver<WebElement> driver = new IOSDriver(new URL(URL), capabilities);

        IOSDriver driver = new IOSDriver(new URL(URL), capabilities);



        //HttpCommandExecutor executor =
          //      new HttpCommandExecutor(new HashMap<String, CommandInfo>(), new URL(URL), clientFactory);
        //driver = new RemoteWebDriver(new URL(URL),capabilities);
        //WebDriverManager.safaridriver().setup();
        //driver = new SafariDriver();




        try {
            // logging start of test session at BrowserStack
            //annotate("verify Apple Pay issue", "info", driver);
            driver.get("https://c.rt2.me/76Czhq0lb2");
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            //final WebDriverWait wait = new WebDriverWait(driver, 10);
            //wait.until(ExpectedConditions.titleIs("WS_automation_testing1"));
            // gClick pl

           Set<String> contexts= driver.getContextHandles();
           Iterator elt=contexts.iterator();
           while(elt.hasNext())
           {
               String ctxt=elt.next().toString();

               if(ctxt.contains("WEBVIEW"))
               {
                    driver.context(ctxt);
               }

           }
            //driver.context("WEB_VIEW");



            WebElement payBtn=driver.findElement(By.xpath("//input[starts-with(@aria-label, 'Pay with Apple Pay')]"));
            payBtn=fluientWaitforElement(payBtn,60,5);
            payBtn.click();
            Thread.sleep(5000);


            driver.context("NATIVE_APP");

            //WebElement payNowBtn=driver.findElement(By.xpath("//*[@id=\"payNowButton\"]"));
            WebElement payNowBtn=driver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"PAY NOW\"]"));
            //WebElement payNowBtn=driver.findElement(By.xpath("//*[@class=\"t-statement-inline-pay-now\"]/div[1]/div[2]/button"));
            payNowBtn=fluientWaitforElement(payNowBtn,60,5);
            //JavascriptExecutor executor = (JavascriptExecutor)driver;
            //executor.executeScript("arguments[0].click();", payNowBtn);
            //Actions action = new Actions(driver);
            //action.moveToElement(payNowBtn).click().perform();
            payNowBtn.click();
            //Thread.sleep(5000);





            //WebElement el2 =driver.findElement(new AppiumBy.ByAccessibilityId("PAY NOW"));
           /* WebElement el2 =driver.findElement(new By.ByXPath("//XCUIElementTypeButton[@name=\"PAY NOW\"]") {
            });
            el2=fluientWaitforElement(el2,60,5);*/
            //MobileElement el2 = (MobileElement) driver.findElementByAccessibilityId("PAY NOW");
            //el2.click();


            /*JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("browserstack_executor:{"+
                    "\"action\":\"applePay\","+
                    "\"arguments\": {"+
                    "\"confirmPayment\" : \"true\"}}");

            new Actions(driver).sendKeys("123456");
*/
            Thread.sleep(20000);


        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.quit();
    }

    // This method accepts the status, reason and WebDriver instance and marks the test on BrowserStack
    public static void markTestStatus(String status, String reason, WebDriver driver) {
        final JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"" + status + "\", \"reason\": \"" + reason + "\"}}");
    }

    public static void annotate(String data, String level, WebDriver driver) {
        final JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("browserstack_executor: {\"action\": \"annotate\", \"arguments\": {\"data\": \"" + data + "\", \"level\": \"" + level + "\"}}");
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
