package com.browserstack.test.consoleannotations;


import com.google.common.collect.ImmutableMap;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AppiumW3CHttpCommandCodec;
import org.jsoup.helper.HttpConnection;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;
import java.sql.Driver;
import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class BStackInject {

    public static final String AUTOMATE_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    public static final String AUTOMATE_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    public static final String WEB_URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
    public WebDriver driver=null;


    @Test
    public void testWebMdLogin() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("deviceName", "iPhone 14");
        browserstackOptions.put("osVersion", "16");
        browserstackOptions.put("local", "false");
        browserstackOptions.put("debug", "true");
        browserstackOptions.put("networkLogs", "true");
        //browserstackOptions.put("appiumVersion", "2.0.0");
        browserstackOptions.put("projectName", "WebMd");
        browserstackOptions.put("buildName", "WebMd-debugging");
        browserstackOptions.put("sessionName", "WebMd-test");
        //capabilities.setCapability("autoAcceptAlerts","true");
        capabilities.setCapability("allowAllCookies", "true");
        capabilities.setCapability("acceptSslCerts", "true");
        capabilities.setCapability("bstack:options", browserstackOptions);

        System.out.println("Caps => "+ capabilities.toString());



        //Create driver object using IOSDriver
        //driver = new IOSDriver(new URL(WEB_URL), capabilities);
        System.out.println("Web URL => "+ WEB_URL);
        driver = new RemoteWebDriver(new URL(WEB_URL), capabilities);

        try {

            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            driver.get("https://login.medscape.com/login/sso/getlogin?ac=401");
            //Set Native App context as it only works in that context


            SessionId session=((RemoteWebDriver)driver).getSessionId();
            System.out.println("Session => "+ session);
            Command cmd=new Command(session,DriverCommand.SWITCH_TO_CONTEXT, ImmutableMap.of("name", "NATIVE_APP"));
            CommandInfo info=new CommandInfo("/session/:sessionId/context",HttpMethod.POST);
            Map<String, CommandInfo> capMap=new HashMap<>();
            capMap.put(DriverCommand.SWITCH_TO_CONTEXT,info);

            WebMdCommandExecutor exec=new WebMdCommandExecutor(new URL(WEB_URL), session);
            exec.execute(cmd);
            Thread.sleep(3000);


            //Enter UserName
            //XCUIElementTypeOther[@name="Medscape Log In"]/XCUIElementTypeOther[3]
            findAndEnterText(session,"//XCUIElementTypeOther[@name=\"Medscape Log In\"]/XCUIElementTypeOther[3]/XCUIElementTypeTextField",
                    exec, "infosession85");

            Thread.sleep(1000);



            //Enter Password
            //XCUIElementTypeOther[@name="Medscape Log In"]/XCUIElementTypeOther[5]/XCUIElementTypeSecureTextField

            findAndEnterText(session,"//XCUIElementTypeOther[@name=\"Medscape Log In\"]/XCUIElementTypeOther[5]/XCUIElementTypeSecureTextField",
                    exec,"medscape");

            Thread.sleep(1000);

            //Click on the Login butt
            //XCUIElementTypeButton[@name="Log In"]
            findAndClick(session,"//XCUIElementTypeButton[@name=\"Log In\"]",exec);

            Thread.sleep(10000);

            Command getCtxtHandles = new Command(session,DriverCommand.GET_CONTEXT_HANDLES);
            Response getCtxtResp =exec.execute(getCtxtHandles);
            System.out.println(getCtxtResp.toString());


            String[] ctxtHandles = getCtxtResp.getValue().toString().replaceAll("\\[","").replaceAll("\\]","").split(",");

            for(String ctxt:ctxtHandles) {
                if (ctxt.contains("WEBVIEW")) {
                    Command switchWebView=new Command(session,DriverCommand.SWITCH_TO_CONTEXT, ImmutableMap.of("name", ctxt.trim()));
                    exec.execute(switchWebView);
                    Thread.sleep(1000);
                }
            }


            WebElement mnet=driver.findElement(By.id("mnet"));
            mnet=fluientWaitforElement(mnet,60,5);
            //driver.switchTo().frame(mnet);

            WebElement headerbox= driver.findElement(By.xpath("//*[@id=\"headerbox\"]/div[2]/a/img"));
            headerbox = fluientWaitforElement(headerbox,60,5);
            String title= headerbox.getAttribute("title");
            System.out.println("Title => "+title);

            /*(io.appium.java_client.TouchAction)TouchAction().press(startX,startY).wait(1000)
                    .move_to(endX,endY).release().perform()
*/


            System.out.println("scroll starts");

            scroll(ScrollDirection.DOWN, 0.5);

            System.out.println("scroll ends");




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


    public void findAndEnterText(SessionId session, String eltSelector,WebMdCommandExecutor exec, String text ) throws IOException {


        Command findElt= new Command(session, DriverCommand.FIND_ELEMENT,ImmutableMap.of("using", "xpath"
                ,"value", eltSelector));
        Response findEltResp=exec.execute(findElt);
        System.out.println("Response : => "+ findEltResp.toString());
        Map<String,String> element=(HashMap)findEltResp.getValue();
        System.out.println("Element Id : => "+element.get("ELEMENT"));

        Command isEltPresent = new Command(session, DriverCommand.IS_ELEMENT_DISPLAYED, ImmutableMap.of("id",element.get("ELEMENT")));
        Response isEltResp=exec.execute(isEltPresent);
        System.out.println(isEltResp.toString());
        if(isEltResp.getValue().toString().equalsIgnoreCase("true"))
        {
            Command clkCmd=new Command(session, DriverCommand.CLICK_ELEMENT, ImmutableMap.of("id",element.get("ELEMENT")));
            exec.execute(clkCmd);


            List<CharSequence> textArray=new ArrayList<>();
            textArray.add(text);
            Command sendTxt = new Command(session, DriverCommand.SEND_KEYS_TO_ELEMENT, ImmutableMap.of("id",element.get("ELEMENT"),
                    "value", textArray));
            Response enterTxtResp=exec.execute(sendTxt);
            System.out.println("UserName response => "+enterTxtResp.toString());

        }
    }

    public void findAndClick(SessionId session, String eltSelector,WebMdCommandExecutor exec) throws IOException {
        Command findElt= new Command(session, DriverCommand.FIND_ELEMENT,ImmutableMap.of("using", "xpath"
                ,"value", eltSelector));
        Response findEltResp=exec.execute(findElt);
        System.out.println("Response : => "+ findEltResp.toString());
        Map<String,String> element=(HashMap)findEltResp.getValue();
        System.out.println("Element Id : => "+element.get("ELEMENT"));

        Command isEltPresent = new Command(session, DriverCommand.IS_ELEMENT_DISPLAYED, ImmutableMap.of("id",element.get("ELEMENT")));
        Response isEltResp=exec.execute(isEltPresent);
        System.out.println(isEltResp.toString());
        if(isEltResp.getValue().toString().equalsIgnoreCase("true"))
        {
            Command clkCmd=new Command(session, DriverCommand.CLICK_ELEMENT, ImmutableMap.of("id",element.get("ELEMENT")));
            exec.execute(clkCmd);

        }
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
    public void scroll(ScrollDirection dir, double scrollRatio) throws UnsupportedCommandException{
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
            if (dir == ScrollDirection.UP) {
                swipe(new Point(midPoint.x, top), new Point(midPoint.x, bottom), SCROLL_DUR);
            } else if (dir == ScrollDirection.DOWN) {
                swipe(new Point(midPoint.x, bottom), new Point(midPoint.x, top), SCROLL_DUR);
            } else if (dir == ScrollDirection.LEFT) {
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
