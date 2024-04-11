package com.qa.pages;

import com.qa.BaseTest;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BaseTest  {



    //create constructor to initalize pagefactory
    //create obj for base test to call driver initialization
   public LoginPage(){

       PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this  ); // passing this(LoginPage) class

    }

    // user name id
    @AndroidFindBy(accessibility = "test-Username" )
   @iOSXCUITFindBy(id = "test-Username")
    private WebElement usernameTextField; //  this is nothing but : WebElement usernameTextfiled = driver.findElement(AppiumBy.accessibilityId("test-LOGIN"));

   //passoword id
    @AndroidFindBy(accessibility = "test-Password" )
    @iOSXCUITFindBy(id = "test-Password")
    private WebElement passwordTextField;

    //Login Button elemnet id
    @AndroidFindBy(accessibility = "test-LOGIN" )
    @iOSXCUITFindBy(id = "test-LOGIN")
    private WebElement loginBtn;

    //Error message
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"test-Error message\"]/child::XCUIElementTypeStaticText")
    private WebElement errorMessage;

    //hamburger menu

    //in page factory, its must to add return type
    //in below method we are staying in LoginPage class only so return the class obj
    // in Public LoginPage enterUserName() method, LoginPage is the return type like how we write public void, public string
    public LoginPage  enterUserName(String userName)
    {
        clear(usernameTextField);
        sendKeys(usernameTextField,userName, "Userame is entered successfully!!" + userName);
         return this;

    }

    public LoginPage enterPassword(String password)
    {
        clear(passwordTextField);
      sendKeys(passwordTextField,password, "Password is entered !!");
        return this;
    }

    public LoginPage emptyUserName()
    {
         usernameTextField.clear();

         return this;
    }

    public ProductsPage clickLoginButton()
    {
       click(loginBtn, "Login button is pressed");
        return new ProductsPage();
    }

    public String getErrorText()
    {

        return getText(errorMessage);
    }

    //to logout



    public ProductsPage properLogin(String userName, String password)
    {
        enterUserName(userName);
        enterPassword(password);
       return clickLoginButton(); // as this method returns the productPage class

    }



}


