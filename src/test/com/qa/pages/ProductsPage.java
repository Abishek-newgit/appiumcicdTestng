package com.qa.pages;

import com.qa.BaseTest;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ProductsPage extends BaseTest  {



    //create constructor to initalize pagefactory
    public ProductsPage(){

        PageFactory.initElements(new AppiumFieldDecorator(getDriver()),this  ); // passing this(LoginPage) class

    }

    //productcs Title

     @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView")
     @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"test-Toggle\"]/parent::*[1]/preceding-sibling::XCUIElementTypeStaticText")
     private WebElement ProductTitle;

    //ProductNames

    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[@name=\"test-Item title\"])[1]")
    private WebElement productName;

    //product price
    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Price\"])[1]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[@name=\"test-Price\"])[1]")

    private WebElement productPrice;

    // hamburger menu

    //@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Menu\"]/android.view.ViewGroup/android.widget.ImageView")
 //   @iOSXCUITFindBy(xpath = )
  //  private WebElement hamburgerMenu;





    //in page factory, its must to add return type
    //in below method we are returning the product header (String)
    public String getTitle()
    {
      return getText(ProductTitle);
    }

    public String getProductName()
    {
        return getText(productName);
    }

    public String getProductPrice()
    {
        return getText(productPrice);
    }

    public ProductDetailsPage clickProduct()
    {
        click(productName);
        return new ProductDetailsPage();
    }

    public MenuPage clickHamburgerMenu()
    {
        tapByCoordinates();
        return new MenuPage();
    }

    /*public LoginPage isProductElementVisible()
    {
      productPageElementIsVisible(ProductTitle);
      return new LoginPage();
    }*/


}


