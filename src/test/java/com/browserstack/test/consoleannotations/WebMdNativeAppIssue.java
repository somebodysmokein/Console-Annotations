package com.browserstack.test.consoleannotations;

        import io.appium.java_client.ios.IOSDriver;
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

public class WebMdNativeAppIssue {

    public static final String AUTOMATE_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    public static final String AUTOMATE_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    public static final String WEB_URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
    public IOSDriver driver=null;


    @Test
    public void testWebMdLogin() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("deviceName", "iPhone 13");
        browserstackOptions.put("osVersion", "15");
        browserstackOptions.put("local", "false");
        browserstackOptions.put("debug", "true");
        browserstackOptions.put("networkLogs", "true");
        browserstackOptions.put("appiumVersion", "2.0.0");
        browserstackOptions.put("projectName", "WebMd");
        browserstackOptions.put("buildName", "WebMd-debugging");
        browserstackOptions.put("sessionName", "WebMd-test");
        capabilities.setCapability("autoAcceptAlerts","true");
        capabilities.setCapability("browserstack.safari.enablePopups", "true");
        capabilities.setCapability("bstack:options", browserstackOptions);

        //Create driver object using IOSDriver
        driver = new IOSDriver(new URL(WEB_URL), capabilities);

        try {

            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            driver.get("https://login.medscape.com/login/sso/getlogin?ac=401");
            //Set Native App context as it only works in that context
            driver.context("NATIVE_APP");

            //Use Native Elements
            WebElement usrId=driver.findElement(By.xpath("//XCUIElementTypeOther[@name=\"Medscape Log In\"]/XCUIElementTypeOther[6]/XCUIElementTypeTextField"));
            //WebElement usrId=driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Medscape Log In\"]/XCUIElementTypeOther[6]/XCUIElementTypeTextField");
            usrId=fluientWaitforElement(usrId,60,5);
            usrId.click();
            usrId.sendKeys("infosession85");
            Thread.sleep(1000);


            WebElement passwd = driver.findElement(By.xpath("//XCUIElementTypeOther[@name=\"Medscape Log In\"]/XCUIElementTypeOther[8]/XCUIElementTypeSecureTextField"));
            //WebElement passwd = driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Medscape Log In\"]/XCUIElementTypeOther[8]/XCUIElementTypeSecureTextField");
            passwd=fluientWaitforElement(passwd,60,5);
            passwd.click();
            passwd.sendKeys("medscape");
            Thread.sleep(2000);

            passwd.sendKeys(Keys.ENTER);


            WebElement submitBtns=driver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"Log In\"]"));
            submitBtns=fluientWaitforElement(submitBtns,60,5);
            submitBtns.click();

            Thread.sleep(10000);

            //Set it back to Web Context after login complete
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

            //Start using web elements instead of native app elements
            WebElement mnet=driver.findElement(By.id("mnet"));
            mnet=fluientWaitforElement(mnet,60,5);
            //driver.switchTo().frame(mnet);

            WebElement headerbox= driver.findElement(By.xpath("//*[@id=\"headerbox\"]/div[2]/a/img"));
            headerbox = fluientWaitforElement(headerbox,60,5);
            String title= headerbox.getAttribute("title");
            System.out.println("Title => "+title);

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
