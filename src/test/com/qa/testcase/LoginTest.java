package com.qa.testcase;

import com.google.gson.Gson;
import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;
import io.appium.java_client.AppiumBy;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.*;
import java.lang.reflect.Method;

//extend BaseTest to initialize the driver
public class LoginTest extends BaseTest {

    /*
    * we are always using login page before each test case, so use it under before method
    * we need to create obj for login page class as we are using in loginTest
    * create obj at class level and define it in @BeforMethod
    *
    * */


LoginPage loginPageobj;
ProductsPage productsPageobj;


// to read json file creat obj for JSONObject clas (its predefined clasS)
    JSONObject jsonObj;

//to read the JSON file

@BeforeClass

public void readJson () throws IOException {
    InputStream datais = null;
    try{


        String dataFileName = "./resources/data/loginUsers.json";
        datais = new FileInputStream(dataFileName);
        JSONTokener tokener = new JSONTokener(datais);


        jsonObj= new JSONObject(tokener);
    }catch (Exception e)
    {
            e.printStackTrace();
            throw  e;
    }finally {

        if(datais != null)
        {
            datais.close();
        }

    }




}


@BeforeMethod

//Method is a predefined class to print the testcase that is currently executing
public void beforeMethod(Method m)
{
    loginPageobj = new LoginPage();

    LaunchApp();

    //to print the current test case execution method
    //getName() will get the name of each method in the test case
    System.out.println("\n" + "**** Starting Testcase:" + m.getName() + "****" + "\n");


}

@AfterMethod

public void afterMethod()
{

    LogoutApp();
}


//to  check invalid username and password
@Test
    public void invalidUserName(){

    //reading values from json file
    //jsonObj.getJSONObject("invalidUser").getString("userName") is one string together
    loginPageobj.enterUserName(jsonObj.getJSONObject("invalidUser").getString("userName"));
    loginPageobj.enterPassword(jsonObj.getJSONObject("invalidUser").getString("password"));
    loginPageobj.clickLoginButton();


    String actualError = loginPageobj.getErrorText();
    String ExpectedError = "Username and password do not match any user in this service.";


    System.out.println("actual error is: " + actualError + "\n" +"expected error is: " +ExpectedError);

    //error validation

    Assert.assertEquals(actualError,ExpectedError);

}

@Test
    public void emptyUsername()
    {

        loginPageobj.emptyUserName();
        loginPageobj.clickLoginButton();
        String actualError = loginPageobj.getErrorText();

        System.out.println(actualError);
        String userNameError = "Username is required";

        Assert.assertEquals(actualError,userNameError);

    }

    @Test
    public void validLogin() {


        loginPageobj.enterUserName(jsonObj.getJSONObject("validUserName").getString("userName"));
        loginPageobj.enterPassword(jsonObj.getJSONObject("validUserName").getString("password"));

        //need to go to products page
       productsPageobj = loginPageobj.clickLoginButton();

        String actualProductTitle = productsPageobj.getTitle();
        String expectedProductTitle = "PRODUCTStes";



        //error validation

        Assert.assertEquals(actualProductTitle, expectedProductTitle);
    }

}
