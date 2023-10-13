package com.browserstack.test.consoleannotations;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
//import io.appium.java_client.ios.IOSElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class WebMDIssue {

    public static final String AUTOMATE_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    public static final String AUTOMATE_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    public static final String WEB_URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
    public IOSDriver driver=null;
    //public AppiumDriver driver=null;


    @Test
    public void testWebMdLogin() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("deviceName", "iPhone 13");
        browserstackOptions.put("osVersion", "15");
        browserstackOptions.put("local", "false");
        browserstackOptions.put("debug", "true");
        browserstackOptions.put("networkLogs", "true");
        //browserstackOptions.put("seleniumVersion", "3.141.59");
        browserstackOptions.put("appiumVersion", "2.0.0");
        browserstackOptions.put("projectName", "WebMd");
        browserstackOptions.put("buildName", "WebMd-debugging");
        browserstackOptions.put("sessionName", "WebMd-test");
        //capabilities.setCapability("nativeWebTap","true");
        //browserstackOptions.put("safari.enablePopups", "true");

        //SafariOptions options = new SafariOptions();
        //options.setCapability("javascript.enabled", true);

        //capabilities.setCapability(SafariOptions.CAPABILITY,options);

        //browserstackOptions.put("machine","207.254.11.100");
        capabilities.setCapability("autoAcceptAlerts","true");
        capabilities.setCapability("browserstack.safari.enablePopups", "true");
        capabilities.setCapability("bstack:options", browserstackOptions);
        //driver = new RemoteWebDriver(new URL(WEB_URL), capabilities);

        driver = new IOSDriver(new URL(WEB_URL), capabilities);


        //et geo location in test scripts
        //Double lati = 40.748441;
        //Double longti = -73.985664;
        //JavascriptExecutor executor = (JavascriptExecutor)driver;
        //executor.executeScript("window.navigator.geolocation.getCurrentPosition=function(success){; var position = {'coords' : {'latitude': '"+String.valueOf(lati)+"','longitude': '"+String.valueOf(longti)+"'}}; success(position);}");


        //driver.setLocation(new Location(40.748441, -73.985664, 10));
        //WebDriverManager.safaridriver().setup();
        //driver = new SafariDriver();




        try {
            // logging start of test session at BrowserStack
            //annotate("verify Apple Pay issue", "info", driver);

            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            driver.get("https://login.medscape.com/login/sso/getlogin?ac=401");
            //final WebDriverWait wait = new WebDriverWait(driver, 10);
            //wait.until(ExpectedConditions.titleIs("WS_automation_testing1"));
            // gClick pl

            driver.context("NATIVE_APP");

            //WebElement usrId=driver.findElement(By.name("userId"));
            WebElement usrId=driver.findElement(By.xpath("//XCUIElementTypeOther[@name=\"Medscape Log In\"]/XCUIElementTypeOther[6]/XCUIElementTypeTextField"));
            //WebElement usrId=driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Medscape Log In\"]/XCUIElementTypeOther[6]/XCUIElementTypeTextField");
            //WebElement usrId=driver.findElementByXPath("//XCUIElementTypeButton[@name=\"Log In\"]");
            usrId=fluientWaitforElement(usrId,60,5);
            //Actions userActn = new Actions(driver);
            //userActn.moveToElement(usrId).click().sendKeys("venky.webhook@gmail.com").perform();
            //usrId.clear();
            //usrId.click();
            //usrId.sendKeys("infosession85");
            //usrId.sendKeys(Keys.ENTER);
            //((IOSElement) usrId).setValue("text");
            //JavascriptExecutor js = (JavascriptExecutor) driver;
            //js.executeScript("$('[name=\"userId\"]').val('infosession85')");

            //js.executeScript("document.getElementByName('userId').focus();");
            usrId.click();
            usrId.sendKeys("infosession85");
            Thread.sleep(1000);

            //WebElement passwd=driver.findElement(By.name("password"));
            WebElement passwd = driver.findElement(By.xpath("//XCUIElementTypeOther[@name=\"Medscape Log In\"]/XCUIElementTypeOther[8]/XCUIElementTypeSecureTextField"));
            //WebElement passwd = driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Medscape Log In\"]/XCUIElementTypeOther[8]/XCUIElementTypeSecureTextField");
            passwd=fluientWaitforElement(passwd,60,5);
            //js.executeScript("$('[name=\"password\"]').val('medscape')");
            //js.executeScript("document.getElementByName('password').focus();");
            //JavascriptExecutor executor = (JavascriptExecutor)driver;
            //executor.executeScript("arguments[0].click();", payNowBtn);
            //Actions action = new Actions(driver);
            //action.moveToElement(passwd).click().sendKeys("medscape").perform();
            //passwd.clear();
            passwd.click();
            passwd.sendKeys("medscape");
            Thread.sleep(2000);

            passwd.sendKeys(Keys.ENTER);

            //WebElement submitBtns=driver.findElement(By.xpath("//*[@class=\"mdscp-button--submit mdscp-button--medium\"]"));
            WebElement submitBtns=driver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"Log In\"]"));
            submitBtns=fluientWaitforElement(submitBtns,60,5);
            submitBtns.click();

            //Actions action = new Actions(driver);
            //action.sendKeys(Keys.ENTER);

            Thread.sleep(10000);

            Set<String> contexts= driver.getContextHandles();
            Iterator elt=contexts.iterator();
            while(elt.hasNext())
            {
                String ctxt=elt.next().toString();

                System.out.println("Context => "+ctxt);
                if(ctxt.contains("WEBVIEW"))
                {
                    driver.context(ctxt);
                }

            }

            WebElement hcp=driver.findElement(By.className("is-www show-hcp-message"));
            hcp=fluientWaitforElement(hcp, 60, 5);

            WebElement mnet=driver.findElement(By.id("mnet"));
            mnet=fluientWaitforElement(mnet,60,5);
            //driver.switchTo().frame(mnet);

           WebElement headerbox= driver.findElement(By.xpath("//*[@id=\"headerbox\"]/div[2]/a/img"));
            headerbox = fluientWaitforElement(headerbox,60,5);
            String title= headerbox.getAttribute("title");
            System.out.println("Title => "+title);

            /*driver.get("https://www.google.com/?q=nearby%20restaurants");
            WebElement q=driver.findElement(By.name("q"));
            q.sendKeys("find nearby restaurants");
            Thread.sleep(1000);
            q.sendKeys(Keys.ENTER);
            Thread.sleep(2000);

            WebElement listElt=driver.findElement(By.xpath("//*[@role=\"listbox\"]/li[1]"));
            listElt.click();
*/
            //WebElement btnK=driver.findElement(By.name("btnK"));
            //btnK.click();
            //Thread.sleep(5000);

            //driver.context("NATIVE_APP");
            //driver.findElement(By.name("Allow")).click();


            /*q.sendKeys("find nearby restaurants");
            Thread.sleep(1000);
            q.sendKeys(Keys.ENTER);
            Thread.sleep(2000);

            listElt.click();*/


            //driver.get("https://www.webmd.com//persantine-side-effects-drug-center.htm");

            //Thread.sleep(20000);

          /*  TouchActions
            (io.appium.java_client.TouchAction)TouchAction(driver).press(startX,startY).wait(1000)
                    .move_to(endX,endY).release().perform()
*/



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
