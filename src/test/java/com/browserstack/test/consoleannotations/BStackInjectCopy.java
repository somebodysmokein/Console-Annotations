package com.browserstack.test.consoleannotations;


import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class BStackInjectCopy {

    public static final String AUTOMATE_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    public static final String AUTOMATE_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    public static final String WEB_URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
    public WebDriver driver=null;


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
        //driver = new IOSDriver(new URL(WEB_URL), capabilities);
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

            /*Command findLogin= new Command(session, DriverCommand.FIND_ELEMENT,ImmutableMap.of("using", "xpath"
                    ,"value", "//XCUIElementTypeOther[@name=\"Medscape Log In\"]/XCUIElementTypeOther[6]/XCUIElementTypeTextField"));
            Response findLgnResp=exec.execute(findLogin);
            System.out.println("Response : => "+ findLgnResp.toString());
            Map<String,String> element=(HashMap)findLgnResp.getValue();
            System.out.println("Element Id : => "+element.get("ELEMENT"));
            Thread.sleep(2000);

            Command isLoginPresent = new Command(session, DriverCommand.IS_ELEMENT_DISPLAYED, ImmutableMap.of("id",element.get("ELEMENT")));
            Response isLoginPresentResp=exec.execute(isLoginPresent);
            System.out.println(isLoginPresentResp.toString());
            if(isLoginPresentResp.getValue().toString().equalsIgnoreCase("true"))
            {
                Command clkusrNm=new Command(session, DriverCommand.CLICK_ELEMENT, ImmutableMap.of("id",element.get("ELEMENT")));
                exec.execute(clkusrNm);
                Thread.sleep(1000);
                System.out.println("infosession85".toCharArray());
                StringBuilder sb=new StringBuilder();
                sb.append("infosession85");
                List<CharSequence> userNameArray=new ArrayList<>();
                userNameArray.add("infosession85");
                Command sendUsrNm = new Command(session, DriverCommand.SEND_KEYS_TO_ELEMENT, ImmutableMap.of("id",element.get("ELEMENT"),
                        "value", userNameArray));
                Response urNmResp=exec.execute(sendUsrNm);
                System.out.println("UserName response => "+urNmResp.toString());

            }*/

            findAndEnterTex(session,"//XCUIElementTypeOther[@name=\"Medscape Log In\"]/XCUIElementTypeOther[6]/XCUIElementTypeTextField",
                    exec, "infosession85");

            Thread.sleep(1000);

            /*Command findPwd =new Command(session, DriverCommand.FIND_ELEMENT,ImmutableMap.of("using", "xpath"
                    ,"value", "//XCUIElementTypeOther[@name=\"Medscape Log In\"]/XCUIElementTypeOther[8]/XCUIElementTypeSecureTextField"));
            Response findpwdResp=exec.execute(findPwd);
            System.out.println("Response : => "+ findpwdResp.toString());
            Map<String,String> pwdElt=(HashMap)findpwdResp.getValue();
            System.out.println("Password Element Id : => "+pwdElt.get("ELEMENT"));
            Thread.sleep(2000);

            Command isPwdPresent = new Command(session, DriverCommand.IS_ELEMENT_DISPLAYED, ImmutableMap.of("id",pwdElt.get("ELEMENT")));
            Response isPwdPresentResp=exec.execute(isPwdPresent);
            System.out.println(isLoginPresentResp.toString());
            if(isPwdPresentResp.getValue().toString().equalsIgnoreCase("true"))
            {

                Command clkPwd=new Command(session, DriverCommand.CLICK_ELEMENT, ImmutableMap.of("id",pwdElt.get("ELEMENT")));
                exec.execute(clkPwd);
                Thread.sleep(1000);
                List<CharSequence> pwdArray=new ArrayList<>();
                pwdArray.add("medscape");
                Command sendPwd = new Command(session, DriverCommand.SEND_KEYS_TO_ELEMENT, ImmutableMap.of("id",pwdElt.get("ELEMENT"),
                        "value", pwdArray));
                Response pwdResp=exec.execute(sendPwd);
                System.out.println("Password response => "+pwdResp.toString());

            }*/

            findAndEnterTex(session,"//XCUIElementTypeOther[@name=\"Medscape Log In\"]/XCUIElementTypeOther[8]/XCUIElementTypeSecureTextField",
                    exec,"medscape");

            Thread.sleep(1000);


            /*Command findLgnBtn= new Command(session, DriverCommand.FIND_ELEMENT,ImmutableMap.of("using", "xpath"
                    ,"value", "//XCUIElementTypeButton[@name=\"Log In\"]"));
            Response findlgnBtnResp=exec.execute(findLgnBtn);
            Map<String,String> lgnBtnElt=(HashMap)findlgnBtnResp.getValue();
            System.out.println("Login Button Element Id : => "+pwdElt.get("ELEMENT"));
            Thread.sleep(2000);


            Command isLgnBtnPresent = new Command(session, DriverCommand.IS_ELEMENT_DISPLAYED, ImmutableMap.of("id",lgnBtnElt.get("ELEMENT")));
            Response isLgnBtnPresentResp=exec.execute(isLgnBtnPresent);
            System.out.println(isLgnBtnPresentResp.toString());
            if(isLgnBtnPresentResp.getValue().toString().equalsIgnoreCase("true"))
            {
                Command clkLgnBtn = new Command(session, DriverCommand.CLICK_ELEMENT, ImmutableMap.of("id",lgnBtnElt.get("ELEMENT")));
                Response lgnBtnClkResp=exec.execute(clkLgnBtn);
                System.out.println("Login response => "+lgnBtnClkResp.toString());

            }*/

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


    public void findAndEnterTex(SessionId session, String eltSelector,WebMdCommandExecutor exec, String text ) throws IOException {


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


}
