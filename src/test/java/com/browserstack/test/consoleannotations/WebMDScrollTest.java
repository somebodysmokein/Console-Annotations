package com.browserstack.test.consoleannotations;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class WebMDScrollTest {

    public static final String AUTOMATE_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    public static final String AUTOMATE_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    public static final String URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
    public WebDriver driver=null;
    @Test
    public void testScroll() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "safari");
        capabilities.setCapability("browserVersion", "latest");
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("deviceName", "iPhone 14");
        browserstackOptions.put("osVersion", "16");
        browserstackOptions.put("local", "false");
        browserstackOptions.put("debug", "true");
        browserstackOptions.put("networkLogs", "true");
        //browserstackOptions.put("seleniumVersion", "3.141.59");
        browserstackOptions.put("projectName", "webmd");
        browserstackOptions.put("buildName", "webmd-scroll");
        browserstackOptions.put("sessionName", "scroll-test");
        capabilities.setCapability("allowAllCookies", "true");
        capabilities.setCapability("acceptSslCerts", "true");
        //browserstackOptions.put("machine","207.254.11.100");
        capabilities.setCapability("bstack:options", browserstackOptions);
        System.out.println();
        driver = new RemoteWebDriver(new URL(URL), capabilities);


        try {
            // logging start of test session at BrowserStack
            annotate("verify Matterport issue", "info", driver);
            driver.get("https://www.webmd.com/drugs/2/drug-181254/qelbree-oral/details");
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            //final WebDriverWait wait = new WebDriverWait(driver, 10);

            //WebElement elt= fluientWaitforElement(driver.findElement(By.xpath("//*[@id=\"onetrust-accept-btn-handler\"]")),60,5);
            boolean bannerExists = driver.findElements( By.xpath("//*[@id=\"onetrust-accept-btn-handler\"]") ).size() != 0;
            if(bannerExists) {
                driver.findElement(By.xpath("//*[@id=\"onetrust-accept-btn-handler\"]")).click();
            }
            WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(30));
            wait.withTimeout(Duration.ofSeconds(30));
            //to perform Scroll on application using Selenium
            //JavascriptExecutor js = (JavascriptExecutor) driver;
            //js.executeScript("window.scrollBy(0,350)", "");
            // verifying whether the product added to cart is available in the cart

            System.out.println("scroll starts");

            scroll(BStackInject.ScrollDirection.DOWN, 0.5);

            System.out.println("scroll ends");


                markTestStatus("passed", "Scroll completed!", driver);

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

    private  Dimension windowSize;
    public enum ScrollDirection {
        UP, DOWN, LEFT, RIGHT
    }
    private  Duration SCROLL_DUR = Duration.ofMillis(1000);
    private  Dimension getWindowSize() {
        if (windowSize == null) {
            windowSize = driver.manage().window().getSize();
        }
        return windowSize;
    }
    public void scroll(BStackInject.ScrollDirection dir, double scrollRatio) throws UnsupportedCommandException{
        try {
            if (scrollRatio < 0 || scrollRatio > 1) {
                throw new Error("Scroll distance must be between 0 and 1");
            }
            Dimension size;

            size = getWindowSize();

            Point midPoint = new Point((int)(size.width * 0.5), (int)(size.height * 0.5));
            int top = midPoint.y - (int)((size.height * scrollRatio) * 0.5);
            int bottom = midPoint.y + (int)((size.height * scrollRatio) * 0.5);
            int left = midPoint.x - (int)((size.width * scrollRatio) * 0.5);
            int right = midPoint.x + (int)((size.width * scrollRatio) * 0.5);
            if (dir == BStackInject.ScrollDirection.UP) {
                swipe(new Point(midPoint.x, top), new Point(midPoint.x, bottom), SCROLL_DUR);
            } else if (dir == BStackInject.ScrollDirection.DOWN) {
                swipe(new Point(midPoint.x, bottom), new Point(midPoint.x, top), SCROLL_DUR);
            } else if (dir == BStackInject.ScrollDirection.LEFT) {
                swipe(new Point(left, midPoint.y), new Point(right, midPoint.y), SCROLL_DUR);
            } else {
                swipe(new Point(right, midPoint.y), new Point(left, midPoint.y), SCROLL_DUR);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private static double SCROLL_RATIO = 0.8;
    private static int ANDROID_SCROLL_DIVISOR = 3;

    protected  void swipe(Point start, Point end, Duration duration) {
        boolean isIOS = driver instanceof RemoteWebDriver;

        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence swipe = new Sequence(input, 0);
        swipe.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y));
        swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        if (isIOS) {
            duration = duration.dividedBy(ANDROID_SCROLL_DIVISOR);
        } else {
            swipe.addAction(new Pause(input, duration));
            duration = Duration.ZERO;
        }
        swipe.addAction(input.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y));
        swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        ((RemoteWebDriver) driver).perform(Arrays.asList(swipe));
    }


}
