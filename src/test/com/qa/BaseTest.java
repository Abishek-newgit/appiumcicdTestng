package com.qa;


import com.google.common.collect.ImmutableMap;
import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//copied the below line 31 from https://www.extentreports.com/docs/versions/4/java/testng.html

public class BaseTest {

    //ThreadLocal is used to make your framework Thread safe

    //static key word is used here as this belogs to this class and not any obj of this class

    // this static is used as Appium driver is common for all obj of this class Baste Test
    //
    protected static ThreadLocal <AppiumDriver> driver = new ThreadLocal<AppiumDriver>();
    protected static ThreadLocal <Properties> props = new ThreadLocal<Properties>(); //import a inbulit Properties class

    protected static ThreadLocal <String> platform = new ThreadLocal<String>();

    protected static ThreadLocal <String> dateTime = new ThreadLocal<String>();

    //to start Appium progrmatically
    protected static AppiumDriverLocalService server;

    TestUtils utilsObj = new TestUtils();

    //to get logs
    //import Logger and Logmanager from import org.apache.logging.log4j.LogManager;
    //import org.apache.logging.log4j.Logger;
    static Logger log = LogManager.getLogger(BaseTest.class.getName()); //getting the class name

    public AppiumDriver getDriver()
    {
        return driver.get(); //returns the value for particular thread
    }

    public void setDriver(AppiumDriver driver2)
    {
        driver.set(driver2); //sets the value to the current thread
    }

    public Properties getProperty()
    {
        return props.get();
    }

    public void setProperty(Properties props2)
    {
         props.set(props2);
    }

    public String getPlatform()
    {
       return platform.get();
    }

    public void setPlatform(String platform1)
    {
        platform.set(platform1);

    }

    public String getDateandTime()
    {
        return dateTime.get();
    }

    public void setDateandTime(String dateandTime1)
    {
        dateTime.set(dateandTime1);

    }




    //define it in each page class

/* public BaseTest(){
        PageFactory.initElements(driver, this  ); // passing this(LoginPage) class

    }*/

  /* @BeforeSuite
    public void beforeSuite(){

        server = getAppiumServerDefault();
        server.start();
        server.clearOutPutStreams(); //to remove the server logs in your IDE console
        System.out.println("Server is started");

    }

    @AfterSuite
    public void afterSuite()
    {
        server.stop();
        System.out.println("Server is stopped");
    }*/

    //to get the AppiumDriverLocal service obj
    public AppiumDriverLocalService getAppiumServerDefault()
    {
        return AppiumDriverLocalService.buildDefaultService();
    }



    //initialized the driver

    //@BeforeTest - will execute before @Test
    //@AfterTest - will execute after @Test
    //@Parameters will get the paramter from the testngxml file
    //now we need to pass this parameters as arguments in driverInit method

    @Parameters({"emulator","platformName", "deviceName","udid","chromeDriverPort",
    "wdaLocalPort","webkitdebugProxyPort"})

@BeforeClass

    //synchronized keyword is used for other threads to wait when alread one thread is using this method

    public synchronized void initializeDriver(@Optional("androidOnly")  String emulator, String platformName, String deviceName, String udid, //@Optional("androidOnly") String systemPort,
                                              @Optional("androidOnly")   String chromeDriverPort,  @Optional("iOSOnly") String wdaLocalPort,  @Optional("iOSOnly")String webkitDebugProxyPort   ) throws Exception {

        log.info("The driver initialization is started");
        log.error("This is the error message");

        FileInputStream input = null;

        setDateandTime(utilsObj.realTimeDateAndTime()); // use setdate and time method to get the time from Utils class
        setPlatform(platformName); // use platformName parameter to set the platform in the line 82
        Properties props = new Properties();
        AppiumDriver driver; //local variable
        URL url;

        try{


            String propFileName = "./resources/config.properties";

            //input = (FileInputStream) getClass().getClassLoader().getResourceAsStream(propFileName);
            // to read the props file
            props.load(new FileInputStream(propFileName));
            setProperty(props);


            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("platformName", platformName);
            caps.setCapability("udid", udid);
            url = new URL(props.getProperty("appiumURL")+ "4723");


            switch (platformName){

                case "Android":

                    caps.setCapability("automationName", props.getProperty("androidAutomationName"));



                        caps.setCapability("deviceName", deviceName);

                        //caps.setCapability("avd", deviceName); // to start the emulator automatically
                    //caps.setCapability("udid","RZCW111YE0V"); //real device
                    if(emulator.equalsIgnoreCase("true"))
                    {
                        caps.setCapability("deviceName", deviceName);
                    } else{
                        caps.setCapability("deviceName",deviceName);
                    }
                    String appUrl = System.getProperty("user.dir")+"/ResourcesBuild/App/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk";
                    // caps.setCapability("app", appUrl);
                   // caps.setCapability("systemPort", systemPort);
                    caps.setCapability("chromeDriverPort", chromeDriverPort);
                    //URL appURL = getClass().getResource(props.getProperty("androidAppLocation"));
                    caps.setCapability("app",appUrl);

                    caps.setCapability("appPackage", props.getProperty("androidAppPackage"));
                    caps.setCapability("appActivity", props.getProperty("androidAppActivity"));

                     //url = new URL(props.getProperty("appiumURL")+ "4723");
                    //this driver is created at method level refer line 157
                    driver = new AndroidDriver(url,caps);
                    break;

                case "iOS":
                  //   caps.setCapability("platformName", "iOS");
                    caps.setCapability("automationName", props.getProperty("iosAutomationName"));
                    caps.setCapability("deviceName", deviceName);
                    caps.setCapability("wdaLocalPort", wdaLocalPort);
                    caps.setCapability("webkitDebugProxyPort", webkitDebugProxyPort);

                   // String iosappURL = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "swagLabsiOS.app";
                   // System.out.println(iosappURL);
                    // caps.setCapability("app", appUrl);
                  // URL iosappURL = getClass().getResource(props.getProperty("iosAppLocation"));
                    String iosappURL = "/Users/abishekr/IdeaProjects/Framwork_Scratch/ResourcesBuild/App/swagLabsiOS.app";
                   //System.out.println(iosappURL);
                    caps.setCapability("app",iosappURL);
                  //  caps.setCapability("bundleId", props.getProperty("iOSBundleId"));

                   // url = new URL(props.getProperty("appiumURL")+ "4724"); //use this if you need different Appium server


                     //adding port num
                    //this driver is created at method level refer line 157
                    driver = new IOSDriver(url,caps);
                    break;

                 default:
                    throw new Exception("Invalid Platform! : " + platformName);

            }

            setDriver(driver);


        }catch (Exception e)
        {
            e.printStackTrace();
            throw  e;
        }



    }



