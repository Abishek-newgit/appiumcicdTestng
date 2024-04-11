package com.qa.testcase;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class AndroidDebug {

    public static void main(String[] args) throws MalformedURLException {

        DesiredCapabilities caps = new DesiredCapabilities();


        caps.setCapability("platformName", "Android");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("deviceName", "ppixel4");
       // caps.setCapability("udid", "0FF07A15-687E-47A8-9F0A-5D58CC7A17D4");
        String appUrl = "/Users/abishekr/IdeaProjects/Framwork_Scratch/ResourcesBuild/App/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk";

        caps.setCapability("app", appUrl);
        caps.setCapability("appPackage", "com.swaglabsmobileapp");
        caps.setCapability("appActivity","com.swaglabsmobileapp.MainActivity");



        URL url = new URL("http://0.0.0.0:4723"); // Add appium server url


        AppiumDriver driver = new AndroidDriver(url,caps);



       driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.accessibilityId("test-LOGIN")).click();
        //driver.findElement(AppiumBy.accessibilityId("test-Menu")).click();
        driver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Menu\"]/android.view.ViewGroup/android.widget.ImageView")).click();





    }
}
