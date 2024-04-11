package com.qa.testcase;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class IOSDebug {

    public static void main(String[] args) throws MalformedURLException, InterruptedException {

        DesiredCapabilities caps = new DesiredCapabilities();


        caps.setCapability("platformName", "iOS");
        caps.setCapability("automationName", "XCUITest");
        caps.setCapability("deviceName", "iPhone 15 Plus");
        caps.setCapability("udid", "0FF07A15-687E-47A8-9F0A-5D58CC7A17D4");
        String appUrl = "/Users/abishekr/IdeaProjects/Framwork_Scratch/ResourcesBuild/App/swagLabsiOS.app";

        caps.setCapability("app", appUrl);

        URL url = new URL("http://0.0.0.0:4723"); // Add appium server url


        AppiumDriver driver = new IOSDriver(url,caps);



        driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.accessibilityId("test-LOGIN")).click();
        Thread.sleep(3000);
//for Android
        /*driver.executeScript("mobile: clickGesture", ImmutableMap.of(
                "x",29 ,
                "y", 53
                ));*/

        //for ioS
        Map<String, Object> params = new HashMap<>();
        //params.put("elementId", ((RemoteWebElement)element).getId());
        params.put("x", 29);
        params.put("y", 53);
        driver.executeScript("mobile: tap", params);


        }
}
