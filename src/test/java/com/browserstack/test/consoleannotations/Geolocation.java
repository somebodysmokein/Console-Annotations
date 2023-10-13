package com.browserstack.test.consoleannotations;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;

import java.util.Map;

public class Geolocation {


    public static void main(String... args)
    {
        Map coordinates = Map.of(
                "latitude", 30.3079823,
                "longitude", -97.893803,
                "accuracy", 1
        );

        WebDriver driver=null;
        WebDriverManager.chromedriver().setup();
        driver= new ChromeDriver();

        /*driver.executeCdpCommand("Emulation.setGeolocationOverride", coordinates);

        driver.navigate().to("https://oldnavy.gap.com/stores");

        var addresses = driver.findElementsByClassName("address");
        assertTrue(addresses.size() > 0, "No addresses found");
        assertTrue(addresses.
                        stream()
                        .allMatch(a -> a.getText().contains(", TX ")),
                "Some addresses listed are not in Texas");*/
    }
}
