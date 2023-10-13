package com.browserstack.test.consoleannotations;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class UploadAPITest {
    public static final String AUTOMATE_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    public static final String AUTOMATE_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    public static final String WEB_URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";


    @Test
    public void testLargeFile() throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("browserVersion", "latest");
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("os", "Windows");
        browserstackOptions.put("osVersion", "10");
        browserstackOptions.put("local", "false");
        browserstackOptions.put("debug", "true");
        browserstackOptions.put("networkLogs", "true");
        browserstackOptions.put("seleniumVersion", "3.141.59");
        browserstackOptions.put("projectName", "File-Upload");
        browserstackOptions.put("buildName", "FileUpload-debugging");
        browserstackOptions.put("sessionName", "Upload-CSV-test");

        //WebDriver driver = new RemoteWebDriver(new URL("https://venkateshraghuna_DNVo9K:YOUR_ACCESSKEY@hub-cloud.browserstack.com/wd/hub"), capabilities);
        RemoteWebDriver driver =  new RemoteWebDriver(new URL(WEB_URL), capabilities);

        try {
            driver.get("http://www.fileconvoy.com");
            driver.setFileDetector(new LocalFileDetector());
            WebElement uploadElement = driver.findElement(By.id("upfile_0"));

            uploadElement.sendKeys("/Users/venkatesh/Downloads/Electric_Vehicle_Population_Data.csv");
            ((JavascriptExecutor) driver).executeScript("document.getElementById('readTermsOfUse').click();");
            driver.findElement(By.name("upload_button")).submit();
            WebElement topMessage = driver.findElement(By.id("TopMessage"));
            if (topMessage.getText().contains("successfully uploaded")) {
                ((JavascriptExecutor) driver).executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"passed\",\"reason\": \"File upload successful\"}}");
            } else {
                ((JavascriptExecutor) driver).executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\",\"reason\": \"File upload failed\"}}");
            }
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\",\"reason\": \"Something wrong with script\"}}");
        } finally {
            driver.quit();
        }

    }

}