    //Created a TestUtils class to define wait
    public void waitForVisibility(WebElement element)
    {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(TestUtils.Wait)); //refer Test Utils class
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    //this i created to logout of the app to check validlogin case in ProductTest

 /*   public void productPageElementIsVisible(WebElement element)
    {
           WebElement element1 = driver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\\\"test-Cart drop zone\\\"]/android.view.ViewGroup/android.widget.TextView\""));
            if(element1.isDisplayed())
            {
                LogoutApp();
            }

    }*/

    public void click(WebElement element)
    {
        waitForVisibility(element);
        element.click();
    }

    //overloading the click method
    public void click(WebElement element, String message)
    {
        waitForVisibility(element);
        utilsObj.log(message);
        element.click();
    }

    public void sendKeys(WebElement element, String txt)
    {
        waitForVisibility(element);
        element.sendKeys(txt);
    }

    public void sendKeys(WebElement element, String txt, String message)
    {
        waitForVisibility(element);
        utilsObj.log(message);
        element.sendKeys(txt);
    }

    //we are returning the title
    public String  getAttribute(WebElement element, String attribute)
    {
        waitForVisibility(element);
       return element.getAttribute(attribute);
    }

    public String getText(WebElement element){
        switch (getPlatform())
        {
            case "Android":
                return getAttribute(element,"text");

            case "iOS":
                return getAttribute(element,"label");
        }
        return null; // not returning anything
    }

    /*public String getText(WebElement element, String message){
       String msgText = null;
        switch (getPlatform())
        {
            case "Android":
                msgText = getAttribute(element,"text");

            case "iOS":
                msgText = getAttribute(element,"label");
        }
        return msgText; // not returning anything
    }*/

    public void clear(WebElement element){

        waitForVisibility(element);
        element.clear();
    }



    public void tapByCoordinates()
    {
        getDriver().executeScript("mobile: clickGesture", ImmutableMap.of(
                "x",29 ,
                "y", 53
        ));
    }

    //scroll code dod: https://developer.android.com/reference/androidx/test/uiautomator/UiScrollable
    //cannot use xpath in UI scrollable
    //this scroll is specific to product price

    public WebElement scrollToElement(){
        return  getDriver().findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().description(\"test-Price\"));"));
    }

    public WebElement iOSScrollToElement()
    {
        //to scroll to particular element, find the scrollable parent and the particular predicate string of child
        // but if you know the accessibility id of the elemet for which it should scroll then parent id is not required

       // RemoteWebElement parentelement = (RemoteWebElement)driver.findElement(AppiumBy.className("XCUIElementTypeOther"));
        RemoteWebElement element = (RemoteWebElement)getDriver().findElement(AppiumBy.name("test-ADD TO CART"));
        String elementId = element.getId();
        Map<String, Object> params = new HashMap<>();
        params.put("element", elementId);
   //     params.put("direction", "down"); // use this if only parent element is given
        // params.put("predicateString", "label == \"Web View\""); //child element
       // params.put("predicateString", "label == 'ADD TO CART'");
      //  params.put("name", "test-ADD TO CART");
        params.put("toVisible", "test-ADD TO CART"); //give the element accessibility id, xpath wont work
        getDriver().executeScript("mobile: scrollToElement", params);
        return element;
    }

    public void LogoutApp()
    {
        switch (getPlatform())
    {
        case "Android":
            ((InteractsWithApps)getDriver()).terminateApp(getProperty().getProperty("androidAppPackage"));
            break;

        case "iOS":
            ((InteractsWithApps)getDriver()).terminateApp(getProperty().getProperty("iOSBundleId"));


    }

    }

    public void LaunchApp()
    {
        switch (getPlatform())
        {
            case "Android":

                ((InteractsWithApps)getDriver()).activateApp(getProperty().getProperty("androidAppPackage"));
                break;

            case "iOS":
                ((InteractsWithApps)getDriver()).activateApp(getProperty().getProperty("iOSBundleId"));

        }
    }


@AfterClass
    public void quitDriver()
    {
        getDriver().quit();
    }

}
