package com.qa.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.qa.BaseTest;


import com.qa.reports.ExtentReport;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


//ITestListerners is a predefined class
public class TestListener implements ITestListener {

    //onTestFaliure is a method in ITestListener
    //this is used for writing logs

    @Override
    public void onTestFailure(ITestResult result) {
        if(result.getThrowable() != null)
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            result.getThrowable().printStackTrace(pw);
            System.out.println(sw.toString());
        }

        // to get ss
        BaseTest base = new BaseTest();
        File fileobj = base.getDriver().getScreenshotAs(OutputType.FILE);



        Map<String, String> params =  new HashMap<String, String >();
        params = result.getTestContext().getCurrentXmlTest().getAllParameters();

        //create your folder to get screenshot saved
        String imagePath = "Screenshots" + File.separator + params.get("platformName") + "_" + params.get("deviceName")
+ "_" + params.get("udid") +File.separator + base.getDateandTime()+ File.separator
                + result.getTestClass().getRealClass().getSimpleName() + File.separator+ result.getName() + ".png";


  // to get complete directory path
        String completeImagePath =  System.getProperty("user.dir") + File.separator + imagePath;


        try {
            FileUtils.copyFile(fileobj,new File(imagePath));
            Reporter.log("This is the sample screenshot");
            // to send screenshot to testNg report
            Reporter.log("<a href = '" + completeImagePath + "'> <img src = '" + completeImagePath + "' height = '100' width '100'/></a>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       // ExtentReport.getTest().log(Status.PASS,"Test Failed");
        //senc screenshot to reports on failure and it will mark as test case failed
       ExtentReport.getTest().fail("Test Failed",
               MediaEntityBuilder.createScreenCaptureFromPath(completeImagePath).build());




    }

    //this method is before statrting ant test case

    @Override
  public void onTestStart(ITestResult result) {
        BaseTest base = new BaseTest();
        //assignCatgory is used to catgorize the test case, its a predefined method
      ExtentReport.startTest(result.getName(),result.getMethod().getDescription()).assignCategory(base.getPlatform())
              .assignAuthor("Abishek");
    }

    @Override
   public void onTestSuccess(ITestResult result) {

        ExtentReport.getTest().log(Status.PASS,"Test Passed");

    }


    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReport.getTest().log(Status.PASS,"Test Skipped");


    }

    @Override
   public void onFinish(ITestContext context) {

        ExtentReport.getReporter().flush(); // adding here will write in reports after all test case is done
    }



}
