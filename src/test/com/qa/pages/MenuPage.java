package com.qa.pages;

import com.qa.BaseTest;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MenuPage extends BaseTest {


    public MenuPage(){

        PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this  ); // passing this(MenuPage) class

    }

    @AndroidFindBy(accessibility = "test-LOGOUT")
    @iOSXCUITFindBy(iOSNsPredicate = "label == \"LOGOUT\"")
    private WebElement LogoutBttn;


    public LoginPage clickLogout()
    {
        click(LogoutBttn);
        return new LoginPage();
    }
}
