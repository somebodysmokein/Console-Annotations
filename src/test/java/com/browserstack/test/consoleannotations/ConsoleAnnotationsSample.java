package com.browserstack.test.consoleannotations;



import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class ConsoleAnnotationsSample {
    public static final String AUTOMATE_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    public static final String AUTOMATE_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    public static final String URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
    public WebDriver driver=null;
    @Test
    public void testCosnoleAnnotations() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("browserVersion", "latest");
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        //browserstackOptions.put("deviceName", "Samsung Galaxy S22 Ultra");
        //browserstackOptions.put("osVersion", "12");
        browserstackOptions.put("os", "OS X");
        browserstackOptions.put("osVersion", "Monterey");
        browserstackOptions.put("browserVersion", "15.0");
        browserstackOptions.put("local", "false");
        browserstackOptions.put("debug", "true");
        browserstackOptions.put("networkLogs", "true");
        browserstackOptions.put("seleniumVersion", "3.141.59");
        browserstackOptions.put("projectName", "skechers");
        browserstackOptions.put("buildName", "skechers-debugging");
        browserstackOptions.put("sessionName", "basic-auth");
        browserstackOptions.put("idleTimeout", 200);
        //browserstackOptions.put("machine","207.254.11.100");
        browserstackOptions.put("headerParams", "{\"Authorization\":\"Basic YWRtaW46YWRtaW4=\"}");
        capabilities.setCapability("bstack:options", browserstackOptions);
        driver = new RemoteWebDriver(new URL(URL), capabilities);


        try {

            driver.get("https://the-internet.herokuapp.com/basic_auth");
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.withTimeout(Duration.ofSeconds(15));
            Thread.sleep(3000);
            // logging start of test session at BrowserStack
            /*annotate("verify Matterport issue", "info", driver);
            driver.get("https://qa3-app.matterport.com/show/?m=YvnxpVHuQit&qs=1&version=3.1.65.5-0-gbed3d535f2");
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            //final WebDriverWait wait = new WebDriverWait(driver, 10);
            final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.titleIs("WS_automation_testing1"));
            // gClick pl
            WebElement playBtn=driver.findElement(By.xpath("//*[@class=\"highlight-tour-controls\"]/descendant-or-self::span[@class=\"icon icon-play\"]"));
            playBtn=fluientWaitforElement(playBtn,60,5);
            playBtn.click();
            Thread.sleep(1000);

            // waiting for the Add to cart button to be clickable
            WebElement dollhouse_btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class=\"mode-buttons\"]/descendant-or-self::span[@class=\"icon icon-dollhouse\"]")));
            // clicking the 'Add to cart' button
            dollhouse_btn.click();

            WebElement container=driver.findElement(By.xpath("//*[@class=\"webgl-canvas\"]"));
            container=fluientWaitforElement(container,60,5);
            container.click();

            // checking if the Cart pane is visible
            WebElement floorplan_btn=driver.findElement(By.xpath("//*[@class=\"mode-buttons\"]/descendant-or-self::span[@class=\"icon icon-floorplan\"]"));
            floorplan_btn=fluientWaitforElement(floorplan_btn,60,5);
            floorplan_btn.click();

            WebElement floorSelector=driver.findElement(By.xpath("//*[@class=\"floor-selector\"]/descendant-or-self::span[@class=\"icon icon-floor-controls\"]"));
            fluientWaitforElement(floorSelector,180,10);
            floorSelector.click();
            // getting the product's name added in the cart
            WebElement floorControls=driver.findElement(By.xpath("//*[@class=\"floor-button\"]"));
            fluientWaitforElement(floorControls,60,5);

            List<WebElement> floors=driver.findElements(By.xpath("//*[@class=\"floor-button\"]"));
            for(WebElement elt:floors)
            {
                System.out.println(elt.getText());
            }

            // verifying whether the product added to cart is available in the cart
            if (floors.size()>0) {
                markTestStatus("passed", "Product has been successfully added to the cart!", driver);
            } else {
                markTestStatus("failed", "Product has not successfully added to the cart!", driver);
                annotate("Product not found in cart. Test Failed", "error", driver);
            }*/
        } catch (Exception e) {
            markTestStatus("failed", "Some elements failed to load", driver);
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
